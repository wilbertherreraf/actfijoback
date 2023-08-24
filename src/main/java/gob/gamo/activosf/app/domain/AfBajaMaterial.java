/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.io.Serializable;
import java.util.Date;

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

/**
 *
 * @author wherrera
 */
@Entity
@Table(name = "acf_baja_material")
public class AfBajaMaterial implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_baja_material")
    private Integer idBajaMaterial;

    @Column(name = "correlativo")
    private Integer correlativo;

    @Column(name = "gestion")
    private Integer gestion;

    @Column(name = "cat_tipo_baja_material")
    private String catTipoBajaMaterial;

    @Column(name = "fecha_baja")
    @Temporal(TemporalType.DATE)
    private Date fechaBaja;

    @JoinColumn(name = "id_usuario_baja", referencedColumnName = "id_usuario")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private TxUsuario idUsuarioBaja;

    @Column(name = "detalle")
    private String detalle;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "cat_estado_baja_material")
    private String catEstadoBajaMaterial;

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

    @JoinColumn(name = "id_registro_kardex_material", referencedColumnName = "id_registro_kardex_material")
    @ManyToOne(fetch = FetchType.LAZY)
    private AfRegistroKardexMaterial idRegistroKardexMaterial;

    @JoinColumn(name = "id_kardex_material", referencedColumnName = "id_kardex_material")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private AfKardexMaterial idKardexMaterial;

    public AfBajaMaterial() {}

    public AfBajaMaterial(Integer idBajaMaterial) {
        this.idBajaMaterial = idBajaMaterial;
    }

    public AfBajaMaterial(
            Integer idBajaMaterial,
            Integer gestion,
            String catTipoBajaMaterial,
            String detalle,
            Integer cantidad,
            String catEstadoBajaMaterial,
            String estado,
            Integer idTransaccion,
            Date txFchIni,
            Integer txUsrIni,
            String txHostIni) {
        this.idBajaMaterial = idBajaMaterial;
        this.gestion = gestion;
        this.catTipoBajaMaterial = catTipoBajaMaterial;
        this.detalle = detalle;
        this.cantidad = cantidad;
        this.catEstadoBajaMaterial = catEstadoBajaMaterial;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdBajaMaterial() {
        return idBajaMaterial;
    }

    public void setIdBajaMaterial(Integer idBajaMaterial) {
        this.idBajaMaterial = idBajaMaterial;
    }

    public Integer getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(Integer correlativo) {
        this.correlativo = correlativo;
    }

    public Integer getGestion() {
        return gestion;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }

    public String getCatTipoBajaMaterial() {
        return catTipoBajaMaterial;
    }

    public void setCatTipoBajaMaterial(String catTipoBajaMaterial) {
        this.catTipoBajaMaterial = catTipoBajaMaterial;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCatEstadoBajaMaterial() {
        return catEstadoBajaMaterial;
    }

    public void setCatEstadoBajaMaterial(String catEstadoBajaMaterial) {
        this.catEstadoBajaMaterial = catEstadoBajaMaterial;
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

    public AfRegistroKardexMaterial getIdRegistroKardexMaterial() {
        return idRegistroKardexMaterial;
    }

    public void setIdRegistroKardexMaterial(AfRegistroKardexMaterial idRegistroKardexMaterial) {
        this.idRegistroKardexMaterial = idRegistroKardexMaterial;
    }

    public AfKardexMaterial getIdKardexMaterial() {
        return idKardexMaterial;
    }

    public void setIdKardexMaterial(AfKardexMaterial idKardexMaterial) {
        this.idKardexMaterial = idKardexMaterial;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public TxUsuario getIdUsuarioBaja() {
        return idUsuarioBaja;
    }

    public void setIdUsuarioBaja(TxUsuario idUsuarioBaja) {
        this.idUsuarioBaja = idUsuarioBaja;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idBajaMaterial != null ? idBajaMaterial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfBajaMaterial)) {
            return false;
        }
        AfBajaMaterial other = (AfBajaMaterial) object;
        if ((this.idBajaMaterial == null && other.idBajaMaterial != null)
                || (this.idBajaMaterial != null && !this.idBajaMaterial.equals(other.idBajaMaterial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfBajaMaterial[ idBajaMaterial=" + idBajaMaterial + " ]";
    }
}
