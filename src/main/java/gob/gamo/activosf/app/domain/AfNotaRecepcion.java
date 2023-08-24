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
@Table(name = "acf_nota_recepcion")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AfNotaRecepcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota_recepcion")
    private Integer idNotaRecepcion;

    @Column(name = "correlativo")
    private Integer correlativo;

    @Column(name = "gestion")
    private Integer gestion;

    @Column(name = "cat_tipo_movimiento")
    private String catTipoMovimiento;

    @Column(name = "tab_tipo_movimiento")
    private Integer tabTipoMovimiento;

    @Column(name = "tipo_movimiento")
    private Integer tipoMovimiento;

    @Column(name = "cat_motivo_tipo_movimiento")
    private String catMotivoTipoMovimiento;

    @Column(name = "cat_tipo_documento_recepcion")
    private String catTipoDocumentoRecepcion;

    @Column(name = "tab_tipo_documento_recepcion")
    private Integer tabTipoDocumentoRecepcion;

    @Column(name = "tipo_documento_recepcion")
    private Integer tipoDocumentoRecepcion;

    @Column(name = "nro_documento_recepcion")
    private String nroDocumentoRecepcion;

    @Column(name = "fecha_recepcion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecepcion;

    @Column(name = "orden_compra")
    private String ordenCompra;

    @Column(name = "cat_estado_nota_recepcion")
    private String catEstadoNotaRecepcion;

    @Column(name = "tab_estado_nota_recepcion")
    private Integer tabEstadoNotaRecepcion;

    @Column(name = "estado_nota_recepcion")
    private Integer estadoNotaRecepcion;

    @JoinColumn(name = "id_usuario_recepcion", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TxUsuario idUsuarioRecepcion;

    @JoinColumn(name = "id_area_solicitante", referencedColumnName = "id_area")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TxArea idAreaSolicitante;

    @JoinColumn(name = "id_control_calidad", referencedColumnName = "id_usuario")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private TxUsuario idControlCalidad;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNotaRecepcion", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AfComisionRecepcion> afComisionRecepcionList;

    @OneToMany(mappedBy = "idNotaRecepcion", fetch = FetchType.EAGER)
    private List<AfTransferenciaAsignacion> afTransferenciaAsignacionList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNotaRecepcion", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AfActivoFijo> afActivoFijoList;

    public AfNotaRecepcion(
            Integer idNotaRecepcion,
            Integer gestion,
            String catTipoMovimiento,
            String catMotivoTipoMovimiento,
            String catTipoDocumentoRecepcion,
            String nroDocumentoRecepcion,
            Date fechaRecepcion,
            String catEstadoNotaRecepcion,
            TxUsuario idUsuarioRecepcion,
            TxArea idAreaSolicitante,
            String estado,
            Integer idTransaccion,
            Date txFchIni,
            Integer txUsrIni,
            String txHostIni) {
        this.idNotaRecepcion = idNotaRecepcion;
        this.gestion = gestion;
        this.catTipoMovimiento = catTipoMovimiento;
        this.catMotivoTipoMovimiento = catMotivoTipoMovimiento;
        this.catTipoDocumentoRecepcion = catTipoDocumentoRecepcion;
        this.nroDocumentoRecepcion = nroDocumentoRecepcion;
        this.fechaRecepcion = fechaRecepcion;
        this.catEstadoNotaRecepcion = catEstadoNotaRecepcion;
        this.idUsuarioRecepcion = idUsuarioRecepcion;
        this.idAreaSolicitante = idAreaSolicitante;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdNotaRecepcion() {
        return idNotaRecepcion;
    }

    public void setIdNotaRecepcion(Integer idNotaRecepcion) {
        this.idNotaRecepcion = idNotaRecepcion;
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

    public String getCatTipoMovimiento() {
        return catTipoMovimiento;
    }

    public void setCatTipoMovimiento(String catTipoMovimiento) {
        this.catTipoMovimiento = catTipoMovimiento;
    }

    public String getCatMotivoTipoMovimiento() {
        return catMotivoTipoMovimiento;
    }

    public void setCatMotivoTipoMovimiento(String catMotivoTipoMovimiento) {
        this.catMotivoTipoMovimiento = catMotivoTipoMovimiento;
    }

    public String getCatTipoDocumentoRecepcion() {
        return catTipoDocumentoRecepcion;
    }

    public void setCatTipoDocumentoRecepcion(String catTipoDocumentoRecepcion) {
        this.catTipoDocumentoRecepcion = catTipoDocumentoRecepcion;
    }

    public String getNroDocumentoRecepcion() {
        return nroDocumentoRecepcion;
    }

    public void setNroDocumentoRecepcion(String nroDocumentoRecepcion) {
        this.nroDocumentoRecepcion = nroDocumentoRecepcion;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(String ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public String getCatEstadoNotaRecepcion() {
        return catEstadoNotaRecepcion;
    }

    public void setCatEstadoNotaRecepcion(String catEstadoNotaRecepcion) {
        this.catEstadoNotaRecepcion = catEstadoNotaRecepcion;
    }

    public TxUsuario getIdUsuarioRecepcion() {
        return idUsuarioRecepcion;
    }

    public void setIdUsuarioRecepcion(TxUsuario idUsuarioRecepcion) {
        this.idUsuarioRecepcion = idUsuarioRecepcion;
    }

    public TxArea getIdAreaSolicitante() {
        return idAreaSolicitante;
    }

    public void setIdAreaSolicitante(TxArea idAreaSolicitante) {
        this.idAreaSolicitante = idAreaSolicitante;
    }

    public TxUsuario getIdControlCalidad() {
        return idControlCalidad;
    }

    public void setIdControlCalidad(TxUsuario idControlCalidad) {
        this.idControlCalidad = idControlCalidad;
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

    public List<AfComisionRecepcion> getAfComisionRecepcionList() {
        return afComisionRecepcionList;
    }

    public void setAfComisionRecepcionList(List<AfComisionRecepcion> afComisionRecepcionList) {
        this.afComisionRecepcionList = afComisionRecepcionList;
    }

    public List<AfTransferenciaAsignacion> getAfTransferenciaAsignacionList() {
        return afTransferenciaAsignacionList;
    }

    public void setAfTransferenciaAsignacionList(List<AfTransferenciaAsignacion> afTransferenciaAsignacionList) {
        this.afTransferenciaAsignacionList = afTransferenciaAsignacionList;
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
        hash += (idNotaRecepcion != null ? idNotaRecepcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfNotaRecepcion)) {
            return false;
        }
        AfNotaRecepcion other = (AfNotaRecepcion) object;
        if ((this.idNotaRecepcion == null && other.idNotaRecepcion != null)
                || (this.idNotaRecepcion != null && !this.idNotaRecepcion.equals(other.idNotaRecepcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfNotaRecepcion[ idNotaRecepcion=" + idNotaRecepcion + " ]";
    }
}
