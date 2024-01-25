CREATE TABLE IF NOT EXISTS users(
    id integer constraint user_pk primary key autoincrement,
    username text not null unique,
    password text not null,
    createdOn date not null default current_date
);
CREATE TABLE IF NOT EXISTS customer(
   customerId integer constraint customer_pk primary key autoincrement,
   name text not null,
   addressLine1 text not null,
   addressLine2 text,
   addressLine3 text,
   country text not null,
   phone text not null,
   postCode text not null,
   updatedOn date not null default current_date,
   createdOn date not null default current_date
);
CREATE TABLE IF NOT EXISTS foodProduct(
    id integer constraint foodProduct_pk primary key autoincrement,
    sku text not null,
    description text not null,
    category text not null,
    price integer default 0,
    stock integer default 0,
    expiresOn date not null,
    updatedOn date not null default current_date,
    createdOn date not null default current_date
);
CREATE TABLE IF NOT EXISTS orders(
    orderNo text not null unique,
    customerId integer not null constraint order_customer_id_fk references customer (customerId) on update cascade on delete cascade,
    updatedOn date not null default current_date,
    createdOn date not null default current_date
);
CREATE TABLE IF NOT EXISTS orderItem(
    orderNo text not null constraint order_item_order_no_fk references orders (orderNo) on update cascade on delete cascade,
    productId integer not null constraint order_item_product_id_fk references foodProduct (id) on update cascade on delete cascade,
    quantity integer default 0
);
CREATE TABLE IF NOT EXISTS basket(
    customerId integer not null constraint basket_customer_id_fk references customer (customerId) on update cascade on delete cascade,
    productId integer not null constraint basket_product_id_fk references foodProduct (id) on update cascade on delete cascade,
    quantity integer default 0
);