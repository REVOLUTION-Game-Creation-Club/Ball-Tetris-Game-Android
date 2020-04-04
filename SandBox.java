package com.cfk.hangball;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

/*
 * �ٱ���
 */
public class SandBox {
	static RectF m_Size = new RectF(); // �ٱ����� ������

	Ball m_pNewBall = null; // �������� ��
	Ball[][] m_ppBall = null; // ���� �迭(���� ���� �ְ� �Ⱥ��� ���� ����)
	int m_array_ball_w; // �ٱ��� �ȿ� ���� �ִ� ���� ���� ����
	int m_array_ball_h; // �ٱ��� �ȿ� ���� �ִ� ���� ���� ����
	ArrayList<Ball> m_vecSameBalls = new ArrayList<Ball>(); // ���� ���� ���� ����Ʈ
	float m_fLimitTime = 0; // ���ѽð��� ������ �Ʒ����� ���� �ö��
	float m_fPlayTime = 0;
	// ���� ������� �� �� ���� 1��.
	// �������� 3���̻� ����������
	// ������� �� ���� ������ ��������� ��
	int m_iScore;
	
	Paint paint = new Paint();
	ArrayList<Point> m_vecMoveList = new ArrayList<Point>();
	Mission theMission = new Mission();
	int difficult = 1;
	int m_bStarEffectColor = -1;
	boolean m_bStarEffect = false;
	
	public SandBox() {

	}

	/*
	 * �ʱ�ȭ �Լ�
	 */
	public boolean Init() {
		m_pNewBall = new Ball();
		m_pNewBall.Init();

		m_Size.left = 60;
		m_Size.right = 640 - 60;
		m_Size.top = 100;
		m_Size.bottom = 960 - 60;


		m_array_ball_h = (int) ((m_Size.bottom - m_Size.top) / (m_pNewBall
				.GetRadius() * 2));
		m_array_ball_w = (int) ((m_Size.right - m_Size.left) / (m_pNewBall
				.GetRadius() * 2));
		
		m_pNewBall = null;
		
		m_ppBall = new Ball[m_array_ball_h][];

		for (int h = 0; h < m_array_ball_h; h++) {
			m_ppBall[h] = new Ball[m_array_ball_w];
			for (int w = 0; w < m_array_ball_w; w++) {
				m_ppBall[h][w] = new Ball();
				m_ppBall[h][w].Init();
				
				float r = m_ppBall[h][w].GetRadius();
				float x = (float) SandBox.GetSize().left;
				float y = (float) SandBox.GetSize().bottom;

				switch (h % 2) {
				case 0:
					m_ppBall[h][w].SetX(x + w * r * 2);
					break;
				case 1:
					m_ppBall[h][w].SetX(x + w * r * 2 + r);
					break;
				}
				m_ppBall[h][w].SetY(y - h * r * 2 - r * 2);

				m_ppBall[h][w].SetExist(false);
			}
		}

		m_fLimitTime = 0.0f;
		m_fPlayTime = 0.0f;
		m_iScore = 0;
		
		theMission.Init(difficult);
		for(int i = 0; i < 4+(int)(difficult/2); i++)
		{
			UpBallLine();
		}
		return true;
	}

	/*
	 * �̹����ε� �Լ�(���� ������� ����)
	 */
	public boolean Load(String _file_name) {

		return true;
	}

	/*
	 * ������ ������Ʈ ó�� �Լ�
	 */
	public boolean Update(double _ElapsedTime) {
		 m_fLimitTime += _ElapsedTime;
		 m_fPlayTime += _ElapsedTime;
		 if(m_fLimitTime >= 30.0f)
		 {
			 m_fLimitTime = 0.0f;
			 UpBallLine();
		 }

		if (CheckGameEnd() == true) {			 
			 Rank.ReadFromFile();
			 Rank.PushRank("�����",m_iScore,(int)m_fPlayTime);
			 Rank.SortRank();
			 Rank.WriteToFile();
			
			 HangballGame.SetStage(9); // ��ŷȭ��
			 return true;
		}

		if(StageClear() == true)
		{
			difficult++;
			Init();
		}
		
		DropBallAll();
		RemoveSameColorBallAll();
		
		if(m_bStarEffect == true)
		{
			StarEffect(m_bStarEffectColor);
			m_bStarEffect = false;
		}
		
		if (m_pNewBall == null) {
			return true;
		}


		m_pNewBall.Update(_ElapsedTime);
		// ���� ���� �� �Ʒ��� ����߸���

		CollisionBallAll();
		CollisionLine();

		return true;
	}

