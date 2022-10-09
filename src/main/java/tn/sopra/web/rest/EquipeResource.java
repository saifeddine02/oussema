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
import tn.sopra.repository.EquipeRepository;
import tn.sopra.service.EquipeService;
import tn.sopra.service.dto.EquipeDTO;
import tn.sopra.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.sopra.domain.Equipe}.
 */
@RestController
@RequestMapping("/api")
public class EquipeResource {

    private final Logger log = LoggerFactory.getLogger(EquipeResource.class);

    private static final String ENTITY_NAME = "equipe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipeService equipeService;

    private final EquipeRepository equipeRepository;

    public EquipeResource(EquipeService equipeService, EquipeRepository equipeRepository) {
        this.equipeService = equipeService;
        this.equipeRepository = equipeRepository;
    }

    /**
     * {@code POST  /equipes} : Create a new equipe.
     *
     * @param equipeDTO the equipeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipeDTO, or with status {@code 400 (Bad Request)} if the equipe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/equipes")
    public ResponseEntity<EquipeDTO> createEquipe(@RequestBody EquipeDTO equipeDTO) throws URISyntaxException {
        log.debug("REST request to save Equipe : {}", equipeDTO);
        if (equipeDTO.getId() != null) {
            throw new BadRequestAlertException("A new equipe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EquipeDTO result = equipeService.save(equipeDTO);
        return ResponseEntity
            .created(new URI("/api/equipes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equipes/:id} : Updates an existing equipe.
     *
     * @param id the id of the equipeDTO to save.
     * @param equipeDTO the equipeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipeDTO,
     * or with status {@code 400 (Bad Request)} if the equipeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/equipes/{id}")
    public ResponseEntity<EquipeDTO> updateEquipe(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EquipeDTO equipeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Equipe : {}, {}", id, equipeDTO);
        if (equipeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EquipeDTO result = equipeService.update(equipeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /equipes/:id} : Partial updates given fields of an existing equipe, field will ignore if it is null
     *
     * @param id the id of the equipeDTO to save.
     * @param equipeDTO the equipeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipeDTO,
     * or with status {@code 400 (Bad Request)} if the equipeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the equipeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the equipeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/equipes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EquipeDTO> partialUpdateEquipe(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EquipeDTO equipeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Equipe partially : {}, {}", id, equipeDTO);
        if (equipeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, equipeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!equipeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EquipeDTO> result = equipeService.partialUpdate(equipeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /equipes} : get all the equipes.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipes in body.
     */
    @GetMapping("/equipes")
    public ResponseEntity<List<EquipeDTO>> getAllEquipes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Equipes");
        Page<EquipeDTO> page;
        if (eagerload) {
            page = equipeService.findAllWithEagerRelationships(pageable);
        } else {
            page = equipeService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /equipes/:id} : get the "id" equipe.
     *
     * @param id the id of the equipeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/equipes/{id}")
    public ResponseEntity<EquipeDTO> getEquipe(@PathVariable Long id) {
        log.debug("REST request to get Equipe : {}", id);
        Optional<EquipeDTO> equipeDTO = equipeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(equipeDTO);
    }

    /**
     * {@code DELETE  /equipes/:id} : delete the "id" equipe.
     *
     * @param id the id of the equipeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/equipes/{id}")
    public ResponseEntity<Void> deleteEquipe(@PathVariable Long id) {
        log.debug("REST request to delete Equipe : {}", id);
        equipeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
