package com.weathercheck.shared.geo.geolocation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weathercheck.shared.http.HttpJsonClient;

import java.util.Optional;

public class IpGeolocationService implements GeolocationService {
    private static final String GEO_IP_URL = "https://ipwho.is/";

    private final HttpJsonClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public IpGeolocationService(HttpJsonClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Optional<GeoCoordinates> locate() {
        try {
            String json = httpClient.get(GEO_IP_URL);
            IpWhoIsResponse response = objectMapper.readValue(json, IpWhoIsResponse.class);
            if (response == null || !response.success() || response.latitude() == null || response.longitude() == null) {
                return Optional.empty();
            }
            return Optional.of(new GeoCoordinates(response.latitude(), response.longitude()));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record IpWhoIsResponse(
            @JsonProperty("success") boolean success,
            @JsonProperty("latitude") Double latitude,
            @JsonProperty("longitude") Double longitude
    ) {
    }
}
