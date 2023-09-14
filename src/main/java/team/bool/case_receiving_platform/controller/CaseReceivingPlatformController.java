package team.bool.case_receiving_platform.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team.bool.case_receiving_platform.constants.AuthRtnCode;
import team.bool.case_receiving_platform.service.ifs.AuthenticationService;
import team.bool.case_receiving_platform.vo.AuthenticationRes;
import team.bool.case_receiving_platform.vo.LoginReq;

@RestController
@RequestMapping("/api")
public class CaseReceivingPlatformController {

	@Autowired
	private AuthenticationService aService;

	@PostMapping("/login")
	public AuthenticationRes login(@RequestBody LoginReq req, HttpSession http) {
		
		// get DB User Info
		AuthenticationRes res = aService.login(req.getEmail(), req.getPwd());
		
		// Is already login return SUCCESSFUL
		String email = (String) http.getAttribute("email");
		if (StringUtils.hasText(email)) {
			return aService.login(req.getEmail(), req.getPwd());
		}


		
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
	@GetMapping("/get_balance")
	public AuthenticationRes getBalance(HttpSession http) {
		//get HttpSession user data
		String email = (String) http.getAttribute("email");
		String pwd = (String) http.getAttribute("password");
		// not find Session id
		if (!StringUtils.hasText(email)) {
			return new AuthenticationRes(AuthRtnCode.PLEASE_LOGIN_FIRST.getCode(), AuthRtnCode.PLEASE_LOGIN_FIRST.getMessage());
		}

		return aService.getBalance(email, pwd);
	}

}
