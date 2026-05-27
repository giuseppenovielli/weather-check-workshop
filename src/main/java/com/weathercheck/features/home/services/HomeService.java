package com.weathercheck.features.home.services;

import com.weathercheck.core.i18n.I18nManager;
import com.weathercheck.core.units.UnitSystem;
import com.weathercheck.features.weather.models.CurrentWeather;

public interface HomeService {
    String weatherText(CurrentWeather weather, UnitSystem unitSystem, I18nManager i18n);
}
