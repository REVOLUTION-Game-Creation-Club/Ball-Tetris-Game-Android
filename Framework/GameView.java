package org.Framework; 

import com.cfk.hangball.HangballGame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GameView extends SurfaceView implements SurfaceHolder.Callback{

	private GameViewThread _thread;
	
	private IState m_state;
	
	
    public GameView(Context context) 
    {
        super(context);
        
    	setFocusable(true);
      	
    	AppManager.getInstance().setGameView(this);
    	AppManager.getInstance().setResources(getResources());
//    	int w = getWidth();
//    	int h = getHeight();
//    	AppManager.getInstance().setSize(w, h);
    	AppManager.getInstance().setSize(640, 960);
  
    	ChangeGameState(new HangballGame(context));
    	
        getHolder().addCallback(this);
//        getHolder().setFixedSize(getWidth(),getHeight());
        getHolder().setFixedSize(640,960);
        
        _thread = new GameViewThread(getHolder(),this);
        
    }
    
    @Override
    public void onDraw(Canvas canvas) 
    {
    	canvas.drawColor(Color.WHITE);
    	m_state.Render(canvas);
    }
	
    @Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) 
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		_thread.setRunning(true);
	    _thread.start(); 		
	}
	
	public void Update(double _fElapsedTime) 
	{
		m_state.Update(_fElapsedTime);
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) 
	{
		boolean retry = true;
	    _thread.setRunning(false);
	    while (retry) 
	    {
	        try 
	        {
	            _thread.join();
	            retry = false;
	        } 
	        catch (InterruptedException e) 
	        {
	        }
	    }
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		m_state.onKeyDown(keyCode, event);
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		m_state.onTouchEvent(event);
		return true;

	}
	
	public void ChangeGameState(IState _state)
	{
		if(m_state!=null)
			m_state.Destroy();
		_state.Init();
		m_state = _state;
	}
}
