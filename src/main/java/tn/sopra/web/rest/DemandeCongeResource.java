package tn.sopra.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import tn.sopra.repository.DemandeCongeRepository;
import tn.sopra.service.DemandeCongeService;
import tn.sopra.service.dto.DemandeCongeDTO;
import tn.sopra.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.sopra.domain.DemandeConge}.
 */
@RestController
@RequestMapping("/api")
public class DemandeCongeResource {

    private final Logger log = LoggerFactory.getLogger(DemandeCongeResource.class);

    private static final String ENTITY_NAME = "demandeConge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandeCongeService demandeCongeService;

    private final DemandeCongeRepository demandeCongeRepository;

    public DemandeCongeResource(DemandeCongeService demandeCongeService, DemandeCongeRepository demandeCongeRepository) {
        this.demandeCongeService = demandeCongeService;
        this.demandeCongeRepository = demandeCongeRepository;
    }

    /**
     * {@code POST  /demande-conges} : Create a new demandeConge.
     *
     * @param demandeCongeDTO the demandeCongeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandeCongeDTO, or with status {@code 400 (Bad Request)} if the demandeConge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demande-conges")
    public ResponseEntity<DemandeCongeDTO> createDemandeConge(@RequestBody DemandeCongeDTO demandeCongeDTO) throws URISyntaxException {
        log.debug("REST request to save DemandeConge : {}", demandeCongeDTO);
        if (demandeCongeDTO.getId() != null) {
            throw new BadRequestAlertException("A new demandeConge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandeCongeDTO result = demandeCongeService.save(demandeCongeDTO);
        return ResponseEntity
            .created(new URI("/api/demande-conges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demande-conges/:id} : Updates an existing demandeConge.
     *
     * @param id the id of the demandeCongeDTO to save.
     * @param demandeCongeDTO the demandeCongeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeCongeDTO,
     * or with status {@code 400 (Bad Request)} if the demandeCongeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandeCongeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demande-conges/{id}")
    public ResponseEntity<DemandeCongeDTO> updateDemandeConge(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandeCongeDTO demandeCongeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DemandeConge : {}, {}", id, demandeCongeDTO);
        if (demandeCongeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeCongeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeCongeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DemandeCongeDTO result = demandeCongeService.update(demandeCongeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeCongeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demande-conges/:id} : Partial updates given fields of an existing demandeConge, field will ignore if it is null
     *
     * @param id the id of the demandeCongeDTO to save.
     * @param demandeCongeDTO the demandeCongeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeCongeDTO,
     * or with status {@code 400 (Bad Request)} if the demandeCongeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the demandeCongeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandeCongeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/demande-conges/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DemandeCongeDTO> partialUpdateDemandeConge(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandeCongeDTO demandeCongeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DemandeConge partially : {}, {}", id, demandeCongeDTO);
        if (demandeCongeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeCongeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeCongeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandeCongeDTO> result = demandeCongeService.partialUpdate(demandeCongeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeCongeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /demande-conges} : get all the demandeConges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandeConges in body.
     */
    @GetMapping("/demande-conges")
    public ResponseEntity<List<DemandeCongeDTO>> getAllDemandeConges(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DemandeConges");
        Page<DemandeCongeDTO> page = demandeCongeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demande-conges/:id} : get the "id" demandeConge.
     *
     * @param id the id of the demandeCongeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandeCongeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demande-conges/{id}")
    public ResponseEntity<DemandeCongeDTO> getDemandeConge(@PathVariable Long id) {
        log.debug("REST request to get DemandeConge : {}", id);
        Optional<DemandeCongeDTO> demandeCongeDTO = demandeCongeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandeCongeDTO);
    }

    /**
     * {@code DELETE  /demande-conges/:id} : delete the "id" demandeConge.
     *
     * @param id the id of the demandeCongeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demande-conges/{id}")
    public ResponseEntity<Void> deleteDemandeConge(@PathVariable Long id) {
        log.debug("REST request to delete DemandeConge : {}", id);
        demandeCongeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
