package team.bool.case_receiving_platform.vo;

import team.bool.case_receiving_platform.entity.UserInfo;

public class PdfUpdateRes {

    private String code;

    private String message;

    private UserInfo userInfo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public PdfUpdateRes(String code, String message, UserInfo userInfo) {
        super();
        this.code = code;
        this.message = message;
        this.userInfo = userInfo;
    }

    public PdfUpdateRes() {
        super();
        // TODO Auto-generated constructor stub
    }

}
