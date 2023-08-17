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


import jakarta.persistence.OneToMany;
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
@Table(name = "acf_proveedor")

public class AfProveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_proveedor")
    private Integer idProveedor;
    
    
    
    @Column(name = "nombre")
    private String nombre;
    
    
    
    @Column(name = "nit")
    private String nit;
    
    @Column(name = "telefono")
    private String telefono;
    
    @Column(name = "correo_electronico")
    private String correoElectronico;
    
    @Column(name = "persona_contacto")
    private String personaContacto;
    
    @Column(name = "cargo_contacto")
    private String cargoContacto;
    
    
    
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
    @OneToMany(mappedBy = "idProveedor", fetch = FetchType.LAZY)
    private List<AfMaterialProveedor> afMaterialProveedorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProveedor", fetch = FetchType.EAGER, orphanRemoval=true)
    private List<AfProveedorActEco> afProveedorActEcoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProveedor", fetch = FetchType.LAZY)
    private List<AfActivoFijo> afActivoFijoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProveedor", fetch = FetchType.LAZY)
    private List<AfAltaMaterial> afAltaMaterialList;

    public AfProveedor() {
    }

    public AfProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public AfProveedor(Integer idProveedor, String nombre, String nit, String estado, Integer idTransaccion, Date txFchIni, Integer txUsrIni, String txHostIni) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.nit = nit;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }

    public String getCargoContacto() {
        return cargoContacto;
    }

    public void setCargoContacto(String cargoContacto) {
        this.cargoContacto = cargoContacto;
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

    
    public List<AfMaterialProveedor> getAfMaterialProveedorList() {
        return afMaterialProveedorList;
    }

    public void setAfMaterialProveedorList(List<AfMaterialProveedor> afMaterialProveedorList) {
        this.afMaterialProveedorList = afMaterialProveedorList;
    }

    
    public List<AfProveedorActEco> getAfProveedorActEcoList() {
        return afProveedorActEcoList;
    }

    public void setAfProveedorActEcoList(List<AfProveedorActEco> afProveedorActEcoList) {
        this.afProveedorActEcoList = afProveedorActEcoList;
    }

    
    public List<AfActivoFijo> getAfActivoFijoList() {
        return afActivoFijoList;
    }

    public void setAfActivoFijoList(List<AfActivoFijo> afActivoFijoList) {
        this.afActivoFijoList = afActivoFijoList;
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
        hash += (idProveedor != null ? idProveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfProveedor)) {
            return false;
        }
        AfProveedor other = (AfProveedor) object;
        if ((this.idProveedor == null && other.idProveedor != null) || (this.idProveedor != null && !this.idProveedor.equals(other.idProveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfProveedor[ idProveedor=" + idProveedor + " ]";
    }
    
}
