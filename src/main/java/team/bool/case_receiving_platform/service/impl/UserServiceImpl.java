package team.bool.case_receiving_platform.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import team.bool.case_receiving_platform.constants.AuthRtnCode;
import team.bool.case_receiving_platform.constants.RtnCode;
import team.bool.case_receiving_platform.entity.User;
import team.bool.case_receiving_platform.entity.UserInfo;
import team.bool.case_receiving_platform.repository.UserDao;
import team.bool.case_receiving_platform.service.ifs.UserService;
import team.bool.case_receiving_platform.vo.AllUserRes;
import team.bool.case_receiving_platform.vo.AuthRes;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	private String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	private String pwdPattern = "^.{8,30}$";
	private String namePattern = "^.{2,30}$";
	private String phonePattern = "^(?:0|\\d{3}-?)(9|2)\\d{2}-?\\d{6}$";

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
		if (!matchesPwdAndHashPass(pwd, op.get().getPwd())) {
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

	// check email & pwd & userName & phone
	private AuthRes checkUserInput(User user) {

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

		// has same email
		if (userDao.existsByEmail(user.getEmail())) {
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
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(pwd);
	}

	private boolean matchesPwdAndHashPass(String pwd, String hashPass) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(pwd, hashPass);
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

		// check Input
		AuthRes checkRes = checkUserInput(user);
		if (!checkRes.getCode().equals(AuthRtnCode.SUCCESSFUL.getCode())) {
			return checkRes;
		}

		// set UUID
		user.setUuid(UUID.randomUUID());

		// encoder pwd
		user.setPwd(encoderPwd(user.getPwd()));

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

	@Override
	public AuthRes findUserbyUuid(UUID uuid) {

		if (uuid == null) {
			return new AuthRes(AuthRtnCode.DATA_ERROR.getCode(), AuthRtnCode.DATA_ERROR.getMessage());
		}

		Optional<User> user = userDao.findById(uuid);

		if (user.isEmpty()) {
			return new AuthRes(AuthRtnCode.ACCOUNT_NOT_FOUND.getCode(), AuthRtnCode.ACCOUNT_NOT_FOUND.getMessage());
		}

		// change User to UserInfo and return
		try {
			// Successful get UserInfo
			UserInfo userInfo = userToUserInfo(user.get());
			return new AuthRes(AuthRtnCode.SUCCESSFUL_ADD_USER.getCode(), AuthRtnCode.SUCCESSFUL_ADD_USER.getMessage(),
					userInfo);

		} catch (Exception e) {
			e.printStackTrace();
			return new AuthRes(AuthRtnCode.DATA_ERROR.getCode(), e.getMessage());

		}
	}

	public AllUserRes findAllUser() {

		List<User> userList = userDao.findAll();

		// userList <= 0 error
		if (userList.size() <= 0) {
			return new AllUserRes(RtnCode.DATA_ERROR.getCode(), RtnCode.DATA_ERROR.getMessage());

		}

		List<UserInfo> userInfoList = new ArrayList<>();

		try {
			for (User user : userList) {
				userInfoList.add(userToUserInfo(user));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new AllUserRes(RtnCode.DATA_ERROR.getCode(), e.getMessage(), userInfoList);

		}

		return new AllUserRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage(),
				userInfoList);
	}

	@Override
	public AuthRes editUser(User user) {

		// check email & pwd & userName & phone
		AuthRes checkRes = checkUserInput(user);
		if (!checkRes.getCode().equals(AuthRtnCode.SUCCESSFUL.getCode())) {
			return checkRes;
		}
		
		// account not found
		if(userDao.existsById(user.getUuid())) {
			return new AuthRes(RtnCode.ACCOUNT_NOT_FOUND.getCode(), RtnCode.ACCOUNT_NOT_FOUND.getMessage());
		}

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
