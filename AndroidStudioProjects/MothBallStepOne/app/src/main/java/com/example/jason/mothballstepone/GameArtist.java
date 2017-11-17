package com.example.jason.mothballstepone;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class GameArtist {

    private int mScreenPixWidth;
    private int mScreenPixHeight;

    private Resources mRes = null;
    private Paint mBlackPaint = null;
    private Paint mWhitePaint = null;
    private Paint mRedPaint = null;
    private Paint mOrangePaint = null;
    private Paint mYellowPaint = null;
    private Paint mGreenPaint = null;
    private Paint mBluePaint = null;
    private Paint mPurplePaint = null;
    private Paint mTextPaint = null;
    private Paint[] mBlockPaints = null;

    private Bitmap mMothBMP, mSpeakerBMP, mMuteBMP;

    //private double mMarginDim;
    //protected double mBlockWidth, mBlockHeight;
    //protected double mMothBmpHeight;
    //protected float mAudioIconWidth, mAudioIconHeight;

    private GameParameters mGameParameters = null;

    public GameArtist(GameParameters gp) { mGameParameters = gp; }

    void setResources(Resources res) {
        mRes = res;
    }

    void setScreenPixWidth(int width) {
        mScreenPixWidth = width;
    }

    void setScreenPixHeight(int height) {
        mScreenPixHeight = height;
    }


    //void setBlockWidth(double width) {
    //    mBlockWidth = width;
    //}

    //void setBlockHeight(double height) {
    //    mBlockHeight = height;
    //}

    //void setMothBmpHeight(double height) {
    //    mMothBmpHeight = height;
    //}



    void createPaints() {
        mBlackPaint = new Paint();
        mBlackPaint.setColor(Color.BLACK);
        mWhitePaint = new Paint();
        mWhitePaint.setColor(Color.WHITE);
        mRedPaint = new Paint();
        mRedPaint.setColor(Color.RED);
        mOrangePaint = new Paint();
        mOrangePaint.setColor(mRes.getColor(R.color.orange));
        mYellowPaint = new Paint();
        mYellowPaint.setColor(Color.YELLOW);
        mGreenPaint = new Paint();
        mGreenPaint.setColor(Color.GREEN);
        mBluePaint = new Paint();
        mBluePaint.setColor(Color.BLUE);
        mPurplePaint = new Paint();
        mPurplePaint.setColor(mRes.getColor(R.color.purple));
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        // Create an array of paints
        mBlockPaints = new Paint[]{mWhitePaint, mRedPaint, mOrangePaint, mYellowPaint, mGreenPaint, mBluePaint, mPurplePaint};
    }

    void setMothBitmap(Bitmap bmp) {
        mMothBMP = bmp;
    }

    void setSpeakerBitmap(Bitmap bmp) {
        mSpeakerBMP = bmp;
    }

    void setMuteBitmap(Bitmap bmp) {
        mMuteBMP = bmp;
    }

    void drawBackgroundRectangle(Canvas canvas, int screenPixWidth, int screenPixHeight) {
        canvas.drawRect(0, 0, screenPixWidth, screenPixHeight, mBlackPaint);
    }

    void drawMothBall(Canvas canvas, int num_strikes_left, float ball_x, float ball_y) {
        final float ball_r = mGameParameters.getBallRadius();
        switch ( num_strikes_left ) {
            case 3:
                canvas.drawCircle(ball_x, ball_y, ball_r, mGreenPaint);
                break;
            case 2:
                canvas.drawCircle(ball_x, ball_y, ball_r, mYellowPaint);
                break;
            case 1:
                canvas.drawCircle(ball_x, ball_y, ball_r, mRedPaint);
                break;
        }
    }


    void drawHorizontalHurdles(Canvas canvas, HorizontalHurdle[] hurdles, int blockPaintIndex, int flashPaintIndex) {
        final double marginSize = mGameParameters.getMarginSize();
        final double blockWidth = mGameParameters.getBlockWidth();
        final double blockHeight = mGameParameters.getBlockHeight();
        for(HorizontalHurdle h : hurdles){
            if ( h != null) {
                for(int i=0; i < h.size(); ++i){
                    if( h.isBlockVisible(i) ){
                        canvas.drawRect((float)(marginSize + (i * (blockWidth + marginSize))),
                                h.getTopY(),
                                (float)((marginSize + (i * (blockWidth + marginSize)))+ blockWidth),
                                (float)(h.getTopY() + blockHeight),
                                mBlockPaints[blockPaintIndex]);

                    }
                }
                if ( h.getIndexOfBlockWithMoth() !=-1 ) {
                    if ( h.mothHasExtraLife() ) {
                        canvas.drawCircle((float)(marginSize + (blockWidth * 0.5) + (h.getIndexOfBlockWithMoth() * (blockWidth + marginSize))),
                                (float)(h.getTopY() + (blockHeight * 0.5)),
                                (float)(blockWidth * 0.5),
                                mBlockPaints[flashPaintIndex]);
                    }
                    canvas.drawBitmap(mMothBMP,
                            (float)(marginSize + (h.getIndexOfBlockWithMoth() * (blockWidth + marginSize))),
                            (float)(h.getTopY() + ((blockHeight - mGameParameters.getMothBmpHeight()) * 0.5)),
                            null);
                }
            }
        }
    }

    void loadBitmaps() {
        Bitmap unscaledMothBMP = BitmapFactory.decodeResource(mRes, R.drawable.moth_tsp);
        Bitmap scaledMothBMP = Bitmap.createScaledBitmap(unscaledMothBMP, (int) mGameParameters.getBlockWidth(),
                (int) mGameParameters.getMothBmpHeight(), true);
        setMothBitmap(scaledMothBMP);
        unscaledMothBMP.recycle();
        Bitmap unscaledSpeakerBMP = BitmapFactory.decodeResource(mRes, R.drawable.speaker_tsp);
        Bitmap scaledSpeakerBMP = Bitmap.createScaledBitmap(unscaledSpeakerBMP,
                (int) mGameParameters.getAudioIconWidth(), (int) mGameParameters.getAudioIconHeight(), true);
        unscaledSpeakerBMP.recycle();
        this.setSpeakerBitmap(scaledSpeakerBMP);
        Bitmap unscaledMuteBMP = BitmapFactory.decodeResource(mRes, R.drawable.mute_tsp);
        Bitmap scaledMuteBMP = Bitmap.createScaledBitmap(unscaledMuteBMP,
                (int) mGameParameters.getAudioIconWidth(), (int) mGameParameters.getAudioIconHeight(), true);
        this.setMuteBitmap(scaledMuteBMP);
        unscaledMuteBMP.recycle();
    }

    void drawSpeaker(Canvas canvas, int screenPixWidth, boolean isAudioMuted) {
        final float audioIconWidth = mGameParameters.getAudioIconWidth();
        Log.i("GameArtist", "drawSpeaker()");
        Log.i("GameArtist", mSpeakerBMP.getHeight() + " " + mSpeakerBMP.getWidth());
        Log.i("GameArtist", "mScreenPixWidth = " + screenPixWidth);
        Log.i("GameArtist", "mAudioIconWidth = " + audioIconWidth);
        float left = (float)(screenPixWidth - audioIconWidth);
        canvas.drawBitmap(mSpeakerBMP, left, 10.0f, null);
        if ( isAudioMuted ) {
            Log.v("GameArtist", "AUDIO MUTED");
            canvas.drawBitmap(mMuteBMP, left, 10.0f, null);
        }
    }

    Bitmap getSpeakerBitmap() { return mSpeakerBMP; }

    Bitmap getMuteBitmap() { return mMuteBMP; }

    void drawPlayerScore(Canvas canvas, int screenPixWidth, int screenPixHeight, int playerScore) {
        mTextPaint.setTextSize((float) (screenPixHeight * 0.05));
        canvas.drawText("Points: " + playerScore, (float) (screenPixWidth * 0.325), (float) (screenPixHeight * 0.125), mTextPaint);
    }
}

