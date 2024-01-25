/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.views.web.handlers.pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.models.User;
import main.views.web.APIResponse;
import main.utils.Helper;
import main.views.web.Redirect;
import main.views.web.handlers.pages.widgets.Footer;
import main.views.web.handlers.pages.widgets.Header;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class BlankPage implements HttpHandler {
    private final User user;
    public BlankPage(User user){
        this.user = user;
    }
    public void handle(HttpExchange app) {
        BufferedWriter w = new BufferedWriter(new OutputStreamWriter(app.getResponseBody()));
        try {
            if(this.user.getId() <= 0 ){
                new Redirect(app,"/login");
                return;
            }
            //Map<String, String> request = new ObjectMapper().readValue(new Helper().formatBodyRequest(app), Map.class);
            HashMap<String, String> query = new Helper().formatQuery(app);
            w.write(new Header(user).getContent()+
                    """
                    
                    """
                    +new Footer().getContent()+ """
                <script>
                    new Vue({
                      el: '#data-app',
                      vuetify: new Vuetify(),
                      data: () => ({
                      
                      })
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
