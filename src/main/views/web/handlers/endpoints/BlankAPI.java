/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.views.web.handlers.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import main.models.User;
import main.views.web.APIResponse;
import main.utils.Helper;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class BlankAPI {
    private final User user;
    public BlankAPI(User user){
        this.user = user;
    }
    public void handle(HttpExchange app) {
        BufferedWriter w = new BufferedWriter(new OutputStreamWriter(app.getResponseBody()));
        try {
            if(this.user.getId() <= 0 ){
                new APIResponse(app, w, 401, "Please login to continue").send();
                return;
            }
            Map<String, String> request = new ObjectMapper().readValue(new Helper().formatBodyRequest(app), Map.class);
            HashMap<String, String> query = new Helper().formatQuery(app);

        }catch(Exception e){
            new APIResponse(app,w,500,e.getMessage());
            e.printStackTrace();
        }
    }
}
