/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
@Table(name = "acf_sub_familia_activo")

public class AfSubFamiliaActivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Column(name = "id_sub_familia")
    private Integer idSubFamilia;
    
    
    @Column(name = "gestion")
    private Integer gestion;
    
    
    
    @Column(name = "codigo")
    private String codigo;
    
    
    
    @Column(name = "descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    
    
    @Column(name = "factor_depreciacion")
    private BigDecimal factorDepreciacion;
    
    
    @Column(name = "depreciar")
    private boolean depreciar;
    
    
    @Column(name = "actualizar")
    private boolean actualizar;
    
    
    @Column(name = "amortizacion_variable")
    private boolean amortizacionVariable;
    
    
    
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSubFamilia", fetch = FetchType.EAGER, orphanRemoval=true)
    private List<AfAtributoSubFamilia> afAtributoSubFamiliaList;
    @JoinColumn(name = "id_familia_activo", referencedColumnName = "id_familia_activo")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private AfFamiliaActivo idFamiliaActivo;
    @OneToMany(mappedBy = "idSubFamilia", fetch = FetchType.LAZY)
    private List<AfActivoFijo> afActivoFijoList;

    public AfSubFamiliaActivo() {
    }

    public AfSubFamiliaActivo(Integer idSubFamilia) {
        this.idSubFamilia = idSubFamilia;
    }

    public AfSubFamiliaActivo(Integer idSubFamilia, Integer gestion, String codigo, String descripcion,  BigDecimal factorDepreciacion, boolean depreciar, boolean actualizar, boolean amortizacionVariable, String estado, Integer idTransaccion, Date txFchIni, Integer txUsrIni, String txHostIni) {
        this.idSubFamilia = idSubFamilia;
        this.gestion = gestion;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.factorDepreciacion = factorDepreciacion;
        this.depreciar = depreciar;
        this.actualizar = actualizar;
        this.amortizacionVariable = amortizacionVariable;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    @Transient
    public Integer getVidaUtilMeses(){
    	if(depreciar && BigDecimal.ZERO.compareTo(factorDepreciacion) != 0){
    		return BigDecimal.ONE.divide(factorDepreciacion, 5, RoundingMode.HALF_UP).multiply(new BigDecimal(12), new MathContext(5)).intValue();
    	} else {
    		return 0;
    	}
    }
    
    public Integer getIdSubFamilia() {
        return idSubFamilia;
    }

    public void setIdSubFamilia(Integer idSubFamilia) {
        this.idSubFamilia = idSubFamilia;
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

    public BigDecimal getFactorDepreciacion() {
		return factorDepreciacion;
	}

	public void setFactorDepreciacion(BigDecimal factorDepreciacion) {
		this.factorDepreciacion = factorDepreciacion;
	}

	public boolean isDepreciar() {
		return depreciar;
	}

	public void setDepreciar(boolean depreciar) {
		this.depreciar = depreciar;
	}

	public boolean isActualizar() {
		return actualizar;
	}

	public void setActualizar(boolean actualizar) {
		this.actualizar = actualizar;
	}

	public boolean isAmortizacionVariable() {
		return amortizacionVariable;
	}

	public void setAmortizacionVariable(boolean amortizacionVariable) {
		this.amortizacionVariable = amortizacionVariable;
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

    
    public List<AfAtributoSubFamilia> getAfAtributoSubFamiliaList() {
        return afAtributoSubFamiliaList;
    }

    public void setAfAtributoSubFamiliaList(List<AfAtributoSubFamilia> afAtributoSubFamiliaList) {
        this.afAtributoSubFamiliaList = afAtributoSubFamiliaList;
    }

    public AfFamiliaActivo getIdFamiliaActivo() {
        return idFamiliaActivo;
    }

    public void setIdFamiliaActivo(AfFamiliaActivo idFamiliaActivo) {
        this.idFamiliaActivo = idFamiliaActivo;
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
        hash += (idSubFamilia != null ? idSubFamilia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfSubFamiliaActivo)) {
            return false;
        }
        AfSubFamiliaActivo other = (AfSubFamiliaActivo) object;
        if ((this.idSubFamilia == null && other.idSubFamilia != null) || (this.idSubFamilia != null && !this.idSubFamilia.equals(other.idSubFamilia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfSubFamiliaActivo[ idSubFamilia=" + idSubFamilia + " ]";
    }
    
}
