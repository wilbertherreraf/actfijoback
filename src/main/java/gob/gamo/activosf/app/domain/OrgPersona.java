/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

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
@Table(name = "org_persona")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrgPersona {
    @Id
    @Column(name = "id_persona")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPersona;

    @Column(name = "primer_nombre")
    private String primerNombre;

    @Column(name = "primer_apellido")
    private String primerApellido;

    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @Column(name = "tx_id")
    private Integer txId;

    @Column(name = "tx_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date txFecha;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "estado")
    private String estado;

    @Column(name = "segundo_nombre")
    private String segundoNombre;

    @Column(name = "trato")
    private String trato;

    /*     @JoinColumn(name = "id_cargo", referencedColumnName = "id_cargo")
    @ManyToOne(fetch = FetchType.EAGER)
    private TxCargo idCargo; */

    @Transient
    public String getNombreCompleto() {
        StringBuilder sb = new StringBuilder(this.getPrimerNombre());
        if (this.getSegundoNombre() != null) {
            sb.append(" ").append(this.getSegundoNombre());
        }
        if (this.getPrimerApellido() != null) {
            sb.append(" ").append(this.getPrimerApellido());
        }
        if (this.getSegundoApellido() != null) {
            sb.append(" ").append(this.getSegundoApellido());
        }
        return sb.toString();
    }

    public OrgPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
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

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getTrato() {
        return trato;
    }

    public void setTrato(String trato) {
        this.trato = trato;
    }

    /*     public TxCargo getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(TxCargo idCargo) {
        this.idCargo = idCargo;
    } */

}
