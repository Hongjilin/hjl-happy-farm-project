package com.zqsoft.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.zqsoft.bean.CropBean;
import com.zqsoft.bean.PackageItemBean;
import com.zqsoft.guiHelper.bean.PackageItem;
import com.zqsoft.utils.FileUtils;

public class PackageDAO {
	FileUtils fileUtils =new FileUtils();
	CropDAO cropDao=new CropDAO();
	/**
	 * 解析用户包裹数据文件
	 * @param userId 用户编号
	 */
	public List<PackageItem>  getUserPackage(int userId){
		List<PackageItem> userPackages =new ArrayList<PackageItem>();
		
		String packFilePath="user/userdetail/"+userId+"_package.txt";
		FileInputStream packInFile= null;
		List<String> packString= fileUtils.readFile(packFilePath);
		String[] tmp;
		List<CropBean> AllCrop=cropDao.getAllCrop();
		for (String string : packString) {
			CropBean cropBeanTmp=null;
			tmp=string.split(":");
			for (CropBean cropBean : AllCrop) {
				if(cropBean.getCropId()==Integer.parseInt(tmp[0]))
					cropBeanTmp=cropBean;
			}
PackageItemBean packageItemBean=new PackageItemBean(cropBeanTmp, Integer.parseInt(tmp[1]));
		userPackages.add(packageItemBean);
		}

		return userPackages;
	}
	
	
	public static void saveUserPackage(List<PackageItemBean> userPackages, int userId) {
		String fileName = "user/userdetail/" + userId + "_package.txt";
		String content = "";
		for (int i = 0; i < userPackages.size(); i++) {
			PackageItem item = userPackages.get(i);
			content += item.toString();
			if (i != userPackages.size() - 1) {
				content += "\r\n";
			}
		}
		FileUtils.writeFile(fileName, content);
	}
	
	
}
