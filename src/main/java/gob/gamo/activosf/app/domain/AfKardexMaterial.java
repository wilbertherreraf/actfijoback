/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import jakarta.persistence.Version;

/**
 *
 * @author wherrera
 */
@Entity
@Table(name = "acf_kardex_material")
public class AfKardexMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kardex_material")
    private Integer idKardexMaterial;

    @Column(name = "gestion")
    private Integer gestion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "saldo_cantidad")
    private Integer saldoCantidad;

    @Column(name = "saldo_importe")
    private BigDecimal saldoImporte;

    @Version
    @Column(name = "version")
    private Integer version;

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

    @JoinColumn(name = "id_material", referencedColumnName = "id_material")
    @ManyToOne(fetch = FetchType.EAGER)
    private AfMaterial idMaterial;

    @JoinColumn(name = "id_almacen", referencedColumnName = "id_almacen")
    @ManyToOne(fetch = FetchType.EAGER)
    private AfAlmacen idAlmacen;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idKardexMaterial", fetch = FetchType.LAZY)
    private List<AfRegistroKardexMaterial> afRegistroKardexMaterialList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idKardexMaterial", fetch = FetchType.LAZY)
    private List<AfBajaMaterial> afBajaMaterialList;

    private static MathContext mc = new MathContext(2, RoundingMode.HALF_UP);

    public AfKardexMaterial() {}

    public AfKardexMaterial(Integer idKardexMaterial) {
        this.idKardexMaterial = idKardexMaterial;
    }

    public AfKardexMaterial(
            Integer idKardexMaterial,
            Integer gestion,
            String estado,
            Integer idTransaccion,
            Date txFchIni,
            Integer txUsrIni,
            String txHostIni) {
        this.idKardexMaterial = idKardexMaterial;
        this.gestion = gestion;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    public AfKardexMaterial getNuevaGestionConSaldoInicial(TxTransaccion txTransaccion) {
        Integer nuevaGestion = (this.gestion + 1);
        AfKardexMaterial afKardexMaterial = new AfKardexMaterial();
        afKardexMaterial.setGestion(nuevaGestion);
        afKardexMaterial.setIdMaterial(this.idMaterial);
        afKardexMaterial.setIdAlmacen(this.idAlmacen);
        afKardexMaterial.setSaldoCantidad(this.saldoCantidad);
        afKardexMaterial.setSaldoImporte(saldoImporte);
        afKardexMaterial.setVersion(0);
        afKardexMaterial.setEstado("A");
        // TransactionUtil.setInitTransactionData(afKardexMaterial);
        AfRegistroKardexMaterial afRegistroKardexMaterial = new AfRegistroKardexMaterial();
        afRegistroKardexMaterial.setIdKardexMaterial(afKardexMaterial);
        Date nG = Date.from(LocalDate.of(nuevaGestion, 1, 1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        afRegistroKardexMaterial.setFechaRegistro(nG);
        afRegistroKardexMaterial.setDetalle("SALDO INICIAL");
        if (this.idAlmacen.getEsValorado() && afKardexMaterial.getSaldoCantidad() > 0) {
            afRegistroKardexMaterial.setImporteUnitario(
                    afKardexMaterial.getSaldoImporte().divide(new BigDecimal(afKardexMaterial.getSaldoCantidad()), mc));
        } else if (afKardexMaterial.getSaldoCantidad() > 0) {
            afRegistroKardexMaterial.setImporteUnitario(BigDecimal.ZERO);
        }
        afRegistroKardexMaterial.setCantidad(afKardexMaterial.getSaldoCantidad());
        afRegistroKardexMaterial.setSaldo(afKardexMaterial.getSaldoCantidad());
        afRegistroKardexMaterial.setCatTipoRegistroKardex("SALINI");
        afRegistroKardexMaterial.setIdUsuarioRegistro(new TxUsuario(txTransaccion.getTxUsuario()));
        afRegistroKardexMaterial.setEstado("A");
        // TransactionUtil.setInitTransactionData(afRegistroKardexMaterial);
        List<AfRegistroKardexMaterial> registros = new ArrayList<>();
        registros.add(afRegistroKardexMaterial);
        afKardexMaterial.setAfRegistroKardexMaterialList(registros);
        return afKardexMaterial;
    }

    public Integer getIdKardexMaterial() {
        return idKardexMaterial;
    }

    public void setIdKardexMaterial(Integer idKardexMaterial) {
        this.idKardexMaterial = idKardexMaterial;
    }

    public Integer getGestion() {
        return gestion;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getSaldoCantidad() {
        return saldoCantidad;
    }

    public void setSaldoCantidad(Integer saldoCantidad) {
        this.saldoCantidad = saldoCantidad;
    }

    public BigDecimal getSaldoImporte() {
        return saldoImporte;
    }

    public void setSaldoImporte(BigDecimal saldoImporte) {
        this.saldoImporte = saldoImporte;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public AfMaterial getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(AfMaterial idMaterial) {
        this.idMaterial = idMaterial;
    }

    public AfAlmacen getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(AfAlmacen idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public List<AfRegistroKardexMaterial> getAfRegistroKardexMaterialList() {
        return afRegistroKardexMaterialList;
    }

    public void setAfRegistroKardexMaterialList(List<AfRegistroKardexMaterial> afRegistroKardexMaterialList) {
        this.afRegistroKardexMaterialList = afRegistroKardexMaterialList;
    }

    public List<AfBajaMaterial> getAfBajaMaterialList() {
        return afBajaMaterialList;
    }

    public void setAfBajaMaterialList(List<AfBajaMaterial> afBajaMaterialList) {
        this.afBajaMaterialList = afBajaMaterialList;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idKardexMaterial != null ? idKardexMaterial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfKardexMaterial)) {
            return false;
        }
        AfKardexMaterial other = (AfKardexMaterial) object;
        if ((this.idKardexMaterial == null && other.idKardexMaterial != null)
                || (this.idKardexMaterial != null && !this.idKardexMaterial.equals(other.idKardexMaterial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gob.gamo.activosf.app.domain.AfKardexMaterial[ idKardexMaterial=" + idKardexMaterial + " ]";
    }
}
