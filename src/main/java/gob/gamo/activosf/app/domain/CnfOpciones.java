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
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


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
//@Entity
//@Table(name = "cnf_opciones")


public class CnfOpciones {
    @Id
    
    
    @Column(name = "id_opcion")
    private Integer idOpcion;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "url")
    private String url;
    @Column(name = "tx_id")
    private Integer txId;
    @Column(name = "tx_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date txFecha;
    
    @Column(name = "usuario")
    private String usuario;
    
    @Column(name = "estado")
    private String estado;
    
    @Column(name = "tipo_opcion")
    private String tipoOpcion;
    
    @Column(name = "aplicacion")
    private String aplicacion;
    @Column(name = "orden")
    private Integer orden;
    @OneToMany(mappedBy = "idOpcion", fetch = FetchType.LAZY)
    private List<CnfRolOpcion> cnfRolOpcionList;
    @OneToMany(mappedBy = "idOpcionPadre", fetch = FetchType.LAZY)
    private List<CnfOpciones> cnfOpcionesList;
    @JoinColumn(name = "id_opcion_padre", referencedColumnName = "id_opcion")
    @ManyToOne(fetch = FetchType.LAZY)
    private CnfOpciones idOpcionPadre;

    public CnfOpciones() {
    }

    public CnfOpciones(Integer idOpcion) {
        this.idOpcion = idOpcion;
    }

    public Integer getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(Integer idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getTxId() {
        return txId;
    }

    public void setTxId(Integer txId) {
        this.txId = txId;
    }

    public Date getTxFecha() {
        return txFecha;
    }

    public void setTxFecha(Date txFecha) {
        this.txFecha = txFecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoOpcion() {
        return tipoOpcion;
    }

    public void setTipoOpcion(String tipoOpcion) {
        this.tipoOpcion = tipoOpcion;
    }

    public String getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    
    public List<CnfRolOpcion> getCnfRolOpcionList() {
        return cnfRolOpcionList;
    }

    public void setCnfRolOpcionList(List<CnfRolOpcion> cnfRolOpcionList) {
        this.cnfRolOpcionList = cnfRolOpcionList;
    }

    
    public List<CnfOpciones> getCnfOpcionesList() {
        return cnfOpcionesList;
    }

    public void setCnfOpcionesList(List<CnfOpciones> cnfOpcionesList) {
        this.cnfOpcionesList = cnfOpcionesList;
    }

    public CnfOpciones getIdOpcionPadre() {
        return idOpcionPadre;
    }

    public void setIdOpcionPadre(CnfOpciones idOpcionPadre) {
        this.idOpcionPadre = idOpcionPadre;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idOpcion != null ? idOpcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CnfOpciones)) {
            return false;
        }
        CnfOpciones other = (CnfOpciones) object;
        if ((this.idOpcion == null && other.idOpcion != null) || (this.idOpcion != null && !this.idOpcion.equals(other.idOpcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.CnfOpciones[ idOpcion=" + idOpcion + " ]";
    }
    
}
