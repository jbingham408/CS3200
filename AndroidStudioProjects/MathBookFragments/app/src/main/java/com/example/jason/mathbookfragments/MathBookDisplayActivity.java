package com.example.jason.mathbookfragments;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * Created by jason on 11/12/15.
 */
public class MathBookDisplayActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }
    }
}
