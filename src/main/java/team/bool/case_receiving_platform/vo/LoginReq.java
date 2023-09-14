package team.bool.case_receiving_platform.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginReq {

	private String email;
	
	@JsonProperty("password")
	private String pwd;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
