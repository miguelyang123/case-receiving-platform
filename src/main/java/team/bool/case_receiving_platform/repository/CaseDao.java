package team.bool.case_receiving_platform.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team.bool.case_receiving_platform.entity.Case;
import team.bool.case_receiving_platform.entity.User;

public interface CaseDao extends JpaRepository<Case, Integer> {

	public Optional<Case> findByCaseName(String caseName);
	
//	@Query(value = "SELECT case_name, budget, location, content, deadline, created_date, case_class, initiator, on_shelf FROM `case` where "
//			+ " case_name like case when :searchKeyword is null then '%%' else concat('%',:searchKeyword,'%') or "
//			+ " content like case when :searchKeyword is null then '%%' else concat('%',:searchKeyword,'%') "
////			+ " case when :minBudget is null then budget is not null else budget >= :minBudget end and "
////			+ " case when :maxBudget is null then budget is not null else budget <= :maxBudget end and "
////			+ " case when :inputLocation is null then location is not null else location = :inputLocation end and "
////			+ " case when null is null then deadline is not null else deadline < '2023-11-10' end and "
////			+ " case when null is null then case_class is not null else case_class = 'onsite' end and "
////			+ " case when null is null then initiator is not null else initiator = '2d94e2dd-f635-4e59-bbe6-9f48bdf23fab' end and "
////			+ " case when null is null then on_shelf is not null else on_shelf = true end "
//			, nativeQuery = true)
//	public List<Case> searchCaseByInput(@Param("searchKeyword") String searchKeyword
////			@Param("minBudget") Integer minBudget, @Param("maxBudget") Integer maxBudget,
////			@Param("inputLocation") String location
////			@Param("deadline") LocalDateTime deadline
//			);
	
//	@Query(value = "SELECT uuid, email, pwd, user_name, phone, rating, resume_pdf_path, is_administrator, locked_status FROM user where "
//			+ " user_name like case when :inputName is null then '%%' else concat('%',:inputName,'%') end and "
//			+ " case when :inputRating is null then rating is not null else rating >= :inputRating end and "
//			+ " case when :inputIsAdministrator is null then is_administrator is not null else is_administrator = :inputIsAdministrator end and "
//			+ " case when :inputLockedStatus is null then locked_status is not null else locked_status = :inputLockedStatus end ", nativeQuery = true)
	
	
	
	
}
