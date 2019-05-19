package com.user.enums;

/**
 * @author MccreeFei
 * @create 2018-04-28 ����1:49
 */
public enum LoginTypeEnum {
    LOGIN(1, "����"),
    LOGOUT(2, "����");
    private int code;
    private String desc;

    LoginTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
