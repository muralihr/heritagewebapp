package org.janastu.heritageapp.web.rest;

import org.janastu.heritageapp.Application;
import org.janastu.heritageapp.domain.HeritageLanguage;
import org.janastu.heritageapp.repository.HeritageLanguageRepository;

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
 * Test class for the HeritageLanguageResource REST controller.
 *
 * @see HeritageLanguageResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HeritageLanguageResourceIntTest {

    private static final String DEFAULT_HERITAGE_LANGUAGE = "AAAAA";
    private static final String UPDATED_HERITAGE_LANGUAGE = "BBBBB";

    @Inject
    private HeritageLanguageRepository heritageLanguageRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHeritageLanguageMockMvc;

    private HeritageLanguage heritageLanguage;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HeritageLanguageResource heritageLanguageResource = new HeritageLanguageResource();
        ReflectionTestUtils.setField(heritageLanguageResource, "heritageLanguageRepository", heritageLanguageRepository);
        this.restHeritageLanguageMockMvc = MockMvcBuilders.standaloneSetup(heritageLanguageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        heritageLanguage = new HeritageLanguage();
        heritageLanguage.setHeritageLanguage(DEFAULT_HERITAGE_LANGUAGE);
    }

    @Test
    @Transactional
    public void createHeritageLanguage() throws Exception {
        int databaseSizeBeforeCreate = heritageLanguageRepository.findAll().size();

        // Create the HeritageLanguage

        restHeritageLanguageMockMvc.perform(post("/api/heritageLanguages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageLanguage)))
                .andExpect(status().isCreated());

        // Validate the HeritageLanguage in the database
        List<HeritageLanguage> heritageLanguages = heritageLanguageRepository.findAll();
        assertThat(heritageLanguages).hasSize(databaseSizeBeforeCreate + 1);
        HeritageLanguage testHeritageLanguage = heritageLanguages.get(heritageLanguages.size() - 1);
        assertThat(testHeritageLanguage.getHeritageLanguage()).isEqualTo(DEFAULT_HERITAGE_LANGUAGE);
    }

    @Test
    @Transactional
    public void checkHeritageLanguageIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageLanguageRepository.findAll().size();
        // set the field null
        heritageLanguage.setHeritageLanguage(null);

        // Create the HeritageLanguage, which fails.

        restHeritageLanguageMockMvc.perform(post("/api/heritageLanguages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageLanguage)))
                .andExpect(status().isBadRequest());

        List<HeritageLanguage> heritageLanguages = heritageLanguageRepository.findAll();
        assertThat(heritageLanguages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHeritageLanguages() throws Exception {
        // Initialize the database
        heritageLanguageRepository.saveAndFlush(heritageLanguage);

        // Get all the heritageLanguages
        restHeritageLanguageMockMvc.perform(get("/api/heritageLanguages?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(heritageLanguage.getId().intValue())))
                .andExpect(jsonPath("$.[*].heritageLanguage").value(hasItem(DEFAULT_HERITAGE_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    public void getHeritageLanguage() throws Exception {
        // Initialize the database
        heritageLanguageRepository.saveAndFlush(heritageLanguage);

        // Get the heritageLanguage
        restHeritageLanguageMockMvc.perform(get("/api/heritageLanguages/{id}", heritageLanguage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(heritageLanguage.getId().intValue()))
            .andExpect(jsonPath("$.heritageLanguage").value(DEFAULT_HERITAGE_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHeritageLanguage() throws Exception {
        // Get the heritageLanguage
        restHeritageLanguageMockMvc.perform(get("/api/heritageLanguages/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHeritageLanguage() throws Exception {
        // Initialize the database
        heritageLanguageRepository.saveAndFlush(heritageLanguage);

		int databaseSizeBeforeUpdate = heritageLanguageRepository.findAll().size();

        // Update the heritageLanguage
        heritageLanguage.setHeritageLanguage(UPDATED_HERITAGE_LANGUAGE);

        restHeritageLanguageMockMvc.perform(put("/api/heritageLanguages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageLanguage)))
                .andExpect(status().isOk());

        // Validate the HeritageLanguage in the database
        List<HeritageLanguage> heritageLanguages = heritageLanguageRepository.findAll();
        assertThat(heritageLanguages).hasSize(databaseSizeBeforeUpdate);
        HeritageLanguage testHeritageLanguage = heritageLanguages.get(heritageLanguages.size() - 1);
        assertThat(testHeritageLanguage.getHeritageLanguage()).isEqualTo(UPDATED_HERITAGE_LANGUAGE);
    }

    @Test
    @Transactional
    public void deleteHeritageLanguage() throws Exception {
        // Initialize the database
        heritageLanguageRepository.saveAndFlush(heritageLanguage);

		int databaseSizeBeforeDelete = heritageLanguageRepository.findAll().size();

        // Get the heritageLanguage
        restHeritageLanguageMockMvc.perform(delete("/api/heritageLanguages/{id}", heritageLanguage.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HeritageLanguage> heritageLanguages = heritageLanguageRepository.findAll();
        assertThat(heritageLanguages).hasSize(databaseSizeBeforeDelete - 1);
    }
}
