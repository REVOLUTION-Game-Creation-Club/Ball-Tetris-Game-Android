package com.cfk.hangball;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Rank
{

	// 랭킹 정보
	static ArrayList<Student> m_vecRank = new ArrayList<Student>();
	Paint paint = new Paint();
	private static BufferedReader br;
	
	public boolean Init()
	{
		return true;
	}
	
	public boolean Load(String _file_name)
	{	
		return true;
	}
	
	public boolean Update(double _ElapsedTime)
	{
		return true;
	}
	
	public boolean Draw(Canvas c)
	{
		int size = m_vecRank.size();
		if(size > 20)
		{
			size = 20;
		}
		
		for(int i = 0; i < size; i++)
		{
			String name = m_vecRank.get(i).getName();
			int score = m_vecRank.get(i).getScore();
			int total_time = m_vecRank.get(i).getTotalTime();
			paint.setTextSize(30.0f);
			if(m_vecRank.get(i).getNew() == true)
			{
				paint.setColor(Color.MAGENTA);
			}
			else
			{
				paint.setColor(Color.BLACK);
			}
			
			int hour = total_time/3600;
			int min = total_time/60;
			int sec = total_time%60;
			c.drawText(String.format("%d위 %s %d시간%d분%d초 %d점",i+1,name,hour,min,sec,score),
					100, 100+i*30, paint );
		}
		return true;
	}

	/* 
	* _pName 이름
	* _iScore 스코어
	* 랭킹정보를 입력한다.
	*/
	public static int PushRank(String _pName, int _iScore, int _fTotalTime)
	{
		Student student = new Student();
		student.setName(_pName);
		student.setScore(_iScore);
		student.setTotalTime(_fTotalTime);
		student.setNew(true);
		m_vecRank.add(student);

		return 0;
	}
	
	public static void SortRank()
	{
	    Comparator<Student> kCompare = new Comparator<Student>()
	    {
	        @Override
	        public int compare(Student lhs, Student rhs)
	        {
	        	if(lhs.getScore() == rhs.getScore())
	        		return 0;
	        	else if(lhs.getScore() < rhs.getScore())
	            	return 1;
	            else
	            	return -1;
	        }
	    };
	    
	    Collections.sort(m_vecRank, kCompare);
	}
	
	/*
	* rank.txt에 입력된 유저이름과 스코어정보를 
	* 메모리로 읽어들인다.
	*/
	public static boolean ReadFromFile()
	{
		m_vecRank.clear();
		
		try {
			File file = new File(HangballGame.GetContext().getFilesDir(),"rank.txt");
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			String read = br.readLine();
			while(read != null)
			{
				StringTokenizer st = new StringTokenizer(read," ");
				
				String name = st.nextToken();
				String score = st.nextToken();
				String total_time = st.nextToken();
				
				Student student = new Student();
 				student.setName(name);
				student.setScore(Integer.parseInt(score));
				student.setTotalTime(Integer.parseInt(total_time));
				student.setNew(false);
				m_vecRank.add(student);
				
				read = br.readLine();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	/*
	* 메모리에 있는 유저이름과 스코어정보(m_vecRank)를
	* 파일에다 기록한다.
	*/
	public static boolean WriteToFile()
	{
		try {
			File file = new File(HangballGame.GetContext().getFilesDir(), "rank.txt");
			FileWriter fw = new FileWriter(file,false);
			
			for(int i = 0; i < m_vecRank.size(); i++)
			{
				Student student = m_vecRank.get(i);
				String name = student.getName();
				int score = student.getScore();
				int total_time = student.getTotalTime();
	
				StringBuilder sb = new StringBuilder();
				sb.append(name+" "+score+" "+total_time+"\n");
				
				fw.write(sb.toString());						
			}
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}
};