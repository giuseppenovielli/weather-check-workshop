package com.weathercheck.features.location.services.geolocation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeoCoordinates(
        @JsonProperty("latitude") double latitude,
        @JsonProperty("longitude") double longitude
) {
}
