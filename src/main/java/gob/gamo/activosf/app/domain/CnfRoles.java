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
//@Table(name = "cnf_roles")

public class CnfRoles {
    @Id
    
    
    @Column(name = "id_rol")
    private Integer idRol;
    
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "tx_id")
    private Integer txId;
    @Column(name = "tx_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date txFecha;
    
    @Column(name = "usuario")
    private String usuario;
    
    @Column(name = "estado")
    private String estado;
    @OneToMany(mappedBy = "idRol", fetch = FetchType.LAZY)
    private List<CnfRolOpcion> cnfRolOpcionList;

    public CnfRoles() {
    }

    public CnfRoles(Integer idRol) {
        this.idRol = idRol;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    
    public List<CnfRolOpcion> getCnfRolOpcionList() {
        return cnfRolOpcionList;
    }

    public void setCnfRolOpcionList(List<CnfRolOpcion> cnfRolOpcionList) {
        this.cnfRolOpcionList = cnfRolOpcionList;
    }

    
    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idRol != null ? idRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CnfRoles)) {
            return false;
        }
        CnfRoles other = (CnfRoles) object;
        if ((this.idRol == null && other.idRol != null) || (this.idRol != null && !this.idRol.equals(other.idRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.CnfRoles[ idRol=" + idRol + " ]";
    }
    
}
