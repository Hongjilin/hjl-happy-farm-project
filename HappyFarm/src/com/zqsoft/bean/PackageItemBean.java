package com.zqsoft.bean;

import com.zqsoft.frame.GameMember;
import com.zqsoft.guiHelper.bean.PackageItem;

public class PackageItemBean implements PackageItem{
	private CropBean cropBean;	
	private int ItemCount;
	
	
	
	
	@Override
	public String toString() {
		return this.cropBean.getCropId()+":"+this.ItemCount;
	}


	public PackageItemBean(CropBean cropBean, int itemCount) {
		this.cropBean = cropBean;
		ItemCount = itemCount;
	}
	
	
	public PackageItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CropBean getCropBean() {
		return cropBean;
	}
	public void setCropBean(CropBean cropBean) {
		this.cropBean = cropBean;
	}
	public int getItemCount() {
		return this.ItemCount;
	}
	/**
	 * 返回包裹中该种子的数量
	 * @return
	 */
	public int ItemCount(){
		return ItemCount;
	}
	/**
	 * 返回包裹中该种子的名称
	 * @return
	 */
	public String getItemName(){
		
		return this.cropBean.getCropName();
	}
	/**
	 * 返回包裹中该种子的图片
	 * @return
	 */
	public String getItemPic(){
		return this.cropBean.getSeedPic();
		
	}
	/**
	 * 返回包裹中该种子的农作物 ID
	 * @return
	 */
	public int getCropId(){
		
		return this.cropBean.getCropId();
		
	}
	/**
	 * 修改包裹中该种子的数量
	 * @param Item_count
	 */
	public void setItemCount(int Item_count){
		
		this.ItemCount=this.ItemCount+Item_count;
	}
	/**
	 * 点击选中需要播种的种子信息，该方法在播种任务中完成，本次无需完成
	 * @return
	 */
	public boolean itemClick(){
		GameMember.mouseState=1;
		GameMember.selectdCropid=this.cropBean.getCropId();
//		System.out.println("选中");
		return true;
	}
	/**
	 * 取消选中的种子
	 */
	public void cancelClick(){
		GameMember.mouseState=0;
		GameMember.selectdCropid=this.cropBean.getCropId();
		System.out.println("取消");
		
	}
	
	
}
