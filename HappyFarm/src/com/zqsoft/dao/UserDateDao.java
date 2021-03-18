package com.zqsoft.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.zqsoft.bean.UserBean;
import com.zqsoft.bean.UserData;
import com.zqsoft.frame.GameMember;
import com.zqsoft.utils.FileUtils;

public class UserDateDao {
	FileUtils fileUtils=new  FileUtils();
	/**
	 * 根据用户编号,读取该用户文件中的数据封装成一个用户游戏对象
	 * 对于新注册的用户不存在这个文件,要求默认的数据:经验为0,金币200
	 * @param userId 
	 * @return 返回用户数据对象
	 */
 public UserData getUserData(int userId){

	 List<UserData> allUserdata =new ArrayList<UserData>();//用于存放所有用户信息
	 String filepath="user/userdetails/"+userId+"_data.txt";
	 UserData resultData=new UserData();
	 List<String> dataStrAll=fileUtils.readFile(filepath);
		UserData userData=new UserData();
		if(dataStrAll.size()==0){
			userData.setExp(0);
			userData.setMoney(200);
			userData.setUserid(userId);
		}else{
			   String userArray[]= dataStrAll.get(0).split(";");
			 userData.setUserid(Integer.parseInt(userArray[0]));
			 userData.setExp(Integer.parseInt(userArray[1]));
			 userData.setMoney(Integer.parseInt(userArray[2]));
		}
		allUserdata.add(userData);
		for (UserData userDatas : allUserdata) {
	   		if (userDatas.getUserid()==userId) {
	   			resultData=userDatas;
			}
	   }
	return resultData;
 }

 
 /**
  * 新增或修改用户游戏数据文件
  * @param data  用户数据实体
  */
 public void  updateUserData(UserData data){
	 String filepath="user/userdetails/"+data.getUserid()+"_data.txt";
	 fileUtils.writeFile(filepath, data.toString());
 }
 
 
 
}
