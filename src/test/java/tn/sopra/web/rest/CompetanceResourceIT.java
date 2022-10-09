package tn.sopra.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.springframework.util.Base64Utils;
import tn.sopra.IntegrationTest;
import tn.sopra.domain.Competance;
import tn.sopra.repository.CompetanceRepository;
import tn.sopra.service.dto.CompetanceDTO;
import tn.sopra.service.mapper.CompetanceMapper;

/**
 * Integration tests for the {@link CompetanceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompetanceResourceIT {

    private static final String DEFAULT_NOM_COMPETANCE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COMPETANCE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DUREE_COMPETANCE = 1;
    private static final Integer UPDATED_DUREE_COMPETANCE = 2;

    private static final Integer DEFAULT_NIVEAU_COMPETANCE = 1;
    private static final Integer UPDATED_NIVEAU_COMPETANCE = 2;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/competances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompetanceRepository competanceRepository;

    @Autowired
    private CompetanceMapper competanceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompetanceMockMvc;

    private Competance competance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competance createEntity(EntityManager em) {
        Competance competance = new Competance()
            .nomCompetance(DEFAULT_NOM_COMPETANCE)
            .dureeCompetance(DEFAULT_DUREE_COMPETANCE)
            .niveauCompetance(DEFAULT_NIVEAU_COMPETANCE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return competance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competance createUpdatedEntity(EntityManager em) {
        Competance competance = new Competance()
            .nomCompetance(UPDATED_NOM_COMPETANCE)
            .dureeCompetance(UPDATED_DUREE_COMPETANCE)
            .niveauCompetance(UPDATED_NIVEAU_COMPETANCE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return competance;
    }

    @BeforeEach
    public void initTest() {
        competance = createEntity(em);
    }

    @Test
    @Transactional
    void createCompetance() throws Exception {
        int databaseSizeBeforeCreate = competanceRepository.findAll().size();
        // Create the Competance
        CompetanceDTO competanceDTO = competanceMapper.toDto(competance);
        restCompetanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(competanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Competance in the database
        List<Competance> competanceList = competanceRepository.findAll();
        assertThat(competanceList).hasSize(databaseSizeBeforeCreate + 1);
        Competance testCompetance = competanceList.get(competanceList.size() - 1);
        assertThat(testCompetance.getNomCompetance()).isEqualTo(DEFAULT_NOM_COMPETANCE);
        assertThat(testCompetance.getDureeCompetance()).isEqualTo(DEFAULT_DUREE_COMPETANCE);
        assertThat(testCompetance.getNiveauCompetance()).isEqualTo(DEFAULT_NIVEAU_COMPETANCE);
        assertThat(testCompetance.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testCompetance.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createCompetanceWithExistingId() throws Exception {
        // Create the Competance with an existing ID
        competance.setId(1L);
        CompetanceDTO competanceDTO = competanceMapper.toDto(competance);

        int databaseSizeBeforeCreate = competanceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(competanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Competance in the database
        List<Competance> competanceList = competanceRepository.findAll();
        assertThat(competanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompetances() throws Exception {
        // Initialize the database
        competanceRepository.saveAndFlush(competance);

        // Get all the competanceList
        restCompetanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competance.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomCompetance").value(hasItem(DEFAULT_NOM_COMPETANCE)))
            .andExpect(jsonPath("$.[*].dureeCompetance").value(hasItem(DEFAULT_DUREE_COMPETANCE)))
            .andExpect(jsonPath("$.[*].niveauCompetance").value(hasItem(DEFAULT_NIVEAU_COMPETANCE)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    void getCompetance() throws Exception {
        // Initialize the database
        competanceRepository.saveAndFlush(competance);

        // Get the competance
        restCompetanceMockMvc
            .perform(get(ENTITY_API_URL_ID, competance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competance.getId().intValue()))
            .andExpect(jsonPath("$.nomCompetance").value(DEFAULT_NOM_COMPETANCE))
            .andExpect(jsonPath("$.dureeCompetance").value(DEFAULT_DUREE_COMPETANCE))
            .andExpect(jsonPath("$.niveauCompetance").value(DEFAULT_NIVEAU_COMPETANCE))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getNonExistingCompetance() throws Exception {
        // Get the competance
        restCompetanceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompetance() throws Exception {
        // Initialize the database
        competanceRepository.saveAndFlush(competance);

        int databaseSizeBeforeUpdate = competanceRepository.findAll().size();

        // Update the competance
        Competance updatedCompetance = competanceRepository.findById(competance.getId()).get();
        // Disconnect from session so that the updates on updatedCompetance are not directly saved in db
        em.detach(updatedCompetance);
        updatedCompetance
            .nomCompetance(UPDATED_NOM_COMPETANCE)
            .dureeCompetance(UPDATED_DUREE_COMPETANCE)
            .niveauCompetance(UPDATED_NIVEAU_COMPETANCE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        CompetanceDTO competanceDTO = competanceMapper.toDto(updatedCompetance);

        restCompetanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, competanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competanceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Competance in the database
        List<Competance> competanceList = competanceRepository.findAll();
        assertThat(competanceList).hasSize(databaseSizeBeforeUpdate);
        Competance testCompetance = competanceList.get(competanceList.size() - 1);
        assertThat(testCompetance.getNomCompetance()).isEqualTo(UPDATED_NOM_COMPETANCE);
        assertThat(testCompetance.getDureeCompetance()).isEqualTo(UPDATED_DUREE_COMPETANCE);
        assertThat(testCompetance.getNiveauCompetance()).isEqualTo(UPDATED_NIVEAU_COMPETANCE);
        assertThat(testCompetance.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testCompetance.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingCompetance() throws Exception {
        int databaseSizeBeforeUpdate = competanceRepository.findAll().size();
        competance.setId(count.incrementAndGet());

        // Create the Competance
        CompetanceDTO competanceDTO = competanceMapper.toDto(competance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, competanceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competance in the database
        List<Competance> competanceList = competanceRepository.findAll();
        assertThat(competanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompetance() throws Exception {
        int databaseSizeBeforeUpdate = competanceRepository.findAll().size();
        competance.setId(count.incrementAndGet());

        // Create the Competance
        CompetanceDTO competanceDTO = competanceMapper.toDto(competance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(competanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competance in the database
        List<Competance> competanceList = competanceRepository.findAll();
        assertThat(competanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompetance() throws Exception {
        int databaseSizeBeforeUpdate = competanceRepository.findAll().size();
        competance.setId(count.incrementAndGet());

        // Create the Competance
        CompetanceDTO competanceDTO = competanceMapper.toDto(competance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetanceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(competanceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competance in the database
        List<Competance> competanceList = competanceRepository.findAll();
        assertThat(competanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompetanceWithPatch() throws Exception {
        // Initialize the database
        competanceRepository.saveAndFlush(competance);

        int databaseSizeBeforeUpdate = competanceRepository.findAll().size();

        // Update the competance using partial update
        Competance partialUpdatedCompetance = new Competance();
        partialUpdatedCompetance.setId(competance.getId());

        partialUpdatedCompetance
            .dureeCompetance(UPDATED_DUREE_COMPETANCE)
            .niveauCompetance(UPDATED_NIVEAU_COMPETANCE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restCompetanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetance))
            )
            .andExpect(status().isOk());

        // Validate the Competance in the database
        List<Competance> competanceList = competanceRepository.findAll();
        assertThat(competanceList).hasSize(databaseSizeBeforeUpdate);
        Competance testCompetance = competanceList.get(competanceList.size() - 1);
        assertThat(testCompetance.getNomCompetance()).isEqualTo(DEFAULT_NOM_COMPETANCE);
        assertThat(testCompetance.getDureeCompetance()).isEqualTo(UPDATED_DUREE_COMPETANCE);
        assertThat(testCompetance.getNiveauCompetance()).isEqualTo(UPDATED_NIVEAU_COMPETANCE);
        assertThat(testCompetance.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testCompetance.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateCompetanceWithPatch() throws Exception {
        // Initialize the database
        competanceRepository.saveAndFlush(competance);

        int databaseSizeBeforeUpdate = competanceRepository.findAll().size();

        // Update the competance using partial update
        Competance partialUpdatedCompetance = new Competance();
        partialUpdatedCompetance.setId(competance.getId());

        partialUpdatedCompetance
            .nomCompetance(UPDATED_NOM_COMPETANCE)
            .dureeCompetance(UPDATED_DUREE_COMPETANCE)
            .niveauCompetance(UPDATED_NIVEAU_COMPETANCE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restCompetanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompetance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompetance))
            )
            .andExpect(status().isOk());

        // Validate the Competance in the database
        List<Competance> competanceList = competanceRepository.findAll();
        assertThat(competanceList).hasSize(databaseSizeBeforeUpdate);
        Competance testCompetance = competanceList.get(competanceList.size() - 1);
        assertThat(testCompetance.getNomCompetance()).isEqualTo(UPDATED_NOM_COMPETANCE);
        assertThat(testCompetance.getDureeCompetance()).isEqualTo(UPDATED_DUREE_COMPETANCE);
        assertThat(testCompetance.getNiveauCompetance()).isEqualTo(UPDATED_NIVEAU_COMPETANCE);
        assertThat(testCompetance.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testCompetance.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingCompetance() throws Exception {
        int databaseSizeBeforeUpdate = competanceRepository.findAll().size();
        competance.setId(count.incrementAndGet());

        // Create the Competance
        CompetanceDTO competanceDTO = competanceMapper.toDto(competance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, competanceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competance in the database
        List<Competance> competanceList = competanceRepository.findAll();
        assertThat(competanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompetance() throws Exception {
        int databaseSizeBeforeUpdate = competanceRepository.findAll().size();
        competance.setId(count.incrementAndGet());

        // Create the Competance
        CompetanceDTO competanceDTO = competanceMapper.toDto(competance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(competanceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Competance in the database
        List<Competance> competanceList = competanceRepository.findAll();
        assertThat(competanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompetance() throws Exception {
        int databaseSizeBeforeUpdate = competanceRepository.findAll().size();
        competance.setId(count.incrementAndGet());

        // Create the Competance
        CompetanceDTO competanceDTO = competanceMapper.toDto(competance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompetanceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(competanceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Competance in the database
        List<Competance> competanceList = competanceRepository.findAll();
        assertThat(competanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompetance() throws Exception {
        // Initialize the database
        competanceRepository.saveAndFlush(competance);

        int databaseSizeBeforeDelete = competanceRepository.findAll().size();

        // Delete the competance
        restCompetanceMockMvc
            .perform(delete(ENTITY_API_URL_ID, competance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Competance> competanceList = competanceRepository.findAll();
        assertThat(competanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
