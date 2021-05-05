package com.example.homesecuritymain.CommonClasses.ClassCommon;

import android.app.ListActivity;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;

import com.example.homesecuritymain.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class CommonClass extends DatabaseRefrencesFirebase {
    DatabaseRefrencesFirebase databaseRefrencesFirebase;

    public Double StringToDouble(String string) {
        Double decimal = Double.parseDouble(string);
        return decimal;
    }

    public Integer getLocationUpdateDuration(String duration) {
        if (duration.equals("STOP")) {
            //stop location
            return 0;
        } else if (duration.equals("15 mins")) {
            //15 mins
            return 900;
        } else if (duration.equals("30 mins")) {
            //30 mins
            return 1800;
        } else if (duration.equals("60 mins")) {
            //60 mins
            return 3600;
        } else {
            return 1;
        }
    }

    //------------------------------------------------------------------------------------GUARDLOGIN
    public DatabaseReference referenceGuardLoginName(String ID){
        return mUserDatabaseGuardLogin.child(ID).child("name");
    }

    public DatabaseReference referenceGuardLoginAddress(String ID){
        return mUserDatabaseGuardLogin.child(ID).child("address");
    }

    public DatabaseReference referenceGuardLoginPassword(String ID){
        return mUserDatabaseGuardLogin.child(ID).child("password");
    }
    //-----------------------------------------------------------------------------------------ADMIN
    public DatabaseReference referenceAdminAccountName(String Phone){
        return databaseRefrencesFirebase.mUserDatabaseAdmin.child("Accounts").child("Name");
    }

    public DatabaseReference referenceAdminAccountPhone(String Phone){
        return databaseRefrencesFirebase.mUserDatabaseAdmin.child("Accounts").child("Phone");
    }

    public DatabaseReference referenceAdminAccountPassword(String Phone){
        return databaseRefrencesFirebase.mUserDatabaseAdmin.child("Accounts").child("Password");
    }

    public DatabaseReference referenceAdminNotices(){
        return databaseRefrencesFirebase.mUserDatabaseAdmin.child("Notices");
    }

    //---------------------------------------------------------------------------------------CITIZEN
    public DatabaseReference referenceGuestCitizenActive(String Flat) {
        return databaseRefrencesFirebase.mUserDatabaseCitizen.child(Flat).child("GUEST").child("Active");
    }

    public DatabaseReference referenceGuestCitizenAll(String Flat) {
        return databaseRefrencesFirebase.mUserDatabaseCitizen.child(Flat).child("GUEST").child("All");
    }

    public DatabaseReference referenceFamilyCitizen(String Flat) {
        return databaseRefrencesFirebase.mUserDatabaseCitizen.child(Flat).child("family");
    }

    public DatabaseReference referenceLocationCitizen(String Flat) {
        return databaseRefrencesFirebase.mUserDatabaseCitizen.child(Flat).child("location");
    }

    public DatabaseReference referenceGuestListCitizen(String Flat) {
        return databaseRefrencesFirebase.mUserDatabaseCitizen.child(Flat).child("GuestList");
    }

    public DatabaseReference referenceGrievanceCitizen(String Flat) {
        return databaseRefrencesFirebase.mUserDatabaseCitizen.child(Flat).child("Grievance");
    }

    public DatabaseReference referenceGrievanceCitizenActive(String Flat) {
        return referenceGrievanceCitizen(Flat).child("Active");
    }

    public DatabaseReference referenceGrievanceCitizenActiveAssignedHelpName(String Flat,String key){
        return referenceGrievanceCitizenActive(Flat).child(key).child("assignedHelpName");
    }

    public DatabaseReference referenceGrievanceCitizenActiveAssignedHelpNumber(String Flat,String key){
        return referenceGrievanceCitizenActive(Flat).child(key).child("assignedHelpNumber");
    }

    public DatabaseReference referenceGrievanceCitizenAll(String Flat) {
        return referenceGrievanceCitizen(Flat).child("All");
    }

    public DatabaseReference referenceGrievanceCitizenAllAssignedHelpName(String Flat,String key){
        return referenceGrievanceCitizenActive(Flat).child(key).child("assignedHelpNameFixed");
    }

    public DatabaseReference referenceGrievanceCitizenAllAssignedHelpNumber(String Flat,String key){
        return referenceGrievanceCitizenActive(Flat).child(key).child("assignedHelpNumberFixed");
    }

    //-----------------------------------------------------------------------------------------GUARD
    public DatabaseReference referenceGuestGuardActive() {
        return databaseRefrencesFirebase.mUserDatabaseGuest.child("Active");
    }

    public DatabaseReference referenceGuestGuardAll() {
        return databaseRefrencesFirebase.mUserDatabaseGuest.child("All");
    }

    //----------------------------------------------------------------------------------LOGINDETAILS
    public DatabaseReference refrenceCitizenLoginPassword(String phone) {
        return databaseRefrencesFirebase.mUserDatabaseLoginDetails.child(phone).child("password");
    }

    public DatabaseReference referenceCitizenLoginKeyUID(String phone) {
        return databaseRefrencesFirebase.mUserDatabaseLoginDetails.child(phone).child("first flat account KeyUID");
    }

    //------------------------------------------------------------------------------------------Main
    public DatabaseReference referenceCitizenMain(String key) {
        return databaseRefrencesFirebase.mUserDatabaseLoginMain.child(key);
    }

    public DatabaseReference referenceCitizenMainAdmin(String key) {
        return databaseRefrencesFirebase.mUserDatabaseLoginMain.child(key).child("admin");
    }

    public DatabaseReference referenceCitizenMainDateJoined(String key) {
        return databaseRefrencesFirebase.mUserDatabaseLoginMain.child(key).child("dateJoined");
    }

    //-------------------------------------------------------------------------------------GRIEVANCE
    public DatabaseReference referenceGrievanceActive() {
        return databaseRefrencesFirebase.mUserDatabaseGrievance.child("Active");
    }

    public DatabaseReference referenceGrievanceActiveAssignedHelpName(String key) {
        return referenceGrievanceActive().child(key).child("assignedHelpName");
    }

    public DatabaseReference referenceGrievanceActiveAssignedHelpPhone(String key) {
        return referenceGrievanceActive().child(key).child("assignedHelpNumber");
    }

    public DatabaseReference referenceGrievanceAll() {
        return databaseRefrencesFirebase.mUserDatabaseGrievance.child("All");
    }

    public DatabaseReference referenceGrievanceAllAssignedHelpName(String key) {
        return referenceGrievanceAll().child(key).child("assignedHelpNameFixed");
    }

    public DatabaseReference referenceGrievanceAllAssignedHelpPhone(String key) {
        return referenceGrievanceAll().child(key).child("assignedHelpNumberFixed");
    }

}
