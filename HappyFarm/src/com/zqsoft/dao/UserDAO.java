package com.zqsoft.dao;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.zqsoft.bean.UserBean;
import com.zqsoft.utils.FileUtils;
public class UserDAO {
	 FileUtils fileUtils=new FileUtils();
	/**
	 * 读取用户的基本信息文本文件,获取所有的用户信息
	 * @return List<UserBean>游戏中所有的用户信息
	 */
	public List<UserBean> getAllUsers(){
	List<UserBean> allUsers =new ArrayList<UserBean>();//用于存放所有用户信息
	
	 List<String> userStrAll=fileUtils.readFile("user/userbase.txt");
	
	 String[] userArray = null;
	 for (String userStr : userStrAll) {
		 userArray=userStr.split(";");
		  UserBean userBean=new UserBean();//定义对象	
		   userBean.setUserId(Integer.parseInt(userArray[0]));
				userBean.setUseName(userArray[1]);
				userBean.setPassword(userArray[2]);
				userBean.setNickName(userArray[3]);
				userBean.setUserModText(userArray[4]);
				userBean.setPic(userArray[5]);
				userBean.setNotice(userArray[6]);
				allUsers.add(userBean);
	}
		return allUsers;
	}
	
	/**
	 * 保存所有的用户信息到文本文件中
	 * @param List<UserBean> allUsers 所有的用户信息
	 */
	public void saveUser(List<UserBean> allUsers){
		String allstr="";
		for (UserBean userBean : allUsers) {
			allstr+=userBean.getUserId()+";";
			allstr+=userBean.getUseName()+";";
			allstr+=userBean.getPassword()+";";
			allstr+=userBean.getNickName()+";";
			allstr+=userBean.getUserModText()+";";
			allstr+=userBean.getPic()+";";
			allstr+=userBean.getNotice()+";\r\n";
		}
		System.out.println(allstr);
		fileUtils.writeFile("user/userbase.txt",allstr);
		
	}
	

}
