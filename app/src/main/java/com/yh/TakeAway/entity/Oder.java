package com.yh.TakeAway.entity;

import java.util.Date;

public class Oder {
    private int OderID;
    private int UserID;

    public Oder(int userID, int vendorID, int status, Date oderTime, String address) {

        UserID = userID;
        VendorID = vendorID;
        Status = status;
        OderTime = oderTime;
        Address = address;
    }

    public Oder() {
    }

    public int getVendorID() {
        return VendorID;
    }

    public void setVendorID(int vendorID) {
        VendorID = vendorID;
    }

    public int getOderID() {
        return OderID;
    }

    public void setOderID(int oderID) {
        OderID = oderID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public Date getOderTime() {
        return OderTime;
    }

    public void setOderTime(Date oderTime) {
        OderTime = oderTime;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    private int VendorID;
    private int Status;
    private Date OderTime;
    private String Address;

}
