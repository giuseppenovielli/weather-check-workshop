package com.weathercheck.features.weather.models;

import com.weathercheck.core.models.Model;

public record CurrentWeather(String time, double temperature, int weatherCode, String weatherKey) implements Model {
}
