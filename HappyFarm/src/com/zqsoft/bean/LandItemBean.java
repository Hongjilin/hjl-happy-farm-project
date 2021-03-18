package com.zqsoft.bean;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import com.test.BlackListBean;
import com.test.userpick;
import com.zqsoft.dao.LandDAO;
import com.zqsoft.dao.StoreHouseDAO;
import com.zqsoft.dao.UserDateDao;
import com.zqsoft.frame.GameMember;
import com.zqsoft.guiHelper.FaceHelper;
import com.zqsoft.guiHelper.bean.LandItem;
import com.zqsoft.guiHelper.bean.PackageItem;
import com.zqsoft.guiHelper.bean.StoreHouseItem;
import com.zqsoft.ncfarm.core.MessageDialogHelper;
import com.zqsoft.thread.CropGrowThread;
import com.zqsoft.utils.FileUtils;

public class LandItemBean implements LandItem{
	private CropBean cropBean=null; //土地上生长的农作物
	private int landId;			//土地编号
	private int count;			//土地上的农作物的数量
	private Date beginTime;		//开始时间
	private CropGrowThread cropGrowThread;
	boolean zhaiqufalg=true;
	public LandItemBean(CropBean cropBean) {	
		this.cropBean = cropBean;
	}

	
	public CropGrowThread getCropGrowThread() {
		return cropGrowThread;
	}


	
	
	public void setCropGrowThread(CropGrowThread cropGrowThread) {
		this.cropGrowThread = cropGrowThread;
	}
	
