package org.janastu.heritageapp.web.rest;

import org.janastu.heritageapp.Application;
import org.janastu.heritageapp.domain.HeritageGroup;
import org.janastu.heritageapp.repository.HeritageGroupRepository;
import org.janastu.heritageapp.service.HeritageGroupService;
import org.janastu.heritageapp.web.rest.dto.HeritageGroupDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageGroupMapper;

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
 * Test class for the HeritageGroupResource REST controller.
 *
 * @see HeritageGroupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HeritageGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final byte[] DEFAULT_ICON = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ICON = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ICON_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ICON_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_DETAILS = "AAAAA";
    private static final String UPDATED_DETAILS = "BBBBB";

    @Inject
    private HeritageGroupRepository heritageGroupRepository;

    @Inject
    private HeritageGroupMapper heritageGroupMapper;

    @Inject
    private HeritageGroupService heritageGroupService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHeritageGroupMockMvc;

    private HeritageGroup heritageGroup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HeritageGroupResource heritageGroupResource = new HeritageGroupResource();
        ReflectionTestUtils.setField(heritageGroupResource, "heritageGroupService", heritageGroupService);
        ReflectionTestUtils.setField(heritageGroupResource, "heritageGroupMapper", heritageGroupMapper);
        this.restHeritageGroupMockMvc = MockMvcBuilders.standaloneSetup(heritageGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        heritageGroup = new HeritageGroup();
        heritageGroup.setName(DEFAULT_NAME);
        heritageGroup.setIcon(DEFAULT_ICON);
        heritageGroup.setIconContentType(DEFAULT_ICON_CONTENT_TYPE);
        heritageGroup.setDetails(DEFAULT_DETAILS);
    }

    @Test
    @Transactional
    public void createHeritageGroup() throws Exception {
        int databaseSizeBeforeCreate = heritageGroupRepository.findAll().size();

        // Create the HeritageGroup
        HeritageGroupDTO heritageGroupDTO = heritageGroupMapper.heritageGroupToHeritageGroupDTO(heritageGroup);

        restHeritageGroupMockMvc.perform(post("/api/heritageGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageGroupDTO)))
                .andExpect(status().isCreated());

        // Validate the HeritageGroup in the database
        List<HeritageGroup> heritageGroups = heritageGroupRepository.findAll();
        assertThat(heritageGroups).hasSize(databaseSizeBeforeCreate + 1);
        HeritageGroup testHeritageGroup = heritageGroups.get(heritageGroups.size() - 1);
        assertThat(testHeritageGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHeritageGroup.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testHeritageGroup.getIconContentType()).isEqualTo(DEFAULT_ICON_CONTENT_TYPE);
        assertThat(testHeritageGroup.getDetails()).isEqualTo(DEFAULT_DETAILS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageGroupRepository.findAll().size();
        // set the field null
        heritageGroup.setName(null);

        // Create the HeritageGroup, which fails.
        HeritageGroupDTO heritageGroupDTO = heritageGroupMapper.heritageGroupToHeritageGroupDTO(heritageGroup);

        restHeritageGroupMockMvc.perform(post("/api/heritageGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageGroupDTO)))
                .andExpect(status().isBadRequest());

        List<HeritageGroup> heritageGroups = heritageGroupRepository.findAll();
        assertThat(heritageGroups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDetailsIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageGroupRepository.findAll().size();
        // set the field null
        heritageGroup.setDetails(null);

        // Create the HeritageGroup, which fails.
        HeritageGroupDTO heritageGroupDTO = heritageGroupMapper.heritageGroupToHeritageGroupDTO(heritageGroup);

        restHeritageGroupMockMvc.perform(post("/api/heritageGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageGroupDTO)))
                .andExpect(status().isBadRequest());

        List<HeritageGroup> heritageGroups = heritageGroupRepository.findAll();
        assertThat(heritageGroups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHeritageGroups() throws Exception {
        // Initialize the database
        heritageGroupRepository.saveAndFlush(heritageGroup);

        // Get all the heritageGroups
        restHeritageGroupMockMvc.perform(get("/api/heritageGroups?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(heritageGroup.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].iconContentType").value(hasItem(DEFAULT_ICON_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].icon").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICON))))
                .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())));
    }

    @Test
    @Transactional
    public void getHeritageGroup() throws Exception {
        // Initialize the database
        heritageGroupRepository.saveAndFlush(heritageGroup);

        // Get the heritageGroup
        restHeritageGroupMockMvc.perform(get("/api/heritageGroups/{id}", heritageGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(heritageGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.iconContentType").value(DEFAULT_ICON_CONTENT_TYPE))
            .andExpect(jsonPath("$.icon").value(Base64Utils.encodeToString(DEFAULT_ICON)))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHeritageGroup() throws Exception {
        // Get the heritageGroup
        restHeritageGroupMockMvc.perform(get("/api/heritageGroups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHeritageGroup() throws Exception {
        // Initialize the database
        heritageGroupRepository.saveAndFlush(heritageGroup);

		int databaseSizeBeforeUpdate = heritageGroupRepository.findAll().size();

        // Update the heritageGroup
        heritageGroup.setName(UPDATED_NAME);
        heritageGroup.setIcon(UPDATED_ICON);
        heritageGroup.setIconContentType(UPDATED_ICON_CONTENT_TYPE);
        heritageGroup.setDetails(UPDATED_DETAILS);
        HeritageGroupDTO heritageGroupDTO = heritageGroupMapper.heritageGroupToHeritageGroupDTO(heritageGroup);

        restHeritageGroupMockMvc.perform(put("/api/heritageGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageGroupDTO)))
                .andExpect(status().isOk());

        // Validate the HeritageGroup in the database
        List<HeritageGroup> heritageGroups = heritageGroupRepository.findAll();
        assertThat(heritageGroups).hasSize(databaseSizeBeforeUpdate);
        HeritageGroup testHeritageGroup = heritageGroups.get(heritageGroups.size() - 1);
        assertThat(testHeritageGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHeritageGroup.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testHeritageGroup.getIconContentType()).isEqualTo(UPDATED_ICON_CONTENT_TYPE);
        assertThat(testHeritageGroup.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    @Transactional
    public void deleteHeritageGroup() throws Exception {
        // Initialize the database
        heritageGroupRepository.saveAndFlush(heritageGroup);

		int databaseSizeBeforeDelete = heritageGroupRepository.findAll().size();

        // Get the heritageGroup
        restHeritageGroupMockMvc.perform(delete("/api/heritageGroups/{id}", heritageGroup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HeritageGroup> heritageGroups = heritageGroupRepository.findAll();
        assertThat(heritageGroups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
