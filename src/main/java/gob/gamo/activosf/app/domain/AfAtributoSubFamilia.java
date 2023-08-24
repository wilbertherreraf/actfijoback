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
@Table(name = "acf_atributo_sub_familia")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AfAtributoSubFamilia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atributo_sub_familia")
    private Integer idAtributoSubFamilia;

    @Column(name = "cat_tipo_atributo")
    private String catTipoAtributo;

    @Column(name = "tab_tipo_atributo")
    private Integer tabTipoAtributo;

    @Column(name = "tipo_atributo")
    private Integer tipoAtributo;

    @Column(name = "prioridad")
    private Integer prioridad;

    @Column(name = "imprimible")
    private boolean imprimible;

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

    @JoinColumn(name = "id_sub_familia", referencedColumnName = "id_sub_familia")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AfSubFamiliaActivo idSubFamilia;

    public AfAtributoSubFamilia(
            Integer idAtributoSubFamilia,
            String catTipoAtributo,
            Integer prioridad,
            boolean imprimible,
            String estado,
            Integer idTransaccion,
            Date txFchIni,
            Integer txUsrIni,
            String txHostIni) {
        this.idAtributoSubFamilia = idAtributoSubFamilia;
        this.catTipoAtributo = catTipoAtributo;
        this.prioridad = prioridad;
        this.imprimible = imprimible;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdAtributoSubFamilia() {
        return idAtributoSubFamilia;
    }

    public void setIdAtributoSubFamilia(Integer idAtributoSubFamilia) {
        this.idAtributoSubFamilia = idAtributoSubFamilia;
    }

    public String getCatTipoAtributo() {
        return catTipoAtributo;
    }

    public void setCatTipoAtributo(String catTipoAtributo) {
        this.catTipoAtributo = catTipoAtributo;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public boolean getImprimible() {
        return imprimible;
    }

    public void setImprimible(boolean imprimible) {
        this.imprimible = imprimible;
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

    public AfSubFamiliaActivo getIdSubFamilia() {
        return idSubFamilia;
    }

    public void setIdSubFamilia(AfSubFamiliaActivo idSubFamilia) {
        this.idSubFamilia = idSubFamilia;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idAtributoSubFamilia != null ? idAtributoSubFamilia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfAtributoSubFamilia)) {
            return false;
        }
        AfAtributoSubFamilia other = (AfAtributoSubFamilia) object;
        if ((this.idAtributoSubFamilia == null && other.idAtributoSubFamilia != null)
                || (this.idAtributoSubFamilia != null
                        && !this.idAtributoSubFamilia.equals(other.idAtributoSubFamilia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfAtributoSubFamilia[ idAtributoSubFamilia=" + idAtributoSubFamilia + " ]";
    }
}
