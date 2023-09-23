package com.example.demo;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import team.bool.case_receiving_platform.CaseReceivingPlatformApplication;
import team.bool.case_receiving_platform.entity.Case;
import team.bool.case_receiving_platform.repository.CaseDao;
import team.bool.case_receiving_platform.service.ifs.CaseService;
import team.bool.case_receiving_platform.vo.CaseListRes;

@SpringBootTest(classes = CaseReceivingPlatformApplication.class)
public class CaseServiceTests {

	@Autowired
	private CaseService caseService;

	@Autowired
	public CaseDao caseDao;
	
	@Test
	public void test() {

		String str = "我的案子";

		System.out.println(str.length());

	}

	/**
	 * 
	 */
	@Test
	public void addTest() {
		Case newCase = new Case(null, "測試案件", 10000, "1A", "我是內文", LocalDateTime.now(), LocalDateTime.now(), null,
				"2d94e2dd-f635-4e59-bbe6-9f48bdf23fab", true,null,0, null,0);
//		newCase.setId(9);
//		newCase.setCaseName("測試案件");
//		newCase.setBudget(10000);
//		newCase.setLocation("1A");
//		newCase.setContent("我是內文");
//		newCase.setDeadline(LocalDateTime.now());
//		newCase.setCreatedDate(LocalDateTime.now());
//		newCase.setInitiator("2d94e2dd-f635-4e59-bbe6-9f48bdf23fab");
//		newCase.one

		Case savedCase = caseDao.save(newCase);
		System.out.println(savedCase.getId());
//		caseService.addNewCase(newCase);
	}
	
	@Test
	public void findCaseTest() {
		 LocalDateTime now = LocalDateTime.now();
		 System.out.println("====================================");
		System.out.println("nowTime"+now);
		
//		List<Case> caseList = caseDao.searchCaseByInput(null, null,null,null,null,null,null,null,null);
//		List<Case> caseList = caseDao.searchCaseByInput(now);
		
		CaseListRes result = caseService.findCaseWithInput(null, 0, 10000, null, null, null, null, null, null, null, null);
		
		List<Case> caseList = result.getCaseList();
		
		for(Case fCase : caseList) {
			System.out.println(fCase.getCaseName());
		}
		
	}

}
