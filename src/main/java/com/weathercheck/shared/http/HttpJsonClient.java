package com.weathercheck.shared.http;

import java.io.IOException;

public interface HttpJsonClient {
    String get(String url) throws IOException, InterruptedException;
}
