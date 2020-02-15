package com.example.pillreminder;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link createScheduleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link createScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class createScheduleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Spinner hourSpinner, minuteSpinner, timeHemisphereSpinner, frequencySpinner = null;
    ArrayAdapter<CharSequence> adapter = null;
    Button fromDateButton, toDateButton;
    boolean isSelectingFromDate, isSelectingToDate = false;
    CalendarView dateSelectorCaleder;
    private OnFragmentInteractionListener mListener;

    public createScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static createScheduleFragment newInstance(String param1, String param2) {
        createScheduleFragment fragment = new createScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            
        }
        hourSpinner = getView().findViewById(R.id.hourDropDown);
        adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.hours_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        try {
            hourSpinner.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
        hourSpinner.setDropDownWidth(hourSpinner.getDropDownWidth()+100);
        hourSpinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        timeHemisphereSpinner = getView().findViewById(R.id.AMPMDropDown);
        adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.timeHemisphere_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        try{
            timeHemisphereSpinner.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
        timeHemisphereSpinner.setDropDownWidth(timeHemisphereSpinner.getDropDownWidth()+100);
        timeHemisphereSpinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


        minuteSpinner = getView().findViewById(R.id.minuteDropDown);
        adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.minutes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        try{
            minuteSpinner.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
        minuteSpinner.setDropDownWidth(minuteSpinner.getDropDownWidth()+100);
        minuteSpinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        frequencySpinner = getView().findViewById(R.id.frequencySpinner);
        adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.frequency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        try{
            frequencySpinner.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
        frequencySpinner.setDropDownWidth(frequencySpinner.getDropDownWidth()+200);
        frequencySpinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        dateSelectorCaleder = getView().findViewById(R.id.caledarView);
        dateSelectorCaleder.setVisibility(View.INVISIBLE);

        fromDateButton = getView().findViewById(R.id.fromDate);
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


        toDateButton = getView().findViewById(R.id.toDate);
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
    void setVisibilityOfObjectsUnderCalendar(boolean b){
        if(b){
            getView().findViewById(R.id.Startingat).setVisibility(View.VISIBLE);
            hourSpinner.setVisibility(View.VISIBLE);
            minuteSpinner.setVisibility(View.VISIBLE);
            timeHemisphereSpinner.setVisibility(View.VISIBLE);
            getView().findViewById(R.id.textView4).setVisibility(View.VISIBLE);
            frequencySpinner.setVisibility(View.VISIBLE);
        }else{
            getView().findViewById(R.id.Startingat).setVisibility(View.GONE);
            hourSpinner.setVisibility(View.GONE);
            minuteSpinner.setVisibility(View.GONE);
            timeHemisphereSpinner.setVisibility(View.GONE);
            getView().findViewById(R.id.textView4).setVisibility(View.GONE);
            frequencySpinner.setVisibility(View.GONE);
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_schedule, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
