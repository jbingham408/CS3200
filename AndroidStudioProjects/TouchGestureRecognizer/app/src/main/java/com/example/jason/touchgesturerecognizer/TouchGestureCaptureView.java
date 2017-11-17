package com.example.jason.touchgesturerecognizer;

/*
    Jason Bingham
    CS3200
    Assignment 7
    Gesture Recognition

    Note:
        Some of the code was proved in the assignment pdf file
 */


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

public class TouchGestureCaptureView extends View {

    Paint viewBackground;
    Paint line;

    //
    private ArrayList<MotionEvent> captureMotion;
    private float pathX;
    private float pathY;
    private Path gesturePath;


    public TouchGestureCaptureView(Context context, AttributeSet attr){
        super(context, attr);

        viewBackground = new Paint();
        viewBackground.setColor(Color.GRAY);
        viewBackground.setAntiAlias(true);

        line = new Paint();
        line.setColor(Color.WHITE);
        line.setAntiAlias(true);
        line.setDither(true);
        line.setStyle(Paint.Style.STROKE);
        line.setStrokeJoin(Paint.Join.ROUND);
        line.setStrokeCap(Paint.Cap.ROUND);
        line.setStrokeWidth(5);

        gesturePath = new Path();
        captureMotion = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int width, int height, int old_width, int old_height){
        super.onSizeChanged(width, height, old_width, old_height);
    }

    //function that draws the line on the screen
    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        final int width = canvas.getWidth();
        final int height = canvas.getHeight();
        canvas.drawRect(0, 0, width, height, viewBackground);
        canvas.drawPath(gesturePath, line);
    }

    // function that is called when the gesture is started to be drawn
    void startTouchGesture(float x, float y){
        clearCapturedMotionEvents();
        gesturePath.reset();
        gesturePath.moveTo(x, y);
        pathX = x;
        pathY = y;
    }

    //function that gets the x and y as the gesture is being drawn
    void continueTouchGesture(float x, float y){
        final float dx = Math.abs(x - pathX);
        final float dy = Math.abs(y - pathY);

        if ( dx >= 2 && dy >= 2 ) {
            gesturePath.quadTo(pathX, pathY, (pathX + x)/2, (pathY + y)/2);
            pathX = x;
            pathY = y;
        }
    }

    // function called when the gesture is finished being drawn
    void stopTouchGesture(){
        gesturePath.lineTo(pathX, pathY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //gets the x and y of the current event
        final float x = event.getX();
        final float y = event.getY();
        //checks what the current action of the event is
        switch( event.getAction() ) {
            //current action is ACTION_DOWN, the gesture has started being drawn
            case MotionEvent.ACTION_DOWN:
                startTouchGesture(x, y);
                //Log.i("start x", Float.toString(x));
                //Log.i("start y", Float.toString(y));
                captureMotion.add(MotionEvent.obtain(event));
                invalidate();
                break;
            //current action is ACTION_MOVE,the gesture is in the middle of being drawn
            case MotionEvent.ACTION_MOVE:
                continueTouchGesture(x, y);
               // Log.i("move x", Float.toString(event.getX()));
               // Log.i("move y", Float.toString(event.getY()));
               // Log.i("action", Integer.toString(event.getAction()));
                captureMotion.add(MotionEvent.obtain(event));
                invalidate();
                break;
            //current action is ACTION_UP, the gesture is finished being drawn
            case MotionEvent.ACTION_UP:
                stopTouchGesture();
                //Log.i("end x", Float.toString(x));
                //Log.i("end y", Float.toString(y));
                captureMotion.add(MotionEvent.obtain(event));
                invalidate();
                break;
        }
        return true;
    }

    //clears the arraylist of the MotionEvents for the gesture that was drawn
    public void clearCapturedMotionEvents(){

        captureMotion.clear();
    }

    //clears the screen of the gesture that was drawn
    public void clearGesture(){
        Log.d("clear", "clear gesture");
        gesturePath.reset();
        invalidate();
    }

    //returns the arraylist of thge MotionEvents for the gesture that was drawn
    public ArrayList<MotionEvent> getCapturedMotionEvents(){

        return captureMotion;
    }
}
