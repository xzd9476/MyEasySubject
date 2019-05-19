package com.chat.pojo;

import java.util.Date;

public class Message {
	private String userName;// 发送者
	private Date sendDate;// 发送时间
	private String content;// 发送内容
	private String messageType;// 发送消息的类型（文本："text",图片："image"）

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	@Override
	public String toString() {
		return "Message [userName=" + userName + ", sendDate=" + sendDate + ", content=" + content + ", messageType="
				+ messageType + "]";
	}

}
