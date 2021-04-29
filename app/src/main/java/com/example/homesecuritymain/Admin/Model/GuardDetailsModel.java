package com.example.homesecuritymain.Admin.Model;

public class GuardDetailsModel {
    String Name;
    String Phone;
    String Password;
    String keyUID;
    String ID;
    Boolean ACTIVE;
    String DateJoined;
    String DateLeft;
    String Address;
    String Shift;

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

    public String getKeyUID() {
        return keyUID;
    }

    public void setKeyUID(String keyUID) {
        this.keyUID = keyUID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Boolean getACTIVE() {
        return ACTIVE;
    }

    public void setACTIVE(Boolean ACTIVE) {
        this.ACTIVE = ACTIVE;
    }

    public String getDateJoined() {
        return DateJoined;
    }

    public void setDateJoined(String dateJoined) {
        DateJoined = dateJoined;
    }

    public String getDateLeft() {
        return DateLeft;
    }

    public void setDateLeft(String dateLeft) {
        DateLeft = dateLeft;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public GuardDetailsModel() {
    }

    public String getShift() {
        return Shift;
    }

    public void setShift(String shift) {
        Shift = shift;
    }

    public GuardDetailsModel(String name, String phone, String password, String keyUID, String ID, Boolean ACTIVE, String dateJoined, String dateLeft, String address, String shift) {
        Name = name;
        Phone = phone;
        Password = password;
        this.keyUID = keyUID;
        this.ID = ID;
        this.ACTIVE = ACTIVE;
        DateJoined = dateJoined;
        DateLeft = dateLeft;
        Address = address;
        Shift = shift;
    }
}
