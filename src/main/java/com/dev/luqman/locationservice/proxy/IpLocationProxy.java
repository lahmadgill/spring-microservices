package com.dev.luqman.locationservice.proxy;

import com.dev.luqman.locationservice.model.Location;
import reactor.core.publisher.Mono;

public interface IpLocationProxy {
    Mono<Location> getLocationByUserAgent(String ipAddress);
}
