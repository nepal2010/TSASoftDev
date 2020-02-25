package com.example.pillreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pillreminder.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class scheduleActivity extends AppCompatActivity {
    private String mParam1;
    private String mParam2;
    Spinner hourSpinner, minuteSpinner, timeHemisphereSpinner, frequencySpinner = null;
    ArrayAdapter<CharSequence> adapter = null;
    Button fromDateButton, toDateButton,saveButton;
    boolean isSelectingFromDate, isSelectingToDate = false;
    CalendarView dateSelectorCaleder;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
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
                }else if(isSelectingToDate){
                    toDateButton.setText("" + (i1+1) + " / " + i2 + " / "+ i);
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
                BufferedWriter noteWriter;
                try {
                    noteWriter = new BufferedWriter(new FileWriter(
                            new File(Environment.getExternalStorageDirectory() + "/PillReminder/medicationList.txt")));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}
