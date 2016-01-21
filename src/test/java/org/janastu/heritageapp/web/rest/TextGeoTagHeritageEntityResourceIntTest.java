package org.janastu.heritageapp.web.rest;

import org.janastu.heritageapp.Application;
import org.janastu.heritageapp.domain.TextGeoTagHeritageEntity;
import org.janastu.heritageapp.repository.TextGeoTagHeritageEntityRepository;
import org.janastu.heritageapp.service.TextGeoTagHeritageEntityService;
import org.janastu.heritageapp.web.rest.dto.TextGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.mapper.TextGeoTagHeritageEntityMapper;

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
 * Test class for the TextGeoTagHeritageEntityResource REST controller.
 *
 * @see TextGeoTagHeritageEntityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TextGeoTagHeritageEntityResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;
    private static final String DEFAULT_CONSOLIDATED_TAGS = "AAAAA";
    private static final String UPDATED_CONSOLIDATED_TAGS = "BBBBB";
    private static final String DEFAULT_TEXT_DETAILS = "AAAAA";
    private static final String UPDATED_TEXT_DETAILS = "BBBBB";

    @Inject
    private TextGeoTagHeritageEntityRepository textGeoTagHeritageEntityRepository;

    @Inject
    private TextGeoTagHeritageEntityMapper textGeoTagHeritageEntityMapper;

    @Inject
    private TextGeoTagHeritageEntityService textGeoTagHeritageEntityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTextGeoTagHeritageEntityMockMvc;

    private TextGeoTagHeritageEntity textGeoTagHeritageEntity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TextGeoTagHeritageEntityResource textGeoTagHeritageEntityResource = new TextGeoTagHeritageEntityResource();
        ReflectionTestUtils.setField(textGeoTagHeritageEntityResource, "textGeoTagHeritageEntityService", textGeoTagHeritageEntityService);
        ReflectionTestUtils.setField(textGeoTagHeritageEntityResource, "textGeoTagHeritageEntityMapper", textGeoTagHeritageEntityMapper);
        this.restTextGeoTagHeritageEntityMockMvc = MockMvcBuilders.standaloneSetup(textGeoTagHeritageEntityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        textGeoTagHeritageEntity = new TextGeoTagHeritageEntity();
        textGeoTagHeritageEntity.setTitle(DEFAULT_TITLE);
        textGeoTagHeritageEntity.setDescription(DEFAULT_DESCRIPTION);
        textGeoTagHeritageEntity.setAddress(DEFAULT_ADDRESS);
        textGeoTagHeritageEntity.setLatitude(DEFAULT_LATITUDE);
        textGeoTagHeritageEntity.setLongitude(DEFAULT_LONGITUDE);
        textGeoTagHeritageEntity.setConsolidatedTags(DEFAULT_CONSOLIDATED_TAGS);
        textGeoTagHeritageEntity.setTextDetails(DEFAULT_TEXT_DETAILS);
    }

    @Test
    @Transactional
    public void createTextGeoTagHeritageEntity() throws Exception {
        int databaseSizeBeforeCreate = textGeoTagHeritageEntityRepository.findAll().size();

        // Create the TextGeoTagHeritageEntity
        TextGeoTagHeritageEntityDTO textGeoTagHeritageEntityDTO = textGeoTagHeritageEntityMapper.textGeoTagHeritageEntityToTextGeoTagHeritageEntityDTO(textGeoTagHeritageEntity);

        restTextGeoTagHeritageEntityMockMvc.perform(post("/api/textGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(textGeoTagHeritageEntityDTO)))
                .andExpect(status().isCreated());

        // Validate the TextGeoTagHeritageEntity in the database
        List<TextGeoTagHeritageEntity> textGeoTagHeritageEntitys = textGeoTagHeritageEntityRepository.findAll();
        assertThat(textGeoTagHeritageEntitys).hasSize(databaseSizeBeforeCreate + 1);
        TextGeoTagHeritageEntity testTextGeoTagHeritageEntity = textGeoTagHeritageEntitys.get(textGeoTagHeritageEntitys.size() - 1);
        assertThat(testTextGeoTagHeritageEntity.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTextGeoTagHeritageEntity.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTextGeoTagHeritageEntity.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testTextGeoTagHeritageEntity.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testTextGeoTagHeritageEntity.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testTextGeoTagHeritageEntity.getConsolidatedTags()).isEqualTo(DEFAULT_CONSOLIDATED_TAGS);
        assertThat(testTextGeoTagHeritageEntity.getTextDetails()).isEqualTo(DEFAULT_TEXT_DETAILS);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = textGeoTagHeritageEntityRepository.findAll().size();
        // set the field null
        textGeoTagHeritageEntity.setTitle(null);

        // Create the TextGeoTagHeritageEntity, which fails.
        TextGeoTagHeritageEntityDTO textGeoTagHeritageEntityDTO = textGeoTagHeritageEntityMapper.textGeoTagHeritageEntityToTextGeoTagHeritageEntityDTO(textGeoTagHeritageEntity);

        restTextGeoTagHeritageEntityMockMvc.perform(post("/api/textGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(textGeoTagHeritageEntityDTO)))
                .andExpect(status().isBadRequest());

        List<TextGeoTagHeritageEntity> textGeoTagHeritageEntitys = textGeoTagHeritageEntityRepository.findAll();
        assertThat(textGeoTagHeritageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = textGeoTagHeritageEntityRepository.findAll().size();
        // set the field null
        textGeoTagHeritageEntity.setDescription(null);

        // Create the TextGeoTagHeritageEntity, which fails.
        TextGeoTagHeritageEntityDTO textGeoTagHeritageEntityDTO = textGeoTagHeritageEntityMapper.textGeoTagHeritageEntityToTextGeoTagHeritageEntityDTO(textGeoTagHeritageEntity);

        restTextGeoTagHeritageEntityMockMvc.perform(post("/api/textGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(textGeoTagHeritageEntityDTO)))
                .andExpect(status().isBadRequest());

        List<TextGeoTagHeritageEntity> textGeoTagHeritageEntitys = textGeoTagHeritageEntityRepository.findAll();
        assertThat(textGeoTagHeritageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = textGeoTagHeritageEntityRepository.findAll().size();
        // set the field null
        textGeoTagHeritageEntity.setLatitude(null);

        // Create the TextGeoTagHeritageEntity, which fails.
        TextGeoTagHeritageEntityDTO textGeoTagHeritageEntityDTO = textGeoTagHeritageEntityMapper.textGeoTagHeritageEntityToTextGeoTagHeritageEntityDTO(textGeoTagHeritageEntity);

        restTextGeoTagHeritageEntityMockMvc.perform(post("/api/textGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(textGeoTagHeritageEntityDTO)))
                .andExpect(status().isBadRequest());

        List<TextGeoTagHeritageEntity> textGeoTagHeritageEntitys = textGeoTagHeritageEntityRepository.findAll();
        assertThat(textGeoTagHeritageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = textGeoTagHeritageEntityRepository.findAll().size();
        // set the field null
        textGeoTagHeritageEntity.setLongitude(null);

        // Create the TextGeoTagHeritageEntity, which fails.
        TextGeoTagHeritageEntityDTO textGeoTagHeritageEntityDTO = textGeoTagHeritageEntityMapper.textGeoTagHeritageEntityToTextGeoTagHeritageEntityDTO(textGeoTagHeritageEntity);

        restTextGeoTagHeritageEntityMockMvc.perform(post("/api/textGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(textGeoTagHeritageEntityDTO)))
                .andExpect(status().isBadRequest());

        List<TextGeoTagHeritageEntity> textGeoTagHeritageEntitys = textGeoTagHeritageEntityRepository.findAll();
        assertThat(textGeoTagHeritageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTextGeoTagHeritageEntitys() throws Exception {
        // Initialize the database
        textGeoTagHeritageEntityRepository.saveAndFlush(textGeoTagHeritageEntity);

        // Get all the textGeoTagHeritageEntitys
        restTextGeoTagHeritageEntityMockMvc.perform(get("/api/textGeoTagHeritageEntitys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(textGeoTagHeritageEntity.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].consolidatedTags").value(hasItem(DEFAULT_CONSOLIDATED_TAGS.toString())))
                .andExpect(jsonPath("$.[*].textDetails").value(hasItem(DEFAULT_TEXT_DETAILS.toString())));
    }

    @Test
    @Transactional
    public void getTextGeoTagHeritageEntity() throws Exception {
        // Initialize the database
        textGeoTagHeritageEntityRepository.saveAndFlush(textGeoTagHeritageEntity);

        // Get the textGeoTagHeritageEntity
        restTextGeoTagHeritageEntityMockMvc.perform(get("/api/textGeoTagHeritageEntitys/{id}", textGeoTagHeritageEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(textGeoTagHeritageEntity.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.consolidatedTags").value(DEFAULT_CONSOLIDATED_TAGS.toString()))
            .andExpect(jsonPath("$.textDetails").value(DEFAULT_TEXT_DETAILS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTextGeoTagHeritageEntity() throws Exception {
        // Get the textGeoTagHeritageEntity
        restTextGeoTagHeritageEntityMockMvc.perform(get("/api/textGeoTagHeritageEntitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTextGeoTagHeritageEntity() throws Exception {
        // Initialize the database
        textGeoTagHeritageEntityRepository.saveAndFlush(textGeoTagHeritageEntity);

		int databaseSizeBeforeUpdate = textGeoTagHeritageEntityRepository.findAll().size();

        // Update the textGeoTagHeritageEntity
        textGeoTagHeritageEntity.setTitle(UPDATED_TITLE);
        textGeoTagHeritageEntity.setDescription(UPDATED_DESCRIPTION);
        textGeoTagHeritageEntity.setAddress(UPDATED_ADDRESS);
        textGeoTagHeritageEntity.setLatitude(UPDATED_LATITUDE);
        textGeoTagHeritageEntity.setLongitude(UPDATED_LONGITUDE);
        textGeoTagHeritageEntity.setConsolidatedTags(UPDATED_CONSOLIDATED_TAGS);
        textGeoTagHeritageEntity.setTextDetails(UPDATED_TEXT_DETAILS);
        TextGeoTagHeritageEntityDTO textGeoTagHeritageEntityDTO = textGeoTagHeritageEntityMapper.textGeoTagHeritageEntityToTextGeoTagHeritageEntityDTO(textGeoTagHeritageEntity);

        restTextGeoTagHeritageEntityMockMvc.perform(put("/api/textGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(textGeoTagHeritageEntityDTO)))
                .andExpect(status().isOk());

        // Validate the TextGeoTagHeritageEntity in the database
        List<TextGeoTagHeritageEntity> textGeoTagHeritageEntitys = textGeoTagHeritageEntityRepository.findAll();
        assertThat(textGeoTagHeritageEntitys).hasSize(databaseSizeBeforeUpdate);
        TextGeoTagHeritageEntity testTextGeoTagHeritageEntity = textGeoTagHeritageEntitys.get(textGeoTagHeritageEntitys.size() - 1);
        assertThat(testTextGeoTagHeritageEntity.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTextGeoTagHeritageEntity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTextGeoTagHeritageEntity.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testTextGeoTagHeritageEntity.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testTextGeoTagHeritageEntity.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testTextGeoTagHeritageEntity.getConsolidatedTags()).isEqualTo(UPDATED_CONSOLIDATED_TAGS);
        assertThat(testTextGeoTagHeritageEntity.getTextDetails()).isEqualTo(UPDATED_TEXT_DETAILS);
    }

    @Test
    @Transactional
    public void deleteTextGeoTagHeritageEntity() throws Exception {
        // Initialize the database
        textGeoTagHeritageEntityRepository.saveAndFlush(textGeoTagHeritageEntity);

		int databaseSizeBeforeDelete = textGeoTagHeritageEntityRepository.findAll().size();

        // Get the textGeoTagHeritageEntity
        restTextGeoTagHeritageEntityMockMvc.perform(delete("/api/textGeoTagHeritageEntitys/{id}", textGeoTagHeritageEntity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TextGeoTagHeritageEntity> textGeoTagHeritageEntitys = textGeoTagHeritageEntityRepository.findAll();
        assertThat(textGeoTagHeritageEntitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
