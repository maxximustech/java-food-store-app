/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.views.console;

import java.util.Scanner;

/**
 * The Console Display class.
 * @author Azeez Boluwatife Abdullahi - 23713593
 */
public class ConsoleDisplay {
    /**
     * Creates a new Console Display instance.
     * This instance starts a console menu application.
     */
    public ConsoleDisplay(){
        Scanner scanner = new Scanner(System.in);
        String selection;
        do {
            System.out.println("--------------------");
            System.out.println("Console Application");
            System.out.println("Choose from these options");
            System.out.println("--------------------");
            System.out.println("[1] Access Products");
            System.out.println("[2] Access Customers");
            System.out.println("[3] Access Orders");
            System.out.println("[4] Access Users");
            System.out.println("[5] Exit");
            System.out.println();

            selection = scanner.nextLine();
            switch (selection) {
                case "1" -> {
                    new ProductDisplay();
                    System.out.println();
                }
                case "2" -> {
                    new CustomerDisplay();
                    System.out.println();
                }
                case "3" -> {
                    new OrderDisplay();
                    System.out.println();
                }
                case "4" -> {
                    new UserDisplay();
                    System.out.println();
                }
            }
        }while (!selection.equals("5"));
    }
}
