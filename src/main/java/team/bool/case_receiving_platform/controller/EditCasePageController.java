package team.bool.case_receiving_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import team.bool.case_receiving_platform.service.ifs.CaseContractorService;
import team.bool.case_receiving_platform.vo.ContractorListRes;
import team.bool.case_receiving_platform.vo.UserListRes;

@RestController
@RequestMapping("edit_case_page")
public class EditCasePageController {

	@Autowired
	private CaseContractorService cService;

	@GetMapping("search_user_by_caseid")
	public ContractorListRes searchUserByCaseId(@RequestParam(name = "caseId") Integer caseId) {

		return cService.searchUserByCaseId(caseId);
	}
}
