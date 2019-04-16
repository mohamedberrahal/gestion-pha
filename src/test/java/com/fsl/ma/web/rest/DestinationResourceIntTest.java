package com.fsl.ma.web.rest;

import com.fsl.ma.DbgestionPhaApp;

import com.fsl.ma.domain.Destination;
import com.fsl.ma.repository.DestinationRepository;
import com.fsl.ma.service.DestinationService;
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
 * Test class for the DestinationResource REST controller.
 *
 * @see DestinationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DbgestionPhaApp.class)
public class DestinationResourceIntTest {

    private static final String DEFAULT_POPULATION = "AAAAAAAAAA";
    private static final String UPDATED_POPULATION = "BBBBBBBBBB";

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private DestinationService destinationService;

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

    private MockMvc restDestinationMockMvc;

    private Destination destination;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DestinationResource destinationResource = new DestinationResource(destinationService);
        this.restDestinationMockMvc = MockMvcBuilders.standaloneSetup(destinationResource)
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
    public static Destination createEntity(EntityManager em) {
        Destination destination = new Destination()
            .population(DEFAULT_POPULATION);
        return destination;
    }

    @Before
    public void initTest() {
        destination = createEntity(em);
    }

    @Test
    @Transactional
    public void createDestination() throws Exception {
        int databaseSizeBeforeCreate = destinationRepository.findAll().size();

        // Create the Destination
        restDestinationMockMvc.perform(post("/api/destinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(destination)))
            .andExpect(status().isCreated());

        // Validate the Destination in the database
        List<Destination> destinationList = destinationRepository.findAll();
        assertThat(destinationList).hasSize(databaseSizeBeforeCreate + 1);
        Destination testDestination = destinationList.get(destinationList.size() - 1);
        assertThat(testDestination.getPopulation()).isEqualTo(DEFAULT_POPULATION);
    }

    @Test
    @Transactional
    public void createDestinationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = destinationRepository.findAll().size();

        // Create the Destination with an existing ID
        destination.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDestinationMockMvc.perform(post("/api/destinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(destination)))
            .andExpect(status().isBadRequest());

        // Validate the Destination in the database
        List<Destination> destinationList = destinationRepository.findAll();
        assertThat(destinationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDestinations() throws Exception {
        // Initialize the database
        destinationRepository.saveAndFlush(destination);

        // Get all the destinationList
        restDestinationMockMvc.perform(get("/api/destinations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(destination.getId().intValue())))
            .andExpect(jsonPath("$.[*].population").value(hasItem(DEFAULT_POPULATION.toString())));
    }
    
    @Test
    @Transactional
    public void getDestination() throws Exception {
        // Initialize the database
        destinationRepository.saveAndFlush(destination);

        // Get the destination
        restDestinationMockMvc.perform(get("/api/destinations/{id}", destination.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(destination.getId().intValue()))
            .andExpect(jsonPath("$.population").value(DEFAULT_POPULATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDestination() throws Exception {
        // Get the destination
        restDestinationMockMvc.perform(get("/api/destinations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDestination() throws Exception {
        // Initialize the database
        destinationService.save(destination);

        int databaseSizeBeforeUpdate = destinationRepository.findAll().size();

        // Update the destination
        Destination updatedDestination = destinationRepository.findById(destination.getId()).get();
        // Disconnect from session so that the updates on updatedDestination are not directly saved in db
        em.detach(updatedDestination);
        updatedDestination
            .population(UPDATED_POPULATION);

        restDestinationMockMvc.perform(put("/api/destinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDestination)))
            .andExpect(status().isOk());

        // Validate the Destination in the database
        List<Destination> destinationList = destinationRepository.findAll();
        assertThat(destinationList).hasSize(databaseSizeBeforeUpdate);
        Destination testDestination = destinationList.get(destinationList.size() - 1);
        assertThat(testDestination.getPopulation()).isEqualTo(UPDATED_POPULATION);
    }

    @Test
    @Transactional
    public void updateNonExistingDestination() throws Exception {
        int databaseSizeBeforeUpdate = destinationRepository.findAll().size();

        // Create the Destination

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDestinationMockMvc.perform(put("/api/destinations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(destination)))
            .andExpect(status().isBadRequest());

        // Validate the Destination in the database
        List<Destination> destinationList = destinationRepository.findAll();
        assertThat(destinationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDestination() throws Exception {
        // Initialize the database
        destinationService.save(destination);

        int databaseSizeBeforeDelete = destinationRepository.findAll().size();

        // Delete the destination
        restDestinationMockMvc.perform(delete("/api/destinations/{id}", destination.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Destination> destinationList = destinationRepository.findAll();
        assertThat(destinationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Destination.class);
        Destination destination1 = new Destination();
        destination1.setId(1L);
        Destination destination2 = new Destination();
        destination2.setId(destination1.getId());
        assertThat(destination1).isEqualTo(destination2);
        destination2.setId(2L);
        assertThat(destination1).isNotEqualTo(destination2);
        destination1.setId(null);
        assertThat(destination1).isNotEqualTo(destination2);
    }
}
