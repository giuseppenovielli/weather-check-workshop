package com.weathercheck.features.map.models;

import com.weathercheck.shared.components.models.Model;

public record MapSelection(double latitude, double longitude, String label) implements Model {
}
