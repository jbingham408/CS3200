package com.example.jason.mothballstepone;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

// This class is coupled with the GameplayView
public class GameActivity extends Activity implements Runnable {

    protected static boolean msRrunning, msPaused;
    protected static final int FPS = 45;
    private static final long NANO_PER_FRAME = 1000/FPS;
    protected static int msScreePixWidth, msScreenPixHeight;
    protected static float msScreenYDPI; // The exact physical pixels per inch of the screen in the Y dimension.

    private GameView mGameView = null;

    protected Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        DisplayMetrics dMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
        msScreenPixHeight = dMetrics.heightPixels;
        msScreePixWidth = dMetrics.widthPixels;
        msScreenYDPI = dMetrics.ydpi;

        mGameView = (GameView) findViewById(R.id.gamepanel);
        mGameView.setScreenParams(msScreePixWidth, msScreenPixHeight, msScreenYDPI);
        mGameView.initialize();

        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    protected void onRestart() {
        msPaused = false;
        //mGameView.mIsEndFrame =false;
        super.onRestart();
    }

    @Override
    protected void onStart() {
        msPaused =false;
        //mGameView.mIsEndFrame =false;
        super.onStart();
    }

    @Override
    protected void onResume() {
        msPaused =false;
        //mGameView.mIsEndFrame =false;
        super.onResume();
    }

    @Override
    protected void onPause() {
        msPaused =true;
        super.onPause();
    }

    @Override
    protected void onStop() {
        msPaused =true;
        super.onStop();
    }

    @Override
    public void run() {
        msRrunning =true;
        msPaused =false;

        long start;
        long elapsed;
        long wait;

        //game loop
        while(msRrunning) {

            if(msPaused){
                try{
                    Thread.sleep(NANO_PER_FRAME);
                }catch(Exception e){
                    e.printStackTrace();
                }
                continue;
            }

            start = System.nanoTime();
            mGameView.update();
            mGameView.postInvalidate();

            elapsed = System.nanoTime() - start;

            wait = NANO_PER_FRAME - elapsed / 1000000;
            if(wait < 0) wait = 1;

            try {
                Thread.sleep(wait);
            }
            catch(Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    @Override
    public void onBackPressed(){
        msRrunning =false;
        super.onBackPressed();
    }

}

