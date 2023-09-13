package team.bool.case_receiving_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team.bool.case_receiving_platform.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, String>{

}
