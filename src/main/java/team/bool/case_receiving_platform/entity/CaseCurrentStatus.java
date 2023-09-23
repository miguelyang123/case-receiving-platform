package team.bool.case_receiving_platform.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "case_current_status")
public class CaseCurrentStatus {

	// 狀態選項(英文)
	@Id
	@Column(name = "status_name")
	private String statusName;

	// 中文狀態選項
	@Column(name = "status_zh_name")
	private String statusZhName;

	public CaseCurrentStatus() {
		super();
	}

	public CaseCurrentStatus(String statusName, String statusZhName) {
		super();
		this.statusName = statusName;
		this.statusZhName = statusZhName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusZhName() {
		return statusZhName;
	}

	public void setStatusZhName(String statusZhName) {
		this.statusZhName = statusZhName;
	}

	
	
	
}
