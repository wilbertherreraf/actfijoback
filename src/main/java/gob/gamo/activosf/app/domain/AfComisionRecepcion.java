/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

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
@Table(name = "acf_comision_recepcion")
public class AfComisionRecepcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comision_recepcion")
    private Integer idComisionRecepcion;

    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TxUsuario idUsuario;

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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AfNotaRecepcion idNotaRecepcion;

    public AfComisionRecepcion() {}

    public AfComisionRecepcion(Integer idComisionRecepcion) {
        this.idComisionRecepcion = idComisionRecepcion;
    }

    public AfComisionRecepcion(
            Integer idComisionRecepcion,
            TxUsuario idUsuario,
            String estado,
            Integer idTransaccion,
            Date txFchIni,
            Integer txUsrIni,
            String txHostIni) {
        this.idComisionRecepcion = idComisionRecepcion;
        this.idUsuario = idUsuario;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdComisionRecepcion() {
        return idComisionRecepcion;
    }

    public void setIdComisionRecepcion(Integer idComisionRecepcion) {
        this.idComisionRecepcion = idComisionRecepcion;
    }

    public TxUsuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(TxUsuario idUsuario) {
        this.idUsuario = idUsuario;
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

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idComisionRecepcion != null ? idComisionRecepcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfComisionRecepcion)) {
            return false;
        }
        AfComisionRecepcion other = (AfComisionRecepcion) object;
        if ((this.idComisionRecepcion == null && other.idComisionRecepcion != null)
                || (this.idComisionRecepcion != null && !this.idComisionRecepcion.equals(other.idComisionRecepcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfComisionRecepcion[ idComisionRecepcion=" + idComisionRecepcion + " ]";
    }
}
