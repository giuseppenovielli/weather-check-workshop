package com.weathercheck.features.weather.providers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
            JsonObject current = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("current");
            int code = current.get("weather_code").getAsInt();
            return new CurrentWeather(
                    current.get("time").getAsString(),
                    current.get("temperature_2m").getAsDouble(),
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
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            JsonArray results = root.has("results") ? root.getAsJsonArray("results") : new JsonArray();
            List<GeoLocation> locations = new ArrayList<>();
            for (int i = 0; i < results.size(); i++) {
                JsonObject item = results.get(i).getAsJsonObject();
                locations.add(new GeoLocation(item.get("name").getAsString(), item.get("latitude").getAsDouble(), item.get("longitude").getAsDouble()));
            }
            return locations;
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to search location", ex);
        }
    }
}
