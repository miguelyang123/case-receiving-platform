package team.bool.case_receiving_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team.bool.case_receiving_platform.entity.Location;

public interface LocationDao extends JpaRepository<Location, String>{

}
