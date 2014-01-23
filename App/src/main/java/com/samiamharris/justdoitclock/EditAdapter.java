package com.samiamharris.justdoitclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by samharris on 12/16/13.
 */
public class EditAdapter extends ArrayAdapter<AlarmData> {

    Context mContext;
    int mLayoutResourceId;
    AlarmData[] mData;
    String minute;
    String formattedTime;
    AlarmData data;

    public EditAdapter(Context context, int layoutResourceId, AlarmData[] data ) {
        super(context, layoutResourceId, data);

        this.mContext = context;
        this.mLayoutResourceId = layoutResourceId;
        this.mData = data;
    }


    public void setData(AlarmData[] setAlarms) {

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
            holder.switchView = (Switch) row.findViewById(R.id.on_off_switch);
            row.setTag(holder);

        }else {
            holder = (PlaceHolder) row.getTag();
        }

        data = mData[position];

        holder.switchView.setChecked(data.mSwitch);
        holder.switchView.setOnCheckedChangeListener(onOffListener);

        Integer myPosition = position;
        holder.switchView.setTag(myPosition);

        holder.nameView.setText(data.getmName());

        //Displaying time in 12 hour instead of 24 hour
        int hour = data.getmHour();
        int fixMinute = data.getmMinute();

        if (fixMinute > 9 ) {
            minute = String.valueOf(data.getmMinute());
        } else {
            minute = "0" + String.valueOf(data.getmMinute());
        }
        formattedTime = "time";
        if(hour > 12){
            formattedTime=String.format((hour-12)+":"+ minute + " PM");
        } else if(hour == 12) {
            formattedTime=String.format(12+":"+ minute +" AM");
        }else {
            formattedTime=String.format(hour+":" + minute + "AM");

        }

        holder.timeView.setText(formattedTime);

        return row;

    }

    //on/off button unique to its position
    CompoundButton.OnCheckedChangeListener onOffListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            Integer viewPosition = (Integer) buttonView.getTag();
            isChecked = mData[viewPosition].mSwitch;

            if(isChecked) {
                mData[viewPosition].turnOff(mContext);
                mData[viewPosition].setmSwitch(false);
                notifyDataSetChanged();
                Storage.getInstance().add(mContext, mData[viewPosition]);


            } else {
                mData[viewPosition].turnOn(mContext);
                mData[viewPosition].setmSwitch(true);
                notifyDataSetChanged();
                Storage.getInstance().add(mContext, mData[viewPosition]);


            }
        }
    };




    static class PlaceHolder {

        TextView timeView;
        TextView nameView;
        Switch switchView;
    }
}
