package com.example.pillreminder;

import com.google.gson.Gson;

public class medication {

    int sYear, sMonth, sDay, eYear, eMonth, eDay, sHour,sMin, deltaT;
    String name;

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
}
