package com.weathercheck.core.theme;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class ThemeManager {
    public void applyTheme(String theme) {
        try {
            if ("dark".equalsIgnoreCase(theme)) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } else {
                UIManager.setLookAndFeel(new FlatLightLaf());
            }
        } catch (UnsupportedLookAndFeelException ex) {
            throw new IllegalStateException("Unable to set theme", ex);
        }
    }
}
