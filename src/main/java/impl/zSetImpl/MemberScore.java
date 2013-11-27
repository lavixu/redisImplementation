package main.java.impl.zSetImpl;

import java.io.Serializable;

public class MemberScore implements Serializable{
	private static final long serialVersionUID = 1L;
	public Integer score;
	public String member;
	
	MemberScore(String member, Integer score)
	{
		this.score = score;
		this.member = member;
	}
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

}
