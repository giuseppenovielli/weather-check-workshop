package com.weathercheck.features.settings.controllers;

import com.weathercheck.core.config.AppSettings;
import com.weathercheck.core.controllers.ControllerBase;
import com.weathercheck.core.i18n.I18nManager;
import com.weathercheck.core.theme.ThemeManager;
import com.weathercheck.features.settings.services.SettingsService;
import com.weathercheck.features.settings.views.SettingsView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SettingsController extends ControllerBase {
    @FunctionalInterface
    public interface SaveListener {
        void onSettingsSaved(AppSettings settings);
    }

    private final SettingsView view;
    private final SettingsService service;
    private final ThemeManager themeManager;
    private final I18nManager i18nManager;
    private final List<SaveListener> saveListeners = new ArrayList<>();
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
        view.language().setSelectedItem(s.language());
        view.theme().setSelectedItem(s.theme());
        return s;
    }

    public void bindSave() {
        view.save().addActionListener(e -> {
            try {
                AppSettings s = new AppSettings(
                        String.valueOf(view.language().getSelectedItem()),
                        String.valueOf(view.theme().getSelectedItem())
                );
                service.save(s);
                loadedSettings = s;
                i18nManager.setLocale(Locale.forLanguageTag(s.language()));
                view.applyTranslations();
                themeManager.applyTheme(s.theme());
                SwingUtilities.updateComponentTreeUI(SwingUtilities.getWindowAncestor(view));
                fireSaveListeners(s);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, i18nManager.tr("settings.invalid"));
            }
        });
    }

    public void addSaveListener(SaveListener listener) {
        saveListeners.add(listener);
    }

    public void removeSaveListener(SaveListener listener) {
        saveListeners.remove(listener);
    }

    private void fireSaveListeners(AppSettings settings) {
        for (SaveListener listener : saveListeners) {
            listener.onSettingsSaved(settings);
        }
    }

    @Override
    public void init() {
        loadIntoView();
        bindSave();
    }
}
