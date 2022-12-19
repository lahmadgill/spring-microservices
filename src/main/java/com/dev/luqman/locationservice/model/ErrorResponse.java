package com.dev.luqman.locationservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorResponse(@JsonProperty("msg") String message, @JsonProperty("error") String error) {}
