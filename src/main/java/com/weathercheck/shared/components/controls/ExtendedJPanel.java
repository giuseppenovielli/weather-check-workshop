package com.weathercheck.shared.components.controls;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class ExtendedJPanel extends JPanel {
    private static final String DEFAULT_MARGIN_KEY = "wc.panel.margin.default";
    private static final int FALLBACK_DEFAULT_MARGIN = 10;
    private boolean initialized;

    public ExtendedJPanel() {
        this(readDefaultMargin());
    }

    public ExtendedJPanel(int margin) {
        this(new Insets(margin, margin, margin, margin));
    }

    public ExtendedJPanel(Insets insets) {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(insets));
    }

    private static int readDefaultMargin() {
        Object value = UIManager.get(DEFAULT_MARGIN_KEY);

        if (value instanceof Number number) {
            return number.intValue();
        }

        if (value instanceof String stringValue) {
            try {
                return Integer.parseInt(stringValue);
            } catch (NumberFormatException ignored) {
                return FALLBACK_DEFAULT_MARGIN;
            }
        }

        return FALLBACK_DEFAULT_MARGIN;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (!initialized) {
            initialized = true;
            add(buildView(), BorderLayout.CENTER);
        }
    }

    protected abstract JComponent buildView();
}
