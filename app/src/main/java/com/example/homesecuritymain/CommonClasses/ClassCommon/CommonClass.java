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

public class CommonClass extends ListActivity {
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
}
