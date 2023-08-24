/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.io.Serializable;
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
@Table(name = "acf_baja_activo_fijo")
public class AfBajaActivoFijo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_baja_activo_fijo")
    private Integer idBajaActivoFijo;

    @Column(name = "correlativo")
    private Integer correlativo;

    @Column(name = "gestion")
    private Integer gestion;

    @Column(name = "fecha_baja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;

    @Column(name = "cat_motivo_baja_activo_fijo")
    private String catMotivoBajaActivoFijo;

    @Column(name = "documento_respaldo")
    private String documentoRespaldo;

    @Column(name = "observaciones")
    private String observaciones;

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

    public AfBajaActivoFijo() {}

    public AfBajaActivoFijo(Integer idBajaActivoFijo) {
        this.idBajaActivoFijo = idBajaActivoFijo;
    }

    public AfBajaActivoFijo(
            Integer idBajaActivoFijo,
            Integer correlativo,
            Integer gestion,
            Date fechaBaja,
            String catMotivoBajaActivoFijo,
            String documentoRespaldo,
            String estado,
            Integer idTransaccion,
            Date txFchIni,
            Integer txUsrIni,
            String txHostIni) {
        this.idBajaActivoFijo = idBajaActivoFijo;
        this.correlativo = correlativo;
        this.gestion = gestion;
        this.fechaBaja = fechaBaja;
        this.catMotivoBajaActivoFijo = catMotivoBajaActivoFijo;
        this.documentoRespaldo = documentoRespaldo;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdBajaActivoFijo() {
        return idBajaActivoFijo;
    }

    public void setIdBajaActivoFijo(Integer idBajaActivoFijo) {
        this.idBajaActivoFijo = idBajaActivoFijo;
    }

    public Integer getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(int correlativo) {
        this.correlativo = correlativo;
    }

    public Integer getGestion() {
        return gestion;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getCatMotivoBajaActivoFijo() {
        return catMotivoBajaActivoFijo;
    }

    public void setCatMotivoBajaActivoFijo(String catMotivoBajaActivoFijo) {
        this.catMotivoBajaActivoFijo = catMotivoBajaActivoFijo;
    }

    public String getDocumentoRespaldo() {
        return documentoRespaldo;
    }

    public void setDocumentoRespaldo(String documentoRespaldo) {
        this.documentoRespaldo = documentoRespaldo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
        hash += (idBajaActivoFijo != null ? idBajaActivoFijo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfBajaActivoFijo)) {
            return false;
        }
        AfBajaActivoFijo other = (AfBajaActivoFijo) object;
        if ((this.idBajaActivoFijo == null && other.idBajaActivoFijo != null)
                || (this.idBajaActivoFijo != null && !this.idBajaActivoFijo.equals(other.idBajaActivoFijo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfBajaActivoFijo[ idBajaActivoFijo=" + idBajaActivoFijo + " ]";
    }
}
