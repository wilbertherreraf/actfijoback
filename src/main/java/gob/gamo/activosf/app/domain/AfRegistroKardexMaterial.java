/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Basic;
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
@Table(name = "acf_registro_kardex_material")

public class AfRegistroKardexMaterial  {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_registro_kardex_material")
    private Integer idRegistroKardexMaterial;
    
    
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    
    
    @Column(name = "detalle")
    private String detalle;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    
    @Column(name = "importe_unitario")
    private BigDecimal importeUnitario;
    
    
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Column(name = "saldo")
    private Integer saldo;
    
    
    
    @Column(name = "cat_tipo_registro_kardex")
    private String catTipoRegistroKardex;
    @JoinColumn(name = "id_usuario_registro", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private TxUsuario idUsuarioRegistro;
    
    
    
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
    @OneToMany(mappedBy = "idRegistroKardexMaterial", fetch = FetchType.LAZY)
    private List<AfBajaMaterial> afBajaMaterialList;
    @OneToMany(mappedBy = "idRegistroKardexMaterial", fetch = FetchType.LAZY)
    private List<AfAltaMaterialDetalle> afAltaMaterialDetalleList;
    
    @JoinColumn(name = "id_kardex_material", referencedColumnName = "id_kardex_material")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private AfKardexMaterial idKardexMaterial;

    @JoinColumn(name = "id_solicitud_material", referencedColumnName = "id_solicitud_material")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private AfSolicitudMaterial idSolicitudMaterial;

    
    @Transient
    private Integer cantidadSaldo;
    
    @Transient
    private BigDecimal importeSaldo;
    

    public AfRegistroKardexMaterial() {
    }

    public AfRegistroKardexMaterial(Integer idRegistroKardexMaterial) {
        this.idRegistroKardexMaterial = idRegistroKardexMaterial;
    }

    public AfRegistroKardexMaterial(Integer idRegistroKardexMaterial, Date fechaRegistro, String detalle, BigDecimal importeUnitario, Integer cantidad, String catTipoRegistroKardex, TxUsuario idUsuarioRegistro, String estado, Integer idTransaccion, Date txFchIni, Integer txUsrIni, String txHostIni) {
        this.idRegistroKardexMaterial = idRegistroKardexMaterial;
        this.fechaRegistro = fechaRegistro;
        this.detalle = detalle;
        this.importeUnitario = importeUnitario;
        this.cantidad = cantidad;
        this.catTipoRegistroKardex = catTipoRegistroKardex;
        this.idUsuarioRegistro = idUsuarioRegistro;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    @Transient
    public Integer getCantidadSaldo() {
		return cantidadSaldo;
	}

    @Transient
	public BigDecimal getImporteSaldo() {
		return importeSaldo;
	}

    @Transient
	public void setCantidadSaldo(int cantidadSaldo) {
		this.cantidadSaldo = cantidadSaldo;
	}

    @Transient
	public void setImporteSaldo(BigDecimal importeSaldo) {
		this.importeSaldo = importeSaldo;
	}

	public Integer getIdRegistroKardexMaterial() {
        return idRegistroKardexMaterial;
    }

    public void setIdRegistroKardexMaterial(Integer idRegistroKardexMaterial) {
        this.idRegistroKardexMaterial = idRegistroKardexMaterial;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public BigDecimal getImporteUnitario() {
        return importeUnitario;
    }

    public void setImporteUnitario(BigDecimal importeUnitario) {
        this.importeUnitario = importeUnitario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getSaldo() {
		return saldo;
	}

	public void setSaldo(Integer saldo) {
		this.saldo = saldo;
	}

	public String getCatTipoRegistroKardex() {
        return catTipoRegistroKardex;
    }

    public void setCatTipoRegistroKardex(String catTipoRegistroKardex) {
        this.catTipoRegistroKardex = catTipoRegistroKardex;
    }

    public TxUsuario getIdUsuarioRegistro() {
        return idUsuarioRegistro;
    }

    public void setIdUsuarioRegistro(TxUsuario idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
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

    public List<AfBajaMaterial> getAfBajaMaterialList() {
		return afBajaMaterialList;
	}

	public void setAfBajaMaterialList(List<AfBajaMaterial> afBajaMaterialList) {
		this.afBajaMaterialList = afBajaMaterialList;
	}

	
    public List<AfAltaMaterialDetalle> getAfAltaMaterialList() {
        return afAltaMaterialDetalleList;
    }

    public void setAfAltaMaterialList(List<AfAltaMaterialDetalle> afAltaMaterialDetalleList) {
        this.afAltaMaterialDetalleList = afAltaMaterialDetalleList;
    }

    public AfKardexMaterial getIdKardexMaterial() {
        return idKardexMaterial;
    }

    public void setIdKardexMaterial(AfKardexMaterial idKardexMaterial) {
        this.idKardexMaterial = idKardexMaterial;
    }

    public AfSolicitudMaterial getIdSolicitudMaterial() {
		return idSolicitudMaterial;
	}

	public void setIdSolicitudMaterial(AfSolicitudMaterial idSolicitudMaterial) {
		this.idSolicitudMaterial = idSolicitudMaterial;
	}

	@Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idRegistroKardexMaterial != null ? idRegistroKardexMaterial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfRegistroKardexMaterial)) {
            return false;
        }
        AfRegistroKardexMaterial other = (AfRegistroKardexMaterial) object;
        if ((this.idRegistroKardexMaterial == null && other.idRegistroKardexMaterial != null) || (this.idRegistroKardexMaterial != null && !this.idRegistroKardexMaterial.equals(other.idRegistroKardexMaterial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfRegistroKardexMaterial[ idRegistroKardexMaterial=" + idRegistroKardexMaterial + " ]";
    }

/* 	@Override
	public int compareTo(AfRegistroKardexMaterial o) {
		return this.fechaRegistro.compareTo(o.fechaRegistro);
	} */
    
}
