package com.weathercheck.features.settings.views;

import javax.swing.*;
import java.awt.*;

public class SettingsView extends JPanel {
    private final JComboBox<String> language = new JComboBox<>(new String[]{"it-IT", "en-US", "fr-FR", "de-DE", "es-ES", "ja-JP"});
    private final JComboBox<String> theme = new JComboBox<>(new String[]{"light", "dark"});
    private final JTextField location = new JTextField(24);
    private final JTextField latitude = new JTextField(8);
    private final JTextField longitude = new JTextField(8);
    private final JButton save = new JButton("Save");

    public SettingsView() {
        setLayout(new GridBagLayout());

        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.insets = new Insets(4, 16, 4, 8);
        labelConstraints.anchor = GridBagConstraints.WEST;
        labelConstraints.gridx = 0;

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.insets = new Insets(4, 0, 4, 16);
        fieldConstraints.anchor = GridBagConstraints.WEST;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.weightx = 1.0;
        fieldConstraints.gridx = 1;

        labelConstraints.gridy = 0;
        fieldConstraints.gridy = 0;
        add(new JLabel("Language"), labelConstraints);
        add(language, fieldConstraints);

        labelConstraints.gridy = 1;
        fieldConstraints.gridy = 1;
        add(new JLabel("Theme"), labelConstraints);
        add(theme, fieldConstraints);

        labelConstraints.gridy = 2;
        fieldConstraints.gridy = 2;
        add(new JLabel("Location"), labelConstraints);
        add(location, fieldConstraints);

        JPanel coordinatesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints coordLabel = new GridBagConstraints();
        coordLabel.insets = new Insets(0, 0, 0, 8);
        coordLabel.anchor = GridBagConstraints.WEST;

        GridBagConstraints coordField = new GridBagConstraints();
        coordField.insets = new Insets(0, 0, 0, 12);
        coordField.anchor = GridBagConstraints.WEST;
        coordField.fill = GridBagConstraints.HORIZONTAL;
        coordField.weightx = 1.0;

        coordLabel.gridx = 0;
        coordLabel.gridy = 0;
        coordinatesPanel.add(new JLabel("Latitude"), coordLabel);

        coordField.gridx = 1;
        coordField.gridy = 0;
        coordinatesPanel.add(latitude, coordField);

        coordLabel.gridx = 2;
        coordinatesPanel.add(new JLabel("Longitude"), coordLabel);

        coordField.gridx = 3;
        coordField.insets = new Insets(0, 0, 0, 0);
        coordinatesPanel.add(longitude, coordField);

        labelConstraints.gridy = 3;
        fieldConstraints.gridy = 3;
        add(new JLabel("Coordinates"), labelConstraints);
        add(coordinatesPanel, fieldConstraints);

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 4;
        buttonConstraints.gridwidth = 2;
        buttonConstraints.weightx = 1.0;
        buttonConstraints.insets = new Insets(20, 16, 16, 16);
        buttonConstraints.anchor = GridBagConstraints.EAST;
        add(save, buttonConstraints);
    }

    public JComboBox<String> language() { return language; }
    public JComboBox<String> theme() { return theme; }
    public JTextField locationField() { return location; }
    public JTextField latitude() { return latitude; }
    public JTextField longitude() { return longitude; }
    public JButton save() { return save; }
}
