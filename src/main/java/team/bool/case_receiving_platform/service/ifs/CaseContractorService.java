package team.bool.case_receiving_platform.service.ifs;

import java.util.List;

import team.bool.case_receiving_platform.entity.CaseContractor;
import team.bool.case_receiving_platform.vo.CaseContractorListRes;
import team.bool.case_receiving_platform.vo.CaseListRes;
import team.bool.case_receiving_platform.vo.ContractorListRes;

public interface CaseContractorService {

	public ContractorListRes searchUserByCaseIdAndAccepted(Integer caseId, Boolean isAccepted);
	
	public CaseContractorListRes contractorAcceptCase(CaseContractor newCaseContractor);
	
	public CaseContractorListRes initiatorChooseContractors(Integer caseId, List<String> userIdList, Boolean isAccepted);
	
}
