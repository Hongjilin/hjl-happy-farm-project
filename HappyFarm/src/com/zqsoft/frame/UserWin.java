package com.zqsoft.frame;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.test.BlackListDao;
import com.test.userpickDao;
import com.zqsoft.bean.LandItemBean;
import com.zqsoft.bean.PackageItemBean;
import com.zqsoft.bean.UserBean;
import com.zqsoft.bean.UserData;
import com.zqsoft.dao.LandDAO;
import com.zqsoft.dao.PackageDAO;
import com.zqsoft.dao.StoreHouseDAO;
import com.zqsoft.dao.UserDAO;
import com.zqsoft.dao.UserDateDao;
import com.zqsoft.guiHelper.net.bean.UserItem;
import com.zqsoft.guiHelper.net.bean.UserWindow;
import com.zqsoft.ncfarm.core.MessageDialogHelper;
import com.zqsoft.utils.FileUtils;
import com.zqsoft.utils.NewID;

public class UserWin implements UserWindow {
	//设置默认用户名及密码
	private String adminName="admin";
	private  String adminPwd="123456";
	
	private List<UserBean> allUser;
	
	

/**
 * (3.5)构造函数中
 * 通过UserDAO的getAllUsers()方法读取并且获得全部用户信息辅导allUser
 * @param allUser
 */
public UserWin() {
		UserDAO userDao=new UserDAO();
		this.allUser = userDao.getAllUsers();
	}
/**
 * 修改用户密码
 * @param userName 用户名
 * @param oldPassword 旧密码
 * @param newPassword 新密码
 * @param checkPassword 确认密码信息
 * @return boolean
*/
	public boolean changePassword(String userName, String oldPassword, String newPassword,
			String checkPassword) {
		if(newPassword.length()<6||newPassword.length()>12){
			MessageDialogHelper.showMessageDialog("用户名密码格式错误,长度请控制在6~12之间", "提示信息");				
			return false;
		}else if(!newPassword.equals(checkPassword)){
			System.out.println("确认密码必须和密码相同");
			MessageDialogHelper.showMessageDialog("确认密码必须和密码相同", "提示信息");						
			return false;
		}else if(!oldPassword.equals(GameMember.loginUser.getPassword())){
			System.out.println("旧密码不正确");
			MessageDialogHelper.showMessageDialog("旧密码不正确", "提示信息");				
			return false;
		}
		
		GameMember.loginUser.setPassword(newPassword);
		
		UserDAO userDao=new UserDAO();
		userDao.saveUser(this.allUser);
		return true;
	}
	
	
	
	
	
/**
 * 修改个人基本信息
 * @param userName 用户名
 * @param userInfo 个人信息
 * @param nickName 昵称
 * @param headPic 头像
 * @param notice 公告信息
 * @return boolean true false
 */
	public boolean changeUserInfo(String userName, String userInfo, String nickName,
			String headPic, String notice) {
		GameMember.loginUser.setUseName(userName);
		GameMember.loginUser.setUserModText(userInfo);
		GameMember.loginUser.setPic(headPic);
		GameMember.loginUser.setUseName(userName);
		GameMember.loginUser.setNotice(notice);
		GameMember.loginUser.setNickName(nickName);
		UserDAO userDao=new UserDAO();
		userDao.saveUser(this.allUser);
		return true;
	}
	
	
/**
 * 获取所有的用户头像集合
 */
	public List getUserFaceList() {
		ArrayList<String> allHeadPathList=new ArrayList<String>();
		
		int index=	GameMember.loginUser.getPic().lastIndexOf("/");
		String path= GameMember.loginUser.getPic().substring(0,index+1);
		File file=new File(path);
		String[] fileList= file.list();
		for (String head : fileList) {
			allHeadPathList.add(path+head);
		}
		
		return allHeadPathList;
	}
	
	
/**
 * 获取用户列表
 * 将自己存放到用户列表第一位
 */
	public List getUserList() {
		List<UserItem> users = new ArrayList<UserItem>();
		//将所有用户存入集合中
		for (UserItem user : allUser) {
			users.add(user);
		}
		//删除本身用户
		users.remove(GameMember.loginUser);
		users.add(0,GameMember.loginUser);
		return users;
	}
	
	
	
	
	/**
	 * 用户登录接口
	 * @param userName 用户名
	 * @param password 密码
	 * @return boolean
	*/
	public boolean loginCheckUser(String userName, String password) {
			//当格式不正确时就没必要进行验证,节省资源
		if (userName.length()<3||userName.length()>10) {
			MessageDialogHelper.showMessageDialog("用户名格式输入错误", "提示信息");		
			return false;
		}else if(password.length()<6||password.length()>12){
			MessageDialogHelper.showMessageDialog("用户名密码格式错误", "提示信息");		
			return false;
		}
			//驗證账户和密码
		for (UserBean user : this.allUser) {
		 if(user.getUseName().equals(userName)&&user.getPassword().equals(password)){
			MessageDialogHelper.showMessageDialog("登陆成功", "提示信息");
			UserDateDao data=new UserDateDao();
			//登陆成功提取并保存此用户信息 
			GameMember.loginUser=user;
			GameMember.userData=data.getUserData(user.getUserId());
			GameMember.currentUser=GameMember.loginUser;
			userpickDao userpickDao=new userpickDao();
			GameMember.allUserPick=userpickDao.getUserpick(user.getUserId());
			BlackListDao BlackListDao=new BlackListDao();
			GameMember.allBackList=BlackListDao.getAllBlackList();
			//GameMember.allUserPick=userpickDao.getUserpick(user.getUserId());
			
			return true;
			}
		}
			MessageDialogHelper.showMessageDialog("账户或者密码不正确 登陆失败", "提示信息");
			return false;
	}
	
