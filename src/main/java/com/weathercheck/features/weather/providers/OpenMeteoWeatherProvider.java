package com.weathercheck.features.weather.providers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weathercheck.core.http.HttpJsonClient;
import com.weathercheck.core.units.UnitSystem;
import com.weathercheck.features.weather.mappers.WeatherCodeCatalog;
import com.weathercheck.features.weather.models.CurrentWeather;
import com.weathercheck.features.weather.models.GeoLocation;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class OpenMeteoWeatherProvider implements WeatherProvider, GeocodingProvider {
    private final HttpJsonClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenMeteoWeatherProvider(HttpJsonClient client) {
        this.client = client;
    }

    @Override
    public CurrentWeather getCurrentWeather(double latitude, double longitude, ZoneId zoneId, UnitSystem unitSystem) {
        String tempUnit = unitSystem == UnitSystem.IMPERIAL ? "fahrenheit" : "celsius";
        String url = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current=temperature_2m,weather_code&temperature_unit=%s&timezone=%s",
                latitude,
                longitude,
                tempUnit,
                zoneId.getId()
        );
        try {
            String json = client.get(url);
            JsonNode current = objectMapper.readTree(json).path("current");
            int code = current.path("weather_code").asInt();
            return new CurrentWeather(
                    current.path("time").asText(),
                    current.path("temperature_2m").asDouble(),
                    code,
                    WeatherCodeCatalog.keyFor(code)
            );
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to fetch weather", ex);
        }
    }

    @Override
    public List<GeoLocation> search(String query) {
        String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = "https://geocoding-api.open-meteo.com/v1/search?name=" + encoded + "&count=5&language=en&format=json";
        try {
            String json = client.get(url);
            JsonNode results = objectMapper.readTree(json).path("results");
            List<GeoLocation> locations = new ArrayList<>();
            if (!results.isArray()) {
                return locations;
            }
            for (JsonNode item : results) {
                locations.add(new GeoLocation(item.path("name").asText(), item.path("latitude").asDouble(), item.path("longitude").asDouble()));
            }
            return locations;
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to search location", ex);
        }
    }
}
