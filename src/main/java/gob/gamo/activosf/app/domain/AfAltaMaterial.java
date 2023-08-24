/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.util.Date;
import java.util.List;

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
@Table(name = "acf_alta_material")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AfAltaMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alta_material")
    private Integer idAltaMaterial;

    @Column(name = "correlativo")
    private Integer correlativo;

    @Column(name = "gestion")
    private Integer gestion;

    @Column(name = "cat_tipo_alta_material")
    private String catTipoAltaMaterial;

    @Column(name = "tab_tipo_alta_material")
    private Integer tabTipoAltaMaterial;

    @Column(name = "tipo_alta_material")
    private Integer tipoAltaMaterial;

    @Column(name = "fecha_alta")
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;

    @Column(name = "fecha_valorado")
    @Temporal(TemporalType.DATE)
    private Date fechaValorado;

    @Column(name = "observaciones")
    private String observaciones;

    @JoinColumn(name = "id_usuario_alta", referencedColumnName = "id_usuario")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private TxUsuario idUsuarioAlta;

    @Column(name = "cat_estado_alta_material")
    private String catEstadoAltaMaterial;

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
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AfFactura idFactura;

    @JoinColumn(name = "id_almacen", referencedColumnName = "id_almacen")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private AfAlmacen idAlmacen;

    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private AfProveedor idProveedor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAltaMaterial", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<AfAltaMaterialDetalle> afAltaMaterialDetalleList;

    public AfAltaMaterial(
            Integer idAltaMaterial,
            Integer correlativo,
            Integer gestion,
            String catTipoAltaMaterial,
            Date fechaAlta,
            String catEstadoAltaMaterial,
            String estado,
            Integer idTransaccion,
            Date txFchIni,
            Integer txUsrIni,
            String txHostIni) {
        this.idAltaMaterial = idAltaMaterial;
        this.correlativo = correlativo;
        this.gestion = gestion;
        this.catTipoAltaMaterial = catTipoAltaMaterial;
        this.fechaAlta = fechaAlta;
        this.catEstadoAltaMaterial = catEstadoAltaMaterial;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdAltaMaterial() {
        return idAltaMaterial;
    }

    public void setIdAltaMaterial(Integer idAltaMaterial) {
        this.idAltaMaterial = idAltaMaterial;
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

    public String getCatTipoAltaMaterial() {
        return catTipoAltaMaterial;
    }

    public void setCatTipoAltaMaterial(String catTipoAltaMaterial) {
        this.catTipoAltaMaterial = catTipoAltaMaterial;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaValorado() {
        return fechaValorado;
    }

    public void setFechaValorado(Date fechaValorado) {
        this.fechaValorado = fechaValorado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getCatEstadoAltaMaterial() {
        return catEstadoAltaMaterial;
    }

    public void setCatEstadoAltaMaterial(String catEstadoAltaMaterial) {
        this.catEstadoAltaMaterial = catEstadoAltaMaterial;
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

    public AfAlmacen getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(AfAlmacen idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public AfProveedor getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(AfProveedor idProveedor) {
        this.idProveedor = idProveedor;
    }

    public List<AfAltaMaterialDetalle> getAfAltaMaterialDetalleList() {
        return afAltaMaterialDetalleList;
    }

    public void setAfAltaMaterialDetalleList(List<AfAltaMaterialDetalle> afAltaMaterialDetalleList) {
        this.afAltaMaterialDetalleList = afAltaMaterialDetalleList;
    }

    public TxUsuario getIdUsuarioAlta() {
        return idUsuarioAlta;
    }

    public void setIdUsuarioAlta(TxUsuario idUsuarioAlta) {
        this.idUsuarioAlta = idUsuarioAlta;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idAltaMaterial != null ? idAltaMaterial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfAltaMaterial)) {
            return false;
        }
        AfAltaMaterial other = (AfAltaMaterial) object;
        if ((this.idAltaMaterial == null && other.idAltaMaterial != null)
                || (this.idAltaMaterial != null && !this.idAltaMaterial.equals(other.idAltaMaterial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfAltaMaterial[ idAltaMaterial=" + idAltaMaterial + " ]";
    }
}
