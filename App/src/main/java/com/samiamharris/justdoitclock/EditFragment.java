package com.samiamharris.justdoitclock;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by samharris on 12/16/13.
 */

public class EditFragment extends ListFragment {

    //a custom list view that has all the current alarms
    //click one and you will go to add page with saved data
    //delete functionality on long click
    //back will take you to the main clock

    EditAdapter mEditAdapter;
    OnListItemClickListener listItemCallback;
    AlarmData[] savedAlarmArray = {};


    public interface OnListItemClickListener {

        public void onEditAlarmSelected(String name);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            listItemCallback = (OnListItemClickListener) activity;
        }
        //if this type of exception pops up
        catch (ClassCastException e){
            //tell you in the log why it failed. That this interface was not implemented
            throw new ClassCastException(activity.toString() +
                    "must implement OnAddEditListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View editFragView = inflater.inflate(R.layout.edit_fragment, container,false);

        return editFragView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                Toast.makeText(getActivity(), savedAlarmArray[arg2].getmName() + " deleted", Toast.LENGTH_SHORT).show();
                Storage.remove(getActivity(), savedAlarmArray[arg2]);
                updateData();

                mEditAdapter = new EditAdapter(getActivity(), R.layout.edit_row, savedAlarmArray);
                setListAdapter(mEditAdapter);

                return true;
            }
        });

    }

    public void updateData() {

        HashMap<String,AlarmData> alarms = Storage.getInstance().getMyData(getActivity());
        AlarmData[] setAlarms =  new AlarmData[alarms.size()];

        int i = 0;
        for(AlarmData singleAlarm : alarms.values()){
            setAlarms[i]= singleAlarm;
            i++;
        }

        if(setAlarms != null){
            savedAlarmArray = setAlarms;
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        updateData();

        mEditAdapter = new EditAdapter(getActivity(), R.layout.edit_row, savedAlarmArray);
        setListAdapter(mEditAdapter);

        Log.d("did it work", "si");
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //pass the alarm name as a string to the activity


        String name = savedAlarmArray[position].getmName();

        listItemCallback.onEditAlarmSelected(name);
    }

}





