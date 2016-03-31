package org.janastu.heritageapp.web.rest;

import org.janastu.heritageapp.Application;
import org.janastu.heritageapp.domain.HeritageGroupUser;
import org.janastu.heritageapp.repository.HeritageGroupUserRepository;
import org.janastu.heritageapp.service.HeritageGroupUserService;
import org.janastu.heritageapp.web.rest.dto.HeritageGroupUserDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageGroupUserMapper;

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
 * Test class for the HeritageGroupUserResource REST controller.
 *
 * @see HeritageGroupUserResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HeritageGroupUserResourceIntTest {

    private static final String DEFAULT_REASON = "AAAAA";
    private static final String UPDATED_REASON = "BBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private HeritageGroupUserRepository heritageGroupUserRepository;

    @Inject
    private HeritageGroupUserMapper heritageGroupUserMapper;

    @Inject
    private HeritageGroupUserService heritageGroupUserService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHeritageGroupUserMockMvc;

    private HeritageGroupUser heritageGroupUser;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HeritageGroupUserResource heritageGroupUserResource = new HeritageGroupUserResource();
        ReflectionTestUtils.setField(heritageGroupUserResource, "heritageGroupUserService", heritageGroupUserService);
        ReflectionTestUtils.setField(heritageGroupUserResource, "heritageGroupUserMapper", heritageGroupUserMapper);
        this.restHeritageGroupUserMockMvc = MockMvcBuilders.standaloneSetup(heritageGroupUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        heritageGroupUser = new HeritageGroupUser();
        heritageGroupUser.setReason(DEFAULT_REASON);
        heritageGroupUser.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createHeritageGroupUser() throws Exception {
        int databaseSizeBeforeCreate = heritageGroupUserRepository.findAll().size();

        // Create the HeritageGroupUser
        HeritageGroupUserDTO heritageGroupUserDTO = heritageGroupUserMapper.heritageGroupUserToHeritageGroupUserDTO(heritageGroupUser);

        restHeritageGroupUserMockMvc.perform(post("/api/heritageGroupUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageGroupUserDTO)))
                .andExpect(status().isCreated());

        // Validate the HeritageGroupUser in the database
        List<HeritageGroupUser> heritageGroupUsers = heritageGroupUserRepository.findAll();
        assertThat(heritageGroupUsers).hasSize(databaseSizeBeforeCreate + 1);
        HeritageGroupUser testHeritageGroupUser = heritageGroupUsers.get(heritageGroupUsers.size() - 1);
        assertThat(testHeritageGroupUser.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testHeritageGroupUser.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkReasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = heritageGroupUserRepository.findAll().size();
        // set the field null
        heritageGroupUser.setReason(null);

        // Create the HeritageGroupUser, which fails.
        HeritageGroupUserDTO heritageGroupUserDTO = heritageGroupUserMapper.heritageGroupUserToHeritageGroupUserDTO(heritageGroupUser);

        restHeritageGroupUserMockMvc.perform(post("/api/heritageGroupUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageGroupUserDTO)))
                .andExpect(status().isBadRequest());

        List<HeritageGroupUser> heritageGroupUsers = heritageGroupUserRepository.findAll();
        assertThat(heritageGroupUsers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHeritageGroupUsers() throws Exception {
        // Initialize the database
        heritageGroupUserRepository.saveAndFlush(heritageGroupUser);

        // Get all the heritageGroupUsers
        restHeritageGroupUserMockMvc.perform(get("/api/heritageGroupUsers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(heritageGroupUser.getId().intValue())))
                .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getHeritageGroupUser() throws Exception {
        // Initialize the database
        heritageGroupUserRepository.saveAndFlush(heritageGroupUser);

        // Get the heritageGroupUser
        restHeritageGroupUserMockMvc.perform(get("/api/heritageGroupUsers/{id}", heritageGroupUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(heritageGroupUser.getId().intValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingHeritageGroupUser() throws Exception {
        // Get the heritageGroupUser
        restHeritageGroupUserMockMvc.perform(get("/api/heritageGroupUsers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHeritageGroupUser() throws Exception {
        // Initialize the database
        heritageGroupUserRepository.saveAndFlush(heritageGroupUser);

		int databaseSizeBeforeUpdate = heritageGroupUserRepository.findAll().size();

        // Update the heritageGroupUser
        heritageGroupUser.setReason(UPDATED_REASON);
        heritageGroupUser.setStatus(UPDATED_STATUS);
        HeritageGroupUserDTO heritageGroupUserDTO = heritageGroupUserMapper.heritageGroupUserToHeritageGroupUserDTO(heritageGroupUser);

        restHeritageGroupUserMockMvc.perform(put("/api/heritageGroupUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageGroupUserDTO)))
                .andExpect(status().isOk());

        // Validate the HeritageGroupUser in the database
        List<HeritageGroupUser> heritageGroupUsers = heritageGroupUserRepository.findAll();
        assertThat(heritageGroupUsers).hasSize(databaseSizeBeforeUpdate);
        HeritageGroupUser testHeritageGroupUser = heritageGroupUsers.get(heritageGroupUsers.size() - 1);
        assertThat(testHeritageGroupUser.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testHeritageGroupUser.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteHeritageGroupUser() throws Exception {
        // Initialize the database
        heritageGroupUserRepository.saveAndFlush(heritageGroupUser);

		int databaseSizeBeforeDelete = heritageGroupUserRepository.findAll().size();

        // Get the heritageGroupUser
        restHeritageGroupUserMockMvc.perform(delete("/api/heritageGroupUsers/{id}", heritageGroupUser.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HeritageGroupUser> heritageGroupUsers = heritageGroupUserRepository.findAll();
        assertThat(heritageGroupUsers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
