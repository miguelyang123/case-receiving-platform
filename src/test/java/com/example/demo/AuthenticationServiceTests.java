package com.example.demo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import team.bool.case_receiving_platform.CaseReceivingPlatformApplication;
import team.bool.case_receiving_platform.entity.User;
import team.bool.case_receiving_platform.repository.UserDao;
import team.bool.case_receiving_platform.service.ifs.UserService;
import team.bool.case_receiving_platform.vo.AuthRes;


@SpringBootTest(classes = CaseReceivingPlatformApplication.class)
public class AuthenticationServiceTests {
	
	@Autowired
	private UserService authenticationService;
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void verifyUserTest() {
		AuthRes res = authenticationService.login("test@gmail.com", "test");
		System.out.println(res.toString());
		System.out.println("test============");
		

		
	}
	
}
