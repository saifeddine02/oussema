package tn.sopra.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.sopra.service.dto.DemandeDTO;

/**
 * Service Interface for managing {@link tn.sopra.domain.Demande}.
 */
public interface DemandeService {
    /**
     * Save a demande.
     *
     * @param demandeDTO the entity to save.
     * @return the persisted entity.
     */
    DemandeDTO save(DemandeDTO demandeDTO);

    /**
     * Updates a demande.
     *
     * @param demandeDTO the entity to update.
     * @return the persisted entity.
     */
    DemandeDTO update(DemandeDTO demandeDTO);

    /**
     * Partially updates a demande.
     *
     * @param demandeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DemandeDTO> partialUpdate(DemandeDTO demandeDTO);

    /**
     * Get all the demandes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DemandeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" demande.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemandeDTO> findOne(Long id);

    /**
     * Delete the "id" demande.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
