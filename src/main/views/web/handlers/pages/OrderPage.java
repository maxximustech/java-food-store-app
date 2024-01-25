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

public class OrderPage implements HttpHandler {
    private final User user;
    public OrderPage(User user){
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
                    <v-row>
                        <v-col md="8" offset-md="2">
                            <h3>Orders</h3>
                        </v-col>
                        <v-col md="8" offset-md="2">
                            <v-data-table
                                :headers="headers"
                                :items="orders"
                                :items-per-page="10"
                                class="elevation-1"
                                :loading="orderLoading"
                              >
                              <template v-slot:item.action="{ item }">
                                    <v-btn
                                      color="red"
                                      dark
                                      x-small
                                      @click="deleteOrder(item.orderNo)"
                                      :loading="deleteLoading"
                                    >
                                      Delete
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
                            this.fetchOrders();
                          },
                          data: () => ({
                            headers: [
                              {
                                text: 'Order No',
                                align: 'start',
                                sortable: false,
                                value: 'orderNo',
                              },
                              { text: 'Customer Name', value: 'customer.customerName' },
                              { text: 'Post Code', value: 'customer.address.postCode' },
                              { text: 'Total', value: 'totalPrice' },
                              { text: 'Date ordered', value: 'createdOn' },
                              { text: 'Action', value: 'action' },
                            ],
                            orderLoading: false,
                            orders: [],
                            deleteLoading: false,
                          }),
                          methods: {
                            fetchOrders(){
                                this.orderLoading = true;
                                    fetch('/api/orders').then(response=>{
                                      return response.json();
                                  }).then(data=>{
                                      this.orderLoading = false;
                                      if(data.statusCode === 200){
                                          this.orders = data.data.orders.map(p=>{
                                          return {
                                            ...p,
                                            createdOn: this.formatDate(p.createdOn)
                                          }
                                          });
                                      }
                                  });
                          },
                          deleteOrder(orderNo){
                             this.deleteLoading = true;
                                  fetch("/api/orders",{
                                      method: "DELETE",
                                      body: JSON.stringify({
                                            orderNo: orderNo
                                        })
                                  }).then(response=>{
                                      return response.json();
                                  }).then(data=>{
                                      this.deleteLoading = false;
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
