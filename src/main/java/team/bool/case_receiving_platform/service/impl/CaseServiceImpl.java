package team.bool.case_receiving_platform.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import team.bool.case_receiving_platform.constants.CaseRtnCode;
import team.bool.case_receiving_platform.entity.Case;
import team.bool.case_receiving_platform.repository.CaseContractorDao;
import team.bool.case_receiving_platform.repository.CaseDao;
import team.bool.case_receiving_platform.service.ifs.CaseService;
import team.bool.case_receiving_platform.vo.CaseListRes;

@Service
public class CaseServiceImpl implements CaseService {

	@Autowired
	public CaseDao caseDao;
	
	@Autowired
	public CaseContractorDao caseContractorDao;

	private CaseListRes checkCaseInput(Case checkCase) {

		String caseName = checkCase.getCaseName();
		int budget = checkCase.getBudget();
		Optional<Integer> opBudget = Optional.ofNullable(budget);
		String content = checkCase.getContent();

		// check case_name
		if (!StringUtils.hasText(caseName) || caseName.length() > 30) {
			return new CaseListRes(CaseRtnCode.NAME_FORMAT_ERROR.getCode(), CaseRtnCode.NAME_FORMAT_ERROR.getMessage());
		}

		// check budget
		if (opBudget.isEmpty()) {
			return new CaseListRes(CaseRtnCode.BUDGET_IS_NULL.getCode(), CaseRtnCode.BUDGET_IS_NULL.getMessage());
		}
		if (budget < 0 || budget > Integer.MAX_VALUE) {
			return new CaseListRes(CaseRtnCode.NAME_FORMAT_ERROR.getCode(), CaseRtnCode.NAME_FORMAT_ERROR.getMessage());
		}

		// check content
		if (StringUtils.hasText(content)) {
			if (content.length() > 1000) {
				return new CaseListRes(CaseRtnCode.CONTENT_FORMAT_ERROR.getCode(),
						CaseRtnCode.CONTENT_FORMAT_ERROR.getMessage());
			}
		}

		return new CaseListRes(CaseRtnCode.SUCCESSFUL.getCode(), CaseRtnCode.SUCCESSFUL.getMessage());
	}

	private Case setCaseClassWithLocation(Case setCase) {
		
		char caseClassNum = setCase.getLocation().charAt(0);
		
		switch (caseClassNum) {
		case '1':
			setCase.setCaseClass("onsite");
			break;
		case '2':
			setCase.setCaseClass("remote");
			break;
		default:
			System.out.println("setCaseClassWithLocation switch error");
			break;
		}
		
		return setCase;
	}
	
	@Override
	public CaseListRes addNewCase(Case newCase) {

		// check input
		CaseListRes result = checkCaseInput(newCase);
		if (!result.getCode().equals(CaseRtnCode.SUCCESSFUL.getCode())) {
			return result;
		}
		
		//set CaseClass
		newCase = setCaseClassWithLocation(newCase);
		
		// set create date time now
		newCase.setCreatedDate(LocalDateTime.now());
		
		// onShelf default 1
		if(newCase.getOnShelf() == null) {
			newCase.setOnShelf(true);
		}
		
		//save to DB
		Case savedCase = caseDao.save(newCase);
		
		return new CaseListRes(CaseRtnCode.SUCCESSFUL_ADD.getCode(), CaseRtnCode.SUCCESSFUL_ADD.getMessage(),
				new ArrayList<Case>(Arrays.asList(savedCase)));
	}

	@Override
	public CaseListRes editCase(Case newCase) {
		
		// check input
		CaseListRes result = checkCaseInput(newCase);
		if (!result.getCode().equals(CaseRtnCode.SUCCESSFUL.getCode())) {
			return result;
		}
		
		//set CaseClass
		newCase = setCaseClassWithLocation(newCase);
		
		//save to DB
		Case savedCase = caseDao.save(newCase);
		
		return new CaseListRes(CaseRtnCode.SUCCESSFUL_UPDATE.getCode(), CaseRtnCode.SUCCESSFUL_UPDATE.getMessage(),
				new ArrayList<Case>(Arrays.asList(savedCase)));
	}

	@Override
	public CaseListRes findCaseWithInput(
			String searchKeyword,
			Integer minBudget,
			Integer maxBudget,
			String location,
			LocalDateTime deadlineFrom,
			LocalDateTime deadlineTo,
			String caseClass,
			String initiator,
			Boolean onShelf,
			String currentStatus,
			Integer caseRating
			) {

		// check Budget input
		if(minBudget != null && minBudget < 0) {
			return new CaseListRes(CaseRtnCode.BUDGET_INPUT_ERROR.getCode(), CaseRtnCode.BUDGET_INPUT_ERROR.getMessage());
		}
		if(maxBudget != null && maxBudget < 0) {
			return new CaseListRes(CaseRtnCode.BUDGET_INPUT_ERROR.getCode(), CaseRtnCode.BUDGET_INPUT_ERROR.getMessage());
		}
		// check caseRating input
		if(caseRating != null && caseRating < 0 && caseRating > 5) {
			return new CaseListRes(CaseRtnCode.RATING_INPUT_ERROR.getCode(), CaseRtnCode.RATING_INPUT_ERROR.getMessage());
		}
		
		
		
		//search case DB
		List<Case> caseList = caseDao.searchCaseByInput(searchKeyword, minBudget, maxBudget, location,
				deadlineFrom, deadlineTo, caseClass, initiator, onShelf, currentStatus, caseRating);
		
		return new CaseListRes(CaseRtnCode.SUCCESSFUL.getCode(), CaseRtnCode.SUCCESSFUL.getMessage(), new ArrayList<Case>(caseList));
	}

	public void contractorAcceptCase(String contractorId, int caseId) {
		
	};

}
