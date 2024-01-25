/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.views.web.handlers.pages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.models.User;
import main.utils.Helper;
import main.views.web.Redirect;
import main.views.web.handlers.pages.widgets.Footer;
import main.views.web.handlers.pages.widgets.Header;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class LogoutPage implements HttpHandler {
    private final User user;
    public LogoutPage(User user){
        this.user = user;
    }
    public void handle(HttpExchange app) {
        try {
            this.user.setUsername(null);
            this.user.setPassword(null);
            this.user.setId(0);
            this.user.setCreatedOn(null);
            new Redirect(app,"/login");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
