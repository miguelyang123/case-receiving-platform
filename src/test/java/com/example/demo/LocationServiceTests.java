package com.example.demo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import team.bool.case_receiving_platform.CaseReceivingPlatformApplication;
import team.bool.case_receiving_platform.entity.Location;
import team.bool.case_receiving_platform.repository.LocationDao;

@SpringBootTest(classes = CaseReceivingPlatformApplication.class)

public class LocationServiceTests {

	@Autowired
	LocationDao locationDao;
	
	@Test
	public void daoTest() {
		 List<Location> list = locationDao.selectOnsite();
//		 List<Location> list = locationDao.selectRemote();
		 
		 list.forEach((item)->{
			 System.out.println(item.getLocationName());
		 });
		 
		 
	}
	
	
}
