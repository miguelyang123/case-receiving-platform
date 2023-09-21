package team.bool.case_receiving_platform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import team.bool.case_receiving_platform.entity.Case;

public interface CaseDao extends JpaRepository<Case, Integer> {

	public Optional<Case> findByCaseName(String caseName);
}
