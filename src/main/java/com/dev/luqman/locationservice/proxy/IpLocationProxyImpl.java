package com.dev.luqman.locationservice.proxy;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.dev.luqman.locationservice.exception.LocationApiException;
import com.dev.luqman.locationservice.model.Location;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
public class IpLocationProxyImpl implements IpLocationProxy {
    private static Logger LOG = LoggerFactory.getLogger(IpLocationProxyImpl.class);
    private static final String URI = "/ipgeo";
    private static final String API_KEY = "apiKey";
    private static final String IP = "ip";
    
    private WebClient webClient;

    @Value("${ip.location.baseurl}")
    private String baseUrl;

    @Value("${ip.location.conTimeout.ms}")
    private int conTimeoutsMs;

    @Value("${ip.location.apikey}")
    private String apiKey;


    @PostConstruct
    private void initWebClient() {
        final HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, this.conTimeoutsMs)
            .responseTimeout(Duration.ofMillis(this.conTimeoutsMs))
                .wiretap(true)
            .doOnConnected(conn -> 
                conn.addHandlerLast(new ReadTimeoutHandler(this.conTimeoutsMs, TimeUnit.MILLISECONDS))
                .addHandlerLast(new WriteTimeoutHandler(this.conTimeoutsMs, TimeUnit.MILLISECONDS)));


        this.webClient = WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .baseUrl(this.baseUrl)
        .build();
    }


    @Override
    public Mono<Location> getLocationByUserAgent(final String ipAddress) {
        LOG.info("Query the ip service for {}", ipAddress);
        Mono<Location> location = webClient.get()
        .uri(builder -> builder.path(URI).queryParam(API_KEY, apiKey).build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .onStatus(status -> status.isError(), response -> {
            LOG.warn("Got  error from IP service, status:{}", response.statusCode());
            return Mono.error(new LocationApiException("Received non 2xx", response.statusCode().value()));
        })
        .bodyToMono(Location.class)
        .onErrorMap(Predicate.not(LocationApiException.class::isInstance), th -> new LocationApiException(String.format("Fatal error %s", th.getMessage())));

        return location;
    }

}
