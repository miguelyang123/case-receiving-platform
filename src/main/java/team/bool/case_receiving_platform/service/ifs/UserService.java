package team.bool.case_receiving_platform.service.ifs;

import java.util.UUID;

import team.bool.case_receiving_platform.entity.User;
import team.bool.case_receiving_platform.vo.AllUserRes;
import team.bool.case_receiving_platform.vo.AuthRes;

public interface UserService {

	public AuthRes login(String email, String pwd);

	public AuthRes getBalance(String email, String pwd);

	public AuthRes addNewUser(User user);

	public AuthRes findUserbyUuid(UUID uuid);

	public AllUserRes findAllUser();

	public AuthRes editUser(User user);

	public AuthRes changePwd(String email, String oldPwd, String newPwd);

}
