/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.views.web;

import com.sun.net.httpserver.HttpServer;
import main.models.User;
import main.utils.Constants;
import main.views.web.handlers.endpoints.AuthAPI;
import main.views.web.handlers.endpoints.CustomerAPI;
import main.views.web.handlers.endpoints.FoodProductAPI;
import main.views.web.handlers.endpoints.OrderAPI;
import main.views.web.handlers.pages.*;
import main.views.web.handlers.pages.scripts.Alert;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    public void start() throws IOException {
        try{
            User user = new User(0,null,null,null);
            HttpServer server = HttpServer.create(new InetSocketAddress(Constants.serverPort), 0);
            server.setExecutor(null);

            //Pages
            server.createContext("/", new ProductPage(user));
            server.createContext("/login", new LoginPage(user));
            server.createContext("/customers", new CustomerPage(user));
            server.createContext("/basket", new BasketPage(user));
            server.createContext("/orders", new OrderPage(user));
            server.createContext("/logout", new LogoutPage(user));

            //API Endpoints
            server.createContext("/api/login", new AuthAPI(user));
            server.createContext("/api/logout", new AuthAPI(user));
            server.createContext("/api/products", new FoodProductAPI(user));
            server.createContext("/api/customers", new CustomerAPI(user));
            server.createContext("/api/basket", new OrderAPI(user));
            server.createContext("/api/orders", new OrderAPI(user));

            //Scripts
            server.createContext("/sweetalert.js", new Alert());
            server.start();
            System.out.println("Server running on port: "+Constants.serverPort);
            System.out.println("You will need to create a new user account using the console app, so as to gain access to the web application.");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
