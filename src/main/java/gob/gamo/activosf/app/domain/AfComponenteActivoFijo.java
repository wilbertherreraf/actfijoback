/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author wherrera
 */
@Entity
@Table(name = "acf_componente_activo_fijo")
public class AfComponenteActivoFijo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_componente_activo_fijo")
    private Integer idComponenteActivoFijo;

    @Column(name = "correlativo")
    private Integer correlativo;

    @Column(name = "cat_componente_activo_fijo")
    private String catComponenteActivoFijo;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "codigo_rfid")
    private String codigoRfid;

    @Column(name = "codigo_ean")
    private String codigoEan;

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

    public AfComponenteActivoFijo() {}

    public AfComponenteActivoFijo(Integer idComponenteActivoFijo) {
        this.idComponenteActivoFijo = idComponenteActivoFijo;
    }

    public AfComponenteActivoFijo(
            Integer idComponenteActivoFijo,
            String catComponenteActivoFijo,
            Integer cantidad,
            String codigoRfid,
            String codigoEan,
            String estado,
            Integer idTransaccion,
            Date txFchIni,
            Integer txUsrIni,
            String txHostIni) {
        this.idComponenteActivoFijo = idComponenteActivoFijo;
        this.catComponenteActivoFijo = catComponenteActivoFijo;
        this.cantidad = cantidad;
        this.codigoRfid = codigoRfid;
        this.codigoEan = codigoEan;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdComponenteActivoFijo() {
        return idComponenteActivoFijo;
    }

    public void setIdComponenteActivoFijo(Integer idComponenteActivoFijo) {
        this.idComponenteActivoFijo = idComponenteActivoFijo;
    }

    public Integer getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(Integer correlativo) {
        this.correlativo = correlativo;
    }

    public String getCatComponenteActivoFijo() {
        return catComponenteActivoFijo;
    }

    public void setCatComponenteActivoFijo(String catComponenteActivoFijo) {
        this.catComponenteActivoFijo = catComponenteActivoFijo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getCodigoRfid() {
        return codigoRfid;
    }

    public void setCodigoRfid(String codigoRfid) {
        this.codigoRfid = codigoRfid;
    }

    public String getCodigoEan() {
        return codigoEan;
    }

    public void setCodigoEan(String codigoEan) {
        this.codigoEan = codigoEan;
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
        hash += (idComponenteActivoFijo != null ? idComponenteActivoFijo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfComponenteActivoFijo)) {
            return false;
        }
        AfComponenteActivoFijo other = (AfComponenteActivoFijo) object;
        if ((this.idComponenteActivoFijo == null && other.idComponenteActivoFijo != null)
                || (this.idComponenteActivoFijo != null
                        && !this.idComponenteActivoFijo.equals(other.idComponenteActivoFijo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfComponenteActivoFijo[ idComponenteActivoFijo=" + idComponenteActivoFijo
                + " ]";
    }
}
