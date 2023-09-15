package com.example.demo;


import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import team.bool.case_receiving_platform.CaseReceivingPlatformApplication;
import team.bool.case_receiving_platform.constants.AuthRtnCode;
import team.bool.case_receiving_platform.entity.User;
import team.bool.case_receiving_platform.repository.UserDao;
import team.bool.case_receiving_platform.service.ifs.UserService;
import team.bool.case_receiving_platform.vo.AuthRes;


@SpringBootTest(classes = CaseReceivingPlatformApplication.class)
public class UserServiceTests {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void daoTest() {
//		AuthRes res = userService.login("test@gmail.com", "test");
//		System.out.println(res.toString());
//		System.out.println("test============");
		
		boolean bool = userDao.existsByEmail("test@gmail.com");
		System.out.println(bool);
	}
	
//	@Transactional
	@Test
	public void addNewUserTest() {
		User user = new User();
		user.setEmail("test@gmail.com");
		user.setPwd("testtest1");
		user.setUserName("測試名稱");
		user.setPhone("0987654321");
		AuthRes res = userService.addNewUser(user);
		Assert.isTrue(!res.getMessage().equals(AuthRtnCode.SUCCESSFUL_ADD_USER.getMessage()), "add new fail!");
		SystemOutMsg(res);
		
		user.setEmail("test3@gmail.com");
		user.setPwd("testtest1");
		user.setUserName("測試名稱");
		user.setPhone("0987654321");
		res = userService.addNewUser(user);
		Assert.isTrue(res.getMessage().equals(AuthRtnCode.SUCCESSFUL_ADD_USER.getMessage()), "add new fail!");
		SystemOutMsg(res);
		
		userDao.deleteById(res.getUserInfo().getUuid());
		
	}
	
	private void SystemOutMsg(Object res) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String str = mapper.writeValueAsString(res);
			System.out.println(str);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
}
