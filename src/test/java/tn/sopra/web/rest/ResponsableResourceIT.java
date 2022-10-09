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
import tn.sopra.IntegrationTest;
import tn.sopra.domain.Responsable;
import tn.sopra.repository.ResponsableRepository;
import tn.sopra.service.dto.ResponsableDTO;
import tn.sopra.service.mapper.ResponsableMapper;

/**
 * Integration tests for the {@link ResponsableResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResponsableResourceIT {

    private static final String DEFAULT_NOM_RESPONSABLE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_RESPONSABLE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_RESPONSABLE = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_RESPONSABLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/responsables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResponsableRepository responsableRepository;

    @Autowired
    private ResponsableMapper responsableMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResponsableMockMvc;

    private Responsable responsable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Responsable createEntity(EntityManager em) {
        Responsable responsable = new Responsable().nomResponsable(DEFAULT_NOM_RESPONSABLE).prenomResponsable(DEFAULT_PRENOM_RESPONSABLE);
        return responsable;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Responsable createUpdatedEntity(EntityManager em) {
        Responsable responsable = new Responsable().nomResponsable(UPDATED_NOM_RESPONSABLE).prenomResponsable(UPDATED_PRENOM_RESPONSABLE);
        return responsable;
    }

    @BeforeEach
    public void initTest() {
        responsable = createEntity(em);
    }

    @Test
    @Transactional
    void createResponsable() throws Exception {
        int databaseSizeBeforeCreate = responsableRepository.findAll().size();
        // Create the Responsable
        ResponsableDTO responsableDTO = responsableMapper.toDto(responsable);
        restResponsableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsableDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Responsable in the database
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeCreate + 1);
        Responsable testResponsable = responsableList.get(responsableList.size() - 1);
        assertThat(testResponsable.getNomResponsable()).isEqualTo(DEFAULT_NOM_RESPONSABLE);
        assertThat(testResponsable.getPrenomResponsable()).isEqualTo(DEFAULT_PRENOM_RESPONSABLE);
    }

    @Test
    @Transactional
    void createResponsableWithExistingId() throws Exception {
        // Create the Responsable with an existing ID
        responsable.setId(1L);
        ResponsableDTO responsableDTO = responsableMapper.toDto(responsable);

        int databaseSizeBeforeCreate = responsableRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Responsable in the database
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllResponsables() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList
        restResponsableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsable.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomResponsable").value(hasItem(DEFAULT_NOM_RESPONSABLE)))
            .andExpect(jsonPath("$.[*].prenomResponsable").value(hasItem(DEFAULT_PRENOM_RESPONSABLE)));
    }

    @Test
    @Transactional
    void getResponsable() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get the responsable
        restResponsableMockMvc
            .perform(get(ENTITY_API_URL_ID, responsable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(responsable.getId().intValue()))
            .andExpect(jsonPath("$.nomResponsable").value(DEFAULT_NOM_RESPONSABLE))
            .andExpect(jsonPath("$.prenomResponsable").value(DEFAULT_PRENOM_RESPONSABLE));
    }

    @Test
    @Transactional
    void getNonExistingResponsable() throws Exception {
        // Get the responsable
        restResponsableMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResponsable() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        int databaseSizeBeforeUpdate = responsableRepository.findAll().size();

        // Update the responsable
        Responsable updatedResponsable = responsableRepository.findById(responsable.getId()).get();
        // Disconnect from session so that the updates on updatedResponsable are not directly saved in db
        em.detach(updatedResponsable);
        updatedResponsable.nomResponsable(UPDATED_NOM_RESPONSABLE).prenomResponsable(UPDATED_PRENOM_RESPONSABLE);
        ResponsableDTO responsableDTO = responsableMapper.toDto(updatedResponsable);

        restResponsableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responsableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsableDTO))
            )
            .andExpect(status().isOk());

        // Validate the Responsable in the database
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeUpdate);
        Responsable testResponsable = responsableList.get(responsableList.size() - 1);
        assertThat(testResponsable.getNomResponsable()).isEqualTo(UPDATED_NOM_RESPONSABLE);
        assertThat(testResponsable.getPrenomResponsable()).isEqualTo(UPDATED_PRENOM_RESPONSABLE);
    }

    @Test
    @Transactional
    void putNonExistingResponsable() throws Exception {
        int databaseSizeBeforeUpdate = responsableRepository.findAll().size();
        responsable.setId(count.incrementAndGet());

        // Create the Responsable
        ResponsableDTO responsableDTO = responsableMapper.toDto(responsable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, responsableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Responsable in the database
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResponsable() throws Exception {
        int databaseSizeBeforeUpdate = responsableRepository.findAll().size();
        responsable.setId(count.incrementAndGet());

        // Create the Responsable
        ResponsableDTO responsableDTO = responsableMapper.toDto(responsable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(responsableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Responsable in the database
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResponsable() throws Exception {
        int databaseSizeBeforeUpdate = responsableRepository.findAll().size();
        responsable.setId(count.incrementAndGet());

        // Create the Responsable
        ResponsableDTO responsableDTO = responsableMapper.toDto(responsable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsableMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(responsableDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Responsable in the database
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResponsableWithPatch() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        int databaseSizeBeforeUpdate = responsableRepository.findAll().size();

        // Update the responsable using partial update
        Responsable partialUpdatedResponsable = new Responsable();
        partialUpdatedResponsable.setId(responsable.getId());

        partialUpdatedResponsable.nomResponsable(UPDATED_NOM_RESPONSABLE);

        restResponsableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponsable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponsable))
            )
            .andExpect(status().isOk());

        // Validate the Responsable in the database
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeUpdate);
        Responsable testResponsable = responsableList.get(responsableList.size() - 1);
        assertThat(testResponsable.getNomResponsable()).isEqualTo(UPDATED_NOM_RESPONSABLE);
        assertThat(testResponsable.getPrenomResponsable()).isEqualTo(DEFAULT_PRENOM_RESPONSABLE);
    }

    @Test
    @Transactional
    void fullUpdateResponsableWithPatch() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        int databaseSizeBeforeUpdate = responsableRepository.findAll().size();

        // Update the responsable using partial update
        Responsable partialUpdatedResponsable = new Responsable();
        partialUpdatedResponsable.setId(responsable.getId());

        partialUpdatedResponsable.nomResponsable(UPDATED_NOM_RESPONSABLE).prenomResponsable(UPDATED_PRENOM_RESPONSABLE);

        restResponsableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResponsable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResponsable))
            )
            .andExpect(status().isOk());

        // Validate the Responsable in the database
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeUpdate);
        Responsable testResponsable = responsableList.get(responsableList.size() - 1);
        assertThat(testResponsable.getNomResponsable()).isEqualTo(UPDATED_NOM_RESPONSABLE);
        assertThat(testResponsable.getPrenomResponsable()).isEqualTo(UPDATED_PRENOM_RESPONSABLE);
    }

    @Test
    @Transactional
    void patchNonExistingResponsable() throws Exception {
        int databaseSizeBeforeUpdate = responsableRepository.findAll().size();
        responsable.setId(count.incrementAndGet());

        // Create the Responsable
        ResponsableDTO responsableDTO = responsableMapper.toDto(responsable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, responsableDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Responsable in the database
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResponsable() throws Exception {
        int databaseSizeBeforeUpdate = responsableRepository.findAll().size();
        responsable.setId(count.incrementAndGet());

        // Create the Responsable
        ResponsableDTO responsableDTO = responsableMapper.toDto(responsable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(responsableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Responsable in the database
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResponsable() throws Exception {
        int databaseSizeBeforeUpdate = responsableRepository.findAll().size();
        responsable.setId(count.incrementAndGet());

        // Create the Responsable
        ResponsableDTO responsableDTO = responsableMapper.toDto(responsable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResponsableMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(responsableDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Responsable in the database
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResponsable() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        int databaseSizeBeforeDelete = responsableRepository.findAll().size();

        // Delete the responsable
        restResponsableMockMvc
            .perform(delete(ENTITY_API_URL_ID, responsable.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
