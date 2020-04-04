package com.cfk.hangball;

import org.Framework.IState;

import android.content.Context;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class HangballGame implements IState 
{
	Crain theCrain = new Crain();
	SandBox theSandBox = new SandBox();
	Rank theRank = new Rank();
	Title theTitle = new Title();
	FPS theFPS = new FPS();

	static Context m_Context;
	public static int stage_no = 0;
	
	public HangballGame(Context _context)
	{
		m_Context = _context;
	}
	
	public static Context GetContext() { return m_Context; } 
	
	@Override
	public void Init() 
	{
		theTitle.Init();
		theCrain.Init();
		theSandBox.Init();
		theRank.Init();
	}
	
	@Override
	public void Update(double _ElapsedTime) 
	{		
		switch(stage_no)
		{
		case 0:
			theTitle.Update(_ElapsedTime);
			break;
		case 1:
			theSandBox.Update(_ElapsedTime);
			break;
		case 9:
			theRank.Update(_ElapsedTime);
			break;
		}
		
		theFPS.Update(_ElapsedTime);

	}

	@Override
	public void Render(Canvas canvas) 
	{		
		switch(stage_no)
		{
		case 0:
			theTitle.Draw(canvas);
			break;
		case 1:
			theSandBox.Draw(canvas);
			theCrain.Draw(canvas);
			break;
		case 9:
			theRank.Draw(canvas);
			break;
		}
		
		theFPS.Draw(canvas);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{			
		if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
		{
			
		}
		if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)
		{
			
		}
		if(keyCode == KeyEvent.KEYCODE_DPAD_UP)
		{
			
		}
		if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
		{
			
		}
		if(keyCode == KeyEvent.KEYCODE_SPACE)
		{
			
		}
		
		return false;
	}


	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		switch(stage_no)
		{
			case 0:
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					SetStage(1);
				}
				break;
			case 1:
//				for(int i =0; i<event.getPointerCount();i++)
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
				}			
				else if(event.getAction() == MotionEvent.ACTION_MOVE)
				{
					
					float x = event.getX();
//					float y = event.getY();

//					float w = AppManager.getInstance().getWidth(); 
//							
//					x = x*640.0f/w;

					theCrain.SetBallX(x);
				}
				else if(event.getAction() == MotionEvent.ACTION_UP)
				{
					if(theSandBox.GetNewBall() != null)
						return  false;

					float x = event.getX();
					theCrain.SetBallX(x);

					Ball pBall = theCrain.GetResultBall();
//					pBall.PlaySound();
					theSandBox.SetBall(pBall);
				}
				break;
			case 9:
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					theSandBox.Init();
					SetStage(0);
				}
				break;
		}
		return true;
	}

	@Override
	public void Destroy() 
	{
	
	}

	public static void SetStage(int i) {
		stage_no = i;
	}
	
	public static int GetStage()
	{
		return stage_no;
	}
}
