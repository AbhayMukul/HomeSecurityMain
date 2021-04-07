package com.example.homesecuritymain.CommonClasses.ClassCommon;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseRefrencesFirebase {

    public static DatabaseReference mUserDatabaseCitizen = FirebaseDatabase.getInstance().getReference("citizen");
    public static DatabaseReference mUserDatabaseGuest = FirebaseDatabase.getInstance().getReference("guest");
    public static DatabaseReference mUserDatabaseLoginDetails = FirebaseDatabase.getInstance().getReference("LoginDetails");
    public static DatabaseReference mUserDatabaseLoginMain = FirebaseDatabase.getInstance().getReference("Main");

}
