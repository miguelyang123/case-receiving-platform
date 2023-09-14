//package team.bool.case_receiving_platform.service.impl;
//
//import java.util.Date;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import com.example.demo.constants.RtnCode;
//import com.example.demo.vo.AddPersonalInfoResponse;
//
//import team.bool.case_receiving_platform.entity.User;
//import team.bool.case_receiving_platform.entity.UserInfo;
//import team.bool.case_receiving_platform.repository.UserDao;
//import team.bool.case_receiving_platform.service.ifs.AuthenticationService;
//import team.bool.case_receiving_platform.vo.AuthenticationRes;
//
//@Service
//public class AuthenticationServiceImpl implements AuthenticationService{
//
//	@Autowired
//	private UserDao userDao;
//	
//	@Override
//	public AuthenticationRes login(String email, String pwd) {
//		
//		
//		
//		
//
//		return new AuthenticationRes();
//	}
//	
//	//check Email And Pwd at DB
//	private AuthenticationRes checkUserEmailAndPwd(String email, String pwd) {
//		if (!StringUtils.hasText(email)) {
//			return new AuthenticationRes();
//		}
//		
//		return new AuthenticationRes();
//	}
//
//}
