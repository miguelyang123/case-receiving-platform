package team.bool.case_receiving_platform.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.OrderBy;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.bool.case_receiving_platform.entity.Case;
import team.bool.case_receiving_platform.entity.User;
import team.bool.case_receiving_platform.vo.ContractorInfoVo;

@Repository
public interface CaseDao extends JpaRepository<Case, Integer> {

	public Optional<Case> findByCaseName(String caseName);
	
//	@Query(value = "SELECT id, case_name, budget, location, content, deadline, created_date, case_class, initiator, on_shelf, "
//			+ " current_status, progress_percentage, completion_date, case_rating FROM `case` where "
//			+ " CONCAT_WS(case_name, content) like case when :searchKeyword is null then '%%' else concat('%',:searchKeyword,'%') end and "
//			+ " budget >= case when :minBudget is null then budget else :minBudget end and "
//			+ " budget <= case when :maxBudget is null then budget else :maxBudget end and "
//			+ " location = case when :inputLocation is null then location else :inputLocation end and "
//			+ " deadline >= case when :deadlineFrom is null then deadline else :deadlineFrom end and "
//			+ " deadline <= case when :deadlineTo is null then deadline else :deadlineTo end and "
//			+ " case_class = case when :caseClass is null then case_class else :caseClass end and "
//			+ " initiator = case when :inputInitiator is null then initiator else :inputInitiator end and "
//			+ " on_shelf = case when :onShelf is null then on_shelf else :onShelf end and "
//			+ " current_status = case when :currentStatus is null then current_status else :currentStatus end and "
//			+ " case_rating = case when :caseRating is null then case_rating else :caseRating end "
////			+ " order by :orderKey desc "
////			+ " case when :sortParam = 'name DESC' then :orderKey end desc "
//			, nativeQuery = true)
//	public List<Case> searchCaseByInput(
//			@Param("searchKeyword") String searchKeyword,
//			@Param("minBudget") Integer minBudget,
//			@Param("maxBudget") Integer maxBudget,
//			@Param("inputLocation") String location,
//			@Param("deadlineFrom") LocalDateTime deadlineFrom,
//			@Param("deadlineTo") LocalDateTime deadlineTo,
//			@Param("caseClass") String caseClass,
//			@Param("inputInitiator") String initiator,
//			@Param("onShelf") Boolean onShelf,
//			@Param("currentStatus") String currentStatus,
//			@Param("caseRating") Integer caseRating,
////			Sort sort
//
//			);
	@Query(value = "SELECT c FROM Case c where "
			+ " CONCAT_WS(c.caseName, c.content) like case when :searchKeyword is null then '%%' else concat('%',:searchKeyword,'%') end and "
			+ " c.budget >= case when :minBudget is null then c.budget else :minBudget end and "
			+ " c.budget <= case when :maxBudget is null then c.budget else :maxBudget end and "
			+ " c.location = case when :inputLocation is null then c.location else :inputLocation end and "
			+ " c.deadline >= case when :deadlineFrom is null then c.deadline else :deadlineFrom end and "
			+ " c.deadline <= case when :deadlineTo is null then c.deadline else :deadlineTo end and "
			+ " c.caseClass = case when :caseClass is null then c.caseClass else :caseClass end and "
			+ " c.initiator = case when :inputInitiator is null then c.initiator else :inputInitiator end and "
			+ " c.onShelf = case when :onShelf is null then c.onShelf else :onShelf end and "
			+ " c.currentStatus = case when :currentStatus is null then c.currentStatus else :currentStatus end and "
			+ " c.caseRating = case when :caseRating is null then c.caseRating else :caseRating end "
//			+ " order by :orderKey desc "
//			+ " case when :sortParam = 'name DESC' then :orderKey end desc "
			)
	public List<Case> searchCaseByInput(
			@Param("searchKeyword") String searchKeyword,
			@Param("minBudget") Integer minBudget,
			@Param("maxBudget") Integer maxBudget,
			@Param("inputLocation") String location,
			@Param("deadlineFrom") LocalDateTime deadlineFrom,
			@Param("deadlineTo") LocalDateTime deadlineTo,
			@Param("caseClass") String caseClass,
			@Param("inputInitiator") String initiator,
			@Param("onShelf") Boolean onShelf,
			@Param("currentStatus") String currentStatus,
			@Param("caseRating") Integer caseRating
			,Sort sort
			
			);
//	@Query(value = "SELECT id, case_name, budget, location, content, deadline, created_date, case_class, initiator, on_shelf, "
//			+ " current_status, progress_percentage, completion_date, case_rating FROM `case` where "
//			+ " CONCAT_WS(case_name, content) like case when ?1 is null then '%%' else concat('%',?1,'%') end and "
//			+ " budget >= case when ?2 is null then budget else ?2 end and "
//			+ " budget <= case when ?3 is null then budget else ?3 end and "
//			+ " location = case when ?4 is null then location else ?4 end and "
//			+ " deadline >= case when ?5 is null then deadline else ?5 end and "
//			+ " deadline <= case when ?6 is null then deadline else ?6 end and "
//			+ " case_class = case when ?7 is null then case_class else ?7 end and "
//			+ " initiator = case when ?8 is null then initiator else ?8 end and "
//			+ " on_shelf = case when ?9 is null then on_shelf else ?9 end and "
//			+ " current_status = case when ?10 is null then current_status else ?10 end and "
//			+ " case_rating = case when ?11 is null then case_rating else ?11 end "
////			+ " order by :orderKey desc "
////			+ " case when :sortParam = 'name DESC' then :orderKey end desc "
//, nativeQuery = true)
//	public List<Case> searchCaseByInput(
//			 String searchKeyword,
//			 Integer minBudget,
//			 Integer maxBudget,
//			 String location,
//			 LocalDateTime deadlineFrom,
//			 LocalDateTime deadlineTo,
//			 String caseClass,
//			 String initiator,
//			 Boolean onShelf,
//			 String currentStatus,
//			 Integer caseRating
//			,Sort sort
//			
//			);
	
	
//	@Query(value = " SELECT new team.bool.case_receiving_platform.vo.ContractorInfoVo(u.uuid, u.email, u.userName, u.phone, u.rating, u.resumePdfPath, u.isAdministrator, u.lockedStatus, c.isAccepted , c.acceptDate) "
//			+ " FROM User as u JOIN CaseContractor as c "
//			+ " ON u.uuid = c.contractorUid "
//			+ " WHERE c.caseId = :inputCaseId and "
//			+ " c.isAccepted = CASE WHEN :inputIsAccepted IS NULL THEN c.isAccepted ELSE :inputIsAccepted END "
//			)
	@Query(value = " SELECT ca.id, ca.case_name, ca.budget, ca.location, ca.content, ca.deadline, ca.created_date, ca.case_class, ca.initiator, ca.on_shelf, ca.current_status, ca.progress_percentage, ca.completion_date, ca.case_rating "
			+ " FROM `case` ca JOIN `case_contractor` cc "
			+ " ON ca.id = cc.case_id WHERE  cc.contractor_uid = :inputUserId "
			,nativeQuery = true)
	public List<Case> searchAcceptCaseByUserId(@Param("inputUserId") String userId);
	
	
	
	
}
