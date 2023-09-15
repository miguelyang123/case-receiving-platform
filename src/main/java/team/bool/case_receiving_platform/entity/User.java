package team.bool.case_receiving_platform.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// Json映照到UserInfo 忽略的欄位

@Entity
@Table(name = "user")
public class User {

	// UUID
	@Id
	@Type(type = "uuid-char")
	@Column(name = "uuid")
	private UUID uuid;

	// E-Mail
	@Column(name = "email")
	private String email;

	// 姓名
	@JsonProperty("user_name")
	@Column(name = "user_name")
	private String userName;

	// 密碼 (加密)
	@JsonProperty("password")
	@Column(name = "pwd")
	private String pwd;

	// 手機
	@Column(name = "phone")
	private String phone;

	// 評價
	@Column(name = "rating")
	private int rating;

	// 履歷 (檔案位置名稱)
	@Column(name = "resume_pdf_path")
	private String resumePdfPath;

	// 身份權限(是否是管理者)
	@Column(name = "is_administrator")
	private boolean isAdministrator;

	// 鎖定狀態
	@Column(name = "locked_status")
	private boolean lockedStatus;

	
	// 建構方法
	public User() {
		super();
	}

	public User(UUID uuid, String email, String pwd, String phone, int rating, String resumePdfPath,
			boolean isAdministrator, boolean lockedStatus) {
		super();
		this.uuid = uuid;
		this.email = email;
		this.pwd = pwd;
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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getResumePdfPath() {
		return resumePdfPath;
	}

	public void setResumePdfPath(String resumePdfPath) {
		this.resumePdfPath = resumePdfPath;
	}

	public boolean isAdministrator() {
		return isAdministrator;
	}

	public void setAdministrator(boolean isAdministrator) {
		this.isAdministrator = isAdministrator;
	}

	public boolean isLockedStatus() {
		return lockedStatus;
	}

	public void setLockedStatus(boolean lockedStatus) {
		this.lockedStatus = lockedStatus;
	}

    @Override
    public String toString() {
        return "User [uuid=" + uuid + ", email=" + email + ", userName=" + userName + ", pwd=" + pwd + ", phone=" + phone + ", rating=" + rating + ", resumePdfPath="
               + resumePdfPath + ", isAdministrator=" + isAdministrator + ", lockedStatus=" + lockedStatus + "]";
    }

}
