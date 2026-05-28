package com.weathercheck.shared.theme;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;

public class ThemeManager {
    private static final String CUSTOM_DEFAULTS_PACKAGE = "com.weathercheck.themes";

    public void applyTheme(String theme) {
        try {
            FlatLaf.setUseNativeWindowDecorations(true);
            FlatLaf.registerCustomDefaultsSource(CUSTOM_DEFAULTS_PACKAGE);
            UIManager.setLookAndFeel(resolveLookAndFeel(theme));
        } catch (UnsupportedLookAndFeelException ex) {
            throw new IllegalStateException("Unable to set theme", ex);
        }
    }

    private LookAndFeel resolveLookAndFeel(String theme) {
        boolean dark = "dark".equalsIgnoreCase(theme);
        String osName = System.getProperty("os.name", "").toLowerCase();
        boolean macOs = osName.contains("mac");
        boolean windows = osName.contains("win");
        boolean linux = osName.contains("nux") || osName.contains("linux");

        if (macOs) {
            return dark ? new FlatMacDarkLaf() : new FlatMacLightLaf();
        }
        if (windows || linux) {
            return dark ? new FlatDarculaLaf() : new FlatIntelliJLaf();
        }
        return dark ? new FlatDarkLaf() : new FlatLightLaf();
    }
}
