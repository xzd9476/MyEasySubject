package com.chat.dao;

import org.springframework.stereotype.Repository;

import com.chat.pojo.MessageRecord;

@Repository("messagerecorddao")
public interface MessageRecordDao {

	void addUserMessageRecord(MessageRecord messageRecordDo);

}
