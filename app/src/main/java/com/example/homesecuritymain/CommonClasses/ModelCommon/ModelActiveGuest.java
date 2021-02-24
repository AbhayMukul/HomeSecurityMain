package com.example.homesecuritymain.CommonClasses.ModelCommon;

import java.io.Serializable;

public class ModelActiveGuest implements Serializable {
    String Name;
    String Flat;
    String Number;
    String Work;
    String KeyUID;
    String TimeIn;

    public ModelActiveGuest(String name, String flat, String number, String work, String keyUID, String timeIn) {
        Name = name;
        Flat = flat;
        Number = number;
        Work = work;
        KeyUID = keyUID;
        TimeIn = timeIn;
    }

    public String getTimeIn() {
        return TimeIn;
    }

    public void setTimeIn(String timeIn) {
        TimeIn = timeIn;
    }

    public ModelActiveGuest() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFlat() {
        return Flat;
    }

    public void setFlat(String flat) {
        Flat = flat;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getWork() {
        return Work;
    }

    public void setWork(String work) {
        Work = work;
    }

    public String getKeyUID() {
        return KeyUID;
    }

    public void setKeyUID(String keyUID) {
        KeyUID = keyUID;
    }
}
