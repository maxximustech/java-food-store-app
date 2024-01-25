/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.views.web;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedWriter;
import java.io.IOException;

public class Redirect {
    private HttpExchange app;

    public Redirect(HttpExchange app, String location) throws IOException {
        this.app = app;

        app.getResponseHeaders().add("Location",location);
        app.sendResponseHeaders(302,0);
    }
}
