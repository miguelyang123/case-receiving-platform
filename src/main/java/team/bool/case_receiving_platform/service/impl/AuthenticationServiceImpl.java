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
import team.bool.case_receiving_platform.service.ifs.AuthenticationService;
import team.bool.case_receiving_platform.vo.AuthenticationRes;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserDao userDao;
	
	private String emailPattern = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

	@Override
	public AuthenticationRes login(String email, String pwd) {

		return checkUserEmailAndPwd(email, pwd);
	}

	@Override
	public AuthenticationRes getBalance(String email, String pwd) {

		return checkUserEmailAndPwd(email, pwd);
	}

	// check Email And Pwd at DB
	private AuthenticationRes checkUserEmailAndPwd(String email, String pwd) {
		
		// input is null & check input format
		if (!StringUtils.hasText(email) || !email.matches(emailPattern)) {
			return new AuthenticationRes(AuthRtnCode.EMAIL_FORMAT_ERROR.getCode(),
					AuthRtnCode.EMAIL_FORMAT_ERROR.getMessage());
		}
		if (!StringUtils.hasText(pwd) || pwd.length() > 30 || pwd.length() <= 8) {
			return new AuthenticationRes(AuthRtnCode.PASSWORD_FORMAT_ERROR.getCode(),
					AuthRtnCode.PASSWORD_FORMAT_ERROR.getMessage());
		}

		Optional<User> op = userDao.findByEmail(email);

		// Account not found
		if (op.isEmpty()) {
			return new AuthenticationRes(AuthRtnCode.ACCOUNT_NOT_FOUND.getCode(),
					AuthRtnCode.ACCOUNT_NOT_FOUND.getMessage());
		}
		// check pwd
		if (!op.get().getPwd().equals(pwd)) {
			return new AuthenticationRes(AuthRtnCode.WORONG_PASSWORD.getCode(),
					AuthRtnCode.WORONG_PASSWORD.getMessage());
		}

		// change User to UserInfo
		try {
			// Successful get UserInfo
			UserInfo userInfo = userToUserInfo(op.get());
			return new AuthenticationRes(AuthRtnCode.SUCCESSFUL.getCode(), AuthRtnCode.SUCCESSFUL.getMessage(),
					userInfo);

		} catch (Exception e) {
			e.printStackTrace();
			return new AuthenticationRes(AuthRtnCode.DATA_ERROR.getCode(), e.getMessage());

		}

	}

	private UserInfo userToUserInfo(User user) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		// user copy to UserInfo use JSON
		String json = objectMapper.writeValueAsString(user);
		return objectMapper.readValue(json, UserInfo.class);
	}

}
