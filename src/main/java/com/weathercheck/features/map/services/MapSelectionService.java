package com.weathercheck.features.map.services;

import com.weathercheck.core.services.Service;
import com.weathercheck.features.map.models.MapSelection;

public interface MapSelectionService extends Service {
    MapSelection current();
    void set(MapSelection selection);
}
