package com.example.pillreminder;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pillreminder.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class currentMedications extends AppCompatActivity {
    List<medication> myMedications = new ArrayList<medication>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_medication);

        populateMeds();
        populateListView();
        registerClickCallback();
    }

    private void populateMeds() {
        Gson jsonReader = new Gson();
        String line;
        try{
            BufferedReader fileReader = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory() + "/PillReminder/medicationList.txt"));

        while((line = fileReader.readLine()) != null){
            if(!line.equals(""))
                myMedications.add(jsonReader.fromJson(line, medication.class));
        }
            Toast.makeText(getBaseContext(), myMedications.get(0).getName(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getBaseContext(), myMedications.get(1).getName(), Toast.LENGTH_SHORT).show();


        }catch (Exception e){
            Toast.makeText(getBaseContext(), "exception" + myMedications.size(), Toast.LENGTH_SHORT).show();
            System.out.println(e);
        }
    }

    private void populateListView() {
        ArrayAdapter<medication> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

                medication clickedMed = myMedications.get(position);
                String message = "You clicked position " + clickedMed.getName()
                        + "\tprogression: " + clickedMed.getProgression()
                        + "\ttTotal: " + clickedMed.totalTime
                        + "\ttCompleted: " + clickedMed.timeCompleted;
                Toast.makeText(currentMedications.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private class MyListAdapter extends ArrayAdapter<medication> {
        public MyListAdapter() {
            super(currentMedications.this, R.layout.activity_item_view, myMedications);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)

            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.activity_item_view, parent, false);
            }



            if(myMedications.size()>0) {

                final medication currentMed = myMedications.get(position);
                // Make:
                TextView name = (TextView) itemView.findViewById(R.id.pillNameTextView);
                name.setText(currentMed.getName());

                // Year:
                TextView startDate = (TextView) itemView.findViewById(R.id.startDateTextView);
                startDate.setText("" + currentMed.getStartDate());

                // Condition:
                TextView endDate = (TextView) itemView.findViewById(R.id.eendDateTextView);
                endDate.setText(currentMed.getEndDate());

                TextView freq = itemView.findViewById(R.id.frequencyTextView);
                freq.setText(currentMed.getFrequency());

                ProgressBar prog = itemView.findViewById(R.id.progressBar);
                prog.setProgress((int)(currentMed.getProgression()*100));
                prog.getProgressDrawable().setColorFilter(
                        Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);

                Button closeButton = itemView.findViewById(R.id.removePillBtn);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Gson jsonReader = new Gson();
                        String remove = jsonReader.toJson(currentMed);
                        if(removeLine(Environment.getExternalStorageDirectory() + "/PillReminder/medicationList.txt",
                                myMedications.indexOf(currentMed))) {
                            Intent intent = new Intent(getBaseContext(), currentMedications.class);
                            startActivity(intent);
                        }else{
                        }
                    }
                });
            }else{

                try {
                    Toast.makeText(getBaseContext(), "else -> try -> name: " + myMedications.get(1).getName(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){

                }
            }
            return itemView;
        }
    }

    boolean removeLine(String inputFile, int line){


        BufferedReader reader = null;
        int l = 0;
        String currentLine;
        if (!new File(Environment.getExternalStorageDirectory() + "/PillReminder/tempFile.txt").exists()) {
            new File(Environment.getExternalStorageDirectory() + "/PillReminder/tempFile.txt");
        }
        File tempFile = new File(Environment.getExternalStorageDirectory() +"/PillReminder/myTempFile.txt");
        try {
            reader = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory() + "/PillReminder/medicationList.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(Environment.getExternalStorageDirectory() + "/PillReminder/tempFile.txt", false));
                while((currentLine = reader.readLine()) != null) {
                if(l != line || currentLine == "") {
                    System.out.println("added line to temp file: " + currentLine);
                    writer.write(currentLine + "\n");
                    l++;
                }else{
                    l++;
                }
            }

            writer.close();
            reader.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
            System.out.println("file not found");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO exception");
            e.printStackTrace();
            return false;
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(Environment.getExternalStorageDirectory() + "/PillReminder/medicationList.txt", false));
            reader = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory() + "/PillReminder/tempFile.txt"));
            System.out.println("Started second transfer");
            while((currentLine = reader.readLine()) != null) {
                writer.write(currentLine+"\n");
                System.out.println(currentLine);
            }
            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Encounter exception at second transfer phase");
            return false;
        }

        return true;

    }

}


