package com.cfk.hangball;

import org.Framework.AppManager;
import org.Framework.R;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/*
* ImBall ��⸻
*/
public class Ball
{
//	private static MediaPlayer mp = new MediaPlayer();
	private static Bitmap 	m_bitmap = null;
	private static Bitmap 	m_bmpStar = null;
	Paint paint = new Paint();
	float m_x; // ��⸻�� x��ǥ
	float m_y;	// ��⸻�� y��ǥ
	float m_radius; // ��⸻�� ������
	int m_color; // ��⸻�� ��
	boolean m_bExist; // ��⸻�� ��������
	boolean m_bCheck; // ���� ���� Ȯ������
	String m_name; // ��⸻�� �̸�
	String m_sound_name; // ��⸻�� ���� �̸�
	int m_name_x; // ��⸻ �̸��迭�� x��
	int m_name_y; // ��⸻ �̸��迭�� y��
	float m_degrees = 0;
	int m_iRotation = 0;
	public int m_iColorID = 0;
	float m_TotalTime = 0;
	boolean m_bEcho = false;
	boolean m_bStar = false;
	
	public Ball()
	{
	}

	
	/*
	* �ʱ�ȭ �Լ�: �������� �ʱ�ȭ�Ѵ�./
	*/
	public boolean Init()
	{
		m_x = 320.0f;
		m_y = 0.0f;

		if(m_bitmap == null)
		{
			m_bitmap = AppManager.getInstance().getBitmap(R.drawable.ball);	
		}
		if(m_bmpStar == null)
		{
			m_bmpStar = AppManager.getInstance().getBitmap(R.drawable.star);	
		}

		m_radius = m_bitmap.getWidth()/6;
		SetColor(Color.GRAY);
		m_bExist = false;
		m_bCheck = false;
		m_name_x = 0;
		m_name_y = 0;
		m_name = "";
		m_sound_name = "";
		return true;
	}
	
	public void CloneBall(Ball _ball)
	{
		this.SetColor(_ball.GetColor());
		this.SetStar(_ball.GetStar());
//		this.SetNameX(_ball.GetNameX());
//		this.SetNameY(_ball.GetNameY());
//		this.SetName(_ball.GetName());
//		this.SetSoundName(_ball.GetSoundName());
	}
	
	/*
	* �̹��������� �ε��Ѵ�.(���� ������� ����)
	* _file_name: �ε��� ���ϸ�
	* return bool: ������ true, ������ ��� false
	*/
	static boolean Load(String _file_name)
	{		
		return true;
	}
	
	Bitmap GetBitmap(){ return m_bitmap; }
	
	/*
	* ������Ʈ �Լ�: ��ǥ�� �����͵��� �����Ѵ�.
	* return bool: ������ true, ������ ��� false
	*/
	public boolean Update(double _ElapsedTime)
	{
		if(m_bExist == false)
			return true;

		//		m_y += (float)(_ElapsedTime*500.0);

		if(m_y == 0)
		{
			m_TotalTime = 0;
		}
		m_TotalTime += _ElapsedTime;
		m_y += (float)(m_TotalTime*100.0);

		return true;
	}

	/*
	* ��ο� �Լ�: �̹����� �׸���.
	* return bool: ������ true, ������ ��� false
	*/
	public boolean Draw(Canvas c)
	{
		if(m_bEcho == true)
		{
			DrawBallEcho(c);
			m_bEcho = false;
			return true;
		}

		if(m_bExist == false)
		{
			return true;
		}
		
		float src_x = (m_iColorID%3)*m_radius*2;
		float src_y = (m_iColorID/3)*m_radius*2;

//		if(m_iRotation != 0)
//		{
//			Matrix m = new Matrix();
//			m.reset();
//			m.postTranslate(-m_radius, -m_radius);
//			m.postRotate(m_degrees);
//			m.postTranslate(m_radius, m_radius);
//	
//			Bitmap rotBitmap = Bitmap.createBitmap(m_bitmap, (int)src_x, (int)src_y, 
//					(int)(m_radius*2), (int)(m_radius*2),m,true);
//			
//			c.drawBitmap(rotBitmap,m_x, m_y,paint);
//		}
//		else
//		{
			c.drawBitmap(m_bitmap, new Rect((int)src_x,(int)src_y,(int)(src_x+m_radius*2), (int)(src_y+m_radius*2)), 
				new Rect((int)m_x,(int)m_y,(int)(m_x+m_radius*2), (int)(m_y+m_radius*2)), paint);
//		}

		if(GetStar())
		{
			c.drawBitmap(m_bmpStar, new Rect(0,0,20,20), 
					new Rect((int)m_x+20,(int)m_y+20, (int)m_x+40, (int)m_y+40), paint);
		}
		
//		float cx = m_x + m_radius;
//		float cy = m_y + m_radius;
//		paint.setColor(m_color);
//		c.drawCircle(cx,cy,m_radius, paint);

		return true;
	}
	
	boolean GetStar()
	{
		return m_bStar;
	}
	
	void SetStar(boolean _bStar)
	{
		m_bStar = _bStar;
	}
	
