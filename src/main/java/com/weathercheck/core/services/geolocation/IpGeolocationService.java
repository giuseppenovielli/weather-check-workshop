package com.weathercheck.core.services.geolocation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weathercheck.core.http.HttpJsonClient;
import com.weathercheck.core.services.Service;

import java.util.Optional;

public class IpGeolocationService implements Service, GeolocationService {
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
            JsonNode root = objectMapper.readTree(json);
            if (!root.path("success").asBoolean(false)) {
                return Optional.empty();
            }

            double latitude = root.path("latitude").asDouble(Double.NaN);
            double longitude = root.path("longitude").asDouble(Double.NaN);
            if (Double.isNaN(latitude) || Double.isNaN(longitude)) {
                return Optional.empty();
            }

            return Optional.of(new GeoCoordinates(latitude, longitude));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
