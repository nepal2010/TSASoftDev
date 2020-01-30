package com.example.pillreminder;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;

import com.example.pillreminder.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    Spinner hourSpinner, minuteSpinner, timeHemisphereSpinner;
    ArrayAdapter<CharSequence> adapter;
    Button fromDateButton, toDateButton;
    boolean isSelectingFromDate, isSelectingToDate = false;
    CalendarView dateSelectorCaleder;
    ColorInt selectedColor, unselectedColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeHemisphereSpinner = findViewById(R.id.AMPMDropDown);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.timeHemisphere_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeHemisphereSpinner.setAdapter(adapter);

        hourSpinner = findViewById(R.id.hourDropDown);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.hours_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourSpinner.setAdapter(adapter);

        minuteSpinner = findViewById(R.id.minuteDropDown);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.minutes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minuteSpinner.setAdapter(adapter);

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
            }
        });
    }

}

