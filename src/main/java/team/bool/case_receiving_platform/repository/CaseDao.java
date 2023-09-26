package team.bool.case_receiving_platform.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.bool.case_receiving_platform.entity.Case;
import team.bool.case_receiving_platform.entity.User;

@Repository
public interface CaseDao extends JpaRepository<Case, Integer> {

	public Optional<Case> findByCaseName(String caseName);
	
	@Query(value = "SELECT id, case_name, budget, location, content, deadline, created_date, case_class, initiator, on_shelf, "
			+ " current_status, progress_percentage, completion_date, case_rating FROM `case` where "
			+ " CONCAT_WS(case_name, content) like case when :searchKeyword is null then '%%' else concat('%',:searchKeyword,'%') end and "
			+ " budget >= case when :minBudget is null then budget else :minBudget end and "
			+ " budget <= case when :maxBudget is null then budget else :maxBudget end and "
			+ " location = case when :inputLocation is null then location else :inputLocation end and "
			+ " deadline >= case when :deadlineFrom is null then deadline else :deadlineFrom end and "
			+ " deadline <= case when :deadlineTo is null then deadline else :deadlineTo end and "
			+ " case_class = case when :caseClass is null then case_class else :caseClass end and "
			+ " initiator = case when :inputInitiator is null then initiator else :inputInitiator end and "
			+ " on_shelf = case when :onShelf is null then on_shelf else :onShelf end and "
			+ " current_status = case when :currentStatus is null then current_status else :currentStatus end and "
			+ " case_rating = case when :caseRating is null then case_rating else :caseRating end"
			, nativeQuery = true)
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
			);
	
	
	
	
}