	public void DrawBallEcho(Canvas c)
	{
		float m_circle_radius = m_radius;
		
		for(float r = m_circle_radius; r < m_circle_radius*2;r+=5)
		{
			float cx = m_x + m_circle_radius;
			float cy = m_y + m_circle_radius;
			paint.setColor(m_color);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(4);
			c.drawCircle(cx,cy,m_circle_radius, paint);
		}
	}
	
	public void SetEcho(boolean _bEcho)
	{
		m_bEcho = _bEcho;
	}
	
	/*
	* ���� ������ �����ų� �����Ѵ�. 
	*/
	public int GetColor(){ return m_color; }
	public void SetColor(int _color)
	{ 
		m_color = _color;
		
		for(int i = 0; i < 9; i++)
		{
			if(m_color == ColorDesigner.GetIndexColor(i))
			{
				m_iColorID = i;
			}
		}
	}

	/*
	* ���� x.y��ǥ�� �����ų� �����Ѵ�. 
	*/
	public float GetX(){ return m_x; }
	public void SetX(float _x){ m_x = _x; }
	public float GetY(){ return m_y; }
	public void SetY(float _y){ m_y = _y; }

	/*
	* ���� �������� ũ�⸦ �����ų� �����Ѵ�. 
	*/
	public float GetRadius(){ return m_radius; }
	public void SetRadius(float _radius){ m_radius = _radius; } 

	public int GetTileX()
	{ 
		return (int)((m_x-SandBox.GetSize().left)/(m_radius*2)); 
	}

	public int GetTileY()
	{ 
		return (int)((SandBox.GetSize().bottom-m_y)/(m_radius*2)); 
	}

	/*
	* ���� ���� ���ڷ� ���� ���� ���� �浹�ߴ��� Ȯ���Ѵ�.
	* return bool: �浹�� true, �浹���� ��� false
	*/
	int CollisionBall(Ball _pBall)
	{
		if(_pBall.GetExist() == false)
		{
			return 0;
		}

		float dx = this.GetX() - _pBall.GetX();
		float dy = this.GetY() - _pBall.GetY();
		float r = GetRadius();

		if(dx*dx+dy*dy <= r*2*r*2*2)
		{
			if(dx >= 0)
				return -1;
			else
				return 1;
		}
		
		return 0;
	}

	boolean CollisionBox(Ball _pBall)
	{
		if(_pBall.GetExist() == false)
		{
			return false;
		}

		RectF My = new RectF();
		My.left = GetX()- GetRadius();
		My.top = GetY()- GetRadius();
		My.right = GetX()+ GetRadius();
		My.bottom = GetY()+ GetRadius();
		
		RectF You = new RectF();
		You.left = _pBall.GetX()-_pBall.GetRadius();
		You.top = _pBall.GetY()-_pBall.GetRadius();
		You.right = _pBall.GetX()+_pBall.GetRadius();
		You.bottom = _pBall.GetY()+_pBall.GetRadius();
		
		if(!(My.left > You.right || You.right < My.left || 
				My.top > You.bottom  || My.bottom < You.top))
			return true;
		
		return false;
	}
	/* ���� ���� ȭ�鿡 ���̸� �����ϴ��� ����
	* return bool: ������ ��� true, �������� ���� ��� false
	*/
	public boolean GetExist(){return m_bExist;}
	public void SetExist(boolean _bExist){ m_bExist = _bExist; }

	/*
	* ���� ���� ������� ���鶧, 
	* �̹� Ȯ���� ���� ��� true, 
	* Ȯ������ ���� ��� false
	*/
	public boolean GetCheck(){return m_bCheck;}
	public void SetCheck(boolean _bCheck){ m_bCheck = _bCheck; }
	/*
	* ��⸻(��)�� �̸��� �����Ѵ�.
	*/
	public String GetName()
	{
		return m_name;
	}
	public void SetName(String _name)
	{
		m_name = _name;
	}

	/*
	* ��⸻�� �̸��� �ش��ϴ� mp3���� ���� �����ϰų� 
	* ���´�.
	*/
	public void SetSoundName(String _sound_name)
	{
		m_sound_name = _sound_name;		
	}
	
	public String GetSoundName(){ return m_sound_name; }
	
	/*
	*��⸻�� �̸����� �� mp3������ �����Ѵ�.
	*/
	public boolean PlaySound()
	{
//		Context context = HangballGame.GetContext();
//		int audioResourceId = context.getResources().getIdentifier(m_sound_name, "raw", "org.Framework");
//        AssetFileDescriptor afd = context.getResources().openRawResourceFd(audioResourceId);
//
//		try {
//			mp.reset();
//			mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
//			mp.setVolume(1.0f, 1.0f);
//			mp.prepare();
//			mp.start();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		return true;
	}
	
	/*
	* ��⸻ �̸��� �迭 ��ǥ�� �����Ѵ�.
	*/
	public int GetNameX(){ return m_name_x; }
	public int GetNameY(){ return m_name_y; }
	public void SetNameX(int _name_x){m_name_x = _name_x; }
	public void SetNameY(int _name_y){m_name_y = _name_y; }
	// ��⸻�� ȸ����Ų��.
	public void SetRotation(int _iRot){m_iRotation = _iRot;}
	public int GetRotation(){return m_iRotation;}
	
	public void AddDegree(float _fDegree){ m_degrees += _fDegree; }
};
