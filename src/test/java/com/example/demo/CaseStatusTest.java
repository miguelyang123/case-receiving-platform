package com.example.demo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import team.bool.case_receiving_platform.CaseReceivingPlatformApplication;
import team.bool.case_receiving_platform.entity.CaseCurrentStatus;
import team.bool.case_receiving_platform.repository.CaseCurrentStatusDao;

@SpringBootTest(classes = CaseReceivingPlatformApplication.class)
public class CaseStatusTest {

	@Autowired
	private CaseCurrentStatusDao caseStatusDao;
	
	
	@Test
	public void caseStatusDaoTest() {
		
		 List<CaseCurrentStatus> res = caseStatusDao.findAll();
		 res.forEach(item->{
			 System.out.println(item.getStatusName());
		 });
	}
}
