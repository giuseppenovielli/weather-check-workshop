package com.weathercheck.shared.components.services;

import java.util.function.Supplier;

public interface Service {

    static <T extends Service> T create(Supplier<T> factory) {
        return factory.get();
    }
}
