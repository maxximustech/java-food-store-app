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

public class BasketPage implements HttpHandler {
    private final User user;
    public BasketPage(User user){
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
                        <v-row class="mb-30" v-for="(b, i) in basket" :key="i">
                            <v-col md="8" offset-md="2">
                                <h3>{{ b.customer.customerName }}'s Basket</h3>
                                <v-btn
                                      color="blue"
                                      dark
                                      @click="createOrder(b.customer.customerID)"
                                      :loading="orderLoading"
                                    >
                                      Create order
                                    </v-btn>
                            </v-col>
                            <v-col md="8" offset-md="2">
                                <v-data-table
                                    :headers="headers"
                                    :items="b.items"
                                    :items-per-page="10"
                                    class="elevation-1"
                                    :loading="basketLoading"
                                  >
                                  <template v-slot:item.action="{ item }">
                                        <v-btn
                                          color="red"
                                          dark
                                          x-small
                                          @click="removeFromBasket(item.product.id,b.customer.customerID)"
                                          :loading="removeLoading"
                                        >
                                          Remove
                                        </v-btn>
                                      </template>
                                  </v-data-table>
                            </v-col>
                          </v-row>
                    """
                    +new Footer().getContent()+ """
                    <script>
                        new Vue({
                          el: '#data-app',
                          vuetify: new Vuetify(),
                          created(){
                            this.fetchBasket();
                          },
                          data: () => ({
                            basket: [],
                            basketLoading: false,
                            removeLoading: false,
                            orderLoading: false,
                            headers: [
                              {
                                text: 'SKU',
                                align: 'start',
                                sortable: false,
                                value: 'product.sku',
                              },
                              { text: 'Description', value: 'product.description' },
                              { text: 'Category', value: 'product.category' },
                              { text: 'Price', value: 'product.price'},
                              { text: 'Expires', value: 'product.expiresOn' },
                              { text: 'Quantity', value: 'quantity' },
                              { text: 'Total', value: 'totalPrice' },
                              { text: 'Action', value: 'action' },
                            ]
                          }),
                          methods: {
                            fetchBasket(){
                                this.basketLoading = true;
                                fetch('/api/basket').then(response=>{
                                  return response.json();
                              }).then(data=>{
                                  this.basketLoading = false;
                                  if(data.statusCode === 200){
                                      this.basket = data.data.basket.map(b=>{
                                        return {
                                            ...b,
                                            items: b.items.map(i=>{
                                                return {
                                                    ...i,
                                                    product: {
                                                        ...i.product,
                                                        expiresOn: this.formatDate(i.product.expiresOn)
                                                    }
                                                }
                                            })
                                        }
                                      });
                                  }
                              });
                            },
                          removeFromBasket(productId,customerId){
                             this.removeLoading = true;
                                  fetch("/api/basket",{
                                      method: "DELETE",
                                      body: JSON.stringify({
                                            productId: productId,
                                            customerId: customerId
                                        })
                                  }).then(response=>{
                                      return response.json();
                                  }).then(data=>{
                                      this.removeLoading = false;
                                     swal({
                                         title: data.statusCode===200||data.statusCode===201?'Success':'Error',
                                         text: data.message,
                                         icon: data.statusCode===200||data.statusCode===201?'success':'error',
                                         button: 'Close'
                                     }).then(result=>{
                                         if(data.statusCode===200||data.statusCode===201){
                                             window.location.reload();
                                         }
                                     });
                                  }); 
                          },
                          createOrder(customerId){
                             this.orderLoading = true;
                                  fetch("/api/orders",{
                                      method: "PUT",
                                      body: JSON.stringify({
                                            id: customerId
                                        })
                                  }).then(response=>{
                                      return response.json();
                                  }).then(data=>{
                                      this.orderLoading = false;
                                     swal({
                                         title: data.statusCode===200||data.statusCode===201?'Success':'Error',
                                         text: data.message,
                                         icon: data.statusCode===200||data.statusCode===201?'success':'error',
                                         button: 'Close'
                                     }).then(result=>{
                                         if(data.statusCode===200||data.statusCode===201){
                                             window.location.replace("/orders");
                                         }
                                     });
                                  }); 
                          },
                          formatDate(timestamp){
                              var date = new Date(timestamp);
                            
                            // Get year, month, and day part from the date
                            var year = date.toLocaleString("default", { year: "numeric" });
                            var month = date.toLocaleString("default", { month: "2-digit" });
                            var day = date.toLocaleString("default", { day: "2-digit" });
                            
                            // Generate yyyy-mm-dd date string
                            return year + "-" + month + "-" + day;
                          },
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
