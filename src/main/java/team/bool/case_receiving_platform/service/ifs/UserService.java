package team.bool.case_receiving_platform.service.ifs;

import team.bool.case_receiving_platform.entity.User;
import team.bool.case_receiving_platform.vo.AuthRes;

public interface UserService {
	
	public AuthRes login(String email, String pwd);
	
	public AuthRes getBalance(String email, String pwd);
	
	public AuthRes addNewUser(User user);
	
}