	void DrawMoveList(Canvas c)
	{
		Point start_p;
		Point end_p;
		
		paint.setColor(Color.YELLOW);
		paint.setStrokeWidth(4);
		
		for(int i = 0; i < m_vecMoveList.size(); i++)
		{
			if(i != 0)
			{
				start_p = m_vecMoveList.get(i-1);
				end_p = m_vecMoveList.get(i);
				c.drawLine(start_p.x+30, start_p.y+30, end_p.x+30, end_p.y+30, paint);
			}
		}
	}
	
	void DrawLimitTime(Canvas c)
	{
		paint.setColor(Color.RED);
		paint.setStrokeWidth(10);
		
		
		c.drawLine(30, 200+m_fLimitTime*20, 30, 200+600, paint);
	}
	/*
	 * �̹��� �׸��� �Լ�
	 */
	public boolean Draw(Canvas c) {
//		DebugView(c);
		DrawBoxLine(c);
		
		for(int h = 0; h < m_array_ball_h; h++){
			for(int w = 0; w < m_array_ball_w; w++){
				 if(m_ppBall[h][w].GetExist() == false)
					 continue;
				
				 m_ppBall[h][w].Draw(c);
			}
		}

		if (m_pNewBall != null) {
			m_pNewBall.Draw(c);
		}

		theMission.Draw(c);

		//DrawMoveList(c);
		DrawLimitTime(c);
//		paint.setColor(Color.RED);
//		paint.setTextSize(40.0f);
//		c.drawText(String.format("%d", m_iScore), 100, 100, paint);
//
//		
//		paint.setColor(Color.BLUE);
//		paint.setTextSize(30);
//		c.drawText(String.format("%f", m_fLimitTime), 500, 50, paint);

		return true;
	}

	/*
	 * ũ�������� ���� ����� �Լ� ���� ������ �޾Ƽ� ȭ�鿡 ǥ���ϰ� ������Ʈ�Ѵ�.
	 */
	public void SetBall(Ball _pBall) {		
		m_pNewBall = _pBall;
		m_pNewBall.SetStar(_pBall.GetStar());
		m_pNewBall.SetExist(true);
		
		m_vecMoveList.clear();
	}

	/*
	 * �ٱ����� ������ ���´�.
	 */
	static RectF GetSize() {
		return m_Size;
	}

	/*
	 * �ٱ����� ����, ���ο� ���� ���� ����
	 */
	public int GetArrayW() {
		return m_array_ball_w;
	}

	public int GetArrayH() {
		return m_array_ball_h;
	}

	/*
	 * ��(��⸻)�� �迭���� ���� ������ ���´�.
	 */
	public Ball GetBall(int _tile_x, int _tile_y) {
		if (_tile_x < 0)
			return null;
		if (_tile_y < 0)
			return null;
		if (_tile_x >= GetArrayW())
			return null;
		if (_tile_y >= GetArrayH())
			return null;

		return m_ppBall[_tile_y][_tile_x];
	}

	/*
	 * �������� ��(NewBall)�� ���´�.
	 */
	public Ball GetNewBall() {
		return m_pNewBall;
	}

	/*
	 * ������ ��ġ�� ���� �Ʒ��� �������� �Ѵ�.
	 */
	private boolean DropBallAll() {
		for (int h = m_array_ball_h - 1; h > 0; h--) {
			for (int w = 0; w < m_array_ball_w; w++) {
				if (m_ppBall[h][w].GetExist() == false)
					continue;

				DropBall(h, w);
//				m_ppBall[h][w].SetRotation(0);
			}
		}
		
		return true;
	}

