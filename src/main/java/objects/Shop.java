/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.logging.LogRecord;

/**
 *
 * @author Reed R Gale
 */
public class Shop 
{
    private int shopid;
    private String name;
    private String street;
    private String city;
    private String state;
    private int zip;
    private String phone;
    private int opentime;
    private int closetime;
    private String description;
    private int wifi;
    private int capacity;
    private int volume;
<<<<<<< HEAD
    public LogRecord toString;
=======
    private double coffeeRank;
    private double foodRank;
    private double expenseRank;
>>>>>>> 8540c3372cb62faf39833b71dc8636e2ceb9ca92

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getOpentime() {
        return opentime;
    }

    public void setOpentime(int opentime) {
        this.opentime = opentime;
    }

    public int getClosetime() {
        return closetime;
    }

    public void setClosetime(int closetime) {
        this.closetime = closetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public int getVolume() {
        return volume;
    }
    
    public void setVolume(int volume) {
        this.volume = volume;
    }
    
    public int getWifi() {
        return wifi;
    }
    
    public void setWifi(int wifi) {
        this.wifi = wifi;
    }
    
    public double getFoodRank() {
        return foodRank;
    }
    
    public void setFoodRank(double rank) {
        foodRank = rank;
    }
    
    public double getCoffeeRank() {
        return coffeeRank;
    }
    
    public void setCoffeeRank(double rank) {
        coffeeRank = rank;
    }
    
    public double getExpenseRank() {
        return expenseRank;
    }
    
    public void setExpenseRank(double rank) {
        expenseRank = rank;
    }
    
}
