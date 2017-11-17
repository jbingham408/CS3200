package com.example.jason.mothballstepone;

public class HorizontalHurdle {
    protected boolean [] mBlocks;
    protected float mTopY;
    protected int mNumOfBlockWithMoth;
    protected boolean mMothHasExtraLife;
    protected float mBlockHeight;
    protected boolean mHasCollided;

    public HorizontalHurdle(boolean a, boolean b, boolean c, boolean d, boolean e, float block_height, int block_with_moth){
        mBlocks = new boolean [5];
        mBlocks[0] = a;
        mBlocks[1] = b;
        mBlocks[2] = c;
        mBlocks[3] = d;
        mBlocks[4] = e;
        mNumOfBlockWithMoth = block_with_moth;
        mTopY = -mBlockHeight;
        mMothHasExtraLife = false;
        mHasCollided = false;
    }

    public boolean mothHasExtraLife() { return mMothHasExtraLife; }

    public void setMothHasExtraLife(boolean b){
        mMothHasExtraLife = b;
    }

    public float getTopY() { return mTopY; }

    public void setTopY(float y) { mTopY = y; }

    public int getIndexOfBlockWithMoth() { return mNumOfBlockWithMoth; }

    public boolean isBlockVisible(int i) { return mBlocks[i]; }

    public void setNumOfBlockWithMoth(int i) {
        mNumOfBlockWithMoth = i;
    }

    public int size() {
        if ( mBlocks != null )
            return mBlocks.length;
        else
            return 0;
    }

    public void markAsCollided() { mHasCollided = true; }
    public boolean markedAsCollided() { return mHasCollided; }
}


