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
import tn.sopra.repository.CompetanceRepository;
import tn.sopra.service.CompetanceService;
import tn.sopra.service.dto.CompetanceDTO;
import tn.sopra.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.sopra.domain.Competance}.
 */
@RestController
@RequestMapping("/api")
public class CompetanceResource {

    private final Logger log = LoggerFactory.getLogger(CompetanceResource.class);

    private static final String ENTITY_NAME = "competance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetanceService competanceService;

    private final CompetanceRepository competanceRepository;

    public CompetanceResource(CompetanceService competanceService, CompetanceRepository competanceRepository) {
        this.competanceService = competanceService;
        this.competanceRepository = competanceRepository;
    }

    /**
     * {@code POST  /competances} : Create a new competance.
     *
     * @param competanceDTO the competanceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competanceDTO, or with status {@code 400 (Bad Request)} if the competance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competances")
    public ResponseEntity<CompetanceDTO> createCompetance(@RequestBody CompetanceDTO competanceDTO) throws URISyntaxException {
        log.debug("REST request to save Competance : {}", competanceDTO);
        if (competanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new competance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetanceDTO result = competanceService.save(competanceDTO);
        return ResponseEntity
            .created(new URI("/api/competances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competances/:id} : Updates an existing competance.
     *
     * @param id the id of the competanceDTO to save.
     * @param competanceDTO the competanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competanceDTO,
     * or with status {@code 400 (Bad Request)} if the competanceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competances/{id}")
    public ResponseEntity<CompetanceDTO> updateCompetance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompetanceDTO competanceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Competance : {}, {}", id, competanceDTO);
        if (competanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competanceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompetanceDTO result = competanceService.update(competanceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /competances/:id} : Partial updates given fields of an existing competance, field will ignore if it is null
     *
     * @param id the id of the competanceDTO to save.
     * @param competanceDTO the competanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competanceDTO,
     * or with status {@code 400 (Bad Request)} if the competanceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the competanceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the competanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/competances/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompetanceDTO> partialUpdateCompetance(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompetanceDTO competanceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Competance partially : {}, {}", id, competanceDTO);
        if (competanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competanceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompetanceDTO> result = competanceService.partialUpdate(competanceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competanceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /competances} : get all the competances.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competances in body.
     */
    @GetMapping("/competances")
    public ResponseEntity<List<CompetanceDTO>> getAllCompetances(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Competances");
        Page<CompetanceDTO> page = competanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /competances/:id} : get the "id" competance.
     *
     * @param id the id of the competanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competances/{id}")
    public ResponseEntity<CompetanceDTO> getCompetance(@PathVariable Long id) {
        log.debug("REST request to get Competance : {}", id);
        Optional<CompetanceDTO> competanceDTO = competanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competanceDTO);
    }

    /**
     * {@code DELETE  /competances/:id} : delete the "id" competance.
     *
     * @param id the id of the competanceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competances/{id}")
    public ResponseEntity<Void> deleteCompetance(@PathVariable Long id) {
        log.debug("REST request to delete Competance : {}", id);
        competanceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
