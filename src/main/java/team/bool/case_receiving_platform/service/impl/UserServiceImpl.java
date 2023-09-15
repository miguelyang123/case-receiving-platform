package team.bool.case_receiving_platform.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	private String pwdPattern = "^.{7,30}$";
	private String namePattern = "^.{0,30}$";
	private String phonePattern = "^(?:0|\\d{3}-?)(9|2)\\d{2}-?\\d{6}";

	// check Email And Pwd at DB
	private AuthRes getUserInfoWithEmailAndPwd(String email, String pwd) {

		// check email & pwd
		AuthRes checkRes = checkEmailAndPwd(email, pwd);
		if (!checkRes.getCode().equals(AuthRtnCode.SUCCESSFUL.getCode())) {
			return checkRes;
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

		// change User to UserInfo and return
		try {
			// Successful get UserInfo
			UserInfo userInfo = userToUserInfo(op.get());
			return new AuthRes(AuthRtnCode.SUCCESSFUL.getCode(), AuthRtnCode.SUCCESSFUL.getMessage(), userInfo);

		} catch (Exception e) {
			e.printStackTrace();
			return new AuthRes(AuthRtnCode.DATA_ERROR.getCode(), e.getMessage());

		}

	}

	// check email & pwd
	private AuthRes checkEmailAndPwd(String email, String pwd) {

		// input is null & check input format
		if (!StringUtils.hasText(email) || !email.matches(emailPattern)) {
			return new AuthRes(AuthRtnCode.EMAIL_FORMAT_ERROR.getCode(), AuthRtnCode.EMAIL_FORMAT_ERROR.getMessage());
		}
		if (!StringUtils.hasText(pwd) || !pwd.matches(pwdPattern)) {
			return new AuthRes(AuthRtnCode.PASSWORD_FORMAT_ERROR.getCode(),
					AuthRtnCode.PASSWORD_FORMAT_ERROR.getMessage());
		}

		return new AuthRes(AuthRtnCode.SUCCESSFUL.getCode(), AuthRtnCode.SUCCESSFUL.getMessage(), null);
	}

	// check userName & phone
	private AuthRes checkUserNameAndPhone(User user) {

		// check email & pwd
		AuthRes checkRes = checkEmailAndPwd(user.getEmail(), user.getPwd());
		if (!checkRes.getCode().equals(AuthRtnCode.SUCCESSFUL.getCode())) {
			return checkRes;
		}

		String userName = user.getUserName();
		String phone = user.getPhone();
		// input is null & check input format
		if (!StringUtils.hasText(userName) || !userName.matches(namePattern)) {
			return new AuthRes(AuthRtnCode.NAME_FORMAT_ERROR.getCode(), AuthRtnCode.NAME_FORMAT_ERROR.getMessage());
		}
		if (!StringUtils.hasText(phone) || !phone.matches(phonePattern)) {
			return new AuthRes(AuthRtnCode.NAME_FORMAT_ERROR.getCode(), AuthRtnCode.NAME_FORMAT_ERROR.getMessage());
		}
		
		//has same email
		if(userDao.existsByEmail(user.getEmail())) {
			return new AuthRes(AuthRtnCode.EMAIL_EXISTED.getCode(), AuthRtnCode.EMAIL_EXISTED.getMessage());
		}

		return new AuthRes(AuthRtnCode.SUCCESSFUL.getCode(), AuthRtnCode.SUCCESSFUL.getMessage(), null);
	}

	// change User to UserInfo
	private UserInfo userToUserInfo(User user) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		// user copy to UserInfo use JSON
		String json = objectMapper.writeValueAsString(user);
		return objectMapper.readValue(json, UserInfo.class);
	}

	// encoder pwd
	private String encoderPwd(String pwd) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return pwd;
	}
	
	@Override
	public AuthRes login(String email, String pwd) {

		return getUserInfoWithEmailAndPwd(email, pwd);
	}

	@Override
	public AuthRes getBalance(String email, String pwd) {

		return getUserInfoWithEmailAndPwd(email, pwd);
	}

	@Override
	public AuthRes addNewUser(User user) {

		// check email & pwd
		AuthRes checkRes = checkUserNameAndPhone(user);
		if (!checkRes.getCode().equals(AuthRtnCode.SUCCESSFUL.getCode())) {
			return checkRes;
		}

		// set UUID
		user.setUuid(UUID.randomUUID());

		// saver to DB
		userDao.save(user);

		// change User to UserInfo and return
		try {
			// Successful get UserInfo
			UserInfo userInfo = userToUserInfo(user);
			return new AuthRes(AuthRtnCode.SUCCESSFUL_ADD_USER.getCode(), AuthRtnCode.SUCCESSFUL_ADD_USER.getMessage(),
					userInfo);

		} catch (Exception e) {
			e.printStackTrace();
			return new AuthRes(AuthRtnCode.DATA_ERROR.getCode(), e.getMessage());

		}

	}

}
