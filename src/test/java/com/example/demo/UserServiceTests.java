package com.example.demo;

<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> Feature/PDF_fetch(upload+download)

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
<<<<<<< HEAD
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
=======
>>>>>>> Feature/PDF_fetch(upload+download)
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import team.bool.case_receiving_platform.CaseReceivingPlatformApplication;
import team.bool.case_receiving_platform.constants.AuthRtnCode;
import team.bool.case_receiving_platform.entity.User;
import team.bool.case_receiving_platform.repository.UserDao;
import team.bool.case_receiving_platform.service.ifs.UserService;
import team.bool.case_receiving_platform.vo.AuthRes;

<<<<<<< HEAD
@SpringBootTest(classes = CaseReceivingPlatformApplication.class)
public class UserServiceTests {

	@Autowired
	private UserService userService;

	@Autowired
	private UserDao userDao;
	

	@Autowired
	private JavaMailSender mailSender;

=======

@SpringBootTest(classes = CaseReceivingPlatformApplication.class)
public class UserServiceTests {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDao userDao;
	
>>>>>>> Feature/PDF_fetch(upload+download)
	@Test
	public void daoTest() {
//		AuthRes res = userService.login("test@gmail.com", "test");
//		System.out.println(res.toString());
//		System.out.println("test============");
<<<<<<< HEAD

		boolean bool = userDao.existsByEmail("test@gmail.com");
		System.out.println(bool);
	}

=======
		
		boolean bool = userDao.existsByEmail("test@gmail.com");
		System.out.println(bool);
	}
	
>>>>>>> Feature/PDF_fetch(upload+download)
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
<<<<<<< HEAD

=======
		
>>>>>>> Feature/PDF_fetch(upload+download)
		user.setEmail("test3@gmail.com");
		user.setPwd("testtest1");
		user.setUserName("測試名稱");
		user.setPhone("0987654321");
		res = userService.addNewUser(user);
		Assert.isTrue(res.getMessage().equals(AuthRtnCode.SUCCESSFUL_ADD_USER.getMessage()), "add new fail!");
		SystemOutMsg(res);
<<<<<<< HEAD

		userDao.deleteById(res.getUserInfo().getUuid());

	}

=======
		
		userDao.deleteById(res.getUserInfo().getUuid());
		
	}
	
>>>>>>> Feature/PDF_fetch(upload+download)
	private void SystemOutMsg(Object res) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String str = mapper.writeValueAsString(res);
			System.out.println(str);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
<<<<<<< HEAD

	@Test
	public void sendTokenToUserMail() throws Exception {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("javae7278@gmail.com");
		message.setTo("	miguelyang1@gmail.com");
		message.setSubject("主旨：Hello Miguel");
		message.setText("內容：這是一封測試信件，恭喜您成功發送了唷");

		mailSender.send(message);
	}
	
	@Test
	public void searchTest() {
		List<User> userList = userDao.searchUserByInput(null,null,null,null);
		if(userList.size()<=0) {
			System.out.println("userList is " + userList.size());
		}
	
		
		for(User user: userList) {
			SystemOutMsg(user);
		}
	}

=======
	
>>>>>>> Feature/PDF_fetch(upload+download)
}
