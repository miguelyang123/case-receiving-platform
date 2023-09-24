package team.bool.case_receiving_platform.constants;

public enum RtnCode {

	SUCCESSFUL("200", "成功"),
	DATA_ERROR("400","資料錯誤"),
	DATA_NOT_FOUND("404","查無資訊"),
	ACCOUNT_NOT_FOUND("404","找不到帳戶"),
	ACCOUNT_NOT_FOUND_WITH_ID("404","此案子無人接案"),
	INPUT_RATING_ERROR("400","評價輸入錯誤"),
	INPUT_CASE_ID_NULL("400","輸入ID為空值"),
	ERROR("400", "error!");
	
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
