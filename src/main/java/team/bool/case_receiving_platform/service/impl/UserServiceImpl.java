package team.bool.case_receiving_platform.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import team.bool.case_receiving_platform.constants.AuthRtnCode;
import team.bool.case_receiving_platform.constants.CaseRtnCode;
import team.bool.case_receiving_platform.constants.RtnCode;
import team.bool.case_receiving_platform.entity.User;
import team.bool.case_receiving_platform.entity.UserInfo;
import team.bool.case_receiving_platform.repository.CaseDao;
import team.bool.case_receiving_platform.repository.UserDao;
import team.bool.case_receiving_platform.service.ifs.CaseContractorService;
import team.bool.case_receiving_platform.service.ifs.UserService;
import team.bool.case_receiving_platform.vo.AuthRes;
import team.bool.case_receiving_platform.vo.ContractorListRes;
import team.bool.case_receiving_platform.vo.MsgRes;
import team.bool.case_receiving_platform.vo.UserListRes;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	public CaseDao caseDao;

	@Autowired
	private CaseContractorService ccService;

	@Autowired
	private JavaMailSender mailSender;

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
			return new AuthRes(AuthRtnCode.SUCCESSFUL_LOGIN.getCode(), AuthRtnCode.SUCCESSFUL_LOGIN.getMessage(),
					userInfo);

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

	//copy bean
	private String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
	
	public void copyPropertiesIgnoreNull(Object src, Object target){ 
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src)); 
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
		// has same email
		if (userDao.existsByEmail(user.getEmail())) {
			return new AuthRes(AuthRtnCode.EMAIL_EXISTED.getCode(), AuthRtnCode.EMAIL_EXISTED.getMessage());
		}

		// set UUID
		UUID newUserId = UUID.randomUUID();
		user.setUuid(newUserId);
		// set pdf Path
		String pdfPath = "pdfs/" + newUserId + ".pdf";
		user.setResumePdfPath(pdfPath);
		// encoder pwd
		user.setPwd(encoderPwd(user.getPwd()));

		// save to DB
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

	@Override
	public AuthRes editUser(User user) {

		// check email & pwd & userName & phone
		user.setPwd("fakeinput");
		AuthRes checkRes = checkUserInput(user);
		if (!checkRes.getCode().equals(AuthRtnCode.SUCCESSFUL.getCode())) {
			return checkRes;
		}

		Optional<User> op = userDao.findById(user.getUuid());

		User dbUser = op.get();

		// account not found
		if (op.isEmpty()) {
			return new AuthRes(AuthRtnCode.ACCOUNT_NOT_FOUND.getCode(), AuthRtnCode.ACCOUNT_NOT_FOUND.getMessage());
		}
		// if change email check has same email
		if (!op.get().getEmail().equals(user.getEmail())) {
			if (userDao.existsByEmail(user.getEmail())) {
				return new AuthRes(AuthRtnCode.EMAIL_EXISTED.getCode(), AuthRtnCode.EMAIL_EXISTED.getMessage());
			}
		}

		// copy user to dbUser without null input
		copyPropertiesIgnoreNull(user, dbUser);

		// get DB Pwd with encoder
//		user.setPwd(op.get().getPwd());

		// save to DB
		userDao.save(dbUser);

		// change User to UserInfo and return
		try {
			// Successful get UserInfo
			UserInfo userInfo = userToUserInfo(dbUser);
			return new AuthRes(AuthRtnCode.SUCCESSFUL_CHANGE.getCode(), AuthRtnCode.SUCCESSFUL_CHANGE.getMessage(),
					userInfo);

		} catch (Exception e) {
			e.printStackTrace();
			return new AuthRes(AuthRtnCode.DATA_ERROR.getCode(), e.getMessage());

		}
	}

	@Override
	public AuthRes changePwd(String email, String oldPwd, String newPwd) {

		Optional<User> op = userDao.findByEmail(email);
		// Account not found
		if (op.isEmpty()) {
			return new AuthRes(AuthRtnCode.ACCOUNT_NOT_FOUND.getCode(), AuthRtnCode.ACCOUNT_NOT_FOUND.getMessage());
		}
		User user = op.get();
		// check oldPwd
		if (!matchesPwdAndHashPass(oldPwd, user.getPwd())) {
			return new AuthRes(AuthRtnCode.WORONG_PASSWORD.getCode(), AuthRtnCode.WORONG_PASSWORD.getMessage());
		}
		// check oldPwd format
		if (!StringUtils.hasText(newPwd) || !newPwd.matches(pwdPattern)) {
			return new AuthRes(AuthRtnCode.PASSWORD_FORMAT_ERROR.getCode(),
					AuthRtnCode.PASSWORD_FORMAT_ERROR.getMessage());
		}

		// encoder pwd
		user.setPwd(encoderPwd(newPwd));

		userDao.save(user);

		// change User to UserInfo and return
		try {
			// Successful get UserInfo
			UserInfo userInfo = userToUserInfo(user);
			return new AuthRes(AuthRtnCode.SUCCESSFUL_CHANGE.getCode(), AuthRtnCode.SUCCESSFUL_CHANGE.getMessage(),
					userInfo);

		} catch (Exception e) {
			e.printStackTrace();
			return new AuthRes(AuthRtnCode.DATA_ERROR.getCode(), e.getMessage());

		}
	}

	@Override
	public MsgRes sendTokenToUserMail(String userEmail, String token) throws Exception {

		// input is null & check input format
		if (!StringUtils.hasText(userEmail) || !userEmail.matches(emailPattern)) {
			return new MsgRes(AuthRtnCode.EMAIL_FORMAT_ERROR.getCode(), AuthRtnCode.EMAIL_FORMAT_ERROR.getMessage());
		}

		// user not found
		if (!userDao.existsByEmail(userEmail)) {
			return new MsgRes(AuthRtnCode.ACCOUNT_NOT_FOUND.getCode(), AuthRtnCode.ACCOUNT_NOT_FOUND.getMessage());
		}

//			SimpleMailMessage message = new SimpleMailMessage();
//			message.setFrom("javae7278@gmail.com");
//			message.setTo(userEmail);
//			message.setSubject("Speed接案網:重製密碼");
//			
//			String content = "<p>你好, </p>"
//			            + "<p>您已要求重新設定密碼</p>"
//			            + "<p>驗證碼:</p>"
//			            + "<p>" + token + "</p>"
//			            + "<br>"
//			            + "<p>驗證碼有效時間為10分鐘</p>"
//			            + "<p>感謝您使用Speed接案網</p>";
//			
//			message.setText(content);
//			
//			// send to user email
//			mailSender.send(message);

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("javae7278@gmail.com", "Speed接案網");
		helper.setTo(userEmail);

		String subject = "已要求重新設定密碼";

		String content = "<p>你好, </p>" + "<p>您已要求重新設定密碼</p>" + "<p>驗證碼:</p>" + "<p>" + token + "</p>" + "<br>"
				+ "<p>驗證碼有效時間為10分鐘</p>" + "<p>感謝您使用Speed接案網</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		// send to user email
		mailSender.send(message);

		return new MsgRes(AuthRtnCode.SUCCESSFUL_SEND_EMAIL.getCode(), AuthRtnCode.SUCCESSFUL_SEND_EMAIL.getMessage());
	}

	@Override
	public MsgRes setNewPwd(String email, String newPwd) {

		Optional<User> op = userDao.findByEmail(email);

		// user not found
		if (op.isEmpty()) {
			return new MsgRes(AuthRtnCode.ACCOUNT_NOT_FOUND.getCode(), AuthRtnCode.ACCOUNT_NOT_FOUND.getMessage());
		}
		// check newPwd
		if (!StringUtils.hasText(newPwd) || !newPwd.matches(pwdPattern)) {
			return new MsgRes(AuthRtnCode.PASSWORD_FORMAT_ERROR.getCode(),
					AuthRtnCode.PASSWORD_FORMAT_ERROR.getMessage());
		}

		User user = op.get();

		// set newPwd with encoder
		user.setPwd(encoderPwd(newPwd));

		userDao.save(user);

		return new MsgRes(AuthRtnCode.SUCCESSFUL_CHANGE.getCode(), AuthRtnCode.SUCCESSFUL_CHANGE.getMessage());

	}

	// CaseId is null update all User Rating
	@Override
	public UserListRes updateUserRatingWithCaseId(Integer caseId) {

		// check Case ID in DB
		if (caseId != null && !caseDao.existsById(caseId)) {
			return new UserListRes(CaseRtnCode.CASE_NOT_FOUND.getCode(), CaseRtnCode.CASE_NOT_FOUND.getMessage());
		}

		int resInt = userDao.updateUserRating(caseId);

		if (resInt <= 0) {
			return new UserListRes(RtnCode.UPDATE_FAILED.getCode(), RtnCode.UPDATE_FAILED.getMessage());
		}

		ContractorListRes cRes = ccService.searchUserByCaseIdAndAccepted(caseId, null);

		// change vo List to UserInfo List
		List<UserInfo> userInfoList = new ArrayList<>();
		cRes.getUserInfoList().forEach(info -> {
			userInfoList.add(info);
		});

		return new UserListRes(AuthRtnCode.SUCCESSFUL_CHANGE.getCode(), AuthRtnCode.SUCCESSFUL_CHANGE.getMessage(),
				userInfoList);
	}

}