	/*
	 * ���� ���� 3���̻� �ִٸ� 3���� ���� ������� �Ѵ�. ���������� �������� ������ ���������� ������ ������� �Ѵ�. �Ͼ��������
	 * ���������� ���� ���� �ƴϸ� ���ӵǴ��� ������� �ʴ´�.
	 */
	private boolean RemoveSameColorBallAll() {
		for (int h = m_array_ball_h - 1; h >= 0; h--) {
			for (int w = 0; w < m_array_ball_w; w++) {
				if (m_ppBall[h][w].GetExist() == false)
					continue;

				
				int count = SameColorBall(h, w,m_ppBall[h][w].GetColor(), 0);
				if (count <= 2) {
					for (Ball itr : m_vecSameBalls) {
						itr.SetCheck(false);
					}
					m_vecSameBalls.clear();
				} 
				else 
				{

					int color = -1;
					for (Ball itr : m_vecSameBalls) {
						 
						color = itr.GetColor();
						ReduceMissionBallCount(color);
						if(itr.GetStar() == true)
						{
							m_bStarEffectColor = color;
							m_bStarEffect = true;
						}
						itr.SetCheck(false);
						itr.SetEcho(true);
						itr.SetExist(false);
					}


					int size = m_vecSameBalls.size();
					if( size > 3)
					{
						m_iScore += size*size;
					}
					
					m_vecSameBalls.clear();
				}
			}
		}

		return true;
	}

	/*
	 * �Ʒ����� ������ ���پ� �ö�´�.(���� ������� ����)
	 */
	private boolean UpBallLine() 
	{
		for (int h = m_array_ball_h-1; h > 0 ; h--) {
			for (int w = 0; w < m_array_ball_w; w++) {
				m_ppBall[h][w].CloneBall(m_ppBall[h-1][w]);
				boolean exist = m_ppBall[h-1][w].GetExist();
				m_ppBall[h][w].SetExist(exist);
			}
		}

		for (int w = 0; w < m_array_ball_w; w++) {
			Ball ball = new Ball();
			int c = ColorDesigner.GetRandomColor();
			ball.SetColor(c);
			m_ppBall[0][w].CloneBall(ball); 
			m_ppBall[0][w].SetExist(true);
		}
		
		return true;
	}


	/*
	 * h,w: ���� ���� ��ǥ DropBallAll()�� �����Լ� ������ ��ǥ ������ ���� �Ʒ��� �������� �Ѵ�.
	 */
	private boolean DropBall(int h, int w) {
		// �ٷ� �Ʒ��� ����ٸ�
		if (m_ppBall[h - 1][w].GetExist() == false) {
			m_ppBall[h][w].SetExist(false);
			m_ppBall[h - 1][w].CloneBall(m_ppBall[h][w]);
			m_ppBall[h - 1][w].SetExist(true);
			
			Point p = new Point();
			p.x = (int)m_ppBall[h-1][w].GetX();
			p.y = (int)m_ppBall[h-1][w].GetY();
			m_vecMoveList.add(p);

			return true;
		}

		switch(h%2)
		{
		case 0:
			if (w > 0) {
				// �Ʒ� ������ ����ٸ�
				if (m_ppBall[h - 1][w - 1].GetExist() == false) {
					m_ppBall[h][w].SetExist(false);
					m_ppBall[h - 1][w - 1].CloneBall(m_ppBall[h][w]);
					m_ppBall[h - 1][w - 1].SetExist(true);

					Point p = new Point();
					p.x = (int)m_ppBall[h-1][w-1].GetX();
					p.y = (int)m_ppBall[h-1][w-1].GetY();
					m_vecMoveList.add(p);

					return true;
				} 
			}
			

		break;
		
		case 1:
			if ((w + 1) < m_array_ball_w) {
				// �Ʒ� �������� ����ٸ�
				if (m_ppBall[h - 1][w + 1].GetExist() == false) {
					m_ppBall[h][w].SetExist(false);
					m_ppBall[h - 1][w + 1].CloneBall(m_ppBall[h][w]);
					m_ppBall[h - 1][w + 1].SetExist(true);

					Point p = new Point();
					p.x = (int)m_ppBall[h-1][w+1].GetX();
					p.y = (int)m_ppBall[h-1][w+1].GetY();
					m_vecMoveList.add(p);

//					if(m_ppBall[h-1][w +1].GetRotation() == 1)
//					{
//						m_ppBall[h-1][w +1].AddDegree(60.0f);
//					}
//					else if(m_ppBall[h-1][w +1].GetRotation() == -1)
//					{
//						m_ppBall[h-1][w +1].AddDegree(-60.0f);
//					}

					return true;
				}
			}
			
			break;
		}

		return false;
	}

