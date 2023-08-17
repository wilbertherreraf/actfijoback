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
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


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
@Table(name = "tx_transaccion")

public class TxTransaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_transaccion")
    private Integer idTransaccion;
    
    
    @Column(name = "tx_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date txFecha;
    
    
    @Column(name = "tx_usuario")
    private Integer txUsuario;
    
    
    
    @Column(name = "tx_host")
    private String txHost;

    public TxTransaccion() {
    }

    public TxTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public TxTransaccion(Integer idTransaccion, Date txFecha, Integer txUsuario, String txHost) {
        this.idTransaccion = idTransaccion;
        this.txFecha = txFecha;
        this.txUsuario = txUsuario;
        this.txHost = txHost;
    }

    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Date getTxFecha() {
        return txFecha;
    }

    public void setTxFecha(Date txFecha) {
        this.txFecha = txFecha;
    }

    public Integer getTxUsuario() {
        return txUsuario;
    }

    public void setTxUsuario(int txUsuario) {
        this.txUsuario = txUsuario;
    }

    public String getTxHost() {
        return txHost;
    }

    public void setTxHost(String txHost) {
        this.txHost = txHost;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idTransaccion != null ? idTransaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TxTransaccion)) {
            return false;
        }
        TxTransaccion other = (TxTransaccion) object;
        if ((this.idTransaccion == null && other.idTransaccion != null) || (this.idTransaccion != null && !this.idTransaccion.equals(other.idTransaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.TxTransaccion[ idTransaccion=" + idTransaccion + " ]";
    }
    
}
