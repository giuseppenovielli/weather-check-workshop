package com.weathercheck.features.weather.services.weather;

import com.weathercheck.core.services.Service;
import com.weathercheck.core.units.UnitSystem;
import com.weathercheck.features.weather.models.CurrentWeather;

import java.time.ZoneId;

public class ProviderBackedWeatherService implements Service, WeatherService {
    private final WeatherProvider provider;

    public ProviderBackedWeatherService(WeatherProvider provider) {
        this.provider = provider;
    }

    @Override
    public CurrentWeather loadCurrent(double latitude, double longitude, ZoneId zoneId, UnitSystem unitSystem) {
        return provider.getCurrentWeather(latitude, longitude, zoneId, unitSystem);
    }
}
