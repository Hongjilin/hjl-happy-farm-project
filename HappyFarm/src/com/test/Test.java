package com.test;

import java.util.LinkedList;
import java.util.List;

import com.zqsoft.bean.CropBean;
import com.zqsoft.bean.LandItemBean;
import com.zqsoft.bean.PackageItemBean;
import com.zqsoft.bean.ShopItemBean;
import com.zqsoft.dao.CropDAO;
import com.zqsoft.dao.LandDAO;
import com.zqsoft.dao.PackageDAO;
import com.zqsoft.dao.StoreHouseDAO;
import com.zqsoft.dao.UserDAO;
import com.zqsoft.dao.UserDateDao;
import com.zqsoft.frame.GameMember;
import com.zqsoft.frame.UserWin;
import com.zqsoft.utils.FileUtils;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	/*	//验证获取用户
		  UserDAO userDao=new UserDAO();
//		System.out.println(userDao.getAllUsers());
		userDao.saveUser(userDao.getAllUsers());*/
		//验证登录注册
//		  UserWin userwin=new UserWin();
//		userwin.loginCheckUser("aaa", "1234567");
//		userwin.changePassword("hong1","1234567","11231234","11231234");
//		userwin.changeUserInfo("honghong", "啊啊啊", "杀马特", "resources/head/20-1.GIF", "这是修改后的公告");
//		List a=	userwin.getUserFaceList();
		//用户信息保存验证
	/*	UserDateDao data=new UserDateDao();
		System.out.println(data.getUserData(1111)+"aaa");
		data.updateUserData(data.getUserData(1111));
		*/
//		FileUtils.readFile("UserGame.txt");
		 
	/*	 UserWin userwin=new UserWin();
			userwin.registerUser("hong1", "1234567","1234567");*/
		
	/*	UserWin userwin= new UserWin();
		userwin.getUserList();*/
	/*	
		CropDAO cropDao=new CropDAO();
		
		cropDao.getAllCrop();*/
		
	/*	GameMember game=new GameMember();
		game.loadShop();*/
//		System.out.println(GameMember.allcropBean);
	/*	GameMember game=new GameMember();
		game.getCropBean(9);
	*/
		
/*		PackageDAO packdao=new PackageDAO();
		packdao.getUserPackage(50949823);*/
	/*	GameMember game=new GameMember();
		game.loadUserPackage();*/
		
	/*	ShopItemBean shop=new ShopItemBean();
		shop.itemClick();*/
/*		
	  UserWin userwin=new UserWin();
	userwin.loginCheckUser("aaa", "1234567");
	GameMember game=new GameMember();
		game.buySeed(game.getCropBean(9), 1);*/
	/*	LandDAO landDao=new LandDAO();
		landDao.getUserLandBean(50949823);*/
		/*StoreHouseDAO a=new StoreHouseDAO();
		a.getUserStoreHouse(50949823);*/
	/*	PackageItemBean p=new PackageItemBean();
		p.itemClick();
		LandItemBean a=new LandItemBean();
		
		a.plantAction();*/
		
	/*	LandItemBean lan=new LandItemBean();
		lan.growing(GameMember.getCropBean(1));*/
//		LinkedList<CropBean> AllCropBean=new LinkedList<CropBean>();
//		
//		CropBean CropBean=new CropBean();
//		
//		
//		FileUtils FileUtils=new FileUtils();
////		
//		 UserWin userwin1=new UserWin();
//			userwin1.loginCheckUser("aaa", "1234567");
//		userpickDao userpick=new userpickDao();
//		userpick.getUserpick(50949823);
//		System.out.println(GameMember.allBackList);
		BlackListDao BlackListDao=new BlackListDao();
		GameMember.allBackList=BlackListDao.getAllBlackList();
		System.out.println(GameMember.allBackList);
		for (BlackListBean string : GameMember.allBackList) {
			System.out.println(string.getPassUserId());
			System.out.println(string.getPickTime());
			System.out.println(string.getUserId());
		}
	}
}
