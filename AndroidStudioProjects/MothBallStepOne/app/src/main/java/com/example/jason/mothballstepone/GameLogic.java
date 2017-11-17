package com.example.jason.mothballstepone;

    /*
    Jason Bingham
    CS 3200
    Moth Ball game
    Description: Implement the handleCollisions function and updatePlayerScore function

    Note:  Most of the code was given to us. The only code that is mine is the code
            for the two functions in the description above
     */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Vladimir Kulyukin on 8/25/2015.
 */
public class GameLogic {

    private HorizontalHurdle mHorizontalHurdles[]   = null;
    private GameParameters mGameParameters          = null;
    private int mNumStrikesLeft                     = 0;
    private boolean mGameOver                       = false;
    private int mPlayerScore                        = 0;
    private float mBallCenterX                      = 0;
    private float mBallCenterY                      = 0;
    private boolean mPlayerBonusAvailable           = false;
    private int mHurdleFront                        = 0;
    private boolean mCreateMothWithExtraLife        = false;
    private GameView mGameView                  = null;
    private Context mContext                        = null;

    final static float  BALL_CENTER_X_SCALER       = 0.5f;
    final static float  BALL_CENTER_Y_SCALER       = 0.7975f;


    public GameLogic(GameParameters gp, GameView gv, Context context) {
        mGameParameters = gp;
        mGameView = gv;
        mContext = context;

        mHorizontalHurdles = new HorizontalHurdle[GameParameters.NUM_HOR_HURDLES];
        mNumStrikesLeft = GameParameters.PERMITTED_NUM_STRIKES;
        mGameOver = false;
        mPlayerBonusAvailable = false;
        mCreateMothWithExtraLife = false;
        mHorizontalHurdles = new HorizontalHurdle[GameParameters.NUM_HOR_HURDLES];

        for(int i=0; i < GameParameters.NUM_HOR_HURDLES; ++i) {
            mHorizontalHurdles[i] = null;
        }

        mPlayerScore = 0;
        mBallCenterX = 0;
        mBallCenterY = 0;
        mHurdleFront = 0;
    }

    HorizontalHurdle[] getHorizontalHurdles() {
        return mHorizontalHurdles;
    }

    boolean noHorizontalHurdlesOnTopOfScreen() {
        for(int i = 0; i < GameParameters.NUM_HOR_HURDLES; ++i ) {
            if ( mHorizontalHurdles[i] != null ) {
                if ( mHorizontalHurdles[i].getTopY() < Math.sqrt(mGameParameters.getDY()) * mGameParameters.getMarginSize() * 8.5 ) {
                    return false;
                }
            }
        }
        return true;
    }

    int getNumStrikesLeft()       { return mNumStrikesLeft; }
    void incrementNumStrikesLeft() { mNumStrikesLeft++; }
    void decrementNumStrikesLeft() { mNumStrikesLeft--; }

    boolean isGameOver() { return mGameOver; }
    void finishGame() { mGameOver = true; }

    void possiblyDecreaseImmuneBufferSize() {
        final double immuneBufferSize = mGameParameters.getImmuneBufferSize();
        if ( immuneBufferSize > 0) {
            mGameParameters.setImmuneBufferSize(-mGameParameters.getDY());
        }
    }

    void setIthHorizontalHurdle(int i, HorizontalHurdle hh) {
        mHorizontalHurdles[i] = hh;
    }

    void removeHorizontalHurdle(HorizontalHurdle h) {
        ArrayList<HorizontalHurdle > hurdleList = new ArrayList<>(Arrays.asList(mHorizontalHurdles));
        hurdleList.remove(h);
        mHorizontalHurdles = hurdleList.toArray(mHorizontalHurdles);
    }

    void possiblyIncreaseHorizontalHurdleDownwardSpeed() {
        final double mDY = mGameParameters.getDY();
        if ( mDY < mGameParameters.getMaxSpeed() ) {
            mGameParameters.setDY(mDY + mGameParameters.getStartSpeed() * GameParameters.DY_INCREMENT_SCALER);
        }
    }

