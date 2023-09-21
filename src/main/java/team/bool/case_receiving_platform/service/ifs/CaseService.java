package team.bool.case_receiving_platform.service.ifs;

import team.bool.case_receiving_platform.vo.CaseListRes;

public interface CaseService {
	
	public CaseListRes addNewCase();
	
	public CaseListRes editCase();
	
	public CaseListRes findCaseWithInput();

}
