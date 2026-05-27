package com.weathercheck.features.settings.controllers;

import com.weathercheck.core.config.AppSettings;
import com.weathercheck.core.i18n.I18nManager;
import com.weathercheck.core.theme.ThemeManager;
import com.weathercheck.features.settings.services.SettingsService;
import com.weathercheck.features.settings.views.SettingsView;

import javax.swing.*;
import java.util.Locale;

public class SettingsController {
    private final SettingsView view;
    private final SettingsService service;
    private final ThemeManager themeManager;
    private final I18nManager i18nManager;
    private AppSettings loadedSettings;

    public SettingsController(SettingsView view, SettingsService service, ThemeManager themeManager, I18nManager i18nManager) {
        this.view = view;
        this.service = service;
        this.themeManager = themeManager;
        this.i18nManager = i18nManager;
    }

    public AppSettings loadIntoView() {
        AppSettings s = service.load();
        loadedSettings = s;
        view.language().setSelectedItem(s.languageTag());
        view.theme().setSelectedItem(s.theme());
        return s;
    }

    public void bindSave(Runnable onSaved) {
        view.save().addActionListener(e -> {
            try {
                AppSettings currentSettings = loadedSettings != null ? loadedSettings : service.load();
                AppSettings s = new AppSettings(
                        String.valueOf(view.language().getSelectedItem()),
                        String.valueOf(view.theme().getSelectedItem()),
                        currentSettings.locationName(),
                        currentSettings.latitude(),
                        currentSettings.longitude()
                );
                service.save(s);
                loadedSettings = s;
                i18nManager.setLocale(Locale.forLanguageTag(s.languageTag()));
                themeManager.applyTheme(s.theme());
                SwingUtilities.updateComponentTreeUI(SwingUtilities.getWindowAncestor(view));
                onSaved.run();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Invalid settings");
            }
        });
    }
}
