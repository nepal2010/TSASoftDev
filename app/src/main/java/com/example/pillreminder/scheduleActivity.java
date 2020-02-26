package com.example.pillreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pillreminder.R;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class scheduleActivity extends AppCompatActivity {
    private String mParam1;
    private String mParam2;
    EditText medication;
    Spinner hourSpinner, minuteSpinner, timeHemisphereSpinner, frequencySpinner = null;
    ArrayAdapter<CharSequence> adapter = null;
    Button fromDateButton, toDateButton,saveButton;
    boolean isSelectingFromDate, isSelectingToDate = false;
    CalendarView dateSelectorCaleder;
    int startYear, startMonth, startDay, endYear, endMonth, endDay, timeInterval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        medication = findViewById(R.id.pillTextEdit);

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

                fromDateButton.setBackgroundColor(Color.GRAY);
                toDateButton.setBackgroundColor(Color.GRAY);

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
                if(!new File(Environment.getExternalStorageDirectory() + "/PillReminder").exists()){
                    File f = new File(Environment.getExternalStorageDirectory() + "/PillReminder");
                    f.mkdir();
                    Toast.makeText(getBaseContext(), f.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                }
                if(!new File(Environment.getExternalStorageDirectory() + "/PillReminder/medicationList.txt").exists()){
                    new File(Environment.getExternalStorageDirectory() + "/PillReminder/medicationList.txt");
                }

                int startHour = hourSpinner.getSelectedItemPosition() + 1
                        + 12*timeHemisphereSpinner.getSelectedItemPosition() ;
                int startMin = minuteSpinner.getSelectedItemPosition()*15;
                switch (frequencySpinner.getSelectedItemPosition()){
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
                    default:
                        timeInterval=24;

                }

                medication med = new medication(medication.getText().toString(), startYear, startMonth, startDay,
                        endYear, endMonth, endDay, timeInterval, startHour, startMin);
                Gson jsonWriter = new Gson();



                BufferedWriter noteWriter;
                try {
                    noteWriter = new BufferedWriter(new FileWriter(
                            new File(Environment.getExternalStorageDirectory() + "/PillReminder/medicationList.txt")));
                    noteWriter.append(jsonWriter.toJson(med));
                    noteWriter.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                goBackHomeAfterCreatingAPillSchedule();
            }
        });
    }
    void goBackHomeAfterCreatingAPillSchedule(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
