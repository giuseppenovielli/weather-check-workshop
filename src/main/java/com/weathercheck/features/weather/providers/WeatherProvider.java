package com.weathercheck.features.weather.providers;

import com.weathercheck.core.units.UnitSystem;
import com.weathercheck.features.weather.models.CurrentWeather;

import java.time.ZoneId;

public interface WeatherProvider {
    CurrentWeather getCurrentWeather(double latitude, double longitude, ZoneId zoneId, UnitSystem unitSystem);
}
