package com.fsl.ma.web.rest;

import com.fsl.ma.DbgestionPhaApp;

import com.fsl.ma.domain.Effet;
import com.fsl.ma.repository.EffetRepository;
import com.fsl.ma.service.EffetService;
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
 * Test class for the EffetResource REST controller.
 *
 * @see EffetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DbgestionPhaApp.class)
public class EffetResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DISCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DISCRIPTION = "BBBBBBBBBB";

    @Autowired
    private EffetRepository effetRepository;

    @Autowired
    private EffetService effetService;

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

    private MockMvc restEffetMockMvc;

    private Effet effet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EffetResource effetResource = new EffetResource(effetService);
        this.restEffetMockMvc = MockMvcBuilders.standaloneSetup(effetResource)
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
    public static Effet createEntity(EntityManager em) {
        Effet effet = new Effet()
            .libelle(DEFAULT_LIBELLE)
            .discription(DEFAULT_DISCRIPTION);
        return effet;
    }

    @Before
    public void initTest() {
        effet = createEntity(em);
    }

    @Test
    @Transactional
    public void createEffet() throws Exception {
        int databaseSizeBeforeCreate = effetRepository.findAll().size();

        // Create the Effet
        restEffetMockMvc.perform(post("/api/effets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(effet)))
            .andExpect(status().isCreated());

        // Validate the Effet in the database
        List<Effet> effetList = effetRepository.findAll();
        assertThat(effetList).hasSize(databaseSizeBeforeCreate + 1);
        Effet testEffet = effetList.get(effetList.size() - 1);
        assertThat(testEffet.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testEffet.getDiscription()).isEqualTo(DEFAULT_DISCRIPTION);
    }

    @Test
    @Transactional
    public void createEffetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = effetRepository.findAll().size();

        // Create the Effet with an existing ID
        effet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEffetMockMvc.perform(post("/api/effets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(effet)))
            .andExpect(status().isBadRequest());

        // Validate the Effet in the database
        List<Effet> effetList = effetRepository.findAll();
        assertThat(effetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEffets() throws Exception {
        // Initialize the database
        effetRepository.saveAndFlush(effet);

        // Get all the effetList
        restEffetMockMvc.perform(get("/api/effets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(effet.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].discription").value(hasItem(DEFAULT_DISCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getEffet() throws Exception {
        // Initialize the database
        effetRepository.saveAndFlush(effet);

        // Get the effet
        restEffetMockMvc.perform(get("/api/effets/{id}", effet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(effet.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.discription").value(DEFAULT_DISCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEffet() throws Exception {
        // Get the effet
        restEffetMockMvc.perform(get("/api/effets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEffet() throws Exception {
        // Initialize the database
        effetService.save(effet);

        int databaseSizeBeforeUpdate = effetRepository.findAll().size();

        // Update the effet
        Effet updatedEffet = effetRepository.findById(effet.getId()).get();
        // Disconnect from session so that the updates on updatedEffet are not directly saved in db
        em.detach(updatedEffet);
        updatedEffet
            .libelle(UPDATED_LIBELLE)
            .discription(UPDATED_DISCRIPTION);

        restEffetMockMvc.perform(put("/api/effets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEffet)))
            .andExpect(status().isOk());

        // Validate the Effet in the database
        List<Effet> effetList = effetRepository.findAll();
        assertThat(effetList).hasSize(databaseSizeBeforeUpdate);
        Effet testEffet = effetList.get(effetList.size() - 1);
        assertThat(testEffet.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testEffet.getDiscription()).isEqualTo(UPDATED_DISCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingEffet() throws Exception {
        int databaseSizeBeforeUpdate = effetRepository.findAll().size();

        // Create the Effet

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEffetMockMvc.perform(put("/api/effets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(effet)))
            .andExpect(status().isBadRequest());

        // Validate the Effet in the database
        List<Effet> effetList = effetRepository.findAll();
        assertThat(effetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEffet() throws Exception {
        // Initialize the database
        effetService.save(effet);

        int databaseSizeBeforeDelete = effetRepository.findAll().size();

        // Delete the effet
        restEffetMockMvc.perform(delete("/api/effets/{id}", effet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Effet> effetList = effetRepository.findAll();
        assertThat(effetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Effet.class);
        Effet effet1 = new Effet();
        effet1.setId(1L);
        Effet effet2 = new Effet();
        effet2.setId(effet1.getId());
        assertThat(effet1).isEqualTo(effet2);
        effet2.setId(2L);
        assertThat(effet1).isNotEqualTo(effet2);
        effet1.setId(null);
        assertThat(effet1).isNotEqualTo(effet2);
    }
}
