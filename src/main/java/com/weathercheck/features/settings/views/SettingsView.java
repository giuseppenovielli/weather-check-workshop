package com.weathercheck.features.settings.views;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class SettingsView extends JPanel {
    private final JComboBox<String> language = new JComboBox<>(new String[]{"it-IT", "en-US", "fr-FR", "de-DE", "es-ES", "ja-JP"});
    private final JComboBox<String> theme = new JComboBox<>(new String[]{"light", "dark"});
    private final JTextField location = new JTextField(24);
    private final JTextField latitude = new JTextField(8);
    private final JTextField longitude = new JTextField(8);
    private final JButton save = new JButton("Save");

    public SettingsView() {
        setLayout(new MigLayout("insets 16, fillx", "[140!][grow]", "[][][][][]20[]"));
        add(new JLabel("Language")); add(language, "growx, wrap");
        add(new JLabel("Theme")); add(theme, "growx, wrap");
        add(new JLabel("Location")); add(location, "growx, wrap");
        add(new JLabel("Latitude")); add(latitude, "growx, split 2"); add(new JLabel("Longitude"), "skip 1, split 2");
        add(longitude, "growx, wrap");
        add(save, "span, right");
    }

    public JComboBox<String> language() { return language; }
    public JComboBox<String> theme() { return theme; }
    public JTextField locationField() { return location; }
    public JTextField latitude() { return latitude; }
    public JTextField longitude() { return longitude; }
    public JButton save() { return save; }
}
