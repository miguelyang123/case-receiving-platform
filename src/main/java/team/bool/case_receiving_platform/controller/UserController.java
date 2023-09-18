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

import team.bool.case_receiving_platform.constants.AuthRtnCode;
import team.bool.case_receiving_platform.entity.User;
import team.bool.case_receiving_platform.service.ifs.UserService;
import team.bool.case_receiving_platform.vo.AllUserRes;
import team.bool.case_receiving_platform.vo.AuthRes;
import team.bool.case_receiving_platform.vo.ChangePwdReq;
import team.bool.case_receiving_platform.vo.LoginReq;

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
		if (!StringUtils.hasText(email)) {
			return new AuthRes(AuthRtnCode.PLEASE_LOGIN_FIRST.getCode(), AuthRtnCode.PLEASE_LOGIN_FIRST.getMessage());
		}

		return userService.getBalance(email, pwd);
	}
	
	@PostMapping("logout")
	public AuthRes logout(HttpSession httpSession) {
		// Invalid Session
		httpSession.invalidate();
		return new AuthRes(AuthRtnCode.SUCCESSFUL_LOGOUT.getCode(),
				AuthRtnCode.SUCCESSFUL_LOGOUT.getMessage());
	}
	
//	@Transactional
	@PostMapping("signup")
	public AuthRes signup(@RequestBody User user) {
		return userService.addNewUser(user);
	}
	
	@GetMapping("get_all_user") 
	public AllUserRes getAllUser(){
		return userService.findAllUser();
	}
	
	@PostMapping("edit_user")
	public AuthRes editUser(@RequestBody User user, HttpSession http) {
		//get HttpSession user data
		String email = (String) http.getAttribute("email");
		String pwd = (String) http.getAttribute("password");
		// not login
		if (!StringUtils.hasText(email)) {
			return new AuthRes(AuthRtnCode.PLEASE_LOGIN_FIRST.getCode(), AuthRtnCode.PLEASE_LOGIN_FIRST.getMessage());
		}
		
		user.setPwd(pwd);
		
		//if change email
		if(!email.equals(user.getEmail())) {
			http.setAttribute("email", user.getEmail());
		}
		
		
		return userService.editUser(user);
	}
	
	@PostMapping("change_pwd")
	public AuthRes changePwd(@RequestBody ChangePwdReq req, HttpSession http) {
		//get HttpSession user data
		String email = (String) http.getAttribute("email");
		// not find Session email
		if (!StringUtils.hasText(email)) {
			return new AuthRes(AuthRtnCode.PLEASE_LOGIN_FIRST.getCode(), AuthRtnCode.PLEASE_LOGIN_FIRST.getMessage());
		}
		
		//get HttpSession user data
		return userService.changePwd(email, req.getOldPwd(), req.getNewPwd());
	}

}
