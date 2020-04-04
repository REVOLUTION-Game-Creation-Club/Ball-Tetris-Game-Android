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
* ImBall 장기말
*/
public class Ball
{
//	private static MediaPlayer mp = new MediaPlayer();
	private static Bitmap 	m_bitmap = null;
	private static Bitmap 	m_bmpStar = null;
	Paint paint = new Paint();
	float m_x; // 장기말의 x좌표
	float m_y;	// 장기말의 y좌표
	float m_radius; // 장기말의 반지름
	int m_color; // 장기말의 색
	boolean m_bExist; // 장기말의 존재유무
	boolean m_bCheck; // 같은 색의 확인유무
	String m_name; // 장기말의 이름
	String m_sound_name; // 장기말의 사운드 이름
	int m_name_x; // 장기말 이름배열의 x값
	int m_name_y; // 장기말 이름배열의 y값
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
	* 초기화 함수: 변수들을 초기화한다./
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
	* 이미지파일을 로드한다.(아직 사용하지 않음)
	* _file_name: 로드할 파일명
	* return bool: 성공시 true, 실패할 경우 false
	*/
	static boolean Load(String _file_name)
	{		
		return true;
	}
	
	Bitmap GetBitmap(){ return m_bitmap; }
	
	/*
	* 업데이트 함수: 좌표나 데이터등을 변경한다.
	* return bool: 성공시 true, 실패할 경우 false
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
	* 드로우 함수: 이미지를 그린다.
	* return bool: 성공시 true, 실패할 경우 false
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
	* 공의 색깔을 얻어오거나 설정한다. 
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
	* 공의 x.y좌표을 얻어오거나 설정한다. 
	*/
	public float GetX(){ return m_x; }
	public void SetX(float _x){ m_x = _x; }
	public float GetY(){ return m_y; }
	public void SetY(float _y){ m_y = _y; }

	/*
	* 공의 반지름의 크기를 얻어오거나 설정한다. 
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
	* 현재 공과 인자로 들어온 공이 서로 충돌했는지 확인한다.
	* return bool: 충돌시 true, 충돌안한 경우 false
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
	/* 현재 공이 화면에 보이며 존재하는지 여부
	* return bool: 존재할 경우 true, 존재하지 않을 경우 false
	*/
	public boolean GetExist(){return m_bExist;}
	public void SetExist(boolean _bExist){ m_bExist = _bExist; }

	/*
	* 같은 색을 사라지게 만들때, 
	* 이미 확인한 공의 경우 true, 
	* 확인하지 않은 경우 false
	*/
	public boolean GetCheck(){return m_bCheck;}
	public void SetCheck(boolean _bCheck){ m_bCheck = _bCheck; }
	/*
	* 장기말(공)의 이름을 설정한다.
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
	* 장기말의 이름에 해당하는 mp3파일 명을 지정하거나 
	* 얻어온다.
	*/
	public void SetSoundName(String _sound_name)
	{
		m_sound_name = _sound_name;		
	}
	
	public String GetSoundName(){ return m_sound_name; }
	
	/*
	*장기말의 이름으로 된 mp3파일을 연주한다.
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
	* 장기말 이름의 배열 좌표을 설정한다.
	*/
	public int GetNameX(){ return m_name_x; }
	public int GetNameY(){ return m_name_y; }
	public void SetNameX(int _name_x){m_name_x = _name_x; }
	public void SetNameY(int _name_y){m_name_y = _name_y; }
	// 장기말이 회전시킨다.
	public void SetRotation(int _iRot){m_iRotation = _iRot;}
	public int GetRotation(){return m_iRotation;}
	
	public void AddDegree(float _fDegree){ m_degrees += _fDegree; }
};
