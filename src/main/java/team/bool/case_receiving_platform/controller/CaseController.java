package team.bool.case_receiving_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.bool.case_receiving_platform.entity.Case;
import team.bool.case_receiving_platform.entity.CaseContractor;
import team.bool.case_receiving_platform.service.ifs.CaseService;
import team.bool.case_receiving_platform.vo.CaseAddTemp;
import team.bool.case_receiving_platform.vo.CaseContractorListRes;
import team.bool.case_receiving_platform.vo.CaseListRes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("case_api")
public class CaseController {

	@Autowired
	private CaseService caseService;

	@PostMapping("add_new_case")
	public CaseListRes addNewCase(@RequestBody Case newCase) {

		return caseService.addNewCase(newCase);

	}

	@PostMapping("edit_case")
	public CaseListRes editCase(@RequestBody Case newCase) {

		return caseService.editCase(newCase);
	}

//    @PostMapping("add_new_case_fix")
//    public CaseListRes addNewCaseFix(@RequestBody CaseAddTemp newCase) {
//        
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime time = LocalDateTime.now();
//        String localTime = df.format(time);
//        LocalDateTime ldt = LocalDateTime.parse(newCase.getDeadline(),df);
//        
//        Case newCase02 = new Case();
//        newCase02.setId(newCase.getId());
//        newCase02.setCaseName(newCase.getCaseName());
//        newCase02.setBudget(newCase.getBudget());
//        newCase02.setLocation(newCase.getLocation());
//        newCase02.setContent(newCase.getContent());
//        newCase02.setDeadline(ldt);
//        newCase02.setCreatedDate(null);
//        newCase02.setCaseClass(null);
//        newCase02.setInitiator(newCase.getInitiator());
//        newCase02.setOnShelf(true);
//        
//        return caseService.addNewCase(newCase02);
//
//    }

	@PostMapping("contractor_accept_case")
	public CaseContractorListRes contractorAcceptCase(@RequestBody CaseContractor newCaseContractor) {
		return caseService.contractorAcceptCase(newCaseContractor);
	}

}
