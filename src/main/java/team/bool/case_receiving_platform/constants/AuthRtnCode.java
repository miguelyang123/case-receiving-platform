package team.bool.case_receiving_platform.constants;

public enum AuthRtnCode {

<<<<<<< HEAD
	SUCCESSFUL("200", "成功"),
	SUCCESSFUL_LOGIN("200", "成功登入"),
	ALREADY_LOGIN("200", "已登入狀態"),
	SUCCESSFUL_LOGOUT("200", "成功登出"),
	SUCCESSFUL_ADD_USER("200", "成功新增帳戶"),
	SUCCESSFUL_CHANGE("200", "成功更改"),
	SUCCESSFUL_SEND_EMAIL("200", "驗證碼成功送到指定信箱"),
	SUCCESSFUL_VERIFY("200", "驗證成功"),
=======
	SUCCESSFUL("200", "成功登入"),
	ALREADY_LOGIN("200", "已登入狀態"),
	SUCCESSFUL_LOGOUT("200", "成功登出"),
	SUCCESSFUL_ADD_USER("200", "成功新增帳戶"),
>>>>>>> Feature/PDF_fetch(upload+download)
	DATA_ERROR("400","資料錯誤"),
	ACCOUNT_EXISTED("400", "帳戶已存在!"),
	EMAIL_EXISTED("400", "信箱已存在!"),
	ACCOUNT_NOT_FOUND("404","找不到帳戶"),
	WORONG_PASSWORD("400","輸入密碼錯誤"),
	EMAIL_FORMAT_ERROR("400","信箱格式錯誤"),
	PASSWORD_FORMAT_ERROR("400","密碼格式錯誤"),
	NAME_FORMAT_ERROR("400","名稱格式錯誤"),
<<<<<<< HEAD
	PLEASE_LOGIN_FIRST("400","請先登入帳戶"),
	TOKEN_NOT_FOUND("404","無此驗證碼或驗證碼已超時"),
	WORONG_TOKEN("400","驗證碼錯誤");
=======
	PLEASE_LOGIN_FIRST("400","請先登入帳戶");
>>>>>>> Feature/PDF_fetch(upload+download)
	
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
