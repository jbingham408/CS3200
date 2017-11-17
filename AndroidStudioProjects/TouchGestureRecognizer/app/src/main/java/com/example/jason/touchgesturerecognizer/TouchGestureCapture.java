package com.example.jason.touchgesturerecognizer;

/*
    Jason Bingham
    CS3200
    Assignment 7
    Gesture Recognition

    Note:
        Some of the code was proved in the assignment pdf file
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

//This is the main activity java code
public class TouchGestureCapture extends Activity implements View.OnClickListener {

    //stores the view for the drawing area of the view
    TouchGestureCaptureView touchCaptureView;
    //stores the gestures that are saved
    TouchGestureDictionary gestureDictionary;
    //stores the buttons on the screen
    Button btnAddGesture;
    Button btnEraseGesture;
    Button btnRecognizeGesture;
    //stores the text area of the screen
    EditText gestureName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_gesture_capture);

        touchCaptureView = (TouchGestureCaptureView) this.findViewById(R.id.gestureView);
        touchCaptureView.setDrawingCacheEnabled(true);
        btnAddGesture = (Button) this.findViewById(R.id.addBtn);
        btnAddGesture.setOnClickListener(this);
        btnEraseGesture = (Button) this.findViewById(R.id.eraseBtn);
        btnEraseGesture.setOnClickListener(this);
        btnRecognizeGesture = (Button) this.findViewById(R.id.testBtn);
        btnRecognizeGesture.setOnClickListener(this);
        gestureName = (EditText) this.findViewById(R.id.gestureText);
        gestureDictionary = new TouchGestureDictionary();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_touch_gesture_capture, menu);
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

    @Override
    protected void onDestroy(){

        super.onDestroy();
    }

    @Override
    public void onLowMemory(){

        super.onLowMemory();
    }

    @Override
    protected void onPause(){

        super.onPause();
    }

    @Override
    protected void onStart(){
        super.onStart();
        if ( touchCaptureView != null ) {
            touchCaptureView.clearCapturedMotionEvents();
        }
        else {
            touchCaptureView = (TouchGestureCaptureView) this.findViewById(R.id.gestureView);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        //checks which button was pressed
        switch(v.getId()) {
            //add button pressed
            //saves the gesture to te hashmap
            case R.id.addBtn:
                gestureDictionary.saveTouchGesture(gestureName.getText().toString(), touchCaptureView.getCapturedMotionEvents());
                Log.i("gesture", touchCaptureView.getCapturedMotionEvents().toString());
                Log.i("GESTURE ADDED", Integer.toString(gestureDictionary.getSize()));
                break;
            //erase button pressed
            //clears the draw area and the text area
            case R.id.eraseBtn:
                touchCaptureView.clearGesture();
                gestureName.setText("");
                break;
            //test button pressed
            //takes the current gesture and compares it to those saved in the hashmap
            case R.id.testBtn:
                ArrayList<String> results;
                results = gestureDictionary.zoneVectorMatch(touchCaptureView.getCapturedMotionEvents());
                printToast(results);
                break;
        }
    }

    //function that shows the toast that contains the results of the compare gestures
    public void printToast(ArrayList<String> result){
        Context context = getApplicationContext();
        String toastText = result.get(0) + ", " + result.get(1);
        Toast toast = Toast.makeText(context, toastText, Toast.LENGTH_LONG);
        toast.show();
    }
}
