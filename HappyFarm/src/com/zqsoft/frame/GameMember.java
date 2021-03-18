package com.zqsoft.frame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JOptionPane;

import com.test.BlackListBean;
import com.test.BlackListDao;
import com.test.userpick;
import com.zqsoft.bean.CropBean;
import com.zqsoft.bean.LandItemBean;
import com.zqsoft.bean.PackageItemBean;
import com.zqsoft.bean.ShopItemBean;
import com.zqsoft.bean.UserBean;
import com.zqsoft.bean.UserData;
import com.zqsoft.dao.CropDAO;
import com.zqsoft.dao.LandDAO;
import com.zqsoft.dao.PackageDAO;
import com.zqsoft.dao.StoreHouseDAO;
import com.zqsoft.dao.UserDAO;
import com.zqsoft.dao.UserDateDao;
import com.zqsoft.guiHelper.FaceHelper;
import com.zqsoft.guiHelper.GameHelper;
import com.zqsoft.guiHelper.bean.PackageItem;
import com.zqsoft.guiHelper.bean.ShopItem;
import com.zqsoft.guiHelper.bean.StoreHouseItem;
import com.zqsoft.thread.CropGrowThread;
import com.zqsoft.utils.FileUtils;

public class GameMember {
	public static UserBean  loginUser;
	public static UserData  userData;
	private static GameHelper gameHelper=new GameHelper();
	public static List<CropBean> allcropBean = null; //所有的农作物信息
	public static List<PackageItem> allUserPackages= null;//用于保存当前用户的包裹信息对象
	public static List<LandItemBean> allUserLand=null;	//用于存放用户的土地对象。
	public static List<StoreHouseItem> allUserStore=null; //作为存放用户仓库数据
	public static int mouseState = 0;	// 是否点击了包裹中的种子 0：未点击，1 已点击
	public static int selectdCropid = 0;// 点击了种子的 ID
	public static UserBean currentUser=null; //表示现在是在自己还是好友的xi
	 public static List<userpick> allUserPick=null; //存放摘取用户
	 public static List<BlackListBean> allBackList=null; //存放摘取用户
	
	 
	 
	 public static void main(String[] args) {
		UserWin userWin= new UserWin();
		gameHelper.loadMod(userWin);
		
		loadUserData();
		loadShop();
		loadUserPackage();
		loadUserLand();
		loadUserStoreHouse();
		loadBackGround();
		loadPick();
		loadBlcak();
	}
	 public static void loadBlcak(){
		 BlackListDao blcakDao=new BlackListDao();
			List<BlackListBean> black=blcakDao.getAllBlackList();
			UserDAO userDao=new UserDAO();
			List<UserBean> user=userDao.getAllUsers();
			boolean a=false;
			if(black.size()>0){
				UserDateDao	UserDateDao=new UserDateDao();
				String pickString="";
				for (BlackListBean UserBlcak :black) {
					for (UserBean userBean : user) {
						if(userBean.getUserId()==UserBlcak.getPassUserId()&&UserBlcak.getUserId()==loginUser.getUserId()){
							pickString+=userBean.getNickName()+"\r\n";
							a=true;
						}
					}
				}
				if(a)
				JOptionPane.showMessageDialog(null,pickString+"因为偷您的菜太过于频繁,自动移入黑名单\r\n"
						+"快去偷回来吧");  
			}
	 }
	 
	 
	public static void loadPick(){
		UserDAO userDao=new UserDAO();
		List<UserBean> user=userDao.getAllUsers();
		
		if(allUserPick.size()>0){
			UserDateDao	UserDateDao=new UserDateDao();
			String pickString="";
			for (userpick UserPick : GameMember.allUserPick) {
				for (UserBean userBean : user) {
					if(userBean.getUserId()==UserPick.getUserId()){
						pickString+="刚刚"+userBean.getNickName()+"偷摘了你的菜\r\n";
					}
				}
			}
//			pickString
			JOptionPane.showMessageDialog(null,pickString);  
		}
	}
	/**
	* 用户加载游戏背景
	*/
	public static void loadBackGround() {
	List<String> list = new ArrayList<String>();
	list.add("resources/background/5.png");
	list.add("resources/background/1.png");
	list.add("resources/background/2.png");
	list.add("resources/background/3.png");
	list.add("resources/background/4.png");

	gameHelper.setBackground(list);
	}

