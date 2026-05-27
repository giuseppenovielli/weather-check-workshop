package com.weathercheck.core.http;

import java.io.IOException;

public interface HttpJsonClient {
    String get(String url) throws IOException, InterruptedException;
}
