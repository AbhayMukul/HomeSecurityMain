package com.example.homesecuritymain.CommonClasses.ModelCommon;

import java.io.Serializable;

public class ModelGuestList implements Serializable {
    String Name;
    String Flat;
    String Number;
    String Work;

    String Code;

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

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public ModelGuestList() {
    }

    public ModelGuestList(String name, String flat, String number, String work, String code) {
        Name = name;
        Flat = flat;
        Number = number;
        Work = work;
        Code = code;
    }
}
