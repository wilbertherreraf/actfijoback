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
//@Entity
//@Table(name = "cnf_rol_opcion")

public class CnfRolOpcion {
    @Id
    
    
    @Column(name = "id_rol_opcion")
    private Integer idRolOpcion;
    @Column(name = "tx_id")
    private Integer txId;
    @Column(name = "tx_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date txFecha;
    
    @Column(name = "usuario")
    private String usuario;
    
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    @ManyToOne(fetch = FetchType.LAZY)
    private CnfRoles idRol;
    @JoinColumn(name = "id_opcion", referencedColumnName = "id_opcion")
    @ManyToOne(fetch = FetchType.LAZY)
    private CnfOpciones idOpcion;

    public CnfRolOpcion() {
    }

    public CnfRolOpcion(Integer idRolOpcion) {
        this.idRolOpcion = idRolOpcion;
    }

    public Integer getIdRolOpcion() {
        return idRolOpcion;
    }

    public void setIdRolOpcion(Integer idRolOpcion) {
        this.idRolOpcion = idRolOpcion;
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

    public CnfRoles getIdRol() {
        return idRol;
    }

    public void setIdRol(CnfRoles idRol) {
        this.idRol = idRol;
    }

    public CnfOpciones getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(CnfOpciones idOpcion) {
        this.idOpcion = idOpcion;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idRolOpcion != null ? idRolOpcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CnfRolOpcion)) {
            return false;
        }
        CnfRolOpcion other = (CnfRolOpcion) object;
        if ((this.idRolOpcion == null && other.idRolOpcion != null) || (this.idRolOpcion != null && !this.idRolOpcion.equals(other.idRolOpcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.CnfRolOpcion[ idRolOpcion=" + idRolOpcion + " ]";
    }
    
}
