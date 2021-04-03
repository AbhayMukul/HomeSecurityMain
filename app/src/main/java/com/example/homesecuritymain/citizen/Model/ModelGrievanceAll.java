package com.example.homesecuritymain.citizen.Model;

public class ModelGrievanceAll {
    String Category;
    String Grievance;
    String PreferredTime;
    String PreferredDate;
    String Flat;
    String CitizenName;
    String CitizenPhone;
    Boolean DONE;
    String FirebaseUID;

    String DateFixed;
    String TimeFixed;
    String AssignedHelpNameFixed;
    String AssignedHelpNumberFixed;

    String RecordedTime;
    String RecordedDate;

    public Boolean getDONE() {
        return DONE;
    }

    public void setDONE(Boolean DONE) {
        this.DONE = DONE;
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

    public ModelGrievanceAll(String category, String grievance, String preferredTime, String preferredDate, String flat, String citizenName, String citizenPhone, Boolean DONE, String firebaseUID, String dateFixed, String timeFixed, String assignedHelpNameFixed, String assignedHelpNumberFixed, String recordedTime, String recordedDate) {
        Category = category;
        Grievance = grievance;
        PreferredTime = preferredTime;
        PreferredDate = preferredDate;
        Flat = flat;
        CitizenName = citizenName;
        CitizenPhone = citizenPhone;
        this.DONE = DONE;
        FirebaseUID = firebaseUID;
        DateFixed = dateFixed;
        TimeFixed = timeFixed;
        AssignedHelpNameFixed = assignedHelpNameFixed;
        AssignedHelpNumberFixed = assignedHelpNumberFixed;
        RecordedTime = recordedTime;
        RecordedDate = recordedDate;
    }

    public ModelGrievanceAll() {
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

    public String getFirebaseUID() {
        return FirebaseUID;
    }

    public void setFirebaseUID(String firebaseUID) {
        FirebaseUID = firebaseUID;
    }

    public String getDateFixed() {
        return DateFixed;
    }

    public void setDateFixed(String dateFixed) {
        DateFixed = dateFixed;
    }

    public String getTimeFixed() {
        return TimeFixed;
    }

    public void setTimeFixed(String timeFixed) {
        TimeFixed = timeFixed;
    }

    public String getAssignedHelpNameFixed() {
        return AssignedHelpNameFixed;
    }

    public void setAssignedHelpNameFixed(String assignedHelpNameFixed) {
        AssignedHelpNameFixed = assignedHelpNameFixed;
    }

    public String getAssignedHelpNumberFixed() {
        return AssignedHelpNumberFixed;
    }

    public void setAssignedHelpNumberFixed(String assignedHelpNumberFixed) {
        AssignedHelpNumberFixed = assignedHelpNumberFixed;
    }
}
