package org.janastu.heritageapp.web.rest;

import org.janastu.heritageapp.Application;
import org.janastu.heritageapp.domain.AudioGeoTagHeritageEntity;
import org.janastu.heritageapp.repository.AudioGeoTagHeritageEntityRepository;
import org.janastu.heritageapp.service.AudioGeoTagHeritageEntityService;
import org.janastu.heritageapp.web.rest.dto.AudioGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.mapper.AudioGeoTagHeritageEntityMapper;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the AudioGeoTagHeritageEntityResource REST controller.
 *
 * @see AudioGeoTagHeritageEntityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AudioGeoTagHeritageEntityResourceIntTest {

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
    private static final String DEFAULT_URL_ORFILE_LINK = "AAAAA";
    private static final String UPDATED_URL_ORFILE_LINK = "BBBBB";

    private static final byte[] DEFAULT_AUDIO_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_AUDIO_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_AUDIO_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_AUDIO_FILE_CONTENT_TYPE = "image/png";

    @Inject
    private AudioGeoTagHeritageEntityRepository audioGeoTagHeritageEntityRepository;

    @Inject
    private AudioGeoTagHeritageEntityMapper audioGeoTagHeritageEntityMapper;

    @Inject
    private AudioGeoTagHeritageEntityService audioGeoTagHeritageEntityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAudioGeoTagHeritageEntityMockMvc;

    private AudioGeoTagHeritageEntity audioGeoTagHeritageEntity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AudioGeoTagHeritageEntityResource audioGeoTagHeritageEntityResource = new AudioGeoTagHeritageEntityResource();
        ReflectionTestUtils.setField(audioGeoTagHeritageEntityResource, "audioGeoTagHeritageEntityService", audioGeoTagHeritageEntityService);
        ReflectionTestUtils.setField(audioGeoTagHeritageEntityResource, "audioGeoTagHeritageEntityMapper", audioGeoTagHeritageEntityMapper);
        this.restAudioGeoTagHeritageEntityMockMvc = MockMvcBuilders.standaloneSetup(audioGeoTagHeritageEntityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        audioGeoTagHeritageEntity = new AudioGeoTagHeritageEntity();
        audioGeoTagHeritageEntity.setTitle(DEFAULT_TITLE);
        audioGeoTagHeritageEntity.setDescription(DEFAULT_DESCRIPTION);
        audioGeoTagHeritageEntity.setAddress(DEFAULT_ADDRESS);
        audioGeoTagHeritageEntity.setLatitude(DEFAULT_LATITUDE);
        audioGeoTagHeritageEntity.setLongitude(DEFAULT_LONGITUDE);
        audioGeoTagHeritageEntity.setConsolidatedTags(DEFAULT_CONSOLIDATED_TAGS);
        audioGeoTagHeritageEntity.setUrlOrfileLink(DEFAULT_URL_ORFILE_LINK);
        audioGeoTagHeritageEntity.setAudioFile(DEFAULT_AUDIO_FILE);
        audioGeoTagHeritageEntity.setAudioFileContentType(DEFAULT_AUDIO_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createAudioGeoTagHeritageEntity() throws Exception {
        int databaseSizeBeforeCreate = audioGeoTagHeritageEntityRepository.findAll().size();

        // Create the AudioGeoTagHeritageEntity
        AudioGeoTagHeritageEntityDTO audioGeoTagHeritageEntityDTO = audioGeoTagHeritageEntityMapper.audioGeoTagHeritageEntityToAudioGeoTagHeritageEntityDTO(audioGeoTagHeritageEntity);

        restAudioGeoTagHeritageEntityMockMvc.perform(post("/api/audioGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(audioGeoTagHeritageEntityDTO)))
                .andExpect(status().isCreated());

        // Validate the AudioGeoTagHeritageEntity in the database
        List<AudioGeoTagHeritageEntity> audioGeoTagHeritageEntitys = audioGeoTagHeritageEntityRepository.findAll();
        assertThat(audioGeoTagHeritageEntitys).hasSize(databaseSizeBeforeCreate + 1);
        AudioGeoTagHeritageEntity testAudioGeoTagHeritageEntity = audioGeoTagHeritageEntitys.get(audioGeoTagHeritageEntitys.size() - 1);
        assertThat(testAudioGeoTagHeritageEntity.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAudioGeoTagHeritageEntity.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAudioGeoTagHeritageEntity.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testAudioGeoTagHeritageEntity.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testAudioGeoTagHeritageEntity.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testAudioGeoTagHeritageEntity.getConsolidatedTags()).isEqualTo(DEFAULT_CONSOLIDATED_TAGS);
        assertThat(testAudioGeoTagHeritageEntity.getUrlOrfileLink()).isEqualTo(DEFAULT_URL_ORFILE_LINK);
        assertThat(testAudioGeoTagHeritageEntity.getAudioFile()).isEqualTo(DEFAULT_AUDIO_FILE);
        assertThat(testAudioGeoTagHeritageEntity.getAudioFileContentType()).isEqualTo(DEFAULT_AUDIO_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = audioGeoTagHeritageEntityRepository.findAll().size();
        // set the field null
        audioGeoTagHeritageEntity.setTitle(null);

        // Create the AudioGeoTagHeritageEntity, which fails.
        AudioGeoTagHeritageEntityDTO audioGeoTagHeritageEntityDTO = audioGeoTagHeritageEntityMapper.audioGeoTagHeritageEntityToAudioGeoTagHeritageEntityDTO(audioGeoTagHeritageEntity);

        restAudioGeoTagHeritageEntityMockMvc.perform(post("/api/audioGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(audioGeoTagHeritageEntityDTO)))
                .andExpect(status().isBadRequest());

        List<AudioGeoTagHeritageEntity> audioGeoTagHeritageEntitys = audioGeoTagHeritageEntityRepository.findAll();
        assertThat(audioGeoTagHeritageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = audioGeoTagHeritageEntityRepository.findAll().size();
        // set the field null
        audioGeoTagHeritageEntity.setDescription(null);

        // Create the AudioGeoTagHeritageEntity, which fails.
        AudioGeoTagHeritageEntityDTO audioGeoTagHeritageEntityDTO = audioGeoTagHeritageEntityMapper.audioGeoTagHeritageEntityToAudioGeoTagHeritageEntityDTO(audioGeoTagHeritageEntity);

        restAudioGeoTagHeritageEntityMockMvc.perform(post("/api/audioGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(audioGeoTagHeritageEntityDTO)))
                .andExpect(status().isBadRequest());

        List<AudioGeoTagHeritageEntity> audioGeoTagHeritageEntitys = audioGeoTagHeritageEntityRepository.findAll();
        assertThat(audioGeoTagHeritageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = audioGeoTagHeritageEntityRepository.findAll().size();
        // set the field null
        audioGeoTagHeritageEntity.setLatitude(null);

        // Create the AudioGeoTagHeritageEntity, which fails.
        AudioGeoTagHeritageEntityDTO audioGeoTagHeritageEntityDTO = audioGeoTagHeritageEntityMapper.audioGeoTagHeritageEntityToAudioGeoTagHeritageEntityDTO(audioGeoTagHeritageEntity);

        restAudioGeoTagHeritageEntityMockMvc.perform(post("/api/audioGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(audioGeoTagHeritageEntityDTO)))
                .andExpect(status().isBadRequest());

        List<AudioGeoTagHeritageEntity> audioGeoTagHeritageEntitys = audioGeoTagHeritageEntityRepository.findAll();
        assertThat(audioGeoTagHeritageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = audioGeoTagHeritageEntityRepository.findAll().size();
        // set the field null
        audioGeoTagHeritageEntity.setLongitude(null);

        // Create the AudioGeoTagHeritageEntity, which fails.
        AudioGeoTagHeritageEntityDTO audioGeoTagHeritageEntityDTO = audioGeoTagHeritageEntityMapper.audioGeoTagHeritageEntityToAudioGeoTagHeritageEntityDTO(audioGeoTagHeritageEntity);

        restAudioGeoTagHeritageEntityMockMvc.perform(post("/api/audioGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(audioGeoTagHeritageEntityDTO)))
                .andExpect(status().isBadRequest());

        List<AudioGeoTagHeritageEntity> audioGeoTagHeritageEntitys = audioGeoTagHeritageEntityRepository.findAll();
        assertThat(audioGeoTagHeritageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAudioFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = audioGeoTagHeritageEntityRepository.findAll().size();
        // set the field null
        audioGeoTagHeritageEntity.setAudioFile(null);

        // Create the AudioGeoTagHeritageEntity, which fails.
        AudioGeoTagHeritageEntityDTO audioGeoTagHeritageEntityDTO = audioGeoTagHeritageEntityMapper.audioGeoTagHeritageEntityToAudioGeoTagHeritageEntityDTO(audioGeoTagHeritageEntity);

        restAudioGeoTagHeritageEntityMockMvc.perform(post("/api/audioGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(audioGeoTagHeritageEntityDTO)))
                .andExpect(status().isBadRequest());

        List<AudioGeoTagHeritageEntity> audioGeoTagHeritageEntitys = audioGeoTagHeritageEntityRepository.findAll();
        assertThat(audioGeoTagHeritageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAudioGeoTagHeritageEntitys() throws Exception {
        // Initialize the database
        audioGeoTagHeritageEntityRepository.saveAndFlush(audioGeoTagHeritageEntity);

        // Get all the audioGeoTagHeritageEntitys
        restAudioGeoTagHeritageEntityMockMvc.perform(get("/api/audioGeoTagHeritageEntitys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(audioGeoTagHeritageEntity.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].consolidatedTags").value(hasItem(DEFAULT_CONSOLIDATED_TAGS.toString())))
                .andExpect(jsonPath("$.[*].urlOrfileLink").value(hasItem(DEFAULT_URL_ORFILE_LINK.toString())))
                .andExpect(jsonPath("$.[*].audioFileContentType").value(hasItem(DEFAULT_AUDIO_FILE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].audioFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_AUDIO_FILE))));
    }

    @Test
    @Transactional
    public void getAudioGeoTagHeritageEntity() throws Exception {
        // Initialize the database
        audioGeoTagHeritageEntityRepository.saveAndFlush(audioGeoTagHeritageEntity);

        // Get the audioGeoTagHeritageEntity
        restAudioGeoTagHeritageEntityMockMvc.perform(get("/api/audioGeoTagHeritageEntitys/{id}", audioGeoTagHeritageEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(audioGeoTagHeritageEntity.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.consolidatedTags").value(DEFAULT_CONSOLIDATED_TAGS.toString()))
            .andExpect(jsonPath("$.urlOrfileLink").value(DEFAULT_URL_ORFILE_LINK.toString()))
            .andExpect(jsonPath("$.audioFileContentType").value(DEFAULT_AUDIO_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.audioFile").value(Base64Utils.encodeToString(DEFAULT_AUDIO_FILE)));
    }

    @Test
    @Transactional
    public void getNonExistingAudioGeoTagHeritageEntity() throws Exception {
        // Get the audioGeoTagHeritageEntity
        restAudioGeoTagHeritageEntityMockMvc.perform(get("/api/audioGeoTagHeritageEntitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAudioGeoTagHeritageEntity() throws Exception {
        // Initialize the database
        audioGeoTagHeritageEntityRepository.saveAndFlush(audioGeoTagHeritageEntity);

		int databaseSizeBeforeUpdate = audioGeoTagHeritageEntityRepository.findAll().size();

        // Update the audioGeoTagHeritageEntity
        audioGeoTagHeritageEntity.setTitle(UPDATED_TITLE);
        audioGeoTagHeritageEntity.setDescription(UPDATED_DESCRIPTION);
        audioGeoTagHeritageEntity.setAddress(UPDATED_ADDRESS);
        audioGeoTagHeritageEntity.setLatitude(UPDATED_LATITUDE);
        audioGeoTagHeritageEntity.setLongitude(UPDATED_LONGITUDE);
        audioGeoTagHeritageEntity.setConsolidatedTags(UPDATED_CONSOLIDATED_TAGS);
        audioGeoTagHeritageEntity.setUrlOrfileLink(UPDATED_URL_ORFILE_LINK);
        audioGeoTagHeritageEntity.setAudioFile(UPDATED_AUDIO_FILE);
        audioGeoTagHeritageEntity.setAudioFileContentType(UPDATED_AUDIO_FILE_CONTENT_TYPE);
        AudioGeoTagHeritageEntityDTO audioGeoTagHeritageEntityDTO = audioGeoTagHeritageEntityMapper.audioGeoTagHeritageEntityToAudioGeoTagHeritageEntityDTO(audioGeoTagHeritageEntity);

        restAudioGeoTagHeritageEntityMockMvc.perform(put("/api/audioGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(audioGeoTagHeritageEntityDTO)))
                .andExpect(status().isOk());

        // Validate the AudioGeoTagHeritageEntity in the database
        List<AudioGeoTagHeritageEntity> audioGeoTagHeritageEntitys = audioGeoTagHeritageEntityRepository.findAll();
        assertThat(audioGeoTagHeritageEntitys).hasSize(databaseSizeBeforeUpdate);
        AudioGeoTagHeritageEntity testAudioGeoTagHeritageEntity = audioGeoTagHeritageEntitys.get(audioGeoTagHeritageEntitys.size() - 1);
        assertThat(testAudioGeoTagHeritageEntity.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAudioGeoTagHeritageEntity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAudioGeoTagHeritageEntity.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testAudioGeoTagHeritageEntity.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testAudioGeoTagHeritageEntity.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testAudioGeoTagHeritageEntity.getConsolidatedTags()).isEqualTo(UPDATED_CONSOLIDATED_TAGS);
        assertThat(testAudioGeoTagHeritageEntity.getUrlOrfileLink()).isEqualTo(UPDATED_URL_ORFILE_LINK);
        assertThat(testAudioGeoTagHeritageEntity.getAudioFile()).isEqualTo(UPDATED_AUDIO_FILE);
        assertThat(testAudioGeoTagHeritageEntity.getAudioFileContentType()).isEqualTo(UPDATED_AUDIO_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteAudioGeoTagHeritageEntity() throws Exception {
        // Initialize the database
        audioGeoTagHeritageEntityRepository.saveAndFlush(audioGeoTagHeritageEntity);

		int databaseSizeBeforeDelete = audioGeoTagHeritageEntityRepository.findAll().size();

        // Get the audioGeoTagHeritageEntity
        restAudioGeoTagHeritageEntityMockMvc.perform(delete("/api/audioGeoTagHeritageEntitys/{id}", audioGeoTagHeritageEntity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AudioGeoTagHeritageEntity> audioGeoTagHeritageEntitys = audioGeoTagHeritageEntityRepository.findAll();
        assertThat(audioGeoTagHeritageEntitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
