package team.bool.case_receiving_platform.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import team.bool.case_receiving_platform.service.ifs.SearchUserService;
import team.bool.case_receiving_platform.vo.SearchUserReq;
import team.bool.case_receiving_platform.vo.UserListRes;

@RestController
@RequestMapping("search_user")
public class SearchUserController {

	@Autowired
	private SearchUserService sService;

	@GetMapping("get_all")
	public UserListRes getAllUser() {
		return sService.findAllUser();
	}

	@GetMapping("with_Input")
	public UserListRes searchUserWithInput(
			@RequestParam(name = "uuid", required = false) String uuid,
			@RequestParam(name = "name", required = false) String userName,
			@RequestParam(name = "rating", required = false) Integer minRating,
			@RequestParam(name = "admin", required = false) Boolean isAdministrator,
			@RequestParam(name = "locked", required = false) Boolean lockedStatus
			) {
		
		return sService.searchUserToList(uuid, userName, minRating, isAdministrator, lockedStatus);
	}

}
