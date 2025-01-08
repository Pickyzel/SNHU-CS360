package com.example.cs360project;

import java.io.Serializable;

public class Item  implements Serializable {

    private String name;
    private String onHand;
    private String price;
    private String location;

    Item(String name, String onHand, String price, String location){
        setName(name);
        setOnHand(onHand);
        setPrice(price);
        setLocation(location);
    }

    Item(){
        setName("Item Name");
        setOnHand("0");
        setPrice("0");
        setLocation("No Location");
    }


    public void setName(String name){
        this.name = name;
    }

    public void setOnHand(String onHand){
        this.onHand = onHand;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getName(){
        return name;
    }

    public String getOnHand(){
        return onHand;
    }

    public String getPrice(){
        return price;
    }

    public String getLocation(){
        return location;
    }





}
