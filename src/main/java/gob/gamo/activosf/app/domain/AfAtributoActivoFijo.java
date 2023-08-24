/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author wherrera
 */
@Entity
@Getter
@Builder
@Table(name = "acf_atributo_activo_fijo")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AfAtributoActivoFijo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atributo_activo_fijo")
    private Integer idAtributoActivoFijo;

    @Column(name = "cat_tipo_atributo")
    private String catTipoAtributo;

    @Column(name = "tab_tipo_atributo")
    private Integer tabTipoAtributo;

    @Column(name = "tipo_atributo")
    private Integer tipoAtributo;

    @Column(name = "detalle")
    private String detalle;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "id_transaccion")
    private Integer idTransaccion;

    @Column(name = "tx_fch_ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date txFchIni;

    @Column(name = "tx_usr_ini")
    private Integer txUsrIni;

    @Column(name = "tx_host_ini")
    private String txHostIni;

    @Column(name = "tx_fch_mod")
    @Temporal(TemporalType.TIMESTAMP)
    private Date txFchMod;

    @Column(name = "tx_usr_mod")
    private Integer txUsrMod;

    @Column(name = "tx_host_mod")
    private String txHostMod;

    @JoinColumn(name = "id_activo_fijo", referencedColumnName = "id_activo_fijo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AfActivoFijo idActivoFijo;

    public AfAtributoActivoFijo(Integer idAtributoActivoFijo) {
        this.idAtributoActivoFijo = idAtributoActivoFijo;
    }

    public AfAtributoActivoFijo(
            Integer idAtributoActivoFijo,
            String catTipoAtributo,
            String detalle,
            String estado,
            Integer idTransaccion,
            Date txFchIni,
            Integer txUsrIni,
            String txHostIni) {
        this.idAtributoActivoFijo = idAtributoActivoFijo;
        this.catTipoAtributo = catTipoAtributo;
        this.detalle = detalle;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdAtributoActivoFijo() {
        return idAtributoActivoFijo;
    }

    public void setIdAtributoActivoFijo(Integer idAtributoActivoFijo) {
        this.idAtributoActivoFijo = idAtributoActivoFijo;
    }

    public String getCatTipoAtributo() {
        return catTipoAtributo;
    }

    public void setCatTipoAtributo(String catTipoAtributo) {
        this.catTipoAtributo = catTipoAtributo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Date getTxFchIni() {
        return txFchIni;
    }

    public void setTxFchIni(Date txFchIni) {
        this.txFchIni = txFchIni;
    }

    public Integer getTxUsrIni() {
        return txUsrIni;
    }

    public void setTxUsrIni(int txUsrIni) {
        this.txUsrIni = txUsrIni;
    }

    public String getTxHostIni() {
        return txHostIni;
    }

    public void setTxHostIni(String txHostIni) {
        this.txHostIni = txHostIni;
    }

    public Date getTxFchMod() {
        return txFchMod;
    }

    public void setTxFchMod(Date txFchMod) {
        this.txFchMod = txFchMod;
    }

    public Integer getTxUsrMod() {
        return txUsrMod;
    }

    public void setTxUsrMod(Integer txUsrMod) {
        this.txUsrMod = txUsrMod;
    }

    public String getTxHostMod() {
        return txHostMod;
    }

    public void setTxHostMod(String txHostMod) {
        this.txHostMod = txHostMod;
    }

    public AfActivoFijo getIdActivoFijo() {
        return idActivoFijo;
    }

    public void setIdActivoFijo(AfActivoFijo idActivoFijo) {
        this.idActivoFijo = idActivoFijo;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idAtributoActivoFijo != null ? idAtributoActivoFijo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfAtributoActivoFijo)) {
            return false;
        }
        AfAtributoActivoFijo other = (AfAtributoActivoFijo) object;
        if ((this.idAtributoActivoFijo == null && other.idAtributoActivoFijo != null)
                || (this.idAtributoActivoFijo != null
                        && !this.idAtributoActivoFijo.equals(other.idAtributoActivoFijo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfAtributoActivoFijo[ idAtributoActivoFijo=" + idAtributoActivoFijo + " ]";
    }
}
