package tn.sopra.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tn.sopra.domain.UserSopra;

/**
 * Spring Data JPA repository for the UserSopra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserSopraRepository extends JpaRepository<UserSopra, Long> {}
