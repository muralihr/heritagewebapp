package org.janastu.heritageapp.web.rest;

import org.janastu.heritageapp.Application;
import org.janastu.heritageapp.domain.HeritageMedia;
import org.janastu.heritageapp.repository.HeritageMediaRepository;
import org.janastu.heritageapp.service.HeritageMediaService;
import org.janastu.heritageapp.web.rest.dto.HeritageMediaDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageMediaMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the HeritageMediaResource REST controller.
 *
 * @see HeritageMediaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HeritageMediaResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

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

    private static final Integer DEFAULT_MEDIA_TYPE = 1;
    private static final Integer UPDATED_MEDIA_TYPE = 2;

    private static final byte[] DEFAULT_MEDIA_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MEDIA_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_MEDIA_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MEDIA_FILE_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_URL_ORFILE_LINK = "AAAAA";
    private static final String UPDATED_URL_ORFILE_LINK = "BBBBB";
    private static final String DEFAULT_CONSOLIDATED_TAGS = "AAAAA";
    private static final String UPDATED_CONSOLIDATED_TAGS = "BBBBB";
    private static final String DEFAULT_USER_AGENT = "AAAAA";
    private static final String UPDATED_USER_AGENT = "BBBBB";

    private static final ZonedDateTime DEFAULT_UPLOAD_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_UPLOAD_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_UPLOAD_TIME_STR = dateTimeFormatter.format(DEFAULT_UPLOAD_TIME);

    @Inject
    private HeritageMediaRepository heritageMediaRepository;

    @Inject
    private HeritageMediaMapper heritageMediaMapper;

    @Inject
    private HeritageMediaService heritageMediaService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHeritageMediaMockMvc;

    private HeritageMedia heritageMedia;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HeritageMediaResource heritageMediaResource = new HeritageMediaResource();
        ReflectionTestUtils.setField(heritageMediaResource, "heritageMediaService", heritageMediaService);
        ReflectionTestUtils.setField(heritageMediaResource, "heritageMediaMapper", heritageMediaMapper);
        this.restHeritageMediaMockMvc = MockMvcBuilders.standaloneSetup(heritageMediaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        heritageMedia = new HeritageMedia();
        heritageMedia.setTitle(DEFAULT_TITLE);
        heritageMedia.setDescription(DEFAULT_DESCRIPTION);
        heritageMedia.setAddress(DEFAULT_ADDRESS);
        heritageMedia.setLatitude(DEFAULT_LATITUDE);
        heritageMedia.setLongitude(DEFAULT_LONGITUDE);
        heritageMedia.setMediaType(DEFAULT_MEDIA_TYPE);
        heritageMedia.setMediaFile(DEFAULT_MEDIA_FILE);
        heritageMedia.setMediaFileContentType(DEFAULT_MEDIA_FILE_CONTENT_TYPE);
        heritageMedia.setUrlOrfileLink(DEFAULT_URL_ORFILE_LINK);
        heritageMedia.setConsolidatedTags(DEFAULT_CONSOLIDATED_TAGS);
        heritageMedia.setUserAgent(DEFAULT_USER_AGENT);
        heritageMedia.setUploadTime(DEFAULT_UPLOAD_TIME);
    }

    @Test
    @Transactional
    public void createHeritageMedia() throws Exception {
        int databaseSizeBeforeCreate = heritageMediaRepository.findAll().size();

        // Create the HeritageMedia
        HeritageMediaDTO heritageMediaDTO = heritageMediaMapper.heritageMediaToHeritageMediaDTO(heritageMedia);

        restHeritageMediaMockMvc.perform(post("/api/heritageMedias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageMediaDTO)))
                .andExpect(status().isCreated());

        // Validate the HeritageMedia in the database
        List<HeritageMedia> heritageMedias = heritageMediaRepository.findAll();
        assertThat(heritageMedias).hasSize(databaseSizeBeforeCreate + 1);
        HeritageMedia testHeritageMedia = heritageMedias.get(heritageMedias.size() - 1);
        assertThat(testHeritageMedia.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testHeritageMedia.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHeritageMedia.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testHeritageMedia.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testHeritageMedia.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testHeritageMedia.getMediaType()).isEqualTo(DEFAULT_MEDIA_TYPE);
        assertThat(testHeritageMedia.getMediaFile()).isEqualTo(DEFAULT_MEDIA_FILE);
        assertThat(testHeritageMedia.getMediaFileContentType()).isEqualTo(DEFAULT_MEDIA_FILE_CONTENT_TYPE);
        assertThat(testHeritageMedia.getUrlOrfileLink()).isEqualTo(DEFAULT_URL_ORFILE_LINK);
        assertThat(testHeritageMedia.getConsolidatedTags()).isEqualTo(DEFAULT_CONSOLIDATED_TAGS);
        assertThat(testHeritageMedia.getUserAgent()).isEqualTo(DEFAULT_USER_AGENT);
        assertThat(testHeritageMedia.getUploadTime()).isEqualTo(DEFAULT_UPLOAD_TIME);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageMediaRepository.findAll().size();
        // set the field null
        heritageMedia.setTitle(null);

        // Create the HeritageMedia, which fails.
        HeritageMediaDTO heritageMediaDTO = heritageMediaMapper.heritageMediaToHeritageMediaDTO(heritageMedia);

        restHeritageMediaMockMvc.perform(post("/api/heritageMedias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageMediaDTO)))
                .andExpect(status().isBadRequest());

        List<HeritageMedia> heritageMedias = heritageMediaRepository.findAll();
        assertThat(heritageMedias).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageMediaRepository.findAll().size();
        // set the field null
        heritageMedia.setDescription(null);

        // Create the HeritageMedia, which fails.
        HeritageMediaDTO heritageMediaDTO = heritageMediaMapper.heritageMediaToHeritageMediaDTO(heritageMedia);

        restHeritageMediaMockMvc.perform(post("/api/heritageMedias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageMediaDTO)))
                .andExpect(status().isBadRequest());

        List<HeritageMedia> heritageMedias = heritageMediaRepository.findAll();
        assertThat(heritageMedias).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageMediaRepository.findAll().size();
        // set the field null
        heritageMedia.setLatitude(null);

        // Create the HeritageMedia, which fails.
        HeritageMediaDTO heritageMediaDTO = heritageMediaMapper.heritageMediaToHeritageMediaDTO(heritageMedia);

        restHeritageMediaMockMvc.perform(post("/api/heritageMedias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageMediaDTO)))
                .andExpect(status().isBadRequest());

        List<HeritageMedia> heritageMedias = heritageMediaRepository.findAll();
        assertThat(heritageMedias).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageMediaRepository.findAll().size();
        // set the field null
        heritageMedia.setLongitude(null);

        // Create the HeritageMedia, which fails.
        HeritageMediaDTO heritageMediaDTO = heritageMediaMapper.heritageMediaToHeritageMediaDTO(heritageMedia);

        restHeritageMediaMockMvc.perform(post("/api/heritageMedias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageMediaDTO)))
                .andExpect(status().isBadRequest());

        List<HeritageMedia> heritageMedias = heritageMediaRepository.findAll();
        assertThat(heritageMedias).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMediaTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageMediaRepository.findAll().size();
        // set the field null
        heritageMedia.setMediaType(null);

        // Create the HeritageMedia, which fails.
        HeritageMediaDTO heritageMediaDTO = heritageMediaMapper.heritageMediaToHeritageMediaDTO(heritageMedia);

        restHeritageMediaMockMvc.perform(post("/api/heritageMedias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageMediaDTO)))
                .andExpect(status().isBadRequest());

        List<HeritageMedia> heritageMedias = heritageMediaRepository.findAll();
        assertThat(heritageMedias).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHeritageMedias() throws Exception {
        // Initialize the database
        heritageMediaRepository.saveAndFlush(heritageMedia);

        // Get all the heritageMedias
        restHeritageMediaMockMvc.perform(get("/api/heritageMedias?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(heritageMedia.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].mediaType").value(hasItem(DEFAULT_MEDIA_TYPE)))
                .andExpect(jsonPath("$.[*].mediaFileContentType").value(hasItem(DEFAULT_MEDIA_FILE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].mediaFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_MEDIA_FILE))))
                .andExpect(jsonPath("$.[*].urlOrfileLink").value(hasItem(DEFAULT_URL_ORFILE_LINK.toString())))
                .andExpect(jsonPath("$.[*].consolidatedTags").value(hasItem(DEFAULT_CONSOLIDATED_TAGS.toString())))
                .andExpect(jsonPath("$.[*].userAgent").value(hasItem(DEFAULT_USER_AGENT.toString())))
                .andExpect(jsonPath("$.[*].uploadTime").value(hasItem(DEFAULT_UPLOAD_TIME_STR)));
    }

    @Test
    @Transactional
    public void getHeritageMedia() throws Exception {
        // Initialize the database
        heritageMediaRepository.saveAndFlush(heritageMedia);

        // Get the heritageMedia
        restHeritageMediaMockMvc.perform(get("/api/heritageMedias/{id}", heritageMedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(heritageMedia.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.mediaType").value(DEFAULT_MEDIA_TYPE))
            .andExpect(jsonPath("$.mediaFileContentType").value(DEFAULT_MEDIA_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.mediaFile").value(Base64Utils.encodeToString(DEFAULT_MEDIA_FILE)))
            .andExpect(jsonPath("$.urlOrfileLink").value(DEFAULT_URL_ORFILE_LINK.toString()))
            .andExpect(jsonPath("$.consolidatedTags").value(DEFAULT_CONSOLIDATED_TAGS.toString()))
            .andExpect(jsonPath("$.userAgent").value(DEFAULT_USER_AGENT.toString()))
            .andExpect(jsonPath("$.uploadTime").value(DEFAULT_UPLOAD_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingHeritageMedia() throws Exception {
        // Get the heritageMedia
        restHeritageMediaMockMvc.perform(get("/api/heritageMedias/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHeritageMedia() throws Exception {
        // Initialize the database
        heritageMediaRepository.saveAndFlush(heritageMedia);

		int databaseSizeBeforeUpdate = heritageMediaRepository.findAll().size();

        // Update the heritageMedia
        heritageMedia.setTitle(UPDATED_TITLE);
        heritageMedia.setDescription(UPDATED_DESCRIPTION);
        heritageMedia.setAddress(UPDATED_ADDRESS);
        heritageMedia.setLatitude(UPDATED_LATITUDE);
        heritageMedia.setLongitude(UPDATED_LONGITUDE);
        heritageMedia.setMediaType(UPDATED_MEDIA_TYPE);
        heritageMedia.setMediaFile(UPDATED_MEDIA_FILE);
        heritageMedia.setMediaFileContentType(UPDATED_MEDIA_FILE_CONTENT_TYPE);
        heritageMedia.setUrlOrfileLink(UPDATED_URL_ORFILE_LINK);
        heritageMedia.setConsolidatedTags(UPDATED_CONSOLIDATED_TAGS);
        heritageMedia.setUserAgent(UPDATED_USER_AGENT);
        heritageMedia.setUploadTime(UPDATED_UPLOAD_TIME);
        HeritageMediaDTO heritageMediaDTO = heritageMediaMapper.heritageMediaToHeritageMediaDTO(heritageMedia);

        restHeritageMediaMockMvc.perform(put("/api/heritageMedias")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageMediaDTO)))
                .andExpect(status().isOk());

        // Validate the HeritageMedia in the database
        List<HeritageMedia> heritageMedias = heritageMediaRepository.findAll();
        assertThat(heritageMedias).hasSize(databaseSizeBeforeUpdate);
        HeritageMedia testHeritageMedia = heritageMedias.get(heritageMedias.size() - 1);
        assertThat(testHeritageMedia.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testHeritageMedia.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHeritageMedia.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testHeritageMedia.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testHeritageMedia.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testHeritageMedia.getMediaType()).isEqualTo(UPDATED_MEDIA_TYPE);
        assertThat(testHeritageMedia.getMediaFile()).isEqualTo(UPDATED_MEDIA_FILE);
        assertThat(testHeritageMedia.getMediaFileContentType()).isEqualTo(UPDATED_MEDIA_FILE_CONTENT_TYPE);
        assertThat(testHeritageMedia.getUrlOrfileLink()).isEqualTo(UPDATED_URL_ORFILE_LINK);
        assertThat(testHeritageMedia.getConsolidatedTags()).isEqualTo(UPDATED_CONSOLIDATED_TAGS);
        assertThat(testHeritageMedia.getUserAgent()).isEqualTo(UPDATED_USER_AGENT);
        assertThat(testHeritageMedia.getUploadTime()).isEqualTo(UPDATED_UPLOAD_TIME);
    }

    @Test
    @Transactional
    public void deleteHeritageMedia() throws Exception {
        // Initialize the database
        heritageMediaRepository.saveAndFlush(heritageMedia);

		int databaseSizeBeforeDelete = heritageMediaRepository.findAll().size();

        // Get the heritageMedia
        restHeritageMediaMockMvc.perform(delete("/api/heritageMedias/{id}", heritageMedia.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HeritageMedia> heritageMedias = heritageMediaRepository.findAll();
        assertThat(heritageMedias).hasSize(databaseSizeBeforeDelete - 1);
    }
}
