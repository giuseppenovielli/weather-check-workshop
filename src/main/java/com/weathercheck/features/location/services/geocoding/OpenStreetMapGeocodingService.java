package com.weathercheck.features.location.services.geocoding;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weathercheck.shared.http.HttpJsonClient;
import com.weathercheck.features.location.services.geolocation.GeoCoordinates;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OpenStreetMapGeocodingService implements GeocodingService {
    private final HttpJsonClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenStreetMapGeocodingService(HttpJsonClient client) {
        this.client = client;
    }

    @Override
    public Optional<GeoCoordinates> getLocation(String query) {
        if (query == null || query.isBlank()) {
            return Optional.empty();
        }

        String encodedQuery = URLEncoder.encode(query.trim(), StandardCharsets.UTF_8);
        String url = String.format(
                "https://nominatim.openstreetmap.org/search?q=%s&format=jsonv2&limit=1&accept-language=it",
                encodedQuery
        );

        try {
            String json = client.get(url);
            JsonNode firstResult = objectMapper.readTree(json).path(0);
            if (firstResult.isMissingNode()) {
                return Optional.empty();
            }

            double latitude = Double.parseDouble(firstResult.path("lat").asText("NaN"));
            double longitude = Double.parseDouble(firstResult.path("lon").asText("NaN"));
            if (Double.isNaN(latitude) || Double.isNaN(longitude)) {
                return Optional.empty();
            }

            return Optional.of(new GeoCoordinates(latitude, longitude));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getPlacemarks(double latitude, double longitude) {
        String url = String.format(
                "https://nominatim.openstreetmap.org/reverse?lat=%s&lon=%s&format=jsonv2&accept-language=it",
                latitude,
                longitude
        );

        try {
            String json = client.get(url);
            JsonNode root = objectMapper.readTree(json);

            List<String> parts = new ArrayList<>();
            addIfPresent(parts, root.path("name").asText(null));
            addIfPresent(parts, root.path("address").path("city").asText(null));
            addIfPresent(parts, root.path("address").path("state").asText(null));
            addIfPresent(parts, root.path("address").path("country").asText(null));

            if (parts.isEmpty()) {
                String displayName = root.path("display_name").asText(null);
                if (displayName == null || displayName.isBlank()) {
                    return Optional.empty();
                }
                return Optional.of(displayName);
            }
            return Optional.of(String.join(", ", parts));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    private void addIfPresent(List<String> parts, String value) {
        if (value == null || value.isBlank()) {
            return;
        }
        if (!parts.contains(value)) {
            parts.add(value);
        }
    }
}
