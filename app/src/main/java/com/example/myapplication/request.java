package com.example.myapplication;

import com.google.firebase.database.core.UserWriteRecord;

public class request {

    String Date,User_ID,User_Name,User_Mobile,Status;

    public request() {
    }

    public request(String date, String user_ID, String user_Name, String user_Mobile, String status) {
        Date = date;
        User_ID = user_ID;
        User_Name = user_Name;
        User_Mobile = user_Mobile;
        Status = status;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getUser_Mobile() {
        return User_Mobile;
    }

    public void setUser_Mobile(String user_Mobile) {
        User_Mobile = user_Mobile;
    }
}
