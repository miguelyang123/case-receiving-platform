package team.bool.case_receiving_platform.constants;

public enum RtnCode {

	SUCCESSFUL("200", "successful!"),
	DATA_ERROR("400","data error!"),
	INSUFFICIENT_BALANCE("400", "insufficient balance!"),
	ACCOUNT_EXISTED("400", "account existed!"),
	ACCOUNT_NOT_FOUND("404","account not found!"),
	EMAIL_IS_NULL("400","email is null!"),
	PASSWORD_IS_NULL("400","password is null!"),
	WORONG_PASSWORD("400","wrong password!"),
	ACCOUNT_FORMAT_ERROR("400","account format error!"),
	PASSWORD_FORMAT_ERROR("400","password format error!"),
	AMOUNT_FORMAT_ERROR("400","amount format error!"),
	PLEASE_LOGIN_FIRST("400","Please login first!");
	
	private String code;

	private String message;

	private RtnCode(String code, String message) {
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
