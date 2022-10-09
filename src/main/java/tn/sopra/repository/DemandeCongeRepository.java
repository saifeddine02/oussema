package tn.sopra.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tn.sopra.domain.DemandeConge;

/**
 * Spring Data JPA repository for the DemandeConge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeCongeRepository extends JpaRepository<DemandeConge, Long> {}
