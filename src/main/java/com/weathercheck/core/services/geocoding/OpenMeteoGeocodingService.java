package com.weathercheck.core.services.geocoding;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weathercheck.core.http.HttpJsonClient;
import com.weathercheck.core.services.geolocation.GeoCoordinates;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class OpenMeteoGeocodingService implements GeocodingService {
    private final HttpJsonClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenMeteoGeocodingService(HttpJsonClient client) {
        this.client = client;
    }

    @Override
    public Optional<GeoCoordinates> getLocation(String query) {
        if (query == null || query.isBlank()) {
            return Optional.empty();
        }

        String encodedQuery = URLEncoder.encode(query.trim(), StandardCharsets.UTF_8);
        String url = String.format(
                "https://geocoding-api.open-meteo.com/v1/search?name=%s&count=1&language=it&format=json",
                encodedQuery
        );

        try {
            String json = client.get(url);
            JsonNode firstResult = objectMapper.readTree(json).path("results").path(0);
            if (firstResult.isMissingNode()) {
                return Optional.empty();
            }

            GeoCoordinates coordinates = objectMapper.treeToValue(firstResult, GeoCoordinates.class);
            if (coordinates == null) {
                return Optional.empty();
            }
            return Optional.of(coordinates);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getPlacemarks(double latitude, double longitude) {
        throw new UnsupportedOperationException("Open-Meteo does not support reverse geocoding");
    }
}
