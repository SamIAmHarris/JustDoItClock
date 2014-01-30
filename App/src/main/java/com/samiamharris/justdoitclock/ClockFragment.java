package com.samiamharris.justdoitclock;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

/**
 * Created by samharris on 12/16/13.
 */
public class ClockFragment extends Fragment implements View.OnClickListener {

    //what will this class need to do?
    //have the text clock in it
    //add button that goes to the add fragment
    //edit button that goes to the edit fragment


    Button addButton;
    Button editButton;
    OnAddEditListener addEditCallback;

    public interface OnAddEditListener {
        /** Called by ClockFragment when a button is clicked */
        public void onAddSelected();
        public void onEditSelected();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            addEditCallback = (OnAddEditListener) activity;
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

        View clockFragView = inflater.inflate(R.layout.clock_fragment, container,false);

        return clockFragView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addButton = (Button) getActivity().findViewById(R.id.add_button);
        editButton = (Button) getActivity().findViewById(R.id.edit_button);

        addButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == addButton) {
            addEditCallback.onAddSelected();

        } else {
            addEditCallback.onEditSelected();
        }

    }




}