    void createHorizontalHurdle() {
        boolean [] hurdle_blocks = new boolean[GameParameters.NUM_BLOCKS_IN_HURDLE];
        for(int i=0; i < GameParameters.NUM_BLOCKS_IN_HURDLE; ++i) { hurdle_blocks[i] = false; }

        int i = 0;
        Random rand = new Random(System.currentTimeMillis());
        while ( i < mGameParameters.getNumOfBlocksInHurdle() ) {
            int t = rand.nextInt(GameParameters.NUM_BLOCKS_IN_HURDLE);
            if( !hurdle_blocks[t] ) {
                hurdle_blocks[t]=true;
                ++i;
            }
        }

        //VK: this is how we set the moth's position in a hurdle.
        int j;
        for(j=rand.nextInt(GameParameters.NUM_BLOCKS_IN_HURDLE); hurdle_blocks[j]; j=rand.nextInt(GameParameters.NUM_BLOCKS_IN_HURDLE)) {}

        HorizontalHurdle hh = new HorizontalHurdle(hurdle_blocks[0],
                hurdle_blocks[1],
                hurdle_blocks[2],
                hurdle_blocks[3],
                hurdle_blocks[4],
                (float)mGameParameters.getBlockHeight(),
                j);

        if ( isCreationOfMothWithExtraLifeEnabled() ) {
            hh.setMothHasExtraLife(true);
            disableCreationOfMothWithExtraLife();
        }

        setIthHorizontalHurdle(mHurdleFront++, hh);

        if( mHurdleFront == GameParameters.NUM_HOR_HURDLES) { mHurdleFront = 0; }
    }

    boolean isCreationOfMothWithExtraLifeEnabled() { return mCreateMothWithExtraLife; }
    void enableCreationOfMothWithExtraLife() { mCreateMothWithExtraLife = true; }
    void disableCreationOfMothWithExtraLife() { mCreateMothWithExtraLife = false; }

    int getPlayerScore() { return mPlayerScore; }
    void incrementPlayerScoreBy(int increment) {
        mPlayerScore += increment;
    }

    boolean isHorizontalHurdleToBeRemoved(HorizontalHurdle h, int screenPixHeight) {
        return h.getTopY() > screenPixHeight;
    }

    // if the player's score == 25, set the number of blocks per hurdle to 2
    // if the player's score == 50, set the number of blocks per hurdle to 3
    void possiblyIncreaseNumberOfBlocksInHorizontalHurdles() {
        if ( (mPlayerScore % 25 == 0) && (mPlayerScore < 100) ) {
            switch (mPlayerScore) {
                case 25: mGameParameters.setNumOfBlocksInHurdle(2); return;
                case 50: mGameParameters.setNumOfBlocksInHurdle(3); return;
            }
        }
    }

    void possiblyCreateHorizontalHurdle() {
        if ( noHorizontalHurdlesOnTopOfScreen() ) {
            createHorizontalHurdle();
        }
    }

    void moveHorizontalHurdles(int screenPixHeight) {
        for (HorizontalHurdle h : mHorizontalHurdles ) {
            if (h != null) {
                h.setTopY((float) (h.getTopY() + mGameParameters.getDY()));
            }
        }

        for(HorizontalHurdle h : mHorizontalHurdles ){
            if( h != null ) {
                // VK: If the hurdle must be removed, then remove it
                if ( this.isHorizontalHurdleToBeRemoved(h, screenPixHeight) ) {
                    this.removeHorizontalHurdle(h);
                    this.possiblyIncreaseNumberOfBlocksInHorizontalHurdles();
                    this.possiblyIncreaseHorizontalHurdleDownwardSpeed();
                }
            }
        }
    }

    float getBallCenterX() { return mBallCenterX; }
    void setBallCenterX(float x) { mBallCenterX = x; }

    float getBallCenterY() { return mBallCenterY; }
    void setBallCenterY(float y) { mBallCenterY = y; }

    void possiblyMoveMothBall(float targetX) {
        float ballCenterX = getBallCenterX();
        if ( ballCenterX != targetX ) {
            ballCenterX += ((targetX - ballCenterX) * (Math.sqrt(mGameParameters.getDY()) / mGameParameters.getMaxSpeed()));
            setBallCenterX(ballCenterX);
        }
    }

