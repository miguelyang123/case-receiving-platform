package team.bool.case_receiving_platform.constants;

public enum AuthRtnCode {

	SUCCESSFUL("200", "成功登入"),
	ALREADY_LOGIN("200", "已登入狀態"),
	SUCCESSFUL_LOGOUT("200", "成功登出"),
	DATA_ERROR("400","資料錯誤"),
	ACCOUNT_EXISTED("400", "帳戶已存在!"),
	ACCOUNT_NOT_FOUND("404","找不到帳戶"),
	EMAIL_IS_NULL("400","信箱是空值"),
	PASSWORD_IS_NULL("400","密碼是空值"),
	WORONG_PASSWORD("400","密碼錯誤"),
	ACCOUNT_FORMAT_ERROR("400","信箱格式錯誤"),
	PASSWORD_FORMAT_ERROR("400","密碼格式錯誤"),
	PLEASE_LOGIN_FIRST("400","請先登入帳戶");
	
	private String code;

	private String message;

	private AuthRtnCode(String code, String message) {
		this.code = code;
		this.message = message;
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

}
