package com.dev.luqman.locationservice.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record Location(String ip, String hostname, @JsonProperty("continent_name") String continentName,
                       @JsonProperty("country_code") String countryCode, @JsonProperty("country_name") String countryName,
                       @JsonProperty("state_prov") String province, @JsonProperty("latitude") String latitude, @JsonProperty("longitude") String longitude, Currency currency, @JsonProperty("time_zone") Timezone timezone) implements  Serializable{


    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Currency (String code, String name, String symbol) {}

    record Timezone(String name, @JsonProperty("current_time") String currentTime,
    @JsonProperty("is_dst") boolean isDayLight, @JsonProperty("dst_savings") Integer dayLightSaving){}

}