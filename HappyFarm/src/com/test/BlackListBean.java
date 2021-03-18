package com.test;

public class BlackListBean {
	private int  passUserId;
	private int  UserId;
	private long pickTime;
	public int getPassUserId() {
		return passUserId;
	}
	public void setPassUserId(int passUserId) {
		this.passUserId = passUserId;
	}
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	public long getPickTime() {
		return pickTime;
	}
	public void setPickTime(long pickTime) {
		this.pickTime = pickTime;
	}
	@Override
	public String toString() {
		return  passUserId + ":" + UserId
				+ ":" + pickTime +"\r\n";
	}
	
	

}
