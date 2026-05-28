package com.weathercheck.shared.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18nManager {
    private Locale locale;

    public I18nManager(Locale locale) {
        this.locale = locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public String tr(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", locale);
        if (bundle.containsKey(key)) {
            return bundle.getString(key);
        }
        ResourceBundle fallback = ResourceBundle.getBundle("i18n.messages", Locale.ENGLISH);
        return fallback.containsKey(key) ? fallback.getString(key) : key;
    }
}
