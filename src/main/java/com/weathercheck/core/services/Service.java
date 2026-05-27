package com.weathercheck.core.services;

import java.util.function.Supplier;

public interface Service {

    static <T extends Service> T create(Supplier<T> factory) {
        return factory.get();
    }
}
