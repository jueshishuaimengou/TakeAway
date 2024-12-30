package com.yh.TakeAway.entity;

import java.sql.Blob;
import java.util.Date;

public class Review {
    private int ReviewID;
    private int OderID;
    private int VendorID;
    private int v_rating;
    private Date time;
    private String text;
    private byte[] img;

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    private int UserID;
    public Review() {
    }

    public int getReviewID() {
        return ReviewID;
    }

    public void setReviewID(int reviewID) {
        ReviewID = reviewID;
    }

    public int getOderID() {
        return OderID;
    }

    public void setOderID(int oderID) {
        OderID = oderID;
    }

    public int getVendorID() {
        return VendorID;
    }

    public void setVendorID(int vendorID) {
        VendorID = vendorID;
    }

    public int getV_rating() {
        return v_rating;
    }

    public void setV_rating(int v_rating) {
        this.v_rating = v_rating;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }


    public Review(int reviewID, int oderID, int vendorID, int v_rating, Date time, String text, byte[]img,int userID) {
        ReviewID = reviewID;
        OderID = oderID;
        VendorID = vendorID;
        this.v_rating = v_rating;
        this.time = time;
        this.text = text;
        this.img = img;
        UserID=userID;
    }
}
