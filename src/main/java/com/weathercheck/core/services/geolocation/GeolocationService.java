package com.weathercheck.core.services.geolocation;

import java.util.Optional;

public interface GeolocationService {
    Optional<GeoCoordinates> locate();
}
