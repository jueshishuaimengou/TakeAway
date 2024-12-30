package com.yh.TakeAway.entity;

public class Address {
    public Address() {
    }

    public Address(int addressID, int userID, String address) {
        AddressID = addressID;
        UserID = userID;
        Address = address;
    }

    private int AddressID;
    private int UserID;
    private String  Address;

    public int getAddressID() {
        return AddressID;
    }

    public void setAddressID(int addressID) {
        AddressID = addressID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
