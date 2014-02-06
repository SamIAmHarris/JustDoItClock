package com.samiamharris.justdoitclock;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by samharris on 12/16/13.
 */
public class AddFragment extends Fragment implements View.OnClickListener, MultiSpinner.MultiSpinnerListener, TimePicker.OnTimeChangedListener {

    //what does this class need to do?
    //have components for all the settings
    //either be completely fresh, or have set data from the edit page
    //go back to the clock fragment

    Button saveButton;

    SeekBar addSeekBar;
    EditText phraseText;
    EditText nameText;
    List<String> items;
    TimePicker timePicker;
    MultiSpinner multiSpinner;


    OnSaveListener saveClickCallback;
    AlarmData savedAlarm;


    public interface OnSaveListener {
        /** Called by ClockFragment when save button is clicked */
        public void onSaveSelected();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View addFragView = inflater.inflate(R.layout.add_fragment, container,false);

        return addFragView;

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            saveClickCallback = (OnSaveListener) activity;
        }
        //if this type of exception pops up
        catch (ClassCastException e){
            //tell you in the log why it failed. That this interface was not implemented
            throw new ClassCastException(activity.toString() +
                    "must implement OnSaveListener");
        }
    }


    @Override
    public void onItemsSelected(boolean[] selected){


        for(int i = 0; i < selected.length; i++){
            Log.e("OPTIONINOS", "length:" + String.valueOf(selected.length) + "element("+i+"):" + String.valueOf(selected[i]));
        }

        if(selected[0] == true) {
            savedAlarm.setMonday(true);
        } else {
            savedAlarm.setMonday(false);
        }
        if(selected[1] == true) {
            savedAlarm.setTuesday(true);
        }else {
            savedAlarm.setTuesday(false);
        }
        if(selected[2] == true) {
            savedAlarm.setWednesday(true);
        }else {
            savedAlarm.setWednesday(false);
        }
        if(selected[3] == true) {
            savedAlarm.setThursday(true);
        }else {
            savedAlarm.setThursday(false);
        }
        if(selected[4] == true) {
            savedAlarm.setFriday(true);
        }else {
            savedAlarm.setFriday(false);
        }
        if(selected[5] == true) {
            savedAlarm.setSaturday(true);
        }else {
            savedAlarm.setSaturday(false);
        }
        if(selected[6] == true) {
            savedAlarm.setSunday(true);
        }else {
            savedAlarm.setSunday(false);
        }

    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

        // set current time into timepicker

        savedAlarm.setmMinute(minute);
        savedAlarm.setmHour(hourOfDay);

        int TODAY = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        savedAlarm.convertTimeToEvent(hourOfDay, minute,TODAY );

    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //set up references to UI elements here
        saveButton = (Button) getActivity().findViewById(R.id.save_button);

        multiSpinner = (MultiSpinner) getActivity().findViewById(R.id.multi_spinner);

        timePicker = (TimePicker) getActivity().findViewById(R.id.time_picker);

        addSeekBar = (SeekBar) getActivity().findViewById(R.id.add_seek_bar);

        phraseText = (EditText) getActivity().findViewById(R.id.add_phrase);
        nameText = (EditText) getActivity().findViewById(R.id.add_name);

        //Set up multi spinner
        items =  new ArrayList<String>(7);

        items.add(0,"Repeat Monday");
        items.add(1,"Repeat Tuesday");
        items.add(2,"Repeat Wednesday");
        items.add(3,"Repeat Thursday");
        items.add(4,"Repeat Friday");
        items.add(5,"Repeat Saturday");
        items.add(6,"Repeat Sunday");


        String defaultText = "None";

        multiSpinner.setItems(items, defaultText, this);

        //instantiating the Alarm Data Object
        //use the alarmdata that was passed in
        //if no data was given to you, create a new object

        Bundle args = getArguments();
        if(args!= null) {

            savedAlarm = Storage.getInstance().getDataForName(getActivity(), args.getString("name"));

            savedAlarm.turnOff(getActivity());
            savedAlarm.setmSwitch(false);

            Toast.makeText(getActivity(), savedAlarm.getmName() + " currently turned off", Toast.LENGTH_LONG).show();

            //Set both Edit Texts
            nameText.setText(savedAlarm.getmName());
            phraseText.setText(savedAlarm.getmPhrase());

            //Set Time Picker
            timePicker.setCurrentHour(savedAlarm.getmHour());
            timePicker.setCurrentMinute(savedAlarm.getmMinute());

            //set Repeat option
            multiSpinner.selected[0] = savedAlarm.monday;
            multiSpinner.selected[1] = savedAlarm.tuesday;
            multiSpinner.selected[2] = savedAlarm.wednesday;
            multiSpinner.selected[3] = savedAlarm.thursday;
            multiSpinner.selected[4] = savedAlarm.friday;
            multiSpinner.selected[5] = savedAlarm.saturday;
            multiSpinner.selected[6] = savedAlarm.sunday;

            //To Do List

            //set volume
            addSeekBar.setProgress(savedAlarm.getmVolume());

            //
            Storage.getInstance().add(getActivity(), savedAlarm);



        } else {
            savedAlarm = new AlarmData();
        }


        //Save button set up
        saveButton.setOnClickListener(this);

        //multispinner setup

//        items =  new ArrayList<String>(7);
//
//        items.add(0,"Repeat Monday");
//        items.add(1,"Repeat Tuesday");
//        items.add(2,"Repeat Wednesday");
//        items.add(3,"Repeat Thursday");
//        items.add(4,"Repeat Friday");
//        items.add(5,"Repeat Saturday");
//        items.add(6,"Repeat Sunday");
//
//
//        String defaultText = "None";
//
//        multiSpinner.setItems(items, defaultText, this);

        //Setting the Volume to the Alarm Data Object
        addSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                savedAlarm.setmVolume(progressChanged);
            }
        });


        //Time Picker set up
        timePicker.setOnTimeChangedListener(this);


    }


    @Override
    public void onClick(View v) {

        //Setting the on/off switch to true
        savedAlarm.setmSwitch(true);

        //Setting the unique ID for the alarm
        savedAlarm.setmAlarmId((int) System.currentTimeMillis());

        //Setting the name of alarm data object
        String enteredName;

        enteredName = nameText.getText().toString();

        if(enteredName.equals("")) {
            enteredName = "Alarm Name";
        }

        savedAlarm.setmName(enteredName);

        //Setting the phrase to the Alarm Data Object
        String phrase = phraseText.getText().toString();

        if (phrase.equals("")) {
            phrase = "Wake up";
        }
        //Fixing issue if user accidentally puts space at end of phrase
        char space = phrase.charAt(phrase.length() - 1);

        if(space == ' ') {
            phrase = phrase.substring(0, phrase.length() - 1);
        }

        savedAlarm.setmPhrase(phrase);

        //Add the alarm to the current alarms, turn the alarm on, callback to clock fragment
        if(savedAlarm.alarmEvent != null) {

            Storage.getInstance().add(getActivity(), savedAlarm);

            savedAlarm.turnOn(getActivity());

            saveClickCallback.onSaveSelected();

        } else {
            //Make sure the user sets an Alarm time
            Toast.makeText(getActivity(), "Set Alarm Time", Toast.LENGTH_LONG).show();

        }
    }
}
