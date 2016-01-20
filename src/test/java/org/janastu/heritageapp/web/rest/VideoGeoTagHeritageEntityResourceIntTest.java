package org.janastu.heritageapp.web.rest;

import org.janastu.heritageapp.Application;
import org.janastu.heritageapp.domain.VideoGeoTagHeritageEntity;
import org.janastu.heritageapp.repository.VideoGeoTagHeritageEntityRepository;
import org.janastu.heritageapp.service.VideoGeoTagHeritageEntityService;
import org.janastu.heritageapp.web.rest.dto.VideoGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.mapper.VideoGeoTagHeritageEntityMapper;

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
 * Test class for the VideoGeoTagHeritageEntityResource REST controller.
 *
 * @see VideoGeoTagHeritageEntityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class VideoGeoTagHeritageEntityResourceIntTest {

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

    private static final byte[] DEFAULT_VIDEO_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_VIDEO_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_VIDEO_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_VIDEO_FILE_CONTENT_TYPE = "image/png";

    @Inject
    private VideoGeoTagHeritageEntityRepository videoGeoTagHeritageEntityRepository;

    @Inject
    private VideoGeoTagHeritageEntityMapper videoGeoTagHeritageEntityMapper;

    @Inject
    private VideoGeoTagHeritageEntityService videoGeoTagHeritageEntityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVideoGeoTagHeritageEntityMockMvc;

    private VideoGeoTagHeritageEntity videoGeoTagHeritageEntity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VideoGeoTagHeritageEntityResource videoGeoTagHeritageEntityResource = new VideoGeoTagHeritageEntityResource();
        ReflectionTestUtils.setField(videoGeoTagHeritageEntityResource, "videoGeoTagHeritageEntityService", videoGeoTagHeritageEntityService);
        ReflectionTestUtils.setField(videoGeoTagHeritageEntityResource, "videoGeoTagHeritageEntityMapper", videoGeoTagHeritageEntityMapper);
        this.restVideoGeoTagHeritageEntityMockMvc = MockMvcBuilders.standaloneSetup(videoGeoTagHeritageEntityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        videoGeoTagHeritageEntity = new VideoGeoTagHeritageEntity();
        videoGeoTagHeritageEntity.setTitle(DEFAULT_TITLE);
        videoGeoTagHeritageEntity.setDescription(DEFAULT_DESCRIPTION);
        videoGeoTagHeritageEntity.setAddress(DEFAULT_ADDRESS);
        videoGeoTagHeritageEntity.setLatitude(DEFAULT_LATITUDE);
        videoGeoTagHeritageEntity.setLongitude(DEFAULT_LONGITUDE);
        videoGeoTagHeritageEntity.setConsolidatedTags(DEFAULT_CONSOLIDATED_TAGS);
        videoGeoTagHeritageEntity.setUrlOrfileLink(DEFAULT_URL_ORFILE_LINK);
        videoGeoTagHeritageEntity.setVideoFile(DEFAULT_VIDEO_FILE);
        videoGeoTagHeritageEntity.setVideoFileContentType(DEFAULT_VIDEO_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createVideoGeoTagHeritageEntity() throws Exception {
        int databaseSizeBeforeCreate = videoGeoTagHeritageEntityRepository.findAll().size();

        // Create the VideoGeoTagHeritageEntity
        VideoGeoTagHeritageEntityDTO videoGeoTagHeritageEntityDTO = videoGeoTagHeritageEntityMapper.videoGeoTagHeritageEntityToVideoGeoTagHeritageEntityDTO(videoGeoTagHeritageEntity);

        restVideoGeoTagHeritageEntityMockMvc.perform(post("/api/videoGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(videoGeoTagHeritageEntityDTO)))
                .andExpect(status().isCreated());

        // Validate the VideoGeoTagHeritageEntity in the database
        List<VideoGeoTagHeritageEntity> videoGeoTagHeritageEntitys = videoGeoTagHeritageEntityRepository.findAll();
        assertThat(videoGeoTagHeritageEntitys).hasSize(databaseSizeBeforeCreate + 1);
        VideoGeoTagHeritageEntity testVideoGeoTagHeritageEntity = videoGeoTagHeritageEntitys.get(videoGeoTagHeritageEntitys.size() - 1);
        assertThat(testVideoGeoTagHeritageEntity.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testVideoGeoTagHeritageEntity.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVideoGeoTagHeritageEntity.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testVideoGeoTagHeritageEntity.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testVideoGeoTagHeritageEntity.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testVideoGeoTagHeritageEntity.getConsolidatedTags()).isEqualTo(DEFAULT_CONSOLIDATED_TAGS);
        assertThat(testVideoGeoTagHeritageEntity.getUrlOrfileLink()).isEqualTo(DEFAULT_URL_ORFILE_LINK);
        assertThat(testVideoGeoTagHeritageEntity.getVideoFile()).isEqualTo(DEFAULT_VIDEO_FILE);
        assertThat(testVideoGeoTagHeritageEntity.getVideoFileContentType()).isEqualTo(DEFAULT_VIDEO_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoGeoTagHeritageEntityRepository.findAll().size();
        // set the field null
        videoGeoTagHeritageEntity.setTitle(null);

        // Create the VideoGeoTagHeritageEntity, which fails.
        VideoGeoTagHeritageEntityDTO videoGeoTagHeritageEntityDTO = videoGeoTagHeritageEntityMapper.videoGeoTagHeritageEntityToVideoGeoTagHeritageEntityDTO(videoGeoTagHeritageEntity);

        restVideoGeoTagHeritageEntityMockMvc.perform(post("/api/videoGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(videoGeoTagHeritageEntityDTO)))
                .andExpect(status().isBadRequest());

        List<VideoGeoTagHeritageEntity> videoGeoTagHeritageEntitys = videoGeoTagHeritageEntityRepository.findAll();
        assertThat(videoGeoTagHeritageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoGeoTagHeritageEntityRepository.findAll().size();
        // set the field null
        videoGeoTagHeritageEntity.setDescription(null);

        // Create the VideoGeoTagHeritageEntity, which fails.
        VideoGeoTagHeritageEntityDTO videoGeoTagHeritageEntityDTO = videoGeoTagHeritageEntityMapper.videoGeoTagHeritageEntityToVideoGeoTagHeritageEntityDTO(videoGeoTagHeritageEntity);

        restVideoGeoTagHeritageEntityMockMvc.perform(post("/api/videoGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(videoGeoTagHeritageEntityDTO)))
                .andExpect(status().isBadRequest());

        List<VideoGeoTagHeritageEntity> videoGeoTagHeritageEntitys = videoGeoTagHeritageEntityRepository.findAll();
        assertThat(videoGeoTagHeritageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoGeoTagHeritageEntityRepository.findAll().size();
        // set the field null
        videoGeoTagHeritageEntity.setLatitude(null);

        // Create the VideoGeoTagHeritageEntity, which fails.
        VideoGeoTagHeritageEntityDTO videoGeoTagHeritageEntityDTO = videoGeoTagHeritageEntityMapper.videoGeoTagHeritageEntityToVideoGeoTagHeritageEntityDTO(videoGeoTagHeritageEntity);

        restVideoGeoTagHeritageEntityMockMvc.perform(post("/api/videoGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(videoGeoTagHeritageEntityDTO)))
                .andExpect(status().isBadRequest());

        List<VideoGeoTagHeritageEntity> videoGeoTagHeritageEntitys = videoGeoTagHeritageEntityRepository.findAll();
        assertThat(videoGeoTagHeritageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoGeoTagHeritageEntityRepository.findAll().size();
        // set the field null
        videoGeoTagHeritageEntity.setLongitude(null);

        // Create the VideoGeoTagHeritageEntity, which fails.
        VideoGeoTagHeritageEntityDTO videoGeoTagHeritageEntityDTO = videoGeoTagHeritageEntityMapper.videoGeoTagHeritageEntityToVideoGeoTagHeritageEntityDTO(videoGeoTagHeritageEntity);

        restVideoGeoTagHeritageEntityMockMvc.perform(post("/api/videoGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(videoGeoTagHeritageEntityDTO)))
                .andExpect(status().isBadRequest());

        List<VideoGeoTagHeritageEntity> videoGeoTagHeritageEntitys = videoGeoTagHeritageEntityRepository.findAll();
        assertThat(videoGeoTagHeritageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVideoFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoGeoTagHeritageEntityRepository.findAll().size();
        // set the field null
        videoGeoTagHeritageEntity.setVideoFile(null);

        // Create the VideoGeoTagHeritageEntity, which fails.
        VideoGeoTagHeritageEntityDTO videoGeoTagHeritageEntityDTO = videoGeoTagHeritageEntityMapper.videoGeoTagHeritageEntityToVideoGeoTagHeritageEntityDTO(videoGeoTagHeritageEntity);

        restVideoGeoTagHeritageEntityMockMvc.perform(post("/api/videoGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(videoGeoTagHeritageEntityDTO)))
                .andExpect(status().isBadRequest());

        List<VideoGeoTagHeritageEntity> videoGeoTagHeritageEntitys = videoGeoTagHeritageEntityRepository.findAll();
        assertThat(videoGeoTagHeritageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVideoGeoTagHeritageEntitys() throws Exception {
        // Initialize the database
        videoGeoTagHeritageEntityRepository.saveAndFlush(videoGeoTagHeritageEntity);

        // Get all the videoGeoTagHeritageEntitys
        restVideoGeoTagHeritageEntityMockMvc.perform(get("/api/videoGeoTagHeritageEntitys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(videoGeoTagHeritageEntity.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].consolidatedTags").value(hasItem(DEFAULT_CONSOLIDATED_TAGS.toString())))
                .andExpect(jsonPath("$.[*].urlOrfileLink").value(hasItem(DEFAULT_URL_ORFILE_LINK.toString())))
                .andExpect(jsonPath("$.[*].videoFileContentType").value(hasItem(DEFAULT_VIDEO_FILE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].videoFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_VIDEO_FILE))));
    }

    @Test
    @Transactional
    public void getVideoGeoTagHeritageEntity() throws Exception {
        // Initialize the database
        videoGeoTagHeritageEntityRepository.saveAndFlush(videoGeoTagHeritageEntity);

        // Get the videoGeoTagHeritageEntity
        restVideoGeoTagHeritageEntityMockMvc.perform(get("/api/videoGeoTagHeritageEntitys/{id}", videoGeoTagHeritageEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(videoGeoTagHeritageEntity.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.consolidatedTags").value(DEFAULT_CONSOLIDATED_TAGS.toString()))
            .andExpect(jsonPath("$.urlOrfileLink").value(DEFAULT_URL_ORFILE_LINK.toString()))
            .andExpect(jsonPath("$.videoFileContentType").value(DEFAULT_VIDEO_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.videoFile").value(Base64Utils.encodeToString(DEFAULT_VIDEO_FILE)));
    }

    @Test
    @Transactional
    public void getNonExistingVideoGeoTagHeritageEntity() throws Exception {
        // Get the videoGeoTagHeritageEntity
        restVideoGeoTagHeritageEntityMockMvc.perform(get("/api/videoGeoTagHeritageEntitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVideoGeoTagHeritageEntity() throws Exception {
        // Initialize the database
        videoGeoTagHeritageEntityRepository.saveAndFlush(videoGeoTagHeritageEntity);

		int databaseSizeBeforeUpdate = videoGeoTagHeritageEntityRepository.findAll().size();

        // Update the videoGeoTagHeritageEntity
        videoGeoTagHeritageEntity.setTitle(UPDATED_TITLE);
        videoGeoTagHeritageEntity.setDescription(UPDATED_DESCRIPTION);
        videoGeoTagHeritageEntity.setAddress(UPDATED_ADDRESS);
        videoGeoTagHeritageEntity.setLatitude(UPDATED_LATITUDE);
        videoGeoTagHeritageEntity.setLongitude(UPDATED_LONGITUDE);
        videoGeoTagHeritageEntity.setConsolidatedTags(UPDATED_CONSOLIDATED_TAGS);
        videoGeoTagHeritageEntity.setUrlOrfileLink(UPDATED_URL_ORFILE_LINK);
        videoGeoTagHeritageEntity.setVideoFile(UPDATED_VIDEO_FILE);
        videoGeoTagHeritageEntity.setVideoFileContentType(UPDATED_VIDEO_FILE_CONTENT_TYPE);
        VideoGeoTagHeritageEntityDTO videoGeoTagHeritageEntityDTO = videoGeoTagHeritageEntityMapper.videoGeoTagHeritageEntityToVideoGeoTagHeritageEntityDTO(videoGeoTagHeritageEntity);

        restVideoGeoTagHeritageEntityMockMvc.perform(put("/api/videoGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(videoGeoTagHeritageEntityDTO)))
                .andExpect(status().isOk());

        // Validate the VideoGeoTagHeritageEntity in the database
        List<VideoGeoTagHeritageEntity> videoGeoTagHeritageEntitys = videoGeoTagHeritageEntityRepository.findAll();
        assertThat(videoGeoTagHeritageEntitys).hasSize(databaseSizeBeforeUpdate);
        VideoGeoTagHeritageEntity testVideoGeoTagHeritageEntity = videoGeoTagHeritageEntitys.get(videoGeoTagHeritageEntitys.size() - 1);
        assertThat(testVideoGeoTagHeritageEntity.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testVideoGeoTagHeritageEntity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVideoGeoTagHeritageEntity.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testVideoGeoTagHeritageEntity.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testVideoGeoTagHeritageEntity.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testVideoGeoTagHeritageEntity.getConsolidatedTags()).isEqualTo(UPDATED_CONSOLIDATED_TAGS);
        assertThat(testVideoGeoTagHeritageEntity.getUrlOrfileLink()).isEqualTo(UPDATED_URL_ORFILE_LINK);
        assertThat(testVideoGeoTagHeritageEntity.getVideoFile()).isEqualTo(UPDATED_VIDEO_FILE);
        assertThat(testVideoGeoTagHeritageEntity.getVideoFileContentType()).isEqualTo(UPDATED_VIDEO_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteVideoGeoTagHeritageEntity() throws Exception {
        // Initialize the database
        videoGeoTagHeritageEntityRepository.saveAndFlush(videoGeoTagHeritageEntity);

		int databaseSizeBeforeDelete = videoGeoTagHeritageEntityRepository.findAll().size();

        // Get the videoGeoTagHeritageEntity
        restVideoGeoTagHeritageEntityMockMvc.perform(delete("/api/videoGeoTagHeritageEntitys/{id}", videoGeoTagHeritageEntity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<VideoGeoTagHeritageEntity> videoGeoTagHeritageEntitys = videoGeoTagHeritageEntityRepository.findAll();
        assertThat(videoGeoTagHeritageEntitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
