/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



/**
 *
 * @author wherrera
 */
@Entity
@Table(name = "acf_material")

public class AfMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_material")
    private Integer idMaterial;
    
    
    @Column(name = "codigo")
    private String codigo;
    
    
    
    @Column(name = "cat_familia_material")
    private String catFamiliaMaterial;
    
    
    
    @Column(name = "cat_medida")
    private String catMedida;
    
    
    
    @Column(name = "nombre")
    private String nombre;
    
    
    @Column(name = "minimo")
    private Integer minimo;
    
    
    @Column(name = "maximo")
    private Integer maximo;
    
    
    @Column(name = "fungible")
    private boolean fungible;
    
    
    @Column(name = "revisar_minimo")
    private boolean revisarMinimo;
    
    
    
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
    @OneToMany(mappedBy = "idMaterial", fetch = FetchType.LAZY)
    private List<AfKardexMaterial> afKardexMaterialList;
    @JoinColumn(name = "id_partida_presupuestaria", referencedColumnName = "id_partida_presupuestaria")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private AfPartidaPresupuestaria idPartidaPresupuestaria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMaterial", fetch = FetchType.LAZY)
    private List<AfSolicitudMaterial> afSolicitudMaterialList;
    @OneToMany(mappedBy = "idMaterial", fetch = FetchType.LAZY)
    private List<AfMaterialProveedor> afMaterialProveedorList;
    @OneToMany(mappedBy = "idMaterial", fetch = FetchType.LAZY)
    private List<AfAltaMaterialDetalle> afAltaMaterialDetalleList;

    public AfMaterial() {
    }

    public AfMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }

    public AfMaterial(Integer idMaterial, String catFamiliaMaterial, String catMedida, String nombre, Integer minimo, Integer maximo, boolean fungible, boolean revisarMinimo, String estado, Integer idTransaccion, Date txFchIni, Integer txUsrIni, String txHostIni) {
        this.idMaterial = idMaterial;
        this.catFamiliaMaterial = catFamiliaMaterial;
        this.catMedida = catMedida;
        this.nombre = nombre;
        this.minimo = minimo;
        this.maximo = maximo;
        this.fungible = fungible;
        this.revisarMinimo = revisarMinimo;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    @Transient
    public String  getCodigoCompleto() {
    	return codigo;
    }
    
    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

    public String getCatFamiliaMaterial() {
        return catFamiliaMaterial;
    }

    public void setCatFamiliaMaterial(String catFamiliaMaterial) {
        this.catFamiliaMaterial = catFamiliaMaterial;
    }

    public String getCatMedida() {
        return catMedida;
    }

    public void setCatMedida(String catMedida) {
        this.catMedida = catMedida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getMinimo() {
        return minimo;
    }

    public void setMinimo(int minimo) {
        this.minimo = minimo;
    }

    public Integer getMaximo() {
        return maximo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    public boolean getFungible() {
        return fungible;
    }

    public void setFungible(boolean fungible) {
        this.fungible = fungible;
    }

    public boolean getRevisarMinimo() {
        return revisarMinimo;
    }

    public void setRevisarMinimo(boolean revisarMinimo) {
        this.revisarMinimo = revisarMinimo;
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

    public AfPartidaPresupuestaria getIdPartidaPresupuestaria() {
        return idPartidaPresupuestaria;
    }

    public void setIdPartidaPresupuestaria(AfPartidaPresupuestaria idPartidaPresupuestaria) {
        this.idPartidaPresupuestaria = idPartidaPresupuestaria;
    }

    
    public List<AfSolicitudMaterial> getAfSolicitudMaterialList() {
        return afSolicitudMaterialList;
    }

    public void setAfSolicitudMaterialList(List<AfSolicitudMaterial> afSolicitudMaterialList) {
        this.afSolicitudMaterialList = afSolicitudMaterialList;
    }

    
    public List<AfMaterialProveedor> getAfMaterialProveedorList() {
        return afMaterialProveedorList;
    }

    public void setAfMaterialProveedorList(List<AfMaterialProveedor> afMaterialProveedorList) {
        this.afMaterialProveedorList = afMaterialProveedorList;
    }

    
    public List<AfAltaMaterialDetalle> getAfAltaMaterialDetalleList() {
        return afAltaMaterialDetalleList;
    }

    public void setAfAltaMaterialDetalleList(List<AfAltaMaterialDetalle> afAltaMaterialDetalleList) {
        this.afAltaMaterialDetalleList = afAltaMaterialDetalleList;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idMaterial != null ? idMaterial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfMaterial)) {
            return false;
        }
        AfMaterial other = (AfMaterial) object;
        if ((this.idMaterial == null && other.idMaterial != null) || (this.idMaterial != null && !this.idMaterial.equals(other.idMaterial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfMaterial[ idMaterial=" + idMaterial + " ]";
    }
    
}
