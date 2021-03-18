package com.zqsoft.bean;

import javax.swing.JOptionPane;

import com.zqsoft.frame.GameMember;
import com.zqsoft.guiHelper.bean.ShopItem;
import com.zqsoft.utils.FileUtils;

/**
 * 该类为商店明细类，该类的作用为游戏中的商店中显示的每个农作物以及相关的购买操作。根据 UI 助手
 * 	的要求必须实现 ShopItem 接口。
 * @author 洪
 *
 */
public class ShopItemBean implements ShopItem , Comparable<ShopItemBean>{
	private CropBean corpBean;
		
	GameMember game=new GameMember();
	
	
	
	
	
	public ShopItemBean(CropBean corpBean){
		this.corpBean=corpBean;
	}
	
	
	public ShopItemBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	/**
	 * 计算用户输入的购买数量需要的金币数
	 */
	public int buyItem(int count) {
		return this.corpBean.getPrice()*count;
	}
	
		/**
		 * 用户在输入购买数量之后得到需要的金币数后点击了确认购买执行方法
		 * money：金币数，count：购买的种子数
		 * 1. 减少用户的金币数
		 * 2. 增加用户包裹中的种子数量
		 * 3. 刷新游戏主界面中的用户游戏信息
		 * 4. 保存用户游戏数据到文件
		 * 5. 保存用户包裹数据到文件
		 * 6. 再次刷新主页面数据
		 */
	public void doBuyItem(int money, int count) {
			int MyMoney=game.userData.getMoney();
			int CropPice= money*count;
		if(MyMoney<CropPice){
JOptionPane.showMessageDialog(null, "您的金币不够哦", "购买失败", JOptionPane.ERROR_MESSAGE);
		return;
		}else{
			game.userData.setMoney(MyMoney-CropPice);
		}
		FileUtils fileUtils=new FileUtils();
		String packFilePath="user/userdetails/"+game.loginUser.getUserId()+"_data.txt";
		//修改包裹数据
		fileUtils.writeFile(packFilePath,game.userData.toString());
		game.buySeed(corpBean, count);
		game.reflashUserMoney();
		
	}
	/**
 	* 返回农作物的购买等级
 	*/
	public int getItemBuyLevel() {
		// TODO Auto-generated method stub
		return this.corpBean.getBuyLevel();
	}
	/**
 * 返回农作物的名称
 	*/
	public String getItemName() {
		// TODO Auto-generated method stub
		return this.corpBean.getCropName();
	}
	
	/**
 	* 返回农作物的种子图片
 	*/
	public String getItemPic() {
		// TODO Auto-generated method stub
//		System.out.println(this.corpBean.getSeedPic());
		return this.corpBean.getSeedPic();
	}
	/**
	 * 返回农作物种子的购买金币数
	 */
	public String getItemPrice() {
		// TODO Auto-generated method stub
		return String.valueOf(this.corpBean.getPrice());
	}

	
	/**
	 * 判断用户是否够等级购买种子
	 * true：登陆的用户等级达到种子的购买等级要求；false：登陆
	 * 的用户等级达不到种子的购买等级要求。
	 */
	public boolean itemClick() {
		if(corpBean.getBuyLevel()<=game.userData.getUserLevel()){
			return true;
		}
		JOptionPane.showMessageDialog(null, "您的等级不够哦", "购买失败", JOptionPane.ERROR_MESSAGE);
		return false;
	}


	public CropBean getCorpBean() {
		return corpBean;
	}


	public void setCorpBean(CropBean corpBean) {
		this.corpBean = corpBean;
	}

/**
 * 排序
 */
	public int compareTo(ShopItemBean o) {
		// TODO Auto-generated method stub
		
		if (this.corpBean.getBuyLevel()>o.getCorpBean().getBuyLevel()) {
			return 1;
		}else if(this.corpBean.getBuyLevel()<o.getCorpBean().getBuyLevel()){
			return -1;
		}
		return 0;
	}
	
	

}
