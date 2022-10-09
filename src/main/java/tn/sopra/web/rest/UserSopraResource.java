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
import tn.sopra.repository.UserSopraRepository;
import tn.sopra.service.UserSopraService;
import tn.sopra.service.dto.UserSopraDTO;
import tn.sopra.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.sopra.domain.UserSopra}.
 */
@RestController
@RequestMapping("/api")
public class UserSopraResource {

    private final Logger log = LoggerFactory.getLogger(UserSopraResource.class);

    private static final String ENTITY_NAME = "userSopra";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserSopraService userSopraService;

    private final UserSopraRepository userSopraRepository;

    public UserSopraResource(UserSopraService userSopraService, UserSopraRepository userSopraRepository) {
        this.userSopraService = userSopraService;
        this.userSopraRepository = userSopraRepository;
    }

    /**
     * {@code POST  /user-sopras} : Create a new userSopra.
     *
     * @param userSopraDTO the userSopraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userSopraDTO, or with status {@code 400 (Bad Request)} if the userSopra has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-sopras")
    public ResponseEntity<UserSopraDTO> createUserSopra(@RequestBody UserSopraDTO userSopraDTO) throws URISyntaxException {
        log.debug("REST request to save UserSopra : {}", userSopraDTO);
        if (userSopraDTO.getId() != null) {
            throw new BadRequestAlertException("A new userSopra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserSopraDTO result = userSopraService.save(userSopraDTO);
        return ResponseEntity
            .created(new URI("/api/user-sopras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-sopras/:id} : Updates an existing userSopra.
     *
     * @param id the id of the userSopraDTO to save.
     * @param userSopraDTO the userSopraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userSopraDTO,
     * or with status {@code 400 (Bad Request)} if the userSopraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userSopraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-sopras/{id}")
    public ResponseEntity<UserSopraDTO> updateUserSopra(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserSopraDTO userSopraDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserSopra : {}, {}", id, userSopraDTO);
        if (userSopraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userSopraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userSopraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserSopraDTO result = userSopraService.update(userSopraDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userSopraDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-sopras/:id} : Partial updates given fields of an existing userSopra, field will ignore if it is null
     *
     * @param id the id of the userSopraDTO to save.
     * @param userSopraDTO the userSopraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userSopraDTO,
     * or with status {@code 400 (Bad Request)} if the userSopraDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userSopraDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userSopraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-sopras/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserSopraDTO> partialUpdateUserSopra(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserSopraDTO userSopraDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserSopra partially : {}, {}", id, userSopraDTO);
        if (userSopraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userSopraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userSopraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserSopraDTO> result = userSopraService.partialUpdate(userSopraDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userSopraDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-sopras} : get all the userSopras.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userSopras in body.
     */
    @GetMapping("/user-sopras")
    public ResponseEntity<List<UserSopraDTO>> getAllUserSopras(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of UserSopras");
        Page<UserSopraDTO> page = userSopraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-sopras/:id} : get the "id" userSopra.
     *
     * @param id the id of the userSopraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userSopraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-sopras/{id}")
    public ResponseEntity<UserSopraDTO> getUserSopra(@PathVariable Long id) {
        log.debug("REST request to get UserSopra : {}", id);
        Optional<UserSopraDTO> userSopraDTO = userSopraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userSopraDTO);
    }

    /**
     * {@code DELETE  /user-sopras/:id} : delete the "id" userSopra.
     *
     * @param id the id of the userSopraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-sopras/{id}")
    public ResponseEntity<Void> deleteUserSopra(@PathVariable Long id) {
        log.debug("REST request to delete UserSopra : {}", id);
        userSopraService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
