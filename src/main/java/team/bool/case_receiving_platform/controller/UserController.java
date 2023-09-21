package team.bool.case_receiving_platform.controller;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.bytebuddy.utility.RandomString;
import team.bool.case_receiving_platform.constants.AuthRtnCode;
import team.bool.case_receiving_platform.constants.RtnCode;
import team.bool.case_receiving_platform.entity.User;
import team.bool.case_receiving_platform.service.ifs.UserService;
import team.bool.case_receiving_platform.vo.UserListRes;
import team.bool.case_receiving_platform.vo.AuthRes;
import team.bool.case_receiving_platform.vo.ChangePwdReq;
import team.bool.case_receiving_platform.vo.ForgotPwdReq;
import team.bool.case_receiving_platform.vo.LoginReq;
import team.bool.case_receiving_platform.vo.MsgRes;

@RestController
@RequestMapping("api")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("login")
	public AuthRes login(@RequestBody LoginReq req, HttpSession http) {
		
		// get DB User Info
		AuthRes res = userService.login(req.getEmail(), req.getPwd());
		
		// Is already login return SUCCESSFUL
		String email = (String) http.getAttribute("email");
		if (StringUtils.hasText(email)) {
			return userService.login(req.getEmail(), req.getPwd());
		}

		// account error
		if(!res.getCode().equals(AuthRtnCode.SUCCESSFUL.getCode())) {
			return res;
		}

		// save to HttpSession
		http.setAttribute("email", req.getEmail());
		http.setAttribute("password", req.getPwd());

		// set time 1 hour
		http.setMaxInactiveInterval(60 * 60);

		return res;

	}

	// for check is login
	@GetMapping("get_balance")
	public AuthRes getBalance(HttpSession http) {
		//get HttpSession user data
		String email = (String) http.getAttribute("email");
		String pwd = (String) http.getAttribute("password");
		// not find Session email
		if (!StringUtils.hasText(email) || !StringUtils.hasText(pwd)) {
			return new AuthRes(AuthRtnCode.PLEASE_LOGIN_FIRST.getCode(), AuthRtnCode.PLEASE_LOGIN_FIRST.getMessage());
		}

		return userService.getBalance(email, pwd);
	}
	
	@GetMapping("logout")
	public AuthRes logout(HttpSession http) {
		// Invalid Session
		http.invalidate();
		
		return new AuthRes(AuthRtnCode.SUCCESSFUL_LOGOUT.getCode(),
				AuthRtnCode.SUCCESSFUL_LOGOUT.getMessage());
	}
	
//	@Transactional
	@PostMapping("signup")
	public AuthRes signup(@RequestBody User user) {
		return userService.addNewUser(user);
	}
	
	@PostMapping("edit_user")
	public AuthRes editUser(@RequestBody User user, HttpSession http) {
		//get HttpSession user data
		String email = (String) http.getAttribute("email");
		String pwd = (String) http.getAttribute("password");
		// not login
		if (!StringUtils.hasText(email) || !StringUtils.hasText(pwd)) {
			return new AuthRes(AuthRtnCode.PLEASE_LOGIN_FIRST.getCode(), AuthRtnCode.PLEASE_LOGIN_FIRST.getMessage());
		}
		
		user.setPwd(pwd);
		
		//if change email
		if(!email.equals(user.getEmail())) {
			http.setAttribute("email", user.getEmail());
		}
		
		// set time 1 hour
		http.setMaxInactiveInterval(60 * 60);
		
		return userService.editUser(user);
	}
	
	@PostMapping("change_pwd")
	public AuthRes changePwd(@RequestBody ChangePwdReq req, HttpSession http) {
		//get HttpSession user data
		String email = (String) http.getAttribute("email");
		String pwd = (String) http.getAttribute("password");
		// not find Session email
		if (!StringUtils.hasText(email) || !StringUtils.hasText(pwd)) {
			return new AuthRes(AuthRtnCode.PLEASE_LOGIN_FIRST.getCode(), AuthRtnCode.PLEASE_LOGIN_FIRST.getMessage());
		}
		
		//get HttpSession user data
		return userService.changePwd(email, req.getOldPwd(), req.getNewPwd());
	}
	
	@PostMapping("forgot_pwd")
	public MsgRes forgotPwd(@RequestBody ForgotPwdReq req , HttpSession http) {
		try {
		String token = RandomString.make(20);
		
		// save to HttpSession
		http.setAttribute("resetPwdToken", token);
		http.setAttribute("email", req.getEmail());
		
		// set time 10 minute
		http.setMaxInactiveInterval(10 * 60);
		
		//send token to user email
		MsgRes res = userService.sendTokenToUserMail(req.getEmail(), token);
		
		return res;
		} catch (Exception e) {
			return new MsgRes(RtnCode.DATA_ERROR.getCode(),e.getMessage());
		}
		

	}
	
	@PostMapping("check_reset_pwd_token")
	public MsgRes checkResetPwdToken(@RequestBody ForgotPwdReq req , HttpSession http) {
		//get HttpSession Token
		String token = (String) http.getAttribute("resetPwdToken");
		
		// Session Token = null
		if(!StringUtils.hasText(token)) {
			return new MsgRes(AuthRtnCode.TOKEN_NOT_FOUND.getCode(),AuthRtnCode.TOKEN_NOT_FOUND.getMessage());
		}
		
		// User token != Session token
		if(!token.equals(req.getResetPwdToken())) {
			return new MsgRes(AuthRtnCode.WORONG_TOKEN.getCode(),AuthRtnCode.WORONG_TOKEN.getMessage());
		}
		
	
		return new MsgRes(AuthRtnCode.SUCCESSFUL_VERIFY.getCode(),AuthRtnCode.SUCCESSFUL_VERIFY.getMessage());
	}
	
	@PostMapping("set_new_pwd")
	public MsgRes setNewPwd(@RequestBody ForgotPwdReq req , HttpSession http) {
		//get HttpSession Token
		String email = (String) http.getAttribute("email");
		
		// Session email = null
		if(!StringUtils.hasText(email)) {
			return new MsgRes(AuthRtnCode.TOKEN_NOT_FOUND.getCode(),AuthRtnCode.TOKEN_NOT_FOUND.getMessage());
		}
		
		MsgRes result = userService.setNewPwd(email, req.getNewPwd());
		
		//code = 200 to delete HttpSession
		if(result.getCode().equals(AuthRtnCode.SUCCESSFUL_CHANGE.getCode())) {
			// Invalid Session
			http.invalidate();
		} 
		
		return result;
	}
}
