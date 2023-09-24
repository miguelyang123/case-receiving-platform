package team.bool.case_receiving_platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import team.bool.case_receiving_platform.constants.RtnCode;
import team.bool.case_receiving_platform.entity.User;
import team.bool.case_receiving_platform.entity.UserInfo;
import team.bool.case_receiving_platform.repository.UserDao;
import team.bool.case_receiving_platform.service.ifs.SearchUserService;
import team.bool.case_receiving_platform.vo.UserListRes;

@Service
public class SearchUserServiceImpl implements SearchUserService {

	@Autowired
	private UserDao userDao;

	// change User to UserInfo
	private UserInfo userToUserInfo(User user) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		// user copy to UserInfo use JSON
		String json = objectMapper.writeValueAsString(user);
		return objectMapper.readValue(json, UserInfo.class);
	}

	public UserListRes findAllUser() {

		List<User> userList = userDao.findAll();

		// userList <= 0 error
		if (userList.size() <= 0) {
			return new UserListRes(RtnCode.ACCOUNT_NOT_FOUND.getCode(), RtnCode.ACCOUNT_NOT_FOUND.getMessage());
		}

		// user change to userInfo
		List<UserInfo> userInfoList = new ArrayList<>();
		try {
			for (User user : userList) {
				userInfoList.add(userToUserInfo(user));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new UserListRes(RtnCode.DATA_ERROR.getCode(), e.getMessage(), userInfoList);

		}

		return new UserListRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage(), userInfoList);
	}

	@Override
	public UserListRes searchUserToList(String userName, Integer minRating, Boolean isAdministrator,
			Boolean lockedStatus) {

		// check rating
		if (minRating != null) {
			if (minRating <= 0 || minRating > 5) {
				return new UserListRes(RtnCode.INPUT_RATING_ERROR.getCode(), RtnCode.INPUT_RATING_ERROR.getMessage());
			}
		}

		// search user list
		List<User> userList = userDao.searchUserByInput(userName, minRating, isAdministrator, lockedStatus);

		// user change to userInfo
		List<UserInfo> userInfoList = new ArrayList<>();
		try {
			for (User user : userList) {
				userInfoList.add(userToUserInfo(user));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new UserListRes(RtnCode.DATA_ERROR.getCode(), e.getMessage(), userInfoList);

		}

		return new UserListRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage(), userInfoList);
	}
	
	public UserListRes searchUserByCaseId(Integer caseId) {
		
		if(caseId == null) {
			return new UserListRes(RtnCode.INPUT_CASE_ID_NULL.getCode(), RtnCode.INPUT_CASE_ID_NULL.getMessage());
		}
		
		// Get from DB
		List<User> userList = userDao.searchUserByCaseId(caseId);
		
		if(userList.size() <= 0) {
			return new UserListRes(RtnCode.ACCOUNT_NOT_FOUND_WITH_ID.getCode(), RtnCode.ACCOUNT_NOT_FOUND_WITH_ID.getMessage());
		}
		
		// user change to userInfo
		try {
			List<UserInfo> userInfoList = new ArrayList<>();
			for (User user : userList) {
				userInfoList.add(userToUserInfo(user));
			}
			
			return new UserListRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage(), userInfoList);
		} catch (Exception e) {
			e.printStackTrace();
			return new UserListRes(RtnCode.DATA_ERROR.getCode(), e.getMessage());

		}
		

	}
	

}
