package team.bool.case_receiving_platform.vo;

import java.util.List;

import team.bool.case_receiving_platform.entity.Case;

public class CaseListRes {

	private String code;

	private String message;

	private List<Case> caseList;

	public CaseListRes() {
		super();
	}

	public CaseListRes(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public CaseListRes(String code, String message, List<Case> caseList) {
		super();
		this.code = code;
		this.message = message;
		this.caseList = caseList;
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

	public List<Case> getCaseList() {
		return caseList;
	}

	public void setCaseList(List<Case> caseList) {
		this.caseList = caseList;
	}

}
