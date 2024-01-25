/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.views.console;

import main.controllers.UserDAO;
import main.models.User;

import java.util.Scanner;

/**
 * The User Display class.
 * @author Azeez Boluwatife Abdullahi - 23713593
 */
public class UserDisplay {
    /**
     * Creates a new User Console Display instance.
     * This instance starts a user console menu application.
     */
    public UserDisplay(){
        Scanner scanner = new Scanner(System.in);
        String selection;
        do {
            System.out.println("--------------------");
            System.out.println("User Access Menu");
            System.out.println("Choose from these options");
            System.out.println("--------------------");
            System.out.println("[1] List all users");
            System.out.println("[2] Create new user");
            System.out.println("[3] Try login as user");
            System.out.println("[4] Change user password");
            System.out.println("[5] Delete user by ID");
            System.out.println("[0] Go Back");
            System.out.println();

            selection = scanner.nextLine();
            switch (selection) {
                case "1" -> {
                    for(User user : new UserDAO().findAllUsers()){
                        System.out.println(user);
                    }
                    System.out.println();
                }
                case "2" -> {
                    System.out.println("\nEnter username");
                    String username = new Scanner(System.in).nextLine();
                    System.out.println("\nEnter password");
                    String password = new Scanner(System.in).nextLine();
                    User user = new User(username,password);
                    System.out.println(new UserDAO().addUser(user));
                    System.out.println();
                }
                case "3" -> {
                    System.out.println("\nEnter username");
                    String username = new Scanner(System.in).nextLine();
                    System.out.println("\nEnter password");
                    String password = new Scanner(System.in).nextLine();
                    User user = new UserDAO().loginAsUser(username,password);
                    if(user == null){
                        System.out.println("\nUser login is invalid");
                        break;
                    }
                    System.out.println("\nLogin is valid");
                    System.out.println();
                }
                case "4" -> {
                    System.out.println("\nEnter username");
                    String username = new Scanner(System.in).nextLine();
                    System.out.println("\nEnter password");
                    String password = new Scanner(System.in).nextLine();
                    User user = new UserDAO().loginAsUser(username,password);
                    if(user == null){
                        System.out.println("\nUser login is invalid");
                        break;
                    }
                    System.out.println("\nEnter new password");
                    String newPassword = new Scanner(System.in).nextLine();
                    System.out.println(new UserDAO().changeUserPassword(username,password,newPassword));
                    System.out.println();
                }
                case "5" -> {
                    System.out.println("\nEnter the user ID to delete");
                    int id = new Scanner(System.in).nextInt();
                    System.out.println(new UserDAO().deleteUser(id));
                    System.out.println();
                }
            }
        }while (!selection.equals("0"));
    }
}
