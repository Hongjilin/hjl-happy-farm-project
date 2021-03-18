package com.zqsoft.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zqsoft.bean.LandItemBean;
import com.zqsoft.frame.GameMember;
import com.zqsoft.utils.FileUtils;

public class LandDAO {
	FileUtils fileUtils=new FileUtils();
	GameMember game=new GameMember();
	/**
	 * 解析用户土地数据文件
	 * @param userId 用户编号
	 * @return 用户土地明细列表
	 */
	public List<LandItemBean> getUserLandBean(int userId){
		List<LandItemBean> allLand = new ArrayList<LandItemBean>();
		String landFilePath="user/userland/"+userId+"_land.txt";
		List<String> landString= fileUtils.readFile(landFilePath);
		String[] tmp = null;
		if(landString.size()==0){
			for (int i = 0; i < 6; i++) {
				LandItemBean landItem = new LandItemBean(null);
				landItem.setLandId(i+1);;
				allLand.add(landItem);
				}
			return allLand;
			}
		
	for (String str : landString) {
		tmp=str.split(",");
	
		LandItemBean landItemBean =null;
		if(tmp.length>1){
			Date beginTime=new Date(Long.parseLong(tmp[3]))	;
			 landItemBean=new LandItemBean(GameMember.getCropBean(Integer.parseInt(tmp[1])),Integer.parseInt(tmp[0])
					, Integer.parseInt(tmp[2]),beginTime);
			}
		else{
		 landItemBean=new LandItemBean(null,Integer.parseInt(tmp[0])
					,0,null);
		}
		allLand.add(landItemBean);
		}	
		return allLand;
	}
	
	
	
	
	
public static void updateUserLand(List<LandItemBean> allLand, int userId){
	
		saveUserLand(allLand,  userId);
		
	}
	/**
	 * 保存用户土地信息到文件中
	 * @param allLand 用户所有的土地信息
	 * @param userId 用户编号
	 */
	public static void saveUserLand(List<LandItemBean> allLand, int userId){
		String landFilePath="user/userland/"+userId+"_land.txt";
		String allLine="";
		for (LandItemBean landItemBean : allLand) {
			allLine+=landItemBean.toString();
		}
		FileUtils fileUtils=new FileUtils();
		fileUtils.writeFile(landFilePath, allLine);
	}

}
