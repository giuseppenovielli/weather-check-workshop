package com.weathercheck.features.weather.services;

import com.weathercheck.features.weather.models.CurrentWeather;
import com.weathercheck.shared.units.UnitSystem;

import java.time.ZoneId;

public interface WeatherService {
    CurrentWeather loadCurrent(double latitude, double longitude, ZoneId zoneId, UnitSystem unitSystem);
}