	private boolean StarEffect(int color) 
	{
		for (int h = m_array_ball_h - 1; h > 0; h--) {
			for (int w = 0; w < m_array_ball_w; w++) {
				if (m_ppBall[h][w].GetExist() == false)
					continue;
				
				if(m_ppBall[h][w].GetColor() == color)
				{
					m_ppBall[h][w].SetExist(false);
					m_ppBall[h][w].SetEcho(true);
				}
			}
		}
		
		return true;
	}

	boolean ReduceMissionBallCount(int color)
	{
		for(int i = 0; i < 3; i++)
		{
			if(color == theMission.BallColor[i])
			{
				theMission.BallCount[i]--;
				if(theMission.BallCount[i] < 0)
				{
					theMission.BallCount[i] = 0;
				}
				
				return true;
			}
		}
		
		return false;
	}

	/*
	 * h, w: ���� ���� ��ǥ RemoveSameColorBallAll()�� �����Լ� ������ �˻��Ͽ� ���� ���� ��������� ã�´�.
	 */
	private int SameColorBall(int h, int w, int color, int count) 
	{
		if (m_ppBall[h][w].GetExist() == false)
			return -1;

		for (int i = -1; i < 2; i++) {
			for (int k = -1; k < 2; k++) {
				if ((i == 0) && (k == 0))
					continue;
				if (h % 2 == 0) {
					if ((i == 1) && (k == 1))
						continue;
					if ((i == -1) && (k == 1))
						continue;
				} else {
					if ((i == 1) && (k == -1))
						continue;
					if ((i == -1) && (k == -1))
						continue;
				}

				if ((h + i < 0) || (h + i > m_array_ball_h - 1))
					continue;
				if ((w + k < 0) || (w + k > m_array_ball_w - 1))
					continue;

				if (m_ppBall[h + i][w + k].GetExist() == false)
					continue;
				if (m_ppBall[h + i][w + k].GetCheck() == true)
					continue;

				int color1 = m_ppBall[h][w].m_iColorID;
				int cc2 = m_ppBall[h + i][w + k].m_iColorID;

				if (color1 == 1) // white
					continue;
				if (cc2 == 1) // white
					continue;

				if (color1 == cc2) {
					if (count == 0) {
						m_vecSameBalls.clear();
						m_vecSameBalls.add(m_ppBall[h][w]);
						m_ppBall[h][w].SetCheck(true);
						count = 1;
					}

					m_vecSameBalls.add(m_ppBall[h + i][w + k]);

					m_ppBall[h + i][w + k].SetCheck(true);

					SameColorBall(h + i, w + k, color1, count + 1);
				}
			}
		}

		return m_vecSameBalls.size();
	}

