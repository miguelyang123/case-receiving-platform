package team.bool.case_receiving_platform.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import team.bool.case_receiving_platform.constants.AuthRtnCode;
import team.bool.case_receiving_platform.constants.CaseRtnCode;
import team.bool.case_receiving_platform.constants.RtnCode;
import team.bool.case_receiving_platform.entity.CaseContractor;
import team.bool.case_receiving_platform.repository.CaseContractorDao;
import team.bool.case_receiving_platform.repository.CaseDao;
import team.bool.case_receiving_platform.repository.UserDao;
import team.bool.case_receiving_platform.service.ifs.CaseContractorService;
import team.bool.case_receiving_platform.service.ifs.UserService;
import team.bool.case_receiving_platform.vo.CaseContractorListRes;
import team.bool.case_receiving_platform.vo.CaseListRes;
import team.bool.case_receiving_platform.vo.ContractorInfoVo;
import team.bool.case_receiving_platform.vo.ContractorListRes;
import team.bool.case_receiving_platform.vo.UserListRes;

@Service
public class CaseContractorServiceImpl implements CaseContractorService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	public CaseDao caseDao;
	
	@Autowired
	public CaseContractorDao caseContractorDao;
	
//	@Autowired
//	public UserService userService;

	@Override
	public ContractorListRes searchUserByCaseIdAndAccepted(Integer caseId, Boolean isAccepted) {

		// check input
		if (caseId == null) {
			return new ContractorListRes(RtnCode.INPUT_CASE_ID_NULL.getCode(), RtnCode.INPUT_CASE_ID_NULL.getMessage());
		}
		
		// check Case ID in DB
		if(!caseDao.existsById(caseId)) {
			return new ContractorListRes(CaseRtnCode.CASE_NOT_FOUND.getCode(), CaseRtnCode.CASE_NOT_FOUND.getMessage());
		}
		

		// Get from DB
		List<ContractorInfoVo> contractorList = userDao.searchUserByCaseId(caseId, isAccepted);

		if (contractorList.size() <= 0) {
			return new ContractorListRes(RtnCode.ACCOUNT_NOT_FOUND_WITH_ID.getCode(),
					RtnCode.ACCOUNT_NOT_FOUND_WITH_ID.getMessage());
		}

		return new ContractorListRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage(), contractorList);

	}
	
	@Override
	public CaseContractorListRes contractorAcceptCase(CaseContractor newCaseContractor) {
		try {
			
			int caseId = newCaseContractor.getCaseId();
			UUID uid = newCaseContractor.getContractorUid();

			// check Input
			if (caseId == 0) {
				return new CaseContractorListRes(RtnCode.INPUT_CASE_ID_NULL.getCode(),
						RtnCode.INPUT_CASE_ID_NULL.getMessage());
			}
			if (uid == null) {
				return new CaseContractorListRes(RtnCode.INPUT_USER_ID_NULL.getCode(),
						RtnCode.INPUT_USER_ID_NULL.getMessage());
			}
			
			if(caseContractorDao.existsByCaseIdAndContractorUid(caseId, uid)) {
				return new CaseContractorListRes(CaseRtnCode.ACCEPT_CASE_IS_REPEAT.getCode(),
						CaseRtnCode.ACCEPT_CASE_IS_REPEAT.getMessage());
			}

			// set Accept Date at now
			newCaseContractor.setAcceptDate(LocalDateTime.now());

			// save to DB
			newCaseContractor = caseContractorDao.save(newCaseContractor);

			return new CaseContractorListRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage(),
					new ArrayList<>(Arrays.asList(newCaseContractor)));

		} catch (Exception e) {
			return new CaseContractorListRes(RtnCode.ERROR.getCode(), e.getMessage());
		}

	};

	@Override
	public CaseContractorListRes initiatorChooseContractors(Integer caseId, List<String> userIdList,
			Boolean isAccepted) {

		if (caseId == null || CollectionUtils.isEmpty(userIdList) || isAccepted == null) {
			return new CaseContractorListRes(RtnCode.INPUT_NULL.getCode(), RtnCode.INPUT_NULL.getMessage());
		}

		int resultInt = caseContractorDao.chooseContractors(caseId, userIdList, isAccepted);

		// Update failed
		if (resultInt <= 0) {
			return new CaseContractorListRes(RtnCode.UPDATE_FAILED.getCode(), RtnCode.UPDATE_FAILED.getMessage());
		}

		return new CaseContractorListRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage(),
				caseContractorDao.findByCaseIdAndUserIdList(caseId, userIdList));

	}



}
