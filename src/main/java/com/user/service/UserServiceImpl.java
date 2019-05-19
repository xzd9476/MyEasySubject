package com.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chat.dao.MessageRecordDao;
import com.chat.pojo.MessageRecord;
import com.user.dao.LoginInfoDao;
import com.user.dao.UserDao;
import com.user.pojo.LoginInfo;
import com.user.pojo.User;

@Service("userservice")
public class UserServiceImpl implements UserService {
	@Autowired
	@Qualifier("userdao")
	private UserDao userdao;
	
	@Autowired
	@Qualifier("logininfodao")
	private LoginInfoDao logininfodao;
	
	@Autowired
	@Qualifier("messagerecorddao")
	private MessageRecordDao messagerecorddao;
	
	@Override
	public void doLogin() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doregist(String name ,String password) {
		// TODO Auto-generated method stub
		userdao.insertOne(name,password);
	}

	@Override
	public boolean isExistUser(String username) {
		// TODO Auto-generated method stub
		User user=userdao.isExitUser(username);
		
		return user != null;
	}

	@Override
	public boolean checkpassword(String name, String password) {
		User user=userdao.checkpassword(name,password);
		//ÃÜÂë´íÎó£¬userÎª¿Õ£¬·µ»Øfalse£»
		return user!=null;
	}

	@Override
	public void addUserLoginInfo(LoginInfo loginInfo) {
		// TODO Auto-generated method stub
		logininfodao.addUserLoginInfo(loginInfo);
	}

	@Override
	public User getUserByName(String name) {
		User user=null;
		user=userdao.getUserByName(name);
		return user;
	}

	@Override
	public void addUserMessageRecord(MessageRecord messageRecordDo) {
		// TODO Auto-generated method stub
		messagerecorddao.addUserMessageRecord(messageRecordDo);
		
	}
	
}
