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
@Table(name = "acf_partida_presupuestaria")
public class AfPartidaPresupuestaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_partida_presupuestaria")
    private Integer idPartidaPresupuestaria;

    @Column(name = "gestion")
    private Integer gestion;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "id_origen")
    private Integer idOrigen;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPartidaPresupuestaria", fetch = FetchType.LAZY)
    private List<AfMaterial> afMaterialList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPartidaPresupuestaria", fetch = FetchType.LAZY)
    private List<AfFamiliaActivo> afFamiliaActivoList;

    public AfPartidaPresupuestaria() {}

    public AfPartidaPresupuestaria(Integer idPartidaPresupuestaria) {
        this.idPartidaPresupuestaria = idPartidaPresupuestaria;
    }

    public AfPartidaPresupuestaria(
            Integer idPartidaPresupuestaria,
            Integer gestion,
            String codigo,
            String descripcion,
            String estado,
            Integer idTransaccion,
            Date txFchIni,
            Integer txUsrIni,
            String txHostIni) {
        this.idPartidaPresupuestaria = idPartidaPresupuestaria;
        this.gestion = gestion;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdPartidaPresupuestaria() {
        return idPartidaPresupuestaria;
    }

    public void setIdPartidaPresupuestaria(Integer idPartidaPresupuestaria) {
        this.idPartidaPresupuestaria = idPartidaPresupuestaria;
    }

    public Integer getGestion() {
        return gestion;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public List<AfMaterial> getAfMaterialList() {
        return afMaterialList;
    }

    public void setAfMaterialList(List<AfMaterial> afMaterialList) {
        this.afMaterialList = afMaterialList;
    }

    public List<AfFamiliaActivo> getAfFamiliaActivoList() {
        return afFamiliaActivoList;
    }

    public void setAfFamiliaActivoList(List<AfFamiliaActivo> afFamiliaActivoList) {
        this.afFamiliaActivoList = afFamiliaActivoList;
    }

    public Integer getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(Integer idOrigen) {
        this.idOrigen = idOrigen;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idPartidaPresupuestaria != null ? idPartidaPresupuestaria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfPartidaPresupuestaria)) {
            return false;
        }
        AfPartidaPresupuestaria other = (AfPartidaPresupuestaria) object;
        if ((this.idPartidaPresupuestaria == null && other.idPartidaPresupuestaria != null)
                || (this.idPartidaPresupuestaria != null
                        && !this.idPartidaPresupuestaria.equals(other.idPartidaPresupuestaria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfPartidaPresupuestaria[ idPartidaPresupuestaria="
                + idPartidaPresupuestaria + " ]";
    }
}
