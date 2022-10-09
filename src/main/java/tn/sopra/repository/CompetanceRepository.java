package tn.sopra.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tn.sopra.domain.Competance;

/**
 * Spring Data JPA repository for the Competance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetanceRepository extends JpaRepository<Competance, Long> {}
