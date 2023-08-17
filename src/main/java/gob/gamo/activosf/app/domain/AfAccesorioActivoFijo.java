/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@Table(name = "acf_accesorio_activo_fijo")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AfAccesorioActivoFijo implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_accesorio_activo_fijo")
    private Integer idAccesorioActivoFijo;
    
    
    
    @Column(name = "cat_tipo_accesorio")
    private String catTipoAccesorio;
	@Column(name = "tab_tipo_accesorio")
	private Integer tabTipoAccesorio;
	@Column(name = "tipo_accesorio")
	private Integer tipoAccesorio;    
    
    @Column(name = "fecha_adquisicion")
    @Temporal(TemporalType.DATE)
    private Date fechaAdquisicion;
    
    
    
    @Column(name = "detalle")
    private String detalle;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Min(value = 1)
    
    
    @Column(name = "importe_total")
    private BigDecimal importeTotal;
    @Min(value = 1)
    
    
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Column(name = "observacion")
    private String observacion;
    
    
    
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
    @JoinColumn(name = "id_factura", referencedColumnName = "id_factura")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional=false)
    private AfFactura idFactura;
    @JoinColumn(name = "id_activo_fijo", referencedColumnName = "id_activo_fijo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AfActivoFijo idActivoFijo;
    @OneToMany(mappedBy = "idAccesorioActivoFijo", fetch = FetchType.LAZY)
    private List<AfImagenActivoFijo> afImagenActivoFijoList;

    public AfAccesorioActivoFijo(Integer idAccesorioActivoFijo, String catTipoAccesorio, Date fechaAdquisicion, String detalle, BigDecimal importeTotal, Integer cantidad, String estado, Integer idTransaccion, Date txFchIni, Integer txUsrIni, String txHostIni) {
        this.idAccesorioActivoFijo = idAccesorioActivoFijo;
        this.catTipoAccesorio = catTipoAccesorio;
        this.fechaAdquisicion = fechaAdquisicion;
        this.detalle = detalle;
        this.importeTotal = importeTotal;
        this.cantidad = cantidad;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdAccesorioActivoFijo() {
        return idAccesorioActivoFijo;
    }

    public void setIdAccesorioActivoFijo(Integer idAccesorioActivoFijo) {
        this.idAccesorioActivoFijo = idAccesorioActivoFijo;
    }

    public String getCatTipoAccesorio() {
        return catTipoAccesorio;
    }

    public void setCatTipoAccesorio(String catTipoAccesorio) {
        this.catTipoAccesorio = catTipoAccesorio;
    }

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
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

    public AfFactura getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(AfFactura idFactura) {
        this.idFactura = idFactura;
    }

    public AfActivoFijo getIdActivoFijo() {
        return idActivoFijo;
    }

    public void setIdActivoFijo(AfActivoFijo idActivoFijo) {
        this.idActivoFijo = idActivoFijo;
    }

    
    public List<AfImagenActivoFijo> getAfImagenActivoFijoList() {
        return afImagenActivoFijoList;
    }

    public void setAfImagenActivoFijoList(List<AfImagenActivoFijo> afImagenActivoFijoList) {
        this.afImagenActivoFijoList = afImagenActivoFijoList;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idAccesorioActivoFijo != null ? idAccesorioActivoFijo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfAccesorioActivoFijo)) {
            return false;
        }
        AfAccesorioActivoFijo other = (AfAccesorioActivoFijo) object;
        if ((this.idAccesorioActivoFijo == null && other.idAccesorioActivoFijo != null) || (this.idAccesorioActivoFijo != null && !this.idAccesorioActivoFijo.equals(other.idAccesorioActivoFijo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfAccesorioActivoFijo[ idAccesorioActivoFijo=" + idAccesorioActivoFijo + " ]";
    }
    
}
