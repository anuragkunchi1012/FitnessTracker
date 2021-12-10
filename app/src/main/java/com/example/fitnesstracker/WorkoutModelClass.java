package com.example.fitnesstracker;

public class WorkoutModelClass {
    String Wname, restTime, WDate;

    public WorkoutModelClass(String wname, String restTime, String WDate) {
        this.Wname = wname;
        this.restTime = restTime;
        this.WDate = WDate;
    }

    public String getWname() {
        return Wname;
    }

    public void setWname(String wname) {
        this.Wname = wname;
    }

    public String getRestTime() {
        return restTime;
    }

    public void setRestTime(String restTime) {
        this.restTime = restTime;
    }

    public String getWDate() {
        return WDate;
    }

    public void setWDate(String WDate) {
        this.WDate = WDate;
    }
}
