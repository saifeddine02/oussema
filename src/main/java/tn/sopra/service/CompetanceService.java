package tn.sopra.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.sopra.service.dto.CompetanceDTO;

/**
 * Service Interface for managing {@link tn.sopra.domain.Competance}.
 */
public interface CompetanceService {
    /**
     * Save a competance.
     *
     * @param competanceDTO the entity to save.
     * @return the persisted entity.
     */
    CompetanceDTO save(CompetanceDTO competanceDTO);

    /**
     * Updates a competance.
     *
     * @param competanceDTO the entity to update.
     * @return the persisted entity.
     */
    CompetanceDTO update(CompetanceDTO competanceDTO);

    /**
     * Partially updates a competance.
     *
     * @param competanceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompetanceDTO> partialUpdate(CompetanceDTO competanceDTO);

    /**
     * Get all the competances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompetanceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" competance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompetanceDTO> findOne(Long id);

    /**
     * Delete the "id" competance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
