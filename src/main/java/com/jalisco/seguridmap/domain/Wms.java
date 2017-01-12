package com.jalisco.seguridmap.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Wms.
 */
@Entity
@Table(name = "wms")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Wms implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "url")
    private String url;

    @Column(name = "configuracion")
    private String configuracion;

    @Column(name = "capa")
    private String capa;

    @ManyToOne
    @NotNull
    private Panel panel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Wms nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public Wms url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getConfiguracion() {
        return configuracion;
    }

    public Wms configuracion(String configuracion) {
        this.configuracion = configuracion;
        return this;
    }

    public void setConfiguracion(String configuracion) {
        this.configuracion = configuracion;
    }

    public String getCapa() {
        return capa;
    }

    public Wms capa(String capa) {
        this.capa = capa;
        return this;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    public Panel getPanel() {
        return panel;
    }

    public Wms panel(Panel panel) {
        this.panel = panel;
        return this;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Wms wms = (Wms) o;
        if (wms.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, wms.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Wms{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", url='" + url + "'" +
            ", configuracion='" + configuracion + "'" +
            ", capa='" + capa + "'" +
            '}';
    }
}
