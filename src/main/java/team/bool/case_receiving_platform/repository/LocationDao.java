package team.bool.case_receiving_platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import team.bool.case_receiving_platform.entity.Location;

@Repository
public interface LocationDao extends JpaRepository<Location, String>{

	@Query(value = "SELECT location_id, location_name FROM location where location_id like '1%' ", nativeQuery = true)
	public List<Location> selectOnsite();
	
	@Query(value = "SELECT location_id, location_name FROM location where location_id like '2%' ", nativeQuery = true)
	public List<Location> selectRemote();
	
}
