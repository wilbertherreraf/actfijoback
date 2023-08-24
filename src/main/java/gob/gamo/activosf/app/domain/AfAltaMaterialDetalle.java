/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
import jakarta.persistence.Transient;

/**
 *
 * @author wherrera
 */
@Entity
@Table(name = "acf_alta_material_detalle")
public class AfAltaMaterialDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alta_material_detalle")
    private Integer idAltaMaterialDetalle;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to
    // enforce field validation

    @Column(name = "importe_unitario")
    private BigDecimal importeUnitario;

    @Column(name = "cantidad")
    private Integer cantidad;

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

    @JoinColumn(name = "id_material", referencedColumnName = "id_material")
    @ManyToOne(fetch = FetchType.EAGER)
    private AfMaterial idMaterial;

    @JoinColumn(name = "id_alta_material", referencedColumnName = "id_alta_material")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AfAltaMaterial idAltaMaterial;

    public AfAltaMaterialDetalle() {}

    public AfAltaMaterialDetalle(Integer idAltaMaterialDetalle) {
        this.idAltaMaterialDetalle = idAltaMaterialDetalle;
    }

    public AfAltaMaterialDetalle(
            Integer idAltaMaterialDetalle,
            BigDecimal importeUnitario,
            Integer cantidad,
            String estado,
            Integer idTransaccion,
            Date txFchIni,
            Integer txUsrIni,
            String txHostIni) {
        this.idAltaMaterialDetalle = idAltaMaterialDetalle;
        this.importeUnitario = importeUnitario;
        this.cantidad = cantidad;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    @Transient
    public BigDecimal getTotal() {
        BigDecimal result = null;
        if (importeUnitario != null) {
            result = importeUnitario.multiply(new BigDecimal(cantidad), new MathContext(5, RoundingMode.HALF_UP));
        }
        return result;
    }

    public Integer getIdAltaMaterialDetalle() {
        return idAltaMaterialDetalle;
    }

    public void setIdAltaMaterialDetalle(Integer idAltaMaterialDetalle) {
        this.idAltaMaterialDetalle = idAltaMaterialDetalle;
    }

    public BigDecimal getImporteUnitario() {
        return importeUnitario;
    }

    public void setImporteUnitario(BigDecimal importeUnitario) {
        this.importeUnitario = importeUnitario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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

    public AfMaterial getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(AfMaterial idMaterial) {
        this.idMaterial = idMaterial;
    }

    public AfAltaMaterial getIdAltaMaterial() {
        return idAltaMaterial;
    }

    public void setIdAltaMaterial(AfAltaMaterial idAltaMaterial) {
        this.idAltaMaterial = idAltaMaterial;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idAltaMaterialDetalle != null ? idAltaMaterialDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfAltaMaterialDetalle)) {
            return false;
        }
        AfAltaMaterialDetalle other = (AfAltaMaterialDetalle) object;
        if ((this.idAltaMaterialDetalle == null && other.idAltaMaterialDetalle != null)
                || (this.idAltaMaterialDetalle != null
                        && !this.idAltaMaterialDetalle.equals(other.idAltaMaterialDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfAltaMaterialDetalle[ idAltaMaterialDetalle=" + idAltaMaterialDetalle
                + " ]";
    }
}
