package com.jalisco.seguridmap.web.rest;

import com.jalisco.seguridmap.SeguridMapApp;

import com.jalisco.seguridmap.domain.UserReport;
import com.jalisco.seguridmap.repository.UserReportRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.jalisco.seguridmap.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jalisco.seguridmap.domain.enumeration.Genero;
import com.jalisco.seguridmap.domain.enumeration.Genero;
import com.jalisco.seguridmap.domain.enumeration.EstadoReporte;
/**
 * Test class for the UserReportResource REST controller.
 *
 * @see UserReportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeguridMapApp.class)
public class UserReportResourceIntTest {

    private static final Boolean DEFAULT_ANONIMO = false;
    private static final Boolean UPDATED_ANONIMO = true;

    private static final String DEFAULT_APELLIDO_PATERNO_DENUNCIANTE = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_PATERNO_DENUNCIANTE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_MATERNO_DENUNCIANTE = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_MATERNO_DENUNCIANTE = "BBBBBBBBBB";

    private static final Genero DEFAULT_GENERO = Genero.Masculino;
    private static final Genero UPDATED_GENERO = Genero.Femenino;

    private static final String DEFAULT_CORREO_DENUNCIANTE = "AAAAAAAAAA";
    private static final String UPDATED_CORREO_DENUNCIANTE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_DENUNCIANTE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_DENUNCIANTE = "BBBBBBBBBB";

    private static final String DEFAULT_DOMICILIO_DENUNCIANTE = "AAAAAAAAAA";
    private static final String UPDATED_DOMICILIO_DENUNCIANTE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CP_DENUNCIANTE = 1;
    private static final Integer UPDATED_CP_DENUNCIANTE = 2;

    private static final String DEFAULT_NOMBRE_DENUNCIADO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DENUNCIADO = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_PATERNO_DENUNCIADO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_PATERNO_DENUNCIADO = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_MATERNO_DENUNCIADO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_MATERNO_DENUNCIADO = "BBBBBBBBBB";

    private static final String DEFAULT_SOBRENOMBRE_DENUNCIADO = "AAAAAAAAAA";
    private static final String UPDATED_SOBRENOMBRE_DENUNCIADO = "BBBBBBBBBB";

    private static final Genero DEFAULT_GENERO_DENUNCIADO = Genero.Masculino;
    private static final Genero UPDATED_GENERO_DENUNCIADO = Genero.Femenino;

    private static final String DEFAULT_DESCRIPCION_DENUNCIADO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION_DENUNCIADO = "BBBBBBBBBB";

    private static final String DEFAULT_DELITO = "AAAAAAAAAA";
    private static final String UPDATED_DELITO = "BBBBBBBBBB";

    private static final String DEFAULT_HORARIO_DELITO = "AAAAAAAAAA";
    private static final String UPDATED_HORARIO_DELITO = "BBBBBBBBBB";

    private static final String DEFAULT_DIA_DELITO = "AAAAAAAAAA";
    private static final String UPDATED_DIA_DELITO = "BBBBBBBBBB";

    private static final String DEFAULT_CALLE_DELITO = "AAAAAAAAAA";
    private static final String UPDATED_CALLE_DELITO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_EXT_DELITO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_EXT_DELITO = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_INT_DELITO = "AAAAAAAAAA";
    private static final String UPDATED_NUM_INT_DELITO = "BBBBBBBBBB";

    private static final String DEFAULT_CALLE_PRINCIPAL_DELITO = "AAAAAAAAAA";
    private static final String UPDATED_CALLE_PRINCIPAL_DELITO = "BBBBBBBBBB";

    private static final String DEFAULT_CALLE_CRUCE_DELITO = "AAAAAAAAAA";
    private static final String UPDATED_CALLE_CRUCE_DELITO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION_DOMICILIO_DELITO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION_DOMICILIO_DELITO = "BBBBBBBBBB";

    private static final String DEFAULT_NARRACION_DELITO = "AAAAAAAAAA";
    private static final String UPDATED_NARRACION_DELITO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_POLICIA = false;
    private static final Boolean UPDATED_POLICIA = true;

    private static final ZonedDateTime DEFAULT_FECHA_DELITO_POLICIA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_DELITO_POLICIA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_HORA_APROXIMADA_DELITO_POLICIA = "AAAAAAAAAA";
    private static final String UPDATED_HORA_APROXIMADA_DELITO_POLICIA = "BBBBBBBBBB";

    private static final String DEFAULT_MUNICIPIO_POLICIA = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPIO_POLICIA = "BBBBBBBBBB";

    private static final String DEFAULT_CORPORACION_POLICIA = "AAAAAAAAAA";
    private static final String UPDATED_CORPORACION_POLICIA = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_UNIDAD_POLICIA = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_UNIDAD_POLICIA = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR_UNIDADA_POLICIA = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_UNIDADA_POLICIA = "BBBBBBBBBB";

    private static final String DEFAULT_PLACAS_POLICIA = "AAAAAAAAAA";
    private static final String UPDATED_PLACAS_POLICIA = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_POLICIA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_POLICIA = "BBBBBBBBBB";

    private static final String DEFAULT_ALIAS_POLICIA = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS_POLICIA = "BBBBBBBBBB";

    private static final String DEFAULT_DOMICILIO_POLICIA = "AAAAAAAAAA";
    private static final String UPDATED_DOMICILIO_POLICIA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION_HECHOS_POLICIA = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION_HECHOS_POLICIA = "BBBBBBBBBB";

    private static final EstadoReporte DEFAULT_ESTADO_REPORTE = EstadoReporte.EsperaAtencion;
    private static final EstadoReporte UPDATED_ESTADO_REPORTE = EstadoReporte.Proceso;

    private static final String DEFAULT_NOMBRE_DENUNCIANTE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DENUNCIANTE = "BBBBBBBBBB";

    @Inject
    private UserReportRepository userReportRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUserReportMockMvc;

    private UserReport userReport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserReportResource userReportResource = new UserReportResource();
        ReflectionTestUtils.setField(userReportResource, "userReportRepository", userReportRepository);
        this.restUserReportMockMvc = MockMvcBuilders.standaloneSetup(userReportResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserReport createEntity(EntityManager em) {
        UserReport userReport = new UserReport()
                .anonimo(DEFAULT_ANONIMO)
                .apellidoPaternoDenunciante(DEFAULT_APELLIDO_PATERNO_DENUNCIANTE)
                .apellidoMaternoDenunciante(DEFAULT_APELLIDO_MATERNO_DENUNCIANTE)
                .genero(DEFAULT_GENERO)
                .correoDenunciante(DEFAULT_CORREO_DENUNCIANTE)
                .telefonoDenunciante(DEFAULT_TELEFONO_DENUNCIANTE)
                .domicilioDenunciante(DEFAULT_DOMICILIO_DENUNCIANTE)
                .cpDenunciante(DEFAULT_CP_DENUNCIANTE)
                .nombreDenunciado(DEFAULT_NOMBRE_DENUNCIADO)
                .apellidoPaternoDenunciado(DEFAULT_APELLIDO_PATERNO_DENUNCIADO)
                .apellidoMaternoDenunciado(DEFAULT_APELLIDO_MATERNO_DENUNCIADO)
                .sobrenombreDenunciado(DEFAULT_SOBRENOMBRE_DENUNCIADO)
                .generoDenunciado(DEFAULT_GENERO_DENUNCIADO)
                .descripcionDenunciado(DEFAULT_DESCRIPCION_DENUNCIADO)
                .delito(DEFAULT_DELITO)
                .horarioDelito(DEFAULT_HORARIO_DELITO)
                .diaDelito(DEFAULT_DIA_DELITO)
                .calleDelito(DEFAULT_CALLE_DELITO)
                .numeroExtDelito(DEFAULT_NUMERO_EXT_DELITO)
                .numIntDelito(DEFAULT_NUM_INT_DELITO)
                .callePrincipalDelito(DEFAULT_CALLE_PRINCIPAL_DELITO)
                .calleCruceDelito(DEFAULT_CALLE_CRUCE_DELITO)
                .descripcionDomicilioDelito(DEFAULT_DESCRIPCION_DOMICILIO_DELITO)
                .narracionDelito(DEFAULT_NARRACION_DELITO)
                .policia(DEFAULT_POLICIA)
                .fechaDelitoPolicia(DEFAULT_FECHA_DELITO_POLICIA)
                .horaAproximadaDelitoPolicia(DEFAULT_HORA_APROXIMADA_DELITO_POLICIA)
                .municipioPolicia(DEFAULT_MUNICIPIO_POLICIA)
                .corporacionPolicia(DEFAULT_CORPORACION_POLICIA)
                .numeroUnidadPolicia(DEFAULT_NUMERO_UNIDAD_POLICIA)
                .colorUnidadaPolicia(DEFAULT_COLOR_UNIDADA_POLICIA)
                .placasPolicia(DEFAULT_PLACAS_POLICIA)
                .nombrePolicia(DEFAULT_NOMBRE_POLICIA)
                .aliasPolicia(DEFAULT_ALIAS_POLICIA)
                .domicilioPolicia(DEFAULT_DOMICILIO_POLICIA)
                .descripcionHechosPolicia(DEFAULT_DESCRIPCION_HECHOS_POLICIA)
                .estadoReporte(DEFAULT_ESTADO_REPORTE)
                .nombreDenunciante(DEFAULT_NOMBRE_DENUNCIANTE);
        return userReport;
    }

    @Before
    public void initTest() {
        userReport = createEntity(em);
    }


    public void createUserReport() throws Exception {
        int databaseSizeBeforeCreate = userReportRepository.findAll().size();

        // Create the UserReport

        restUserReportMockMvc.perform(post("/api/user-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userReport)))
            .andExpect(status().isCreated());

        // Validate the UserReport in the database
        List<UserReport> userReportList = userReportRepository.findAll();
        assertThat(userReportList).hasSize(databaseSizeBeforeCreate + 1);
        UserReport testUserReport = userReportList.get(userReportList.size() - 1);
        assertThat(testUserReport.isAnonimo()).isEqualTo(DEFAULT_ANONIMO);
        assertThat(testUserReport.getApellidoPaternoDenunciante()).isEqualTo(DEFAULT_APELLIDO_PATERNO_DENUNCIANTE);
        assertThat(testUserReport.getApellidoMaternoDenunciante()).isEqualTo(DEFAULT_APELLIDO_MATERNO_DENUNCIANTE);
        assertThat(testUserReport.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testUserReport.getCorreoDenunciante()).isEqualTo(DEFAULT_CORREO_DENUNCIANTE);
        assertThat(testUserReport.getTelefonoDenunciante()).isEqualTo(DEFAULT_TELEFONO_DENUNCIANTE);
        assertThat(testUserReport.getDomicilioDenunciante()).isEqualTo(DEFAULT_DOMICILIO_DENUNCIANTE);
        assertThat(testUserReport.getCpDenunciante()).isEqualTo(DEFAULT_CP_DENUNCIANTE);
        assertThat(testUserReport.getNombreDenunciado()).isEqualTo(DEFAULT_NOMBRE_DENUNCIADO);
        assertThat(testUserReport.getApellidoPaternoDenunciado()).isEqualTo(DEFAULT_APELLIDO_PATERNO_DENUNCIADO);
        assertThat(testUserReport.getApellidoMaternoDenunciado()).isEqualTo(DEFAULT_APELLIDO_MATERNO_DENUNCIADO);
        assertThat(testUserReport.getSobrenombreDenunciado()).isEqualTo(DEFAULT_SOBRENOMBRE_DENUNCIADO);
        assertThat(testUserReport.getGeneroDenunciado()).isEqualTo(DEFAULT_GENERO_DENUNCIADO);
        assertThat(testUserReport.getDescripcionDenunciado()).isEqualTo(DEFAULT_DESCRIPCION_DENUNCIADO);
        assertThat(testUserReport.getDelito()).isEqualTo(DEFAULT_DELITO);
        assertThat(testUserReport.getHorarioDelito()).isEqualTo(DEFAULT_HORARIO_DELITO);
        assertThat(testUserReport.getDiaDelito()).isEqualTo(DEFAULT_DIA_DELITO);
        assertThat(testUserReport.getCalleDelito()).isEqualTo(DEFAULT_CALLE_DELITO);
        assertThat(testUserReport.getNumeroExtDelito()).isEqualTo(DEFAULT_NUMERO_EXT_DELITO);
        assertThat(testUserReport.getNumIntDelito()).isEqualTo(DEFAULT_NUM_INT_DELITO);
        assertThat(testUserReport.getCallePrincipalDelito()).isEqualTo(DEFAULT_CALLE_PRINCIPAL_DELITO);
        assertThat(testUserReport.getCalleCruceDelito()).isEqualTo(DEFAULT_CALLE_CRUCE_DELITO);
        assertThat(testUserReport.getDescripcionDomicilioDelito()).isEqualTo(DEFAULT_DESCRIPCION_DOMICILIO_DELITO);
        assertThat(testUserReport.getNarracionDelito()).isEqualTo(DEFAULT_NARRACION_DELITO);
        assertThat(testUserReport.isPolicia()).isEqualTo(DEFAULT_POLICIA);
        assertThat(testUserReport.getFechaDelitoPolicia()).isEqualTo(DEFAULT_FECHA_DELITO_POLICIA);
        assertThat(testUserReport.getHoraAproximadaDelitoPolicia()).isEqualTo(DEFAULT_HORA_APROXIMADA_DELITO_POLICIA);
        assertThat(testUserReport.getMunicipioPolicia()).isEqualTo(DEFAULT_MUNICIPIO_POLICIA);
        assertThat(testUserReport.getCorporacionPolicia()).isEqualTo(DEFAULT_CORPORACION_POLICIA);
        assertThat(testUserReport.getNumeroUnidadPolicia()).isEqualTo(DEFAULT_NUMERO_UNIDAD_POLICIA);
        assertThat(testUserReport.getColorUnidadaPolicia()).isEqualTo(DEFAULT_COLOR_UNIDADA_POLICIA);
        assertThat(testUserReport.getPlacasPolicia()).isEqualTo(DEFAULT_PLACAS_POLICIA);
        assertThat(testUserReport.getNombrePolicia()).isEqualTo(DEFAULT_NOMBRE_POLICIA);
        assertThat(testUserReport.getAliasPolicia()).isEqualTo(DEFAULT_ALIAS_POLICIA);
        assertThat(testUserReport.getDomicilioPolicia()).isEqualTo(DEFAULT_DOMICILIO_POLICIA);
        assertThat(testUserReport.getDescripcionHechosPolicia()).isEqualTo(DEFAULT_DESCRIPCION_HECHOS_POLICIA);
        assertThat(testUserReport.getEstadoReporte()).isEqualTo(DEFAULT_ESTADO_REPORTE);
        assertThat(testUserReport.getNombreDenunciante()).isEqualTo(DEFAULT_NOMBRE_DENUNCIANTE);
    }


    public void createUserReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userReportRepository.findAll().size();

        // Create the UserReport with an existing ID
        UserReport existingUserReport = new UserReport();
        existingUserReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserReportMockMvc.perform(post("/api/user-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserReport)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserReport> userReportList = userReportRepository.findAll();
        assertThat(userReportList).hasSize(databaseSizeBeforeCreate);
    }


    public void getAllUserReports() throws Exception {
        // Initialize the database
        userReportRepository.saveAndFlush(userReport);

        // Get all the userReportList
        restUserReportMockMvc.perform(get("/api/user-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].anonimo").value(hasItem(DEFAULT_ANONIMO.booleanValue())))
            .andExpect(jsonPath("$.[*].apellidoPaternoDenunciante").value(hasItem(DEFAULT_APELLIDO_PATERNO_DENUNCIANTE.toString())))
            .andExpect(jsonPath("$.[*].apellidoMaternoDenunciante").value(hasItem(DEFAULT_APELLIDO_MATERNO_DENUNCIANTE.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].correoDenunciante").value(hasItem(DEFAULT_CORREO_DENUNCIANTE.toString())))
            .andExpect(jsonPath("$.[*].telefonoDenunciante").value(hasItem(DEFAULT_TELEFONO_DENUNCIANTE.toString())))
            .andExpect(jsonPath("$.[*].domicilioDenunciante").value(hasItem(DEFAULT_DOMICILIO_DENUNCIANTE.toString())))
            .andExpect(jsonPath("$.[*].cpDenunciante").value(hasItem(DEFAULT_CP_DENUNCIANTE)))
            .andExpect(jsonPath("$.[*].nombreDenunciado").value(hasItem(DEFAULT_NOMBRE_DENUNCIADO.toString())))
            .andExpect(jsonPath("$.[*].apellidoPaternoDenunciado").value(hasItem(DEFAULT_APELLIDO_PATERNO_DENUNCIADO.toString())))
            .andExpect(jsonPath("$.[*].apellidoMaternoDenunciado").value(hasItem(DEFAULT_APELLIDO_MATERNO_DENUNCIADO.toString())))
            .andExpect(jsonPath("$.[*].sobrenombreDenunciado").value(hasItem(DEFAULT_SOBRENOMBRE_DENUNCIADO.toString())))
            .andExpect(jsonPath("$.[*].generoDenunciado").value(hasItem(DEFAULT_GENERO_DENUNCIADO.toString())))
            .andExpect(jsonPath("$.[*].descripcionDenunciado").value(hasItem(DEFAULT_DESCRIPCION_DENUNCIADO.toString())))
            .andExpect(jsonPath("$.[*].delito").value(hasItem(DEFAULT_DELITO.toString())))
            .andExpect(jsonPath("$.[*].horarioDelito").value(hasItem(DEFAULT_HORARIO_DELITO.toString())))
            .andExpect(jsonPath("$.[*].diaDelito").value(hasItem(DEFAULT_DIA_DELITO.toString())))
            .andExpect(jsonPath("$.[*].calleDelito").value(hasItem(DEFAULT_CALLE_DELITO.toString())))
            .andExpect(jsonPath("$.[*].numeroExtDelito").value(hasItem(DEFAULT_NUMERO_EXT_DELITO.toString())))
            .andExpect(jsonPath("$.[*].numIntDelito").value(hasItem(DEFAULT_NUM_INT_DELITO.toString())))
            .andExpect(jsonPath("$.[*].callePrincipalDelito").value(hasItem(DEFAULT_CALLE_PRINCIPAL_DELITO.toString())))
            .andExpect(jsonPath("$.[*].calleCruceDelito").value(hasItem(DEFAULT_CALLE_CRUCE_DELITO.toString())))
            .andExpect(jsonPath("$.[*].descripcionDomicilioDelito").value(hasItem(DEFAULT_DESCRIPCION_DOMICILIO_DELITO.toString())))
            .andExpect(jsonPath("$.[*].narracionDelito").value(hasItem(DEFAULT_NARRACION_DELITO.toString())))
            .andExpect(jsonPath("$.[*].policia").value(hasItem(DEFAULT_POLICIA.booleanValue())))
            .andExpect(jsonPath("$.[*].fechaDelitoPolicia").value(hasItem(sameInstant(DEFAULT_FECHA_DELITO_POLICIA))))
            .andExpect(jsonPath("$.[*].horaAproximadaDelitoPolicia").value(hasItem(DEFAULT_HORA_APROXIMADA_DELITO_POLICIA.toString())))
            .andExpect(jsonPath("$.[*].municipioPolicia").value(hasItem(DEFAULT_MUNICIPIO_POLICIA.toString())))
            .andExpect(jsonPath("$.[*].corporacionPolicia").value(hasItem(DEFAULT_CORPORACION_POLICIA.toString())))
            .andExpect(jsonPath("$.[*].numeroUnidadPolicia").value(hasItem(DEFAULT_NUMERO_UNIDAD_POLICIA.toString())))
            .andExpect(jsonPath("$.[*].colorUnidadaPolicia").value(hasItem(DEFAULT_COLOR_UNIDADA_POLICIA.toString())))
            .andExpect(jsonPath("$.[*].placasPolicia").value(hasItem(DEFAULT_PLACAS_POLICIA.toString())))
            .andExpect(jsonPath("$.[*].nombrePolicia").value(hasItem(DEFAULT_NOMBRE_POLICIA.toString())))
            .andExpect(jsonPath("$.[*].aliasPolicia").value(hasItem(DEFAULT_ALIAS_POLICIA.toString())))
            .andExpect(jsonPath("$.[*].domicilioPolicia").value(hasItem(DEFAULT_DOMICILIO_POLICIA.toString())))
            .andExpect(jsonPath("$.[*].descripcionHechosPolicia").value(hasItem(DEFAULT_DESCRIPCION_HECHOS_POLICIA.toString())))
            .andExpect(jsonPath("$.[*].estadoReporte").value(hasItem(DEFAULT_ESTADO_REPORTE.toString())))
            .andExpect(jsonPath("$.[*].nombreDenunciante").value(hasItem(DEFAULT_NOMBRE_DENUNCIANTE.toString())));
    }

    @Test
    @Transactional
    public void getUserReport() throws Exception {
        // Initialize the database
        userReportRepository.saveAndFlush(userReport);

        // Get the userReport
        restUserReportMockMvc.perform(get("/api/user-reports/{id}", userReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userReport.getId().intValue()))
            .andExpect(jsonPath("$.anonimo").value(DEFAULT_ANONIMO.booleanValue()))
            .andExpect(jsonPath("$.apellidoPaternoDenunciante").value(DEFAULT_APELLIDO_PATERNO_DENUNCIANTE.toString()))
            .andExpect(jsonPath("$.apellidoMaternoDenunciante").value(DEFAULT_APELLIDO_MATERNO_DENUNCIANTE.toString()))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO.toString()))
            .andExpect(jsonPath("$.correoDenunciante").value(DEFAULT_CORREO_DENUNCIANTE.toString()))
            .andExpect(jsonPath("$.telefonoDenunciante").value(DEFAULT_TELEFONO_DENUNCIANTE.toString()))
            .andExpect(jsonPath("$.domicilioDenunciante").value(DEFAULT_DOMICILIO_DENUNCIANTE.toString()))
            .andExpect(jsonPath("$.cpDenunciante").value(DEFAULT_CP_DENUNCIANTE))
            .andExpect(jsonPath("$.nombreDenunciado").value(DEFAULT_NOMBRE_DENUNCIADO.toString()))
            .andExpect(jsonPath("$.apellidoPaternoDenunciado").value(DEFAULT_APELLIDO_PATERNO_DENUNCIADO.toString()))
            .andExpect(jsonPath("$.apellidoMaternoDenunciado").value(DEFAULT_APELLIDO_MATERNO_DENUNCIADO.toString()))
            .andExpect(jsonPath("$.sobrenombreDenunciado").value(DEFAULT_SOBRENOMBRE_DENUNCIADO.toString()))
            .andExpect(jsonPath("$.generoDenunciado").value(DEFAULT_GENERO_DENUNCIADO.toString()))
            .andExpect(jsonPath("$.descripcionDenunciado").value(DEFAULT_DESCRIPCION_DENUNCIADO.toString()))
            .andExpect(jsonPath("$.delito").value(DEFAULT_DELITO.toString()))
            .andExpect(jsonPath("$.horarioDelito").value(DEFAULT_HORARIO_DELITO.toString()))
            .andExpect(jsonPath("$.diaDelito").value(DEFAULT_DIA_DELITO.toString()))
            .andExpect(jsonPath("$.calleDelito").value(DEFAULT_CALLE_DELITO.toString()))
            .andExpect(jsonPath("$.numeroExtDelito").value(DEFAULT_NUMERO_EXT_DELITO.toString()))
            .andExpect(jsonPath("$.numIntDelito").value(DEFAULT_NUM_INT_DELITO.toString()))
            .andExpect(jsonPath("$.callePrincipalDelito").value(DEFAULT_CALLE_PRINCIPAL_DELITO.toString()))
            .andExpect(jsonPath("$.calleCruceDelito").value(DEFAULT_CALLE_CRUCE_DELITO.toString()))
            .andExpect(jsonPath("$.descripcionDomicilioDelito").value(DEFAULT_DESCRIPCION_DOMICILIO_DELITO.toString()))
            .andExpect(jsonPath("$.narracionDelito").value(DEFAULT_NARRACION_DELITO.toString()))
            .andExpect(jsonPath("$.policia").value(DEFAULT_POLICIA.booleanValue()))
            .andExpect(jsonPath("$.fechaDelitoPolicia").value(sameInstant(DEFAULT_FECHA_DELITO_POLICIA)))
            .andExpect(jsonPath("$.horaAproximadaDelitoPolicia").value(DEFAULT_HORA_APROXIMADA_DELITO_POLICIA.toString()))
            .andExpect(jsonPath("$.municipioPolicia").value(DEFAULT_MUNICIPIO_POLICIA.toString()))
            .andExpect(jsonPath("$.corporacionPolicia").value(DEFAULT_CORPORACION_POLICIA.toString()))
            .andExpect(jsonPath("$.numeroUnidadPolicia").value(DEFAULT_NUMERO_UNIDAD_POLICIA.toString()))
            .andExpect(jsonPath("$.colorUnidadaPolicia").value(DEFAULT_COLOR_UNIDADA_POLICIA.toString()))
            .andExpect(jsonPath("$.placasPolicia").value(DEFAULT_PLACAS_POLICIA.toString()))
            .andExpect(jsonPath("$.nombrePolicia").value(DEFAULT_NOMBRE_POLICIA.toString()))
            .andExpect(jsonPath("$.aliasPolicia").value(DEFAULT_ALIAS_POLICIA.toString()))
            .andExpect(jsonPath("$.domicilioPolicia").value(DEFAULT_DOMICILIO_POLICIA.toString()))
            .andExpect(jsonPath("$.descripcionHechosPolicia").value(DEFAULT_DESCRIPCION_HECHOS_POLICIA.toString()))
            .andExpect(jsonPath("$.estadoReporte").value(DEFAULT_ESTADO_REPORTE.toString()))
            .andExpect(jsonPath("$.nombreDenunciante").value(DEFAULT_NOMBRE_DENUNCIANTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserReport() throws Exception {
        // Get the userReport
        restUserReportMockMvc.perform(get("/api/user-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserReport() throws Exception {
        // Initialize the database
        userReportRepository.saveAndFlush(userReport);
        int databaseSizeBeforeUpdate = userReportRepository.findAll().size();

        // Update the userReport
        UserReport updatedUserReport = userReportRepository.findOne(userReport.getId());
        updatedUserReport
                .anonimo(UPDATED_ANONIMO)
                .apellidoPaternoDenunciante(UPDATED_APELLIDO_PATERNO_DENUNCIANTE)
                .apellidoMaternoDenunciante(UPDATED_APELLIDO_MATERNO_DENUNCIANTE)
                .genero(UPDATED_GENERO)
                .correoDenunciante(UPDATED_CORREO_DENUNCIANTE)
                .telefonoDenunciante(UPDATED_TELEFONO_DENUNCIANTE)
                .domicilioDenunciante(UPDATED_DOMICILIO_DENUNCIANTE)
                .cpDenunciante(UPDATED_CP_DENUNCIANTE)
                .nombreDenunciado(UPDATED_NOMBRE_DENUNCIADO)
                .apellidoPaternoDenunciado(UPDATED_APELLIDO_PATERNO_DENUNCIADO)
                .apellidoMaternoDenunciado(UPDATED_APELLIDO_MATERNO_DENUNCIADO)
                .sobrenombreDenunciado(UPDATED_SOBRENOMBRE_DENUNCIADO)
                .generoDenunciado(UPDATED_GENERO_DENUNCIADO)
                .descripcionDenunciado(UPDATED_DESCRIPCION_DENUNCIADO)
                .delito(UPDATED_DELITO)
                .horarioDelito(UPDATED_HORARIO_DELITO)
                .diaDelito(UPDATED_DIA_DELITO)
                .calleDelito(UPDATED_CALLE_DELITO)
                .numeroExtDelito(UPDATED_NUMERO_EXT_DELITO)
                .numIntDelito(UPDATED_NUM_INT_DELITO)
                .callePrincipalDelito(UPDATED_CALLE_PRINCIPAL_DELITO)
                .calleCruceDelito(UPDATED_CALLE_CRUCE_DELITO)
                .descripcionDomicilioDelito(UPDATED_DESCRIPCION_DOMICILIO_DELITO)
                .narracionDelito(UPDATED_NARRACION_DELITO)
                .policia(UPDATED_POLICIA)
                .fechaDelitoPolicia(UPDATED_FECHA_DELITO_POLICIA)
                .horaAproximadaDelitoPolicia(UPDATED_HORA_APROXIMADA_DELITO_POLICIA)
                .municipioPolicia(UPDATED_MUNICIPIO_POLICIA)
                .corporacionPolicia(UPDATED_CORPORACION_POLICIA)
                .numeroUnidadPolicia(UPDATED_NUMERO_UNIDAD_POLICIA)
                .colorUnidadaPolicia(UPDATED_COLOR_UNIDADA_POLICIA)
                .placasPolicia(UPDATED_PLACAS_POLICIA)
                .nombrePolicia(UPDATED_NOMBRE_POLICIA)
                .aliasPolicia(UPDATED_ALIAS_POLICIA)
                .domicilioPolicia(UPDATED_DOMICILIO_POLICIA)
                .descripcionHechosPolicia(UPDATED_DESCRIPCION_HECHOS_POLICIA)
                .estadoReporte(UPDATED_ESTADO_REPORTE)
                .nombreDenunciante(UPDATED_NOMBRE_DENUNCIANTE);

        restUserReportMockMvc.perform(put("/api/user-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserReport)))
            .andExpect(status().isOk());

        // Validate the UserReport in the database
        List<UserReport> userReportList = userReportRepository.findAll();
        assertThat(userReportList).hasSize(databaseSizeBeforeUpdate);
        UserReport testUserReport = userReportList.get(userReportList.size() - 1);
        assertThat(testUserReport.isAnonimo()).isEqualTo(UPDATED_ANONIMO);
        assertThat(testUserReport.getApellidoPaternoDenunciante()).isEqualTo(UPDATED_APELLIDO_PATERNO_DENUNCIANTE);
        assertThat(testUserReport.getApellidoMaternoDenunciante()).isEqualTo(UPDATED_APELLIDO_MATERNO_DENUNCIANTE);
        assertThat(testUserReport.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testUserReport.getCorreoDenunciante()).isEqualTo(UPDATED_CORREO_DENUNCIANTE);
        assertThat(testUserReport.getTelefonoDenunciante()).isEqualTo(UPDATED_TELEFONO_DENUNCIANTE);
        assertThat(testUserReport.getDomicilioDenunciante()).isEqualTo(UPDATED_DOMICILIO_DENUNCIANTE);
        assertThat(testUserReport.getCpDenunciante()).isEqualTo(UPDATED_CP_DENUNCIANTE);
        assertThat(testUserReport.getNombreDenunciado()).isEqualTo(UPDATED_NOMBRE_DENUNCIADO);
        assertThat(testUserReport.getApellidoPaternoDenunciado()).isEqualTo(UPDATED_APELLIDO_PATERNO_DENUNCIADO);
        assertThat(testUserReport.getApellidoMaternoDenunciado()).isEqualTo(UPDATED_APELLIDO_MATERNO_DENUNCIADO);
        assertThat(testUserReport.getSobrenombreDenunciado()).isEqualTo(UPDATED_SOBRENOMBRE_DENUNCIADO);
        assertThat(testUserReport.getGeneroDenunciado()).isEqualTo(UPDATED_GENERO_DENUNCIADO);
        assertThat(testUserReport.getDescripcionDenunciado()).isEqualTo(UPDATED_DESCRIPCION_DENUNCIADO);
        assertThat(testUserReport.getDelito()).isEqualTo(UPDATED_DELITO);
        assertThat(testUserReport.getHorarioDelito()).isEqualTo(UPDATED_HORARIO_DELITO);
        assertThat(testUserReport.getDiaDelito()).isEqualTo(UPDATED_DIA_DELITO);
        assertThat(testUserReport.getCalleDelito()).isEqualTo(UPDATED_CALLE_DELITO);
        assertThat(testUserReport.getNumeroExtDelito()).isEqualTo(UPDATED_NUMERO_EXT_DELITO);
        assertThat(testUserReport.getNumIntDelito()).isEqualTo(UPDATED_NUM_INT_DELITO);
        assertThat(testUserReport.getCallePrincipalDelito()).isEqualTo(UPDATED_CALLE_PRINCIPAL_DELITO);
        assertThat(testUserReport.getCalleCruceDelito()).isEqualTo(UPDATED_CALLE_CRUCE_DELITO);
        assertThat(testUserReport.getDescripcionDomicilioDelito()).isEqualTo(UPDATED_DESCRIPCION_DOMICILIO_DELITO);
        assertThat(testUserReport.getNarracionDelito()).isEqualTo(UPDATED_NARRACION_DELITO);
        assertThat(testUserReport.isPolicia()).isEqualTo(UPDATED_POLICIA);
        assertThat(testUserReport.getFechaDelitoPolicia()).isEqualTo(UPDATED_FECHA_DELITO_POLICIA);
        assertThat(testUserReport.getHoraAproximadaDelitoPolicia()).isEqualTo(UPDATED_HORA_APROXIMADA_DELITO_POLICIA);
        assertThat(testUserReport.getMunicipioPolicia()).isEqualTo(UPDATED_MUNICIPIO_POLICIA);
        assertThat(testUserReport.getCorporacionPolicia()).isEqualTo(UPDATED_CORPORACION_POLICIA);
        assertThat(testUserReport.getNumeroUnidadPolicia()).isEqualTo(UPDATED_NUMERO_UNIDAD_POLICIA);
        assertThat(testUserReport.getColorUnidadaPolicia()).isEqualTo(UPDATED_COLOR_UNIDADA_POLICIA);
        assertThat(testUserReport.getPlacasPolicia()).isEqualTo(UPDATED_PLACAS_POLICIA);
        assertThat(testUserReport.getNombrePolicia()).isEqualTo(UPDATED_NOMBRE_POLICIA);
        assertThat(testUserReport.getAliasPolicia()).isEqualTo(UPDATED_ALIAS_POLICIA);
        assertThat(testUserReport.getDomicilioPolicia()).isEqualTo(UPDATED_DOMICILIO_POLICIA);
        assertThat(testUserReport.getDescripcionHechosPolicia()).isEqualTo(UPDATED_DESCRIPCION_HECHOS_POLICIA);
        assertThat(testUserReport.getEstadoReporte()).isEqualTo(UPDATED_ESTADO_REPORTE);
        assertThat(testUserReport.getNombreDenunciante()).isEqualTo(UPDATED_NOMBRE_DENUNCIANTE);
    }

    public void updateNonExistingUserReport() throws Exception {
        int databaseSizeBeforeUpdate = userReportRepository.findAll().size();

        // Create the UserReport

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserReportMockMvc.perform(put("/api/user-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userReport)))
            .andExpect(status().isCreated());

        // Validate the UserReport in the database
        List<UserReport> userReportList = userReportRepository.findAll();
        assertThat(userReportList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserReport() throws Exception {
        // Initialize the database
        userReportRepository.saveAndFlush(userReport);
        int databaseSizeBeforeDelete = userReportRepository.findAll().size();

        // Get the userReport
        restUserReportMockMvc.perform(delete("/api/user-reports/{id}", userReport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserReport> userReportList = userReportRepository.findAll();
        assertThat(userReportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
