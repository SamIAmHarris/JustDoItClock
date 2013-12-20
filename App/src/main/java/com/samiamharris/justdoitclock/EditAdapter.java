package com.samiamharris.justdoitclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.sql.Array;
import java.util.HashMap;

/**
 * Created by samharris on 12/16/13.
 */
public class EditAdapter extends ArrayAdapter<AlarmData> {

    Context mContext;
    int mLayoutResourceId;
    AlarmData[] mData;

    public EditAdapter(Context context, int layoutResourceId, AlarmData[] data ) {
        super(context, layoutResourceId, data);

        this.mContext = context;
        this.mLayoutResourceId = layoutResourceId;
        this.mData = data;
    }


    public void updateData() {

        HashMap<String,AlarmData> alarms = Storage.getInstance().getMyData(mContext);

        //get this to create an array of the alarms
       // for(AlarmData alarm : alarms.values()){
        AlarmData[] setAlarms =  new AlarmData[alarms.size()];

        int i = 0;
        for(AlarmData singleAlarm : alarms.values()){
            setAlarms[i]= singleAlarm;
            i++;
        }

        if(setAlarms != null){
            mData = setAlarms;
        }
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        PlaceHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(mLayoutResourceId, parent, false);

            holder = new PlaceHolder();

            holder.timeView = (TextView) row.findViewById(R.id.edit_alarm_time);
            holder.nameView = (TextView) row.findViewById(R.id.edit_alarm_name);
            holder.switchView = (Switch) row.findViewById(R.id.edit_switch);



            row.setTag(holder);
        }else {
            holder = (PlaceHolder) row.getTag();
        }

        AlarmData data = mData[position];


        holder.switchView.setChecked(data.ismSwitch());
        holder.nameView.setText(data.getmName());

        String hour = String.valueOf(data.getmHour());
        String minute = String.valueOf(data.getmMinute());

        holder.timeView.setText(hour + ":" + minute);



        return row;

    }


    static class PlaceHolder {

        TextView timeView;
        TextView nameView;
        Switch switchView;
    }
}
