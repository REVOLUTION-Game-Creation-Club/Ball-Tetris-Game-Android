package com.cfk.hangball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class FPS {
	static double frame_count = 0;
	static double TotalTime = 0.0;
	static double fps = 0.0;
	Paint paint = new Paint();
	
	public boolean Update(double ElapsedTime)
	{
		frame_count++;
		TotalTime += ElapsedTime;
		if(TotalTime > 1.0)
		{
			fps = frame_count/TotalTime;
			
			frame_count = 0;
			TotalTime = 0.0;
			
		}

		return true;
	}
	
	public boolean Draw(Canvas c)
	{
		paint.setColor(Color.BLUE);
		paint.setTextSize(30);
		c.drawText(String.format("%f", fps), 0, 50, paint);

		return true;
	}
}
