package com.weathercheck.core.controllers;

import java.util.function.Supplier;

public abstract class ControllerBase {

    public abstract void init();

    public static <T extends ControllerBase> T create(Supplier<T> factory) {
        T controller = factory.get();
        controller.init();
        return controller;
    }
}
