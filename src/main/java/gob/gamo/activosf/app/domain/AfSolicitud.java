/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.io.Serializable;
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
@Table(name = "acf_solicitud")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AfSolicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_solicitud")
    private Integer idSolicitud;
    @Column(name = "correlativo")
    private Integer correlativo;
    
    @Column(name = "gestion")
    private Integer gestion;
    
    @Column(name = "cat_tipo_solicitud")
    private String catTipoSolicitud;
	@Column(name = "tab_tipo_solicitud")
	private Integer tabTipoSolicitud;
	@Column(name = "tipo_solicitud")
	private Integer tipoSolicitud;       
    
    
    @Column(name = "cat_estado_solicitud")
    private String catEstadoSolicitud;
    @JoinColumn(name = "id_usuario_solicitud", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TxUsuario idUsuarioSolicitud;
    
    @Column(name = "detalle_solicitud")
    private String detalleSolicitud;
    
    @Column(name = "fecha_solicitud")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolicitud;
    @JoinColumn(name = "id_usuario_autorizacion", referencedColumnName = "id_usuario")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private TxUsuario idUsuarioAutorizacion;
    
    @Column(name = "detalle_autorizacion")
    private String detalleAutorizacion;
    @Column(name = "fecha_autorizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAutorizacion;
    @JoinColumn(name = "id_usuario_ejecucion", referencedColumnName = "id_usuario")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private TxUsuario idUsuarioEjecucion;
    
    @Column(name = "detalle_ejecucion")
    private String detalleEjecucion;
    @Column(name = "fecha_ejecucion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEjecucion;
    
    
    
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
    @OneToMany(mappedBy = "idSolicitud", fetch = FetchType.LAZY)
    private List<AfSolicitudActivoFijo> afSolicitudActivoFijoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSolicitud", fetch = FetchType.EAGER, orphanRemoval=true)
    private List<AfSolicitudMaterial> afSolicitudMaterialList;

    public AfSolicitud(Integer idSolicitud, Integer gestion, String catTipoSolicitud, String catEstadoSolicitud, TxUsuario idUsuarioSolicitud, String detalleSolicitud, Date fechaSolicitud, String estado, Integer idTransaccion, Date txFchIni, Integer txUsrIni, String txHostIni) {
        this.idSolicitud = idSolicitud;
        this.gestion = gestion;
        this.catTipoSolicitud = catTipoSolicitud;
        this.catEstadoSolicitud = catEstadoSolicitud;
        this.idUsuarioSolicitud = idUsuarioSolicitud;
        this.detalleSolicitud = detalleSolicitud;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
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

    public String getCatTipoSolicitud() {
        return catTipoSolicitud;
    }

    public void setCatTipoSolicitud(String catTipoSolicitud) {
        this.catTipoSolicitud = catTipoSolicitud;
    }

    public String getCatEstadoSolicitud() {
        return catEstadoSolicitud;
    }

    public void setCatEstadoSolicitud(String catEstadoSolicitud) {
        this.catEstadoSolicitud = catEstadoSolicitud;
    }

    public TxUsuario getIdUsuarioSolicitud() {
        return idUsuarioSolicitud;
    }

    public void setIdUsuarioSolicitud(TxUsuario idUsuarioSolicitud) {
        this.idUsuarioSolicitud = idUsuarioSolicitud;
    }

    public String getDetalleSolicitud() {
        return detalleSolicitud;
    }

    public void setDetalleSolicitud(String detalleSolicitud) {
        this.detalleSolicitud = detalleSolicitud;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public TxUsuario getIdUsuarioAutorizacion() {
        return idUsuarioAutorizacion;
    }

    public void setIdUsuarioAutorizacion(TxUsuario idUsuarioAutorizacion) {
        this.idUsuarioAutorizacion = idUsuarioAutorizacion;
    }

    public String getDetalleAutorizacion() {
        return detalleAutorizacion;
    }

    public void setDetalleAutorizacion(String detalleAutorizacion) {
        this.detalleAutorizacion = detalleAutorizacion;
    }

    public Date getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(Date fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public TxUsuario getIdUsuarioEjecucion() {
        return idUsuarioEjecucion;
    }

    public void setIdUsuarioEjecucion(TxUsuario idUsuarioEjecucion) {
        this.idUsuarioEjecucion = idUsuarioEjecucion;
    }

    public String getDetalleEjecucion() {
        return detalleEjecucion;
    }

    public void setDetalleEjecucion(String detalleEjecucion) {
        this.detalleEjecucion = detalleEjecucion;
    }

    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
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

    
    public List<AfSolicitudActivoFijo> getAfSolicitudActivoFijoList() {
        return afSolicitudActivoFijoList;
    }

    public void setAfSolicitudActivoFijoList(List<AfSolicitudActivoFijo> afSolicitudActivoFijoList) {
        this.afSolicitudActivoFijoList = afSolicitudActivoFijoList;
    }

    
    public List<AfSolicitudMaterial> getAfSolicitudMaterialList() {
        return afSolicitudMaterialList;
    }

    public void setAfSolicitudMaterialList(List<AfSolicitudMaterial> afSolicitudMaterialList) {
        this.afSolicitudMaterialList = afSolicitudMaterialList;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idSolicitud != null ? idSolicitud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfSolicitud)) {
            return false;
        }
        AfSolicitud other = (AfSolicitud) object;
        if ((this.idSolicitud == null && other.idSolicitud != null) || (this.idSolicitud != null && !this.idSolicitud.equals(other.idSolicitud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfSolicitud[ idSolicitud=" + idSolicitud + " ]";
    }
    
}
