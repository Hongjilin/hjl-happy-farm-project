package com.zqsoft.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.zqsoft.bean.CropBean;

public class CropDAO {
	
	/**
	 * 读取所有的农作物数据文件，将每个农作物数据文件解析成一个 CropBean 对象，最终使用 List 集合存放所有的农作物
	 * @return List<CropBean>
	 */
	public List<CropBean> getAllCrop(){
		List<CropBean> allCrops = new ArrayList<CropBean>();
		File file = new File("resources/crops");
		String[] cropdirs = file.list();
		//循环列表文件读取
		for (String cropFileName : cropdirs) {
			String cropPreperName="resources/crops/"+cropFileName+"/cron.properties";
			
			String PicPath="resources/crops/"+cropFileName+"/";
			FileInputStream cropInFile= null;
			try {
				cropInFile = new FileInputStream(cropPreperName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			//使用Properties
			Properties prop = new Properties();
			try {
				prop.load(cropInFile);
				cropInFile.close();
			} catch (IOException e) {
				e.printStackTrace();
		}
//			System.out.println(prop.getProperty("ITEM_SEED_PIC"));
				//将 Properties 中的内容取出设入到 CropBean对象中
			CropBean corpBean=new CropBean();
			corpBean.setCropId(Integer.parseInt(prop.getProperty("ITEM_ID")));
			corpBean.setCropName(prop.getProperty("ITEM_NAME"));
			
			corpBean.setSeedPic(PicPath+prop.getProperty("ITEM_SEED_PIC"));
			corpBean.setStage(Integer.parseInt(prop.getProperty("ITEM_STAGE")));
			corpBean.setSellPrice(Integer.parseInt(prop.getProperty("ITEM_SELL_MONEY")));
			corpBean.setPrice(Integer.parseInt(prop.getProperty("ITEM_PRICE")));
			corpBean.setBuyLevel(Integer.parseInt(prop.getProperty("ITEM_NEED_LEVEL")));
//			corpBean.setBeginPic(PicPath+prop.getProperty("ITEM_STAGE_SEED"));
			corpBean.setEndPic(PicPath+prop.getProperty("ITEM_STAGE_END"));
			List<String> allStagePic=new ArrayList<String>();
			List<Integer> allStageTime=new ArrayList<Integer>();
			for (int i = 0; i < Integer.parseInt(prop.getProperty("ITEM_STAGE")); i++) {
				allStagePic.add(prop.getProperty("ITEM_STAGE_"+(i+1)));
				allStageTime.add(Integer.parseInt(prop.getProperty("ITEM_STAGE_NEXT_TIME_"+(i+1))));
			}
			corpBean.setAllStagePic(allStagePic);
			corpBean.setAllStageTime(allStageTime);
			//将所有的种子对象加入对象
			allCrops.add(corpBean);
		}
		
	/*	for (CropBean string : allCrops) {
			System.out.println(string);
		}*/
		return allCrops;
	}
	

}
