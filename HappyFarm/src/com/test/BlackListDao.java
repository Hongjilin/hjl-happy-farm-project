package com.test;

import java.util.ArrayList;
import java.util.List;

import com.zqsoft.utils.FileUtils;

public class BlackListDao {

	public List<BlackListBean> getAllBlackList(){
		List<BlackListBean> AllBlackList = new ArrayList<BlackListBean>();
		String pickFilePath="user/blacklist.txt";
		FileUtils fileUtils=new FileUtils();
		List<String> landString= fileUtils.readFile(pickFilePath);
		String[] tmp = null;
		if(landString.size()!=0)
		for (String str : landString) {
			tmp=str.split(":");
			BlackListBean BlackList =new BlackListBean();
			BlackList.setPassUserId( Integer.parseInt(tmp[0]));
			BlackList.setUserId( Integer.parseInt(tmp[1]));
			BlackList.setPickTime(Long.parseLong(tmp[2]));;
			AllBlackList.add(BlackList);
			}
		return AllBlackList;
	}
	
	
	
	
}
