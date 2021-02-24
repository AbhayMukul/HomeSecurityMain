package com.example.homesecuritymain.CommonClasses.ClassCommon;

import java.util.Date;

public class DateAndTimeClass {
    String time,d;

    public DateAndTimeClass() {
        Date date = new Date();
        d = (String) android.text.format.DateFormat.format("dd/MM/yyyy", date);
        time = (String) android.text.format.DateFormat.format("kk:mm:ss", date);
    }

    public String getCurrentTime(){
        return time;
    }

    public String getCurrentDate(){
        return d;
    }

}
