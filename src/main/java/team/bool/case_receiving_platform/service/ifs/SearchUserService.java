package team.bool.case_receiving_platform.service.ifs;

import team.bool.case_receiving_platform.vo.UserListRes;

public interface SearchUserService {

	public UserListRes findAllUser();
	
	public UserListRes searchUserToList(String userName, Integer minRating, Boolean isAdministrator, Boolean lockedStatus);
	

}
