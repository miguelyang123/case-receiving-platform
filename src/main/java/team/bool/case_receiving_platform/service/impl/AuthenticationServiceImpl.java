package team.bool.case_receiving_platform.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import team.bool.case_receiving_platform.constants.RtnCode;
import team.bool.case_receiving_platform.entity.User;
import team.bool.case_receiving_platform.entity.UserInfo;
import team.bool.case_receiving_platform.repository.UserDao;
import team.bool.case_receiving_platform.service.ifs.AuthenticationService;
import team.bool.case_receiving_platform.vo.AuthenticationRes;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserDao userDao;

	@Override
	public AuthenticationRes login(String email, String pwd) {

		return checkUserEmailAndPwd(email, pwd);
	}

	// check Email And Pwd at DB
	private AuthenticationRes checkUserEmailAndPwd(String email, String pwd) {
		// email or pwd is null
		if (!StringUtils.hasText(email)) {
			return new AuthenticationRes(RtnCode.EMAIL_IS_NULL.getCode(), RtnCode.EMAIL_IS_NULL.getMessage());
		}
		if (!StringUtils.hasText(pwd)) {
			return new AuthenticationRes(RtnCode.PASSWORD_IS_NULL.getCode(), RtnCode.PASSWORD_IS_NULL.getMessage());
		}

		 Optional<User> op = userDao.findByEmail(email);
		 //Account not found
		if(op.isEmpty()) {
			return new AuthenticationRes(RtnCode.ACCOUNT_NOT_FOUND.getCode(), RtnCode.ACCOUNT_NOT_FOUND.getMessage());
		}
		
		
		
		return new AuthenticationRes(RtnCode.SUCCESSFUL.getCode(),RtnCode.SUCCESSFUL.getMessage());
	}
	
//	public UserInfo userToUserInfo(User user) {
//		 ObjectMapper objectMapper = new ObjectMapper();
//		 
//		 String json = objectMapper.writeValueAsString(user);
//		
//		return null;
//	}

}
