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

public class CustomerPage implements HttpHandler {
    private final User user;
    public CustomerPage(User user){
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
            String url = "/api/customers";
            w.write(new Header(user).getContent()+
                    """
                            <v-dialog
                                    v-model="dialog"
                                    width="500"
                                    scrollable
                                  >
                                    <v-card>
                              
                                      <v-card-text>
                                          <v-text-field
                                            label="Name"
                                            v-model="customer.customerName"
                                          ></v-text-field>
                                          <v-text-field
                                            label="Address Line 1"
                                            v-model="customer.address.addressLine1"
                                          ></v-text-field>
                                          <v-text-field
                                            label="Address Line 2"
                                            v-model="customer.address.addressLine2"
                                          ></v-text-field>
                                          <v-text-field
                                            label="Address Line 3"
                                            v-model="customer.address.addressLine3"
                                          ></v-text-field>
                                          <v-text-field
                                            label="Phone"
                                            v-model="customer.telephoneNumber"
                                          ></v-text-field>
                                          <v-text-field
                                            label="Country"
                                            v-model="customer.address.country"
                                          ></v-text-field>
                                          <v-text-field
                                            label="Post Code"
                                            v-model="customer.address.postCode"
                                          ></v-text-field>
                                      </v-card-text>
                              
                                      <v-divider></v-divider>
                              
                                      <v-card-actions>
                                        <v-spacer></v-spacer>
                                        <v-btn
                                          color="primary"
                                          text
                                          @click="dialog = false"
                                        >
                                          Close
                                        </v-btn>
                                        <v-btn
                                          color="primary"
                                          text
                                          @click="saveCustomer()"
                                          :loading="modalLoading"
                                        >
                                          Save
                                        </v-btn>
                                      </v-card-actions>
                                    </v-card>
                                  </v-dialog>
                            <v-row>
                                <v-col md="8" offset-md="2">
                                    <h3>Customers</h3>
                            <v-btn
                                  color="blue"
                                  dark
                                  x-small
                                  class="mr-2"
                                  @click="editModal(0)"
                                >
                                  Create New
                                </v-btn>
                                </v-col>
                                <v-col md="8" offset-md="2">
                                    <v-data-table
                                        :headers="headers"
                                        :items="customers"
                                        :items-per-page="10"
                                        class="elevation-1"
                                        :loading="customerLoading"
                                      >
                                      <template v-slot:item.action="{ item }">
                                            <v-btn
                                              color="blue"
                                              dark
                                              x-small
                                              class="mr-2"
                                              @click="editModal(item.customerID)"
                                            >
                                              Edit
                                            </v-btn>
                                            <v-btn
                                              color="red"
                                              dark
                                              x-small
                                              @click="deleteCustomer(item.customerID)"
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
                            this.fetchCustomers();
                          },
                          data: () => ({
                            customers: [],
                            customerLoading: false,
                            dialog: false,
                            modalLoading: false,
                            customer: {
                                "customerID": 0,
                                "customerName":"",
                                "telephoneNumber":"",
                                "address": {
                                    "addressLine1":"",
                                    "addressLine2":"",
                                    "addressLine3":"",
                                    "country":"",
                                    "postCode":"",
                                }
                            },
                            headers: [
                              { text: 'Name', value: 'customerName' },
                              { text: 'Address Line 1', value: 'address.addressLine1' },
                              { text: 'Address Line 2', value: 'address.addressLine2' },
                              { text: 'Address Line 3', value: 'address.addressLine3' },
                              { text: 'Post Code', value: 'address.postCode' },
                              { text: 'Country', value: 'address.country' },
                              { text: 'Action', value: 'action' },
                            ]
                          }),
                          methods: {
                            fetchCustomers(){
                                this.customerLoading = true;
                                    fetch('/api/customers').then(response=>{
                                      return response.json();
                                  }).then(data=>{
                                      this.customerLoading = false;
                                      if(data.statusCode === 200){
                                          this.customers = data.data.customers;
                                      }
                                  });
                            },
                          saveCustomer(){
                             this.modalLoading = true;
                                  fetch("/api/customers",{
                                      method: this.customer.customerID > 0 ? "POST" : "PUT",
                                      body: JSON.stringify(this.customer)
                                  }).then(response=>{
                                      return response.json();
                                  }).then(data=>{
                                      this.modalLoading = false;
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
                          deleteCustomer(id){
                             this.modalLoading = true;
                                  fetch("/api/customers",{
                                      method: "DELETE",
                                      body: JSON.stringify({
                                            id: id
                                        })
                                  }).then(response=>{
                                      return response.json();
                                  }).then(data=>{
                                      this.modalLoading = false;
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
                          editModal(id){
                          if(id <= 0){
                            this.customer = {
                                "customerID":0,
                                "customerName":"",
                                "telephoneNumber":"",
                                "address": {
                                    "addressLine1":"",
                                    "addressLine2":"",
                                    "addressLine3":"",
                                    "country":"",
                                    "postCode":"",
                                }
                            }
                              this.dialog = true;
                          }else{
                            this.customer = this.customers.find(p=>{
                                  return p.customerID === id;
                              });
                              this.dialog = true;
                          }
                              
                          },
                          formatDate(timestamp){
                              var date = new Date(timestamp);
                            
                            // Get year, month, and day part from the date
                            var year = date.toLocaleString("default", { year: "numeric" });
                            var month = date.toLocaleString("default", { month: "2-digit" });
                            var day = date.toLocaleString("default", { day: "2-digit" });
                            
                            // Generate yyyy-mm-dd date string
                            return year + "-" + month + "-" + day;
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
