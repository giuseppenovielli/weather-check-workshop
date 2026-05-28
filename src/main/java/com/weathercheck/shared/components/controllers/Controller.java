package com.weathercheck.shared.components.controllers;

import java.util.function.Supplier;

public interface Controller {

    void init();

    static <T extends Controller> T create(Supplier<T> factory) {
        T controller = factory.get();
        controller.init();
        return controller;
    }
}
