/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.views.web.handlers.pages.widgets;

import main.models.User;

public class Header {
    private final User user;
    public Header(User user){
        this.user = user;
    }
    public String getContent(){
        return """
                <!DOCTYPE html>
                <html>
                <head>
                  <link href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700,900" rel="stylesheet">
                  <link href="https://cdn.jsdelivr.net/npm/@mdi/font@6.x/css/materialdesignicons.min.css" rel="stylesheet">
                  <link href="https://cdn.jsdelivr.net/npm/vuetify@2.x/dist/vuetify.min.css" rel="stylesheet">
                  <link
                        rel="stylesheet"
                        href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp"
                  />
                  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
                    <script src="https://cdn.jsdelivr.net/npm/vue@2.x/dist/vue.js"></script>
                    <script src="https://cdn.jsdelivr.net/npm/vuetify@2.x/dist/vuetify.js"></script>
                    <script src="/sweetalert.js"></script>
                </head>
                <body>
                  <div id="data-app">
                    <v-app>
                      <v-app-bar
                                color="deep-purple accent-4"
                                dense
                                dark
                                app
                              >
                          <v-toolbar-title>Food Store</v-toolbar-title>
                          <v-spacer></v-spacer>
                                """
                +
                (this.user.getId() > 0 ? """
                                        <v-btn href="/" text small>Products</v-btn>
                                       <v-btn href="/?expired" text small>Expired Products</v-btn>
                                        <v-btn href="/customers" text small>Customers</v-btn>
                                        <v-btn href="/basket" text small>Basket</v-btn>
                                        <v-btn href="/orders" text small>Orders</v-btn>
                                        <v-btn href="/logout" text small>Logout</v-btn>
                        """: """
                    <v-btn href="/login" text small>Login</v-btn>
                """)+"""
                              </v-app-bar>
                      <v-main>
                        <!-- Provides the application the proper gutter -->
                        <v-container fluid>
                    """;
    }
}
