package com.cfk.hangball;

import java.util.Random;

import android.graphics.Color;

/*
* ����ó�� Ŭ����
*/
public class ColorDesigner
{
	public ColorDesigner()
	{
	}

	/*
	* 9���� ���� �� �����ϰ� ���� ���´�.
	* Color : ����Ŭ����
	*/
	static int GetRandomColor()
	{
		Random rand = new Random();

		return GetIndexColor(rand.nextInt(9));
	}
	
	static int GetIndexColor(int idx)
	{
		switch(idx)
		{
		case 0:
			return Color.GRAY;
		case 1:
			return Color.BLACK;
		case 2:
			return Color.RED;
		case 3:
			return Color.rgb(255, 154, 0);
		case 4:
			return Color.YELLOW;
		case 5:
			return  Color.GREEN;
		case 6:
			return Color.BLUE;
		case 7:
			return Color.rgb(25, 25, 112);
		case 8:
			return Color.rgb(102, 51, 153);
		default:
			break;
		}

		return 0;	
	}
};