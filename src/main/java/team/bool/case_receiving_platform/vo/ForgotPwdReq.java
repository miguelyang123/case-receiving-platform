package team.bool.case_receiving_platform.vo;

public class ForgotPwdReq {

	private String email;

	private String resetPwdToken;
	
	private String newPwd;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getResetPwdToken() {
		return resetPwdToken;
	}

	public void setResetPwdToken(String resetPwdToken) {
		this.resetPwdToken = resetPwdToken;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	
	
}
