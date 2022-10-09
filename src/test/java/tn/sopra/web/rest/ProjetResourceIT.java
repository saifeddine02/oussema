package tn.sopra.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tn.sopra.IntegrationTest;
import tn.sopra.domain.Projet;
import tn.sopra.repository.ProjetRepository;
import tn.sopra.service.ProjetService;
import tn.sopra.service.dto.ProjetDTO;
import tn.sopra.service.mapper.ProjetMapper;

/**
 * Integration tests for the {@link ProjetResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProjetResourceIT {

    private static final String DEFAULT_NOM_PROJET = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PROJET = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS_PROJET = "AAAAAAAAAA";
    private static final String UPDATED_PAYS_PROJET = "BBBBBBBBBB";

    private static final String DEFAULT_REGION_PROJET = "AAAAAAAAAA";
    private static final String UPDATED_REGION_PROJET = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_DEBUT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DEBUT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_NOMBRE_RESSOURCE = 1;
    private static final Integer UPDATED_NOMBRE_RESSOURCE = 2;

    private static final Instant DEFAULT_DATE_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMPETANCE_DEMANDER = "AAAAAAAAAA";
    private static final String UPDATED_COMPETANCE_DEMANDER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/projets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjetRepository projetRepository;

    @Mock
    private ProjetRepository projetRepositoryMock;

    @Autowired
    private ProjetMapper projetMapper;

    @Mock
    private ProjetService projetServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjetMockMvc;

    private Projet projet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projet createEntity(EntityManager em) {
        Projet projet = new Projet()
            .nomProjet(DEFAULT_NOM_PROJET)
            .paysProjet(DEFAULT_PAYS_PROJET)
            .regionProjet(DEFAULT_REGION_PROJET)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .nombreRessource(DEFAULT_NOMBRE_RESSOURCE)
            .dateFin(DEFAULT_DATE_FIN)
            .competanceDemander(DEFAULT_COMPETANCE_DEMANDER);
        return projet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projet createUpdatedEntity(EntityManager em) {
        Projet projet = new Projet()
            .nomProjet(UPDATED_NOM_PROJET)
            .paysProjet(UPDATED_PAYS_PROJET)
            .regionProjet(UPDATED_REGION_PROJET)
            .dateDebut(UPDATED_DATE_DEBUT)
            .nombreRessource(UPDATED_NOMBRE_RESSOURCE)
            .dateFin(UPDATED_DATE_FIN)
            .competanceDemander(UPDATED_COMPETANCE_DEMANDER);
        return projet;
    }

    @BeforeEach
    public void initTest() {
        projet = createEntity(em);
    }

    @Test
    @Transactional
    void createProjet() throws Exception {
        int databaseSizeBeforeCreate = projetRepository.findAll().size();
        // Create the Projet
        ProjetDTO projetDTO = projetMapper.toDto(projet);
        restProjetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projetDTO)))
            .andExpect(status().isCreated());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeCreate + 1);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getNomProjet()).isEqualTo(DEFAULT_NOM_PROJET);
        assertThat(testProjet.getPaysProjet()).isEqualTo(DEFAULT_PAYS_PROJET);
        assertThat(testProjet.getRegionProjet()).isEqualTo(DEFAULT_REGION_PROJET);
        assertThat(testProjet.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testProjet.getNombreRessource()).isEqualTo(DEFAULT_NOMBRE_RESSOURCE);
        assertThat(testProjet.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testProjet.getCompetanceDemander()).isEqualTo(DEFAULT_COMPETANCE_DEMANDER);
    }

    @Test
    @Transactional
    void createProjetWithExistingId() throws Exception {
        // Create the Projet with an existing ID
        projet.setId(1L);
        ProjetDTO projetDTO = projetMapper.toDto(projet);

        int databaseSizeBeforeCreate = projetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjets() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get all the projetList
        restProjetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projet.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomProjet").value(hasItem(DEFAULT_NOM_PROJET)))
            .andExpect(jsonPath("$.[*].paysProjet").value(hasItem(DEFAULT_PAYS_PROJET)))
            .andExpect(jsonPath("$.[*].regionProjet").value(hasItem(DEFAULT_REGION_PROJET)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].nombreRessource").value(hasItem(DEFAULT_NOMBRE_RESSOURCE)))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].competanceDemander").value(hasItem(DEFAULT_COMPETANCE_DEMANDER)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProjetsWithEagerRelationshipsIsEnabled() throws Exception {
        when(projetServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProjetMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(projetServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProjetsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(projetServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProjetMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(projetRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProjet() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        // Get the projet
        restProjetMockMvc
            .perform(get(ENTITY_API_URL_ID, projet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projet.getId().intValue()))
            .andExpect(jsonPath("$.nomProjet").value(DEFAULT_NOM_PROJET))
            .andExpect(jsonPath("$.paysProjet").value(DEFAULT_PAYS_PROJET))
            .andExpect(jsonPath("$.regionProjet").value(DEFAULT_REGION_PROJET))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.nombreRessource").value(DEFAULT_NOMBRE_RESSOURCE))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.competanceDemander").value(DEFAULT_COMPETANCE_DEMANDER));
    }

    @Test
    @Transactional
    void getNonExistingProjet() throws Exception {
        // Get the projet
        restProjetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProjet() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        int databaseSizeBeforeUpdate = projetRepository.findAll().size();

        // Update the projet
        Projet updatedProjet = projetRepository.findById(projet.getId()).get();
        // Disconnect from session so that the updates on updatedProjet are not directly saved in db
        em.detach(updatedProjet);
        updatedProjet
            .nomProjet(UPDATED_NOM_PROJET)
            .paysProjet(UPDATED_PAYS_PROJET)
            .regionProjet(UPDATED_REGION_PROJET)
            .dateDebut(UPDATED_DATE_DEBUT)
            .nombreRessource(UPDATED_NOMBRE_RESSOURCE)
            .dateFin(UPDATED_DATE_FIN)
            .competanceDemander(UPDATED_COMPETANCE_DEMANDER);
        ProjetDTO projetDTO = projetMapper.toDto(updatedProjet);

        restProjetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projetDTO))
            )
            .andExpect(status().isOk());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getNomProjet()).isEqualTo(UPDATED_NOM_PROJET);
        assertThat(testProjet.getPaysProjet()).isEqualTo(UPDATED_PAYS_PROJET);
        assertThat(testProjet.getRegionProjet()).isEqualTo(UPDATED_REGION_PROJET);
        assertThat(testProjet.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testProjet.getNombreRessource()).isEqualTo(UPDATED_NOMBRE_RESSOURCE);
        assertThat(testProjet.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testProjet.getCompetanceDemander()).isEqualTo(UPDATED_COMPETANCE_DEMANDER);
    }

    @Test
    @Transactional
    void putNonExistingProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();
        projet.setId(count.incrementAndGet());

        // Create the Projet
        ProjetDTO projetDTO = projetMapper.toDto(projet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();
        projet.setId(count.incrementAndGet());

        // Create the Projet
        ProjetDTO projetDTO = projetMapper.toDto(projet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();
        projet.setId(count.incrementAndGet());

        // Create the Projet
        ProjetDTO projetDTO = projetMapper.toDto(projet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(projetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjetWithPatch() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        int databaseSizeBeforeUpdate = projetRepository.findAll().size();

        // Update the projet using partial update
        Projet partialUpdatedProjet = new Projet();
        partialUpdatedProjet.setId(projet.getId());

        partialUpdatedProjet.regionProjet(UPDATED_REGION_PROJET);

        restProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjet))
            )
            .andExpect(status().isOk());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getNomProjet()).isEqualTo(DEFAULT_NOM_PROJET);
        assertThat(testProjet.getPaysProjet()).isEqualTo(DEFAULT_PAYS_PROJET);
        assertThat(testProjet.getRegionProjet()).isEqualTo(UPDATED_REGION_PROJET);
        assertThat(testProjet.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testProjet.getNombreRessource()).isEqualTo(DEFAULT_NOMBRE_RESSOURCE);
        assertThat(testProjet.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testProjet.getCompetanceDemander()).isEqualTo(DEFAULT_COMPETANCE_DEMANDER);
    }

    @Test
    @Transactional
    void fullUpdateProjetWithPatch() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        int databaseSizeBeforeUpdate = projetRepository.findAll().size();

        // Update the projet using partial update
        Projet partialUpdatedProjet = new Projet();
        partialUpdatedProjet.setId(projet.getId());

        partialUpdatedProjet
            .nomProjet(UPDATED_NOM_PROJET)
            .paysProjet(UPDATED_PAYS_PROJET)
            .regionProjet(UPDATED_REGION_PROJET)
            .dateDebut(UPDATED_DATE_DEBUT)
            .nombreRessource(UPDATED_NOMBRE_RESSOURCE)
            .dateFin(UPDATED_DATE_FIN)
            .competanceDemander(UPDATED_COMPETANCE_DEMANDER);

        restProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjet.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjet))
            )
            .andExpect(status().isOk());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
        Projet testProjet = projetList.get(projetList.size() - 1);
        assertThat(testProjet.getNomProjet()).isEqualTo(UPDATED_NOM_PROJET);
        assertThat(testProjet.getPaysProjet()).isEqualTo(UPDATED_PAYS_PROJET);
        assertThat(testProjet.getRegionProjet()).isEqualTo(UPDATED_REGION_PROJET);
        assertThat(testProjet.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testProjet.getNombreRessource()).isEqualTo(UPDATED_NOMBRE_RESSOURCE);
        assertThat(testProjet.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testProjet.getCompetanceDemander()).isEqualTo(UPDATED_COMPETANCE_DEMANDER);
    }

    @Test
    @Transactional
    void patchNonExistingProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();
        projet.setId(count.incrementAndGet());

        // Create the Projet
        ProjetDTO projetDTO = projetMapper.toDto(projet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projetDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();
        projet.setId(count.incrementAndGet());

        // Create the Projet
        ProjetDTO projetDTO = projetMapper.toDto(projet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjet() throws Exception {
        int databaseSizeBeforeUpdate = projetRepository.findAll().size();
        projet.setId(count.incrementAndGet());

        // Create the Projet
        ProjetDTO projetDTO = projetMapper.toDto(projet);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjetMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(projetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Projet in the database
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjet() throws Exception {
        // Initialize the database
        projetRepository.saveAndFlush(projet);

        int databaseSizeBeforeDelete = projetRepository.findAll().size();

        // Delete the projet
        restProjetMockMvc
            .perform(delete(ENTITY_API_URL_ID, projet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Projet> projetList = projetRepository.findAll();
        assertThat(projetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
