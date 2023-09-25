package team.bool.case_receiving_platform.service.ifs;

import java.util.List;
import java.util.UUID;

import team.bool.case_receiving_platform.entity.User;
import team.bool.case_receiving_platform.entity.UserInfo;
import team.bool.case_receiving_platform.vo.AuthRes;
import team.bool.case_receiving_platform.vo.MsgRes;
import team.bool.case_receiving_platform.vo.UserListRes;

public interface UserService {

	public AuthRes login(String email, String pwd);

	public AuthRes getBalance(String email, String pwd);

	public AuthRes addNewUser(User user);

	public AuthRes findUserbyUuid(UUID uuid);

	public AuthRes editUser(User user);

	public AuthRes changePwd(String email, String oldPwd, String newPwd);
	
	public MsgRes sendTokenToUserMail(String email, String token) throws Exception;

	public MsgRes setNewPwd(String email, String newPwd);
	
	public UserListRes updateUserRatingWithCaseId(Integer CaseId);
	
}
