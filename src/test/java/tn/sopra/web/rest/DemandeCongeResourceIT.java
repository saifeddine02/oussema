package tn.sopra.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tn.sopra.IntegrationTest;
import tn.sopra.domain.DemandeConge;
import tn.sopra.domain.enumeration.StatusDemande;
import tn.sopra.repository.DemandeCongeRepository;
import tn.sopra.service.dto.DemandeCongeDTO;
import tn.sopra.service.mapper.DemandeCongeMapper;

/**
 * Integration tests for the {@link DemandeCongeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemandeCongeResourceIT {

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_AVIS_TL = false;
    private static final Boolean UPDATED_AVIS_TL = true;

    private static final StatusDemande DEFAULT_STATUS_DEMANDE = StatusDemande.ENCOURS;
    private static final StatusDemande UPDATED_STATUS_DEMANDE = StatusDemande.TRAITE;

    private static final Instant DEFAULT_DATE_DEBUT_CONGE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DEBUT_CONGE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_FIN_CONGE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_FIN_CONGE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_NOMBRE_JOUR = 1;
    private static final Integer UPDATED_NOMBRE_JOUR = 2;

    private static final String ENTITY_API_URL = "/api/demande-conges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandeCongeRepository demandeCongeRepository;

    @Autowired
    private DemandeCongeMapper demandeCongeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandeCongeMockMvc;

    private DemandeConge demandeConge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeConge createEntity(EntityManager em) {
        DemandeConge demandeConge = new DemandeConge()
            .creationDate(DEFAULT_CREATION_DATE)
            .avisTl(DEFAULT_AVIS_TL)
            .statusDemande(DEFAULT_STATUS_DEMANDE)
            .dateDebutConge(DEFAULT_DATE_DEBUT_CONGE)
            .dateFinConge(DEFAULT_DATE_FIN_CONGE)
            .nombreJour(DEFAULT_NOMBRE_JOUR);
        return demandeConge;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeConge createUpdatedEntity(EntityManager em) {
        DemandeConge demandeConge = new DemandeConge()
            .creationDate(UPDATED_CREATION_DATE)
            .avisTl(UPDATED_AVIS_TL)
            .statusDemande(UPDATED_STATUS_DEMANDE)
            .dateDebutConge(UPDATED_DATE_DEBUT_CONGE)
            .dateFinConge(UPDATED_DATE_FIN_CONGE)
            .nombreJour(UPDATED_NOMBRE_JOUR);
        return demandeConge;
    }

    @BeforeEach
    public void initTest() {
        demandeConge = createEntity(em);
    }

    @Test
    @Transactional
    void createDemandeConge() throws Exception {
        int databaseSizeBeforeCreate = demandeCongeRepository.findAll().size();
        // Create the DemandeConge
        DemandeCongeDTO demandeCongeDTO = demandeCongeMapper.toDto(demandeConge);
        restDemandeCongeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeCongeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DemandeConge in the database
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeCreate + 1);
        DemandeConge testDemandeConge = demandeCongeList.get(demandeCongeList.size() - 1);
        assertThat(testDemandeConge.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testDemandeConge.getAvisTl()).isEqualTo(DEFAULT_AVIS_TL);
        assertThat(testDemandeConge.getStatusDemande()).isEqualTo(DEFAULT_STATUS_DEMANDE);
        assertThat(testDemandeConge.getDateDebutConge()).isEqualTo(DEFAULT_DATE_DEBUT_CONGE);
        assertThat(testDemandeConge.getDateFinConge()).isEqualTo(DEFAULT_DATE_FIN_CONGE);
        assertThat(testDemandeConge.getNombreJour()).isEqualTo(DEFAULT_NOMBRE_JOUR);
    }

    @Test
    @Transactional
    void createDemandeCongeWithExistingId() throws Exception {
        // Create the DemandeConge with an existing ID
        demandeConge.setId(1L);
        DemandeCongeDTO demandeCongeDTO = demandeCongeMapper.toDto(demandeConge);

        int databaseSizeBeforeCreate = demandeCongeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandeCongeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeCongeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeConge in the database
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDemandeConges() throws Exception {
        // Initialize the database
        demandeCongeRepository.saveAndFlush(demandeConge);

        // Get all the demandeCongeList
        restDemandeCongeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandeConge.getId().intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].avisTl").value(hasItem(DEFAULT_AVIS_TL.booleanValue())))
            .andExpect(jsonPath("$.[*].statusDemande").value(hasItem(DEFAULT_STATUS_DEMANDE.toString())))
            .andExpect(jsonPath("$.[*].dateDebutConge").value(hasItem(DEFAULT_DATE_DEBUT_CONGE.toString())))
            .andExpect(jsonPath("$.[*].dateFinConge").value(hasItem(DEFAULT_DATE_FIN_CONGE.toString())))
            .andExpect(jsonPath("$.[*].nombreJour").value(hasItem(DEFAULT_NOMBRE_JOUR)));
    }

    @Test
    @Transactional
    void getDemandeConge() throws Exception {
        // Initialize the database
        demandeCongeRepository.saveAndFlush(demandeConge);

        // Get the demandeConge
        restDemandeCongeMockMvc
            .perform(get(ENTITY_API_URL_ID, demandeConge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandeConge.getId().intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.avisTl").value(DEFAULT_AVIS_TL.booleanValue()))
            .andExpect(jsonPath("$.statusDemande").value(DEFAULT_STATUS_DEMANDE.toString()))
            .andExpect(jsonPath("$.dateDebutConge").value(DEFAULT_DATE_DEBUT_CONGE.toString()))
            .andExpect(jsonPath("$.dateFinConge").value(DEFAULT_DATE_FIN_CONGE.toString()))
            .andExpect(jsonPath("$.nombreJour").value(DEFAULT_NOMBRE_JOUR));
    }

    @Test
    @Transactional
    void getNonExistingDemandeConge() throws Exception {
        // Get the demandeConge
        restDemandeCongeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDemandeConge() throws Exception {
        // Initialize the database
        demandeCongeRepository.saveAndFlush(demandeConge);

        int databaseSizeBeforeUpdate = demandeCongeRepository.findAll().size();

        // Update the demandeConge
        DemandeConge updatedDemandeConge = demandeCongeRepository.findById(demandeConge.getId()).get();
        // Disconnect from session so that the updates on updatedDemandeConge are not directly saved in db
        em.detach(updatedDemandeConge);
        updatedDemandeConge
            .creationDate(UPDATED_CREATION_DATE)
            .avisTl(UPDATED_AVIS_TL)
            .statusDemande(UPDATED_STATUS_DEMANDE)
            .dateDebutConge(UPDATED_DATE_DEBUT_CONGE)
            .dateFinConge(UPDATED_DATE_FIN_CONGE)
            .nombreJour(UPDATED_NOMBRE_JOUR);
        DemandeCongeDTO demandeCongeDTO = demandeCongeMapper.toDto(updatedDemandeConge);

        restDemandeCongeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandeCongeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeCongeDTO))
            )
            .andExpect(status().isOk());

        // Validate the DemandeConge in the database
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeUpdate);
        DemandeConge testDemandeConge = demandeCongeList.get(demandeCongeList.size() - 1);
        assertThat(testDemandeConge.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testDemandeConge.getAvisTl()).isEqualTo(UPDATED_AVIS_TL);
        assertThat(testDemandeConge.getStatusDemande()).isEqualTo(UPDATED_STATUS_DEMANDE);
        assertThat(testDemandeConge.getDateDebutConge()).isEqualTo(UPDATED_DATE_DEBUT_CONGE);
        assertThat(testDemandeConge.getDateFinConge()).isEqualTo(UPDATED_DATE_FIN_CONGE);
        assertThat(testDemandeConge.getNombreJour()).isEqualTo(UPDATED_NOMBRE_JOUR);
    }

    @Test
    @Transactional
    void putNonExistingDemandeConge() throws Exception {
        int databaseSizeBeforeUpdate = demandeCongeRepository.findAll().size();
        demandeConge.setId(count.incrementAndGet());

        // Create the DemandeConge
        DemandeCongeDTO demandeCongeDTO = demandeCongeMapper.toDto(demandeConge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeCongeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandeCongeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeCongeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeConge in the database
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandeConge() throws Exception {
        int databaseSizeBeforeUpdate = demandeCongeRepository.findAll().size();
        demandeConge.setId(count.incrementAndGet());

        // Create the DemandeConge
        DemandeCongeDTO demandeCongeDTO = demandeCongeMapper.toDto(demandeConge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeCongeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeCongeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeConge in the database
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandeConge() throws Exception {
        int databaseSizeBeforeUpdate = demandeCongeRepository.findAll().size();
        demandeConge.setId(count.incrementAndGet());

        // Create the DemandeConge
        DemandeCongeDTO demandeCongeDTO = demandeCongeMapper.toDto(demandeConge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeCongeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeCongeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeConge in the database
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandeCongeWithPatch() throws Exception {
        // Initialize the database
        demandeCongeRepository.saveAndFlush(demandeConge);

        int databaseSizeBeforeUpdate = demandeCongeRepository.findAll().size();

        // Update the demandeConge using partial update
        DemandeConge partialUpdatedDemandeConge = new DemandeConge();
        partialUpdatedDemandeConge.setId(demandeConge.getId());

        partialUpdatedDemandeConge
            .statusDemande(UPDATED_STATUS_DEMANDE)
            .dateFinConge(UPDATED_DATE_FIN_CONGE)
            .nombreJour(UPDATED_NOMBRE_JOUR);

        restDemandeCongeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeConge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeConge))
            )
            .andExpect(status().isOk());

        // Validate the DemandeConge in the database
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeUpdate);
        DemandeConge testDemandeConge = demandeCongeList.get(demandeCongeList.size() - 1);
        assertThat(testDemandeConge.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testDemandeConge.getAvisTl()).isEqualTo(DEFAULT_AVIS_TL);
        assertThat(testDemandeConge.getStatusDemande()).isEqualTo(UPDATED_STATUS_DEMANDE);
        assertThat(testDemandeConge.getDateDebutConge()).isEqualTo(DEFAULT_DATE_DEBUT_CONGE);
        assertThat(testDemandeConge.getDateFinConge()).isEqualTo(UPDATED_DATE_FIN_CONGE);
        assertThat(testDemandeConge.getNombreJour()).isEqualTo(UPDATED_NOMBRE_JOUR);
    }

    @Test
    @Transactional
    void fullUpdateDemandeCongeWithPatch() throws Exception {
        // Initialize the database
        demandeCongeRepository.saveAndFlush(demandeConge);

        int databaseSizeBeforeUpdate = demandeCongeRepository.findAll().size();

        // Update the demandeConge using partial update
        DemandeConge partialUpdatedDemandeConge = new DemandeConge();
        partialUpdatedDemandeConge.setId(demandeConge.getId());

        partialUpdatedDemandeConge
            .creationDate(UPDATED_CREATION_DATE)
            .avisTl(UPDATED_AVIS_TL)
            .statusDemande(UPDATED_STATUS_DEMANDE)
            .dateDebutConge(UPDATED_DATE_DEBUT_CONGE)
            .dateFinConge(UPDATED_DATE_FIN_CONGE)
            .nombreJour(UPDATED_NOMBRE_JOUR);

        restDemandeCongeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeConge.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeConge))
            )
            .andExpect(status().isOk());

        // Validate the DemandeConge in the database
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeUpdate);
        DemandeConge testDemandeConge = demandeCongeList.get(demandeCongeList.size() - 1);
        assertThat(testDemandeConge.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testDemandeConge.getAvisTl()).isEqualTo(UPDATED_AVIS_TL);
        assertThat(testDemandeConge.getStatusDemande()).isEqualTo(UPDATED_STATUS_DEMANDE);
        assertThat(testDemandeConge.getDateDebutConge()).isEqualTo(UPDATED_DATE_DEBUT_CONGE);
        assertThat(testDemandeConge.getDateFinConge()).isEqualTo(UPDATED_DATE_FIN_CONGE);
        assertThat(testDemandeConge.getNombreJour()).isEqualTo(UPDATED_NOMBRE_JOUR);
    }

    @Test
    @Transactional
    void patchNonExistingDemandeConge() throws Exception {
        int databaseSizeBeforeUpdate = demandeCongeRepository.findAll().size();
        demandeConge.setId(count.incrementAndGet());

        // Create the DemandeConge
        DemandeCongeDTO demandeCongeDTO = demandeCongeMapper.toDto(demandeConge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeCongeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandeCongeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeCongeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeConge in the database
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandeConge() throws Exception {
        int databaseSizeBeforeUpdate = demandeCongeRepository.findAll().size();
        demandeConge.setId(count.incrementAndGet());

        // Create the DemandeConge
        DemandeCongeDTO demandeCongeDTO = demandeCongeMapper.toDto(demandeConge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeCongeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeCongeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeConge in the database
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandeConge() throws Exception {
        int databaseSizeBeforeUpdate = demandeCongeRepository.findAll().size();
        demandeConge.setId(count.incrementAndGet());

        // Create the DemandeConge
        DemandeCongeDTO demandeCongeDTO = demandeCongeMapper.toDto(demandeConge);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeCongeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeCongeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeConge in the database
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandeConge() throws Exception {
        // Initialize the database
        demandeCongeRepository.saveAndFlush(demandeConge);

        int databaseSizeBeforeDelete = demandeCongeRepository.findAll().size();

        // Delete the demandeConge
        restDemandeCongeMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandeConge.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DemandeConge> demandeCongeList = demandeCongeRepository.findAll();
        assertThat(demandeCongeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
