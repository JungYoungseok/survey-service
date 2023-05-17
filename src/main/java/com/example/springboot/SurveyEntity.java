package com.example.springboot;

public class SurveyEntity {

	public SurveyEntity() {

	}

	private String datadog_user;
	private String job;
	private String nickname;
	private long startTime = 0;

	public SurveyEntity(String nickname, String job, String datadog_user) {
		this.nickname = nickname;
		this.job = job;
		this.datadog_user = datadog_user;
	}

	public String getNickname() {
		// TODO Auto-generated method stub
		return this.nickname;
	}

	public String getJob() {
		// TODO Auto-generated method stub
		return this.job;
	}

        public String getDatadog_user() {
                // TODO Auto-generated method stub
                return this.datadog_user;
        }	

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

}

