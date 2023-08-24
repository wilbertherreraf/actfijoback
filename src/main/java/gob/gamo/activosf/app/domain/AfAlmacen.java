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
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author wherrera
 */
@Entity
@Table(name = "acf_almacen")
public class AfAlmacen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_almacen")
    private Integer idAlmacen;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "es_valorado")
    private boolean esValorado;

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

    @OneToMany(mappedBy = "idAlmacen", fetch = FetchType.LAZY)
    private List<AfKardexMaterial> afKardexMaterialList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAlmacen", fetch = FetchType.LAZY)
    private List<AfAltaMaterial> afAltaMaterialList;

    public AfAlmacen() {}

    public AfAlmacen(Integer idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public AfAlmacen(
            Integer idAlmacen,
            String nombre,
            boolean esValorado,
            String estado,
            Integer idTransaccion,
            Date txFchIni,
            Integer txUsrIni,
            String txHostIni) {
        this.idAlmacen = idAlmacen;
        this.nombre = nombre;
        this.esValorado = esValorado;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(Integer idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean getEsValorado() {
        return esValorado;
    }

    public void setEsValorado(boolean esValorado) {
        this.esValorado = esValorado;
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

    public List<AfKardexMaterial> getAfKardexMaterialList() {
        return afKardexMaterialList;
    }

    public void setAfKardexMaterialList(List<AfKardexMaterial> afKardexMaterialList) {
        this.afKardexMaterialList = afKardexMaterialList;
    }

    public List<AfAltaMaterial> getAfAltaMaterialList() {
        return afAltaMaterialList;
    }

    public void setAfAltaMaterialList(List<AfAltaMaterial> afAltaMaterialList) {
        this.afAltaMaterialList = afAltaMaterialList;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idAlmacen != null ? idAlmacen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfAlmacen)) {
            return false;
        }
        AfAlmacen other = (AfAlmacen) object;
        if ((this.idAlmacen == null && other.idAlmacen != null)
                || (this.idAlmacen != null && !this.idAlmacen.equals(other.idAlmacen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfAlmacen[ idAlmacen=" + idAlmacen + " ]";
    }
}
