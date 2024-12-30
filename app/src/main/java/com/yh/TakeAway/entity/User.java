package com.yh.TakeAway.entity;

import java.io.Serializable;

import java.sql.Date;

public class User implements Serializable {
    public User() {
    }

    public User(int userID, String username, String Password, String phone, byte[] profile, Date registertime) {
        UserID = userID;
        Username = username;
        Password = Password;
        Phone = phone;
        this.profile = profile;
        this.registertime = registertime;
    }

    private int UserID;
    private String Username;
    private String Password;
    private String Phone;
    private byte[] profile;
    private Date registertime;
    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String passWord) {
        Password = passWord;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public byte[] getProfile() {
        return profile;
    }

    public void setProfile(byte[] profile) {
        this.profile = profile;
    }

    public Date getRegistertime() {
        return registertime;
    }

    public void setRegistertime(Date registertime) {
        this.registertime = registertime;
    }

    @Override
   public String toString() {
        return "User{" +
                "UserID='" + UserID + '\'' +
                ", Username='" +Username + '\'' +
                ", Password='" + Password + '\'' +
                ", Phone='" +Phone + '\'' +
                ", profile'" +  profile + '\'' +
                ", registertime='" + registertime + '\'' +
                '}';
    }



}
