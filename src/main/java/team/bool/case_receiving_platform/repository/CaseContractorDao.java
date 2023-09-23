package team.bool.case_receiving_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team.bool.case_receiving_platform.entity.CaseContractor;
import team.bool.case_receiving_platform.entity.CaseContractorId;

@Repository
public interface CaseContractorDao extends JpaRepository<CaseContractor, CaseContractorId>{

}
