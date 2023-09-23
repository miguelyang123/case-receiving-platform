package team.bool.case_receiving_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team.bool.case_receiving_platform.constants.RtnCode;
import team.bool.case_receiving_platform.repository.CaseCurrentStatusDao;
import team.bool.case_receiving_platform.vo.CaseStatusListRes;

@RestController
@RequestMapping("case_status")
public class CaseCurrentStatusController {

	@Autowired
	private CaseCurrentStatusDao caseStatusDao;

	@GetMapping("get_all_status")
	public CaseStatusListRes getAllStatus() {
		try {
			
			return new CaseStatusListRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage(),
					caseStatusDao.findAll());
			
		} catch (Exception e) {
			
			return new CaseStatusListRes(RtnCode.ERROR.getCode(), e.getMessage());
			
		}

	}

}
