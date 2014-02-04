package com.samiamharris.justdoitclock;

import android.app.Activity;
import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by samharris on 12/16/13.
 */
public class PhraseFragment extends Fragment{

    //what will this class do?
    //triggered when the alarm goes off
    //play alarm noise
    //get phrase info when triggered
    //fill text view with the phrase text
    //edit text that will need to match text view
    //recognize when the phrase matches
    //goes back to the clock fragment when the phrase is typed in correctly

    onAlarmListener alarmPerformedCallback;
    AlarmData currentAlarm;
    static MediaPlayer mp;
    TextView preText;
    EditText postText;


    public interface onAlarmListener {

        public void onPhraseTyped();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            alarmPerformedCallback = (onAlarmListener) activity;
        }
        //if this type of exception pops up
        catch (ClassCastException e){
            //tell you in the log why it failed. That this interface was not implemented
            throw new ClassCastException(activity.toString() +
                    "must implement OnAlarmListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View phraseView = inflater.inflate(R.layout.phrase_fragment, container,false);
        return phraseView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        preText = (TextView) getActivity().findViewById(R.id.pre_phrase);
        postText = (EditText) getActivity().findViewById(R.id.post_phrase);
        Bundle args = getArguments();
        if (args != null) {
            currentAlarm = Storage.getInstance().getDataForName(getActivity(), args.getString("activeName"));

            preText.setText(currentAlarm.getmPhrase());


            mp = MediaPlayer.create(getActivity(), R.raw.mystery);
            mp.setVolume(0, currentAlarm.getmVolume());

            if(!mp.isPlaying()){
                mp.start();
            }

            postText.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String enteredText = s.toString();
                    if (enteredText.equals(preText.getText())) {
                        mp.stop();
                        alarmPerformedCallback.onPhraseTyped();


                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }

            });

        }
    }



}