    boolean collisionDetected() {
        Log.i("collisionDetected()", "collisionDetected()");
        final double blockHeight = mGameParameters.getBlockHeight();
        final double blockWidth = mGameParameters.getBlockWidth();
        final float ballRadius = mGameParameters.getBallRadius();

        for(HorizontalHurdle hurdle : mHorizontalHurdles) {
            if ( hurdle != null ) {
                if ( ( hurdle.getTopY() >= ( mBallCenterY - blockHeight - (ballRadius * 0.5 )) ) &&
                        ( hurdle.getTopY() <= ( mBallCenterY + (ballRadius * 0.5)) ) &&
                        hurdle.isBlockVisible((int)( mBallCenterX / blockWidth) ) &&
                        !hurdle.markedAsCollided() ) {
                    hurdle.markAsCollided();
                    return true;
                }
            }
        }
        return false;
    }

    boolean isPlayerBonusAvailable() { return mPlayerBonusAvailable; }
    void cancelPlayerBonus() { mPlayerBonusAvailable = false; }
    void enablePlayerBonus() { mPlayerBonusAvailable = true; }

    boolean hasPlayerScored() {
        for(HorizontalHurdle hurdle : mHorizontalHurdles) {
            if( hurdle != null ) {
                if ( (hurdle.getTopY() >= (mBallCenterY - mGameParameters.getBlockHeight())) && (hurdle.getTopY() <= mBallCenterY) &&
                        (hurdle.getIndexOfBlockWithMoth() == (int)(mBallCenterX / mGameParameters.getBlockWidth())) ) {
                    hurdle.setNumOfBlockWithMoth(-1);
                    // if the player hit a moth with extra life, there will be a player bonus
                    if ( hurdle.mothHasExtraLife() ) {
                        enablePlayerBonus();
                        hurdle.setMothHasExtraLife(false);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /***********Code I worked on for the homework*******/

    //Updates the players score as they collect moths
    public void updatePlayerScore() {
        //checks if there was a collision with a moth
        if(this.hasPlayerScored()){
            //checks if the player has a bonus available
            if(this.isPlayerBonusAvailable()){
                //checks how many strikes are left
                if(this.getNumStrikesLeft() == 3){
                    //gives the player bonus points
                    mGameView.possiblyPlayExtraPointsAudio();
                    this.incrementPlayerScoreBy(mGameParameters.PLAYER_BONUS);
                }
                else if(this.getNumStrikesLeft() < 3){
                    //gives the player an extra strike
                    mGameView.possiblyPlayExtraNumberOfStrikesAudio();
                    this.incrementNumStrikesLeft();
                }
                //ends the bonus
                this.cancelPlayerBonus();
            }
            else{
                //adds 1 to player score
                mGameView.possiblyPlayDingAudio();
                this.incrementPlayerScoreBy(1);
            }
            //checks if the players score is divisible by 25
            if(this.getPlayerScore() % 25 == 0){
                //creates a bonus moth
                this.enableCreationOfMothWithExtraLife();
            }
            else{
                //disables the ability to create bonus moths
                this.disableCreationOfMothWithExtraLife();
            }

            //changes the block colors
            mGameView.updateBlockPaintIndex();
        }
    }

    //handles if the player has a collision with a block
    void handleCollisions() {
        //checks if a collision has happened
        if(collisionDetected()){
            //checks to see what the immune buffer for the player is
            if(mGameParameters.getImmuneBufferSize() <= 0){
                mGameView.possiblyPlayCollisionAudio();
                //checks if the player will be out of strikes
                if(this.getNumStrikesLeft() == 1){
                    //creates a new intent for EndActivity
                    Intent i = new Intent(mContext.getApplicationContext(), EndActivity.class);
                    //puts the players score in
                    i.putExtra(mContext.getString(R.string.player_score_key), this.getPlayerScore());
                    //tells the game it is over
                    this.finishGame();
                    //starts the EndActivity intent
                    mContext.startActivity(i);
                    //ends the GameView activity
                    System.exit(0);
                }
                else if(this.getNumStrikesLeft() > 1){
                    //decreases the number of strikes by one
                    this.decrementNumStrikesLeft();
                    mGameParameters.setImmuneBufferSize(mGameParameters.getBlockHeight() * 2);
                }
            }
        }
    }

    /**********End of code I worked on for the homework***********/

    static float scaleBallCenterX(int screenPixWidth) {
        return screenPixWidth * BALL_CENTER_X_SCALER;
    }

    static float scaleBallCenterY(int screenPixHeight) {
        return screenPixHeight * BALL_CENTER_Y_SCALER;
    }
}

