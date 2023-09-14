package team.bool.case_receiving_platform.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team.bool.case_receiving_platform.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, UUID>{
	
	public Optional<User> findByEmail(String email);
}
