/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.views.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import main.models.User;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class APIResponse {
    private HttpExchange app;
    private BufferedWriter writer;
    private int statusCode;
    private String message;
    private HashMap<String, Object> data;

    public APIResponse(HttpExchange app, BufferedWriter writer, int statusCode, String message) {
        this.app = app;
        this.writer = writer;
        this.statusCode = statusCode;
        this.message = message;
    }

    public APIResponse(HttpExchange app, BufferedWriter writer, int statusCode, String message, HashMap<String, Object> data) {
        this.app = app;
        this.writer = writer;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void send() throws IOException {
        app.sendResponseHeaders(statusCode,0);
        writer.write(new ObjectMapper().writeValueAsString(this));
        writer.close();
    }

    @Override
    public String toString() {
        return "APIResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
