package org.Framework;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameViewThread extends Thread {
	private SurfaceHolder _surfaceHolder;
    private GameView _gameview;
    private boolean _run = false;
    Canvas c = null;

    long CurTime = 0;
    long ElapsedTime = 0;
	long LastTime = 0;

    public GameViewThread(SurfaceHolder surfaceHolder, GameView gameview) 
    {
        _surfaceHolder = surfaceHolder;
        _gameview = gameview;
        LastTime = System.currentTimeMillis();
    }
 
    public void setRunning(boolean run) 
    {
        _run = run;
    }
 
	@Override
    public void run() 
    {
		while (_run) 
        {
			CurTime = System.currentTimeMillis();
			ElapsedTime = CurTime-LastTime;
			LastTime = CurTime;

			double double_second = (double)ElapsedTime/(double)(1000.0);
			_gameview.Update(double_second);
            try 
            {
            	c = _surfaceHolder.lockCanvas(null);
                synchronized (_surfaceHolder) 
                {
                	_gameview.onDraw(c);
                }
            } 
            finally 
            {
                synchronized (_surfaceHolder) 
                {
                	_surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }

}
