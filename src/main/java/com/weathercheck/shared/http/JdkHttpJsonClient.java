package com.weathercheck.shared.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class JdkHttpJsonClient implements HttpJsonClient {
    @Override
    public String get(String url) throws IOException, InterruptedException {
        HttpURLConnection connection = (HttpURLConnection) URI.create(url).toURL().openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setConnectTimeout(10_000);
        connection.setReadTimeout(10_000);

        int statusCode = connection.getResponseCode();
        if (statusCode >= 400) {
            throw new IOException("HTTP error: " + statusCode);
        }

        try (InputStream inputStream = connection.getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } finally {
            connection.disconnect();
        }
    }
}
