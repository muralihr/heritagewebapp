package org.janastu.heritageapp.web.rest;

import org.janastu.heritageapp.Application;
import org.janastu.heritageapp.domain.HeritageWalk;
import org.janastu.heritageapp.repository.HeritageWalkRepository;
import org.janastu.heritageapp.service.HeritageWalkService;
import org.janastu.heritageapp.web.rest.dto.HeritageWalkDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageWalkMapper;

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
 * Test class for the HeritageWalkResource REST controller.
 *
 * @see HeritageWalkResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HeritageWalkResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_BACKGROUND = "AAAAA";
    private static final String UPDATED_BACKGROUND = "BBBBB";

    @Inject
    private HeritageWalkRepository heritageWalkRepository;

    @Inject
    private HeritageWalkMapper heritageWalkMapper;

    @Inject
    private HeritageWalkService heritageWalkService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHeritageWalkMockMvc;

    private HeritageWalk heritageWalk;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HeritageWalkResource heritageWalkResource = new HeritageWalkResource();
        ReflectionTestUtils.setField(heritageWalkResource, "heritageWalkService", heritageWalkService);
        ReflectionTestUtils.setField(heritageWalkResource, "heritageWalkMapper", heritageWalkMapper);
        this.restHeritageWalkMockMvc = MockMvcBuilders.standaloneSetup(heritageWalkResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        heritageWalk = new HeritageWalk();
        heritageWalk.setName(DEFAULT_NAME);
        heritageWalk.setBackground(DEFAULT_BACKGROUND);
    }

    @Test
    @Transactional
    public void createHeritageWalk() throws Exception {
        int databaseSizeBeforeCreate = heritageWalkRepository.findAll().size();

        // Create the HeritageWalk
        HeritageWalkDTO heritageWalkDTO = heritageWalkMapper.heritageWalkToHeritageWalkDTO(heritageWalk);

        restHeritageWalkMockMvc.perform(post("/api/heritageWalks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageWalkDTO)))
                .andExpect(status().isCreated());

        // Validate the HeritageWalk in the database
        List<HeritageWalk> heritageWalks = heritageWalkRepository.findAll();
        assertThat(heritageWalks).hasSize(databaseSizeBeforeCreate + 1);
        HeritageWalk testHeritageWalk = heritageWalks.get(heritageWalks.size() - 1);
        assertThat(testHeritageWalk.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHeritageWalk.getBackground()).isEqualTo(DEFAULT_BACKGROUND);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageWalkRepository.findAll().size();
        // set the field null
        heritageWalk.setName(null);

        // Create the HeritageWalk, which fails.
        HeritageWalkDTO heritageWalkDTO = heritageWalkMapper.heritageWalkToHeritageWalkDTO(heritageWalk);

        restHeritageWalkMockMvc.perform(post("/api/heritageWalks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageWalkDTO)))
                .andExpect(status().isBadRequest());

        List<HeritageWalk> heritageWalks = heritageWalkRepository.findAll();
        assertThat(heritageWalks).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHeritageWalks() throws Exception {
        // Initialize the database
        heritageWalkRepository.saveAndFlush(heritageWalk);

        // Get all the heritageWalks
        restHeritageWalkMockMvc.perform(get("/api/heritageWalks?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(heritageWalk.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].background").value(hasItem(DEFAULT_BACKGROUND.toString())));
    }

    @Test
    @Transactional
    public void getHeritageWalk() throws Exception {
        // Initialize the database
        heritageWalkRepository.saveAndFlush(heritageWalk);

        // Get the heritageWalk
        restHeritageWalkMockMvc.perform(get("/api/heritageWalks/{id}", heritageWalk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(heritageWalk.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.background").value(DEFAULT_BACKGROUND.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHeritageWalk() throws Exception {
        // Get the heritageWalk
        restHeritageWalkMockMvc.perform(get("/api/heritageWalks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHeritageWalk() throws Exception {
        // Initialize the database
        heritageWalkRepository.saveAndFlush(heritageWalk);

		int databaseSizeBeforeUpdate = heritageWalkRepository.findAll().size();

        // Update the heritageWalk
        heritageWalk.setName(UPDATED_NAME);
        heritageWalk.setBackground(UPDATED_BACKGROUND);
        HeritageWalkDTO heritageWalkDTO = heritageWalkMapper.heritageWalkToHeritageWalkDTO(heritageWalk);

        restHeritageWalkMockMvc.perform(put("/api/heritageWalks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageWalkDTO)))
                .andExpect(status().isOk());

        // Validate the HeritageWalk in the database
        List<HeritageWalk> heritageWalks = heritageWalkRepository.findAll();
        assertThat(heritageWalks).hasSize(databaseSizeBeforeUpdate);
        HeritageWalk testHeritageWalk = heritageWalks.get(heritageWalks.size() - 1);
        assertThat(testHeritageWalk.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHeritageWalk.getBackground()).isEqualTo(UPDATED_BACKGROUND);
    }

    @Test
    @Transactional
    public void deleteHeritageWalk() throws Exception {
        // Initialize the database
        heritageWalkRepository.saveAndFlush(heritageWalk);

		int databaseSizeBeforeDelete = heritageWalkRepository.findAll().size();

        // Get the heritageWalk
        restHeritageWalkMockMvc.perform(delete("/api/heritageWalks/{id}", heritageWalk.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HeritageWalk> heritageWalks = heritageWalkRepository.findAll();
        assertThat(heritageWalks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
