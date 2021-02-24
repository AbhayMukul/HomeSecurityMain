package com.example.homesecuritymain.CommonClasses.ModelCommon;

import java.io.Serializable;

public class ModelAllGuest implements Serializable {
    String Name;
    String Flat;
    String Number;
    String Work;
    String KeyUID;

    //enter details
    String DateIn;
    String TimeIn;
    String GuardIn;
    String CitizenIn;

    //exit details
    String DateOutCitizen;
    String TimeOutCitizen;
    String DateOutGuard;
    String TimeOUtGuard;
    String GuardOut;
    String CitizenOut;

    Boolean STOP;
    Boolean Allowed;

    public Boolean getAllowed() {
        return Allowed;
    }

    public void setAllowed(Boolean allowed) {
        Allowed = allowed;
    }

    public ModelAllGuest(String name, String flat, String number, String work, String keyUID, String dateIn, String timeIn, String guardIn, String citizenIn, String dateOutCitizen, String timeOutCitizen, String dateOutGuard, String timeOUtGuard, String guardOut, String citizenOut, Boolean STOP, Boolean allowed) {
        Name = name;
        Flat = flat;
        Number = number;
        Work = work;
        KeyUID = keyUID;
        DateIn = dateIn;
        TimeIn = timeIn;
        GuardIn = guardIn;
        CitizenIn = citizenIn;
        DateOutCitizen = dateOutCitizen;
        TimeOutCitizen = timeOutCitizen;
        DateOutGuard = dateOutGuard;
        TimeOUtGuard = timeOUtGuard;
        GuardOut = guardOut;
        CitizenOut = citizenOut;
        this.STOP = STOP;
        Allowed = allowed;
    }

    public String getDateIn() {
        return DateIn;
    }

    public void setDateIn(String dateIn) {
        DateIn = dateIn;
    }

    public String getTimeIn() {
        return TimeIn;
    }

    public void setTimeIn(String timeIn) {
        TimeIn = timeIn;
    }

    public String getGuardIn() {
        return GuardIn;
    }

    public void setGuardIn(String guardIn) {
        GuardIn = guardIn;
    }

    public String getCitizenIn() {
        return CitizenIn;
    }

    public void setCitizenIn(String citizenIn) {
        CitizenIn = citizenIn;
    }

    public String getDateOutCitizen() {
        return DateOutCitizen;
    }

    public void setDateOutCitizen(String dateOutCitizen) {
        DateOutCitizen = dateOutCitizen;
    }

    public String getTimeOutCitizen() {
        return TimeOutCitizen;
    }

    public void setTimeOutCitizen(String timeOutCitizen) {
        TimeOutCitizen = timeOutCitizen;
    }

    public String getDateOutGuard() {
        return DateOutGuard;
    }

    public void setDateOutGuard(String dateOutGuard) {
        DateOutGuard = dateOutGuard;
    }

    public String getTimeOUtGuard() {
        return TimeOUtGuard;
    }

    public void setTimeOUtGuard(String timeOUtGuard) {
        TimeOUtGuard = timeOUtGuard;
    }

    public String getGuardOut() {
        return GuardOut;
    }

    public void setGuardOut(String guardOut) {
        GuardOut = guardOut;
    }

    public String getCitizenOut() {
        return CitizenOut;
    }

    public void setCitizenOut(String citizenOut) {
        CitizenOut = citizenOut;
    }

    public Boolean getSTOP() {
        return STOP;
    }

    public void setSTOP(Boolean STOP) {
        this.STOP = STOP;
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

    public ModelAllGuest() {
    }

}
