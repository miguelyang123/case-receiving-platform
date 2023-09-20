package team.bool.case_receiving_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@PostMapping("with_Input")
	public UserListRes searchUserWithInput(@RequestBody SearchUserReq req) {
		return sService.searchUserToList(req.getUserName(), req.getMinRating(), req.getIsAdministrator(),
				req.getLockedStatus());
	}

}
