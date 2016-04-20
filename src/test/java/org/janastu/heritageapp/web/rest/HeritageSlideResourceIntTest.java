package org.janastu.heritageapp.web.rest;

import org.janastu.heritageapp.Application;
import org.janastu.heritageapp.domain.HeritageSlide;
import org.janastu.heritageapp.repository.HeritageSlideRepository;
import org.janastu.heritageapp.service.HeritageSlideService;
import org.janastu.heritageapp.web.rest.dto.HeritageSlideDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageSlideMapper;

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
 * Test class for the HeritageSlideResource REST controller.
 *
 * @see HeritageSlideResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HeritageSlideResourceIntTest {


    private static final Integer DEFAULT_INDEX_VAL = 1;
    private static final Integer UPDATED_INDEX_VAL = 2;
    private static final String DEFAULT_NOTES = "AAAAA";
    private static final String UPDATED_NOTES = "BBBBB";

    @Inject
    private HeritageSlideRepository heritageSlideRepository;

    @Inject
    private HeritageSlideMapper heritageSlideMapper;

    @Inject
    private HeritageSlideService heritageSlideService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHeritageSlideMockMvc;

    private HeritageSlide heritageSlide;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HeritageSlideResource heritageSlideResource = new HeritageSlideResource();
        ReflectionTestUtils.setField(heritageSlideResource, "heritageSlideService", heritageSlideService);
        ReflectionTestUtils.setField(heritageSlideResource, "heritageSlideMapper", heritageSlideMapper);
        this.restHeritageSlideMockMvc = MockMvcBuilders.standaloneSetup(heritageSlideResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        heritageSlide = new HeritageSlide();
        heritageSlide.setIndexVal(DEFAULT_INDEX_VAL);
        heritageSlide.setNotes(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createHeritageSlide() throws Exception {
        int databaseSizeBeforeCreate = heritageSlideRepository.findAll().size();

        // Create the HeritageSlide
        HeritageSlideDTO heritageSlideDTO = heritageSlideMapper.heritageSlideToHeritageSlideDTO(heritageSlide);

        restHeritageSlideMockMvc.perform(post("/api/heritageSlides")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageSlideDTO)))
                .andExpect(status().isCreated());

        // Validate the HeritageSlide in the database
        List<HeritageSlide> heritageSlides = heritageSlideRepository.findAll();
        assertThat(heritageSlides).hasSize(databaseSizeBeforeCreate + 1);
        HeritageSlide testHeritageSlide = heritageSlides.get(heritageSlides.size() - 1);
        assertThat(testHeritageSlide.getIndexVal()).isEqualTo(DEFAULT_INDEX_VAL);
        assertThat(testHeritageSlide.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void checkIndexValIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageSlideRepository.findAll().size();
        // set the field null
        heritageSlide.setIndexVal(null);

        // Create the HeritageSlide, which fails.
        HeritageSlideDTO heritageSlideDTO = heritageSlideMapper.heritageSlideToHeritageSlideDTO(heritageSlide);

        restHeritageSlideMockMvc.perform(post("/api/heritageSlides")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageSlideDTO)))
                .andExpect(status().isBadRequest());

        List<HeritageSlide> heritageSlides = heritageSlideRepository.findAll();
        assertThat(heritageSlides).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHeritageSlides() throws Exception {
        // Initialize the database
        heritageSlideRepository.saveAndFlush(heritageSlide);

        // Get all the heritageSlides
        restHeritageSlideMockMvc.perform(get("/api/heritageSlides?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(heritageSlide.getId().intValue())))
                .andExpect(jsonPath("$.[*].indexVal").value(hasItem(DEFAULT_INDEX_VAL)))
                .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }

    @Test
    @Transactional
    public void getHeritageSlide() throws Exception {
        // Initialize the database
        heritageSlideRepository.saveAndFlush(heritageSlide);

        // Get the heritageSlide
        restHeritageSlideMockMvc.perform(get("/api/heritageSlides/{id}", heritageSlide.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(heritageSlide.getId().intValue()))
            .andExpect(jsonPath("$.indexVal").value(DEFAULT_INDEX_VAL))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHeritageSlide() throws Exception {
        // Get the heritageSlide
        restHeritageSlideMockMvc.perform(get("/api/heritageSlides/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHeritageSlide() throws Exception {
        // Initialize the database
        heritageSlideRepository.saveAndFlush(heritageSlide);

		int databaseSizeBeforeUpdate = heritageSlideRepository.findAll().size();

        // Update the heritageSlide
        heritageSlide.setIndexVal(UPDATED_INDEX_VAL);
        heritageSlide.setNotes(UPDATED_NOTES);
        HeritageSlideDTO heritageSlideDTO = heritageSlideMapper.heritageSlideToHeritageSlideDTO(heritageSlide);

        restHeritageSlideMockMvc.perform(put("/api/heritageSlides")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageSlideDTO)))
                .andExpect(status().isOk());

        // Validate the HeritageSlide in the database
        List<HeritageSlide> heritageSlides = heritageSlideRepository.findAll();
        assertThat(heritageSlides).hasSize(databaseSizeBeforeUpdate);
        HeritageSlide testHeritageSlide = heritageSlides.get(heritageSlides.size() - 1);
        assertThat(testHeritageSlide.getIndexVal()).isEqualTo(UPDATED_INDEX_VAL);
        assertThat(testHeritageSlide.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void deleteHeritageSlide() throws Exception {
        // Initialize the database
        heritageSlideRepository.saveAndFlush(heritageSlide);

		int databaseSizeBeforeDelete = heritageSlideRepository.findAll().size();

        // Get the heritageSlide
        restHeritageSlideMockMvc.perform(delete("/api/heritageSlides/{id}", heritageSlide.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HeritageSlide> heritageSlides = heritageSlideRepository.findAll();
        assertThat(heritageSlides).hasSize(databaseSizeBeforeDelete - 1);
    }
}
