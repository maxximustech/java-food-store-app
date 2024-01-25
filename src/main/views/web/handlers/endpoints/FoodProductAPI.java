/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.views.web.handlers.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.controllers.FoodProductDAO;
import main.models.FoodProduct;
import main.models.User;
import main.views.web.APIResponse;
import main.utils.Helper;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FoodProductAPI implements HttpHandler {
    private final User user;
    public FoodProductAPI(User user){
        this.user = user;
    }
    public void handle(HttpExchange app) {
        BufferedWriter w = new BufferedWriter(new OutputStreamWriter(app.getResponseBody()));
        try{
            Map<String, Object> request = new HashMap<>();
            try{
                request = new ObjectMapper().readValue(new Helper().formatBodyRequest(app), Map.class);
            }catch(Exception e){

            }

            HashMap<String, String> query = new Helper().formatQuery(app);
            if(this.user.getId() <= 0 ){
                new APIResponse(app, w, 401, "Please login to continue").send();
                return;
            }
            switch(app.getRequestMethod()){
                case "GET" -> {
                    HashMap<String, Object> p = new HashMap<>();
                    p.put("products", new FoodProductDAO().findAllProducts(query.get("expired") != null,query.get("search")));
                    new APIResponse(app, w, 200, "Products fetched successfully!",p).send();
                }
                case "PUT" -> {
                    boolean created = new FoodProductDAO().addProduct(new FoodProduct((String) request.get("sku"),(String) request.get("description"),
                            (String) request.get("category"),(int) request.get("price"),(int) request.get("stock"),
                            Date.valueOf((String) request.get("expiresOn"))
                    ));
                    new APIResponse(app, w, created ? 201 : 400, created ? "Product created successfully!":"Error creating product!").send();
                }
                case "POST" -> {
                    boolean updated = new FoodProductDAO().updateProduct(new FoodProduct((int) request.get("id"),(String) request.get("sku"),(String) request.get("description"),
                            (String) request.get("category"),(int) request.get("price"),(int) request.get("stock"),
                            Date.valueOf((String) request.get("expiresOn"))
                    ));
                    new APIResponse(app, w, updated ? 200 : 400, updated ? "Product updated successfully!":"Error updating product!").send();
                }
                case "DELETE" -> {
                    boolean deleted = new FoodProductDAO().deleteProduct((int) request.get("id"));
                    new APIResponse(app, w, deleted ? 200 : 400, deleted ? "Product deleted successfully!":"Error deleting product!").send();
                }
                default -> {
                    new APIResponse(app, w, 400, "Bad Request").send();
                }
            }
        }catch(Exception e){
            new APIResponse(app,w,500,e.getMessage());
            e.printStackTrace();
        }
    }
}
