package com.weathercheck.features.weather.controllers;

import com.weathercheck.core.controllers.Controller;
import com.weathercheck.core.i18n.I18nManager;
import com.weathercheck.core.units.UnitSystem;
import com.weathercheck.features.map.models.MapSelection;
import com.weathercheck.features.weather.models.CurrentWeather;
import com.weathercheck.features.weather.services.WeatherService;
import com.weathercheck.features.weather.views.WeatherView;

import javax.swing.*;
import java.time.ZoneId;

public class WeatherController implements Controller {
    private final WeatherView view;
    private final WeatherService weatherService;
    private final I18nManager i18n;
    private final UnitSystem unitSystem;
    private MapSelection mapSelection;

    public WeatherController(WeatherView view, WeatherService weatherService, I18nManager i18n, UnitSystem unitSystem) {
        this.view = view;
        this.weatherService = weatherService;
        this.i18n = i18n;
        this.unitSystem = unitSystem;
    }

    @Override
    public void init() {
        view.mapPanel().onMapClick(pos -> {
            String label = String.format("%.4f, %.4f", pos.getLatitude(), pos.getLongitude());
            mapSelection = new MapSelection(pos.getLatitude(), pos.getLongitude(), label);
            view.locationLabel().setText(label);
        });

        view.downloadButton().addActionListener(e -> {
            MapSelection selection = mapSelection;
            if (selection == null) {
                JOptionPane.showMessageDialog(view, i18n.tr("home.select_location"));
                return;
            }
            SwingUtilities.invokeLater(() -> {
                try {
                    CurrentWeather weather = weatherService.loadCurrent(
                            selection.latitude(),
                            selection.longitude(),
                            ZoneId.systemDefault(),
                            unitSystem
                    );
                    view.weatherLabel().setText(weatherText(weather));
                } catch (Exception ex) {
                    view.weatherLabel().setText(i18n.tr("home.error"));
                }
            });
        });
    }

    private String weatherText(CurrentWeather weather) {
        String symbol = unitSystem == UnitSystem.IMPERIAL ? "°F" : "°C";
        return i18n.tr(weather.weatherKey()) + " - " + weather.temperature() + symbol;
    }
}
