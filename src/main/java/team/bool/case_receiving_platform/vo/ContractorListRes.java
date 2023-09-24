package team.bool.case_receiving_platform.vo;

import java.util.List;

public class ContractorListRes {

	private String code;

	private String message;

	private List<ContractorInfoVo> userInfoList;

	public ContractorListRes() {
		super();
	}

	public ContractorListRes(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public ContractorListRes(String code, String message, List<ContractorInfoVo> userInfoList) {
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

	public List<ContractorInfoVo> getUserInfoList() {
		return userInfoList;
	}

	public void setUserInfoList(List<ContractorInfoVo> userInfoList) {
		this.userInfoList = userInfoList;
	}

}
