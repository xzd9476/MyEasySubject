package com.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chat.pojo.MessageRecord;
import com.user.dao.UserDao;
import com.user.pojo.LoginInfo;
import com.user.pojo.User;

public interface UserService {
	void doLogin();
	void doregist(String name, String password);
	boolean isExistUser(String username);
	boolean checkpassword(String name, String password);
	void addUserLoginInfo(LoginInfo loginInfo);
	User getUserByName(String name);
	void addUserMessageRecord(MessageRecord messageRecordDo);
}
