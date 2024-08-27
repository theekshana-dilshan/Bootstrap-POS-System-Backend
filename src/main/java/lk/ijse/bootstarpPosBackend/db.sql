CREATE DATABASE IF NOT EXISTS boostrapposbackend;

USE boostrapposbackend;

create table customer(
                        customerId VARCHAR(50) primary key,
                        customerName VARCHAR(50),
                        customerAddress VARCHAR(50),
                        customerSalary VARCHAR(50)
)

create table item(
                     itemCode VARCHAR(50) primary key,
                     itemName VARCHAR(50),
                     qtyOnHand VARCHAR(50),
                     itemPrice VARCHAR(50)
)

create table orders(
                       orderId VARCHAR(50) primary key,
                       orderDate VARCHAR(50),
                       orderCustomer VARCHAR(50),
                       discount VARCHAR(50),
                       subTotal VARCHAR(50)
)


