package team.bool.case_receiving_platform.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchUserReq {

	// 姓名
	@JsonProperty("user_name")
	private String userName;

	// 大於此評價
	private Integer minRating;

	// 身份權限(是否是管理者)
	private Boolean isAdministrator;

	// 鎖定狀態
	private Boolean lockedStatus;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getMinRating() {
		return minRating;
	}

	public void setMinRating(Integer minRating) {
		this.minRating = minRating;
	}

	public Boolean getIsAdministrator() {
		return isAdministrator;
	}

	public void setIsAdministrator(Boolean isAdministrator) {
		this.isAdministrator = isAdministrator;
	}

	public Boolean getLockedStatus() {
		return lockedStatus;
	}

	public void setLockedStatus(Boolean lockedStatus) {
		this.lockedStatus = lockedStatus;
	}

}
