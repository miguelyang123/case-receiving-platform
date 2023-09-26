package com.example.demo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import team.bool.case_receiving_platform.CaseReceivingPlatformApplication;
import team.bool.case_receiving_platform.entity.Case;
import team.bool.case_receiving_platform.entity.CaseContractor;
import team.bool.case_receiving_platform.repository.CaseContractorDao;
import team.bool.case_receiving_platform.repository.CaseDao;
import team.bool.case_receiving_platform.service.ifs.CaseService;
import team.bool.case_receiving_platform.vo.CaseContractorListRes;
import team.bool.case_receiving_platform.vo.CaseListRes;

@SpringBootTest(classes = CaseReceivingPlatformApplication.class)
public class CaseServiceTests {

	@Autowired
	private CaseService caseService;

	@Autowired
	private CaseDao caseDao;

	@Autowired
	private CaseContractorDao caseContractorDao;

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
				"2d94e2dd-f635-4e59-bbe6-9f48bdf23fab", true, null, 0, null, 0);
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
//		LocalDateTime now = LocalDateTime.now();
//		System.out.println("====================================");
//		System.out.println("nowTime" + now);

//		List<Case> caseList = caseDao.searchCaseByInput(null, null,null,null,null,null,null,null,null);
//		List<Case> caseList = caseDao.searchCaseByInput(now);

		CaseListRes result = caseService.findCaseWithInput(null, null, null, null, null, null, null, null, null, null,
				null);

		List<Case> caseList = result.getCaseList();

		for (Case fCase : caseList) {
//			System.out.println(fCase.getCaseName());
			System.out.println(fCase.getId());
			
		}
		
		

	}

	@Test
	public void chooseContractorsTest() {

		List<String> idList = new ArrayList<>(
				Arrays.asList("e451beb8-fc91-472b-933b-b96a0e8c853b", "e81b75d7-78b2-4ff0-aea5-a91b49b5aaaf"));

//		String userIdListStr = String.join(",", idList);

//		System.out.println(userIdListStr);

//		caseContractorDao.chooseContractors(1 , userIdListStr);
		int resNum = caseContractorDao.chooseContractors(1, idList, false);
		System.out.println("resNum : " + resNum);
	}

	@Test
	public void findByCaseIdAndUserIdListTest() {

		List<String> idList = new ArrayList<>(
				Arrays.asList("e451beb8-fc91-472b-933b-b96a0e8c853b", "e81b75d7-78b2-4ff0-aea5-a91b49b5aaaf"));

		List<CaseContractor> res = caseContractorDao.findByCaseIdAndUserIdList(1, idList);

		res.forEach(item -> {
			
			System.out.println(item.getCaseId());
			System.out.println(item.getContractorUid());
			System.out.println(item.getIsAccepted());
			System.out.println(item.getAcceptDate());

		});
	}
	
	@Test
	public void caseCompletionTest() {
//		CaseContractorListRes res = caseService.caseCompletion(2, 4);
//		
//		System.out.println(res.getMessage());
	}
	
	@Test
	public void existsByCaseIdAndContractorUidTest() {
		UUID uuid = UUID.fromString("e451beb8-fc91-472b-933b-b96a0e8c853b");
		
		boolean bool = caseContractorDao.existsByCaseIdAndContractorUid(2, uuid);
		System.out.println(bool);
	}
	
	@Test
	public void searchUserByCaseIdDaoTest() {
		List<Case> caseList = caseDao.searchAcceptCaseByUserId("da1c30fe-2728-45c1-830b-23d0fc43f676");
		caseList.forEach(caseInfo->{
			System.out.println(caseInfo.getCaseName());
		});
	}
	

}
