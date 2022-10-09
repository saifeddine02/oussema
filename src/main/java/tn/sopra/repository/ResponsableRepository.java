package tn.sopra.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tn.sopra.domain.Responsable;

/**
 * Spring Data JPA repository for the Responsable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResponsableRepository extends JpaRepository<Responsable, Long> {}
