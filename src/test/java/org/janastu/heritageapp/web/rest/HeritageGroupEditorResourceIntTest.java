package org.janastu.heritageapp.web.rest;

import org.janastu.heritageapp.Application;
import org.janastu.heritageapp.domain.HeritageGroupEditor;
import org.janastu.heritageapp.repository.HeritageGroupEditorRepository;
import org.janastu.heritageapp.service.HeritageGroupEditorService;
import org.janastu.heritageapp.web.rest.dto.HeritageGroupEditorDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageGroupEditorMapper;

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
 * Test class for the HeritageGroupEditorResource REST controller.
 *
 * @see HeritageGroupEditorResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HeritageGroupEditorResourceIntTest {

    private static final String DEFAULT_ABOUT = "AAAAA";
    private static final String UPDATED_ABOUT = "BBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    @Inject
    private HeritageGroupEditorRepository heritageGroupEditorRepository;

    @Inject
    private HeritageGroupEditorMapper heritageGroupEditorMapper;

    @Inject
    private HeritageGroupEditorService heritageGroupEditorService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restHeritageGroupEditorMockMvc;

    private HeritageGroupEditor heritageGroupEditor;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HeritageGroupEditorResource heritageGroupEditorResource = new HeritageGroupEditorResource();
        ReflectionTestUtils.setField(heritageGroupEditorResource, "heritageGroupEditorService", heritageGroupEditorService);
        ReflectionTestUtils.setField(heritageGroupEditorResource, "heritageGroupEditorMapper", heritageGroupEditorMapper);
        this.restHeritageGroupEditorMockMvc = MockMvcBuilders.standaloneSetup(heritageGroupEditorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        heritageGroupEditor = new HeritageGroupEditor();
        heritageGroupEditor.setAbout(DEFAULT_ABOUT);
        heritageGroupEditor.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createHeritageGroupEditor() throws Exception {
        int databaseSizeBeforeCreate = heritageGroupEditorRepository.findAll().size();

        // Create the HeritageGroupEditor
        HeritageGroupEditorDTO heritageGroupEditorDTO = heritageGroupEditorMapper.heritageGroupEditorToHeritageGroupEditorDTO(heritageGroupEditor);

        restHeritageGroupEditorMockMvc.perform(post("/api/heritageGroupEditors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageGroupEditorDTO)))
                .andExpect(status().isCreated());

        // Validate the HeritageGroupEditor in the database
        List<HeritageGroupEditor> heritageGroupEditors = heritageGroupEditorRepository.findAll();
        assertThat(heritageGroupEditors).hasSize(databaseSizeBeforeCreate + 1);
        HeritageGroupEditor testHeritageGroupEditor = heritageGroupEditors.get(heritageGroupEditors.size() - 1);
        assertThat(testHeritageGroupEditor.getAbout()).isEqualTo(DEFAULT_ABOUT);
        assertThat(testHeritageGroupEditor.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllHeritageGroupEditors() throws Exception {
        // Initialize the database
        heritageGroupEditorRepository.saveAndFlush(heritageGroupEditor);

        // Get all the heritageGroupEditors
        restHeritageGroupEditorMockMvc.perform(get("/api/heritageGroupEditors?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(heritageGroupEditor.getId().intValue())))
                .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    public void getHeritageGroupEditor() throws Exception {
        // Initialize the database
        heritageGroupEditorRepository.saveAndFlush(heritageGroupEditor);

        // Get the heritageGroupEditor
        restHeritageGroupEditorMockMvc.perform(get("/api/heritageGroupEditors/{id}", heritageGroupEditor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(heritageGroupEditor.getId().intValue()))
            .andExpect(jsonPath("$.about").value(DEFAULT_ABOUT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingHeritageGroupEditor() throws Exception {
        // Get the heritageGroupEditor
        restHeritageGroupEditorMockMvc.perform(get("/api/heritageGroupEditors/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHeritageGroupEditor() throws Exception {
        // Initialize the database
        heritageGroupEditorRepository.saveAndFlush(heritageGroupEditor);

		int databaseSizeBeforeUpdate = heritageGroupEditorRepository.findAll().size();

        // Update the heritageGroupEditor
        heritageGroupEditor.setAbout(UPDATED_ABOUT);
        heritageGroupEditor.setStatus(UPDATED_STATUS);
        HeritageGroupEditorDTO heritageGroupEditorDTO = heritageGroupEditorMapper.heritageGroupEditorToHeritageGroupEditorDTO(heritageGroupEditor);

        restHeritageGroupEditorMockMvc.perform(put("/api/heritageGroupEditors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(heritageGroupEditorDTO)))
                .andExpect(status().isOk());

        // Validate the HeritageGroupEditor in the database
        List<HeritageGroupEditor> heritageGroupEditors = heritageGroupEditorRepository.findAll();
        assertThat(heritageGroupEditors).hasSize(databaseSizeBeforeUpdate);
        HeritageGroupEditor testHeritageGroupEditor = heritageGroupEditors.get(heritageGroupEditors.size() - 1);
        assertThat(testHeritageGroupEditor.getAbout()).isEqualTo(UPDATED_ABOUT);
        assertThat(testHeritageGroupEditor.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteHeritageGroupEditor() throws Exception {
        // Initialize the database
        heritageGroupEditorRepository.saveAndFlush(heritageGroupEditor);

		int databaseSizeBeforeDelete = heritageGroupEditorRepository.findAll().size();

        // Get the heritageGroupEditor
        restHeritageGroupEditorMockMvc.perform(delete("/api/heritageGroupEditors/{id}", heritageGroupEditor.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<HeritageGroupEditor> heritageGroupEditors = heritageGroupEditorRepository.findAll();
        assertThat(heritageGroupEditors).hasSize(databaseSizeBeforeDelete - 1);
    }
}
