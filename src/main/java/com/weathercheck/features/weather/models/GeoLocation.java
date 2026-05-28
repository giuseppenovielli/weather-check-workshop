package com.weathercheck.features.weather.models;

import com.weathercheck.shared.components.models.Model;

public record GeoLocation(String name, double latitude, double longitude) implements Model {
}
