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
import tn.sopra.domain.UserSopra;
import tn.sopra.domain.enumeration.UserRolesSopra;
import tn.sopra.repository.UserSopraRepository;
import tn.sopra.service.dto.UserSopraDTO;
import tn.sopra.service.mapper.UserSopraMapper;

/**
 * Integration tests for the {@link UserSopraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserSopraResourceIT {

    private static final String DEFAULT_NOM_USER = "AAAAAAAAAA";
    private static final String UPDATED_NOM_USER = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_USER = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_USER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_USER = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_USER = "BBBBBBBBBB";

    private static final String DEFAULT_MATRICULE_USER = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE_USER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPONIBILITE_USER = false;
    private static final Boolean UPDATED_DISPONIBILITE_USER = true;

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final UserRolesSopra DEFAULT_USER_ROLES_SOPRA = UserRolesSopra.COLLABORATEUR;
    private static final UserRolesSopra UPDATED_USER_ROLES_SOPRA = UserRolesSopra.CHEF_DE_PROJET;

    private static final String ENTITY_API_URL = "/api/user-sopras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserSopraRepository userSopraRepository;

    @Autowired
    private UserSopraMapper userSopraMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserSopraMockMvc;

    private UserSopra userSopra;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSopra createEntity(EntityManager em) {
        UserSopra userSopra = new UserSopra()
            .nomUser(DEFAULT_NOM_USER)
            .prenomUser(DEFAULT_PRENOM_USER)
            .emailUser(DEFAULT_EMAIL_USER)
            .matriculeUser(DEFAULT_MATRICULE_USER)
            .disponibiliteUser(DEFAULT_DISPONIBILITE_USER)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .userRolesSopra(DEFAULT_USER_ROLES_SOPRA);
        return userSopra;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSopra createUpdatedEntity(EntityManager em) {
        UserSopra userSopra = new UserSopra()
            .nomUser(UPDATED_NOM_USER)
            .prenomUser(UPDATED_PRENOM_USER)
            .emailUser(UPDATED_EMAIL_USER)
            .matriculeUser(UPDATED_MATRICULE_USER)
            .disponibiliteUser(UPDATED_DISPONIBILITE_USER)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .userRolesSopra(UPDATED_USER_ROLES_SOPRA);
        return userSopra;
    }

    @BeforeEach
    public void initTest() {
        userSopra = createEntity(em);
    }

    @Test
    @Transactional
    void createUserSopra() throws Exception {
        int databaseSizeBeforeCreate = userSopraRepository.findAll().size();
        // Create the UserSopra
        UserSopraDTO userSopraDTO = userSopraMapper.toDto(userSopra);
        restUserSopraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSopraDTO)))
            .andExpect(status().isCreated());

        // Validate the UserSopra in the database
        List<UserSopra> userSopraList = userSopraRepository.findAll();
        assertThat(userSopraList).hasSize(databaseSizeBeforeCreate + 1);
        UserSopra testUserSopra = userSopraList.get(userSopraList.size() - 1);
        assertThat(testUserSopra.getNomUser()).isEqualTo(DEFAULT_NOM_USER);
        assertThat(testUserSopra.getPrenomUser()).isEqualTo(DEFAULT_PRENOM_USER);
        assertThat(testUserSopra.getEmailUser()).isEqualTo(DEFAULT_EMAIL_USER);
        assertThat(testUserSopra.getMatriculeUser()).isEqualTo(DEFAULT_MATRICULE_USER);
        assertThat(testUserSopra.getDisponibiliteUser()).isEqualTo(DEFAULT_DISPONIBILITE_USER);
        assertThat(testUserSopra.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testUserSopra.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testUserSopra.getUserRolesSopra()).isEqualTo(DEFAULT_USER_ROLES_SOPRA);
    }

    @Test
    @Transactional
    void createUserSopraWithExistingId() throws Exception {
        // Create the UserSopra with an existing ID
        userSopra.setId(1L);
        UserSopraDTO userSopraDTO = userSopraMapper.toDto(userSopra);

        int databaseSizeBeforeCreate = userSopraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserSopraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSopraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserSopra in the database
        List<UserSopra> userSopraList = userSopraRepository.findAll();
        assertThat(userSopraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserSopras() throws Exception {
        // Initialize the database
        userSopraRepository.saveAndFlush(userSopra);

        // Get all the userSopraList
        restUserSopraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userSopra.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomUser").value(hasItem(DEFAULT_NOM_USER)))
            .andExpect(jsonPath("$.[*].prenomUser").value(hasItem(DEFAULT_PRENOM_USER)))
            .andExpect(jsonPath("$.[*].emailUser").value(hasItem(DEFAULT_EMAIL_USER)))
            .andExpect(jsonPath("$.[*].matriculeUser").value(hasItem(DEFAULT_MATRICULE_USER)))
            .andExpect(jsonPath("$.[*].disponibiliteUser").value(hasItem(DEFAULT_DISPONIBILITE_USER.booleanValue())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].userRolesSopra").value(hasItem(DEFAULT_USER_ROLES_SOPRA.toString())));
    }

    @Test
    @Transactional
    void getUserSopra() throws Exception {
        // Initialize the database
        userSopraRepository.saveAndFlush(userSopra);

        // Get the userSopra
        restUserSopraMockMvc
            .perform(get(ENTITY_API_URL_ID, userSopra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userSopra.getId().intValue()))
            .andExpect(jsonPath("$.nomUser").value(DEFAULT_NOM_USER))
            .andExpect(jsonPath("$.prenomUser").value(DEFAULT_PRENOM_USER))
            .andExpect(jsonPath("$.emailUser").value(DEFAULT_EMAIL_USER))
            .andExpect(jsonPath("$.matriculeUser").value(DEFAULT_MATRICULE_USER))
            .andExpect(jsonPath("$.disponibiliteUser").value(DEFAULT_DISPONIBILITE_USER.booleanValue()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.userRolesSopra").value(DEFAULT_USER_ROLES_SOPRA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUserSopra() throws Exception {
        // Get the userSopra
        restUserSopraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserSopra() throws Exception {
        // Initialize the database
        userSopraRepository.saveAndFlush(userSopra);

        int databaseSizeBeforeUpdate = userSopraRepository.findAll().size();

        // Update the userSopra
        UserSopra updatedUserSopra = userSopraRepository.findById(userSopra.getId()).get();
        // Disconnect from session so that the updates on updatedUserSopra are not directly saved in db
        em.detach(updatedUserSopra);
        updatedUserSopra
            .nomUser(UPDATED_NOM_USER)
            .prenomUser(UPDATED_PRENOM_USER)
            .emailUser(UPDATED_EMAIL_USER)
            .matriculeUser(UPDATED_MATRICULE_USER)
            .disponibiliteUser(UPDATED_DISPONIBILITE_USER)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .userRolesSopra(UPDATED_USER_ROLES_SOPRA);
        UserSopraDTO userSopraDTO = userSopraMapper.toDto(updatedUserSopra);

        restUserSopraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userSopraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userSopraDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserSopra in the database
        List<UserSopra> userSopraList = userSopraRepository.findAll();
        assertThat(userSopraList).hasSize(databaseSizeBeforeUpdate);
        UserSopra testUserSopra = userSopraList.get(userSopraList.size() - 1);
        assertThat(testUserSopra.getNomUser()).isEqualTo(UPDATED_NOM_USER);
        assertThat(testUserSopra.getPrenomUser()).isEqualTo(UPDATED_PRENOM_USER);
        assertThat(testUserSopra.getEmailUser()).isEqualTo(UPDATED_EMAIL_USER);
        assertThat(testUserSopra.getMatriculeUser()).isEqualTo(UPDATED_MATRICULE_USER);
        assertThat(testUserSopra.getDisponibiliteUser()).isEqualTo(UPDATED_DISPONIBILITE_USER);
        assertThat(testUserSopra.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testUserSopra.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testUserSopra.getUserRolesSopra()).isEqualTo(UPDATED_USER_ROLES_SOPRA);
    }

    @Test
    @Transactional
    void putNonExistingUserSopra() throws Exception {
        int databaseSizeBeforeUpdate = userSopraRepository.findAll().size();
        userSopra.setId(count.incrementAndGet());

        // Create the UserSopra
        UserSopraDTO userSopraDTO = userSopraMapper.toDto(userSopra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserSopraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userSopraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userSopraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSopra in the database
        List<UserSopra> userSopraList = userSopraRepository.findAll();
        assertThat(userSopraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserSopra() throws Exception {
        int databaseSizeBeforeUpdate = userSopraRepository.findAll().size();
        userSopra.setId(count.incrementAndGet());

        // Create the UserSopra
        UserSopraDTO userSopraDTO = userSopraMapper.toDto(userSopra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSopraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userSopraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSopra in the database
        List<UserSopra> userSopraList = userSopraRepository.findAll();
        assertThat(userSopraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserSopra() throws Exception {
        int databaseSizeBeforeUpdate = userSopraRepository.findAll().size();
        userSopra.setId(count.incrementAndGet());

        // Create the UserSopra
        UserSopraDTO userSopraDTO = userSopraMapper.toDto(userSopra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSopraMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userSopraDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserSopra in the database
        List<UserSopra> userSopraList = userSopraRepository.findAll();
        assertThat(userSopraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserSopraWithPatch() throws Exception {
        // Initialize the database
        userSopraRepository.saveAndFlush(userSopra);

        int databaseSizeBeforeUpdate = userSopraRepository.findAll().size();

        // Update the userSopra using partial update
        UserSopra partialUpdatedUserSopra = new UserSopra();
        partialUpdatedUserSopra.setId(userSopra.getId());

        partialUpdatedUserSopra
            .nomUser(UPDATED_NOM_USER)
            .prenomUser(UPDATED_PRENOM_USER)
            .disponibiliteUser(UPDATED_DISPONIBILITE_USER)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restUserSopraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserSopra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserSopra))
            )
            .andExpect(status().isOk());

        // Validate the UserSopra in the database
        List<UserSopra> userSopraList = userSopraRepository.findAll();
        assertThat(userSopraList).hasSize(databaseSizeBeforeUpdate);
        UserSopra testUserSopra = userSopraList.get(userSopraList.size() - 1);
        assertThat(testUserSopra.getNomUser()).isEqualTo(UPDATED_NOM_USER);
        assertThat(testUserSopra.getPrenomUser()).isEqualTo(UPDATED_PRENOM_USER);
        assertThat(testUserSopra.getEmailUser()).isEqualTo(DEFAULT_EMAIL_USER);
        assertThat(testUserSopra.getMatriculeUser()).isEqualTo(DEFAULT_MATRICULE_USER);
        assertThat(testUserSopra.getDisponibiliteUser()).isEqualTo(UPDATED_DISPONIBILITE_USER);
        assertThat(testUserSopra.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testUserSopra.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testUserSopra.getUserRolesSopra()).isEqualTo(DEFAULT_USER_ROLES_SOPRA);
    }

    @Test
    @Transactional
    void fullUpdateUserSopraWithPatch() throws Exception {
        // Initialize the database
        userSopraRepository.saveAndFlush(userSopra);

        int databaseSizeBeforeUpdate = userSopraRepository.findAll().size();

        // Update the userSopra using partial update
        UserSopra partialUpdatedUserSopra = new UserSopra();
        partialUpdatedUserSopra.setId(userSopra.getId());

        partialUpdatedUserSopra
            .nomUser(UPDATED_NOM_USER)
            .prenomUser(UPDATED_PRENOM_USER)
            .emailUser(UPDATED_EMAIL_USER)
            .matriculeUser(UPDATED_MATRICULE_USER)
            .disponibiliteUser(UPDATED_DISPONIBILITE_USER)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .userRolesSopra(UPDATED_USER_ROLES_SOPRA);

        restUserSopraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserSopra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserSopra))
            )
            .andExpect(status().isOk());

        // Validate the UserSopra in the database
        List<UserSopra> userSopraList = userSopraRepository.findAll();
        assertThat(userSopraList).hasSize(databaseSizeBeforeUpdate);
        UserSopra testUserSopra = userSopraList.get(userSopraList.size() - 1);
        assertThat(testUserSopra.getNomUser()).isEqualTo(UPDATED_NOM_USER);
        assertThat(testUserSopra.getPrenomUser()).isEqualTo(UPDATED_PRENOM_USER);
        assertThat(testUserSopra.getEmailUser()).isEqualTo(UPDATED_EMAIL_USER);
        assertThat(testUserSopra.getMatriculeUser()).isEqualTo(UPDATED_MATRICULE_USER);
        assertThat(testUserSopra.getDisponibiliteUser()).isEqualTo(UPDATED_DISPONIBILITE_USER);
        assertThat(testUserSopra.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testUserSopra.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testUserSopra.getUserRolesSopra()).isEqualTo(UPDATED_USER_ROLES_SOPRA);
    }

    @Test
    @Transactional
    void patchNonExistingUserSopra() throws Exception {
        int databaseSizeBeforeUpdate = userSopraRepository.findAll().size();
        userSopra.setId(count.incrementAndGet());

        // Create the UserSopra
        UserSopraDTO userSopraDTO = userSopraMapper.toDto(userSopra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserSopraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userSopraDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userSopraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSopra in the database
        List<UserSopra> userSopraList = userSopraRepository.findAll();
        assertThat(userSopraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserSopra() throws Exception {
        int databaseSizeBeforeUpdate = userSopraRepository.findAll().size();
        userSopra.setId(count.incrementAndGet());

        // Create the UserSopra
        UserSopraDTO userSopraDTO = userSopraMapper.toDto(userSopra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSopraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userSopraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserSopra in the database
        List<UserSopra> userSopraList = userSopraRepository.findAll();
        assertThat(userSopraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserSopra() throws Exception {
        int databaseSizeBeforeUpdate = userSopraRepository.findAll().size();
        userSopra.setId(count.incrementAndGet());

        // Create the UserSopra
        UserSopraDTO userSopraDTO = userSopraMapper.toDto(userSopra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserSopraMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userSopraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserSopra in the database
        List<UserSopra> userSopraList = userSopraRepository.findAll();
        assertThat(userSopraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserSopra() throws Exception {
        // Initialize the database
        userSopraRepository.saveAndFlush(userSopra);

        int databaseSizeBeforeDelete = userSopraRepository.findAll().size();

        // Delete the userSopra
        restUserSopraMockMvc
            .perform(delete(ENTITY_API_URL_ID, userSopra.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserSopra> userSopraList = userSopraRepository.findAll();
        assertThat(userSopraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
