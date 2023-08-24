/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "acf_garantia_activo_fijo")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AfGarantiaActivoFijo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_garantia_activo_fijo")
    private Integer idGarantiaActivoFijo;

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFin;

    @Column(name = "cat_tipo_garantia")
    private String catTipoGarantia;

    @Column(name = "tab_tipo_garantia")
    private Integer tabTipoGarantia;

    @Column(name = "tipo_garantia")
    private Integer tipoGarantia;

    @Column(name = "codigo_contrato")
    private String codigoContrato;

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

    @OneToMany(mappedBy = "idGarantiaActivoFijo", fetch = FetchType.LAZY)
    private List<AfActivoFijo> afActivoFijoList;

    public AfGarantiaActivoFijo(Integer idGarantiaActivoFijo) {
        this.idGarantiaActivoFijo = idGarantiaActivoFijo;
    }

    public AfGarantiaActivoFijo(
            Integer idGarantiaActivoFijo,
            String estado,
            Integer idTransaccion,
            Date txFchIni,
            Integer txUsrIni,
            String txHostIni) {
        this.idGarantiaActivoFijo = idGarantiaActivoFijo;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdGarantiaActivoFijo() {
        return idGarantiaActivoFijo;
    }

    public void setIdGarantiaActivoFijo(Integer idGarantiaActivoFijo) {
        this.idGarantiaActivoFijo = idGarantiaActivoFijo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getCatTipoGarantia() {
        return catTipoGarantia;
    }

    public void setCatTipoGarantia(String catTipoGarantia) {
        this.catTipoGarantia = catTipoGarantia;
    }

    public String getCodigoContrato() {
        return codigoContrato;
    }

    public void setCodigoContrato(String codigoContrato) {
        this.codigoContrato = codigoContrato;
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

    public List<AfActivoFijo> getAfActivoFijoList() {
        return afActivoFijoList;
    }

    public void setAfActivoFijoList(List<AfActivoFijo> afActivoFijoList) {
        this.afActivoFijoList = afActivoFijoList;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idGarantiaActivoFijo != null ? idGarantiaActivoFijo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfGarantiaActivoFijo)) {
            return false;
        }
        AfGarantiaActivoFijo other = (AfGarantiaActivoFijo) object;
        if ((this.idGarantiaActivoFijo == null && other.idGarantiaActivoFijo != null)
                || (this.idGarantiaActivoFijo != null
                        && !this.idGarantiaActivoFijo.equals(other.idGarantiaActivoFijo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfGarantiaActivoFijo[ idGarantiaActivoFijo=" + idGarantiaActivoFijo + " ]";
    }
}
