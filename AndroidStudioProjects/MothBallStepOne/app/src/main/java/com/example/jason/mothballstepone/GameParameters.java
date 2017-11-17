package com.example.jason.mothballstepone;

/**
 * Created by Vladimir Kulyukin on 8/25/2015.
 */
public class GameParameters {

    static final int NUM_PAINTS                     = 7;
    static final int NUM_HOR_HURDLES                = 8;
    static final int NUM_BLOCKS_IN_HURDLE           = 5;
    static final int PLAYER_BONUS                   = 9;
    static final double SCREEN_PIX_WIDTH_SCALER     = 0.18;
    static final double SCREEN_PIX_HEIGHT_SCALER    = 0.1125;
    static final double BALL_RADIUS_SCALER          = 0.045;
    static final double BLOCK_WIDTH_SCALER          = 0.8;
    static final double MARGIN_SIZE_SCALER          = 60.0;
    static final double SPEED_SCALER                = 160;
    static final double START_SPEED_CONST           = 7.0;
    static final double MAX_SPEED_CONST             = 15.0;
    static final double DY_INCREMENT_SCALER         = 0.01;
    static final float AUDIO_ICON_WIDTH_SCALER      = 0.15f;
    static final float AUDIO_ICON_HEIGHT_SCALER     = 0.13f;
    static final int PERMITTED_NUM_STRIKES          = 3;
    static final int MAX_COLUMN_NUMBER              = 3;

    //static final String PLAYER_SCORE_KEY           = "final_player_score";

    private double mMarginSize                      = 0;
    private double mStartSpeed                      = 0;
    private double mMaxSpeed                        = 0;
    private double mImmuneBufferSize                = 0;

    private double mDY                              = 0;

    private double mBlockHeight                     = 0;
    private double mBlockWidth                      = 0;

    private double mMothBmpHeight                   = 0;
    private double mMothBmpWidth                    = 0;

    private float mBallRadius                       = 0;

    protected float mAudioIconWidth                 = 0;
    protected float mAudioIconHeight                = 0;

    protected int mNumOfBlocksInHurdle               = 0;

    public GameParameters() {}

    double getMarginSize()         { return mMarginSize; }
    void setMarginSize(double dim) { mMarginSize = dim; }

    double getStartSpeed()        { return mStartSpeed; }
    void setStartSpeed(double sp) { mStartSpeed = sp; }

    double getMaxSpeed()        { return mMaxSpeed; }
    void setMaxSpeed(double ms) { mMaxSpeed = ms; }

    double getImmuneBufferSize()        { return mImmuneBufferSize; }
    void setImmuneBufferSize(double is) { mImmuneBufferSize = is; }

    double getDY()        { return mDY; }
    void setDY(double dy) { mDY = dy; }

    double getBlockHeight()        { return mBlockHeight; }
    void setBlockHeight(double bh) { mBlockHeight = bh; }

    double getBlockWidth()        { return mBlockWidth; }
    void setBlockWidth(double bw) { mBlockWidth = bw; }

    double getMothBmpHeight()     { return mMothBmpHeight; }
    void setMothBmpHeight(double h) { mMothBmpHeight = h; }

    double getMothBmpWidth()       { return mMothBmpWidth; }
    void setMothBmpWidth(double h) { mMothBmpWidth = h; }

    float getBallRadius()       { return mBallRadius; }
    void setBallRadius(float r) { mBallRadius = r; }

    float getAudioIconWidth()       { return mAudioIconWidth; }
    void setAudioIconWidth(float w) {
        mAudioIconWidth = w;
    }

    float getAudioIconHeight()       { return mAudioIconHeight; }
    void setAudioIconHeight(float h) {
        mAudioIconHeight = h;
    }

    int getNumOfBlocksInHurdle() { return mNumOfBlocksInHurdle; }
    void setNumOfBlocksInHurdle(int nb) { mNumOfBlocksInHurdle = nb; }

    static double scaleMarginSize(double screenPixWidth) {
        return screenPixWidth / MARGIN_SIZE_SCALER;
    }

    static double scaleStartSpeed(double screenPixYDPI) {
        return START_SPEED_CONST * (screenPixYDPI / SPEED_SCALER);
    }

    static double scaleMaxSpeed(double screenPixYDPI) {
        return MAX_SPEED_CONST * (screenPixYDPI / SPEED_SCALER);
    }

    static double scaleBlockWidth(int screenPixWidth) {
        return screenPixWidth * SCREEN_PIX_WIDTH_SCALER;
    }

    static double scaleBlockHeight(int screenPixHeight) {
        return screenPixHeight * SCREEN_PIX_HEIGHT_SCALER;
    }

    static double scaleMothBmpWidth(double blockWidth) {
        return blockWidth * BLOCK_WIDTH_SCALER;
    }

    static double scaleMothBmpHeight(double blockHeight) {
        return blockHeight * BLOCK_WIDTH_SCALER;
    }

    static float scaleBallRadius(int screenPixWidth) {
        return (float)(screenPixWidth * BALL_RADIUS_SCALER);
    }

    static float scaleAudioIconWidth(int screePixWidth) {
        return (float)(screePixWidth * AUDIO_ICON_WIDTH_SCALER);
    }

    static float scaleAudioIconHeight(int screenPixWidth) {
        return (float)(screenPixWidth * AUDIO_ICON_HEIGHT_SCALER);
    }



}

