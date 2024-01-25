/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.views.console;

import main.controllers.CustomerDAO;
import main.controllers.FoodProductDAO;
import main.controllers.OrderDAO;
import main.models.Customer;
import main.models.FoodProduct;
import main.models.Order;
import main.models.OrderItem;

import java.util.Scanner;

/**
 * The Order Display class.
 * @author Azeez Boluwatife Abdullahi - 23713593
 */
public class OrderDisplay {
    /**
     * Creates a new Order Console Display instance.
     * This instance starts an order console menu application.
     */
    public OrderDisplay(){
        Scanner scanner = new Scanner(System.in);
        String selection;
        do {
            System.out.println("--------------------");
            System.out.println("Order Access Menu");
            System.out.println("Choose from these options");
            System.out.println("--------------------");
            System.out.println("[1] List all orders");
            System.out.println("[2] List all customer orders");
            System.out.println("[3] Search for an order by order number");
            System.out.println("[4] List customer basket");
            System.out.println("[5] Add a product to customer basket");
            System.out.println("[6] Remove product from customer basket");
            System.out.println("[7] Create new order for customer");
            System.out.println("[8] Delete an order by order number");
            System.out.println("[0] Go Back");
            System.out.println();

            selection = scanner.nextLine();
            switch (selection) {
                case "1" -> {
                    for(Order order : new OrderDAO().findAllOrders()){
                        System.out.println(order);
                    }
                    System.out.println();
                }
                case "2" -> {
                    System.out.println("\nEnter Customer ID");
                    for(Order order : new OrderDAO().findCustomerOrders(new Scanner(System.in).nextInt())){
                        System.out.println(order);
                    }
                    System.out.println();
                }
                case "3" -> {
                    System.out.println("\nEnter Order Number");
                    Order order = new OrderDAO().findOrder(new Scanner(System.in).nextLine());
                    if(order == null){
                        System.out.println("\nOrder with this order number does not exist");
                        break;
                    }
                    System.out.println(order);
                    System.out.println();
                }
                case "4" -> {
                    System.out.println("\nEnter Customer ID");
                    for(OrderItem item : new OrderDAO().findCustomerBasketItems(new Scanner(System.in).nextInt())){
                        System.out.println(item);
                    }
                    System.out.println();
                }
                case "5" -> {
                    System.out.println("\nEnter Product ID");
                    FoodProduct product = new FoodProductDAO().findProduct(new Scanner(System.in).nextInt());
                    if(product == null){
                        System.out.println("\nProduct with this ID does not exist");
                        break;
                    }
                    System.out.println("\nEnter Customer ID");
                    Customer customer = new CustomerDAO().findCustomer(new Scanner(System.in).nextInt());
                    if(customer == null){
                        System.out.println("\nCustomer with this ID does not exist");
                        break;
                    }
                    System.out.println(new OrderDAO().addProductToCustomerBasket(product.getID(), customer.getCustomerID()));
                    System.out.println();
                }
                case "6" -> {
                    System.out.println("\nEnter Product ID");
                    FoodProduct product = new FoodProductDAO().findProduct(new Scanner(System.in).nextInt());
                    if(product == null){
                        System.out.println("\nProduct with this ID does not exist");
                        break;
                    }
                    System.out.println("\nEnter Customer ID");
                    Customer customer = new CustomerDAO().findCustomer(new Scanner(System.in).nextInt());
                    if(customer == null){
                        System.out.println("\nCustomer with this ID does not exist");
                        break;
                    }
                    System.out.println(new OrderDAO().removeProductFromCustomerBasket(product.getID(), customer.getCustomerID()));
                    System.out.println();
                }
                case "7" -> {
                    System.out.println("\nEnter Customer ID");
                    Customer customer = new CustomerDAO().findCustomer(new Scanner(System.in).nextInt());
                    if(customer == null){
                        System.out.println("\nCustomer with this ID does not exist");
                        break;
                    }
                    String err = "";
                    for(OrderItem item : new OrderDAO().findCustomerBasketItems(customer.getCustomerID())){
                        if(!new FoodProductDAO().isStockEnough(item.getProduct().getID(),item.getQuantity())){
                            err = "Product with ID : "+item.getProduct().getID()+" does not have enough stock to add to customer order. Please remove the product and add the available stock correctly.";
                            break;
                        }
                    }
                    if(!err.trim().isEmpty()){
                        System.out.println(err);
                        break;
                    }
                    System.out.println(new OrderDAO().addOrder(customer.getCustomerID()));
                    System.out.println();
                }
                case "8" -> {
                    System.out.println("\nEnter Order Number");
                    Order order = new OrderDAO().findOrder(new Scanner(System.in).nextLine());
                    if(order == null){
                        System.out.println("\nOrder with this order number does not exist");
                        break;
                    }
                    System.out.println(new OrderDAO().deleteOrder(order.getOrderNo()));
                    System.out.println();
                }
            }
        }while (!selection.equals("0"));
    }
}
