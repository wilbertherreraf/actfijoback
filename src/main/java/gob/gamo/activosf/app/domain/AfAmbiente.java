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
@Table(name = "acf_ambiente")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AfAmbiente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ambiente")
    private Integer idAmbiente;

    @Column(name = "cat_tipo_ambiente")
    private String catTipoAmbiente;

    @Column(name = "tab_tipo_ambiente")
    private Integer tabTipoAmbiente;

    @Column(name = "tipo_ambiente")
    private Integer tipoAmbiente;

    @Column(name = "cat_edificio")
    private String catEdificio;

    @Column(name = "cat_piso")
    private String catPiso;

    @Column(name = "nombre")
    private String nombre;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAmbienteOrigen", fetch = FetchType.LAZY)
    private List<AfTransferenciaAsignacion> afTransferenciaAsignacionOrigenList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAmbienteDestino", fetch = FetchType.LAZY)
    private List<AfTransferenciaAsignacion> afTransferenciaAsignacionDestinoList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAmbiente", fetch = FetchType.LAZY)
    private List<AfActivoFijo> afActivoFijoList;

    public AfAmbiente(
            Integer idAmbiente,
            String catTipoAmbiente,
            String catEdificio,
            String catPiso,
            String nombre,
            String estado,
            Integer idTransaccion,
            Date txFchIni,
            Integer txUsrIni,
            String txHostIni) {
        this.idAmbiente = idAmbiente;
        this.catTipoAmbiente = catTipoAmbiente;
        this.catEdificio = catEdificio;
        this.catPiso = catPiso;
        this.nombre = nombre;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdAmbiente() {
        return idAmbiente;
    }

    public void setIdAmbiente(Integer idAmbiente) {
        this.idAmbiente = idAmbiente;
    }

    public String getCatTipoAmbiente() {
        return catTipoAmbiente;
    }

    public void setCatTipoAmbiente(String catTipoAmbiente) {
        this.catTipoAmbiente = catTipoAmbiente;
    }

    public String getCatEdificio() {
        return catEdificio;
    }

    public void setCatEdificio(String catEdificio) {
        this.catEdificio = catEdificio;
    }

    public String getCatPiso() {
        return catPiso;
    }

    public void setCatPiso(String catPiso) {
        this.catPiso = catPiso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public List<AfTransferenciaAsignacion> getAfTransferenciaAsignacionOrigenList() {
        return afTransferenciaAsignacionOrigenList;
    }

    public void setAfTransferenciaAsignacionOrigenList(List<AfTransferenciaAsignacion> afTransferenciaAsignacionList) {
        this.afTransferenciaAsignacionOrigenList = afTransferenciaAsignacionList;
    }

    public List<AfTransferenciaAsignacion> getAfTransferenciaAsignacionDestino() {
        return afTransferenciaAsignacionDestinoList;
    }

    public void setAfTransferenciaAsignacionDestino(List<AfTransferenciaAsignacion> afTransferenciaAsignacionList) {
        this.afTransferenciaAsignacionDestinoList = afTransferenciaAsignacionList;
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
        hash += (idAmbiente != null ? idAmbiente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfAmbiente)) {
            return false;
        }
        AfAmbiente other = (AfAmbiente) object;
        if ((this.idAmbiente == null && other.idAmbiente != null)
                || (this.idAmbiente != null && !this.idAmbiente.equals(other.idAmbiente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfAmbiente[ idAmbiente=" + idAmbiente + " ]";
    }
}
