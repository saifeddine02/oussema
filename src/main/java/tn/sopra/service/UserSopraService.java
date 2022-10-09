package tn.sopra.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.sopra.service.dto.UserSopraDTO;

/**
 * Service Interface for managing {@link tn.sopra.domain.UserSopra}.
 */
public interface UserSopraService {
    /**
     * Save a userSopra.
     *
     * @param userSopraDTO the entity to save.
     * @return the persisted entity.
     */
    UserSopraDTO save(UserSopraDTO userSopraDTO);

    /**
     * Updates a userSopra.
     *
     * @param userSopraDTO the entity to update.
     * @return the persisted entity.
     */
    UserSopraDTO update(UserSopraDTO userSopraDTO);

    /**
     * Partially updates a userSopra.
     *
     * @param userSopraDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserSopraDTO> partialUpdate(UserSopraDTO userSopraDTO);

    /**
     * Get all the userSopras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserSopraDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userSopra.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserSopraDTO> findOne(Long id);

    /**
     * Delete the "id" userSopra.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
