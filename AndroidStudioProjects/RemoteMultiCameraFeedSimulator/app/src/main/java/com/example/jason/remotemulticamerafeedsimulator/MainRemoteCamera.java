package com.example.jason.remotemulticamerafeedsimulator;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainRemoteCamera extends Activity implements View.OnClickListener {

    int imageOneCounter = 1;

    Button btnCameraOne;
    Button btnCameratwo;
    ImageView imageCameraOne;
    ImageView imageCameraTwo;
    Thread threadOne = null;
    boolean threadOneRunning = false;
    Handler camOneHandler = new Handler();
    Handler camTwoHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_remote_camera);

        btnCameraOne = (Button) findViewById(R.id.btnCameraOne);
        btnCameraOne.setOnClickListener(this);
        btnCameratwo = (Button) findViewById(R.id.btnCameraTwo);
        btnCameratwo.setOnClickListener(this);
        imageCameraOne = (ImageView) findViewById(R.id.cameraOne);
        imageCameraTwo = (ImageView) findViewById(R.id.cameraTwo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_remote_camera, menu);
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
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btnCameraOne:
                if(threadOneRunning){
                    Log.d("kill", "Kill thread");
                    threadOne.getState();
                    threadOne.interrupt();
                    threadOne = null;
                    threadOneRunning = false;
                }
                else {
                    threadOne = new Thread() {
                        public void run() {
                            cameraOneRun();
                        }
                    };
                    threadOne.start();
                    threadOneRunning = true;
                }
                break;
            case R.id.btnCameraTwo:

                break;
        }

    }

    public void cameraOneRun(){
        while(!Thread.currentThread().isInterrupted()) {
            Log.d("check", "In cameraonerun");
            try {
                camOneHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (imageOneCounter == 1) {
                            imageCameraOne.setImageResource(R.drawable.img01);
                            ++imageOneCounter;
                        } else if (imageOneCounter == 2) {
                            imageCameraOne.setImageResource(R.drawable.img02);
                            ++imageOneCounter;
                        } else if (imageOneCounter == 3) {
                            imageCameraOne.setImageResource(R.drawable.img03);
                            imageOneCounter = 1;
                        }

                    }
                });

            } catch (Exception e) {

            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void cameraTwoRun(){

    }
}

