package tn.sopra.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import tn.sopra.repository.ResponsableRepository;
import tn.sopra.service.ResponsableService;
import tn.sopra.service.dto.ResponsableDTO;
import tn.sopra.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.sopra.domain.Responsable}.
 */
@RestController
@RequestMapping("/api")
public class ResponsableResource {

    private final Logger log = LoggerFactory.getLogger(ResponsableResource.class);

    private static final String ENTITY_NAME = "responsable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResponsableService responsableService;

    private final ResponsableRepository responsableRepository;

    public ResponsableResource(ResponsableService responsableService, ResponsableRepository responsableRepository) {
        this.responsableService = responsableService;
        this.responsableRepository = responsableRepository;
    }

    /**
     * {@code POST  /responsables} : Create a new responsable.
     *
     * @param responsableDTO the responsableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new responsableDTO, or with status {@code 400 (Bad Request)} if the responsable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/responsables")
    public ResponseEntity<ResponsableDTO> createResponsable(@RequestBody ResponsableDTO responsableDTO) throws URISyntaxException {
        log.debug("REST request to save Responsable : {}", responsableDTO);
        if (responsableDTO.getId() != null) {
            throw new BadRequestAlertException("A new responsable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResponsableDTO result = responsableService.save(responsableDTO);
        return ResponseEntity
            .created(new URI("/api/responsables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /responsables/:id} : Updates an existing responsable.
     *
     * @param id the id of the responsableDTO to save.
     * @param responsableDTO the responsableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsableDTO,
     * or with status {@code 400 (Bad Request)} if the responsableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the responsableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/responsables/{id}")
    public ResponseEntity<ResponsableDTO> updateResponsable(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResponsableDTO responsableDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Responsable : {}, {}", id, responsableDTO);
        if (responsableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responsableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ResponsableDTO result = responsableService.update(responsableDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responsableDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /responsables/:id} : Partial updates given fields of an existing responsable, field will ignore if it is null
     *
     * @param id the id of the responsableDTO to save.
     * @param responsableDTO the responsableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsableDTO,
     * or with status {@code 400 (Bad Request)} if the responsableDTO is not valid,
     * or with status {@code 404 (Not Found)} if the responsableDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the responsableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/responsables/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ResponsableDTO> partialUpdateResponsable(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ResponsableDTO responsableDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Responsable partially : {}, {}", id, responsableDTO);
        if (responsableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, responsableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!responsableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ResponsableDTO> result = responsableService.partialUpdate(responsableDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responsableDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /responsables} : get all the responsables.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of responsables in body.
     */
    @GetMapping("/responsables")
    public ResponseEntity<List<ResponsableDTO>> getAllResponsables(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("responsable-is-null".equals(filter)) {
            log.debug("REST request to get all Responsables where responsable is null");
            return new ResponseEntity<>(responsableService.findAllWhereResponsableIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Responsables");
        Page<ResponsableDTO> page = responsableService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /responsables/:id} : get the "id" responsable.
     *
     * @param id the id of the responsableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the responsableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/responsables/{id}")
    public ResponseEntity<ResponsableDTO> getResponsable(@PathVariable Long id) {
        log.debug("REST request to get Responsable : {}", id);
        Optional<ResponsableDTO> responsableDTO = responsableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responsableDTO);
    }

    /**
     * {@code DELETE  /responsables/:id} : delete the "id" responsable.
     *
     * @param id the id of the responsableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/responsables/{id}")
    public ResponseEntity<Void> deleteResponsable(@PathVariable Long id) {
        log.debug("REST request to delete Responsable : {}", id);
        responsableService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
