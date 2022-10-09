package tn.sopra.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tn.sopra.domain.Demande;

/**
 * Spring Data JPA repository for the Demande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeRepository extends JpaRepository<Demande, Long> {}
