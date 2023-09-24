package team.bool.case_receiving_platform.service.ifs;

import team.bool.case_receiving_platform.vo.ContractorListRes;

public interface CaseContractorService {

	public ContractorListRes searchUserByCaseId(Integer caseId);
	
}
