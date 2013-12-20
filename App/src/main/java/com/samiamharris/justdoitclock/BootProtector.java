package com.samiamharris.justdoitclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;

/**
 * Created by samharris on 12/19/13.
 */
public class BootProtector extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //
        //  This BroadcastReceiver will specifically look for when we've booted up
        // so it can re-instantiate any and all alarms.
        //
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            HashMap<String,AlarmData> alarms = Storage.getInstance().getMyData(context);
            for(AlarmData alarm : alarms.values()){
                alarm.turnOn(context);
            }

        }
    }
}
