package team.bool.case_receiving_platform.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import team.bool.case_receiving_platform.constants.AuthRtnCode;
import team.bool.case_receiving_platform.constants.CaseRtnCode;
import team.bool.case_receiving_platform.constants.RtnCode;
import team.bool.case_receiving_platform.entity.Case;
import team.bool.case_receiving_platform.entity.CaseContractor;
import team.bool.case_receiving_platform.repository.CaseContractorDao;
import team.bool.case_receiving_platform.repository.CaseDao;
import team.bool.case_receiving_platform.service.ifs.CaseService;
import team.bool.case_receiving_platform.service.ifs.UserService;
import team.bool.case_receiving_platform.vo.CaseContractorListRes;
import team.bool.case_receiving_platform.vo.CaseListRes;
import team.bool.case_receiving_platform.vo.UserListRes;

@Service
public class CaseServiceImpl implements CaseService {

	@Autowired
	public CaseDao caseDao;

	@Autowired
	public UserService userService;

//	@Autowired
//	public CaseContractorDao caseContractorDao;

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

		// set CaseClass
		newCase = setCaseClassWithLocation(newCase);

		// set create date time now
		newCase.setCreatedDate(LocalDateTime.now());

		// onShelf default 1
		if (newCase.getOnShelf() == null) {
			newCase.setOnShelf(true);
		}

		// save to DB
		Case savedCase = caseDao.save(newCase);

		return new CaseListRes(CaseRtnCode.SUCCESSFUL_ADD.getCode(), CaseRtnCode.SUCCESSFUL_ADD.getMessage(),
				new ArrayList<Case>(Arrays.asList(savedCase)));
	}

	@Override
	public CaseListRes editCase(Case editedCase) {

		Integer eCaseId = editedCase.getId();
		Optional<Case> opCase = caseDao.findById(eCaseId);
		Case oldCase = opCase.get();
		boolean editedCaseHasChanged = editedCase.getCaseRating() != oldCase.getCaseRating();
		
		// check input
		CaseListRes result = checkCaseInput(editedCase);
		if (!result.getCode().equals(CaseRtnCode.SUCCESSFUL.getCode())) {
			return result;
		}
		// check Case ID in DB
		if (opCase.isEmpty()) {
			return new CaseListRes(CaseRtnCode.CASE_NOT_FOUND.getCode(), CaseRtnCode.CASE_NOT_FOUND.getMessage());
		}

		// set CaseClass
		editedCase = setCaseClassWithLocation(editedCase);
		// set create date time
		editedCase.setCreatedDate(caseDao.findById(editedCase.getId()).get().getCreatedDate());

		// save to DB
		Case savedCase = caseDao.save(editedCase);
		
		
		// if change case rating, will change user rating
		if (editedCase.getCaseRating() != 0 && editedCaseHasChanged) {

			try {
				
				// update user rating
				UserListRes userListRes = userService.updateUserRatingWithCaseId(editedCase.getId());

				// update error
				if (!userListRes.getCode().equals(AuthRtnCode.SUCCESSFUL_CHANGE.getCode())) {
					return new CaseListRes(userListRes.getCode(), userListRes.getMessage());
				}
				
			} catch (Exception e) {
				return new CaseListRes(CaseRtnCode.DATA_ERROR.getCode(), e.getMessage());
			}


		}

		return new CaseListRes(CaseRtnCode.SUCCESSFUL_UPDATE.getCode(), CaseRtnCode.SUCCESSFUL_UPDATE.getMessage(),
				new ArrayList<Case>(Arrays.asList(savedCase)));
	}

	@Override
	public CaseListRes findCaseWithInput(String searchKeyword, Integer minBudget, Integer maxBudget, String location,
			LocalDateTime deadlineFrom, LocalDateTime deadlineTo, String caseClass, String initiator, Boolean onShelf,
			String currentStatus, Integer caseRating) {

		// check Budget input
		if (minBudget != null && minBudget < 0) {
			return new CaseListRes(CaseRtnCode.BUDGET_INPUT_ERROR.getCode(),
					CaseRtnCode.BUDGET_INPUT_ERROR.getMessage());
		}
		if (maxBudget != null && maxBudget < 0) {
			return new CaseListRes(CaseRtnCode.BUDGET_INPUT_ERROR.getCode(),
					CaseRtnCode.BUDGET_INPUT_ERROR.getMessage());
		}
		// check caseRating input
		if (caseRating != null && caseRating < 0 && caseRating > 5) {
			return new CaseListRes(CaseRtnCode.RATING_INPUT_ERROR.getCode(),
					CaseRtnCode.RATING_INPUT_ERROR.getMessage());
		}

		// search case DB
		List<Case> caseList = caseDao.searchCaseByInput(searchKeyword, minBudget, maxBudget, location, deadlineFrom,
				deadlineTo, caseClass, initiator, onShelf, currentStatus, caseRating);

		return new CaseListRes(CaseRtnCode.SUCCESSFUL.getCode(), CaseRtnCode.SUCCESSFUL.getMessage(),
				new ArrayList<Case>(caseList));
	}

//	public CaseContractorListRes contractorAcceptCase(CaseContractor newCaseContractor) {
//		try {
//
//			// check Input
//			if (newCaseContractor.getCaseId() == 0) {
//				return new CaseContractorListRes(RtnCode.INPUT_CASE_ID_NULL.getCode(),
//						RtnCode.INPUT_CASE_ID_NULL.getMessage());
//			}
//			if (newCaseContractor.getContractorUid() == null) {
//				return new CaseContractorListRes(RtnCode.INPUT_USER_ID_NULL.getCode(),
//						RtnCode.INPUT_USER_ID_NULL.getMessage());
//			}
//
//			// set Accept Date at now
//			newCaseContractor.setAcceptDate(LocalDateTime.now());
//
//			// save to DB
//			newCaseContractor = caseContractorDao.save(newCaseContractor);
//
//			return new CaseContractorListRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage(),
//					new ArrayList<>(Arrays.asList(newCaseContractor)));
//
//		} catch (Exception e) {
//			return new CaseContractorListRes(RtnCode.ERROR.getCode(), e.getMessage());
//		}
//
//	};
//
//	public CaseContractorListRes initiatorChooseContractors(Integer caseId, List<String> userIdList,
//			Boolean isAccepted) {
//
//		if (caseId == null || CollectionUtils.isEmpty(userIdList) || isAccepted == null) {
//			return new CaseContractorListRes(RtnCode.INPUT_NULL.getCode(), RtnCode.INPUT_NULL.getMessage());
//		}
//
//		int resultInt = caseContractorDao.chooseContractors(caseId, userIdList, isAccepted);
//
//		// Update failed
//		if (resultInt <= 0) {
//			return new CaseContractorListRes(RtnCode.UPDATE_FAILED.getCode(), RtnCode.UPDATE_FAILED.getMessage());
//		}
//
//		return new CaseContractorListRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage(),
//				caseContractorDao.findByCaseIdAndUserIdList(caseId, userIdList));
//
//	}

}
