package team.bool.case_receiving_platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team.bool.case_receiving_platform.constants.RtnCode;
import team.bool.case_receiving_platform.repository.UserDao;
import team.bool.case_receiving_platform.service.ifs.CaseContractorService;
import team.bool.case_receiving_platform.vo.ContractorInfoVo;
import team.bool.case_receiving_platform.vo.ContractorListRes;

@Service
public class CaseContractorServiceImpl implements CaseContractorService {

	@Autowired
	private UserDao userDao;

	@Override
	public ContractorListRes searchUserByCaseId(Integer caseId) {

		if (caseId == null) {
			return new ContractorListRes(RtnCode.INPUT_CASE_ID_NULL.getCode(), RtnCode.INPUT_CASE_ID_NULL.getMessage());
		}

		// Get from DB
		List<ContractorInfoVo> contractorList = userDao.searchUserByCaseId(caseId);

		if (contractorList.size() <= 0) {
			return new ContractorListRes(RtnCode.ACCOUNT_NOT_FOUND_WITH_ID.getCode(),
					RtnCode.ACCOUNT_NOT_FOUND_WITH_ID.getMessage());
		}

		return new ContractorListRes(RtnCode.SUCCESSFUL.getCode(), RtnCode.SUCCESSFUL.getMessage(), contractorList);

	}

}