	/*
	 * �� �迭�� ��ġ�� ������ ǥ���Ѵ�. ����� �뵵�� ���ȴ�.
	 */
	@SuppressWarnings("unused")
	private void DebugView(Canvas c) {
		for (int h = 0; h < m_array_ball_h; h++) {
			for (int w = 0; w < m_array_ball_w; w++) {
				float x = m_ppBall[h][w].GetX();
				float y = m_ppBall[h][w].GetY();
				float r = m_ppBall[h][w].GetRadius();

				paint.setTextSize(20.0f);
				paint.setColor(Color.MAGENTA);
				c.drawOval(new RectF(x, y, x + r * 2, y + r * 2), paint);
				c.drawText(String.format("%d,%d", h, w), x, y, paint);
			}
		}
	}

	/*
	 * ���� �� ����(m_Size)�� ǥ���Ѵ�.
	 */
	private void DrawBoxLine(Canvas c) {
		paint.setColor(Color.GREEN);

		c.drawLine(m_Size.left, m_Size.top, m_Size.left, m_Size.bottom, paint);
		c.drawLine(m_Size.right, m_Size.top, m_Size.right, m_Size.bottom, paint);
		c.drawLine(m_Size.left, m_Size.bottom, m_Size.right, m_Size.bottom,
				paint);
	}

	/*
	 * ���ο� ���� �׿��� ���� �浹�ߴ��� ���θ� ó���Ѵ�.
	 */
	private boolean CollisionBallAll() {
		if(m_pNewBall == null)
			return false;
		
		int w = m_pNewBall.GetTileX();
		for (int h = 0; h < m_array_ball_h-1; h++) {
				
			if(CollisionVertical(w, h) == false)
				continue;

			int c = m_pNewBall.m_iColorID;

			if (c == 0) // Bomb// black
			{
				BombBall(h, w);
				return true;
			}
			
			if(SettleBall(h, w) == true)
			{
				return true;
			}
		}

		return false;
	}

	private boolean CollisionVertical(int w, int h) {
		int ret = m_pNewBall.CollisionBall(m_ppBall[h][w]);
		if (ret != 0)
			return true;

		if(w-1 >= 0)
		{
			ret = m_pNewBall.CollisionBall(m_ppBall[h][w-1]);
			if (ret != 0)
				return true;
		}

		if(w+1 < m_array_ball_w)
		{
			ret = m_pNewBall.CollisionBall(m_ppBall[h][w+1]);
			if (ret != 0)
				return true;
		}
		
		return false;
	}
	
	/*
	 * ���� ������ ��ġ�� �̵���Ų��.
	 */
	private boolean SettleBall(int h, int w) 
	{
		if (m_ppBall[h][w].GetExist() == false)
		{
			m_ppBall[h][w].CloneBall(m_pNewBall);
			m_ppBall[h][w].SetExist(true);			
			m_pNewBall = null;
			
			Point p = new Point();
			p.x = (int)m_ppBall[h][w].GetX();
			p.y = (int)m_ppBall[h][w].GetY();
			m_vecMoveList.add(p);

			return true;
		}

		if(h+1 >= m_array_ball_h)
			return false;
		
		if(m_ppBall[h + 1][w].GetExist() == false) 
		{
			m_ppBall[h + 1][w].CloneBall(m_pNewBall);
			m_ppBall[h + 1][w].SetExist(true);
			m_pNewBall = null;

			Point p = new Point();
			p.x = (int)m_ppBall[h+1][w].GetX();
			p.y = (int)m_ppBall[h+1][w].GetY();
			m_vecMoveList.add(p);

			return true;
		}

		if(w-1 < 0)
			return false;

		if (m_ppBall[h][w - 1].GetExist() == false) 
		{
			m_ppBall[h][w - 1].CloneBall(m_pNewBall);
			m_ppBall[h][w - 1].SetExist(true);
			m_pNewBall = null;
			
			Point p = new Point();
			p.x = (int)m_ppBall[h][w-1].GetX();
			p.y = (int)m_ppBall[h][w-1].GetY();
			m_vecMoveList.add(p);

			return true;
		}


		if(w+1 >= m_array_ball_w)
			return false;
		

		if (m_ppBall[h][w + 1].GetExist() == false)
		{
			m_ppBall[h][w + 1].CloneBall(m_pNewBall);
			m_ppBall[h][w + 1].SetExist(true);
			m_pNewBall = null;

			Point p = new Point();
			p.x = (int)m_ppBall[h][w+1].GetX();
			p.y = (int)m_ppBall[h][w+1].GetY();
			m_vecMoveList.add(p);

			return true;
		}

		if (m_ppBall[h + 1][w - 1].GetExist() == false) 
		{
			m_ppBall[h + 1][w - 1].CloneBall(m_pNewBall);
			m_ppBall[h + 1][w - 1].SetExist(true);
			m_pNewBall = null;

			Point p = new Point();
			p.x = (int)m_ppBall[h+1][w-1].GetX();
			p.y = (int)m_ppBall[h+1][w-1].GetY();
			m_vecMoveList.add(p);

			return true;
		}

		if (m_ppBall[h + 1][w + 1].GetExist() == false)
		{
			m_ppBall[h + 1][w + 1].CloneBall(m_pNewBall);
			m_ppBall[h + 1][w + 1].SetExist(true);
			m_pNewBall = null;

			Point p = new Point();
			p.x = (int)m_ppBall[h+1][w+1].GetX();
			p.y = (int)m_ppBall[h+1][w+1].GetY();
			m_vecMoveList.add(p);

			return true;
		}

		return false;
	}

