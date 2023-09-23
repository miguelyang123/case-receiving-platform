package team.bool.case_receiving_platform.constants;

public enum CaseRtnCode {

	SUCCESSFUL("200", "成功"),
	SUCCESSFUL_ADD("200", "成功新增案件"),
	SUCCESSFUL_UPDATE("200", "成功更新案件"),
	DATA_ERROR("400","資料錯誤"),
	SAVE_DATA_ERROR("400","儲存資料錯誤"),
	DATA_NOT_FOUND("404","查無資訊"),
	NAME_FORMAT_ERROR("400","案件名稱格式錯誤"),
	BUDGET_IS_NULL("400","請輸入案子預算"),
	BUDGET_INPUT_ERROR("400","輸入預算不可為負數或輸入值過大"),
	RATING_INPUT_ERROR("400","輸入評價不可為負數或大於5"),
	CONTENT_FORMAT_ERROR("400","輸入的內文過長請小於1000字"),
	ERROR("400", "error!");
	
	private String code;

	private String message;

	private CaseRtnCode(String code, String message) {
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
