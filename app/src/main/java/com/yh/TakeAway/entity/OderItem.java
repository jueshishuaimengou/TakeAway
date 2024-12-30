package com.yh.TakeAway.entity;

import java.sql.Blob;

public class OderItem {
    private int OderItemID;

    public OderItem() {
    }

    private int OderID;

    public OderItem(int oderItemID, int oderID, String dishname, int dishID, int quantity, double price, byte[] img) {
        OderItemID = oderItemID;
        OderID = oderID;
        Dishname = dishname;
        DishID = dishID;
        Quantity = quantity;
        this.price = price;
        this.img = img;
    }

    public String getDishName() {
        return Dishname;
    }

    public void setDishName(String dishName) {
        Dishname = dishName;
    }

    private String Dishname;
    private int DishID;
    private int Quantity;
    private double price;

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    private byte[] img;

    public int getOderItemID() {
        return OderItemID;
    }

    public void setOderItemID(int oderItemID) {
        OderItemID = oderItemID;
    }

    public int getOderID() {
        return OderID;
    }

    public void setOderID(int oderID) {
        OderID = oderID;
    }

    public int getDishID() {
        return DishID;
    }

    public void setDishID(int dishID) {
        DishID = dishID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
