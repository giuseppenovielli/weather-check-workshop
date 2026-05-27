package com.weathercheck.features.home.models;

import com.weathercheck.core.models.Model;

public record HomeState(String locationLabel, String weatherText) implements Model {
}
