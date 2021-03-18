package com.zqsoft.bean;

import java.util.Iterator;

import javax.swing.JOptionPane;

import com.zqsoft.dao.StoreHouseDAO;
import com.zqsoft.dao.UserDateDao;
import com.zqsoft.frame.GameMember;
import com.zqsoft.guiHelper.bean.PackageItem;
import com.zqsoft.guiHelper.bean.StoreHouseItem;
import com.zqsoft.ncfarm.core.MessageDialogHelper;
import com.zqsoft.utils.FileUtils;

public class StoreHouseItemBean implements StoreHouseItem {
	private	CropBean cropBean ;    //包裹中存放的农作物
	private int count;	//农作物数量
	  
	
	public StoreHouseItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StoreHouseItemBean(CropBean cropBean, int count) {
		this.cropBean = cropBean;
		this.count = count;
	}
	public String toString() {
		return this.cropBean.getCropId()+":"+this.count;
	}
	
	

	/**
	 * 该方法返回的农作物的数量
	 */
	public int getItemCount() {
		// TODO Auto-generated method stub
		return this.count;
	}
	/**
	 * 该方法返回的是农作物的名称
	 */
	public String getItemName() {
		// TODO Auto-generated method stub
		return this.cropBean.getCropName();
	}
		/**
		 * 返方法回该仓库中的农作物图片，该方便只需要返回农作物的种子图片即可
		 */
	public String getItemPic() {
		// TODO Auto-generated method stub
		return this.cropBean.getSeedPic();
	}
		/**
		 * 该方法返回的是农作物的销售价格
		 */
	public String getItemPrice() {
		// TODO Auto-generated method stub
		return String.valueOf(this.cropBean.getSellPrice());
	}
	
	
	
	/**
	 * 该方法为用户确认卖出仓库中收获的农作物，该方法本次任务暂且无需完成。
	 * money:钱
	 * count：数量
	 */
	public void doSellItem(int money, int count) {
	
		int corpCountDo=0;
		for (Iterator<StoreHouseItem> iter = GameMember.allUserStore.iterator(); iter.hasNext(); ) {
			StoreHouseItem tar = iter.next();
	    if (tar.getItemName().equals(this.cropBean.getCropName())){
	    	corpCountDo=tar.getItemCount();
	    	if(corpCountDo>=count){
		        iter.remove();
		        corpCountDo-=count;
	    	}else{
	    		JOptionPane.showMessageDialog(null, "您的数量没有这么多", "出售失败", JOptionPane.ERROR_MESSAGE);  		
	    	return;
	    	}
	   
	        }
		}	
		if(corpCountDo>0){
			StoreHouseItemBean StoreHouseItemBean	=new StoreHouseItemBean(this.cropBean,corpCountDo);
			GameMember.allUserStore.add(0,StoreHouseItemBean);
		}
		String storeFilePath="user/userdetail/"+GameMember.loginUser.getUserId()+"_store.txt";
		String allLine="";
		for (StoreHouseItem allUserStore : GameMember.allUserStore) {
			allLine+=allUserStore.toString()+"\r\n";
		}
		FileUtils fileUtils=new FileUtils();
		
		fileUtils.writeFile(storeFilePath, allLine);
		GameMember.userData.setMoney(GameMember.userData.getMoney()+money*count);
		UserDateDao UserDateDao=new UserDateDao();
		UserDateDao.updateUserData(GameMember.userData);
		GameMember.loadUserData();
	}
	
	/**
	 * 该方法为用户点击仓库中的农作物需要实现的方法
	 */
	public boolean itemClick() {
		// TODO Auto-generated method stub
		
		
		
		return true;
	}
	/**
	 * 该方法为用户点击仓库中的农作物，弹出销售窗口之后输入数量且点击卖出的执行方法
	 */
	public int sellItem(int count) {
		return count*this.cropBean.getSellPrice();
	}
	/**
	 * 修改仓库中该农作物的数量，该方法需要完成的功能为在原有数量的基础上加上传入的数量
	 * @param count
	 */
	public void addCount(int count){
		this.count+=count;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public CropBean getCropBean() {
		return cropBean;
	}
	public void setCropBean(CropBean cropBean) {
		this.cropBean = cropBean;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	
}
