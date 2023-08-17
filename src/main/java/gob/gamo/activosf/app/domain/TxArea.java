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
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


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
@Table(name = "org_unidad")

public class TxArea {
    @Id
    
    
    @Column(name = "id_area")
    private Integer idArea;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "tipo_area")
    private String tipoArea;
    @Column(name = "tx_id")
    private Integer txId;
    @Column(name = "tx_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date txFecha;
    
    @Column(name = "usuario")
    private String usuario;
    
    @Column(name = "estado")
    private String estado;
    
    @Column(name = "abreviacion")
    private String abreviacion;
    
    @Column(name = "cite")
    private String cite;
    @OneToMany(mappedBy = "idAreaPadre", fetch = FetchType.LAZY)
    private List<TxArea> txAreaList;
    @JoinColumn(name = "id_area_padre", referencedColumnName = "id_area")
    @ManyToOne(fetch = FetchType.LAZY)
    private TxArea idAreaPadre;


    public TxArea() {
    }

    public TxArea(Integer idArea) {
        this.idArea = idArea;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoArea() {
        return tipoArea;
    }

    public void setTipoArea(String tipoArea) {
        this.tipoArea = tipoArea;
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

    public String getAbreviacion() {
        return abreviacion;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }

    public String getCite() {
        return cite;
    }

    public void setCite(String cite) {
        this.cite = cite;
    }

    
    public List<TxArea> getTxAreaList() {
        return txAreaList;
    }

    public void setTxAreaList(List<TxArea> txAreaList) {
        this.txAreaList = txAreaList;
    }

    public TxArea getIdAreaPadre() {
        return idAreaPadre;
    }

    public void setIdAreaPadre(TxArea idAreaPadre) {
        this.idAreaPadre = idAreaPadre;
    }

    
    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idArea != null ? idArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TxArea)) {
            return false;
        }
        TxArea other = (TxArea) object;
        if ((this.idArea == null && other.idArea != null) || (this.idArea != null && !this.idArea.equals(other.idArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.TxArea[ idArea=" + idArea + " ]";
    }
    
}
