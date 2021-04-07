package com.example.homesecuritymain.CommonClasses.ModelCommon;

public class ModelActiveGuestGuard {
    String Name;
    String Flat;
    String Number;
    String Work;
    String KeyUID;
    String TimeIn;
    Boolean STOP;
    Boolean AllowedExit;

    public ModelActiveGuestGuard() {
    }

    public ModelActiveGuestGuard(String name, String flat, String number, String work, String keyUID, String timeIn, Boolean STOP, Boolean allowedExit) {
        Name = name;
        Flat = flat;
        Number = number;
        Work = work;
        KeyUID = keyUID;
        TimeIn = timeIn;
        this.STOP = STOP;
        AllowedExit = allowedExit;
    }

    public Boolean getAllowedExit() {
        return AllowedExit;
    }

    public void setAllowedExit(Boolean allowedExit) {
        AllowedExit = allowedExit;
    }

    public Boolean getSTOP() {
        return STOP;
    }

    public void setSTOP(Boolean STOP) {
        this.STOP = STOP;
    }

    public String getTimeIn() {
        return TimeIn;
    }

    public void setTimeIn(String timeIn) {
        TimeIn = timeIn;
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
