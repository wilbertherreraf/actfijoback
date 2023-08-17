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
@Entity
@Table(name = "acf_familia_activo")

public class AfFamiliaActivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_familia_activo")
    private Integer idFamiliaActivo;
    
    
    @Column(name = "gestion")
    private Integer gestion;
    
    
    
    @Column(name = "codigo")
    private String codigo;
    
    
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "id_origen")
    private Integer idOrigen;
    
    
    
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFamiliaActivo", fetch = FetchType.LAZY)
    private List<AfSubFamiliaActivo> afSubFamiliaActivoList;
    @JoinColumn(name = "id_partida_presupuestaria", referencedColumnName = "id_partida_presupuestaria")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private AfPartidaPresupuestaria idPartidaPresupuestaria;
    @JoinColumn(name = "id_codigo_contable", referencedColumnName = "id_codigo_contable")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private AfCodigoContable idCodigoContable;

    public AfFamiliaActivo() {
    }

    public AfFamiliaActivo(Integer idFamiliaActivo) {
        this.idFamiliaActivo = idFamiliaActivo;
    }

    public AfFamiliaActivo(Integer idFamiliaActivo, Integer gestion, String codigo, String descripcion, String estado, Integer idTransaccion, Date txFchIni, Integer txUsrIni, String txHostIni) {
        this.idFamiliaActivo = idFamiliaActivo;
        this.gestion = gestion;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }


    public Integer getIdFamiliaActivo() {
        return idFamiliaActivo;
    }

    public void setIdFamiliaActivo(Integer idFamiliaActivo) {
        this.idFamiliaActivo = idFamiliaActivo;
    }

    public Integer getGestion() {
        return gestion;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdOrigen() {
		return idOrigen;
	}

	public void setIdOrigen(Integer idOrigen) {
		this.idOrigen = idOrigen;
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

    
    public List<AfSubFamiliaActivo> getAfSubFamiliaActivoList() {
        return afSubFamiliaActivoList;
    }

    public void setAfSubFamiliaActivoList(List<AfSubFamiliaActivo> afSubFamiliaActivoList) {
        this.afSubFamiliaActivoList = afSubFamiliaActivoList;
    }

    public AfPartidaPresupuestaria getIdPartidaPresupuestaria() {
        return idPartidaPresupuestaria;
    }

    public void setIdPartidaPresupuestaria(AfPartidaPresupuestaria idPartidaPresupuestaria) {
        this.idPartidaPresupuestaria = idPartidaPresupuestaria;
    }

    public AfCodigoContable getIdCodigoContable() {
        return idCodigoContable;
    }

    public void setIdCodigoContable(AfCodigoContable idCodigoContable) {
        this.idCodigoContable = idCodigoContable;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idFamiliaActivo != null ? idFamiliaActivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfFamiliaActivo)) {
            return false;
        }
        AfFamiliaActivo other = (AfFamiliaActivo) object;
        if ((this.idFamiliaActivo == null && other.idFamiliaActivo != null) || (this.idFamiliaActivo != null && !this.idFamiliaActivo.equals(other.idFamiliaActivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfFamiliaActivo[ idFamiliaActivo=" + idFamiliaActivo + " ]";
    }
    
}
