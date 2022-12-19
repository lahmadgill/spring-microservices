package com.dev.luqman.locationservice.endpoint;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthEndpoint {

    @GetMapping
    public Map<String, String> health() {
        return Map.of("success", "200", "msg" , "success");
    }
}
