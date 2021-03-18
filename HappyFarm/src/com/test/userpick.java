package com.test;

public class userpick {
	private int userId;
	private long pickTime;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public long getPickTime() {
		return pickTime;
	}
	public void setPickTime(long pickTime) {
		this.pickTime = pickTime;
	}
	@Override
	public String toString() {
		return  userId + ":" + pickTime + "\r\n";
	}
	
	
	
	
	
	

}
