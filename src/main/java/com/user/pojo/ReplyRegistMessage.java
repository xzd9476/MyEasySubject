package com.user.pojo;

public class ReplyRegistMessage {
	public static final int USER_NAME_EXTST=1;//�û����Ѿ�����
	private boolean successed;//ע��ɹ�
	private int errStatus;//����ԭ��
	
	public ReplyRegistMessage(boolean isSuccessed){
		this.successed=isSuccessed;
	}
	
	public ReplyRegistMessage(boolean isSuccessed,int errStatus){
		this.successed=isSuccessed;
		this.errStatus=errStatus;
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
