package com.weathercheck.features.weather.models;

import com.weathercheck.shared.components.models.Model;

public record WeatherState(String locationLabel, String weatherText) implements Model {
}
