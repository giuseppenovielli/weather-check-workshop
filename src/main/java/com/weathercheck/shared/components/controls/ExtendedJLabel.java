package com.weathercheck.shared.components.controls;

import com.weathercheck.shared.theme.LabelStyles;

import javax.swing.*;

public class ExtendedJLabel extends JLabel {
    private static final LabelStyles LABEL_STYLES = new LabelStyles();
    private LabelStyle labelStyle;

    public ExtendedJLabel() {}

    public ExtendedJLabel(LabelStyle labelStyle) {
        setLabelStyle(labelStyle);
    }

    public ExtendedJLabel(LabelStyle labelStyle, String text) {
        super(text);
        setLabelStyle(labelStyle);
    }

    public ExtendedJLabel(LabelStyle labelStyle, String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        setLabelStyle(labelStyle);
    }

    public void setLabelStyle(LabelStyle labelStyle) {
        this.labelStyle = labelStyle;
        applyCurrentStyle();
    }

    public LabelStyle getLabelStyle() {
        return labelStyle;
    }

    protected final void applyCurrentStyle() {
        if (labelStyle == null) {
            return;
        }
        labelStyle.apply(LABEL_STYLES, this);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        applyCurrentStyle();
    }

    @FunctionalInterface
    public interface LabelStyle {
        void apply(LabelStyles labelStyles, JLabel label);
    }

    public static final class Styles {
        public static final LabelStyle TITLE_LARGE = (labelStyles, label) -> labelStyles.applyTitleLargeTextStyle(label);
        public static final LabelStyle TITLE_MEDIUM = (labelStyles, label) -> labelStyles.applyTitleMediumTextStyle(label);
        public static final LabelStyle TITLE_DEFAULT = (labelStyles, label) -> labelStyles.applyTitleDefaultTextStyle(label);
        public static final LabelStyle TITLE = (labelStyles, label) -> labelStyles.applyTitleText(label);
        public static final LabelStyle BODY_LARGE = (labelStyles, label) -> labelStyles.applyBodyLargeTextStyle(label);
        public static final LabelStyle BODY_MEDIUM = (labelStyles, label) -> labelStyles.applyBodyMediumTextStyle(label);
        public static final LabelStyle BODY_DEFAULT = (labelStyles, label) -> labelStyles.applyBodyTextStyle(label);
        public static final LabelStyle BODY_SMALL = (labelStyles, label) -> labelStyles.applyBodySmallTextStyle(label);
        public static final LabelStyle BODY_MICRO = (labelStyles, label) -> labelStyles.applyBodyMicroTextStyle(label);
        public static final LabelStyle BODY_LARGE_INVERSE = (labelStyles, label) -> labelStyles.applyBodyLargeInverseTextStyle(label);
        public static final LabelStyle BODY_MEDIUM_INVERSE = (labelStyles, label) -> labelStyles.applyBodyMediumInverseTextStyle(label);
        public static final LabelStyle BODY_INVERSE = (labelStyles, label) -> labelStyles.applyBodyInverseTextStyle(label);
        public static final LabelStyle BODY_SMALL_INVERSE = (labelStyles, label) -> labelStyles.applyBodySmallInverseTextStyle(label);
        public static final LabelStyle BODY_MICRO_INVERSE = (labelStyles, label) -> labelStyles.applyBodyMicroInverseTextStyle(label);

        private Styles() {}
    }
}
