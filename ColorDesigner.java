package com.cfk.hangball;

import java.util.Random;

import android.graphics.Color;

/*
* 색깔처리 클래스
*/
public class ColorDesigner
{
	public ColorDesigner()
	{
	}

	/*
	* 9가지 색깔 중 랜덤하게 색을 얻어온다.
	* Color : 색깔클래스
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