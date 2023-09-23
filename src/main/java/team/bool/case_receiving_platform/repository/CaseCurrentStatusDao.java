package team.bool.case_receiving_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team.bool.case_receiving_platform.entity.CaseCurrentStatus;

@Repository
public interface CaseCurrentStatusDao extends JpaRepository<CaseCurrentStatus, String> {

}
