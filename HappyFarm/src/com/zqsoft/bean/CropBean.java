package com.zqsoft.bean;

import java.util.List;

public class CropBean implements Comparable<CropBean>, Cloneable{
		
	private int cropId;			//农作物编号
	private	String cropName ; 	//农作物名称
	private String seedPic; 	//农作物种子图片
	private int stage;			// 农作物总的生长阶段
	private int sellPrice;		//农作物成熟之后的销售金币数
	private int price;			//农作物种子在商店中的购买金币数
	private int buyLevel; 			//农作物种子在商店中的购买级别
	private List<String> allStagePic;// 农作物所有阶段的图片，从开始阶段，第 1阶段…第 N 阶段，结束阶段。必须按顺序存放
	private List<Integer> allStageTime; //用于存放每个阶段需要的时间，要求按照顺序存放
	private String beginPic= "resources/cron1/cron_start.png"; 		//	  开始播种时在土地上显示的图片
	private String endPic= "resources/cron1/cron_end.png";   		//	  农作物摘取之后显示农作物摘取之后显示
	
	private int current=0;//描述农作物的当前状态
	
	
	
	/**
	 * 在农作物类中增加一个获取当前阶段的图片的方法
	 */
	public String getCurrentpic(){
		if (current == 0) {
			return beginPic;
		}
		if (current <= stage) {
			return allStagePic.get(current - 1);
		}
		return null;
	}
	
	/**
	 * 将农作物对象的生长阶段增加 1。
	 */
	public void  goNextStage(){
		if(current < stage){
			current++;
		}
	}
	
	
	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	/**
	 *  比较两个农作物购买等级和购买金额，要求按照购买等级升序购买金币升序
	 */
	public int compareTo(CropBean o) {
		if (this.buyLevel>o.getBuyLevel()) {
			return 1;
		}else if(this.buyLevel<o.getBuyLevel()){
			return -1;
		}
		return 0;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	@Override
	public String toString() {
		return "CropBean [cropId=" + cropId + ", cropName=" + cropName
				+ ", seedPic=" + seedPic + ", stage=" + stage + ", sellPrice="
				+ sellPrice + ", price=" + price + ", buyLevel=" + buyLevel
				+ ", allStagePic=" + allStagePic + ", allStageTime="
				+ allStageTime + ", beginPic=" + beginPic + ", endPic="
				+ endPic + "]";
	}



	public int getCropId() {
		return cropId;
	}

	public void setCropId(int cropId) {
		this.cropId = cropId;
	}

	public String getCropName() {
		return cropName;
	}

	public void setCropName(String cropName) {
		this.cropName = cropName;
	}

	public String getSeedPic() {
		return seedPic;
	}

	public void setSeedPic(String seedPic) {
		this.seedPic = seedPic;
	}

	public int getStage() {
		return this.stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	public int getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getBuyLevel() {
		return buyLevel;
	}

	public void setBuyLevel(int buyLevel) {
		this.buyLevel = buyLevel;
	}

	public List<String> getAllStagePic() {
		return allStagePic;
	}

	public void setAllStagePic(List<String> allStagePic) {
		this.allStagePic = allStagePic;
	}

	public List<Integer> getAllStageTime() {
		return allStageTime;
	}

	public void setAllStageTime(List<Integer> allStageTime) {
		this.allStageTime = allStageTime;
	}

	public String getBeginPic() {
		return beginPic;
	}

	public void setBeginPic(String beginPic) {
		this.beginPic = beginPic;
	}

	public String getEndPic() {
		return endPic;
	}

	public void setEndPic(String endPic) {
		this.endPic = endPic;
	}
	  

    public long getStageMillsTime(int stage){
    	long alltime=0;
    	for(int i =0;i<=stage;i++){
    		alltime+=this.allStageTime.get(i);
    	}
    	return alltime*1000;
    }
	
	  
	
	
	
	
	
	
	  
	
	


	

}
