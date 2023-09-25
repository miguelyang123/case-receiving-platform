package team.bool.case_receiving_platform.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
			+ " WHERE c.caseId = :inputCaseId and "
			+ " c.isAccepted = CASE WHEN :inputIsAccepted IS NULL THEN c.isAccepted ELSE :inputIsAccepted END "
			)
	public List<ContractorInfoVo> searchUserByCaseId(@Param("inputCaseId") int caseId, @Param("inputIsAccepted") Boolean isAccepted);
	

	@Modifying
	@Transactional
	@Query(value = " UPDATE `user` u JOIN ( SELECT  cc.contractor_uid uid, avg(ca.case_rating) avgprice "
			+ " FROM `case` ca JOIN case_contractor cc ON ca.id = cc.case_id "
			+ " WHERE ca.case_rating != 0 AND cc.contractor_uid IN "
			+ " ( SELECT contractor_uid FROM case_contractor WHERE "
			+ " case_id = CASE WHEN :inputCaseId is null THEN case_id ELSE :inputCaseId END ) "
			+ " GROUP BY cc.contractor_uid ) s "
			+ " ON u.uuid = s.uid SET u.rating = s.avgprice ", nativeQuery = true)
	public int updateUserRating(@Param("inputCaseId") Integer caseId);
	
}
