package com.example.jan_paul.handpickedandroidclient.Domain;

import android.util.Log;

import java.util.Calendar;

public class TimeRange {
    private Calendar begin;
    private Calendar end;
    private Calendar currentTime;

    public TimeRange(Calendar begin, Calendar end) {
        this.begin = begin;
        this.end = end;
        currentTime = Calendar.getInstance();
    }

    public Boolean isInRange(){
        if (begin == null || end == null){
            return true;
        }
        else if (currentTime.after(begin) && currentTime.before(end)){
            return true;
        }
        else {
            return false;
        }
    }
}
