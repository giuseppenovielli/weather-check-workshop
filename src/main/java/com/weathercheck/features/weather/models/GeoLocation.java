package com.weathercheck.features.weather.models;

import com.weathercheck.core.models.Model;

public record GeoLocation(String name, double latitude, double longitude) implements Model {
}
