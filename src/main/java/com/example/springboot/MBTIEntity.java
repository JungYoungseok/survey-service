package com.example.springboot;

public class MBTIEntity {

	public MBTIEntity() {

	}

	private String mbti;
	private String team;
	private long startTime = 0;

	public MBTIEntity(String username, String team) {
		this.mbti = username;
		this.team = team;
	}

	public String getMbti() {
		// TODO Auto-generated method stub
		return this.mbti;
	}

	public String getTeam() {
		// TODO Auto-generated method stub
		return this.team;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

}

