package team.bool.case_receiving_platform.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.bool.case_receiving_platform.entity.User;
import team.bool.case_receiving_platform.vo.ContractorInfoVo;

@Repository
public interface UserDao extends JpaRepository<User, UUID> {

	public Optional<User> findByEmail(String email);

	public boolean existsByEmail(String email);

	@Query(value = "SELECT uuid, email, pwd, user_name, phone, rating, resume_pdf_path, is_administrator, locked_status FROM user where "
			+ "  case when :inputUuid is null then uuid is not null else uuid = :inputUuid end and  "
			+ " user_name like case when :inputName is null then '%%' else concat('%',:inputName,'%') end and "
			+ " case when :inputRating is null then rating is not null else rating >= :inputRating end and "
			+ " case when :inputIsAdministrator is null then is_administrator is not null else is_administrator = :inputIsAdministrator end and "
			+ " case when :inputLockedStatus is null then locked_status is not null else locked_status = :inputLockedStatus end ", nativeQuery = true)
	public List<User> searchUserByInput(@Param("inputUuid") String uuid, @Param("inputName") String userName, @Param("inputRating") Integer minRating,
			@Param("inputIsAdministrator") Boolean isAdministrator, @Param("inputLockedStatus") Boolean lockedStatus);
	
	
	@Query(value = " SELECT new team.bool.case_receiving_platform.vo.ContractorInfoVo(u.uuid, u.email, u.userName, u.phone, u.rating, u.resumePdfPath, u.isAdministrator, u.lockedStatus, c.isAccepted , c.acceptDate) "
			+ " FROM User as u JOIN CaseContractor as c "
			+ " ON u.uuid = c.contractorUid "
			+ " WHERE c.caseId = :inputCaseId ")
	public List<ContractorInfoVo> searchUserByCaseId(@Param("inputCaseId") int caseId);
	

	
//	@Query(value = " SELECT u.uuid, u.email, u.pwd, u.user_name, u.phone, u.rating, u.resume_pdf_path, u.is_administrator, u.locked_status "
//			+ " FROM user u JOIN case_contractor c "
//			+ " ON u.uuid = c.contractor_uid "
//			+ " WHERE c.case_id = :caseId ", nativeQuery = true)
//	public List<User> searchUserByCaseId(@Param("caseId") int caseId);
}
