package com.example.homesecuritymain.citizen.Model;

public class ModelLocation {
    String Latitude;
    String Longitude;

    String Name;
    String Time;

    String Duration;

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public ModelLocation(String latitude, String longitude, String name, String time, String duration) {
        Latitude = latitude;
        Longitude = longitude;
        Name = name;
        Time = time;
        Duration = duration;
    }
//    public ModelLocation(Double latitude, Double longitude, String name, String time, String duration) {
//        Latitude = latitude;
//        Longitude = longitude;
//        Name = name;
//        Time = time;
//        Duration = duration;
//    }
//
//    public Double getLatitude() {
//        return Latitude;
//    }
//
//    public void setLatitude(Double latitude) {
//        Latitude = latitude;
//    }
//
//    public Double getLongitude() {
//        return Longitude;
//    }
//
//    public void setLongitude(Double longitude) {
//        Longitude = longitude;
//    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public ModelLocation() {
    }

}
