package com.example.homesecuritymain.Login.Model;

import java.io.Serializable;

public class ModelCitizen implements Serializable {
    String Name;
    String Phone;

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

    public ModelCitizen(String name, String phone, Boolean ADMIN, String flat, String dateJoined) {
        Name = name;
        Phone = phone;
        this.ADMIN = ADMIN;
        Flat = flat;
        DateJoined = dateJoined;
    }
}
