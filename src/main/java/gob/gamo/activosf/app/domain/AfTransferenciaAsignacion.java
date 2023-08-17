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
@Table(name = "acf_transferencia_asignacion")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AfTransferenciaAsignacion {
	
	public enum TipoTransferencia {
        ASIGNACION("ASGNCN"), TRANSFERENCIA("TRNSFR") , DEVOLUCION("DEVLCN");
        private final String valor;
        private TipoTransferencia(String valor) {
            this.valor = valor;
        }
        public String getValor(){
            return this.valor;
        }
    }
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transferencia_activo_fijo")
    private Integer idTransferenciaActivoFijo;
    
    
    @Column(name = "correlativo")
    private Integer correlativo;
    
    @Column(name = "gestion")
    private Integer gestion;
    
    @Column(name = "cat_transferencia_asignacion")
    private String catTransferenciaAsignacion;
    
    @Column(name = "fecha_transferencia")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaTransferencia;
    
    @Column(name = "cat_motivo_transferencia")
    private String catMotivoTransferencia;
    
    @Column(name = "cat_tipo_asignacion")
    private String catTipoAsignacion;
	@Column(name = "tab_tipo_asignacion")
	private Integer tabTipoAsignacion;
	@Column(name = "tipo_asignacion")
	private Integer tipoAsignacion;    
    
    @Column(name = "cat_estado_uso")
    private String catEstadoUso;
    
    @Column(name = "observaciones")
    private String observaciones;
    
    @Column(name = "cat_centro_costo_origen")
    private String catCentroCostoOrigen;
	@Column(name = "tab_centro_costo_origen")
	private Integer tabCentroCostoOrigen;
	@Column(name = "centro_costo_origen")
	private Integer centroCostoOrigen;  

    @JoinColumn(name = "id_usuario_origen", referencedColumnName = "id_usuario")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private TxUsuario idUsuarioOrigen;
    
    @Column(name = "cat_centro_costo_destino")
    private String catCentroCostoDestino;
	@Column(name = "tab_centro_costo_destino")
	private Integer tabCentroCostoDestino;
	@Column(name = "centro_costo_destino")
	private Integer centroCostoDestino;  

    @JoinColumn(name = "id_usuario_destino", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TxUsuario idUsuarioDestino;
    
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
    @JoinColumn(name = "id_nota_recepcion", referencedColumnName = "id_nota_recepcion")
    @ManyToOne(fetch = FetchType.LAZY)
    private AfNotaRecepcion idNotaRecepcion;
    @JoinColumn(name = "id_ambiente_origen", referencedColumnName = "id_ambiente")
    @ManyToOne(fetch = FetchType.LAZY)
    private AfAmbiente idAmbienteOrigen;
    @JoinColumn(name = "id_ambiente_destino", referencedColumnName = "id_ambiente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AfAmbiente idAmbienteDestino;
    @JoinColumn(name = "id_activo_fijo", referencedColumnName = "id_activo_fijo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AfActivoFijo idActivoFijo;
    @OneToMany(mappedBy = "idTransferenciaActivoFijo", fetch = FetchType.LAZY)
    private List<AfImagenActivoFijo> afImagenActivoFijoList;

    public AfTransferenciaAsignacion(Integer idTransferenciaActivoFijo, Integer correlativo, Integer gestion, String catTransferenciaAsignacion, Date fechaTransferencia, String catTipoAsignacion, String catEstadoUso, String catCentroCostoDestino, TxUsuario idUsuarioDestino, String estado, Integer idTransaccion, Date txFchIni, Integer txUsrIni, String txHostIni) {
        this.idTransferenciaActivoFijo = idTransferenciaActivoFijo;
        this.correlativo = correlativo;
        this.gestion = gestion;
        this.catTransferenciaAsignacion = catTransferenciaAsignacion;
        this.fechaTransferencia = fechaTransferencia;
        this.catTipoAsignacion = catTipoAsignacion;
        this.catEstadoUso = catEstadoUso;
        this.catCentroCostoDestino = catCentroCostoDestino;
        this.idUsuarioDestino = idUsuarioDestino;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdTransferenciaActivoFijo() {
        return idTransferenciaActivoFijo;
    }

    public void setIdTransferenciaActivoFijo(Integer idTransferenciaActivoFijo) {
        this.idTransferenciaActivoFijo = idTransferenciaActivoFijo;
    }

    public Integer getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(int correlativo) {
        this.correlativo = correlativo;
    }

    public Integer getGestion() {
        return gestion;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }

    public String getCatTransferenciaAsignacion() {
        return catTransferenciaAsignacion;
    }

    public void setCatTransferenciaAsignacion(String catTransferenciaAsignacion) {
        this.catTransferenciaAsignacion = catTransferenciaAsignacion;
    }

    public Date getFechaTransferencia() {
        return fechaTransferencia;
    }

    public void setFechaTransferencia(Date fechaTransferencia) {
        this.fechaTransferencia = fechaTransferencia;
    }

    public String getCatMotivoTransferencia() {
        return catMotivoTransferencia;
    }

    public void setCatMotivoTransferencia(String catMotivoTransferencia) {
        this.catMotivoTransferencia = catMotivoTransferencia;
    }

    public String getCatTipoAsignacion() {
        return catTipoAsignacion;
    }

    public void setCatTipoAsignacion(String catTipoAsignacion) {
        this.catTipoAsignacion = catTipoAsignacion;
    }

    public String getCatEstadoUso() {
        return catEstadoUso;
    }

    public void setCatEstadoUso(String catEstadoUso) {
        this.catEstadoUso = catEstadoUso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getCatCentroCostoOrigen() {
        return catCentroCostoOrigen;
    }

    public void setCatCentroCostoOrigen(String catCentroCostoOrigen) {
        this.catCentroCostoOrigen = catCentroCostoOrigen;
    }

    public TxUsuario getIdUsuarioOrigen() {
        return idUsuarioOrigen;
    }

    public void setIdUsuarioOrigen(TxUsuario idUsuarioOrigen) {
        this.idUsuarioOrigen = idUsuarioOrigen;
    }

    public String getCatCentroCostoDestino() {
        return catCentroCostoDestino;
    }

    public void setCatCentroCostoDestino(String catCentroCostoDestino) {
        this.catCentroCostoDestino = catCentroCostoDestino;
    }

    public TxUsuario getIdUsuarioDestino() {
        return idUsuarioDestino;
    }

    public void setIdUsuarioDestino(TxUsuario idUsuarioDestino) {
        this.idUsuarioDestino = idUsuarioDestino;
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

    public AfNotaRecepcion getIdNotaRecepcion() {
        return idNotaRecepcion;
    }

    public void setIdNotaRecepcion(AfNotaRecepcion idNotaRecepcion) {
        this.idNotaRecepcion = idNotaRecepcion;
    }

    public AfAmbiente getIdAmbienteOrigen() {
        return idAmbienteOrigen;
    }

    public void setIdAmbienteOrigen(AfAmbiente idAmbienteOrigen) {
        this.idAmbienteOrigen = idAmbienteOrigen;
    }

    public AfAmbiente getIdAmbienteDestino() {
        return idAmbienteDestino;
    }

    public void setIdAmbienteDestino(AfAmbiente idAmbienteDestino) {
        this.idAmbienteDestino = idAmbienteDestino;
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
        hash += (idTransferenciaActivoFijo != null ? idTransferenciaActivoFijo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfTransferenciaAsignacion)) {
            return false;
        }
        AfTransferenciaAsignacion other = (AfTransferenciaAsignacion) object;
        if ((this.idTransferenciaActivoFijo == null && other.idTransferenciaActivoFijo != null) || (this.idTransferenciaActivoFijo != null && !this.idTransferenciaActivoFijo.equals(other.idTransferenciaActivoFijo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfTransferenciaAsignacion[ idTransferenciaActivoFijo=" + idTransferenciaActivoFijo + " ]";
    }
    
}
