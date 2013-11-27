package main.java.impl.zSetImpl;

public class MemberScore {
	
	
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
