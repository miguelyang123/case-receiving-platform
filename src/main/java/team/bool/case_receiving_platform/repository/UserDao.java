package team.bool.case_receiving_platform.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.bool.case_receiving_platform.entity.User;

import java.util.UUID;

@Repository
public interface UserDao extends JpaRepository<User, UUID>{

}
