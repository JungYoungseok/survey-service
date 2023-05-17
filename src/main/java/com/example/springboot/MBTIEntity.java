package com.example.springboot;

public class MBTIEntity {

	public MBTIEntity() {

	}

	private String mbti;
	private String team;
	private long startTime = 0;
	private String job;
	private String nickname;
	private String datadog_user;

	public MBTIEntity(String username, String team) {
		this.mbti = username;
		this.team = team;
	}

	public MBTIEntity(String nickname, String job, String datadog_user) {
		this.datadog_user = datadog_user;
		this.job = job;
		this.nickname = nickname;
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

	public String getJob() {
		return this.job;
	}

	public String getNickname() {
		return this.nickname;
	}

	public String getDatadog_user() {
		return this.datadog_user;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

}

