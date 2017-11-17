package com.example.jason.touchgesturerecognizer;

/*
    Jason Bingham
    CS3200
    Assignment 7
    Gesture Recognition

    Note:
        Some of the code was proved in the assignment pdf file
 */

import android.util.Log;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class TouchGestureDictionary {

    private HashMap<String, ArrayList<MotionEvent>> gestures = null;
    private HashMap<String, ArrayList<Integer>> gestureScores = null;
    private float xmax;
    private float xmin;
    private float ymax;
    private float ymin;
    private float ySection;
    private float xSection;

    public TouchGestureDictionary() {

        gestures = new HashMap<>();
        gestureScores = new HashMap<>();
    }

    public void saveTouchGesture(String name, ArrayList<MotionEvent> motionEvents) {
        gestures.put(name, motionEvents);
        gestureScores.put(name, findGestureScores(motionEvents));
    }

    // function to determine gesture using zones over the image
    // There are six zones the images will be split into.
    public ArrayList<String> zoneVectorMatch(ArrayList<MotionEvent> motionEvents) {

        //stores the arraylist for the values of the six zones scores
        ArrayList<Integer> testGestureScores = findGestureScores(motionEvents);
        //stores the scores for the gestures that are saved in the map
        ArrayList<Integer> storedGestureScores;
        // holds the highest score determined from comparing gestures
        int highestCompareScore = 0;
        // stores the name of the gesture that best fits what was drawn
        String highestCompareScoreName = "";

        //loops through the map getting the keys of the map which are the names of the saved gestures
        for(String key: gestures.keySet()){
            Log.i("Key", key);
            // gets the scores of the zones for the current gesture that is being compared
            storedGestureScores = gestureScores.get(key);

            // stores a temp score to help determine which saved gesture best matches the tested gesture
            int tempScore = 0;
            // loops six times to get the scores for each zone
            for(int i = 0; i < gestureScores.get(key).size(); ++i) {
                Log.i("testGestureScores", Integer.toString(storedGestureScores.get(i)) + " " + Integer.toString(testGestureScores.get(i)));
                // compares each zone of the stored gesture with the score of that zone of the gesture being tested
                // The lower the difference, the better match.
                //The overall score is determined by how far apart the scores are.
                switch(storedGestureScores.get(i) - testGestureScores.get(i)){
                    case 0:
                        Log.i("Perfect Score", "10");
                        tempScore += 10;
                        break;
                    case 1:
                        Log.i("Score", "9");
                        tempScore += 9;
                        break;
                    case 2:
                        Log.i("Score", "8");
                        tempScore += 8;
                        break;
                    case 3:
                        Log.i("Score", "7");
                        tempScore += 7;
                        break;
                    case 4:
                        Log.i("Score", "6");
                        tempScore += 6;
                        break;
                    case 5:
                        Log.i("Score", "5");
                        tempScore += 5;
                        break;
                    case 6:
                        Log.i("Score", "4");
                        tempScore += 4;
                        break;
                    case 7:
                        Log.i("Score", "3");
                        tempScore += 3;
                        break;
                    case 8:
                        Log.i("Score", "2");
                        tempScore += 2;
                        break;
                    case 9:
                        Log.i("Score", "1");
                        tempScore += 1;
                        break;
                }
            }
            // determines if the current saved gesture is a better match.
            if(tempScore > highestCompareScore){
                highestCompareScore = tempScore;
                highestCompareScoreName = key;
            }
        }
        // stores the results to be printed in the toast
        ArrayList<String> results = new ArrayList<>();
        results.add(Float.toString(highestCompareScore));
        results.add(highestCompareScoreName);

        return results;
    }

    //I never got around to finishing this function
    //Its another way of determining what gesture was drawn
    public double euclidDistMatch(ArrayList<MotionEvent> motionEvents) {

        return 0;
    }

    public int getSize() {
        return gestures.size();
    }

    // This function creates the zones to get the score for the gesture
    // The zones are laided out in a 2 X 3 grid. The grids are labeled left to right
    // So the first row is zone 1 and zone 2.
    public ArrayList<Integer> findGestureScores(ArrayList<MotionEvent> gesture){
        Log.i("motion", gesture.toString());
        //stores the scores for each zone
        ArrayList<Integer> scores = new ArrayList<>();
        // these six ints are used to determine the score for each zone
        int zoneOneScore = 0;
        int zoneTwoScore = 0;
        int zoneThreeScore = 0;
        int zoneFourScore = 0;
        int zoneFiveScore = 0;
        int zoneSixScore = 0;
        float x;
        float y;

        MotionEvent tempEvent;
        //this is a function call to determine the size of the image that is being looked at
        findGestureBorders(gesture);

        //loops through all the events in the gesture
        for(int i = 0; i < gesture.size(); ++i) {
            //gets the current event being looked at
            tempEvent = MotionEvent.obtain(gesture.get(i));
            //stores that events x and y coordinates
            x = tempEvent.getX();
            y = tempEvent.getY();

            //these statements determine in what zones the current event coordinates are located
            //this if statement checks if the coordinates are in the first column of the zone grid
            if (x >= xmin && x < xmin + xSection) {
                //this is for the first row of the grid
                if (y <= ymax && y > ymax - ySection) {
                    zoneOneScore++;
                //this is for the second row of the grid
                } else if (y <= ymax - ySection && y > ymin + ySection) {
                    zoneThreeScore++;
                //this is for the third row of the grid
                } else if (y <= ymin + ySection && y >= ymin) {
                    zoneFiveScore++;
                }
            //this is for the second column of the grid
            } else if (x >= xmin + xSection && x <= xmax) {
                //first row
                if (y <= ymax && y > ymax - ySection) {
                    zoneTwoScore++;
                //second row
                } else if (y <= ymax - ySection && y > ymin + ySection) {
                    zoneFourScore++;
                //thrid row
                } else if (y <= ymin + ySection && y >= ymin) {
                    zoneSixScore++;
                }
            }
        }
        //Log.i("zone 1", Integer.toString(zoneOneScore));
        //Log.i("zone 2", Integer.toString(zoneTwoScore));
        //Log.i("zone 3", Integer.toString(zoneThreeScore));
        //Log.i("zone 4", Integer.toString(zoneFourScore));
        //Log.i("zone 5", Integer.toString(zoneFiveScore));
        //Log.i("zone 6", Integer.toString(zoneSixScore));

        //stores all the resulting scores in the arraylist
        scores.add(zoneOneScore);
        scores.add(zoneTwoScore);
        scores.add(zoneThreeScore);
        scores.add(zoneFourScore);
        scores.add(zoneFiveScore);
        scores.add(zoneSixScore);

        //returns the arraylist
        return scores;
    }

    //This function is used to find the size of the image to help determine the size of the zones
    public void findGestureBorders(ArrayList<MotionEvent> motionEvents){
        MotionEvent tempEvent;
        //loops through the event checking its coordinates
        for(int i = 0; i < motionEvents.size(); ++i) {
            tempEvent = MotionEvent.obtain(motionEvents.get(i));
            //checks if it is the first event being checked and sets an initial min and max values for x and y
            if (i == 0) {
                xmax = xmin = tempEvent.getX();
                ymax = ymin = tempEvent.getY();
            }
            else{
                //checks if the event x coordinate is less than the xmin or higher than the xmax
                if(tempEvent.getX() < xmin)
                    xmin = tempEvent.getX();
                else if(tempEvent.getX() > xmax)
                    xmax = tempEvent.getX();
                //checks if the event y coordinate is less than the ymin or higher than the ymax
                if(tempEvent.getY() < ymin)
                    ymin = tempEvent.getY();
                else if(tempEvent.getY() > ymax)
                    ymax = tempEvent.getY();
            }
        }
        // adds a little border around the gesture
        // the min values don't get as bigger of border so that the gestures won't be in the exact middle of the image
        xmin -= 50;
        ymin -= 50;
        xmax += 100;
        ymax += 100;
        //xSection and ySection are used to help with creating the boundries of the zones
        //they get the distance between the min and max and for x its divided by 2 to get 2 columns and y is divided by 3 to get 3 rows
        xSection = (xmax - xmin) / 2;
        ySection = (ymax - ymin) / 3;
    }

}
