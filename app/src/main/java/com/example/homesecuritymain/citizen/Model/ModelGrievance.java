package com.example.homesecuritymain.citizen.Model;

public class ModelGrievance {
    String Category;
    String Grievance;
    String PreferredTime;
    String PreferredDate;
    String Flat;
    String CitizenName;
    String CitizenPhone;
    Boolean DONECitizen;
    Boolean DONEHelp;
    String FirebaseUID;
    String AssignedHelpName;
    String AssignedHelpNumber;

    String RecordedTime;
    String RecordedDate;

    public ModelGrievance() {
    }

    public String getRecordedTime() {
        return RecordedTime;
    }

    public void setRecordedTime(String recordedTime) {
        RecordedTime = recordedTime;
    }

    public String getRecordedDate() {
        return RecordedDate;
    }

    public void setRecordedDate(String recordedDate) {
        RecordedDate = recordedDate;
    }

    public ModelGrievance(String category, String grievance, String preferredTime, String preferredDate, String flat, String citizenName, String citizenPhone, Boolean DONECitizen, Boolean DONEHelp, String firebaseUID, String assignedHelpName, String assignedHelpNumber, String recordedTime, String recordedDate) {
        Category = category;
        Grievance = grievance;
        PreferredTime = preferredTime;
        PreferredDate = preferredDate;
        Flat = flat;
        CitizenName = citizenName;
        CitizenPhone = citizenPhone;
        this.DONECitizen = DONECitizen;
        this.DONEHelp = DONEHelp;
        FirebaseUID = firebaseUID;
        AssignedHelpName = assignedHelpName;
        AssignedHelpNumber = assignedHelpNumber;
        RecordedTime = recordedTime;
        RecordedDate = recordedDate;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getGrievance() {
        return Grievance;
    }

    public void setGrievance(String grievance) {
        Grievance = grievance;
    }

    public String getPreferredTime() {
        return PreferredTime;
    }

    public void setPreferredTime(String preferredTime) {
        PreferredTime = preferredTime;
    }

    public String getPreferredDate() {
        return PreferredDate;
    }

    public void setPreferredDate(String preferredDate) {
        PreferredDate = preferredDate;
    }

    public String getFlat() {
        return Flat;
    }

    public void setFlat(String flat) {
        Flat = flat;
    }

    public String getCitizenName() {
        return CitizenName;
    }

    public void setCitizenName(String citizenName) {
        CitizenName = citizenName;
    }

    public String getCitizenPhone() {
        return CitizenPhone;
    }

    public void setCitizenPhone(String citizenPhone) {
        CitizenPhone = citizenPhone;
    }

    public Boolean getDONECitizen() {
        return DONECitizen;
    }

    public void setDONECitizen(Boolean DONECitizen) {
        this.DONECitizen = DONECitizen;
    }

    public Boolean getDONEHelp() {
        return DONEHelp;
    }

    public void setDONEHelp(Boolean DONEHelp) {
        this.DONEHelp = DONEHelp;
    }

    public String getFirebaseUID() {
        return FirebaseUID;
    }

    public void setFirebaseUID(String firebaseUID) {
        FirebaseUID = firebaseUID;
    }

    public String getAssignedHelpName() {
        return AssignedHelpName;
    }

    public void setAssignedHelpName(String assignedHelpName) {
        AssignedHelpName = assignedHelpName;
    }

    public String getAssignedHelpNumber() {
        return AssignedHelpNumber;
    }

    public void setAssignedHelpNumber(String assignedHelpNumber) {
        AssignedHelpNumber = assignedHelpNumber;
    }
}
