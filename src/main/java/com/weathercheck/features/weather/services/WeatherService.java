package com.weathercheck.features.weather.services;

import com.weathercheck.core.units.UnitSystem;
import com.weathercheck.features.weather.models.CurrentWeather;

import java.time.ZoneId;

public interface WeatherService {
    CurrentWeather loadCurrent(double latitude, double longitude, ZoneId zoneId, UnitSystem unitSystem);
}
