package com.weathercheck.core.controls;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class InsetsJPanelBase extends JPanel {
    public static final int DEFAULT_MARGIN = 10;
    private boolean initialized;

    public InsetsJPanelBase() {
        this(DEFAULT_MARGIN);
    }

    public InsetsJPanelBase(int margin) {
        this(new Insets(margin, margin, margin, margin));
    }

    public InsetsJPanelBase(Insets insets) {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(insets));
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
