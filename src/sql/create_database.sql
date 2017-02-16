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
    username varchar(40),
    password varchar(40),
    email varchar(40)
);

create table shops (
    shopid int,
    name varchar(50),
    city varchar(50),
    state varchar(2),
    zip long,
    phone long,
    opentime int,
    closetime int,
    description text
);

