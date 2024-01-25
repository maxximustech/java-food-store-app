/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.models.User;
import main.utils.Database;
import main.utils.Helper;
import main.views.console.ConsoleDisplay;
import main.views.web.Server;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        new Database().initDBTables(false);
        new Main().startApp();
    }
    public void startApp() throws IOException {

        Scanner scanner = new Scanner(System.in);
        String selection;
        do {
            System.out.println("--------------------");
            System.out.println("The Food Store");
            System.out.println("Choose from these options");
            System.out.println("--------------------");
            System.out.println("[1] Run Console Application");
            System.out.println("[2] Run Web Application");
            System.out.println("[3] Exit");
            System.out.println();

            selection = scanner.nextLine();
            switch (selection) {
                case "1" -> {
                    new ConsoleDisplay();
                    return;
                }
                case "2" -> {
                    try{
                        new Server().start();
                        System.out.println("Server started");
                        return;
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
            }
        }while (!selection.equals("3"));
    }
}