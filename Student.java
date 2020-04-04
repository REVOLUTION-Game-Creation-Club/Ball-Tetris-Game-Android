package com.cfk.hangball;

public class Student 
{
	private String name;
	private int score; 
	private int total_time;
	private boolean m_bNew;
	
	Student()
	{   
	    setName("");
	    setScore(0);
	    setTotalTime(0);
	    setNew(false);
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public int getScore() {
		return score;
	}

	public void setTotalTime(int _total_time) {
		this.total_time = _total_time;
	}


	public int getTotalTime() {
		return this.total_time;
	}

	public boolean getNew(){ return m_bNew;}
	public void setNew(boolean _new){
		m_bNew = _new;
	}
	
	
}
