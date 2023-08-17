/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Basic;
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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


/**
 *
 * @author wherrera
 */
@Entity
@Table(name = "acf_material_proveedor")

public class AfMaterialProveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_material_proveedor")
    private Integer idMaterialProveedor;
    
    
    
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
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor")
    @ManyToOne(fetch = FetchType.LAZY)
    private AfProveedor idProveedor;
    @JoinColumn(name = "id_material", referencedColumnName = "id_material")
    @ManyToOne(fetch = FetchType.LAZY)
    private AfMaterial idMaterial;

    public AfMaterialProveedor() {
    }

    public AfMaterialProveedor(Integer idMaterialProveedor) {
        this.idMaterialProveedor = idMaterialProveedor;
    }

    public AfMaterialProveedor(Integer idMaterialProveedor, String estado, Integer idTransaccion, Date txFchIni, Integer txUsrIni, String txHostIni) {
        this.idMaterialProveedor = idMaterialProveedor;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdMaterialProveedor() {
        return idMaterialProveedor;
    }

    public void setIdMaterialProveedor(Integer idMaterialProveedor) {
        this.idMaterialProveedor = idMaterialProveedor;
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

    public AfProveedor getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(AfProveedor idProveedor) {
        this.idProveedor = idProveedor;
    }

    public AfMaterial getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(AfMaterial idMaterial) {
        this.idMaterial = idMaterial;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idMaterialProveedor != null ? idMaterialProveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfMaterialProveedor)) {
            return false;
        }
        AfMaterialProveedor other = (AfMaterialProveedor) object;
        if ((this.idMaterialProveedor == null && other.idMaterialProveedor != null) || (this.idMaterialProveedor != null && !this.idMaterialProveedor.equals(other.idMaterialProveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfMaterialProveedor[ idMaterialProveedor=" + idMaterialProveedor + " ]";
    }
    
}
