package tn.sopra.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.sopra.service.dto.ResponsableDTO;

/**
 * Service Interface for managing {@link tn.sopra.domain.Responsable}.
 */
public interface ResponsableService {
    /**
     * Save a responsable.
     *
     * @param responsableDTO the entity to save.
     * @return the persisted entity.
     */
    ResponsableDTO save(ResponsableDTO responsableDTO);

    /**
     * Updates a responsable.
     *
     * @param responsableDTO the entity to update.
     * @return the persisted entity.
     */
    ResponsableDTO update(ResponsableDTO responsableDTO);

    /**
     * Partially updates a responsable.
     *
     * @param responsableDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ResponsableDTO> partialUpdate(ResponsableDTO responsableDTO);

    /**
     * Get all the responsables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResponsableDTO> findAll(Pageable pageable);
    /**
     * Get all the ResponsableDTO where Responsable is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ResponsableDTO> findAllWhereResponsableIsNull();

    /**
     * Get the "id" responsable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResponsableDTO> findOne(Long id);

    /**
     * Delete the "id" responsable.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
