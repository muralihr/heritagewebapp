package org.janastu.heritageapp.web.rest;

import org.janastu.heritageapp.Application;
import org.janastu.heritageapp.domain.HeritageApp;
import org.janastu.heritageapp.repository.HeritageAppRepository;
import org.janastu.heritageapp.service.HeritageAppService;
import org.janastu.heritageapp.web.rest.dto.HeritageAppDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageAppMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the HeritageAppResource REST controller.
 *
 * @see HeritageAppResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HeritageAppResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_CONTACT = "AAAAA";
    private static final String UPDATED_CONTACT = "BBBBB";

    @Inject
    private HeritageAppRepository heritageAppRepository;

    @Inject
    private HeritageAppMapper heritageAppMapper;

    @Inject
    private HeritageAppService heritageAppService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHeritageAppMockMvc;

    private HeritageApp heritageApp;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HeritageAppResource heritageAppResource = new HeritageAppResource();
        ReflectionTestUtils.setField(heritageAppResource, "heritageAppService", heritageAppService);
        ReflectionTestUtils.setField(heritageAppResource, "heritageAppMapper", heritageAppMapper);
        this.restHeritageAppMockMvc = MockMvcBuilders.standaloneSetup(heritageAppResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        heritageApp = new HeritageApp();
        heritageApp.setName(DEFAULT_NAME);
        heritageApp.setContact(DEFAULT_CONTACT);
    }

    @Test
    @Transactional
    public void createHeritageApp() throws Exception {
        int databaseSizeBeforeCreate = heritageAppRepository.findAll().size();

        // Create the HeritageApp
        HeritageAppDTO heritageAppDTO = heritageAppMapper.heritageAppToHeritageAppDTO(heritageApp);

        restHeritageAppMockMvc.perform(post("/api/heritageApps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageAppDTO)))
                .andExpect(status().isCreated());

        // Validate the HeritageApp in the database
        List<HeritageApp> heritageApps = heritageAppRepository.findAll();
        assertThat(heritageApps).hasSize(databaseSizeBeforeCreate + 1);
        HeritageApp testHeritageApp = heritageApps.get(heritageApps.size() - 1);
        assertThat(testHeritageApp.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHeritageApp.getContact()).isEqualTo(DEFAULT_CONTACT);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageAppRepository.findAll().size();
        // set the field null
        heritageApp.setName(null);

        // Create the HeritageApp, which fails.
        HeritageAppDTO heritageAppDTO = heritageAppMapper.heritageAppToHeritageAppDTO(heritageApp);

        restHeritageAppMockMvc.perform(post("/api/heritageApps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageAppDTO)))
                .andExpect(status().isBadRequest());

        List<HeritageApp> heritageApps = heritageAppRepository.findAll();
        assertThat(heritageApps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageAppRepository.findAll().size();
        // set the field null
        heritageApp.setContact(null);

        // Create the HeritageApp, which fails.
        HeritageAppDTO heritageAppDTO = heritageAppMapper.heritageAppToHeritageAppDTO(heritageApp);

        restHeritageAppMockMvc.perform(post("/api/heritageApps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageAppDTO)))
                .andExpect(status().isBadRequest());

        List<HeritageApp> heritageApps = heritageAppRepository.findAll();
        assertThat(heritageApps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHeritageApps() throws Exception {
        // Initialize the database
        heritageAppRepository.saveAndFlush(heritageApp);

        // Get all the heritageApps
        restHeritageAppMockMvc.perform(get("/api/heritageApps?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(heritageApp.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())));
    }

    @Test
    @Transactional
    public void getHeritageApp() throws Exception {
        // Initialize the database
        heritageAppRepository.saveAndFlush(heritageApp);

        // Get the heritageApp
        restHeritageAppMockMvc.perform(get("/api/heritageApps/{id}", heritageApp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(heritageApp.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHeritageApp() throws Exception {
        // Get the heritageApp
        restHeritageAppMockMvc.perform(get("/api/heritageApps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHeritageApp() throws Exception {
        // Initialize the database
        heritageAppRepository.saveAndFlush(heritageApp);

		int databaseSizeBeforeUpdate = heritageAppRepository.findAll().size();

        // Update the heritageApp
        heritageApp.setName(UPDATED_NAME);
        heritageApp.setContact(UPDATED_CONTACT);
        HeritageAppDTO heritageAppDTO = heritageAppMapper.heritageAppToHeritageAppDTO(heritageApp);

        restHeritageAppMockMvc.perform(put("/api/heritageApps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageAppDTO)))
                .andExpect(status().isOk());

        // Validate the HeritageApp in the database
        List<HeritageApp> heritageApps = heritageAppRepository.findAll();
        assertThat(heritageApps).hasSize(databaseSizeBeforeUpdate);
        HeritageApp testHeritageApp = heritageApps.get(heritageApps.size() - 1);
        assertThat(testHeritageApp.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHeritageApp.getContact()).isEqualTo(UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void deleteHeritageApp() throws Exception {
        // Initialize the database
        heritageAppRepository.saveAndFlush(heritageApp);

		int databaseSizeBeforeDelete = heritageAppRepository.findAll().size();

        // Get the heritageApp
        restHeritageAppMockMvc.perform(delete("/api/heritageApps/{id}", heritageApp.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HeritageApp> heritageApps = heritageAppRepository.findAll();
        assertThat(heritageApps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
