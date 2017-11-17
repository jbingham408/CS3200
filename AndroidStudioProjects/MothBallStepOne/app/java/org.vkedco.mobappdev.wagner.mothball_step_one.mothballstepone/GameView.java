package org.vkedco.mobappdev.wagner.mothball_step_one.mothballstepone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.util.Log;

public class GameView extends View {

    protected Resources mRes = null;
    protected int DING_ID, OO_ID, ARPEG_ID, EXTRA_PTS_ID;
    protected int mScreenPixWidth, mScreenPixHeight;
    protected float mScreenPixYDPI;

    protected float mTargetX;

    protected int mFlashFrameCounter, mFlashPaintIndex; // these two control the color of the circle behind the moth
    protected int mBlockPaintIndex; // index of the paint from the block paint array

    protected boolean mAudioMuted;

    private Context mContext;
    private SoundPool mSoundPool = null;

    private GameParameters mGameParameters = null;
    private GameArtist mGameArtist = null;
    private GameLogic mGameLogic = null;

    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);
        mRes = getResources();
        mContext = context;
        // Create the sound pool and initialize the sounds into it
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        DING_ID = mSoundPool.load(mContext, R.raw.ding, 1);
        OO_ID = mSoundPool.load(mContext, R.raw.oo, 4);
        ARPEG_ID = mSoundPool.load(mContext, R.raw.arpegio, 2);
        EXTRA_PTS_ID = mSoundPool.load(mContext, R.raw.extra_points, 3);
    }

    protected void setScreenParams(int w, int h, float d){
        mScreenPixWidth = w;
        mScreenPixHeight = h;
        mScreenPixYDPI = d;
    }

    protected void initialize() {
        mAudioMuted         = false;

        mFlashPaintIndex = 0; // this variable controls the current paint color that draws against the moth
        mBlockPaintIndex = 0;

        mFlashFrameCounter = 1;
        mTargetX = (float)(mScreenPixWidth * 0.5);

        createGameParameters();
        createGameArtist();
        createGameLogic();
    }

    public void update() {
        Log.i("UPDATE()", "UPDATE()");
        playOneGameCycle();
        updateFlashPaintIndex();
    }

    @Override
    public void onDraw(Canvas canvas){
        if ( mGameLogic.isGameOver() ) return;

        synchronized ( this ) {
            mGameArtist.drawBackgroundRectangle(canvas, mScreenPixWidth, mScreenPixHeight);
            mGameArtist.drawSpeaker(canvas, mScreenPixWidth, mAudioMuted);
            mGameArtist.drawMothBall(canvas, mGameLogic.getNumStrikesLeft(), mGameLogic.getBallCenterX(), mGameLogic.getBallCenterY());
            mGameArtist.drawHorizontalHurdles(canvas, mGameLogic.getHorizontalHurdles(), mBlockPaintIndex, mFlashPaintIndex);
            mGameArtist.drawPlayerScore(canvas, mScreenPixWidth, mScreenPixHeight, mGameLogic.getPlayerScore());
        }

        super.onDraw(canvas);
    }

    private void createGameParameters() {
        mGameParameters = new GameParameters();

        final double marginSize = GameParameters.scaleMarginSize(mScreenPixWidth);
        final double startSpeed = GameParameters.scaleStartSpeed(mScreenPixYDPI);
        final double maxSpeed   = GameParameters.scaleMaxSpeed(mScreenPixYDPI);

        final double blockHeight = GameParameters.scaleBlockHeight(mScreenPixHeight);
        final double blockWidth  = GameParameters.scaleBlockWidth(mScreenPixWidth);
        final double mothBmpWidth = GameParameters.scaleMothBmpWidth(blockWidth);
        final double mothBmpHeight = GameParameters.scaleMothBmpHeight(blockWidth);
        final float ballRadius = GameParameters.scaleBallRadius(mScreenPixWidth);

        final float audioIconWidth = GameParameters.scaleAudioIconWidth(mScreenPixWidth);
        final float audioIconHeight = GameParameters.scaleAudioIconHeight(mScreenPixWidth);

        mGameParameters.setMarginSize(marginSize);
        mGameParameters.setStartSpeed(startSpeed);
        mGameParameters.setDY(startSpeed);
        mGameParameters.setMaxSpeed(maxSpeed);
        mGameParameters.setImmuneBufferSize(0);

        mGameParameters.setBlockHeight(blockHeight);
        mGameParameters.setBlockWidth(blockWidth);

        mGameParameters.setMothBmpHeight(mothBmpHeight);
        mGameParameters.setMothBmpWidth(mothBmpWidth);

        mGameParameters.setBallRadius(ballRadius);

        mGameParameters.setAudioIconHeight(audioIconHeight);
        mGameParameters.setAudioIconWidth(audioIconWidth);

        mGameParameters.setNumOfBlocksInHurdle(1);
    }

    private void createGameArtist() {
        mGameArtist = new GameArtist(mGameParameters);
        mGameArtist.setResources(mRes);
        mGameArtist.setScreenPixHeight(mScreenPixHeight);
        mGameArtist.setScreenPixWidth(mScreenPixWidth);
        mGameArtist.createPaints();
        mGameArtist.loadBitmaps();
    }

    private void createGameLogic() {
        final float bcx = GameLogic.scaleBallCenterX(mScreenPixWidth);
        final float bcy = GameLogic.scaleBallCenterY(mScreenPixHeight);
        mGameLogic = new GameLogic(mGameParameters, this, mContext);
        mGameLogic.setBallCenterX(bcx);
        mGameLogic.setBallCenterY(bcy);
    }

    private void playOneGameCycle() {
        Log.i("playOneGameCycle()", "playOneGameCycle()");
        mGameLogic.possiblyMoveMothBall(mTargetX);
        //mGameLogic.moveHorizontalHurdles(mScreenPixHeight);
        //mGameLogic.possiblyDecreaseImmuneBufferSize();
        //mGameLogic.handleCollisions();
        //mGameLogic.updatePlayerScore();
        //mGameLogic.possiblyCreateHorizontalHurdle();
    }

    public void possiblyPlayExtraPointsAudio() {
        if ( !mAudioMuted )
            mSoundPool.play(EXTRA_PTS_ID, 1f, 1f, 1, 0, 1f);
    }

    public void possiblyPlayExtraNumberOfStrikesAudio() {
        if ( !mAudioMuted )
            mSoundPool.play(ARPEG_ID, 1f, 1f, 1, 0, 1f);
    }

    public void possiblyPlayDingAudio() {
        if ( !mAudioMuted )
            mSoundPool.play(DING_ID, 1f, 1f, 1, 0, 1f);
    }

    public void possiblyPlayCollisionAudio() {
        if ( !mAudioMuted )
            mSoundPool.play(OO_ID, 1f, 1f, 1, 0, 1f);
    }

    public void updateBlockPaintIndex() {
        if ( mGameLogic.getPlayerScore() % 10 == 0) {
            ++mBlockPaintIndex;
        }
        if (mBlockPaintIndex == 7) {
            mBlockPaintIndex = 0;
        }
    }

    private void updateFlashPaintIndex() {
        // this block of code copntrols the flash index of the circle behind the moth with
        // extra life
        ++mFlashFrameCounter;
        if ( mFlashFrameCounter == 56 ) mFlashFrameCounter = 1;
        if ( mFlashFrameCounter % 5 == 0) ++mFlashPaintIndex;
        if ( mFlashPaintIndex == 7 ) mFlashPaintIndex = 0;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            float touchX = event.getX();
            if ( touchX > (mScreenPixWidth - mGameParameters.getAudioIconWidth()) &&
                    event.getY() < mGameParameters.getAudioIconHeight() ){
                mAudioMuted = !mAudioMuted;
            }else{
                final double blockWidth = mGameParameters.getBlockWidth();
                final double marginSize = mGameParameters.getMarginSize();
                int touchCol  = (int)(touchX/(blockWidth + marginSize));
                int targetCol = (int) (mTargetX /(blockWidth + marginSize));
                if ( touchCol > targetCol && targetCol <= GameParameters.MAX_COLUMN_NUMBER ) {
                    mTargetX += (blockWidth + marginSize);
                }else if( touchCol < targetCol &&  targetCol > 0){
                    mTargetX -= (blockWidth + marginSize);
                }
            }
        }
        return true;
    }
}
