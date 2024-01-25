/*
 * Copyright (c) 2024. Written by Azeez Boluwatife Abdullahi - 23713593
 */

package main.views.web.handlers.pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.controllers.FoodProductDAO;
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

public class ProductPage implements HttpHandler {
    private final User user;
    public ProductPage(User user){
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
            String url = "/api/products";
            if(query.get("expired") != null && query.get("search") != null && !query.get("search").trim().isEmpty()){
                url += "?expired=&search="+query.get("search");
            }else if(query.get("expired") != null){
                url += "?expired";
            }else if(query.get("search") != null && !query.get("search").trim().isEmpty()){
                url += "?search="+query.get("search");
            }
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
                                    label="Stock Keeping Unit"
                                    v-model="product.sku"
                                  ></v-text-field>
                                  <v-text-field
                                    label="Description"
                                    v-model="product.description"
                                  ></v-text-field>
                                  <v-text-field
                                    label="Category"
                                    v-model="product.category"
                                  ></v-text-field>
                                  <v-text-field
                                    type="number"
                                    label="Price"
                                    v-model="product.price"
                                  ></v-text-field>
                                  <v-text-field
                                    type="number"
                                    label="Stock"
                                    v-model="product.stock"
                                  ></v-text-field>
                                  <v-text-field
                                    label="Expires - YYYY-MM-DD"
                                    v-model="product.expiresOn"
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
                                  @click="saveProduct()"
                                  :loading="modalLoading"
                                >
                                  Save
                                </v-btn>
                              </v-card-actions>
                            </v-card>
                          </v-dialog>
                         <v-dialog
                                 v-model="cartDialog"
                                 width="500"
                                 scrollable>
                                 <v-card>
                                   <v-card-text>
                                    <v-select
                                      :items="customers"
                                      label="Select a customer"
                                      item-text="customerName"
                                      item-value="customerID"
                                      v-model="basketCustomerId"
                                      outlined
                                    ></v-select>
                                   </v-card-text>
                                   <v-divider></v-divider>
                                   <v-card-actions>
                                     <v-spacer></v-spacer>
                                     <v-btn
                                       color="primary"
                                       text
                                       @click="basketDialog = false"
                                     >
                                       Close
                                     </v-btn>
                                     <v-btn
                                       color="primary"
                                       text
                                       @click="addToBasket()"
                                       :loading="basketLoading"
                                       :disabled="basketCustomerId <= 0"
                                     >
                                       Add to basket
                                     </v-btn>
                                   </v-card-actions>
                                 </v-card>
                               </v-dialog>
                            <v-row>
                                <v-col md="8" offset-md="2">
                                    <h3>Products</h3>
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
                                        :items="products"
                                        :items-per-page="10"
                                        class="elevation-1"
                                        :loading="productLoading"
                                      >
                                      <template v-slot:item.action="{ item }">
                                            <v-btn
                                              color="deep-purple"
                                              dark
                                              x-small
                                              class="mr-2"
                                              @click="openCustomerModal(item.id)"
                                            >
                                              Add to Basket
                                            </v-btn>
                                            <v-btn
                                              color="blue"
                                              dark
                                              x-small
                                              class="mr-2"
                                              @click="editModal(item.id)"
                                            >
                                              Edit
                                            </v-btn>
                                            <v-btn
                                              color="red"
                                              dark
                                              x-small
                                              @click="deleteProduct(item.id)"
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
                            this.fetchProducts();
                            this.fetchCustomers();
                          },
                          data: () => ({
                            products: [],
                            customers: [],
                            basketCustomerId: 0,
                            basketProductId: 0,
                            cartDialog: false,
                            basketLoading: false,
                            productLoading: false,
                            dialog: false,
                            modalLoading: false,
                            product: {
                                "id":0,
                                "description":"",
                                "category":"",
                                "price":0,
                                "stock":0,
                                "sku":"",
                                "expiresOn":""
                            },
                            headers: [
                              {
                                text: 'SKU',
                                align: 'start',
                                sortable: false,
                                value: 'sku',
                              },
                              { text: 'Description', value: 'description' },
                              { text: 'Category', value: 'category' },
                              { text: 'Price', value: 'price' },
                              { text: 'Stock', value: 'stock' },
                              { text: 'Expires', value: 'expiresOn' },
                              { text: 'Action', value: 'action' },
                            ]
                          }),
                          methods: {
                            fetchProducts(){
                                this.productLoading = true;
                                    fetch('"""
                                    +url+"""
                                  ').then(response=>{
                                      return response.json();
                                  }).then(data=>{
                                      this.productLoading = false;
                                      if(data.statusCode === 200){
                                          this.products = data.data.products.map(p=>{
                                          return {
                                            ...p,
                                            expiresOn: this.formatDate(p.expiresOn)
                                          }
                                          });
                                      }
                                  });
                          },
                          saveProduct(){
                             this.modalLoading = true;
                                  fetch("/api/products",{
                                      method: this.product.id > 0 ? "POST" : "PUT",
                                      body: JSON.stringify({
                                            ...this.product,
                                            price: parseInt(this.product.price),
                                            stock: parseInt(this.product.stock),
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
                          deleteProduct(id){
                             this.modalLoading = true;
                                  fetch("/api/products",{
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
                            this.product = {
                                "id":0,
                                "description":"",
                                "category":"",
                                "price":0,
                                "stock":0,
                                "sku":"",
                                "expiresOn":""
                            }
                              this.dialog = true;
                          }else{
                            this.product = this.products.find(p=>{
                                  return p.id === id;
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
                          },
                            fetchCustomers(){
                                    fetch('/api/customers').then(response=>{
                                      return response.json();
                                  }).then(data=>{
                                      if(data.statusCode === 200){
                                          this.customers = data.data.customers;
                                      }
                                  });
                            },
                            openCustomerModal(id){
                                this.basketProductId = id;
                                this.cartDialog = true;
                            },
                            addToBasket(){
                                this.basketLoading = true;
                                  fetch("/api/basket",{
                                      method: "PUT",
                                      body: JSON.stringify({
                                            productId: this.basketProductId,
                                            customerId: this.basketCustomerId
                                        })
                                  }).then(response=>{
                                      return response.json();
                                  }).then(data=>{
                                      this.basketLoading = false;
                                      this.cartDialog = false;
                                      this.basketProductId = 0;
                                      this.basketCustomerId = 0;
                                     swal({
                                         title: data.statusCode===200||data.statusCode===201?'Success':'Error',
                                         text: data.message,
                                         icon: data.statusCode===200||data.statusCode===201?'success':'error',
                                         button: 'Close'
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
