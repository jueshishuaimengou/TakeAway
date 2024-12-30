package com.yh.TakeAway.entity;

import java.io.Serializable;
import java.sql.Blob;

public class Vendor implements Serializable {
    private int VendorID;
    private String Venname;
    private String Desc;
    private String phone;
    private String Address;
    private int Rating;
    private byte[] image;


    @Override
    public String toString(){
        return "Vendor{" +
                "VendorID='" + VendorID + '\'' +
                ",Venname='" +Venname + '\'' +
                ", Desc='" +Desc+ '\'' +
                ", phone='" + phone+ '\'' +
                ", Address='" +  Address + '\'' +
                ", Rating='" + Rating + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
    public Vendor(int vendorID, String venname, String desc, String phone, String address, int rating, byte[] image) {
        VendorID = vendorID;
        Venname = venname;
        Desc = desc;
        this.phone = phone;
        Address = address;
        Rating = rating;
        this.image = image;
    }

    public Vendor() {
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }



    public int getVendorID() {
        return VendorID;
    }

    public void setVendorID(int vendorID) {
        VendorID = vendorID;
    }

    public String getVenname() {
        return Venname;
    }

    public void setVenname(String venname) {
        Venname = venname;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }


}
