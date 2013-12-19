package com.samiamharris.justdoitclock;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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

    public static void add(Context context, AlarmData addedData) {

        ArrayList<AlarmData> savedAlarmArray;

        try{
        savedAlarmArray = getMyDataArray(context);

            if(savedAlarmArray == null){
                savedAlarmArray = new ArrayList<AlarmData>();
            }
            savedAlarmArray.add(addedData);
            saveMyDataArray(context, savedAlarmArray);



        }catch(Exception e){

        }

    }

    public static void remove(Context context, AlarmData removedData) {

        ArrayList<AlarmData> savedAlarmArray;

        try{
            savedAlarmArray = getMyDataArray(context);

            if(savedAlarmArray==null) {

                return;
            }
                savedAlarmArray.remove(removedData);
                saveMyDataArray(context, savedAlarmArray);

        }catch(Exception e){

        }
    }
    //
    // Serialization here
    //

    static String DATA_FILE = "data_file_single";
    static String DATA_FILE_ARRAY = "data_file_array";



    private static boolean saveMyDataArray(Context context, ArrayList<AlarmData> myData) {
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

    public static ArrayList<AlarmData> getMyDataArray(Context context) {
        try {
            FileInputStream fis = context.openFileInput(DATA_FILE_ARRAY);
            ObjectInputStream is = new ObjectInputStream(fis);
            Object readObject = is.readObject();
            is.close();

            if(readObject != null && readObject instanceof AlarmData) {
                return (ArrayList<AlarmData>) readObject;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
