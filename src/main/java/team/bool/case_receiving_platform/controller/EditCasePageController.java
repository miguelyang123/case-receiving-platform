package team.bool.case_receiving_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import team.bool.case_receiving_platform.service.ifs.CaseContractorService;
import team.bool.case_receiving_platform.service.ifs.CaseService;
import team.bool.case_receiving_platform.vo.CaseCompletionReq;
import team.bool.case_receiving_platform.vo.CaseContractorListRes;
import team.bool.case_receiving_platform.vo.ChooseContractorsReq;
import team.bool.case_receiving_platform.vo.ContractorListRes;

@RestController
@RequestMapping("edit_case_page")
public class EditCasePageController {

	@Autowired
	private CaseContractorService ccService;
	
	@Autowired
	private CaseService caseService;

	@GetMapping("search_user_by_caseid")
	public ContractorListRes searchUserByCaseIdAndAccepted(@RequestParam(name = "caseId") Integer caseId, @RequestParam(name = "isAccepted", required = false) Boolean isAccepted) {

		return ccService.searchUserByCaseIdAndAccepted(caseId, isAccepted);
	}

	@PostMapping("choose_contractors")
	public CaseContractorListRes chooseContractors(@RequestBody ChooseContractorsReq req) {

		return ccService.initiatorChooseContractors(req.getCaseId(), req.getUserIdList(), req.getIsAccepted());
	}
	
	@PostMapping("case_completion")
	public CaseContractorListRes caseCompletion(@RequestBody CaseCompletionReq req) {
		
		return caseService.caseCompletion(req.getCaseId(), req.getCaseRating());
	
	}
	
}
