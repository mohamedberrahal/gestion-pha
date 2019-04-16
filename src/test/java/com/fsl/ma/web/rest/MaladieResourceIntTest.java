package com.fsl.ma.web.rest;

import com.fsl.ma.DbgestionPhaApp;

import com.fsl.ma.domain.Maladie;
import com.fsl.ma.repository.MaladieRepository;
import com.fsl.ma.service.MaladieService;
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
import java.util.List;


import static com.fsl.ma.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MaladieResource REST controller.
 *
 * @see MaladieResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DbgestionPhaApp.class)
public class MaladieResourceIntTest {

    private static final String DEFAULT_MALADIE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MALADIE_NAME = "BBBBBBBBBB";

    @Autowired
    private MaladieRepository maladieRepository;

    @Autowired
    private MaladieService maladieService;

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

    private MockMvc restMaladieMockMvc;

    private Maladie maladie;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MaladieResource maladieResource = new MaladieResource(maladieService);
        this.restMaladieMockMvc = MockMvcBuilders.standaloneSetup(maladieResource)
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
    public static Maladie createEntity(EntityManager em) {
        Maladie maladie = new Maladie()
            .maladieName(DEFAULT_MALADIE_NAME);
        return maladie;
    }

    @Before
    public void initTest() {
        maladie = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaladie() throws Exception {
        int databaseSizeBeforeCreate = maladieRepository.findAll().size();

        // Create the Maladie
        restMaladieMockMvc.perform(post("/api/maladies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maladie)))
            .andExpect(status().isCreated());

        // Validate the Maladie in the database
        List<Maladie> maladieList = maladieRepository.findAll();
        assertThat(maladieList).hasSize(databaseSizeBeforeCreate + 1);
        Maladie testMaladie = maladieList.get(maladieList.size() - 1);
        assertThat(testMaladie.getMaladieName()).isEqualTo(DEFAULT_MALADIE_NAME);
    }

    @Test
    @Transactional
    public void createMaladieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = maladieRepository.findAll().size();

        // Create the Maladie with an existing ID
        maladie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaladieMockMvc.perform(post("/api/maladies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maladie)))
            .andExpect(status().isBadRequest());

        // Validate the Maladie in the database
        List<Maladie> maladieList = maladieRepository.findAll();
        assertThat(maladieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMaladieNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = maladieRepository.findAll().size();
        // set the field null
        maladie.setMaladieName(null);

        // Create the Maladie, which fails.

        restMaladieMockMvc.perform(post("/api/maladies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maladie)))
            .andExpect(status().isBadRequest());

        List<Maladie> maladieList = maladieRepository.findAll();
        assertThat(maladieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMaladies() throws Exception {
        // Initialize the database
        maladieRepository.saveAndFlush(maladie);

        // Get all the maladieList
        restMaladieMockMvc.perform(get("/api/maladies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(maladie.getId().intValue())))
            .andExpect(jsonPath("$.[*].maladieName").value(hasItem(DEFAULT_MALADIE_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getMaladie() throws Exception {
        // Initialize the database
        maladieRepository.saveAndFlush(maladie);

        // Get the maladie
        restMaladieMockMvc.perform(get("/api/maladies/{id}", maladie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(maladie.getId().intValue()))
            .andExpect(jsonPath("$.maladieName").value(DEFAULT_MALADIE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMaladie() throws Exception {
        // Get the maladie
        restMaladieMockMvc.perform(get("/api/maladies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaladie() throws Exception {
        // Initialize the database
        maladieService.save(maladie);

        int databaseSizeBeforeUpdate = maladieRepository.findAll().size();

        // Update the maladie
        Maladie updatedMaladie = maladieRepository.findById(maladie.getId()).get();
        // Disconnect from session so that the updates on updatedMaladie are not directly saved in db
        em.detach(updatedMaladie);
        updatedMaladie
            .maladieName(UPDATED_MALADIE_NAME);

        restMaladieMockMvc.perform(put("/api/maladies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMaladie)))
            .andExpect(status().isOk());

        // Validate the Maladie in the database
        List<Maladie> maladieList = maladieRepository.findAll();
        assertThat(maladieList).hasSize(databaseSizeBeforeUpdate);
        Maladie testMaladie = maladieList.get(maladieList.size() - 1);
        assertThat(testMaladie.getMaladieName()).isEqualTo(UPDATED_MALADIE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMaladie() throws Exception {
        int databaseSizeBeforeUpdate = maladieRepository.findAll().size();

        // Create the Maladie

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaladieMockMvc.perform(put("/api/maladies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(maladie)))
            .andExpect(status().isBadRequest());

        // Validate the Maladie in the database
        List<Maladie> maladieList = maladieRepository.findAll();
        assertThat(maladieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMaladie() throws Exception {
        // Initialize the database
        maladieService.save(maladie);

        int databaseSizeBeforeDelete = maladieRepository.findAll().size();

        // Delete the maladie
        restMaladieMockMvc.perform(delete("/api/maladies/{id}", maladie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Maladie> maladieList = maladieRepository.findAll();
        assertThat(maladieList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Maladie.class);
        Maladie maladie1 = new Maladie();
        maladie1.setId(1L);
        Maladie maladie2 = new Maladie();
        maladie2.setId(maladie1.getId());
        assertThat(maladie1).isEqualTo(maladie2);
        maladie2.setId(2L);
        assertThat(maladie1).isNotEqualTo(maladie2);
        maladie1.setId(null);
        assertThat(maladie1).isNotEqualTo(maladie2);
    }
}
