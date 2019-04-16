package com.fsl.ma.web.rest;

import com.fsl.ma.DbgestionPhaApp;

import com.fsl.ma.domain.Medicament;
import com.fsl.ma.repository.MedicamentRepository;
import com.fsl.ma.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.fsl.ma.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MedicamentResource REST controller.
 *
 * @see MedicamentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DbgestionPhaApp.class)
public class MedicamentResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DISCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DISCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_PRIX = 1L;
    private static final Long UPDATED_PRIX = 2L;

    private static final Instant DEFAULT_DATE_EXPIRACTION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_EXPIRACTION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MedicamentRepository medicamentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restMedicamentMockMvc;

    private Medicament medicament;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedicamentResource medicamentResource = new MedicamentResource(medicamentRepository);
        this.restMedicamentMockMvc = MockMvcBuilders.standaloneSetup(medicamentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medicament createEntity(EntityManager em) {
        Medicament medicament = new Medicament()
            .nom(DEFAULT_NOM)
            .libelle(DEFAULT_LIBELLE)
            .discription(DEFAULT_DISCRIPTION)
            .prix(DEFAULT_PRIX)
            .dateExpiraction(DEFAULT_DATE_EXPIRACTION);
        return medicament;
    }

    @Before
    public void initTest() {
        medicament = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicament() throws Exception {
        int databaseSizeBeforeCreate = medicamentRepository.findAll().size();

        // Create the Medicament
        restMedicamentMockMvc.perform(post("/api/medicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicament)))
            .andExpect(status().isCreated());

        // Validate the Medicament in the database
        List<Medicament> medicamentList = medicamentRepository.findAll();
        assertThat(medicamentList).hasSize(databaseSizeBeforeCreate + 1);
        Medicament testMedicament = medicamentList.get(medicamentList.size() - 1);
        assertThat(testMedicament.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMedicament.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testMedicament.getDiscription()).isEqualTo(DEFAULT_DISCRIPTION);
        assertThat(testMedicament.getPrix()).isEqualTo(DEFAULT_PRIX);
        assertThat(testMedicament.getDateExpiraction()).isEqualTo(DEFAULT_DATE_EXPIRACTION);
    }

    @Test
    @Transactional
    public void createMedicamentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicamentRepository.findAll().size();

        // Create the Medicament with an existing ID
        medicament.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicamentMockMvc.perform(post("/api/medicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicament)))
            .andExpect(status().isBadRequest());

        // Validate the Medicament in the database
        List<Medicament> medicamentList = medicamentRepository.findAll();
        assertThat(medicamentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedicaments() throws Exception {
        // Initialize the database
        medicamentRepository.saveAndFlush(medicament);

        // Get all the medicamentList
        restMedicamentMockMvc.perform(get("/api/medicaments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicament.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].discription").value(hasItem(DEFAULT_DISCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.intValue())))
            .andExpect(jsonPath("$.[*].dateExpiraction").value(hasItem(DEFAULT_DATE_EXPIRACTION.toString())));
    }
    
    @Test
    @Transactional
    public void getMedicament() throws Exception {
        // Initialize the database
        medicamentRepository.saveAndFlush(medicament);

        // Get the medicament
        restMedicamentMockMvc.perform(get("/api/medicaments/{id}", medicament.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medicament.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.discription").value(DEFAULT_DISCRIPTION.toString()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.intValue()))
            .andExpect(jsonPath("$.dateExpiraction").value(DEFAULT_DATE_EXPIRACTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedicament() throws Exception {
        // Get the medicament
        restMedicamentMockMvc.perform(get("/api/medicaments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicament() throws Exception {
        // Initialize the database
        medicamentRepository.saveAndFlush(medicament);

        int databaseSizeBeforeUpdate = medicamentRepository.findAll().size();

        // Update the medicament
        Medicament updatedMedicament = medicamentRepository.findById(medicament.getId()).get();
        // Disconnect from session so that the updates on updatedMedicament are not directly saved in db
        em.detach(updatedMedicament);
        updatedMedicament
            .nom(UPDATED_NOM)
            .libelle(UPDATED_LIBELLE)
            .discription(UPDATED_DISCRIPTION)
            .prix(UPDATED_PRIX)
            .dateExpiraction(UPDATED_DATE_EXPIRACTION);

        restMedicamentMockMvc.perform(put("/api/medicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicament)))
            .andExpect(status().isOk());

        // Validate the Medicament in the database
        List<Medicament> medicamentList = medicamentRepository.findAll();
        assertThat(medicamentList).hasSize(databaseSizeBeforeUpdate);
        Medicament testMedicament = medicamentList.get(medicamentList.size() - 1);
        assertThat(testMedicament.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMedicament.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testMedicament.getDiscription()).isEqualTo(UPDATED_DISCRIPTION);
        assertThat(testMedicament.getPrix()).isEqualTo(UPDATED_PRIX);
        assertThat(testMedicament.getDateExpiraction()).isEqualTo(UPDATED_DATE_EXPIRACTION);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicament() throws Exception {
        int databaseSizeBeforeUpdate = medicamentRepository.findAll().size();

        // Create the Medicament

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicamentMockMvc.perform(put("/api/medicaments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medicament)))
            .andExpect(status().isBadRequest());

        // Validate the Medicament in the database
        List<Medicament> medicamentList = medicamentRepository.findAll();
        assertThat(medicamentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicament() throws Exception {
        // Initialize the database
        medicamentRepository.saveAndFlush(medicament);

        int databaseSizeBeforeDelete = medicamentRepository.findAll().size();

        // Delete the medicament
        restMedicamentMockMvc.perform(delete("/api/medicaments/{id}", medicament.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Medicament> medicamentList = medicamentRepository.findAll();
        assertThat(medicamentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medicament.class);
        Medicament medicament1 = new Medicament();
        medicament1.setId(1L);
        Medicament medicament2 = new Medicament();
        medicament2.setId(medicament1.getId());
        assertThat(medicament1).isEqualTo(medicament2);
        medicament2.setId(2L);
        assertThat(medicament1).isNotEqualTo(medicament2);
        medicament1.setId(null);
        assertThat(medicament1).isNotEqualTo(medicament2);
    }
}
