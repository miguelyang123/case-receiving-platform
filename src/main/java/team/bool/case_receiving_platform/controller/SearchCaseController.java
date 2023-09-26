package team.bool.case_receiving_platform.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import team.bool.case_receiving_platform.entity.Case;
import team.bool.case_receiving_platform.service.ifs.CaseService;
import team.bool.case_receiving_platform.vo.CaseListRes;

@RestController
@RequestMapping("search_case")
public class SearchCaseController {

	@Autowired
	private CaseService caseService;

	@GetMapping("with_param")
	public CaseListRes findCaseWithInput(
			@RequestParam(name = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(name = "minBudget", required = false) Integer minBudget,
			@RequestParam(name = "maxBudget", required = false) Integer maxBudget,
			@RequestParam(name = "location", required = false) String location,
			@RequestParam(name = "deadlineFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadlineFrom,
			@RequestParam(name = "deadlineTo", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadlineTo,
			@RequestParam(name = "caseClass", required = false) String caseClass,
			@RequestParam(name = "initiator", required = false) String initiator,
			@RequestParam(name = "onShelf", required = false) Boolean onShelf,
			@RequestParam(name = "currentStatus", required = false) String currentStatus,
			@RequestParam(name = "caseRating", required = false) Integer caseRating
			) {
		
		// Param String is "" 
		if(!StringUtils.hasText(location)) {
			location = null;
		}
		if(!StringUtils.hasText(caseClass)) {
			caseClass = null;
		}
		if(!StringUtils.hasText(initiator)) {
			initiator = null;
		}
		
		return caseService.findCaseWithInput(searchKeyword, minBudget, maxBudget, location, deadlineFrom, deadlineTo, caseClass, initiator, onShelf, currentStatus, caseRating);
	}
	
	@GetMapping("with_user_id")
	public CaseListRes findAcceptCaseWithUserId(@RequestParam(name = "userId") String userId) {
		
		return caseService.findAcceptCaseWithUserId(userId);
		
	}
	
	
	
	
}
