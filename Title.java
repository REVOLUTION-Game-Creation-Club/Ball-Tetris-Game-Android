package com.cfk.hangball;

import org.Framework.AppManager;
import org.Framework.R;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Title 
{
	private static Bitmap 	m_bitmap = null;
	Paint paint = new Paint();

	Title()
	{
	}
	
	public boolean Init()
	{
		if(m_bitmap == null)
		{
			m_bitmap = AppManager.getInstance().getBitmap(R.drawable.dippin);
		}

		return true;
	}

	static boolean Load(String _file_name)
	{
		
		return true;
	}

	public boolean Update(double ElapsedTime)
	{
		
		return true;
	}
	
	public boolean Draw(Canvas c)
	{
		c.drawBitmap(m_bitmap,
				new Rect(0,0,m_bitmap.getWidth(),m_bitmap.getHeight()),
				new Rect((int)0,(int)0,(int)(0+640),(int)(0+960)),
				paint);

		return true;
	}
}
