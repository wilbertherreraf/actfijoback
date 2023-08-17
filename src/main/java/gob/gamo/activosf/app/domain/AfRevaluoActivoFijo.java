/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "acf_revaluo_activo_fijo")

public class AfRevaluoActivoFijo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_revaluo_activo_fijo")
    private Integer idRevaluoActivoFijo;
    
    
    @Column(name = "fecha_revaluo")
    @Temporal(TemporalType.DATE)
    private Date fechaRevaluo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    
    
    @Column(name = "nuevo_factor_depreciacion")
    private BigDecimal nuevoFactorDepreciacion;
    
    
    
    @Column(name = "dispocision_respaldo")
    private String dispocisionRespaldo;
    
    @Column(name = "motivo")
    private String motivo;
    @Column(name = "costo_historico")
    private BigDecimal costoHistorico;
    @Column(name = "costo_nuevo")
    private BigDecimal costoNuevo;
    
    
    @Column(name = "dep_al_revaluo")
    private BigDecimal depAlRevaluo;
    
    
    @Column(name = "dep_acum_al_revaluo")
    private BigDecimal depAcumAlRevaluo;
    
    
    @Column(name = "valor_neto_al_revaluo")
    private BigDecimal valorNetoAlRevaluo;
    
    
    
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

    public AfRevaluoActivoFijo() {
    }

    public AfRevaluoActivoFijo(Integer idRevaluoActivoFijo) {
        this.idRevaluoActivoFijo = idRevaluoActivoFijo;
    }

    public AfRevaluoActivoFijo(Integer idRevaluoActivoFijo, Date fechaRevaluo, BigDecimal nuevoFactorDepreciacion, String dispocisionRespaldo, BigDecimal depAlRevaluo, BigDecimal depAcumAlRevaluo, BigDecimal valorNetoAlRevaluo, String estado, Integer idTransaccion, Date txFchIni, Integer txUsrIni, String txHostIni) {
        this.idRevaluoActivoFijo = idRevaluoActivoFijo;
        this.fechaRevaluo = fechaRevaluo;
        this.nuevoFactorDepreciacion = nuevoFactorDepreciacion;
        this.dispocisionRespaldo = dispocisionRespaldo;
        this.depAlRevaluo = depAlRevaluo;
        this.depAcumAlRevaluo = depAcumAlRevaluo;
        this.valorNetoAlRevaluo = valorNetoAlRevaluo;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdRevaluoActivoFijo() {
        return idRevaluoActivoFijo;
    }

    public void setIdRevaluoActivoFijo(Integer idRevaluoActivoFijo) {
        this.idRevaluoActivoFijo = idRevaluoActivoFijo;
    }

    public Date getFechaRevaluo() {
        return fechaRevaluo;
    }

    public void setFechaRevaluo(Date fechaRevaluo) {
        this.fechaRevaluo = fechaRevaluo;
    }

    public BigDecimal getNuevoFactorDepreciacion() {
        return nuevoFactorDepreciacion;
    }

    public void setNuevoFactorDepreciacion(BigDecimal nuevoFactorDepreciacion) {
        this.nuevoFactorDepreciacion = nuevoFactorDepreciacion;
    }

    public String getDispocisionRespaldo() {
        return dispocisionRespaldo;
    }

    public void setDispocisionRespaldo(String dispocisionRespaldo) {
        this.dispocisionRespaldo = dispocisionRespaldo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public BigDecimal getCostoHistorico() {
        return costoHistorico;
    }

    public void setCostoHistorico(BigDecimal costoHistorico) {
        this.costoHistorico = costoHistorico;
    }

    public BigDecimal getCostoNuevo() {
        return costoNuevo;
    }

    public void setCostoNuevo(BigDecimal costoNuevo) {
        this.costoNuevo = costoNuevo;
    }

    public BigDecimal getDepAlRevaluo() {
        return depAlRevaluo;
    }

    public void setDepAlRevaluo(BigDecimal depAlRevaluo) {
        this.depAlRevaluo = depAlRevaluo;
    }

    public BigDecimal getDepAcumAlRevaluo() {
        return depAcumAlRevaluo;
    }

    public void setDepAcumAlRevaluo(BigDecimal depAcumAlRevaluo) {
        this.depAcumAlRevaluo = depAcumAlRevaluo;
    }

    public BigDecimal getValorNetoAlRevaluo() {
        return valorNetoAlRevaluo;
    }

    public void setValorNetoAlRevaluo(BigDecimal valorNetoAlRevaluo) {
        this.valorNetoAlRevaluo = valorNetoAlRevaluo;
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
        hash += (idRevaluoActivoFijo != null ? idRevaluoActivoFijo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfRevaluoActivoFijo)) {
            return false;
        }
        AfRevaluoActivoFijo other = (AfRevaluoActivoFijo) object;
        if ((this.idRevaluoActivoFijo == null && other.idRevaluoActivoFijo != null) || (this.idRevaluoActivoFijo != null && !this.idRevaluoActivoFijo.equals(other.idRevaluoActivoFijo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfRevaluoActivoFijo[ idRevaluoActivoFijo=" + idRevaluoActivoFijo + " ]";
    }
    
}
