/*
    Jason Bingham
    Assignment 1 CS 3200
    Hello KitKat
 */

package com.example.jason.hellokitkat;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by jason on 9/10/15.
 */
public class helloKitKat44 extends Activity{

    Button myKitKatButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitkat44);
        myKitKatButton = (Button) this.findViewById(R.id.kitkat_button);
        myKitKatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), R.string.kitkat_toast, Toast.LENGTH_SHORT).show();
            }
        });
    }
}