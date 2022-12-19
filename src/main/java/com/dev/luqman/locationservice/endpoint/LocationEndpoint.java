package com.dev.luqman.locationservice.endpoint;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.luqman.locationservice.model.Location;
import com.dev.luqman.locationservice.proxy.IpLocationProxy;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/location")
public class LocationEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(LocationEndpoint.class);

    private final IpLocationProxy proxy;

    public LocationEndpoint(final IpLocationProxy proxy) {
        this.proxy = proxy;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Location> getLocation(@RequestHeader Map<String, String> headers, final ServerHttpRequest request) {
        LOG.info("Query the location based on the device/client IP addresses");
        LOG.info("The headers is {}", headers);
        return proxy.getLocationByUserAgent(request.getRemoteAddress().getAddress().toString());
    }

    
}
