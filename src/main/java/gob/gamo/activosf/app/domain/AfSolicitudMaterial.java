/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author wherrera
 */
@Entity
@Table(name = "acf_solicitud_material")
public class AfSolicitudMaterial implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud_material")
    private Integer idSolicitudMaterial;

    @Column(name = "cantidad_solicitada")
    private Integer cantidadSolicitada;

    @Column(name = "cantidad_aprobada")
    private Integer cantidadAprobada;

    @Column(name = "cantidad_entregada")
    private Integer cantidadEntregada;

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

    @JoinColumn(name = "id_solicitud", referencedColumnName = "id_solicitud")
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AfSolicitud idSolicitud;

    @JoinColumn(name = "id_material", referencedColumnName = "id_material")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private AfMaterial idMaterial;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSolicitudMaterial", fetch = FetchType.LAZY)
    private List<AfRegistroKardexMaterial> afRegistroKardexMaterialList;

    public AfSolicitudMaterial() {}

    public AfSolicitudMaterial(Integer idSolicitudMaterial) {
        this.idSolicitudMaterial = idSolicitudMaterial;
    }

    public AfSolicitudMaterial(
            Integer idSolicitudMaterial,
            Integer cantidadSolicitada,
            String estado,
            Integer idTransaccion,
            Date txFchIni,
            Integer txUsrIni,
            String txHostIni) {
        this.idSolicitudMaterial = idSolicitudMaterial;
        this.cantidadSolicitada = cantidadSolicitada;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdSolicitudMaterial() {
        return idSolicitudMaterial;
    }

    public void setIdSolicitudMaterial(Integer idSolicitudMaterial) {
        this.idSolicitudMaterial = idSolicitudMaterial;
    }

    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(int cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public Integer getCantidadAprobada() {
        return cantidadAprobada;
    }

    public void setCantidadAprobada(Integer cantidadAprobada) {
        this.cantidadAprobada = cantidadAprobada;
    }

    public Integer getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(Integer cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
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

    public AfSolicitud getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(AfSolicitud idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public AfMaterial getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(AfMaterial idMaterial) {
        this.idMaterial = idMaterial;
    }

    public List<AfRegistroKardexMaterial> getAfRegistroKardexMaterialList() {
        return afRegistroKardexMaterialList;
    }

    public void setAfRegistroKardexMaterialList(List<AfRegistroKardexMaterial> afRegistroKardexMaterialList) {
        this.afRegistroKardexMaterialList = afRegistroKardexMaterialList;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idSolicitudMaterial != null ? idSolicitudMaterial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfSolicitudMaterial)) {
            return false;
        }
        AfSolicitudMaterial other = (AfSolicitudMaterial) object;
        if ((this.idSolicitudMaterial == null && other.idSolicitudMaterial != null)
                || (this.idSolicitudMaterial != null && !this.idSolicitudMaterial.equals(other.idSolicitudMaterial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfSolicitudMaterial[ idSolicitudMaterial=" + idSolicitudMaterial + " ]";
    }
}
