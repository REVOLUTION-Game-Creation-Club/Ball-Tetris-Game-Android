package org.Framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class SpriteAnimation extends GraphicObject {
	
    private Rect mSRectangle;
    private int mFPS;
    private int mNoOfFrames;
    private int mCurrentFrame;
    
    private long mFrameTimer;

    private int mSpriteHeight;
    private int mSpriteWidth;
    
    protected boolean mbReply =true;
    protected boolean mbEnd = false;

	public SpriteAnimation(Bitmap bitmap) {
		super(bitmap);
		mSRectangle = new Rect(0,0,0,0);
	    mFrameTimer =0;
	    mCurrentFrame =0;
	}

	public void InitSpriteData(int Height, int Width, int theFPS, int theFrameCount) {
	    mSpriteHeight = Height;
	    mSpriteWidth = Width;
	    mSRectangle.top = 0;
	    mSRectangle.bottom = mSpriteHeight;
	    mSRectangle.left = 0;
	    mSRectangle.right = mSpriteWidth;
	    mFPS = 1000 /theFPS;
	    mNoOfFrames = theFrameCount;
	}
	@Override
	public void Draw(Canvas canvas){
	    Rect dest = new Rect(m_x, m_y,m_x + mSpriteWidth,
	    		m_y + mSpriteHeight);
	 
	    canvas.drawBitmap(m_bitmap, mSRectangle, dest, null);
	}
	
	public void Update(long GameTime) {
		if(!mbEnd){
		    if(GameTime > mFrameTimer + mFPS ) {
		        mFrameTimer = GameTime;
		        mCurrentFrame +=1;
		 
		        if(mCurrentFrame >= mNoOfFrames) {
		            if(mbReply)
		            	mCurrentFrame = 0;
		            else
		            	mbEnd = true;
		        }
		    }
	    }
	 
	    mSRectangle.left = mCurrentFrame * mSpriteWidth;
	    mSRectangle.right = mSRectangle.left + mSpriteWidth;
	}
	public boolean getAnimationEnd(){
		return mbEnd;
	}

	
}
