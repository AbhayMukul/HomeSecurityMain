package com.example.homesecuritymain.citizen.Model;

public class ModelFamilyMember {
    //from inputs
    String Name;
    String Phone;
    String Password;

    Boolean ADMIN;

    //from already present data
    String Flat;
    String DateJoined;

    //For multiple flats

    public ModelFamilyMember(String name, String phone, String password, Boolean ADMIN,String flat) {
        Name = name;
        Phone = phone;
        Password = password;
        Flat = flat;
        this.ADMIN = ADMIN;
    }

    public ModelFamilyMember() {
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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
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

    public ModelFamilyMember(String name, String phone, String password, Boolean ADMIN, String flat, String dateJoined) {
        Name = name;
        Phone = phone;
        Password = password;
        this.ADMIN = ADMIN;
        Flat = flat;
        DateJoined = dateJoined;
    }
}
