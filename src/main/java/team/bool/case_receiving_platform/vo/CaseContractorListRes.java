package team.bool.case_receiving_platform.vo;

import java.util.List;

import team.bool.case_receiving_platform.entity.CaseContractor;

public class CaseContractorListRes {

	private String code;

	private String message;

	private List<CaseContractor> caseContractorList;

	public CaseContractorListRes() {
		super();
	}

	public CaseContractorListRes(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public CaseContractorListRes(String code, String message, List<CaseContractor> caseContractorList) {
		super();
		this.code = code;
		this.message = message;
		this.caseContractorList = caseContractorList;
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

	public List<CaseContractor> getCaseContractorList() {
		return caseContractorList;
	}

	public void setCaseContractorList(List<CaseContractor> caseContractorList) {
		this.caseContractorList = caseContractorList;
	}

}
