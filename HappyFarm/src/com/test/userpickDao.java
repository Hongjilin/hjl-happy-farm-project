package com.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zqsoft.bean.LandItemBean;
import com.zqsoft.frame.GameMember;
import com.zqsoft.utils.FileUtils;

public class userpickDao {
	public List<userpick> getUserpick(int userId){
		List<userpick> allPick = new ArrayList<userpick>();
		String pickFilePath="user/userpick/"+userId+"_pick.txt";
		FileUtils fileUtils=new FileUtils();
		List<String> landString= fileUtils.readFile(pickFilePath);
		String[] tmp = null;
		if(landString.size()!=0)
		for (String str : landString) {
			tmp=str.split(":");
			userpick userpick =new userpick();
			userpick.setUserId( Integer.parseInt(tmp[0]));
			userpick.setPickTime(Long.parseLong(tmp[1]));
			allPick.add(userpick);
			}
		return allPick;
	}
	
}
