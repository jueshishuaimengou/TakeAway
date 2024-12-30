package com.yh.TakeAway.entity;

import java.io.Serializable;

public class Dish implements Serializable {
    private int DishID;
    private String Dishname;
    private String Desc;
    private float Price;
    private int Catagory;
    private byte[] Image;
    private int VendorID;
    @Override
    public String toString() {
        return "Dish{" +
                "DishID='" + DishID + '\'' +
                ", Dishname='" +Dishname + '\'' +
                ",  Desc='" +  Desc + '\'' +
                ",  Price='" + Price + '\'' +
                ", Catagory='" +Catagory + '\'' +
                ", Image='" + Image+ '\'' +
                ", VendorID='" +VendorID+ '\'' +
                '}';
    }

    public Dish() {
    }

    public Dish(int dishID, String dishname, String desc, float price, int catagory, byte[] image, int vendorID) {
        DishID = dishID;
        Dishname = dishname;
        Desc = desc;
        Price = price;
        Catagory = catagory;
        VendorID = vendorID;
        Image = image;
    }

    public int getDishID() {
        return DishID;
    }

    public void setDishID(int dishID) {
        DishID = dishID;
    }

    public String getDishname() {
        return Dishname;
    }

    public void setDishname(String dishname) {
        Dishname = dishname;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getCatagory() {
        return Catagory;
    }

    public void setCatagory(int catagory) {
        Catagory = catagory;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public int getVendorID() {
        return VendorID;
    }

    public void setVendorID(int vendorID) {
        VendorID = vendorID;
    }
}
