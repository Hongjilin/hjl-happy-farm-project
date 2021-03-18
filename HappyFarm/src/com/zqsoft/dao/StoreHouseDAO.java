package com.zqsoft.dao;

import java.util.ArrayList;
import java.util.List;

import com.zqsoft.bean.CropBean;
import com.zqsoft.bean.LandItemBean;
import com.zqsoft.bean.StoreHouseItemBean;
import com.zqsoft.guiHelper.bean.StoreHouseItem;
import com.zqsoft.utils.FileUtils;

public class StoreHouseDAO {
	FileUtils fileUtils=new FileUtils();
	CropDAO cropDao=new CropDAO();
	
		/**
		 * 解析用户仓库数据文件
		 * @param userId
		 * @return
		 */
		public List<StoreHouseItem> getUserStoreHouse(int userId ){
			List<StoreHouseItem> allStore =new ArrayList<StoreHouseItem>();
			String storeFilePath="user/userdetail/"+userId+"_store.txt";
			List<String> storeString= fileUtils.readFile(storeFilePath);
			String[] tmp = null;
			List<CropBean> AllCrop=cropDao.getAllCrop();
			for (String str : storeString) {
				tmp=str.split(":");
				CropBean cropBeanTmp=null;
			for (CropBean cropBean : AllCrop) {
				if(cropBean.getCropId()==Integer.parseInt(tmp[0]))
					cropBeanTmp=cropBean;
			}
			StoreHouseItem storeHouseItem= new StoreHouseItemBean(cropBeanTmp,Integer.parseInt(tmp[1]));	
			allStore.add(storeHouseItem);	
			}
			return allStore;
		}
	
	
		
		/**
		 * 保存用户土地信息到文件中
		 * @param allLand
		 * @param userId
		 */
	public void updateUserStore(List<LandItemBean> allLand,int userId){
			
		String storeFilePath="user/userdetail/"+userId+"_store.txt";
		String allLine="";
		for (LandItemBean landItemBean : allLand) {
			allLine+=landItemBean.toString();
		}
		FileUtils fileUtils=new FileUtils();
		fileUtils.writeFile(storeFilePath, allLine);
		}
	
}
