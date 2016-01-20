package org.janastu.heritageapp.web.rest;

import org.janastu.heritageapp.Application;
import org.janastu.heritageapp.domain.ImageGeoTagHeritageEntity;
import org.janastu.heritageapp.repository.ImageGeoTagHeritageEntityRepository;
import org.janastu.heritageapp.service.ImageGeoTagHeritageEntityService;
import org.janastu.heritageapp.web.rest.dto.ImageGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.mapper.ImageGeoTagHeritageEntityMapper;

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
 * Test class for the ImageGeoTagHeritageEntityResource REST controller.
 *
 * @see ImageGeoTagHeritageEntityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ImageGeoTagHeritageEntityResourceIntTest {

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

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    @Inject
    private ImageGeoTagHeritageEntityRepository imageGeoTagHeritageEntityRepository;

    @Inject
    private ImageGeoTagHeritageEntityMapper imageGeoTagHeritageEntityMapper;

    @Inject
    private ImageGeoTagHeritageEntityService imageGeoTagHeritageEntityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restImageGeoTagHeritageEntityMockMvc;

    private ImageGeoTagHeritageEntity imageGeoTagHeritageEntity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ImageGeoTagHeritageEntityResource imageGeoTagHeritageEntityResource = new ImageGeoTagHeritageEntityResource();
        ReflectionTestUtils.setField(imageGeoTagHeritageEntityResource, "imageGeoTagHeritageEntityService", imageGeoTagHeritageEntityService);
        ReflectionTestUtils.setField(imageGeoTagHeritageEntityResource, "imageGeoTagHeritageEntityMapper", imageGeoTagHeritageEntityMapper);
        this.restImageGeoTagHeritageEntityMockMvc = MockMvcBuilders.standaloneSetup(imageGeoTagHeritageEntityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        imageGeoTagHeritageEntity = new ImageGeoTagHeritageEntity();
        imageGeoTagHeritageEntity.setTitle(DEFAULT_TITLE);
        imageGeoTagHeritageEntity.setDescription(DEFAULT_DESCRIPTION);
        imageGeoTagHeritageEntity.setAddress(DEFAULT_ADDRESS);
        imageGeoTagHeritageEntity.setLatitude(DEFAULT_LATITUDE);
        imageGeoTagHeritageEntity.setLongitude(DEFAULT_LONGITUDE);
        imageGeoTagHeritageEntity.setConsolidatedTags(DEFAULT_CONSOLIDATED_TAGS);
        imageGeoTagHeritageEntity.setUrlOrfileLink(DEFAULT_URL_ORFILE_LINK);
        imageGeoTagHeritageEntity.setPhoto(DEFAULT_PHOTO);
        imageGeoTagHeritageEntity.setPhotoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createImageGeoTagHeritageEntity() throws Exception {
        int databaseSizeBeforeCreate = imageGeoTagHeritageEntityRepository.findAll().size();

        // Create the ImageGeoTagHeritageEntity
        ImageGeoTagHeritageEntityDTO imageGeoTagHeritageEntityDTO = imageGeoTagHeritageEntityMapper.imageGeoTagHeritageEntityToImageGeoTagHeritageEntityDTO(imageGeoTagHeritageEntity);

        restImageGeoTagHeritageEntityMockMvc.perform(post("/api/imageGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imageGeoTagHeritageEntityDTO)))
                .andExpect(status().isCreated());

        // Validate the ImageGeoTagHeritageEntity in the database
        List<ImageGeoTagHeritageEntity> imageGeoTagHeritageEntitys = imageGeoTagHeritageEntityRepository.findAll();
        assertThat(imageGeoTagHeritageEntitys).hasSize(databaseSizeBeforeCreate + 1);
        ImageGeoTagHeritageEntity testImageGeoTagHeritageEntity = imageGeoTagHeritageEntitys.get(imageGeoTagHeritageEntitys.size() - 1);
        assertThat(testImageGeoTagHeritageEntity.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testImageGeoTagHeritageEntity.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testImageGeoTagHeritageEntity.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testImageGeoTagHeritageEntity.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testImageGeoTagHeritageEntity.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testImageGeoTagHeritageEntity.getConsolidatedTags()).isEqualTo(DEFAULT_CONSOLIDATED_TAGS);
        assertThat(testImageGeoTagHeritageEntity.getUrlOrfileLink()).isEqualTo(DEFAULT_URL_ORFILE_LINK);
        assertThat(testImageGeoTagHeritageEntity.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testImageGeoTagHeritageEntity.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageGeoTagHeritageEntityRepository.findAll().size();
        // set the field null
        imageGeoTagHeritageEntity.setTitle(null);

        // Create the ImageGeoTagHeritageEntity, which fails.
        ImageGeoTagHeritageEntityDTO imageGeoTagHeritageEntityDTO = imageGeoTagHeritageEntityMapper.imageGeoTagHeritageEntityToImageGeoTagHeritageEntityDTO(imageGeoTagHeritageEntity);

        restImageGeoTagHeritageEntityMockMvc.perform(post("/api/imageGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imageGeoTagHeritageEntityDTO)))
                .andExpect(status().isBadRequest());

        List<ImageGeoTagHeritageEntity> imageGeoTagHeritageEntitys = imageGeoTagHeritageEntityRepository.findAll();
        assertThat(imageGeoTagHeritageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageGeoTagHeritageEntityRepository.findAll().size();
        // set the field null
        imageGeoTagHeritageEntity.setDescription(null);

        // Create the ImageGeoTagHeritageEntity, which fails.
        ImageGeoTagHeritageEntityDTO imageGeoTagHeritageEntityDTO = imageGeoTagHeritageEntityMapper.imageGeoTagHeritageEntityToImageGeoTagHeritageEntityDTO(imageGeoTagHeritageEntity);

        restImageGeoTagHeritageEntityMockMvc.perform(post("/api/imageGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imageGeoTagHeritageEntityDTO)))
                .andExpect(status().isBadRequest());

        List<ImageGeoTagHeritageEntity> imageGeoTagHeritageEntitys = imageGeoTagHeritageEntityRepository.findAll();
        assertThat(imageGeoTagHeritageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageGeoTagHeritageEntityRepository.findAll().size();
        // set the field null
        imageGeoTagHeritageEntity.setLatitude(null);

        // Create the ImageGeoTagHeritageEntity, which fails.
        ImageGeoTagHeritageEntityDTO imageGeoTagHeritageEntityDTO = imageGeoTagHeritageEntityMapper.imageGeoTagHeritageEntityToImageGeoTagHeritageEntityDTO(imageGeoTagHeritageEntity);

        restImageGeoTagHeritageEntityMockMvc.perform(post("/api/imageGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imageGeoTagHeritageEntityDTO)))
                .andExpect(status().isBadRequest());

        List<ImageGeoTagHeritageEntity> imageGeoTagHeritageEntitys = imageGeoTagHeritageEntityRepository.findAll();
        assertThat(imageGeoTagHeritageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageGeoTagHeritageEntityRepository.findAll().size();
        // set the field null
        imageGeoTagHeritageEntity.setLongitude(null);

        // Create the ImageGeoTagHeritageEntity, which fails.
        ImageGeoTagHeritageEntityDTO imageGeoTagHeritageEntityDTO = imageGeoTagHeritageEntityMapper.imageGeoTagHeritageEntityToImageGeoTagHeritageEntityDTO(imageGeoTagHeritageEntity);

        restImageGeoTagHeritageEntityMockMvc.perform(post("/api/imageGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imageGeoTagHeritageEntityDTO)))
                .andExpect(status().isBadRequest());

        List<ImageGeoTagHeritageEntity> imageGeoTagHeritageEntitys = imageGeoTagHeritageEntityRepository.findAll();
        assertThat(imageGeoTagHeritageEntitys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllImageGeoTagHeritageEntitys() throws Exception {
        // Initialize the database
        imageGeoTagHeritageEntityRepository.saveAndFlush(imageGeoTagHeritageEntity);

        // Get all the imageGeoTagHeritageEntitys
        restImageGeoTagHeritageEntityMockMvc.perform(get("/api/imageGeoTagHeritageEntitys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(imageGeoTagHeritageEntity.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].consolidatedTags").value(hasItem(DEFAULT_CONSOLIDATED_TAGS.toString())))
                .andExpect(jsonPath("$.[*].urlOrfileLink").value(hasItem(DEFAULT_URL_ORFILE_LINK.toString())))
                .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    public void getImageGeoTagHeritageEntity() throws Exception {
        // Initialize the database
        imageGeoTagHeritageEntityRepository.saveAndFlush(imageGeoTagHeritageEntity);

        // Get the imageGeoTagHeritageEntity
        restImageGeoTagHeritageEntityMockMvc.perform(get("/api/imageGeoTagHeritageEntitys/{id}", imageGeoTagHeritageEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(imageGeoTagHeritageEntity.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.consolidatedTags").value(DEFAULT_CONSOLIDATED_TAGS.toString()))
            .andExpect(jsonPath("$.urlOrfileLink").value(DEFAULT_URL_ORFILE_LINK.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingImageGeoTagHeritageEntity() throws Exception {
        // Get the imageGeoTagHeritageEntity
        restImageGeoTagHeritageEntityMockMvc.perform(get("/api/imageGeoTagHeritageEntitys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImageGeoTagHeritageEntity() throws Exception {
        // Initialize the database
        imageGeoTagHeritageEntityRepository.saveAndFlush(imageGeoTagHeritageEntity);

		int databaseSizeBeforeUpdate = imageGeoTagHeritageEntityRepository.findAll().size();

        // Update the imageGeoTagHeritageEntity
        imageGeoTagHeritageEntity.setTitle(UPDATED_TITLE);
        imageGeoTagHeritageEntity.setDescription(UPDATED_DESCRIPTION);
        imageGeoTagHeritageEntity.setAddress(UPDATED_ADDRESS);
        imageGeoTagHeritageEntity.setLatitude(UPDATED_LATITUDE);
        imageGeoTagHeritageEntity.setLongitude(UPDATED_LONGITUDE);
        imageGeoTagHeritageEntity.setConsolidatedTags(UPDATED_CONSOLIDATED_TAGS);
        imageGeoTagHeritageEntity.setUrlOrfileLink(UPDATED_URL_ORFILE_LINK);
        imageGeoTagHeritageEntity.setPhoto(UPDATED_PHOTO);
        imageGeoTagHeritageEntity.setPhotoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        ImageGeoTagHeritageEntityDTO imageGeoTagHeritageEntityDTO = imageGeoTagHeritageEntityMapper.imageGeoTagHeritageEntityToImageGeoTagHeritageEntityDTO(imageGeoTagHeritageEntity);

        restImageGeoTagHeritageEntityMockMvc.perform(put("/api/imageGeoTagHeritageEntitys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(imageGeoTagHeritageEntityDTO)))
                .andExpect(status().isOk());

        // Validate the ImageGeoTagHeritageEntity in the database
        List<ImageGeoTagHeritageEntity> imageGeoTagHeritageEntitys = imageGeoTagHeritageEntityRepository.findAll();
        assertThat(imageGeoTagHeritageEntitys).hasSize(databaseSizeBeforeUpdate);
        ImageGeoTagHeritageEntity testImageGeoTagHeritageEntity = imageGeoTagHeritageEntitys.get(imageGeoTagHeritageEntitys.size() - 1);
        assertThat(testImageGeoTagHeritageEntity.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testImageGeoTagHeritageEntity.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testImageGeoTagHeritageEntity.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testImageGeoTagHeritageEntity.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testImageGeoTagHeritageEntity.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testImageGeoTagHeritageEntity.getConsolidatedTags()).isEqualTo(UPDATED_CONSOLIDATED_TAGS);
        assertThat(testImageGeoTagHeritageEntity.getUrlOrfileLink()).isEqualTo(UPDATED_URL_ORFILE_LINK);
        assertThat(testImageGeoTagHeritageEntity.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testImageGeoTagHeritageEntity.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteImageGeoTagHeritageEntity() throws Exception {
        // Initialize the database
        imageGeoTagHeritageEntityRepository.saveAndFlush(imageGeoTagHeritageEntity);

		int databaseSizeBeforeDelete = imageGeoTagHeritageEntityRepository.findAll().size();

        // Get the imageGeoTagHeritageEntity
        restImageGeoTagHeritageEntityMockMvc.perform(delete("/api/imageGeoTagHeritageEntitys/{id}", imageGeoTagHeritageEntity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ImageGeoTagHeritageEntity> imageGeoTagHeritageEntitys = imageGeoTagHeritageEntityRepository.findAll();
        assertThat(imageGeoTagHeritageEntitys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
