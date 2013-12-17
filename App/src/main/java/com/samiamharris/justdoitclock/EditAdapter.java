package com.samiamharris.justdoitclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

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




        holder.switchView.setChecked(true);
        holder.nameView.setText("Weekday Alarm");
        holder.timeView.setText("7:30AM");


        return row;

    }


    static class PlaceHolder {

        TextView timeView;
        TextView nameView;
        Switch switchView;
    }
}
