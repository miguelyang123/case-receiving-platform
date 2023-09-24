package team.bool.case_receiving_platform.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.bool.case_receiving_platform.entity.CaseContractor;
import team.bool.case_receiving_platform.entity.CaseContractorId;

@Repository
public interface CaseContractorDao extends JpaRepository<CaseContractor, CaseContractorId> {

	@Modifying
	@Transactional
	@Query(value = " UPDATE `case_contractor` SET `is_accepted` = :inputIsAccepted WHERE `case_id` = :inputCaseId AND `contractor_uid` IN :userIdList ", nativeQuery = true)
	public int chooseContractors(@Param("inputCaseId") int caseId, @Param("userIdList") List<String> userIdList,
			@Param("inputIsAccepted") boolean isAccepted);
	
//	SELECT case_id, contractor_uid, is_accepted, accept_date FROM case_contractor WHERE `case_id` = 1 AND contractor_uid IN ('e451beb8-fc91-472b-933b-b96a0e8c853b','e81b75d7-78b2-4ff0-aea5-a91b49b5aaaf')
	
	@Query(value = " SELECT case_id, contractor_uid, is_accepted, accept_date FROM case_contractor WHERE `case_id` = :inputCaseId AND contractor_uid IN :userIdList ", nativeQuery = true)
	public List<CaseContractor> findByCaseIdAndUserIdList(@Param("inputCaseId") int caseId, @Param("userIdList") List<String> userIdList);

}
