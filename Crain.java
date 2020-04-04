package com.cfk.hangball;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;

/*
* ũ����: �ٱ��� ������ �����̴� ��⸻
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
	* �ʱ�ȭ�Լ�
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
	* �̹������� �ε� �Լ�
	*/
	public boolean Load(String _file_name)
	{
		
		return true;
	}

	/*
	* ������ ������Ʈ �Լ�
	*/
	public boolean Update(float _fElapsedTime)
	{
		return true;
	}

	/*
	* �̹����� �׸��� �Լ�
	*/
	public boolean Draw(Canvas c)
	{
		m_pNextBall.Draw(c);
		m_pBall.Draw(c);
		
		return true;
	}

	/*
	* ���콺 ��ǥ�� ���� ũ������ ��⸻�� 
	* x���� �������ش�.
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
	* ũ������ ���� �����Ͽ� �����Ѵ�.
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

		// ũ���� �ؽ�Ʈ �� �缳��
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