package team.bool.case_receiving_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team.bool.case_receiving_platform.entity.Case;
import team.bool.case_receiving_platform.service.ifs.CaseService;
import team.bool.case_receiving_platform.vo.CaseListRes;

@RestController
@RequestMapping("case_api")
public class CaseController {

	@Autowired
	private CaseService caseService;

	@PostMapping("add_new_case")
	public CaseListRes addNewCase(@RequestBody Case newCase) {

		return caseService.addNewCase(newCase);

	}

}
