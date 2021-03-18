package com.zqsoft.thread;

import com.zqsoft.bean.LandItemBean;

public class CropGrowThread extends Thread{
	private LandItemBean cronItemBean; //土地明细对象
	private  Boolean Isrun=true; //  开关变量，是否需要运行，该参数为停止线程使用。
	
	
	
	
	public CropGrowThread(LandItemBean cronItemBean, Boolean isrun) {
		if(cronItemBean.getCount()!=0&&cronItemBean.getCropBean()!=null){
			this.cronItemBean = cronItemBean;
			Isrun = isrun;
		}
	
	}
	public CropGrowThread() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	 public CropGrowThread(LandItemBean land) {
		this.cronItemBean = land;
	}
	/**
	   * 停止线程
	   */
	public  void stopgrown(){
		this.Isrun=false;
		
	}
	/**
	 * 线程运行执行的方法
	 */
	public void run() {
		if(this.cronItemBean==null) return;
		while (Isrun) {//判断开关是否在运行状态
		this.cronItemBean.growing ();//执行自动农作物自动生长
		try {
			sleep(300);//休眠 300 毫秒，让出 CPU
		} catch (InterruptedException e) {
//		e.printStackTrace();
		}
	}
	}

	
	
	
	
	
	public LandItemBean getCronItemBean() {
		return cronItemBean;
	}


	public void setCronItemBean(LandItemBean cronItemBean) {
		this.cronItemBean = cronItemBean;
	}


	public Boolean getIsrun() {
		return Isrun;
	}


	public void setIsrun(Boolean isrun) {
		Isrun = isrun;
	}
	
	
	
}
