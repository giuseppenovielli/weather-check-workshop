package com.weathercheck.features.home.controllers;

import com.weathercheck.core.i18n.I18nManager;
import com.weathercheck.core.units.UnitSystem;
import com.weathercheck.features.home.services.HomeService;
import com.weathercheck.features.home.views.HomeView;
import com.weathercheck.features.map.models.MapSelection;
import com.weathercheck.features.map.services.MapSelectionService;
import com.weathercheck.features.weather.models.CurrentWeather;
import com.weathercheck.features.weather.services.weather.WeatherService;

import javax.swing.*;
import java.time.ZoneId;

public class HomeController {
    private final HomeView view;
    private final WeatherService weatherService;
    private final MapSelectionService mapSelectionService;
    private final HomeService homeService;
    private final I18nManager i18n;

    public HomeController(HomeView view, WeatherService weatherService, MapSelectionService mapSelectionService, HomeService homeService, I18nManager i18n) {
        this.view = view;
        this.weatherService = weatherService;
        this.mapSelectionService = mapSelectionService;
        this.homeService = homeService;
        this.i18n = i18n;
    }

    public void init(UnitSystem unitSystem) {
        view.mapPanel().onMapClick(pos -> {
            String label = String.format("%.4f, %.4f", pos.getLatitude(), pos.getLongitude());
            mapSelectionService.set(new MapSelection(pos.getLatitude(), pos.getLongitude(), label));
            view.locationLabel().setText(label);
        });

        view.downloadButton().addActionListener(e -> {
            MapSelection selection = mapSelectionService.current();
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
                    view.weatherLabel().setText(homeService.weatherText(weather, unitSystem, i18n));
                } catch (Exception ex) {
                    view.weatherLabel().setText(i18n.tr("home.error"));
                }
            });
        });
    }
}
