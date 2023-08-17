/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import jakarta.persistence.OneToMany;
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
@Table(name = "acf_factura")

public class AfFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_factura")
    private Integer idFactura;
    
    
    
    @Column(name = "nro_factura")
    private String nroFactura;
    
    
    @Column(name = "fecha_factura")
    @Temporal(TemporalType.DATE)
    private Date fechaFactura;
    
    @Column(name = "nro_autorizacion")
    private String nroAutorizacion;
    
    @Column(name = "codigo_control")
    private String codigoControl;
    
    
    
    @Column(name = "razon_social")
    private String razonSocial;
    
    
    
    @Column(name = "nit")
    private String nit;
    
    
    
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFactura", fetch = FetchType.LAZY)
    private List<AfAccesorioActivoFijo> afAccesorioActivoFijoList;
    @OneToMany(mappedBy = "idFactura", fetch = FetchType.LAZY)
    private List<AfAltaMaterial> afAltaMaterialList;
    @OneToMany(mappedBy = "idFactura", fetch = FetchType.LAZY)
    private List<AfActivoFijo> afActivoFijoList;

    public AfFactura() {
    }

    public AfFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public AfFactura(Integer idFactura, String nroFactura, Date fechaFactura, String razonSocial, String nit, String estado, Integer idTransaccion, Date txFchIni, Integer txUsrIni, String txHostIni) {
        this.idFactura = idFactura;
        this.nroFactura = nroFactura;
        this.fechaFactura = fechaFactura;
        this.razonSocial = razonSocial;
        this.nit = nit;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public String getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(String nroFactura) {
        this.nroFactura = nroFactura;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getNroAutorizacion() {
        return nroAutorizacion;
    }

    public void setNroAutorizacion(String nroAutorizacion) {
        this.nroAutorizacion = nroAutorizacion;
    }

    public String getCodigoControl() {
        return codigoControl;
    }

    public void setCodigoControl(String codigoControl) {
        this.codigoControl = codigoControl;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
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

    
    public List<AfAccesorioActivoFijo> getAfAccesorioActivoFijoList() {
        return afAccesorioActivoFijoList;
    }

    public void setAfAccesorioActivoFijoList(List<AfAccesorioActivoFijo> afAccesorioActivoFijoList) {
        this.afAccesorioActivoFijoList = afAccesorioActivoFijoList;
    }

    
    public List<AfAltaMaterial> getAfAltaMaterialList() {
        return afAltaMaterialList;
    }

    public void setAfAltaMaterialList(List<AfAltaMaterial> afAltaMaterialList) {
        this.afAltaMaterialList = afAltaMaterialList;
    }

    
    public List<AfActivoFijo> getAfActivoFijoList() {
        return afActivoFijoList;
    }

    public void setAfActivoFijoList(List<AfActivoFijo> afActivoFijoList) {
        this.afActivoFijoList = afActivoFijoList;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idFactura != null ? idFactura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfFactura)) {
            return false;
        }
        AfFactura other = (AfFactura) object;
        if ((this.idFactura == null && other.idFactura != null) || (this.idFactura != null && !this.idFactura.equals(other.idFactura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfFactura[ idFactura=" + idFactura + " ]";
    }
    
}
