package com.weathercheck.core.services;

import java.util.Optional;

public interface GeolocationService {
    Optional<GeoCoordinates> locate();
}
