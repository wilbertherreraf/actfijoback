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
@Table(name = "sec_usuario")
public class TxUsuario {
    @Id
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "alias")
    private String alias;

    @Column(name = "tipo_usuario")
    private String tipoUsuario;

    @Column(name = "tx_id")
    private Integer txId;

    @Column(name = "tx_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date txFecha;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "estado")
    private String estado;

    @Column(name = "contrasena")
    private String contrasena;

    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    @ManyToOne(fetch = FetchType.EAGER)
    private TxPersona idPersona;

    public TxUsuario() {}

    public TxUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Integer getTxId() {
        return txId;
    }

    public void setTxId(Integer txId) {
        this.txId = txId;
    }

    public Date getTxFecha() {
        return txFecha;
    }

    public void setTxFecha(Date txFecha) {
        this.txFecha = txFecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public TxPersona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(TxPersona idPersona) {
        this.idPersona = idPersona;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TxUsuario)) {
            return false;
        }
        TxUsuario other = (TxUsuario) object;
        if ((this.idUsuario == null && other.idUsuario != null)
                || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.TxUsuario[ idUsuario=" + idUsuario + " ]";
    }
}
