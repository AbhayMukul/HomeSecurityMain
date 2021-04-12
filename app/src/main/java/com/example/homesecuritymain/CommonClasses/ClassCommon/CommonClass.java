package com.example.homesecuritymain.CommonClasses.ClassCommon;

import android.provider.ContactsContract;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommonClass {
    DatabaseRefrencesFirebase databaseRefrencesFirebase;

    public Double StringToDouble(String string){
        Double decimal = Double.parseDouble(string);
        return decimal;
    }

    //---------------------------------------------------------------------------------------CITIZEN
    public DatabaseReference referenceGuestCitizenActive(String Flat){
        return databaseRefrencesFirebase.mUserDatabaseCitizen.child(Flat).child("GUEST").child("Active");
    }

    public DatabaseReference referenceGuestCitizenAll(String Flat){
        return databaseRefrencesFirebase.mUserDatabaseCitizen.child(Flat).child("GUEST").child("All");
    }

    public DatabaseReference referenceFamilyCitizen(String Flat){
        return databaseRefrencesFirebase.mUserDatabaseCitizen.child(Flat).child("family");
    }

    public DatabaseReference referenceLocationCitizen(String Flat){
        return databaseRefrencesFirebase.mUserDatabaseCitizen.child(Flat).child("location");
    }

    //-----------------------------------------------------------------------------------------GUARD
    public DatabaseReference referenceGuestGuardActive(){
        return databaseRefrencesFirebase.mUserDatabaseGuest.child("Active");
    }

    public DatabaseReference referenceGuestGuardAll(){
        return databaseRefrencesFirebase.mUserDatabaseGuest.child("All");
    }

    //----------------------------------------------------------------------------------LOGINDETAILS
    public DatabaseReference refrenceCitizenLoginPassword(String phone){
        return databaseRefrencesFirebase.mUserDatabaseLoginDetails.child(phone).child("password");
    }

    public DatabaseReference referenceCitizenLoginKeyUID(String phone){
        return databaseRefrencesFirebase.mUserDatabaseLoginDetails.child(phone).child("first flat account KeyUID");
    }

    //------------------------------------------------------------------------------------------Main
    public DatabaseReference referenceCitizenMain(String key){
        return databaseRefrencesFirebase.mUserDatabaseLoginMain.child(key);
    }

    public DatabaseReference referenceCitizenMainAdmin(String key){
        return databaseRefrencesFirebase.mUserDatabaseLoginMain.child(key).child("admin");
    }

    public DatabaseReference referenceCitizenMainDateJoined(String key){
        return databaseRefrencesFirebase.mUserDatabaseLoginMain.child(key).child("dateJoined");
    }
}
