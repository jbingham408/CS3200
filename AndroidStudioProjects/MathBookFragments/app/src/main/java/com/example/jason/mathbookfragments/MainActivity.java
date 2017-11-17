package com.example.jason.mathbookfragments;

/*
    Jason Bingham
    CS3200 Exam 2
 */


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    static Resources res;
    static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        FragmentManager.enableDebugLogging(true);
        setContentView(R.layout.fragments);
        res = getResources();
        fragmentManager = getFragmentManager();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    static boolean isInLandscapeOrientation() {
        return res.getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    void displayBookText(int bookSelection){
        if(isInLandscapeOrientation()){
            MathBookDisplayFragment bookInfoFragment = (MathBookDisplayFragment) fragmentManager.findFragmentById(R.id.bookDisplay);
            if(bookInfoFragment == null){
                bookInfoFragment = MathBookDisplayFragment.newInstance(bookSelection);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.bookDisplay, bookInfoFragment);
                fragmentTransaction.commit();
            }
        }
        else{
            Intent i = new Intent();
            i.setClass(this, MathBookDisplayFragment.class);
            this.startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
