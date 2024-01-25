/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.views.web.handlers.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.controllers.CustomerDAO;
import main.controllers.OrderDAO;
import main.models.Customer;
import main.models.OrderItem;
import main.models.User;
import main.views.web.APIResponse;
import main.utils.Helper;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderAPI implements HttpHandler {
    private final User user;
    public OrderAPI(User user){
        this.user = user;
    }
    public void handle(HttpExchange app) {
        BufferedWriter w = new BufferedWriter(new OutputStreamWriter(app.getResponseBody()));
        try {
            if(this.user.getId() <= 0 ){
                new APIResponse(app, w, 401, "Please login to continue").send();
                return;
            }

            Map<String, Object> request = new HashMap<>();
            try{
                request = new ObjectMapper().readValue(new Helper().formatBodyRequest(app), Map.class);
            }catch(Exception e){

            }
            HashMap<String, String> query = new Helper().formatQuery(app);
            if(app.getRequestURI().getPath().equals("/api/orders")){
                switch(app.getRequestMethod()){
                    case "GET" -> {
                        HashMap<String, Object> o = new HashMap<>();
                        o.put("orders", new OrderDAO().findAllOrders());
                        new APIResponse(app, w, 200, "Orders fetched successfully!",o).send();
                        return;
                    }
                    case "PUT" -> {
                        if(new OrderDAO().findCustomerBasketItems((int) request.get("id")).size() <= 0){
                            new APIResponse(app, w, 404, "No items available in customer basket").send();
                            return;
                        }
                        boolean created = new OrderDAO().addOrder((int) request.get("id"));
                        new APIResponse(app, w, created ? 201 : 400, created ? "Order created successfully!":"Error creating order!").send();
                        return;
                    }
                    case "DELETE" -> {
                        boolean deleted = new OrderDAO().deleteOrder((String) request.get("orderNo"));
                        new APIResponse(app, w, deleted ? 200 : 400, deleted ? "Order deleted successfully!":"Error deleting order!").send();
                        return;
                    }
                }
            }
            if(app.getRequestURI().getPath().equals("/api/orders/customer") && "GET".equals(app.getRequestMethod())) {
                    HashMap<String, Object> o = new HashMap<>();
                    o.put("orders", new OrderDAO().findCustomerOrders((int) request.get("id")));
                    new APIResponse(app, w, 200, "Customer orders fetched successfully!", o).send();
                    return;
            }else if(app.getRequestURI().getPath().equals("/api/basket")){
                switch(app.getRequestMethod()){
                    case "GET" -> {
                        ArrayList<HashMap<String, Object>> customerBasket = new ArrayList<>();
                        for(Customer customer : new CustomerDAO().findAllCustomers()){
                            ArrayList<OrderItem> bc = new OrderDAO().findCustomerBasketItems(customer.getCustomerID());
                            if(bc.size() > 0){
                                HashMap<String, Object> ob = new HashMap<>();
                                ob.put("customer",customer);
                                ob.put("items",bc);
                                customerBasket.add(ob);
                            }
                        }
                        HashMap<String, Object> o = new HashMap<>();
                        o.put("basket", customerBasket);
                        new APIResponse(app, w, 200, "Customer basket fetched successfully!",o).send();
                        return;
                    }
                    case "PUT" -> {
                        boolean created = new OrderDAO().addProductToCustomerBasket((int) request.get("productId"), (int) request.get("customerId"));
                        new APIResponse(app, w, created ? 201 : 400, created ? "Product added to customer basket successfully!":"Error adding product to customer basket!").send();
                        return;
                    }
                    case "DELETE" -> {
                        boolean deleted = new OrderDAO().removeProductFromCustomerBasket((int) request.get("productId"), (int) request.get("customerId"));
                        new APIResponse(app, w, deleted ? 200 : 400, deleted ? "Product removed from customer basket successfully!":"Error removing product from customer basket!").send();
                        return;
                    }
                }
            }
            new APIResponse(app, w, 400, "Bad Request").send();
        }catch(Exception e){
            new APIResponse(app,w,500,e.getMessage());
            e.printStackTrace();
        }
    }
}
