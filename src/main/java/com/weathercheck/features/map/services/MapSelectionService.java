package com.weathercheck.features.map.services;

import com.weathercheck.features.map.models.MapSelection;

public interface MapSelectionService {
    MapSelection current();
    void set(MapSelection selection);
}
