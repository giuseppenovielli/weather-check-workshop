package com.weathercheck.features.weather.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weathercheck.core.http.HttpJsonClient;
import com.weathercheck.core.units.UnitSystem;
import com.weathercheck.features.weather.mappers.WeatherCodeCatalog;
import com.weathercheck.features.weather.models.CurrentWeather;

import java.time.ZoneId;

public class OpenMeteoWeatherProvider implements WeatherService {
    private final HttpJsonClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenMeteoWeatherProvider(HttpJsonClient client) {
        this.client = client;
    }

    @Override
    public CurrentWeather loadCurrent(double latitude, double longitude, ZoneId zoneId, UnitSystem unitSystem) {
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
            OpenMeteoResponse response = objectMapper.readValue(json, OpenMeteoResponse.class);
            if (response == null || response.current() == null) {
                throw new IllegalStateException("Missing current weather payload");
            }
            CurrentPayload current = response.current();
            int code = current.weatherCode();
            return new CurrentWeather(
                    current.time(),
                    current.temperature2m(),
                    code,
                    WeatherCodeCatalog.keyFor(code)
            );
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to fetch weather", ex);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record OpenMeteoResponse(
            @JsonProperty("current") CurrentPayload current
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record CurrentPayload(
            @JsonProperty("time") String time,
            @JsonProperty("temperature_2m") double temperature2m,
            @JsonProperty("weather_code") int weatherCode
    ) {
    }
}
