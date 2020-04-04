package com.cfk.hangball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Mission 
{
	public int[] BallColor = new int[3];
	public int[] BallCount = new int[3];
	Paint paint = new Paint();
	
	Mission()
	{
		
	}
	
	boolean Init(int difficult)
	{
		int random_color0 = ColorDesigner.GetRandomColor();
		while(true)
		{
			if((random_color0 != Color.GRAY)&&
					(random_color0 != Color.BLACK))
			{
				break;
			}
			random_color0 = ColorDesigner.GetRandomColor();
		}

		BallColor[0] = random_color0;

		
		int random_color1 = ColorDesigner.GetRandomColor();
		while(true)
		{
			if((random_color0 != random_color1)&&
					(random_color1 != Color.GRAY)&&
					(random_color1 != Color.BLACK))
			{
				break;
			}
			random_color1 = ColorDesigner.GetRandomColor();
		}
		
		BallColor[1] = random_color1;
		
		
		int random_color2 = ColorDesigner.GetRandomColor();
		while(true)
		{
			if((random_color0 != random_color2)&&
					(random_color1 != random_color2)&&
					(random_color2 != Color.GRAY)&&
					(random_color2 != Color.BLACK))
			{
				break;
			}
			random_color2 = ColorDesigner.GetRandomColor();
		}

		BallColor[2] = random_color2;

		BallCount[0] = 9*difficult;
		BallCount[1] = 9*difficult;
		BallCount[2] = 9*difficult;

		return true;
	}
	boolean Update()
	{

		return true;
	}
	
	boolean Draw(Canvas c)
	{
		paint.setTextSize(30);

		paint.setColor(BallColor[0]);
		c.drawText(String.format("%d", BallCount[0]), 400, 50, paint);
		paint.setColor(BallColor[1]);
		c.drawText(String.format("%d", BallCount[1]), 450, 50, paint);
		paint.setColor(BallColor[2]);
		c.drawText(String.format("%d", BallCount[2]), 500, 50, paint);

		return true;
	}
}
