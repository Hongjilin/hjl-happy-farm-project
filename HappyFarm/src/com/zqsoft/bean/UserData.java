package com.zqsoft.bean;

public class UserData {
	private int exp; //经验
	private int money; //金币
	private int userid; // 用户id
	/**
	 * 根据用户经验计算等级
	 * @return
	 */
	public int  getUserLevel(){
		
		return (int)exp/100;
	}
	
	
	
	@Override
	public String toString() {
		return this.userid+";"+this.exp+";"+this.money;
	}



	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	 
	

}
