package org.janastu.heritageapp.web.rest;

import org.janastu.heritageapp.Application;
import org.janastu.heritageapp.domain.HeritageRegionName;
import org.janastu.heritageapp.repository.HeritageRegionNameRepository;
import org.janastu.heritageapp.service.HeritageRegionNameService;
import org.janastu.heritageapp.web.rest.dto.HeritageRegionNameDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageRegionNameMapper;

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
 * Test class for the HeritageRegionNameResource REST controller.
 *
 * @see HeritageRegionNameResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HeritageRegionNameResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Double DEFAULT_CENTER_LATITUDE = 1D;
    private static final Double UPDATED_CENTER_LATITUDE = 2D;

    private static final Double DEFAULT_CENTER_LONGITUDE = 1D;
    private static final Double UPDATED_CENTER_LONGITUDE = 2D;

    private static final Double DEFAULT_TOP_LATITUDE = 1D;
    private static final Double UPDATED_TOP_LATITUDE = 2D;

    private static final Double DEFAULT_TOP_LONGITUDE = 1D;
    private static final Double UPDATED_TOP_LONGITUDE = 2D;

    private static final Double DEFAULT_BOTTOM_LATITUDE = 1D;
    private static final Double UPDATED_BOTTOM_LATITUDE = 2D;

    private static final Double DEFAULT_BOTTOM_LONGITUDE = 1D;
    private static final Double UPDATED_BOTTOM_LONGITUDE = 2D;

    @Inject
    private HeritageRegionNameRepository heritageRegionNameRepository;

    @Inject
    private HeritageRegionNameMapper heritageRegionNameMapper;

    @Inject
    private HeritageRegionNameService heritageRegionNameService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHeritageRegionNameMockMvc;

    private HeritageRegionName heritageRegionName;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HeritageRegionNameResource heritageRegionNameResource = new HeritageRegionNameResource();
        ReflectionTestUtils.setField(heritageRegionNameResource, "heritageRegionNameService", heritageRegionNameService);
        ReflectionTestUtils.setField(heritageRegionNameResource, "heritageRegionNameMapper", heritageRegionNameMapper);
        this.restHeritageRegionNameMockMvc = MockMvcBuilders.standaloneSetup(heritageRegionNameResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        heritageRegionName = new HeritageRegionName();
        heritageRegionName.setName(DEFAULT_NAME);
        heritageRegionName.setCenterLatitude(DEFAULT_CENTER_LATITUDE);
        heritageRegionName.setCenterLongitude(DEFAULT_CENTER_LONGITUDE);
        heritageRegionName.setTopLatitude(DEFAULT_TOP_LATITUDE);
        heritageRegionName.setTopLongitude(DEFAULT_TOP_LONGITUDE);
        heritageRegionName.setBottomLatitude(DEFAULT_BOTTOM_LATITUDE);
        heritageRegionName.setBottomLongitude(DEFAULT_BOTTOM_LONGITUDE);
    }

    @Test
    @Transactional
    public void createHeritageRegionName() throws Exception {
        int databaseSizeBeforeCreate = heritageRegionNameRepository.findAll().size();

        // Create the HeritageRegionName
        HeritageRegionNameDTO heritageRegionNameDTO = heritageRegionNameMapper.heritageRegionNameToHeritageRegionNameDTO(heritageRegionName);

        restHeritageRegionNameMockMvc.perform(post("/api/heritageRegionNames")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageRegionNameDTO)))
                .andExpect(status().isCreated());

        // Validate the HeritageRegionName in the database
        List<HeritageRegionName> heritageRegionNames = heritageRegionNameRepository.findAll();
        assertThat(heritageRegionNames).hasSize(databaseSizeBeforeCreate + 1);
        HeritageRegionName testHeritageRegionName = heritageRegionNames.get(heritageRegionNames.size() - 1);
        assertThat(testHeritageRegionName.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHeritageRegionName.getCenterLatitude()).isEqualTo(DEFAULT_CENTER_LATITUDE);
        assertThat(testHeritageRegionName.getCenterLongitude()).isEqualTo(DEFAULT_CENTER_LONGITUDE);
        assertThat(testHeritageRegionName.getTopLatitude()).isEqualTo(DEFAULT_TOP_LATITUDE);
        assertThat(testHeritageRegionName.getTopLongitude()).isEqualTo(DEFAULT_TOP_LONGITUDE);
        assertThat(testHeritageRegionName.getBottomLatitude()).isEqualTo(DEFAULT_BOTTOM_LATITUDE);
        assertThat(testHeritageRegionName.getBottomLongitude()).isEqualTo(DEFAULT_BOTTOM_LONGITUDE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageRegionNameRepository.findAll().size();
        // set the field null
        heritageRegionName.setName(null);

        // Create the HeritageRegionName, which fails.
        HeritageRegionNameDTO heritageRegionNameDTO = heritageRegionNameMapper.heritageRegionNameToHeritageRegionNameDTO(heritageRegionName);

        restHeritageRegionNameMockMvc.perform(post("/api/heritageRegionNames")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageRegionNameDTO)))
                .andExpect(status().isBadRequest());

        List<HeritageRegionName> heritageRegionNames = heritageRegionNameRepository.findAll();
        assertThat(heritageRegionNames).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCenterLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageRegionNameRepository.findAll().size();
        // set the field null
        heritageRegionName.setCenterLatitude(null);

        // Create the HeritageRegionName, which fails.
        HeritageRegionNameDTO heritageRegionNameDTO = heritageRegionNameMapper.heritageRegionNameToHeritageRegionNameDTO(heritageRegionName);

        restHeritageRegionNameMockMvc.perform(post("/api/heritageRegionNames")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageRegionNameDTO)))
                .andExpect(status().isBadRequest());

        List<HeritageRegionName> heritageRegionNames = heritageRegionNameRepository.findAll();
        assertThat(heritageRegionNames).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCenterLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageRegionNameRepository.findAll().size();
        // set the field null
        heritageRegionName.setCenterLongitude(null);

        // Create the HeritageRegionName, which fails.
        HeritageRegionNameDTO heritageRegionNameDTO = heritageRegionNameMapper.heritageRegionNameToHeritageRegionNameDTO(heritageRegionName);

        restHeritageRegionNameMockMvc.perform(post("/api/heritageRegionNames")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageRegionNameDTO)))
                .andExpect(status().isBadRequest());

        List<HeritageRegionName> heritageRegionNames = heritageRegionNameRepository.findAll();
        assertThat(heritageRegionNames).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageRegionNameRepository.findAll().size();
        // set the field null
        heritageRegionName.setTopLatitude(null);

        // Create the HeritageRegionName, which fails.
        HeritageRegionNameDTO heritageRegionNameDTO = heritageRegionNameMapper.heritageRegionNameToHeritageRegionNameDTO(heritageRegionName);

        restHeritageRegionNameMockMvc.perform(post("/api/heritageRegionNames")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageRegionNameDTO)))
                .andExpect(status().isBadRequest());

        List<HeritageRegionName> heritageRegionNames = heritageRegionNameRepository.findAll();
        assertThat(heritageRegionNames).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageRegionNameRepository.findAll().size();
        // set the field null
        heritageRegionName.setTopLongitude(null);

        // Create the HeritageRegionName, which fails.
        HeritageRegionNameDTO heritageRegionNameDTO = heritageRegionNameMapper.heritageRegionNameToHeritageRegionNameDTO(heritageRegionName);

        restHeritageRegionNameMockMvc.perform(post("/api/heritageRegionNames")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageRegionNameDTO)))
                .andExpect(status().isBadRequest());

        List<HeritageRegionName> heritageRegionNames = heritageRegionNameRepository.findAll();
        assertThat(heritageRegionNames).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBottomLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageRegionNameRepository.findAll().size();
        // set the field null
        heritageRegionName.setBottomLatitude(null);

        // Create the HeritageRegionName, which fails.
        HeritageRegionNameDTO heritageRegionNameDTO = heritageRegionNameMapper.heritageRegionNameToHeritageRegionNameDTO(heritageRegionName);

        restHeritageRegionNameMockMvc.perform(post("/api/heritageRegionNames")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageRegionNameDTO)))
                .andExpect(status().isBadRequest());

        List<HeritageRegionName> heritageRegionNames = heritageRegionNameRepository.findAll();
        assertThat(heritageRegionNames).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBottomLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageRegionNameRepository.findAll().size();
        // set the field null
        heritageRegionName.setBottomLongitude(null);

        // Create the HeritageRegionName, which fails.
        HeritageRegionNameDTO heritageRegionNameDTO = heritageRegionNameMapper.heritageRegionNameToHeritageRegionNameDTO(heritageRegionName);

        restHeritageRegionNameMockMvc.perform(post("/api/heritageRegionNames")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageRegionNameDTO)))
                .andExpect(status().isBadRequest());

        List<HeritageRegionName> heritageRegionNames = heritageRegionNameRepository.findAll();
        assertThat(heritageRegionNames).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHeritageRegionNames() throws Exception {
        // Initialize the database
        heritageRegionNameRepository.saveAndFlush(heritageRegionName);

        // Get all the heritageRegionNames
        restHeritageRegionNameMockMvc.perform(get("/api/heritageRegionNames?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(heritageRegionName.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].centerLatitude").value(hasItem(DEFAULT_CENTER_LATITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].centerLongitude").value(hasItem(DEFAULT_CENTER_LONGITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].topLatitude").value(hasItem(DEFAULT_TOP_LATITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].topLongitude").value(hasItem(DEFAULT_TOP_LONGITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].bottomLatitude").value(hasItem(DEFAULT_BOTTOM_LATITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].bottomLongitude").value(hasItem(DEFAULT_BOTTOM_LONGITUDE.doubleValue())));
    }

    @Test
    @Transactional
    public void getHeritageRegionName() throws Exception {
        // Initialize the database
        heritageRegionNameRepository.saveAndFlush(heritageRegionName);

        // Get the heritageRegionName
        restHeritageRegionNameMockMvc.perform(get("/api/heritageRegionNames/{id}", heritageRegionName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(heritageRegionName.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.centerLatitude").value(DEFAULT_CENTER_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.centerLongitude").value(DEFAULT_CENTER_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.topLatitude").value(DEFAULT_TOP_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.topLongitude").value(DEFAULT_TOP_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.bottomLatitude").value(DEFAULT_BOTTOM_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.bottomLongitude").value(DEFAULT_BOTTOM_LONGITUDE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHeritageRegionName() throws Exception {
        // Get the heritageRegionName
        restHeritageRegionNameMockMvc.perform(get("/api/heritageRegionNames/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHeritageRegionName() throws Exception {
        // Initialize the database
        heritageRegionNameRepository.saveAndFlush(heritageRegionName);

		int databaseSizeBeforeUpdate = heritageRegionNameRepository.findAll().size();

        // Update the heritageRegionName
        heritageRegionName.setName(UPDATED_NAME);
        heritageRegionName.setCenterLatitude(UPDATED_CENTER_LATITUDE);
        heritageRegionName.setCenterLongitude(UPDATED_CENTER_LONGITUDE);
        heritageRegionName.setTopLatitude(UPDATED_TOP_LATITUDE);
        heritageRegionName.setTopLongitude(UPDATED_TOP_LONGITUDE);
        heritageRegionName.setBottomLatitude(UPDATED_BOTTOM_LATITUDE);
        heritageRegionName.setBottomLongitude(UPDATED_BOTTOM_LONGITUDE);
        HeritageRegionNameDTO heritageRegionNameDTO = heritageRegionNameMapper.heritageRegionNameToHeritageRegionNameDTO(heritageRegionName);

        restHeritageRegionNameMockMvc.perform(put("/api/heritageRegionNames")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageRegionNameDTO)))
                .andExpect(status().isOk());

        // Validate the HeritageRegionName in the database
        List<HeritageRegionName> heritageRegionNames = heritageRegionNameRepository.findAll();
        assertThat(heritageRegionNames).hasSize(databaseSizeBeforeUpdate);
        HeritageRegionName testHeritageRegionName = heritageRegionNames.get(heritageRegionNames.size() - 1);
        assertThat(testHeritageRegionName.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHeritageRegionName.getCenterLatitude()).isEqualTo(UPDATED_CENTER_LATITUDE);
        assertThat(testHeritageRegionName.getCenterLongitude()).isEqualTo(UPDATED_CENTER_LONGITUDE);
        assertThat(testHeritageRegionName.getTopLatitude()).isEqualTo(UPDATED_TOP_LATITUDE);
        assertThat(testHeritageRegionName.getTopLongitude()).isEqualTo(UPDATED_TOP_LONGITUDE);
        assertThat(testHeritageRegionName.getBottomLatitude()).isEqualTo(UPDATED_BOTTOM_LATITUDE);
        assertThat(testHeritageRegionName.getBottomLongitude()).isEqualTo(UPDATED_BOTTOM_LONGITUDE);
    }

    @Test
    @Transactional
    public void deleteHeritageRegionName() throws Exception {
        // Initialize the database
        heritageRegionNameRepository.saveAndFlush(heritageRegionName);

		int databaseSizeBeforeDelete = heritageRegionNameRepository.findAll().size();

        // Get the heritageRegionName
        restHeritageRegionNameMockMvc.perform(delete("/api/heritageRegionNames/{id}", heritageRegionName.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HeritageRegionName> heritageRegionNames = heritageRegionNameRepository.findAll();
        assertThat(heritageRegionNames).hasSize(databaseSizeBeforeDelete - 1);
    }
}
