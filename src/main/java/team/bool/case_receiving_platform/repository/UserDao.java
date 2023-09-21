package team.bool.case_receiving_platform.repository;

<<<<<<< HEAD
import java.util.List;
import java.util.Optional;
import java.util.UUID;

=======
>>>>>>> Feature/PDF_fetch(upload+download)
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import team.bool.case_receiving_platform.entity.User;

<<<<<<< HEAD
=======
import java.util.Optional;
import java.util.UUID;

>>>>>>> Feature/PDF_fetch(upload+download)
@Repository
public interface UserDao extends JpaRepository<User, UUID> {

	public Optional<User> findByEmail(String email);

	public boolean existsByEmail(String email);
<<<<<<< HEAD

	@Query(value = "SELECT uuid, email, pwd, user_name, phone, rating, resume_pdf_path, is_administrator, locked_status FROM user where "
			+ " user_name like case when :inputName is null then '%%' else concat('%',:inputName,'%') end and "
			+ " case when :inputRating is null then rating is not null else rating >= :inputRating end and "
			+ " case when :inputIsAdministrator is null then is_administrator is not null else is_administrator = :inputIsAdministrator end and "
			+ " case when :inputLockedStatus is null then locked_status is not null else locked_status = :inputLockedStatus end ", nativeQuery = true)
	public List<User> searchUserByInput(@Param("inputName") String userName, @Param("inputRating") Integer minRating,
			@Param("inputIsAdministrator") Boolean isAdministrator, @Param("inputLockedStatus") Boolean lockedStatus);
=======
	
>>>>>>> Feature/PDF_fetch(upload+download)
}