	/*
	 * ���ο� ���� �Ʒ������� �浹�ߴ��� ���θ� ó���Ѵ�.
	 */
	private boolean CollisionLine() {
		if (m_pNewBall == null)
			return false;

		float end_line_y = (float) (GetSize().bottom - m_pNewBall.GetRadius());

		float r = m_pNewBall.GetRadius();
		
		if (m_pNewBall.GetY() >= end_line_y-r) {
			m_pNewBall.SetY(end_line_y);

			int tile_x = m_pNewBall.GetTileX();
			if (tile_x < 0) {
				tile_x = 0;
			}
			int tile_y = m_pNewBall.GetTileY();
			if (tile_y < 0) {
				tile_y = 0;
			}

//			int color = m_pNewBall.GetColor();
//			if (color == Color.BLACK) // bomb
//			{
//				BombBall(tile_x, tile_y);
//			} 
			if (m_ppBall[tile_y][tile_x].GetExist() == false) 
			{
				m_ppBall[tile_y][tile_x].CloneBall(m_pNewBall);
				m_ppBall[tile_y][tile_x].SetExist(true);
			}

			if (m_pNewBall != null) {
				m_pNewBall = null;
			}

			return true;
		}

		return false;
	}

	/*
	 * ���� �����ο� ���� ���̸� ������ ������. ���� ���� ������� Ȯ���Ѵ�.
	 */
	private boolean CheckGameEnd() {
		for (int w = 0; w < m_array_ball_w; w++) {
			if (m_ppBall[m_array_ball_h - 1][w].GetExist() == true) {
				return true;
			}
		}
		
		return false;
	}

	private boolean StageClear()
	{
		if((theMission.BallCount[0] == 0)&&(theMission.BallCount[1] == 0)&&
				(theMission.BallCount[2] == 0))
		{
			return true;
		}
		
		return false;
	}

	/*
	 * h,w: �� �迭�� ��ǥ ������ ��(��ź ��)�� ������ ��ġ ������ ������ ������� �Ѵ�.
	 */
	private int BombBall(int h, int w) {
		for (int i = -1; i < 2; i++) {
			for (int k = -1; k < 2; k++) {
				if ((h + i < 0) || (h + i > m_array_ball_h - 1))
					continue;
				if ((w + k < 0) || (w + k > m_array_ball_w - 1))
					continue;

				if (m_ppBall[h + i][w + k].GetExist() == false)
					continue;

				m_ppBall[h + i][w + k].SetExist(false);
				m_iScore += 1;
			}
		}

		if(m_pNewBall != null)
		{
			m_pNewBall = null;
		}

		return 0;
	}
	
};
