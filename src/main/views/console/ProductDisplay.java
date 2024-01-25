/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.views.console;

import main.controllers.FoodProductDAO;
import main.models.FoodProduct;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Product Display class.
 * @author Azeez Boluwatife Abdullahi - 23713593
 */
public class ProductDisplay {
    /**
     * Creates a new Product Console Display instance.
     * This instance starts a product console menu application.
     */
    public ProductDisplay(){
        Scanner scanner = new Scanner(System.in);
        String selection;
        do {
            System.out.println("--------------------");
            System.out.println("Product Access Menu");
            System.out.println("Choose from these options");
            System.out.println("--------------------");
            System.out.println("[1] List all products");
            System.out.println("[2] List all expired products");
            System.out.println("[3] Search for product by ID");
            System.out.println("[4] Search for product by description or SKU");
            System.out.println("[5] Add a new product");
            System.out.println("[6] Update a product by ID");
            System.out.println("[7] Delete a product by ID");
            System.out.println("[0] Go Back");
            System.out.println();

            selection = scanner.nextLine();
            switch (selection) {
                case "1" -> {
                    ArrayList<FoodProduct> products = new FoodProductDAO().findAllProducts(false,null);
                    for (FoodProduct product : products) {
                        System.out.println(product);
                    }
                    System.out.println();
                }
                case "2" -> {
                    ArrayList<FoodProduct> products = new FoodProductDAO().findAllProducts(true,null);
                    for (FoodProduct product : products) {
                        System.out.println(product);
                    }
                    System.out.println();
                }
                case "3" -> {
                    System.out.println("\nSearch for a product by ID");
                    int id = new Scanner(System.in).nextInt();
                    FoodProduct product = new FoodProductDAO().findProduct(id);
                    System.out.println(product);
                    System.out.println();
                }
                case "4" -> {
                    System.out.println("\nSearch for a product by description or SKU");
                    String search = new Scanner(System.in).nextLine();
                    ArrayList<FoodProduct> products = new FoodProductDAO().findAllProducts(false,search);
                    for (FoodProduct product : products) {
                        System.out.println(product);
                    }
                    System.out.println();
                }
                case "5" -> {
                    System.out.println(new FoodProductDAO().addProduct(setUpFoodProductDataFromConsole(0)));
                    System.out.println();
                }
                case "6" -> {
                    System.out.println("\nEnter product ID to update");
                    int id = new Scanner(System.in).nextInt();
                    FoodProduct product = new FoodProductDAO().findProduct(id);
                    if(product == null){
                        System.out.println("\nProduct with this ID does not exist");
                    }else{
                        System.out.println(new FoodProductDAO().updateProduct(setUpFoodProductDataFromConsole(id)));
                    }
                    System.out.println();
                }
                case "7" -> {
                    System.out.println("\nEnter the product ID to delete");
                    int id = new Scanner(System.in).nextInt();
                    FoodProduct product = new FoodProductDAO().findProduct(id);
                    if(product == null){
                        System.out.println("\nProduct with this ID does not exist");
                    }else {
                        System.out.println(new FoodProductDAO().deleteProduct(id));
                    }
                    System.out.println();
                }
            }
        }while (!selection.equals("0"));
    }
    private FoodProduct setUpFoodProductDataFromConsole(int id){
        System.out.println("\nEnter product SKU");
        String sku = new Scanner(System.in).nextLine();
        System.out.println("\nEnter product description");
        String desc = new Scanner(System.in).nextLine();
        System.out.println("\nEnter product category");
        String category = new Scanner(System.in).nextLine();
        System.out.println("\nEnter product price");
        int price = new Scanner(System.in).nextInt();
        System.out.println("\nEnter product available stock");
        int stock = new Scanner(System.in).nextInt();
        Date expiresOn = null;
        while(expiresOn == null){
            try{
                System.out.println("\nEnter product expiry date. Please match the format (YYYY-MM-DD)");
                expiresOn = Date.valueOf(new Scanner(System.in).nextLine());
            }catch(Exception e){
                System.out.println("\nPlease match the format (YYYY-MM-DD)");
            }
        }
        if(id > 0){
            return new FoodProduct(id,sku,desc,category,price,stock,expiresOn);
        }else{
            return new FoodProduct(sku,desc,category,price,stock,expiresOn);
        }
    }
}
