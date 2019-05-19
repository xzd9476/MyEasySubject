package com.user.pojo;

public class ReplyLoginMessage {
	public static final int USER_NAME_NOT_EXIST = 1;// ÕËºÅÎ´×¢²á
	public static final int USER_PASSWORD_WRONG = 2;// ÃÜÂë´íÎó
	public static final int USER_NAME_OR_PASWORD_NULL = 3;
	public boolean successed;
	private Integer errStatus;  //´íÎóÔ­Òò

	public ReplyLoginMessage(boolean successed) {
		this.successed = successed;
	}

	public ReplyLoginMessage(boolean successed, Integer errStatus) {
		this.successed = successed;
		this.errStatus = errStatus;
	}

	public boolean isSuccessed() {
		return successed;
	}

	public void setSuccessed(boolean successed) {
		this.successed = successed;
	}

	public Integer getErrStatus() {
		return errStatus;
	}

	public void setErrStatus(Integer errStatus) {
		this.errStatus = errStatus;
	}

}
