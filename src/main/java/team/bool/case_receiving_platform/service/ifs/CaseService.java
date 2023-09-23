package team.bool.case_receiving_platform.service.ifs;

import java.time.LocalDateTime;

import team.bool.case_receiving_platform.entity.Case;
import team.bool.case_receiving_platform.vo.CaseListRes;

public interface CaseService {

	public CaseListRes addNewCase(Case newCase);

	public CaseListRes editCase(Case newCase);

	public CaseListRes findCaseWithInput(String searchKeyword, Integer minBudget, Integer maxBudget, String location,
			LocalDateTime deadlineFrom, LocalDateTime deadlineTo, String caseClass, String initiator, Boolean onShelf,
			String currentStatus, Integer caseRating);

	public void getContractorWithCaseId();

}
