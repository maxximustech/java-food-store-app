/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.views.web.handlers.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.controllers.CustomerDAO;
import main.models.Address;
import main.models.Customer;
import main.models.User;
import main.views.web.APIResponse;
import main.utils.Helper;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerAPI  implements HttpHandler {
    private final User user;
    public CustomerAPI(User user){
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
            switch(app.getRequestMethod()){
                case "GET" -> {
                    HashMap<String, Object> c = new HashMap<>();
                    c.put("customers", new CustomerDAO().findAllCustomers());
                    new APIResponse(app, w, 200, "Customers fetched successfully!",c).send();
                }
                case "PUT" -> {
                    boolean created = new CustomerDAO().addCustomer(new Customer((String) request.get("customerName"),
                            new Address(((Map<String, String>) request.get("address")).get("addressLine1"), ((Map<String, String>) request.get("address")).get("addressLine2"), ((Map<String, String>) request.get("address")).get("addressLine3"),
                                    ((Map<String, String>) request.get("address")).get("country"), ((Map<String, String>) request.get("address")).get("postCode")
                            ),(String) request.get("telephoneNumber"))
                    );
                    new APIResponse(app, w, created ? 201 : 400, created ? "Customer created successfully!":"Error creating customer!").send();
                }
                case "POST" -> {
                    boolean updated = new CustomerDAO().updateCustomer(new Customer((int) request.get("customerID"), (String) request.get("customerName"),
                            new Address(((Map<String, String>) request.get("address")).get("addressLine1"), ((Map<String, String>) request.get("address")).get("addressLine2"), ((Map<String, String>) request.get("address")).get("addressLine3"),
                                    ((Map<String, String>) request.get("address")).get("country"), ((Map<String, String>) request.get("address")).get("postCode")
                            ),(String) request.get("telephoneNumber"))
                    );
                    new APIResponse(app, w, updated ? 201 : 400, updated ? "Customer updated successfully!":"Error updating customer!").send();
                }
                case "DELETE" -> {
                    boolean deleted = new CustomerDAO().deleteCustomer((int) request.get("id"));
                    new APIResponse(app, w, deleted ? 200 : 400, deleted ? "Customer deleted successfully!":"Error deleting customer!").send();
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
