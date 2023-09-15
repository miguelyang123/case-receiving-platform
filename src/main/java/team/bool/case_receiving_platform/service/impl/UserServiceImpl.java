package team.bool.case_receiving_platform.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import team.bool.case_receiving_platform.constants.AuthRtnCode;
import team.bool.case_receiving_platform.entity.User;
import team.bool.case_receiving_platform.entity.UserInfo;
import team.bool.case_receiving_platform.repository.UserDao;
import team.bool.case_receiving_platform.service.ifs.UserService;
import team.bool.case_receiving_platform.vo.AuthRes;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	private String emailPattern = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

	// check Email And Pwd at DB
	private AuthRes checkUserEmailAndPwdAtDB(String email, String pwd) {

		// input is null & check input format
		if (!StringUtils.hasText(email) || !email.matches(emailPattern)) {
			return new AuthRes(AuthRtnCode.EMAIL_FORMAT_ERROR.getCode(), AuthRtnCode.EMAIL_FORMAT_ERROR.getMessage());
		}
		if (!StringUtils.hasText(pwd) || pwd.length() > 30 || pwd.length() <= 8) {
			return new AuthRes(AuthRtnCode.WORONG_PASSWORD.getCode(),
					AuthRtnCode.WORONG_PASSWORD.getMessage());
		}

		Optional<User> op = userDao.findByEmail(email);

		// Account not found
		if (op.isEmpty()) {
			return new AuthRes(AuthRtnCode.ACCOUNT_NOT_FOUND.getCode(), AuthRtnCode.ACCOUNT_NOT_FOUND.getMessage());
		}
		// check pwd
		if (!op.get().getPwd().equals(pwd)) {
			return new AuthRes(AuthRtnCode.WORONG_PASSWORD.getCode(), AuthRtnCode.WORONG_PASSWORD.getMessage());
		}

		// change User to UserInfo
		try {
			// Successful get UserInfo
			UserInfo userInfo = userToUserInfo(op.get());
			return new AuthRes(AuthRtnCode.SUCCESSFUL.getCode(), AuthRtnCode.SUCCESSFUL.getMessage(), userInfo);

		} catch (Exception e) {
			e.printStackTrace();
			return new AuthRes(AuthRtnCode.DATA_ERROR.getCode(), e.getMessage());

		}

	}

	// ch
//	private AuthRes checkUserInput(User user) {
//
//		String email = user.getEmail();
//		String pwd = user.getPwd();
//		String userName = user.getUserName();
//		String phone = user.getPhone();
//		
//		
//		// input is null & check input format
//		if (!StringUtils.hasText(email) || !email.matches(emailPattern)) {
//			return new AuthRes(AuthRtnCode.EMAIL_FORMAT_ERROR.getCode(), AuthRtnCode.EMAIL_FORMAT_ERROR.getMessage());
//		}
//		if (!StringUtils.hasText(pwd) || pwd.length() > 30 || pwd.length() <= 8) {
//			return new AuthRes(AuthRtnCode.PASSWORD_FORMAT_ERROR.getCode(),
//					AuthRtnCode.PASSWORD_FORMAT_ERROR.getMessage());
//		}
//		
//		
//
//
//		return new AuthRes(AuthRtnCode.SUCCESSFUL.getCode(), AuthRtnCode.SUCCESSFUL.getMessage(),null);
//
//		
//
//	}
	
	private UserInfo userToUserInfo(User user) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		// user copy to UserInfo use JSON
		String json = objectMapper.writeValueAsString(user);
		return objectMapper.readValue(json, UserInfo.class);
	}

	
	
	@Override
	public AuthRes login(String email, String pwd) {

		return checkUserEmailAndPwdAtDB(email, pwd);
	}

	@Override
	public AuthRes getBalance(String email, String pwd) {

		return checkUserEmailAndPwdAtDB(email, pwd);
	}

	@Override
	public AuthRes addNewUser(User user) {
		
		return null;
	}
	
	

}
