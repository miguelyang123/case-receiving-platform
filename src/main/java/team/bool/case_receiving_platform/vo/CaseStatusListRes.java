package team.bool.case_receiving_platform.vo;

import java.util.List;

import team.bool.case_receiving_platform.entity.CaseCurrentStatus;

public class CaseStatusListRes {

	private String code;

	private String message;

	private List<CaseCurrentStatus> statusList;

	public CaseStatusListRes() {
		super();
	}

	public CaseStatusListRes(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public CaseStatusListRes(String code, String message, List<CaseCurrentStatus> statusList) {
		super();
		this.code = code;
		this.message = message;
		this.statusList = statusList;
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

	public List<CaseCurrentStatus> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<CaseCurrentStatus> statusList) {
		this.statusList = statusList;
	}

}
