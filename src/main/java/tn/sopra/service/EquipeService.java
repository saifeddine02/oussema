package tn.sopra.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.sopra.service.dto.EquipeDTO;

/**
 * Service Interface for managing {@link tn.sopra.domain.Equipe}.
 */
public interface EquipeService {
    /**
     * Save a equipe.
     *
     * @param equipeDTO the entity to save.
     * @return the persisted entity.
     */
    EquipeDTO save(EquipeDTO equipeDTO);

    /**
     * Updates a equipe.
     *
     * @param equipeDTO the entity to update.
     * @return the persisted entity.
     */
    EquipeDTO update(EquipeDTO equipeDTO);

    /**
     * Partially updates a equipe.
     *
     * @param equipeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EquipeDTO> partialUpdate(EquipeDTO equipeDTO);

    /**
     * Get all the equipes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EquipeDTO> findAll(Pageable pageable);

    /**
     * Get all the equipes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EquipeDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" equipe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EquipeDTO> findOne(Long id);

    /**
     * Delete the "id" equipe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
