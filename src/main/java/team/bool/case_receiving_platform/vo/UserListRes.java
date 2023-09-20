package team.bool.case_receiving_platform.vo;

import java.util.List;

import team.bool.case_receiving_platform.entity.UserInfo;

public class UserListRes {

	private String code;

	private String message;

	private List<UserInfo> userInfoList;

	public UserListRes() {
		super();
	}

	public UserListRes(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public UserListRes(String code, String message, List<UserInfo> userInfoList) {
		super();
		this.code = code;
		this.message = message;
		this.userInfoList = userInfoList;
	}

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

	public List<UserInfo> getUserInfoList() {
		return userInfoList;
	}

	public void setUserInfoList(List<UserInfo> userInfoList) {
		this.userInfoList = userInfoList;
	}

}
