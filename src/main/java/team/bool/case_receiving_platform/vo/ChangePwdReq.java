package team.bool.case_receiving_platform.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangePwdReq {

	@JsonProperty("old_password")
	private String oldPwd;

	@JsonProperty("new_password")
	private String newPwd;

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

}
