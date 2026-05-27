package com.weathercheck.features.map.services;

import com.weathercheck.features.map.models.MapSelection;

public class MapSelectionService {
    private MapSelection current;

    public MapSelection current() {
        return current;
    }

    public void set(MapSelection selection) {
        this.current = selection;
    }
}
