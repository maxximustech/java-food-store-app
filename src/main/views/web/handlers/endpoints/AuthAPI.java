/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.views.web.handlers.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.controllers.UserDAO;
import main.models.User;
import main.views.web.APIResponse;
import main.utils.Helper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class AuthAPI implements HttpHandler {
    private User user;
    public AuthAPI(User user){
        this.user = user;
    }
    public void handle(HttpExchange app) throws IOException {
        BufferedWriter w = new BufferedWriter(new OutputStreamWriter(app.getResponseBody()));
        Map<String, String> request = new ObjectMapper().readValue(new Helper().formatBodyRequest(app), Map.class);
        if(app.getRequestURI().getPath().equals("/api/login")){
            if (app.getRequestMethod().equals("POST")) {
                User user = new UserDAO().loginAsUser(request.get("username"), request.get("password"));
                if (user == null) {
                    new APIResponse(app, w, 403, "Login is invalid").send();
                    return;
                }
                this.user.setUsername(user.getUsername());
                this.user.setPassword(user.getPassword());
                this.user.setId(user.getId());
                this.user.setCreatedOn(user.getCreatedOn());
                new APIResponse(app, w, 200, "Login is valid").send();
                return;
            }
        }else if(app.getRequestURI().getPath().equals("/api/logout")){
            this.user.setUsername(null);
            this.user.setPassword(null);
            this.user.setId(0);
            this.user.setCreatedOn(null);
            new APIResponse(app, w, 200, "Logged out successfully").send();
            return;
        }
        new APIResponse(app, w, 400, "Bad Request",null).send();
    }
}
