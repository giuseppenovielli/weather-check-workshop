package com.weathercheck.features.map.models;

import com.weathercheck.core.models.Model;

public record MapSelection(double latitude, double longitude, String label) implements Model {
}
