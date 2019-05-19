package com.user.dao;

//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import com.chat.pojo.MessageRecord;
import com.user.pojo.LoginInfo;
import com.user.pojo.User;

@Repository("userdao")
public interface UserDao {
	void findByNameAndPass(User user);
	
	void insertOne(String name,String password);

	User isExitUser(String username);

	User checkpassword(String name, String password);

	User getUserByName(String name);

	void addUserMessageRecord(MessageRecord messageRecordDo);



}
