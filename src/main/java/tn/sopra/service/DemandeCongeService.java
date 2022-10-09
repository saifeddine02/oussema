package tn.sopra.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.sopra.service.dto.DemandeCongeDTO;

/**
 * Service Interface for managing {@link tn.sopra.domain.DemandeConge}.
 */
public interface DemandeCongeService {
    /**
     * Save a demandeConge.
     *
     * @param demandeCongeDTO the entity to save.
     * @return the persisted entity.
     */
    DemandeCongeDTO save(DemandeCongeDTO demandeCongeDTO);

    /**
     * Updates a demandeConge.
     *
     * @param demandeCongeDTO the entity to update.
     * @return the persisted entity.
     */
    DemandeCongeDTO update(DemandeCongeDTO demandeCongeDTO);

    /**
     * Partially updates a demandeConge.
     *
     * @param demandeCongeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DemandeCongeDTO> partialUpdate(DemandeCongeDTO demandeCongeDTO);

    /**
     * Get all the demandeConges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DemandeCongeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" demandeConge.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemandeCongeDTO> findOne(Long id);

    /**
     * Delete the "id" demandeConge.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
