package com.weathercheck.core.services;

import java.util.function.Supplier;

public abstract class ServiceBase {

    public static <T extends ServiceBase> T create(Supplier<T> factory) {
        return factory.get();
    }
}
