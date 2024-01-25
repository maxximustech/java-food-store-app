/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.views.web.handlers.pages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.models.User;
import main.views.web.handlers.pages.widgets.Footer;
import main.views.web.handlers.pages.widgets.Header;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class LoginPage implements HttpHandler {
    private User user;
    public LoginPage(User user){
        this.user = user;
    }
    public void handle(HttpExchange app){
        try{
            BufferedWriter w = new BufferedWriter(new OutputStreamWriter(app.getResponseBody()));
            w.write(new Header(user).getContent()+"""
                    <v-row no-gutters>
                        <v-col md="6" offset-md="3">
                            <v-form
                                ref="form"
                                v-model="valid"
                                lazy-validation
                              >
                                <v-text-field
                                  v-model="username"
                                  :rules="nameRules"
                                  label="Username"
                                  required
                                ></v-text-field>
                            
                                <v-text-field
                                  v-model="password"
                                  :rules="passRules"
                                  label="Password"
                                  required
                                ></v-text-field>
                                <v-btn
                                  color="blue"
                                  :loading="loading"
                                  @click="tryLogin()"
                                >
                                  Login
                                </v-btn>
                              </v-form>
                        </v-col>
                      </v-row>
                            """+ new Footer().getContent()+ """
                        <script>
                            new Vue({
                              el: '#data-app',
                              vuetify: new Vuetify(),
                              data: () => ({
                                valid: true,
                                username: "",
                                password: "",
                                loading: false,
                                nameRules: [
                                    v => !!v || 'Username is required',
                                ],
                                passRules: [
                                    v => !!v || 'Password is required',
                                ],
                              }),
                              methods: {
                                tryLogin(){
                                    this.loading = true;
                                    fetch("/api/login",{
                                        method: "POST",
                                        body: JSON.stringify({
                                            username: this.username,
                                            password: this.password
                                        })
                                    }).then(response=>{
                                        return response.json();
                                    }).then(data=>{
                                        this.loading = false;
                                       swal({
                                           title: data.statusCode===200||data.statusCode===201?'Success':'Error',
                                           text: data.message,
                                           icon: data.statusCode===200||data.statusCode===201?'success':'error',
                                           button: 'Close'
                                       }).then(result=>{
                                           if(data.statusCode===200||data.statusCode===201){
                                               window.location.replace("/");
                                           }
                                       });
                                    });
                                }
                              }
                            })
                          </script>
                        </body>
                    </html>
                    """);
            app.sendResponseHeaders(200,0);
            w.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
