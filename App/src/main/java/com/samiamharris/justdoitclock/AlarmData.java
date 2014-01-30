package com.samiamharris.justdoitclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by samharris on 12/16/13.
 */
public class AlarmData implements Serializable{

    //Data class with the settings of an alarm
    public static String INTENT_KEY = "name";

    public String mName;
    public int mHour;
    public int mMinute;
    public String mPhrase;
    public int mVolume;

    public boolean mSwitch;

    public boolean monday;
    public boolean tuesday;
    public boolean wednesday;
    public boolean thursday;
    public boolean friday;
    public boolean saturday;
    public boolean sunday;

    public int mAlarmId;

    Calendar alarmEvent;


    public AlarmData() {

    }

    public AlarmData(String mName, int alarmId, int mHour, int mMinute, String mPhrase, int mVolume, boolean mSwitch, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, Calendar alarmEvent) {
        this.mName = mName;
        this.mHour = mHour;
        this.mMinute = mMinute;
        this.mPhrase = mPhrase;
        this.mVolume = mVolume;
        this.mSwitch = mSwitch;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.alarmEvent = alarmEvent;
        this.mAlarmId = alarmId;

    }

    //changed this from static so that the alarmevent can be handled
    public Calendar convertTimeToEvent(int hour,int minute,int day){

        Calendar newEvent = Calendar.getInstance();
        newEvent.setTimeInMillis(System.currentTimeMillis());
        //zero the seconds so it start right when the clock ticks over
        newEvent.set(Calendar.SECOND,0);
        newEvent.set(Calendar.DAY_OF_WEEK, day);
        newEvent.set(Calendar.HOUR_OF_DAY, hour);
        newEvent.set(Calendar.MINUTE, minute);
        alarmEvent = newEvent;
        return newEvent;

    }


//    public void singleTurnOn(Context c){
//        Intent intent = new Intent(c, AlarmReceiver.class);
//        //this extra will be used so we can later find this specific alarm.
//        intent.putExtra(INTENT_KEY, mName);
//        PendingIntent alarmIntent = PendingIntent.getBroadcast(c, mAlarmId, intent, 0);
//        AlarmManager alarmMgr = (AlarmManager)c.getSystemService(Context.ALARM_SERVICE);
//        alarmMgr.set(AlarmManager.RTC_WAKEUP,alarmEvent.getTimeInMillis(),alarmIntent);
//
//    }


    public void turnOn(Context c) {

        Intent intent = new Intent(c, AlarmReceiver.class);
        //this extra will be used so we can later find this specific alarm.
        intent.putExtra(INTENT_KEY, mName);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(c, mAlarmId, intent, 0);
        AlarmManager alarmMgr = (AlarmManager)c.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, alarmEvent.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

    }

    public void turnOff(Context c){
        Intent intent = new Intent(c, AlarmReceiver.class);
        AlarmManager alarmMgr = (AlarmManager)c.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(c, mAlarmId, intent, 0);
        if (alarmMgr!= null && alarmIntent != null) {
            alarmMgr.cancel(alarmIntent);
        }
    }

    public void performAlarm(Context c){
        //play sound
        final MediaPlayer mp = MediaPlayer.create(c, R.raw.red_alert);
        mp.start();
        Log.e("DELTA", mName + " alarm receieved!");
        Toast.makeText(c, "ALERT:" + mName, Toast.LENGTH_LONG).show();

    }


    /**
     * Always treat de-serialization as a full-blown constructor, by validating
     * the final state of the de-serialized object.
     */
    private void readObject(ObjectInputStream aInputStream)
            throws ClassNotFoundException, IOException {
        // always perform the default de-serialization first
        aInputStream.defaultReadObject();
    }

    /**
     * This is the default implementation of writeObject.
     */
    private void writeObject(ObjectOutputStream aOutputStream)
            throws IOException {
        // perform the default serialization for all non-transient, non-static
        // fields
        aOutputStream.defaultWriteObject();
    }



    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmHour() {
        return mHour;
    }

    public void setmHour(int mHour) {
        this.mHour = mHour;
    }

    public int getmMinute() {
        return mMinute;
    }

    public void setmMinute(int mMinute) {
        this.mMinute = mMinute;
    }

    public String getmPhrase() {
        return mPhrase;
    }

    public void setmPhrase(String mPhrase) {
        this.mPhrase = mPhrase;
    }

    public int getmVolume() {
        return mVolume;
    }

    public void setmVolume(int mVolume) {
        this.mVolume = mVolume;
    }

    public int getMAlarmId() { return mAlarmId; }

    public void setmAlarmId(int mAlarmId) {this.mAlarmId = mAlarmId;}

    public boolean ismSwitch() {
        return mSwitch;
    }

    public void setmSwitch(boolean mSwitch) {
        this.mSwitch = mSwitch;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }
}
