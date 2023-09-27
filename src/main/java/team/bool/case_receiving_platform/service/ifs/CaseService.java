package team.bool.case_receiving_platform.service.ifs;

import java.time.LocalDateTime;
import java.util.List;

import team.bool.case_receiving_platform.entity.Case;
import team.bool.case_receiving_platform.entity.CaseContractor;
import team.bool.case_receiving_platform.vo.CaseContractorListRes;
import team.bool.case_receiving_platform.vo.CaseListRes;

public interface CaseService {

	public CaseListRes addNewCase(Case newCase);

	public CaseListRes editCase(Case newCase);

	public CaseListRes findCaseWithInput(String searchKeyword, Integer minBudget, Integer maxBudget, String location,
			LocalDateTime deadlineFrom, LocalDateTime deadlineTo, String caseClass, String initiator, Boolean onShelf,
			String currentStatus, Integer caseRating, String fieldKey, String sortParam);

	public CaseListRes findAcceptCaseWithUserId(String userId);
	
	public CaseContractorListRes caseCompletion(Integer caseId, Integer caseRating);

}
