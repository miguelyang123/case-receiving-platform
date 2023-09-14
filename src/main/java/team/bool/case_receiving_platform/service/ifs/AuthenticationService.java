package team.bool.case_receiving_platform.service.ifs;

import team.bool.case_receiving_platform.vo.AuthenticationRes;

public interface AuthenticationService {
	
	public AuthenticationRes login(String email, String password);
	
}