	/**
	 * 加载用户数据
	 */
	public static void loadUserData() {
		FaceHelper.setExp (String.valueOf(userData.getExp()));
		FaceHelper.setLevel(String.valueOf(userData.getUserLevel()));
		FaceHelper.setMoney(String.valueOf(userData.getMoney()));
		FaceHelper.setBoardText(currentUser.getNotice());
		}
	
	public static boolean subPackage(int id){
		for (int i = 0; i < allUserPackages.size(); i++) {
			PackageItemBean item=(PackageItemBean) allUserPackages.get(i);
			if(item.getCropBean().getCropId() == id){
					item.setItemCount(-1);
			if(item.getItemCount()==0){
				allUserPackages.remove(item);
			}
			return true;
			}
		}
		return false;
		
	}
	
	
	/**
	 * 用于加载商店明细信息。
	 */
	public static void loadShop(){
		List<ShopItem> shopItemList=new ArrayList<ShopItem>();
		CropDAO cropDAO=new CropDAO();
		allcropBean=cropDAO.getAllCrop();
		Collections.sort(allcropBean);
		
		for (CropBean shopItemBean : allcropBean) {
			ShopItem shopItem = new ShopItemBean(shopItemBean);
			shopItemList.add(shopItem);
		}		
		gameHelper.setShopItemList(shopItemList);
	}
	/**
	 * 该方法作用为通过农作物编号获取农作物对象，
	 * 该方法将在包裹解析，土地解析，以及仓库解析中均需要用到
	 * @param cropId 农作物编号
	 * @return 农作物对象
	 */
	public static CropBean getCropBean(int cropId){
		for (CropBean cropBean : allcropBean) {
	/*		System.out.println(cropBean.getCropId());
			System.out.println(cropId);*/
			if(cropBean.getCropId()==cropId){
				return cropBean;
			}
		}
		return null;
	}
	
	
	public static void loadUserPackage(){
		PackageDAO packDao=new PackageDAO();
		PackageItemBean packageItemBean=new PackageItemBean();
		allUserPackages= packDao.getUserPackage( loginUser.getUserId());
		gameHelper.setPackageItemList(allUserPackages);
		}

	/**
	 * 增加用户包裹中的种子数量
	 * @param crop 种子对象
	 * @param count 种子数量
	 */
	public static void  buySeed(CropBean crop,int count){
		int corpCount=count;
		//出现的错误
	/*	for (PackageItem userPackages : allUserPackages) {
			if(crop.getCropName().equals(userPackages.getItemName())){
				corpCount=userPackages.getItemCount()+count;
				System.out.println(userPackages);
//				allUserPackages.remove(userPackages);
			}
		}*/
		for (Iterator<PackageItem> iter = allUserPackages.iterator(); iter.hasNext(); ) {
				PackageItem tar = iter.next();
		    if (tar.getItemName().equals(crop.getCropName())){
		    	corpCount=tar.getItemCount()+count;
		        iter.remove();
		        }
			}
		PackageItemBean packageItemBean	=new PackageItemBean(crop,corpCount);
		allUserPackages.add(0,packageItemBean);
		//执行写入
		
		String allUserPackagesString="";
		for (PackageItem userPackages : allUserPackages) {
			allUserPackagesString +=userPackages+"\r\n";
		}
		FileUtils fileUtils=new FileUtils();
		String packFilePath="user/userdetail/"+loginUser.getUserId()+"_package.txt";
		//修改包裹数据
		fileUtils.writeFile(packFilePath, allUserPackagesString);
//		System.out.println(allUserPackagesString);
//		userData.setMoney(money);
		
	
	
	}

	public static void reflashUserMoney(){
		FaceHelper.setMoney(String.valueOf(userData.getMoney()));
	}

	/**
	 * 初始化显示用户土地数据
	 */
	public static void loadUserLand(){
		
		LandDAO lanDao=new LandDAO();
		allUserLand=lanDao.getUserLandBean(currentUser.getUserId());
		
		for (LandItemBean UserLand : allUserLand) {
			gameHelper.addLandItem(UserLand.getLandId(), UserLand);
			CropGrowThread cropGrowThread=new CropGrowThread(UserLand);
			cropGrowThread.start();
			
		}
	}
	/**
	 * 用于加载用户的仓库数据 初始化显示用户仓库数据
	 */
	public static void loadUserStoreHouse(){
		StoreHouseDAO storeHouseDAO=new StoreHouseDAO();
		allUserStore=new ArrayList<StoreHouseItem>();
		allUserStore.addAll(storeHouseDAO.getUserStoreHouse(loginUser.getUserId()));
		gameHelper.setStoreItemList(allUserStore);
	}


}
