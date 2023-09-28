package team.bool.case_receiving_platform.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// User with out pwd
@JsonIgnoreProperties(value = { "password" })
public class UserInfo {

	// UUID
	private UUID uuid;

	// E-Mail
	private String email;

	// 姓名
	@JsonProperty("user_name")
	private String userName;

	// 手機
	private String phone;

	// 評價
	private Double rating;

	// 履歷 (檔案位置名稱)
	private String resumePdfPath;

	// 身份權限(是否是管理者)
	@JsonProperty("administrator")
	private Boolean isAdministrator;

	// 鎖定狀態
	private Boolean lockedStatus;

	// 建構方法
	public UserInfo() {
		super();
	}

	public UserInfo(UUID uuid, String email, String userName, String phone, Double rating, String resumePdfPath,
			Boolean isAdministrator, Boolean lockedStatus) {
		super();
		this.uuid = uuid;
		this.email = email;
		this.userName = userName;
		this.phone = phone;
		this.rating = rating;
		this.resumePdfPath = resumePdfPath;
		this.isAdministrator = isAdministrator;
		this.lockedStatus = lockedStatus;
	}

	// Getters and setters
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getResumePdfPath() {
		return resumePdfPath;
	}

	public void setResumePdfPath(String resumePdfPath) {
		this.resumePdfPath = resumePdfPath;
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
