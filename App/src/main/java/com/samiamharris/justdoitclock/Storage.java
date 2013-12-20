package com.samiamharris.justdoitclock;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by samharris on 12/17/13.
 */
public class Storage {


    //
    // Singleton pattern here:
    //
    private static Storage storageRef;
    private Storage(){

    }
    public static Storage getInstance()
    {
        if (storageRef == null){
            storageRef = new Storage();
        }
        return storageRef;
    }

    //
    // Serialization here
    //

    static String DATA_FILE_ARRAY = "data_file_array";


    //this method is private so we can control what gets saved.
    private static boolean saveMyData(Context context, HashMap<String,AlarmData> myData) {
        try {
            FileOutputStream fos = context.openFileOutput(DATA_FILE_ARRAY, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(myData);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    //used to add another alarm to the file
    public static void add(Context context , AlarmData customAlarm){

        HashMap<String,AlarmData> myData;

        try{
            myData = getMyData(context);
            //if it doesnt exist, create one.
            if(myData == null){
                myData = new HashMap<String, AlarmData>();
            }

            //This try catch is to replace the old alarm name if needed.
            try{
                myData.put(customAlarm.mName, customAlarm);
            }catch (Exception e){
                myData.remove(customAlarm.mName);
                myData.put(customAlarm.mName,customAlarm);
            }

            saveMyData(context,myData);

        }catch(Exception e){

        }
    }


    //will remove an alarm from the file
    public static void remove(Context context , AlarmData customAlarm){
        HashMap<String,AlarmData> myData;
        try{
            myData = getMyData(context);
            //if it doesnt exist, stop one.
            if(myData == null){
                return;
            }
            //remember to turn it off
            customAlarm.turnOff(context);
            myData.remove(customAlarm);
            saveMyData(context,myData);
        }catch(Exception e){

        }
    }

    //used to get a list of all current alarms.
    public static HashMap<String,AlarmData> getMyData(Context context) {
        try {
            FileInputStream fis = context.openFileInput(DATA_FILE_ARRAY);
            ObjectInputStream is = new ObjectInputStream(fis);
            Object readObject = is.readObject();
            is.close();

            if(readObject != null && readObject instanceof HashMap) {
                return (HashMap<String,AlarmData>) readObject;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
