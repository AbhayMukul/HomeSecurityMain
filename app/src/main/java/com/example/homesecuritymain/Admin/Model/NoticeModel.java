package com.example.homesecuritymain.Admin.Model;

import java.io.Serializable;

public class NoticeModel implements Serializable {
    String Subject;
    String Body;
    String PhoneAdminUpload;
    String NameAdminUpload;
    String Time;
    String Date;

    public NoticeModel() {
    }

    public NoticeModel(String subject, String body, String phoneAdminUpload, String nameAdminUpload, String time, String date) {
        Subject = subject;
        Body = body;
        PhoneAdminUpload = phoneAdminUpload;
        NameAdminUpload = nameAdminUpload;
        Time = time;
        Date = date;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getPhoneAdminUpload() {
        return PhoneAdminUpload;
    }

    public void setPhoneAdminUpload(String phoneAdminUpload) {
        PhoneAdminUpload = phoneAdminUpload;
    }

    public String getNameAdminUpload() {
        return NameAdminUpload;
    }

    public void setNameAdminUpload(String nameAdminUpload) {
        NameAdminUpload = nameAdminUpload;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
