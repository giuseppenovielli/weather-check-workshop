package com.weathercheck.features.home.services;

import com.weathercheck.core.i18n.I18nManager;
import com.weathercheck.core.services.ServiceBase;
import com.weathercheck.core.units.UnitSystem;
import com.weathercheck.features.weather.models.CurrentWeather;

public class DefaultHomeService extends ServiceBase implements HomeService {
    @Override
    public String weatherText(CurrentWeather weather, UnitSystem unitSystem, I18nManager i18n) {
        String symbol = unitSystem == UnitSystem.IMPERIAL ? "°F" : "°C";
        return i18n.tr(weather.weatherKey()) + " - " + weather.temperature() + symbol;
    }
}