	public LandItemBean(int landId) {
		this.landId=landId;
	}

	
	public LandItemBean(CropBean cropBean, int landId, int count, Date beginTime) {
		if (cropBean != null) {
			try {
				this.cropBean = (CropBean) cropBean.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		this.landId = landId;
		this.count = count;
		this.beginTime = beginTime;
		if (cropBean != null) {
			cropGrowThread = new CropGrowThread(this);
			cropGrowThread.start();
		}
	}
	public LandItemBean() {
		
		super();
	}

	public String toString() {
		if(this.cropBean!=null){
			return  this.landId+ "," + this.cropBean.getCropId()
					+ "," + this.count + ","+this.beginTime.getTime()+"\r\n";
		}
		return  this.landId+",,,"+"\r\n";
	}
	/**
	 * 返方法回该土地上的农作物图片
	 */
	public String getItemPic() {
		if(this.cropBean==null) return null;
		
		String path="resources/crops/cron"+this.cropBean.getCropId()+"/";
		if(this.count==0)	{
			return path+"cron_end.png";
		}
		if(this.cropBean.getCurrent()-1==-1){
			return path+"cron_start.png";
		}else
		return path+this.cropBean.getAllStagePic().get(this.cropBean.getCurrent()-1);
	}
	/**
	 * 该方法为土地上农作物的摘取方法
	 */
	public int pickCount=3;
	public void pickAction() {
		FileUtils fileUtils=new FileUtils();
		String blackstring="";
		String blackPath="user/blacklist.txt";
		//删除过期黑名单数据
		for (Iterator<BlackListBean> iter = GameMember.allBackList.iterator(); iter.hasNext(); ) {
			BlackListBean tar = iter.next();
	    if (tar.getPickTime()<System.currentTimeMillis()-3600000){
	        iter.remove();
	        }
		}
		boolean	blackflag=true;
		boolean userfalg=true;
		int newcount=1;
		Random random1 = new Random();
		int random = random1.nextInt(10)%10 + 1;
		if(this.cropBean==null){
//			MessageDialogHelper.showConfirmDialog("这片土地光秃秃的,快去种点什么吧", "提示信息");
			JOptionPane.showMessageDialog(null, "这片土地光秃秃的,快去种点什么吧", "提示信息", JOptionPane.ERROR_MESSAGE);				
			return;
		}
		//判断农作物是否成熟
		if(this.cropBean.getCurrent()<this.cropBean.getStage()-1){
//		MessageDialogHelper.showConfirmDialog("农作物还没有成熟", "提示信息");
		JOptionPane.showMessageDialog(null, "农作物还没有成熟", "提示信息", JOptionPane.ERROR_MESSAGE);		
		
		return;
		}

		StoreHouseItemBean store=new StoreHouseItemBean();
		StoreHouseDAO storeDao=new StoreHouseDAO();	
		//将农作物添加到仓库中
		String show=null;
		if(GameMember.loginUser!=GameMember.currentUser){

			//偷摘后解除黑名单
			//保存当前用户
			String blickString= GameMember.loginUser.getUserId()+":"+GameMember.currentUser.getUserId()+":"+String.valueOf( System.currentTimeMillis())+"\r\n";
			//删除前数据
			for (Iterator<BlackListBean> iterx = GameMember.allBackList.iterator(); iterx.hasNext(); ) {
				
				BlackListBean tar = iterx.next();
			
			    if (tar.getUserId()==GameMember.loginUser.getUserId()&&tar.getPassUserId()==GameMember.currentUser.getUserId()){
			    	iterx.remove();
			        blackflag=true;
			       
			    	JOptionPane.showMessageDialog(null,"摘菜的同时将"+GameMember.currentUser.getNickName()+"移除了您的黑名单");   
			    }else if (tar.getUserId()==GameMember.currentUser.getUserId()&&tar.getPassUserId()==GameMember.loginUser.getUserId()){
		    	blackflag=false;
			    }
//
		    
			}
			if(blackflag==false){
				pickCount=-1;
				JOptionPane.showMessageDialog(null,"您因为偷菜太过频繁被加入了名单,无法偷菜,等明天再来吧"); 
//				return;
			}
				
			
			//这是报存偷摘人信息
			String PICKFilePath="user/userpick/"+GameMember.currentUser.getUserId()+"_pick.txt";
			String cms= GameMember.loginUser.getUserId()+":"+String.valueOf( System.currentTimeMillis())+"\r\n";
			//删除前数据
			for (Iterator<userpick> iter1 = GameMember.allUserPick.iterator(); iter1.hasNext(); ) {
				userpick tar1 = iter1.next();
		    if (tar1.getPickTime()<System.currentTimeMillis()-360000){
		        iter1.remove();
		        }
			}
			String pickString="";
			for (userpick UserPick : GameMember.allUserPick) {
				pickString+=UserPick.toString();
			}
//			System.out.println(pickString);
			pickString+=cms;
			System.out.println(pickString);
			fileUtils.writeFile(PICKFilePath, pickString);
			
			
		if(pickCount>0){
//			if(count==0) show="你已经偷光le"+GameMember.currentUser.getNickName()+"土地上的"+this.getCropBean().getCropName()+",数量为1";
			if(random>5){
				this.count-=1;
				store.addCount(1);
		show="你偷了"+GameMember.currentUser.getNickName()+"土地上的"+this.getCropBean().getCropName()+",数量为1";
			}else{
		
				store.addCount(1);
		show="你偷了"+GameMember.currentUser.getNickName()+"土地上的"+this.getCropBean().getCropName()+",数量为1\n"
				+ "但同时你被抓了,减少经验10点";	
		if(GameMember.userData.getExp()-10>200)
		GameMember.userData.setExp(GameMember.userData.getExp()-10);
		else
			show="你偷了"+GameMember.currentUser.getNickName()+"土地上的"+this.getCropBean().getCropName()+",数量为1\n"
					+ "于此同时你被抓了,减少经验10点\n但看你经验太低这次就放过你了";	
		
			}
			
		}else if(pickCount==0){
			show=GameMember.currentUser.getNickName()+"说:"+GameMember.loginUser.getNickName()+"你太过分了,我"+
		"迟早偷回来;并且踢走了你,并没有偷到菜";	
			userfalg=false;
		}
		else{
			show=GameMember.currentUser.getNickName()+"又说:"+GameMember.loginUser.getNickName()+"你欺人太甚,我"
					+ "然后胖揍了你一顿,然后你又没有偷到菜,\n于此同时你被"+GameMember.currentUser.getNickName()+"加入黑名单了";
			userfalg=false;
			//五次后加入黑名单
			//保存当前用户
		
//			String blickString= GameMember.loginUser.getUserId()+":"+GameMember.currentUser.getUserId()+":"+String.valueOf( System.currentTimeMillis())+"\r\n";
			//删除前数据
			for (Iterator<BlackListBean> iter = GameMember.allBackList.iterator(); iter.hasNext(); ) {
				BlackListBean tar = iter.next();
		    if (tar.getPickTime()<System.currentTimeMillis()-3600000){
		        iter.remove();
		        }
			}
	
			for (BlackListBean UserPick : GameMember.allBackList) {
				blackstring+=UserPick.toString();
			}
			blackstring+=blickString;
			
		}
		fileUtils.writeFile(blackPath, blackstring);
		pickCount--;
		}else{
			
			store.addCount(this.count);
			 newcount=this.count;
			this.count=0;
			if(zhaiqufalg){
		show="已经将该土地上的"+this.getCropBean().getCropName()+"摘取,数量为:"+newcount;
//		MessageDialogHelper.showConfirmDialog(show, "提示信息");
		JOptionPane.showMessageDialog(null,show);  
//		JOptionPane.showMessageDialog(null, show, "提示信息", JOptionPane.ERROR_MESSAGE);		
			}
		}
		
		if(this.count==0){
			//数量为0清空土地信息
			zhaiqufalg=false;
//			MessageDialogHelper.showConfirmDialog("该土地上农作物已经摘完了", "提示信息");		
//			JOptionPane.showMessageDialog(null, "该土地上农作物已经摘完了", "提示信息", JOptionPane.ERROR_MESSAGE);
			JOptionPane.showMessageDialog(null,"该土地上农作物已经摘完了"); 
			for (Iterator<LandItemBean> iter = GameMember.allUserLand.iterator(); iter.hasNext(); ) {
				LandItemBean tar = iter.next();
		    if (tar.getLandId()==this.landId){
		        iter.remove();
		        }
			}
			LandItemBean lan=new LandItemBean();
			lan.setLandId(this.landId);
			GameMember.allUserLand.add(lan);
		}else{
			if(	userfalg==false&&GameMember.loginUser!=GameMember.currentUser){
//				MessageDialogHelper.showConfirmDialog(show, "提示信息");
				JOptionPane.showMessageDialog(null, show, "提示信息", JOptionPane.ERROR_MESSAGE);			
			}else{
				//数量不为0是土地农作物数量减一
//				MessageDialogHelper.showConfirmDialog(show, "提示信息");
				JOptionPane.showMessageDialog(null,show);  
				LandItemBean LandItemBean=new LandItemBean();
				for (Iterator<LandItemBean> iter = GameMember.allUserLand.iterator(); iter.hasNext(); ) {
					LandItemBean tar = iter.next();
			    if (tar.getLandId()==this.landId){
			    	LandItemBean=tar;
			        iter.remove();
			        }
				}
				LandItemBean.setCount(LandItemBean.getCount()-1);
//				LandItemBean.setLandId(this.landId);
				GameMember.allUserLand.add(LandItemBean);
			}
		
		}
	
		//保存当前用户
		String packFilePath="user/userland/"+GameMember.currentUser.getUserId()+"_land.txt";
		String UserLandString="";
		for (LandItemBean UserLand : GameMember.allUserLand) {
			UserLandString+=UserLand.toString();
		}
//		FileUtils fileUtils=new FileUtils();
		fileUtils.writeFile(packFilePath, UserLandString);
		//添加仓库
		StoreHouseItemBean StoreHouseItemBean=new StoreHouseItemBean();
		StoreHouseItemBean.setCropBean(cropBean);
	
		int oldCount=0;
		for (Iterator<StoreHouseItem> iter = GameMember.allUserStore.iterator(); iter.hasNext(); ) {
			StoreHouseItem tar = iter.next();
	    if (tar.getItemName().equals(this.getCropBean().getCropName())){
	    	oldCount=tar.getItemCount();
	        iter.remove();
	        }
		}
		StoreHouseItemBean.setCount(oldCount+newcount);
//		System.out.println(StoreHouseItemBean);
		GameMember.allUserStore.add(StoreHouseItemBean);
		String UserStoreFilePath="user/userdetail/"+GameMember.loginUser.getUserId()+"_store.txt";
		String UserStoreString="";
		for (StoreHouseItem UserStore : GameMember.allUserStore){
			UserStoreString+=UserStore.toString()+"\r\n";
		}
		fileUtils.writeFile(UserStoreFilePath, UserStoreString);
		if(	userfalg==true&&GameMember.loginUser!=GameMember.currentUser||GameMember.loginUser==GameMember.currentUser)
		
		
		GameMember.userData.setExp(GameMember.userData.getExp()+1);
		if(this.cropBean.getCropId()==16)
		GameMember.userData.setExp(GameMember.userData.getExp()+99);
		UserDateDao UserDateDao=new UserDateDao();
		UserDateDao.updateUserData(GameMember.userData);
		FaceHelper.setExp(String.valueOf(GameMember.userData.getExp()));
		FaceHelper.setLevel(String.valueOf(GameMember.userData.getUserLevel()));
			return;
		}
		
	
	
	
	/**
	 * 该方法为土地上农作物的种植方法
	 */
	public void plantAction() {
		int countfalg=0;
		if(this.cropBean!=null){
			JOptionPane.showMessageDialog(null, "菜已经种了，等收了再种吧", "种植失败", JOptionPane.ERROR_MESSAGE);		
				return ;
				}
		if(GameMember.loginUser!=GameMember.currentUser) {
			JOptionPane.showMessageDialog(null, "不能在好友菜地种植", "种植失败", JOptionPane.ERROR_MESSAGE);				
			return;
		}	
		
	CropBean crop=GameMember.getCropBean(GameMember.selectdCropid);
	int corpCount=0;
	for (Iterator<PackageItem> iter = GameMember.allUserPackages.iterator(); iter.hasNext(); ) {
		PackageItem tar = iter.next();
    if (tar.getItemName().equals(GameMember.getCropBean(GameMember.selectdCropid).getCropName())){
    	corpCount=tar.getItemCount();
    	this.cropBean=crop;
        iter.remove();
        corpCount-=1;
        }
	}
	if(corpCount>0){
		PackageItemBean packageItemBean	=new PackageItemBean(crop,corpCount);
		GameMember.allUserPackages.add(0,packageItemBean);
	}else if(corpCount==0){
		JOptionPane.showMessageDialog(null,"种子用完啦，记得取去商店购买一些种子哦");  
	}


	String allUserPackagesString="";
	for (PackageItem userPackages : GameMember.allUserPackages) {
		allUserPackagesString +=userPackages+"\r\n";
	}
	FileUtils fileUtils=new FileUtils();
	String packFilePath="user/userdetail/"+GameMember.loginUser.getUserId()+"_package.txt";
	//修改包裹数据
	fileUtils.writeFile(packFilePath, allUserPackagesString);
	for (Iterator<LandItemBean> iters =  GameMember.allUserLand.iterator(); iters.hasNext(); ) {
		LandItemBean tar = iters.next();
    if (tar.getLandId()== this.landId){
        iters.remove();
        }
	}

	
	//增加与刷新用户信息
	if(countfalg>0)
	GameMember.userData.setExp(GameMember.userData.getExp()+5);
	UserDateDao userDataDao=new UserDateDao();
	userDataDao.updateUserData(GameMember.userData);
	FaceHelper.setExp(String.valueOf(GameMember.userData.getExp()));
	FaceHelper.setLevel(String.valueOf(GameMember.userData.getUserLevel()));
	
	LandItemBean lan=new LandItemBean();
	this.setBeginTime(new Date());
	this.count=new Random().nextInt(50);
	
	lan.setBeginTime(this.getBeginTime());
	lan.setCount(this.getCount());
	lan.setLandId(this.landId);
	lan.setCropBean(crop);
	GameMember.allUserLand.add(lan);
	LandDAO lanDao=new LandDAO();
	lanDao.updateUserLand(GameMember.allUserLand,GameMember.loginUser.getUserId());
	this.cropGrowThread=new CropGrowThread(lan);
	this.cropGrowThread.start();
	
	
	}
	
	
	/**
	 * 法该方法负责判断农作物是否需要进行生长
	 */
	public void growing( ){
		if (this.cropBean == null) {
			return;
		}
		if (this.cropBean.getStage() == this.cropBean.getCurrent()&&cropGrowThread!=null) {
			cropGrowThread.stopgrown();
			return;
		}
		if(this.count==0)	return;

		long pms=0;//阶段要时间毫秒数
		long bms=0;//播种时间毫秒数
		long cms=0;//当前时间
		int curr=0;
		for (int i = 0; i < this.cropBean.getAllStageTime().size(); i++) {
			bms=(this.beginTime.getTime());	//播种时间毫秒数
			pms=this.cropBean.getStageMillsTime(i);					//阶段要时间毫秒数
			cms=  System.currentTimeMillis();
			if(cms<bms+pms) {
				return;
				}
			else {
				this.cropBean.goNextStage();
			}
			}
		
	}
	
	
	
	
	
	
	/**
	 * 该方法为土地的铲除方法，该方法之后再实现，本次任务不完成
	 */
	public void uprootAction() {
		// TODO Auto-generated method stub
		if(GameMember.loginUser!=GameMember.currentUser){
			MessageDialogHelper.showMessageDialog("不能铲除好友农作物", "提示");
			return; 
		}
		if(this.cropBean!=null){
		boolean flag=	MessageDialogHelper.showConfirmDialog("是否铲除农作物", "提示");		
		if(!flag)	return;
		
			LandDAO LandDAO=new LandDAO();
			//铲除的时候清空土地信息
			String show="已经将该土地上的"+this.getCropBean().getCropName()+"铲除";
			MessageDialogHelper.showConfirmDialog(show, "提示信息");		
			for (Iterator<LandItemBean> iter = GameMember.allUserLand.iterator(); iter.hasNext(); ) {
				LandItemBean tar = iter.next();
		    if (tar.getLandId()==this.landId){
		        iter.remove();
		        }
			}
			LandItemBean lan=new LandItemBean();
			lan.setLandId(this.landId);
			GameMember.allUserLand.add(lan);
			String packFilePath="user/userland/"+GameMember.loginUser.getUserId()+"_land.txt";
			String UserLandString="";
			for (LandItemBean UserLand : GameMember.allUserLand) {
				UserLandString+=UserLand.toString();
			}
			FileUtils fileUtils=new FileUtils();
			fileUtils.writeFile(packFilePath, UserLandString);
			
			this.cropBean=null;
			this.count=0;			//土地上的农作物的数量
			beginTime=null;		//开始时间
			LandDAO.updateUserLand(GameMember.allUserLand, GameMember.loginUser.getUserId());
			GameMember.userData.setExp(GameMember.userData.getExp()+3);
			UserDateDao UserDataDAO=new UserDateDao();
			UserDataDAO.updateUserData(GameMember.userData);
			FaceHelper.setExp(String.valueOf(GameMember.userData.getExp()));
			FaceHelper.setLevel(String.valueOf(GameMember.userData.getUserLevel()));
			
		}
		
	}

	public CropBean getCropBean() {
		return cropBean;
	}

	public void setCropBean(CropBean cropBean) {
		this.cropBean = cropBean;
	}

	public int getLandId() {
		return landId;
	}

	public void setLandId(int landId) {
		this.landId = landId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public void stopGrowing() {
		if (this.cropGrowThread != null) {
			cropGrowThread.stopgrown();
		}
	}
	/**
	 * 判断增加
	 * @param addCount
	 */
	public void addStore(int addCount) {
		boolean isExit = false;
		for (int i = 0; i < GameMember.allUserStore.size(); i++) {
			StoreHouseItemBean item = (StoreHouseItemBean) GameMember.allUserStore
					.get(i);
			if (item.getItemName().equals(this.cropBean.getCropName())) {
				item.addCount(addCount);
				isExit = true;
			}
		}
		if (!isExit) {
			StoreHouseItemBean item = new StoreHouseItemBean(this.cropBean, addCount);
			GameMember.allUserStore.add(item);
		}
	}
	

}
