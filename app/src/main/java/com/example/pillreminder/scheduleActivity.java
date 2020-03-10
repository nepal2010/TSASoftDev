package com.example.pillreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pillreminder.R;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class scheduleActivity extends AppCompatActivity {
    private String mParam1;
    private String mParam2;
    EditText medication;
    Spinner hourSpinner, minuteSpinner, timeHemisphereSpinner, frequencySpinner = null;
    ArrayAdapter<CharSequence> adapter = null;
    Button fromDateButton, toDateButton,saveButton;
    boolean isSelectingFromDate, isSelectingToDate = false, isValidDate;
    CalendarView dateSelectorCaleder;
    int startYear = 0, startMonth, startDay, endYear = 0, endMonth, endDay, timeInterval;
    String name;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        medication = findViewById(R.id.pillTextEdit);
        medication.setOnEditorActionListener(new DoneOnEditorActionListener());
        setUpSpinners();
        setUpCalendar();
        setUpSaveButton();

    }

    void setVisibilityOfObjectsUnderCalendar(boolean b){
        if(b){
            findViewById(R.id.Startingat).setVisibility(View.VISIBLE);
            hourSpinner.setVisibility(View.VISIBLE);
            minuteSpinner.setVisibility(View.VISIBLE);
            timeHemisphereSpinner.setVisibility(View.VISIBLE);
            findViewById(R.id.textView4).setVisibility(View.VISIBLE);
            frequencySpinner.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.Startingat).setVisibility(View.GONE);
            hourSpinner.setVisibility(View.GONE);
            minuteSpinner.setVisibility(View.GONE);
            timeHemisphereSpinner.setVisibility(View.GONE);
            findViewById(R.id.textView4).setVisibility(View.GONE);
            frequencySpinner.setVisibility(View.GONE);
            saveButton.setVisibility(View.GONE);

        }
    }

    void setUpSpinners(){
        hourSpinner = findViewById(R.id.hourDropDown);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.hours_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        try {
            hourSpinner.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
        hourSpinner.setDropDownWidth(hourSpinner.getDropDownWidth()+100);
        hourSpinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        timeHemisphereSpinner = findViewById(R.id.AMPMDropDown);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.timeHemisphere_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        try{
            timeHemisphereSpinner.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
        timeHemisphereSpinner.setDropDownWidth(timeHemisphereSpinner.getDropDownWidth()+100);
        timeHemisphereSpinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


        minuteSpinner = findViewById(R.id.minuteDropDown);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.minutes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        try{
            minuteSpinner.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
        minuteSpinner.setDropDownWidth(minuteSpinner.getDropDownWidth()+100);
        minuteSpinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        frequencySpinner = findViewById(R.id.frequencySpinner);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.frequency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        try{
            frequencySpinner.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
        frequencySpinner.setDropDownWidth(frequencySpinner.getDropDownWidth()+200);
        frequencySpinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

    }

    void setUpCalendar(){
        dateSelectorCaleder = findViewById(R.id.caledarView);
        dateSelectorCaleder.setVisibility(View.INVISIBLE);

        fromDateButton = findViewById(R.id.fromDate);
        fromDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(fromDateButton.getWindowToken(), 0);

                isSelectingFromDate = true;
                fromDateButton.setBackgroundColor(Color.GREEN);

                isSelectingToDate = false;
                toDateButton.setBackgroundColor(Color.GRAY);

                dateSelectorCaleder.setVisibility(View.VISIBLE);
                setVisibilityOfObjectsUnderCalendar(false);
            }
        });



        toDateButton = findViewById(R.id.toDate);
        toDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(toDateButton.getWindowToken(), 0);

                isSelectingToDate = true;
                toDateButton.setBackgroundColor(Color.GREEN);

                isSelectingFromDate = false;
                fromDateButton.setBackgroundColor(Color.GRAY);

                dateSelectorCaleder.setVisibility(View.VISIBLE);
                setVisibilityOfObjectsUnderCalendar(false);
            }
        });

        dateSelectorCaleder.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                if(isSelectingFromDate){
                    fromDateButton.setText("" + (i1+1) + " / " + i2 + " / "+ i);
                    startYear = i;
                    startMonth = i1;
                    startDay = i2;
                }else if(isSelectingToDate){
                    toDateButton.setText("" + (i1+1) + " / " + i2 + " / "+ i);
                    endYear = i;
                    endMonth = i1;
                    endDay = i2;
                }
                isSelectingFromDate = false;
                isSelectingToDate = false;
                if(startDay!=0 && endYear != 0) {
                    if(isBefore(startYear, startMonth, startDay, endYear, endMonth, endDay)) {
                        fromDateButton.setBackgroundColor(Color.GREEN);
                        toDateButton.setBackgroundColor(Color.GREEN);
                        isValidDate = true;
                    }else{
                        fromDateButton.setBackgroundColor(Color.GREEN);
                        toDateButton.setBackgroundColor(Color.RED);
                        isValidDate = false;
                    }
                }
                dateSelectorCaleder.setVisibility(View.INVISIBLE);
                setVisibilityOfObjectsUnderCalendar(true);
            }
        });
    }

    void setUpSaveButton(){
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidDate) {
                    if (!new File(Environment.getExternalStorageDirectory() + "/PillReminder").exists()) {
                        File f = new File(Environment.getExternalStorageDirectory() + "/PillReminder");
                        f.mkdir();

                    }
                    if (!new File(Environment.getExternalStorageDirectory() + "/PillReminder/medicationList.txt").exists()) {
                        new File(Environment.getExternalStorageDirectory() + "/PillReminder/medicationList.txt");
                    }


                    int startHour = hourSpinner.getSelectedItemPosition() + 1
                            + 12 * timeHemisphereSpinner.getSelectedItemPosition();
                    int startMin = minuteSpinner.getSelectedItemPosition() * 15;
                    switch (frequencySpinner.getSelectedItemPosition()) {
                        case 0:
                            timeInterval = 72;
                            break;
                        case 1:
                            timeInterval = 48;
                            break;
                        case 2:
                            timeInterval = 24;
                            break;
                        case 3:
                            timeInterval = 12;
                            break;
                        case 4:
                            timeInterval = 8;
                            break;
                        case 5:
                            timeInterval = 6;
                            break;
                        case 6:
                            timeInterval = 4;
                            break;
                        case 7:
                            timeInterval = 2;
                            break;
                        case 8:
                            timeInterval = 1;
                            break;
                        default:
                            timeInterval = 24;

                    }
                    name = medication.getText().toString();
                    medication med = new medication(name, startYear, startMonth, startDay,
                            endYear, endMonth, endDay, timeInterval, startHour, startMin);
                    Gson jsonWriter = new Gson();


                    BufferedWriter noteWriter;
                    try {
                        noteWriter = new BufferedWriter(new FileWriter(
                                Environment.getExternalStorageDirectory() + "/PillReminder/medicationList.txt", true));
                        noteWriter.newLine();
                        noteWriter.append(jsonWriter.toJson(med));
                        noteWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Date start = new Date(startYear - 1900, startMonth, startDay, startHour, startMin);
                    Date end = new Date(endYear - 1900, endMonth, endDay, startHour, startMin);
                    try {
                        Calendar c = findNextPillSpot(start, timeInterval);
                        Calendar n = Calendar.getInstance();
                        n.setTime(sdf.parse(sdf.format(new Date())));
                        Calendar e = Calendar.getInstance();
                        e.setTime(sdf.parse(sdf.format(end)));



                        while (c.before(e)) {
                            int secBetween = (int) secondsBetween(c, n);
                            scheduleNotification(getNotification("Time to take your " + name), secBetween);
                            c.add(Calendar.HOUR, timeInterval);
                            System.out.println("current: " + c.get(Calendar.YEAR) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.DATE) + "/" + c.get(Calendar.HOUR) + "/" + c.get(Calendar.MINUTE));
                            System.out.println("end: " + e.get(Calendar.YEAR) + "/" + e.get(Calendar.MONTH) + "/" + e.get(Calendar.DATE) + "/" + e.get(Calendar.HOUR) + "/" + e.get(Calendar.MINUTE));

                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    goBackHomeAfterCreatingAPillSchedule();
                }else{
                    Toast.makeText(getBaseContext(), "End Date is before Start Date" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public static long secondsBetween(Calendar startDate, Calendar endDate) {
        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();
        return TimeUnit.MILLISECONDS.toSeconds(Math.abs(end - start));
    }
    Calendar findNextPillSpot(Date start, int dHours) throws ParseException {

        String startTime = sdf.format(start);
        String nowTime = sdf.format(new Date());
        System.out.println(startTime);
        System.out.println(nowTime);
        Date sDate = null;
        Date nDate = null;

        try {
            sDate = sdf.parse(startTime);
            nDate = sdf.parse(nowTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar nTime = Calendar.getInstance();
        nTime.setTime(nDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(sdf.format(start)));
        while(calendar.before(nTime)){

            calendar.add(Calendar.HOUR, dHours);


        }
        return calendar;
    }
    void goBackHomeAfterCreatingAPillSchedule(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(getApplicationContext(), NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, (int)System.currentTimeMillis());
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), (int)System.currentTimeMillis(), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay*1000;
        System.out.println("raw delay: " +  (delay));
        System.out.println("Minutes until notif: " +  (float)(delay)/60);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    public Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setContentTitle("Pill Reminder");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.pill);
        return builder.build();
    }

    boolean isBefore(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay){
        if(startYear<endYear){
            return true;
        }else if(startYear==endYear){
            if(startMonth < endMonth){
                return true;
            }else if(startMonth == endMonth){
                if(startDay < endDay){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            return false;
        }

    }
}

class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            return true;
        }
        return false;
    }
}
