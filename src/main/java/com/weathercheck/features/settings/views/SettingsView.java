package com.weathercheck.features.settings.views;

import javax.swing.*;
import java.awt.*;

public class SettingsView extends JPanel {
    private final JComboBox<String> language = new JComboBox<>(new String[]{"it-IT", "en-US", "fr-FR", "de-DE", "es-ES", "ja-JP"});
    private final JComboBox<String> theme = new JComboBox<>(new String[]{"light", "dark"});
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
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 2;
        buttonConstraints.gridwidth = 2;
        buttonConstraints.weightx = 1.0;
        buttonConstraints.insets = new Insets(20, 16, 16, 16);
        buttonConstraints.anchor = GridBagConstraints.EAST;
        add(save, buttonConstraints);
    }

    public JComboBox<String> language() { return language; }
    public JComboBox<String> theme() { return theme; }
    public JButton save() { return save; }
}
