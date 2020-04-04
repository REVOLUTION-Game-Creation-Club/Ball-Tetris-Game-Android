package com.cfk.hangball;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;

/*
* 크래인: 바구니 위에서 움직이는 장기말
*/
public class Crain
{
	private Ball m_pBall = new Ball();
	private Ball m_pNextBall = new Ball();
	private Random rand = new Random();
	
	public Crain()
	{
	}

	/*
	* 초기화함수
	*/
	public boolean Init()
	{
		m_pBall.Init();
		m_pBall.SetExist(true);
		int color = ColorDesigner.GetRandomColor();
		m_pBall.SetColor(color);	

		
		m_pNextBall.Init();
		m_pNextBall.SetX(0);
		m_pNextBall.SetY(0);
		m_pNextBall.SetExist(true);

		int next_color = ColorDesigner.GetRandomColor();
		m_pNextBall.SetColor(next_color);	

		return true;
	}

	
	/*
	* 이미지파일 로드 함수
	*/
	public boolean Load(String _file_name)
	{
		
		return true;
	}

	/*
	* 데이터 업데이트 함수
	*/
	public boolean Update(float _fElapsedTime)
	{
		return true;
	}

	/*
	* 이미지를 그리는 함수
	*/
	public boolean Draw(Canvas c)
	{
		m_pNextBall.Draw(c);
		m_pBall.Draw(c);
		
		return true;
	}

	/*
	* 마우스 좌표에 따라 크레인의 장기말의 
	* x값을 설정해준다.
	*/
	public void SetBallX(float _x)
	{
		float box_left = SandBox.GetSize().left;
		float box_right = SandBox.GetSize().right - m_pBall.GetRadius()*2;
		
		if( _x <= box_left)
		{
			m_pBall.SetX(box_left);
		}
		else if( box_right <= _x)
		{
			m_pBall.SetX(box_right);
		}
		else
		{
			m_pBall.SetX(_x);
		}
	}

	public Ball GetBall()
	{
		return m_pBall;
	}
	
	/*
	* 크래인의 볼을 복사하여 리턴한다.
	*/
	public Ball GetResultBall()
	{
		Ball result_ball = new Ball();
		result_ball.Init();
		int bitmap_w = result_ball.GetBitmap().getWidth();
		result_ball.SetRadius(bitmap_w/6);

		result_ball.CloneBall(m_pBall);
		
		float x = m_pBall.GetX();
		float y = m_pBall.GetY();
		result_ball.SetX(x);
		result_ball.SetY(y);

		m_pBall.CloneBall(m_pNextBall);

		// 크래인 넥스트 볼 재설정
		int new_c = ColorDesigner.GetRandomColor();
		m_pNextBall.SetColor(new_c);
		
		if((rand.nextInt(30) == 0)&&
				(new_c != Color.BLACK)&&
				(new_c != Color.GRAY)) 
		{
			m_pNextBall.SetStar(true);
		}
		else
		{
			m_pNextBall.SetStar(false);
		}
		
		return result_ball;
	}
};