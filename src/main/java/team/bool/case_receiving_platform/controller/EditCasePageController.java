package team.bool.case_receiving_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import team.bool.case_receiving_platform.service.ifs.CaseContractorService;
import team.bool.case_receiving_platform.vo.CaseContractorListRes;
import team.bool.case_receiving_platform.vo.ChooseContractorsReq;
import team.bool.case_receiving_platform.vo.ContractorListRes;

@RestController
@RequestMapping("edit_case_page")
public class EditCasePageController {

	@Autowired
	private CaseContractorService ccService;

	@GetMapping("search_user_by_caseid")
	public ContractorListRes searchUserByCaseId(@RequestParam(name = "caseId") Integer caseId) {

		return ccService.searchUserByCaseId(caseId);
	}

	@PostMapping("choose_contractors")
	public CaseContractorListRes chooseContractors(@RequestBody ChooseContractorsReq req) {

		return ccService.initiatorChooseContractors(req.getCaseId(), req.getUserIdList(), req.getIsAccepted());
	}
}
