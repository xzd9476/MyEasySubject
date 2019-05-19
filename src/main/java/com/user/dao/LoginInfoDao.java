package com.user.dao;

import org.springframework.stereotype.Repository;

import com.user.pojo.LoginInfo;

@Repository("logininfodao")
public interface LoginInfoDao {

	void addUserLoginInfo(LoginInfo loginInfo);

}
