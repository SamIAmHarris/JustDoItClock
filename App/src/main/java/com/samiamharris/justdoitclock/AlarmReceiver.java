package com.samiamharris.justdoitclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;

import java.util.HashMap;

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
        if(receivedAlarm != null){
            receivedAlarm.performAlarm(context);
            //alarm is complete, we might want to remove it- depending on how we have things setup
            Storage.remove(context,receivedAlarm);
        }

        //Release the lock
        wl.release();
    }
}
