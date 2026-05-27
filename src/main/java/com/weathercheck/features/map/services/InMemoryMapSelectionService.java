package com.weathercheck.features.map.services;

import com.weathercheck.core.services.Service;
import com.weathercheck.features.map.models.MapSelection;

public class InMemoryMapSelectionService implements Service, MapSelectionService {
    private MapSelection current;

    @Override
    public MapSelection current() {
        return current;
    }

    @Override
    public void set(MapSelection selection) {
        this.current = selection;
    }
}
