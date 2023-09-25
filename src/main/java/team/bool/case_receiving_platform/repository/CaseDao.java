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
			+ " case when :minBudget is null then budget is not null else budget >= :minBudget end and "
			+ " case when :maxBudget is null then budget is not null else budget <= :maxBudget end and "
			+ " case when :inputLocation is null then location is not null else location = :inputLocation end and "
			+ " case when :deadlineFrom is null then deadline is not null else deadline >= :deadlineFrom end and "
			+ " case when :deadlineTo is null then deadline is not null else deadline <= :deadlineTo end and "
			+ " case when :caseClass is null then case_class is not null else case_class = :caseClass end and "
			+ " case when :inputInitiator is null then initiator is not null else initiator = :inputInitiator end and "
			+ " case when :onShelf is null then on_shelf is not null else on_shelf = :onShelf end and "
			+ " case when :currentStatus is null then current_status is not null else current_status = :currentStatus end and "
			+ " case when :caseRating is null then case_rating is not null else case_rating = :caseRating end"
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
