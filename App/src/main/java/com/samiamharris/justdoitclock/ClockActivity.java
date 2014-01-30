package com.samiamharris.justdoitclock;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import java.util.ArrayList;

public class ClockActivity extends Activity implements ClockFragment.OnAddEditListener, EditFragment.OnListItemClickListener, AddFragment.OnSaveListener, PhraseFragment.onAlarmListener {


    // What will this class need to do?
    // go directly to the clock fragment on start up
    // communicate between fragments
    // pass data between fragments

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clock_activity);

        ClockFragment clockFragment = new ClockFragment();

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, clockFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.clock, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddSelected() {

        AddFragment addFragment = new AddFragment();

        getFragmentManager().beginTransaction()
                .replace(R.id.container, addFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onEditSelected() {

        EditFragment editFragment = new EditFragment();

        getFragmentManager().beginTransaction()
                .replace(R.id.container, editFragment )
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onEditAlarmSelected(String name) {

        AddFragment addFragment = new AddFragment();

        Bundle args = new Bundle();
        args.putString("name", name);
        addFragment.setArguments(args);

        getFragmentManager().beginTransaction()
                .replace(R.id.container, addFragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onSaveSelected() {

        ClockFragment clockFragment = new ClockFragment();

        getFragmentManager().beginTransaction()
                .replace(R.id.container, clockFragment)
                .addToBackStack(null)
                .commit();

    }


    @Override
    public void onPhraseTyped() {

        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        ClockFragment clockFragment = new ClockFragment();

        getFragmentManager().beginTransaction()
                .replace(R.id.container, clockFragment)
                .commit();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        PhraseFragment phraseFragment = new PhraseFragment();

        String activeName = intent.getStringExtra("Alarm Name");

        Bundle args = new Bundle();
        args.putString("activeName", activeName);
        phraseFragment.setArguments(args);

        getFragmentManager().beginTransaction()
                .replace(R.id.container, phraseFragment)
                .addToBackStack("phrase")
                .commit();

    }

}
