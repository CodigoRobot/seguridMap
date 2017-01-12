package com.jalisco.seguridmap.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.jalisco.seguridmap.domain.enumeration.Genero;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.geom.GeometryFactory;

import com.jalisco.seguridmap.domain.enumeration.EstadoReporte;

/**
 * A UserReport.
 */
@Entity
@Table(name = "user_report")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "anonimo")
    private Boolean anonimo;

    @Column(name = "apellido_paterno_denunciante")
    private String apellidoPaternoDenunciante;

    @Column(name = "apellido_materno_denunciante")
    private String apellidoMaternoDenunciante;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private Genero genero;

    @Column(name = "correo_denunciante")
    private String correoDenunciante;

    @Column(name = "telefono_denunciante")
    private String telefonoDenunciante;

    @Column(name = "domicilio_denunciante")
    private String domicilioDenunciante;

    @Column(name = "cp_denunciante")
    private Integer cpDenunciante;

    @Column(name = "nombre_denunciado")
    private String nombreDenunciado;

    @Column(name = "apellido_paterno_denunciado")
    private String apellidoPaternoDenunciado;

    @Column(name = "apellido_materno_denunciado")
    private String apellidoMaternoDenunciado;

    @Column(name = "sobrenombre_denunciado")
    private String sobrenombreDenunciado;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero_denunciado")
    private Genero generoDenunciado;

    @Column(name = "descripcion_denunciado")
    private String descripcionDenunciado;

    @Column(name = "delito")
    private String delito;

    @Column(name = "horario_delito")
    private String horarioDelito;

    @Column(name = "dia_delito")
    private String diaDelito;

    @Column(name = "calle_delito")
    private String calleDelito;

    @Column(name = "numero_ext_delito")
    private String numeroExtDelito;

    @Column(name = "num_int_delito")
    private String numIntDelito;

    @Column(name = "calle_principal_delito")
    private String callePrincipalDelito;

    @Column(name = "calle_cruce_delito")
    private String calleCruceDelito;

    @Column(name = "descripcion_domicilio_delito")
    private String descripcionDomicilioDelito;

    @Column(name = "narracion_delito")
    private String narracionDelito;

    @Column(name = "policia")
    private Boolean policia;

    @Column(name = "fecha_delito_policia")
    private ZonedDateTime fechaDelitoPolicia;

    @Column(name = "hora_aproximada_delito_policia")
    private String horaAproximadaDelitoPolicia;

    @Column(name = "municipio_policia")
    private String municipioPolicia;

    @Column(name = "corporacion_policia")
    private String corporacionPolicia;

    @Column(name = "numero_unidad_policia")
    private String numeroUnidadPolicia;

    @Column(name = "color_unidada_policia")
    private String colorUnidadaPolicia;

    @Column(name = "placas_policia")
    private String placasPolicia;

    @Column(name = "nombre_policia")
    private String nombrePolicia;

    @Column(name = "alias_policia")
    private String aliasPolicia;

    @Column(name = "domicilio_policia")
    private String domicilioPolicia;

    @Column(name = "descripcion_hechos_policia")
    private String descripcionHechosPolicia;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_reporte")
    private EstadoReporte estadoReporte;

    @Column(name = "nombre_denunciante")
    private String nombreDenunciante;

    @ManyToOne
    private User autor;

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point position;

    @Column(name = "observacion")
    private String observacion;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAnonimo() {
        return anonimo;
    }

    public UserReport anonimo(Boolean anonimo) {
        this.anonimo = anonimo;
        return this;
    }

    public void setAnonimo(Boolean anonimo) {
        this.anonimo = anonimo;
    }

    public String getApellidoPaternoDenunciante() {
        return apellidoPaternoDenunciante;
    }

    public UserReport apellidoPaternoDenunciante(String apellidoPaternoDenunciante) {
        this.apellidoPaternoDenunciante = apellidoPaternoDenunciante;
        return this;
    }

    public void setApellidoPaternoDenunciante(String apellidoPaternoDenunciante) {
        this.apellidoPaternoDenunciante = apellidoPaternoDenunciante;
    }

    public String getApellidoMaternoDenunciante() {
        return apellidoMaternoDenunciante;
    }

    public UserReport apellidoMaternoDenunciante(String apellidoMaternoDenunciante) {
        this.apellidoMaternoDenunciante = apellidoMaternoDenunciante;
        return this;
    }

    public void setApellidoMaternoDenunciante(String apellidoMaternoDenunciante) {
        this.apellidoMaternoDenunciante = apellidoMaternoDenunciante;
    }

    public Genero getGenero() {
        return genero;
    }

    public UserReport genero(Genero genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getCorreoDenunciante() {
        return correoDenunciante;
    }

    public UserReport correoDenunciante(String correoDenunciante) {
        this.correoDenunciante = correoDenunciante;
        return this;
    }

    public void setCorreoDenunciante(String correoDenunciante) {
        this.correoDenunciante = correoDenunciante;
    }

    public String getTelefonoDenunciante() {
        return telefonoDenunciante;
    }

    public UserReport telefonoDenunciante(String telefonoDenunciante) {
        this.telefonoDenunciante = telefonoDenunciante;
        return this;
    }

    public void setTelefonoDenunciante(String telefonoDenunciante) {
        this.telefonoDenunciante = telefonoDenunciante;
    }

    public String getDomicilioDenunciante() {
        return domicilioDenunciante;
    }

    public UserReport domicilioDenunciante(String domicilioDenunciante) {
        this.domicilioDenunciante = domicilioDenunciante;
        return this;
    }

    public void setDomicilioDenunciante(String domicilioDenunciante) {
        this.domicilioDenunciante = domicilioDenunciante;
    }

    public Integer getCpDenunciante() {
        return cpDenunciante;
    }

    public UserReport cpDenunciante(Integer cpDenunciante) {
        this.cpDenunciante = cpDenunciante;
        return this;
    }

    public void setCpDenunciante(Integer cpDenunciante) {
        this.cpDenunciante = cpDenunciante;
    }

    public String getNombreDenunciado() {
        return nombreDenunciado;
    }

    public UserReport nombreDenunciado(String nombreDenunciado) {
        this.nombreDenunciado = nombreDenunciado;
        return this;
    }

    public void setNombreDenunciado(String nombreDenunciado) {
        this.nombreDenunciado = nombreDenunciado;
    }

    public String getApellidoPaternoDenunciado() {
        return apellidoPaternoDenunciado;
    }

    public UserReport apellidoPaternoDenunciado(String apellidoPaternoDenunciado) {
        this.apellidoPaternoDenunciado = apellidoPaternoDenunciado;
        return this;
    }

    public void setApellidoPaternoDenunciado(String apellidoPaternoDenunciado) {
        this.apellidoPaternoDenunciado = apellidoPaternoDenunciado;
    }

    public String getApellidoMaternoDenunciado() {
        return apellidoMaternoDenunciado;
    }

    public UserReport apellidoMaternoDenunciado(String apellidoMaternoDenunciado) {
        this.apellidoMaternoDenunciado = apellidoMaternoDenunciado;
        return this;
    }

    public void setApellidoMaternoDenunciado(String apellidoMaternoDenunciado) {
        this.apellidoMaternoDenunciado = apellidoMaternoDenunciado;
    }

    public String getSobrenombreDenunciado() {
        return sobrenombreDenunciado;
    }

    public UserReport sobrenombreDenunciado(String sobrenombreDenunciado) {
        this.sobrenombreDenunciado = sobrenombreDenunciado;
        return this;
    }

    public void setSobrenombreDenunciado(String sobrenombreDenunciado) {
        this.sobrenombreDenunciado = sobrenombreDenunciado;
    }

    public Genero getGeneroDenunciado() {
        return generoDenunciado;
    }

    public UserReport generoDenunciado(Genero generoDenunciado) {
        this.generoDenunciado = generoDenunciado;
        return this;
    }

    public void setGeneroDenunciado(Genero generoDenunciado) {
        this.generoDenunciado = generoDenunciado;
    }

    public String getDescripcionDenunciado() {
        return descripcionDenunciado;
    }

    public UserReport descripcionDenunciado(String descripcionDenunciado) {
        this.descripcionDenunciado = descripcionDenunciado;
        return this;
    }

    public void setDescripcionDenunciado(String descripcionDenunciado) {
        this.descripcionDenunciado = descripcionDenunciado;
    }

    public String getDelito() {
        return delito;
    }

    public UserReport delito(String delito) {
        this.delito = delito;
        return this;
    }

    public void setDelito(String delito) {
        this.delito = delito;
    }

    public String getHorarioDelito() {
        return horarioDelito;
    }

    public UserReport horarioDelito(String horarioDelito) {
        this.horarioDelito = horarioDelito;
        return this;
    }

    public void setHorarioDelito(String horarioDelito) {
        this.horarioDelito = horarioDelito;
    }

    public String getDiaDelito() {
        return diaDelito;
    }

    public UserReport diaDelito(String diaDelito) {
        this.diaDelito = diaDelito;
        return this;
    }

    public void setDiaDelito(String diaDelito) {
        this.diaDelito = diaDelito;
    }

    public String getCalleDelito() {
        return calleDelito;
    }

    public UserReport calleDelito(String calleDelito) {
        this.calleDelito = calleDelito;
        return this;
    }

    public void setCalleDelito(String calleDelito) {
        this.calleDelito = calleDelito;
    }

    public String getNumeroExtDelito() {
        return numeroExtDelito;
    }

    public UserReport numeroExtDelito(String numeroExtDelito) {
        this.numeroExtDelito = numeroExtDelito;
        return this;
    }

    public void setNumeroExtDelito(String numeroExtDelito) {
        this.numeroExtDelito = numeroExtDelito;
    }

    public String getNumIntDelito() {
        return numIntDelito;
    }

    public UserReport numIntDelito(String numIntDelito) {
        this.numIntDelito = numIntDelito;
        return this;
    }

    public void setNumIntDelito(String numIntDelito) {
        this.numIntDelito = numIntDelito;
    }

    public String getCallePrincipalDelito() {
        return callePrincipalDelito;
    }

    public UserReport callePrincipalDelito(String callePrincipalDelito) {
        this.callePrincipalDelito = callePrincipalDelito;
        return this;
    }

    public void setCallePrincipalDelito(String callePrincipalDelito) {
        this.callePrincipalDelito = callePrincipalDelito;
    }

    public String getCalleCruceDelito() {
        return calleCruceDelito;
    }

    public UserReport calleCruceDelito(String calleCruceDelito) {
        this.calleCruceDelito = calleCruceDelito;
        return this;
    }

    public void setCalleCruceDelito(String calleCruceDelito) {
        this.calleCruceDelito = calleCruceDelito;
    }

    public String getDescripcionDomicilioDelito() {
        return descripcionDomicilioDelito;
    }

    public UserReport descripcionDomicilioDelito(String descripcionDomicilioDelito) {
        this.descripcionDomicilioDelito = descripcionDomicilioDelito;
        return this;
    }

    public void setDescripcionDomicilioDelito(String descripcionDomicilioDelito) {
        this.descripcionDomicilioDelito = descripcionDomicilioDelito;
    }

    public String getNarracionDelito() {
        return narracionDelito;
    }

    public UserReport narracionDelito(String narracionDelito) {
        this.narracionDelito = narracionDelito;
        return this;
    }

    public void setNarracionDelito(String narracionDelito) {
        this.narracionDelito = narracionDelito;
    }

    public Boolean isPolicia() {
        return policia;
    }

    public UserReport policia(Boolean policia) {
        this.policia = policia;
        return this;
    }

    public void setPolicia(Boolean policia) {
        this.policia = policia;
    }

    public ZonedDateTime getFechaDelitoPolicia() {
        return fechaDelitoPolicia;
    }

    public UserReport fechaDelitoPolicia(ZonedDateTime fechaDelitoPolicia) {
        this.fechaDelitoPolicia = fechaDelitoPolicia;
        return this;
    }

    public void setFechaDelitoPolicia(ZonedDateTime fechaDelitoPolicia) {
        this.fechaDelitoPolicia = fechaDelitoPolicia;
    }

    public String getHoraAproximadaDelitoPolicia() {
        return horaAproximadaDelitoPolicia;
    }

    public UserReport horaAproximadaDelitoPolicia(String horaAproximadaDelitoPolicia) {
        this.horaAproximadaDelitoPolicia = horaAproximadaDelitoPolicia;
        return this;
    }

    public void setHoraAproximadaDelitoPolicia(String horaAproximadaDelitoPolicia) {
        this.horaAproximadaDelitoPolicia = horaAproximadaDelitoPolicia;
    }

    public String getMunicipioPolicia() {
        return municipioPolicia;
    }

    public UserReport municipioPolicia(String municipioPolicia) {
        this.municipioPolicia = municipioPolicia;
        return this;
    }

    public void setMunicipioPolicia(String municipioPolicia) {
        this.municipioPolicia = municipioPolicia;
    }

    public String getCorporacionPolicia() {
        return corporacionPolicia;
    }

    public UserReport corporacionPolicia(String corporacionPolicia) {
        this.corporacionPolicia = corporacionPolicia;
        return this;
    }

    public void setCorporacionPolicia(String corporacionPolicia) {
        this.corporacionPolicia = corporacionPolicia;
    }

    public String getNumeroUnidadPolicia() {
        return numeroUnidadPolicia;
    }

    public UserReport numeroUnidadPolicia(String numeroUnidadPolicia) {
        this.numeroUnidadPolicia = numeroUnidadPolicia;
        return this;
    }

    public void setNumeroUnidadPolicia(String numeroUnidadPolicia) {
        this.numeroUnidadPolicia = numeroUnidadPolicia;
    }

    public String getColorUnidadaPolicia() {
        return colorUnidadaPolicia;
    }

    public UserReport colorUnidadaPolicia(String colorUnidadaPolicia) {
        this.colorUnidadaPolicia = colorUnidadaPolicia;
        return this;
    }

    public void setColorUnidadaPolicia(String colorUnidadaPolicia) {
        this.colorUnidadaPolicia = colorUnidadaPolicia;
    }

    public String getPlacasPolicia() {
        return placasPolicia;
    }

    public UserReport placasPolicia(String placasPolicia) {
        this.placasPolicia = placasPolicia;
        return this;
    }

    public void setPlacasPolicia(String placasPolicia) {
        this.placasPolicia = placasPolicia;
    }

    public String getNombrePolicia() {
        return nombrePolicia;
    }

    public UserReport nombrePolicia(String nombrePolicia) {
        this.nombrePolicia = nombrePolicia;
        return this;
    }

    public void setNombrePolicia(String nombrePolicia) {
        this.nombrePolicia = nombrePolicia;
    }

    public String getAliasPolicia() {
        return aliasPolicia;
    }

    public UserReport aliasPolicia(String aliasPolicia) {
        this.aliasPolicia = aliasPolicia;
        return this;
    }

    public void setAliasPolicia(String aliasPolicia) {
        this.aliasPolicia = aliasPolicia;
    }

    public String getDomicilioPolicia() {
        return domicilioPolicia;
    }

    public UserReport domicilioPolicia(String domicilioPolicia) {
        this.domicilioPolicia = domicilioPolicia;
        return this;
    }

    public void setDomicilioPolicia(String domicilioPolicia) {
        this.domicilioPolicia = domicilioPolicia;
    }

    public String getDescripcionHechosPolicia() {
        return descripcionHechosPolicia;
    }

    public UserReport descripcionHechosPolicia(String descripcionHechosPolicia) {
        this.descripcionHechosPolicia = descripcionHechosPolicia;
        return this;
    }

    public void setDescripcionHechosPolicia(String descripcionHechosPolicia) {
        this.descripcionHechosPolicia = descripcionHechosPolicia;
    }

    public EstadoReporte getEstadoReporte() {
        return estadoReporte;
    }

    public UserReport estadoReporte(EstadoReporte estadoReporte) {
        this.estadoReporte = estadoReporte;
        return this;
    }

    public void setEstadoReporte(EstadoReporte estadoReporte) {
        this.estadoReporte = estadoReporte;
    }

    public String getNombreDenunciante() {
        return nombreDenunciante;
    }

    public UserReport nombreDenunciante(String nombreDenunciante) {
        this.nombreDenunciante = nombreDenunciante;
        return this;
    }

    public void setNombreDenunciante(String nombreDenunciante) {
        this.nombreDenunciante = nombreDenunciante;
    }

    public User getAutor() {
        return autor;
    }

    public UserReport autor(User user) {
        this.autor = user;
        return this;
    }

    public void setAutor(User user) {
        this.autor = user;
    }

    public String getPosition() {
        if(position!=null)
            return position.toText();
        return null;
    }

    public void setPosition(String position){
        if(position!=null && position.length() > 0 ) {
            GeometryFactory geometryFactory = new GeometryFactory();
            WKTReader reader = new WKTReader(geometryFactory);
            try {
                this.position = (Point) reader.read(position);
                this.position.setSRID(4326);
            } catch (ParseException e) {
            }
        }
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserReport userReport = (UserReport) o;
        if (userReport.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userReport.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserReport{" +
            "id=" + id +
            ", anonimo='" + anonimo + "'" +
            ", apellidoPaternoDenunciante='" + apellidoPaternoDenunciante + "'" +
            ", apellidoMaternoDenunciante='" + apellidoMaternoDenunciante + "'" +
            ", genero='" + genero + "'" +
            ", correoDenunciante='" + correoDenunciante + "'" +
            ", telefonoDenunciante='" + telefonoDenunciante + "'" +
            ", domicilioDenunciante='" + domicilioDenunciante + "'" +
            ", cpDenunciante='" + cpDenunciante + "'" +
            ", nombreDenunciado='" + nombreDenunciado + "'" +
            ", apellidoPaternoDenunciado='" + apellidoPaternoDenunciado + "'" +
            ", apellidoMaternoDenunciado='" + apellidoMaternoDenunciado + "'" +
            ", sobrenombreDenunciado='" + sobrenombreDenunciado + "'" +
            ", generoDenunciado='" + generoDenunciado + "'" +
            ", descripcionDenunciado='" + descripcionDenunciado + "'" +
            ", delito='" + delito + "'" +
            ", horarioDelito='" + horarioDelito + "'" +
            ", diaDelito='" + diaDelito + "'" +
            ", calleDelito='" + calleDelito + "'" +
            ", numeroExtDelito='" + numeroExtDelito + "'" +
            ", numIntDelito='" + numIntDelito + "'" +
            ", callePrincipalDelito='" + callePrincipalDelito + "'" +
            ", calleCruceDelito='" + calleCruceDelito + "'" +
            ", descripcionDomicilioDelito='" + descripcionDomicilioDelito + "'" +
            ", narracionDelito='" + narracionDelito + "'" +
            ", policia='" + policia + "'" +
            ", fechaDelitoPolicia='" + fechaDelitoPolicia + "'" +
            ", horaAproximadaDelitoPolicia='" + horaAproximadaDelitoPolicia + "'" +
            ", municipioPolicia='" + municipioPolicia + "'" +
            ", corporacionPolicia='" + corporacionPolicia + "'" +
            ", numeroUnidadPolicia='" + numeroUnidadPolicia + "'" +
            ", colorUnidadaPolicia='" + colorUnidadaPolicia + "'" +
            ", placasPolicia='" + placasPolicia + "'" +
            ", nombrePolicia='" + nombrePolicia + "'" +
            ", aliasPolicia='" + aliasPolicia + "'" +
            ", domicilioPolicia='" + domicilioPolicia + "'" +
            ", descripcionHechosPolicia='" + descripcionHechosPolicia + "'" +
            ", estadoReporte='" + estadoReporte + "'" +
            ", nombreDenunciante='" + nombreDenunciante + "'" +
            '}';
    }
}
