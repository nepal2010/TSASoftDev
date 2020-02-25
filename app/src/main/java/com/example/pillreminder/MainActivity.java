package com.example.pillreminder;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pillreminder.ui.main.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ViewPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    ImageButton createScheduleBtn, reviewMedicationButton, progressButton;
    private int REQUEST_CODE_PERMISSIONS = 10; //arbitrary number, can be changed accordingly
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"}; //array w/ permissions from manifest

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createScheduleBtn = findViewById(R.id.createScheduleButton);
        reviewMedicationButton = findViewById(R.id.reviewScheduleButton);
        progressButton = findViewById(R.id.progressButton);

        setUpImageButtons();

        if(allPermissionsGranted()){

        } else{
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

    }
    void setUpImageButtons(){
        createScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCreateScheduleActivity();
            }
        });

        reviewMedicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToReviewActivity();
            }
        });

        progressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProgressActivity();
            }
        });
    }
    void goToCreateScheduleActivity(){
        Intent intent = new Intent(this, scheduleActivity.class);
        startActivity(intent);
    }

    void goToReviewActivity(){
        Intent intent = new Intent(this, scheduleActivity.class);
        startActivity(intent);
    }

    void goToProgressActivity(){
        Intent intent = new Intent(this, scheduleActivity.class);
        startActivity(intent);
    }
    private boolean allPermissionsGranted(){
        //check if req permissions have been granted
        for(String permission : REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //start camera when permissions have been granted otherwise exit app
        if(requestCode == REQUEST_CODE_PERMISSIONS){
            if(allPermissionsGranted()){

            } else{
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}

//public class SectionsPagerAdapter extends FragmentPagerAdapter {
//
//    public SectionsPagerAdapter(FragmentManager fm) {
//        super(fm);
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        Fragment fragment = null;
//        switch (position) {
//            case 0:
//                fragment = new createScheduleFragment();
//                break;
//            case 1:
//                break;ViewPagerAdapter
//            case 2:
//                break;
//        }
//        return fragment;
//    }
//
//    @Override
//    public int getCount() {
//        // Show 3 total pages.
//        return 3;
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:
//                return "Fragment 1";
//            case 1:
//                return "Fragment 2";
//            case 2:
//                return "Fragment 3";
//        }
//        return null;
//    }
//}