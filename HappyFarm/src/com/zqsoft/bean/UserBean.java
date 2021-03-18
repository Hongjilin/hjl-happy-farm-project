package com.zqsoft.bean;

import java.io.Serializable;

import com.zqsoft.frame.GameMember;
import com.zqsoft.guiHelper.FaceHelper;
import com.zqsoft.guiHelper.net.bean.UserItem;

public class UserBean implements UserItem , Serializable{
	private  int userId;//用户编号
	private String useName;//用户登陆名
	private String password;//登陆密码
	private String nickName;//昵称
	private String userModText;//个性签名
	private String pic;//用户头像
	private String notice=" ";//用户公告信息
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "userId:"+this.userId+"\t useName:"+this.useName+
				"\t密码"+this.password+"\t nickName:"+this.nickName+"\t userModText:"+this.userModText
				+"\t pic:"+this.pic+"\t notice:"+this.notice;
	}
	

	public UserBean(int userId, String useName, String password,
			String nickName, String userModText, String pic, String notice) {
		super();
		this.userId = userId;
		this.useName = useName;
		this.password = password;
		this.nickName = nickName;
		this.userModText = userModText;
		this.pic = pic;
		this.notice = notice;
	}


	public UserBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUseName() {
		return useName;
	}

	public void setUseName(String useName) {
		this.useName = useName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setUserModText(String userModText) {
		this.userModText = userModText;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	
	

	public String getNickName() {
		// TODO Auto-generated method stub
		return this.nickName;
	}

	public String getPic() {
		// TODO Auto-generated method stub
		return this.pic;
	}

	public String getUserModText() {
		// TODO Auto-generated method stub
		return this.userModText;
	}

	public String getUserName() {
		// TODO Auto-generated method stub
		return this.useName;
	}

	public void itemClick() {
		// TODO Auto-generated method stub
		FaceHelper.setBoardText(this.notice);
		GameMember.currentUser=this;
		for (int i = 0; i < GameMember.allUserLand.size(); i++) {
			LandItemBean ItemBean=GameMember.allUserLand.get(i);
//			ItemBean.getCropGrowThread().stopgrown();
		}
		//加载土地信息
		GameMember.loadUserLand();
		
	}


	
	

	
	
}
