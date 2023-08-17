/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.io.Serializable;
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
@Table(name = "acf_imagen_activo_fijo")

public class AfImagenActivoFijo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_imagen_activo_fijo")
    private Integer idImagenActivoFijo;
    
    
    @Column(name = "imagen")
    private byte[] imagen;
    
    
    
    @Column(name = "nombre_archivo")
    private String nombreArchivo;
    
    
    
    @Column(name = "tipo_mime")
    private String tipoMime;
    
    
    @Column(name = "fecha_captura")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCaptura;
    
    
    
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
    @JoinColumn(name = "id_transferencia_activo_fijo", referencedColumnName = "id_transferencia_activo_fijo")
    @ManyToOne(fetch = FetchType.LAZY)
    private AfTransferenciaAsignacion idTransferenciaActivoFijo;
    @JoinColumn(name = "id_activo_fijo", referencedColumnName = "id_activo_fijo")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AfActivoFijo idActivoFijo;
    @JoinColumn(name = "id_accesorio_activo_fijo", referencedColumnName = "id_accesorio_activo_fijo")
    @ManyToOne(fetch = FetchType.LAZY)
    private AfAccesorioActivoFijo idAccesorioActivoFijo;

    public AfImagenActivoFijo() {
    }

    public AfImagenActivoFijo(Integer idImagenActivoFijo) {
        this.idImagenActivoFijo = idImagenActivoFijo;
    }

    public AfImagenActivoFijo(Integer idImagenActivoFijo, byte[] imagen, String nombreArchivo, String tipoMime, Date fechaCaptura, String estado, Integer idTransaccion, Date txFchIni, Integer txUsrIni, String txHostIni) {
        this.idImagenActivoFijo = idImagenActivoFijo;
        this.imagen = imagen;
        this.nombreArchivo = nombreArchivo;
        this.tipoMime = tipoMime;
        this.fechaCaptura = fechaCaptura;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdImagenActivoFijo() {
        return idImagenActivoFijo;
    }

    public void setIdImagenActivoFijo(Integer idImagenActivoFijo) {
        this.idImagenActivoFijo = idImagenActivoFijo;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getTipoMime() {
        return tipoMime;
    }

    public void setTipoMime(String tipoMime) {
        this.tipoMime = tipoMime;
    }

    public Date getFechaCaptura() {
        return fechaCaptura;
    }

    public void setFechaCaptura(Date fechaCaptura) {
        this.fechaCaptura = fechaCaptura;
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

    public AfTransferenciaAsignacion getIdTransferenciaActivoFijo() {
        return idTransferenciaActivoFijo;
    }

    public void setIdTransferenciaActivoFijo(AfTransferenciaAsignacion idTransferenciaActivoFijo) {
        this.idTransferenciaActivoFijo = idTransferenciaActivoFijo;
    }

    public AfActivoFijo getIdActivoFijo() {
        return idActivoFijo;
    }

    public void setIdActivoFijo(AfActivoFijo idActivoFijo) {
        this.idActivoFijo = idActivoFijo;
    }

    public AfAccesorioActivoFijo getIdAccesorioActivoFijo() {
        return idAccesorioActivoFijo;
    }

    public void setIdAccesorioActivoFijo(AfAccesorioActivoFijo idAccesorioActivoFijo) {
        this.idAccesorioActivoFijo = idAccesorioActivoFijo;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idImagenActivoFijo != null ? idImagenActivoFijo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfImagenActivoFijo)) {
            return false;
        }
        AfImagenActivoFijo other = (AfImagenActivoFijo) object;
        if ((this.idImagenActivoFijo == null && other.idImagenActivoFijo != null) || (this.idImagenActivoFijo != null && !this.idImagenActivoFijo.equals(other.idImagenActivoFijo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfImagenActivoFijo[ idImagenActivoFijo=" + idImagenActivoFijo + " ]";
    }
    
}
