package com.jalisco.seguridmap.web.rest;

import com.jalisco.seguridmap.SeguridMapApp;

import com.jalisco.seguridmap.domain.Panel;
import com.jalisco.seguridmap.repository.PanelRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PanelResource REST controller.
 *
 * @see PanelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeguridMapApp.class)
public class PanelResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIGURACION = "AAAAAAAAAA";
    private static final String UPDATED_CONFIGURACION = "BBBBBBBBBB";

    private static final Long DEFAULT_ORDEN = 1L;
    private static final Long UPDATED_ORDEN = 2L;

    @Inject
    private PanelRepository panelRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPanelMockMvc;

    private Panel panel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PanelResource panelResource = new PanelResource();
        ReflectionTestUtils.setField(panelResource, "panelRepository", panelRepository);
        this.restPanelMockMvc = MockMvcBuilders.standaloneSetup(panelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Panel createEntity(EntityManager em) {
        Panel panel = new Panel()
                .nombre(DEFAULT_NOMBRE)
                .descripcion(DEFAULT_DESCRIPCION)
                .configuracion(DEFAULT_CONFIGURACION)
                .orden(DEFAULT_ORDEN);
        return panel;
    }

    @Before
    public void initTest() {
        panel = createEntity(em);
    }

    @Test
    @Transactional
    public void createPanel() throws Exception {
        int databaseSizeBeforeCreate = panelRepository.findAll().size();

        // Create the Panel

        restPanelMockMvc.perform(post("/api/panels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(panel)))
            .andExpect(status().isCreated());

        // Validate the Panel in the database
        List<Panel> panelList = panelRepository.findAll();
        assertThat(panelList).hasSize(databaseSizeBeforeCreate + 1);
        Panel testPanel = panelList.get(panelList.size() - 1);
        assertThat(testPanel.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPanel.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testPanel.getConfiguracion()).isEqualTo(DEFAULT_CONFIGURACION);
        assertThat(testPanel.getOrden()).isEqualTo(DEFAULT_ORDEN);
    }

    @Test
    @Transactional
    public void createPanelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = panelRepository.findAll().size();

        // Create the Panel with an existing ID
        Panel existingPanel = new Panel();
        existingPanel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPanelMockMvc.perform(post("/api/panels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPanel)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Panel> panelList = panelRepository.findAll();
        assertThat(panelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPanels() throws Exception {
        // Initialize the database
        panelRepository.saveAndFlush(panel);

        // Get all the panelList
        restPanelMockMvc.perform(get("/api/panels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(panel.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].configuracion").value(hasItem(DEFAULT_CONFIGURACION.toString())))
            .andExpect(jsonPath("$.[*].orden").value(hasItem(DEFAULT_ORDEN.intValue())));
    }

    @Test
    @Transactional
    public void getPanel() throws Exception {
        // Initialize the database
        panelRepository.saveAndFlush(panel);

        // Get the panel
        restPanelMockMvc.perform(get("/api/panels/{id}", panel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(panel.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.configuracion").value(DEFAULT_CONFIGURACION.toString()))
            .andExpect(jsonPath("$.orden").value(DEFAULT_ORDEN.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPanel() throws Exception {
        // Get the panel
        restPanelMockMvc.perform(get("/api/panels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePanel() throws Exception {
        // Initialize the database
        panelRepository.saveAndFlush(panel);
        int databaseSizeBeforeUpdate = panelRepository.findAll().size();

        // Update the panel
        Panel updatedPanel = panelRepository.findOne(panel.getId());
        updatedPanel
                .nombre(UPDATED_NOMBRE)
                .descripcion(UPDATED_DESCRIPCION)
                .configuracion(UPDATED_CONFIGURACION)
                .orden(UPDATED_ORDEN);

        restPanelMockMvc.perform(put("/api/panels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPanel)))
            .andExpect(status().isOk());

        // Validate the Panel in the database
        List<Panel> panelList = panelRepository.findAll();
        assertThat(panelList).hasSize(databaseSizeBeforeUpdate);
        Panel testPanel = panelList.get(panelList.size() - 1);
        assertThat(testPanel.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPanel.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testPanel.getConfiguracion()).isEqualTo(UPDATED_CONFIGURACION);
        assertThat(testPanel.getOrden()).isEqualTo(UPDATED_ORDEN);
    }

    @Test
    @Transactional
    public void updateNonExistingPanel() throws Exception {
        int databaseSizeBeforeUpdate = panelRepository.findAll().size();

        // Create the Panel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPanelMockMvc.perform(put("/api/panels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(panel)))
            .andExpect(status().isCreated());

        // Validate the Panel in the database
        List<Panel> panelList = panelRepository.findAll();
        assertThat(panelList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePanel() throws Exception {
        // Initialize the database
        panelRepository.saveAndFlush(panel);
        int databaseSizeBeforeDelete = panelRepository.findAll().size();

        // Get the panel
        restPanelMockMvc.perform(delete("/api/panels/{id}", panel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Panel> panelList = panelRepository.findAll();
        assertThat(panelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
