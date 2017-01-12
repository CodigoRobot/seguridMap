package com.jalisco.seguridmap.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Panel.
 */
@Entity
@Table(name = "panel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Panel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "configuracion")
    private String configuracion;

    @Column(name = "orden")
    private Long orden;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Panel nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Panel descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getConfiguracion() {
        return configuracion;
    }

    public Panel configuracion(String configuracion) {
        this.configuracion = configuracion;
        return this;
    }

    public void setConfiguracion(String configuracion) {
        this.configuracion = configuracion;
    }

    public Long getOrden() {
        return orden;
    }

    public Panel orden(Long orden) {
        this.orden = orden;
        return this;
    }

    public void setOrden(Long orden) {
        this.orden = orden;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Panel panel = (Panel) o;
        if (panel.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, panel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Panel{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", descripcion='" + descripcion + "'" +
            ", configuracion='" + configuracion + "'" +
            ", orden='" + orden + "'" +
            '}';
    }
}
