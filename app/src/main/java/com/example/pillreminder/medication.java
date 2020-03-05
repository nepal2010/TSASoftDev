package com.example.pillreminder;

import com.google.gson.Gson;

public class medication {

    private int sYear, sMonth, sDay, eYear, eMonth, eDay, sHour,sMin, deltaT;
    private String name;

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
}
