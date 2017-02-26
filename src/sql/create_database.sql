/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Jared
 * Created: Feb 14, 2017
 */

create table users (
    userid serial primary key,
    username varchar(40),
    password varchar(40),
    email varchar(40)
);

create table shops (
    shopid serial primary key,
    name varchar(50),
    street varchar(255),
    city varchar(50),
    state varchar(2),
    zip int,
    phone varchar(11),
    opentime int,
    closetime int,
    description text
);

create table reviews (
    reviewid serial primary key,
    foodrank int,
    expenserank int,
    coffeerank int,
    dateadded timestamp,
    owner int,
    shop int,
    comment text
);
