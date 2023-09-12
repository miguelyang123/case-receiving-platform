package team.bool.case_receiving_platform.entity;

import java.io.Serializable;
import java.util.UUID;

@SuppressWarnings("serial")
public class CaseContractorId implements Serializable {

	private int caseId;

	private UUID contractorUid;

	public CaseContractorId() {
		super();
	}

	public CaseContractorId(int caseId, UUID contractorUid) {
		super();
		this.caseId = caseId;
		this.contractorUid = contractorUid;
	}

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

}
