package org.janastu.heritageapp.web.rest;

import org.janastu.heritageapp.Application;
import org.janastu.heritageapp.domain.HeritageCategory;
import org.janastu.heritageapp.repository.HeritageCategoryRepository;

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
 * Test class for the HeritageCategoryResource REST controller.
 *
 * @see HeritageCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HeritageCategoryResourceIntTest {

    private static final String DEFAULT_CATEGORY_NAME = "AAAAA";
    private static final String UPDATED_CATEGORY_NAME = "BBBBB";

    private static final byte[] DEFAULT_CATEGORY_ICON = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CATEGORY_ICON = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CATEGORY_ICON_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CATEGORY_ICON_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_CATEGORY_DECRIPTION = "AAAAA";
    private static final String UPDATED_CATEGORY_DECRIPTION = "BBBBB";

    @Inject
    private HeritageCategoryRepository heritageCategoryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHeritageCategoryMockMvc;

    private HeritageCategory heritageCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HeritageCategoryResource heritageCategoryResource = new HeritageCategoryResource();
        ReflectionTestUtils.setField(heritageCategoryResource, "heritageCategoryRepository", heritageCategoryRepository);
        this.restHeritageCategoryMockMvc = MockMvcBuilders.standaloneSetup(heritageCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        heritageCategory = new HeritageCategory();
        heritageCategory.setCategoryName(DEFAULT_CATEGORY_NAME);
        heritageCategory.setCategoryIcon(DEFAULT_CATEGORY_ICON);
        heritageCategory.setCategoryIconContentType(DEFAULT_CATEGORY_ICON_CONTENT_TYPE);
        heritageCategory.setCategoryDecription(DEFAULT_CATEGORY_DECRIPTION);
    }

    @Test
    @Transactional
    public void createHeritageCategory() throws Exception {
        int databaseSizeBeforeCreate = heritageCategoryRepository.findAll().size();

        // Create the HeritageCategory

        restHeritageCategoryMockMvc.perform(post("/api/heritageCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageCategory)))
                .andExpect(status().isCreated());

        // Validate the HeritageCategory in the database
        List<HeritageCategory> heritageCategorys = heritageCategoryRepository.findAll();
        assertThat(heritageCategorys).hasSize(databaseSizeBeforeCreate + 1);
        HeritageCategory testHeritageCategory = heritageCategorys.get(heritageCategorys.size() - 1);
        assertThat(testHeritageCategory.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testHeritageCategory.getCategoryIcon()).isEqualTo(DEFAULT_CATEGORY_ICON);
        assertThat(testHeritageCategory.getCategoryIconContentType()).isEqualTo(DEFAULT_CATEGORY_ICON_CONTENT_TYPE);
        assertThat(testHeritageCategory.getCategoryDecription()).isEqualTo(DEFAULT_CATEGORY_DECRIPTION);
    }

    @Test
    @Transactional
    public void checkCategoryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageCategoryRepository.findAll().size();
        // set the field null
        heritageCategory.setCategoryName(null);

        // Create the HeritageCategory, which fails.

        restHeritageCategoryMockMvc.perform(post("/api/heritageCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageCategory)))
                .andExpect(status().isBadRequest());

        List<HeritageCategory> heritageCategorys = heritageCategoryRepository.findAll();
        assertThat(heritageCategorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHeritageCategorys() throws Exception {
        // Initialize the database
        heritageCategoryRepository.saveAndFlush(heritageCategory);

        // Get all the heritageCategorys
        restHeritageCategoryMockMvc.perform(get("/api/heritageCategorys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(heritageCategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME.toString())))
                .andExpect(jsonPath("$.[*].categoryIconContentType").value(hasItem(DEFAULT_CATEGORY_ICON_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].categoryIcon").value(hasItem(Base64Utils.encodeToString(DEFAULT_CATEGORY_ICON))))
                .andExpect(jsonPath("$.[*].categoryDecription").value(hasItem(DEFAULT_CATEGORY_DECRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getHeritageCategory() throws Exception {
        // Initialize the database
        heritageCategoryRepository.saveAndFlush(heritageCategory);

        // Get the heritageCategory
        restHeritageCategoryMockMvc.perform(get("/api/heritageCategorys/{id}", heritageCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(heritageCategory.getId().intValue()))
            .andExpect(jsonPath("$.categoryName").value(DEFAULT_CATEGORY_NAME.toString()))
            .andExpect(jsonPath("$.categoryIconContentType").value(DEFAULT_CATEGORY_ICON_CONTENT_TYPE))
            .andExpect(jsonPath("$.categoryIcon").value(Base64Utils.encodeToString(DEFAULT_CATEGORY_ICON)))
            .andExpect(jsonPath("$.categoryDecription").value(DEFAULT_CATEGORY_DECRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHeritageCategory() throws Exception {
        // Get the heritageCategory
        restHeritageCategoryMockMvc.perform(get("/api/heritageCategorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHeritageCategory() throws Exception {
        // Initialize the database
        heritageCategoryRepository.saveAndFlush(heritageCategory);

		int databaseSizeBeforeUpdate = heritageCategoryRepository.findAll().size();

        // Update the heritageCategory
        heritageCategory.setCategoryName(UPDATED_CATEGORY_NAME);
        heritageCategory.setCategoryIcon(UPDATED_CATEGORY_ICON);
        heritageCategory.setCategoryIconContentType(UPDATED_CATEGORY_ICON_CONTENT_TYPE);
        heritageCategory.setCategoryDecription(UPDATED_CATEGORY_DECRIPTION);

        restHeritageCategoryMockMvc.perform(put("/api/heritageCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageCategory)))
                .andExpect(status().isOk());

        // Validate the HeritageCategory in the database
        List<HeritageCategory> heritageCategorys = heritageCategoryRepository.findAll();
        assertThat(heritageCategorys).hasSize(databaseSizeBeforeUpdate);
        HeritageCategory testHeritageCategory = heritageCategorys.get(heritageCategorys.size() - 1);
        assertThat(testHeritageCategory.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testHeritageCategory.getCategoryIcon()).isEqualTo(UPDATED_CATEGORY_ICON);
        assertThat(testHeritageCategory.getCategoryIconContentType()).isEqualTo(UPDATED_CATEGORY_ICON_CONTENT_TYPE);
        assertThat(testHeritageCategory.getCategoryDecription()).isEqualTo(UPDATED_CATEGORY_DECRIPTION);
    }

    @Test
    @Transactional
    public void deleteHeritageCategory() throws Exception {
        // Initialize the database
        heritageCategoryRepository.saveAndFlush(heritageCategory);

		int databaseSizeBeforeDelete = heritageCategoryRepository.findAll().size();

        // Get the heritageCategory
        restHeritageCategoryMockMvc.perform(delete("/api/heritageCategorys/{id}", heritageCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HeritageCategory> heritageCategorys = heritageCategoryRepository.findAll();
        assertThat(heritageCategorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
