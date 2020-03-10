package com.example.pillreminder;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class medication {
    private int sYear, sMonth, sDay, eYear, eMonth, eDay, sHour,sMin, deltaT;
    private String name;
    float totalTime = 1;
    float timeCompleted = 0;
    double progression = 0.2;
    medication(String name, int startYear, int startMonth, int startDay,
                            int endYear,   int endMonth,   int endDay,
               int dT, int startHour, int startMinute){

        this.name = name;
        sYear = startYear;
        sMonth = startMonth;
        sDay = startDay;
        eYear = endYear;
        eMonth = endMonth;
        eDay = endDay;
        deltaT = dT;
        sHour = startHour;
        sMin = startMinute;

    }

    public String getName(){
        return  name;
    }

    public String getStartDate(){
        return "" + (sMonth+1) + "/" + sDay + "/" + sYear;
    }
    public String getEndDate(){
        return "" + (eMonth+1) + "/" + eDay + "/" + eYear;
    }
    public String  getFrequency(){
        return "every\n" + deltaT + " hours";
    }

    double getProgression(){
        Calendar s = Calendar.getInstance();
        Calendar n = Calendar.getInstance();
        Calendar e = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
        try {
            s.setTime(sdf.parse(sdf.format(new Date(sYear - 1900, sMonth, sDay, sHour, sMin))));
            n.setTime(sdf.parse(sdf.format(new Date())));
            e.setTime(sdf.parse(sdf.format(new Date(eYear - 1900, eMonth, eDay, sHour, sMin))));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

            totalTime = hoursBetween(s , e);
            timeCompleted = hoursBetween(s , n);


        progression = (double) (timeCompleted)/totalTime;
        return progression;
    }

    public static long hoursBetween(Calendar startDate, Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();
        return TimeUnit.MILLISECONDS.toHours(Math.abs(end - start));
    }

}
