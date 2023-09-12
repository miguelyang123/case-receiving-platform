package team.bool.case_receiving_platform.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


@Entity
@Table(name = "case_contractor")
@IdClass(CaseContractorId.class)
public class CaseContractor {
	
	// 雙ID
	// 案子ID(PK)
	@Id
	@Column(name = "case_id")
	private int caseId;
	
	// 接案帳戶ID(user uuid)(PK)
	@Id
	@Type(type = "uuid-char")
	@Column(name = "contractor_uid")
	private UUID contractorUid;
	
	// 是否成功接案
	@Column(name = "is_accepted")
	private boolean isAccepted;
	
	// 案子接案時間
	@Column(name = "accept_date")
	private LocalDateTime acceptDate;
	
	// 案子完成時間
	@Column(name = "accepted_date")
	private LocalDateTime acceptedDate;

	// 建構方法
	public CaseContractor() {
		super();
	}

	public CaseContractor(int caseId, UUID contractorUid, boolean isAccepted, LocalDateTime acceptDate,
			LocalDateTime acceptedDate) {
		super();
		this.caseId = caseId;
		this.contractorUid = contractorUid;
		this.isAccepted = isAccepted;
		this.acceptDate = acceptDate;
		this.acceptedDate = acceptedDate;
	}

	// Getters and setters
	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}

	public UUID getContractorUid() {
		return contractorUid;
	}

	public void setContractorUid(UUID contractorUid) {
		this.contractorUid = contractorUid;
	}

	public boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public LocalDateTime getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(LocalDateTime acceptDate) {
		this.acceptDate = acceptDate;
	}

	public LocalDateTime getAcceptedDate() {
		return acceptedDate;
	}

	public void setAcceptedDate(LocalDateTime acceptedDate) {
		this.acceptedDate = acceptedDate;
	}
	
	
	
	
}
