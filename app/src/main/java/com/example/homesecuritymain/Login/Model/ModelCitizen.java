package com.example.homesecuritymain.Login.Model;

import java.io.Serializable;

public class ModelCitizen implements Serializable {
    String Name;
    String Phone;
    String KeyUID;

    Boolean ADMIN;

    //from already present data
    String Flat;
    String DateJoined;

    public ModelCitizen() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public Boolean getADMIN() {
        return ADMIN;
    }

    public void setADMIN(Boolean ADMIN) {
        this.ADMIN = ADMIN;
    }

    public String getFlat() {
        return Flat;
    }

    public void setFlat(String flat) {
        Flat = flat;
    }

    public String getDateJoined() {
        return DateJoined;
    }

    public void setDateJoined(String dateJoined) {
        DateJoined = dateJoined;
    }

    public String getKeyUID() {
        return KeyUID;
    }

    public void setKeyUID(String keyUID) {
        KeyUID = keyUID;
    }

    public ModelCitizen(String name, String phone, String keyUID, Boolean ADMIN, String flat, String dateJoined) {
        Name = name;
        Phone = phone;
        KeyUID = keyUID;
        this.ADMIN = ADMIN;
        Flat = flat;
        DateJoined = dateJoined;
    }
}
