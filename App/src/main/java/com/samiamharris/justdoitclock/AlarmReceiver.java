package com.samiamharris.justdoitclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by samharris on 12/19/13.
 */
public class AlarmReceiver extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DELTA");
        //Acquire the lock
        wl.acquire();

        Bundle b = intent.getExtras();
        String whichAlarm = b.getString(AlarmData.INTENT_KEY);

        //find the alarm we got
        HashMap<String,AlarmData> alarms = Storage.getInstance().getMyData(context);
        AlarmData receivedAlarm = alarms.get(whichAlarm);

        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        boolean noRepeat;
        //check if no repeat was hit

        if (receivedAlarm.monday || receivedAlarm.tuesday || receivedAlarm.wednesday || receivedAlarm.thursday
                || receivedAlarm.friday || receivedAlarm.saturday || receivedAlarm.sunday) {

            noRepeat = false;
        } else {
            noRepeat = true;
        }

        boolean[] days = new boolean[8];

        days[0] = noRepeat;
        days[1] = receivedAlarm.sunday;
        days[2] = receivedAlarm.monday;
        days[3] = receivedAlarm.tuesday;
        days[4] = receivedAlarm.wednesday;
        days[5] = receivedAlarm.thursday;
        days[6] = receivedAlarm.friday;
        days[7] = receivedAlarm.saturday;

            if(receivedAlarm != null && noRepeat || receivedAlarm!= null && days[dayOfWeek]){

                try
                {

                    Intent alarmIntent = new Intent( context, ClockActivity.class );
                    alarmIntent.addFlags(Intent.FLAG_FROM_BACKGROUND);
                    alarmIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    alarmIntent.putExtra("Alarm Name", receivedAlarm.getmName());
                    context.startActivity(alarmIntent);
                }
                catch( Exception e ) {

                }
            }


        //Release the lock
        wl.release();
    }
}
