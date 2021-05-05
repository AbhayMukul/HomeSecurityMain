package com.example.homesecuritymain.CommonClasses.ClassCommon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAndTimeClass {
    String time, d;

    public DateAndTimeClass() {
        Date date = new Date();
        d = (String) android.text.format.DateFormat.format("dd/MM/yyyy", date);
        time = (String) android.text.format.DateFormat.format("kk:mm:ss", date);
    }

    public Date StringToDate(String string) {
        SimpleDateFormat df = new SimpleDateFormat("kk:mm:ss");
        try {
            return df.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean ShiftTimings(String shitf) {
        if (shitf.equals("Night Shift")){
            if(DateCompare("20:00:00")){
                return true;
            }else if (!DateCompare("08:00:00")){
                return true;
            }else {
                return false;
            }
        }else {
            return DateCompare("08:00:00") && !DateCompare("20:00:00");
        }
    }

    public Boolean DateCompare(String timeUpdate) {
        SimpleDateFormat df = new SimpleDateFormat("kk:mm:ss");

        Date dateUpdate = null;
        Date dateCurrent = null;

        try {
            dateUpdate = df.parse(timeUpdate);
            dateCurrent = df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateCurrent.after(dateUpdate);
    }

    public String getCurrentTime() {
        return time;
    }

    public String getCurrentDate() {
        return d;
    }

}