	/**
	 * 注册用户接口
	 * @param userName 用户名
	 * @param password 新密码
	 * @param checkPassword 确认密码信息
	 * @return boolean
	*/
	public boolean registerUser(String userName, String password, String checkPassword) {
		if (userName.length()<3||userName.length()>10) {
			MessageDialogHelper.showMessageDialog("用户名格式输入错误,长度请控制在3~10", "提示信息");				
			return false;
		}else if(password.length()<6||password.length()>12){
			MessageDialogHelper.showMessageDialog("用户名密码格式错误,长度请控制在6~12之间", "提示信息");
			return false;
		}else if(!password.equals(checkPassword)){
			MessageDialogHelper.showMessageDialog("确认密码必须和密码相同", "提示信息");
			return false;
		}
		
		for (UserBean Users : this.allUser) {
		if(Users.getUseName().equals(userName)){
			MessageDialogHelper.showMessageDialog("该用户已经存在", "提示信息");	
			return false;
		}
		}
		
		NewID newId=new NewID();
		UserBean newUser=new UserBean();
		UserDateDao dataDao=new UserDateDao();
		
		int newid=newId.getGeneratID();
		newUser.setUserId(newid);
		newUser.setUseName(userName);
		newUser.setNickName(userName);
		newUser.setPassword(password);
		newUser.setPic("resources/head/1-1.GIF");
		newUser.setUserModText("这个人很懒惰,什么都没写");
		newUser.setNotice("没有菜的菜园");
		this.allUser.add(newUser);
		
		UserDAO userDao= new UserDAO();
		userDao.saveUser(this.allUser);
		UserData resData=new UserData();
		resData.setExp(100);
		resData.setMoney(200);
		resData.setUserid(newid);
		dataDao.updateUserData(resData);
		
		
		
//		PackageDAO.saveUserPackage(); 
		
		LandDAO LandDAO=new LandDAO();
		LandDAO.updateUserLand(new ArrayList<LandItemBean>(), newid);
		FileUtils fileUtils=new FileUtils();
		fileUtils.writeFile("user/userdetail/"+newid+"_store.txt","");
		fileUtils.writeFile("user/userdetail/"+newid+"_package.txt","");
		StoreHouseDAO StoreHouseDAO=new StoreHouseDAO();
		PackageDAO packageDAO=new PackageDAO();
		packageDAO.saveUserPackage(new ArrayList<PackageItemBean>(), newid);
		
		
		//生成偷摘名单表
		String PICKFilePath="user/userpick/"+newid+"_pick.txt";
//		String cms= String.valueOf( System.currentTimeMillis());
		fileUtils.writeFile(PICKFilePath, "");
		
		
		StoreHouseDAO.updateUserStore(new ArrayList<LandItemBean>(),newid );
		MessageDialogHelper.showMessageDialog("注册成功", "提示信息");
		return true;
		
		
	
	}

	
	
}
