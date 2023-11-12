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

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import gob.gamo.activosf.app.services.CalcContabService;

/**
 *
 * @author wherrera
 */
@Entity
@Getter
@Setter
@Builder
@Table(name = "acf_activo_fijo")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AfActivoFijo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_activo_fijo")
    private Integer idActivoFijo;

    @Column(name = "id_item")
    private Integer idItemaf;

    @Column(name = "correlativo")
    private Integer correlativo;

    @Column(name = "gestion")
    private Integer gestion;

    @Column(name = "revalorizado")
    private Boolean revalorizado;

    @Column(name = "codigo_antiguo")
    private String codigoAntiguo;

    @Column(name = "cat_centro_costo")
    private String catCentroCosto;

    @Column(name = "tab_centro_costo")
    private Integer tabCentroCosto;

    @Column(name = "centro_costo")
    private Integer centroCosto;

    @Column(name = "cat_estado_uso")
    private String catEstadoUso;

    @Column(name = "tab_estado_uso")
    private Integer tabEstadoUso;

    @Column(name = "estado_uso")
    private Integer estadoUso;

    @Column(name = "cat_tipo_asignacion")
    private String catTipoAsignacion;

    @Column(name = "tab_tipo_asignacion")
    private Integer tabTipoAsignacion;

    @Column(name = "tipo_asignacion")
    private Integer tipoAsignacion;

    @Column(name = "cat_tipo_actualizacion")
    private String catTipoActualizacion;

    @Column(name = "tab_tipo_actualizacion")
    private Integer tabTipoActualizacion;

    @Column(name = "tipo_actualizacion")
    private Integer tipoActualizacion;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to
    // enforce field validation
    @Column(name = "costo_historico")
    private BigDecimal costoHistorico;

    @Column(name = "costo_actual")
    private BigDecimal costoActual;

    @Column(name = "costo_antes_revaluo")
    private BigDecimal costoAntesRevaluo;

    @Column(name = "dep_acumulada_actual")
    private BigDecimal depAcumuladaActual;

    @Column(name = "dep_acumulada_historico")
    private BigDecimal depAcumuladaHistorico;

    @Column(name = "fecha_historico")
    @Temporal(TemporalType.DATE)
    private Date fechaHistorico;

    @Column(name = "fecha_actual")
    @Temporal(TemporalType.DATE)
    private Date fechaActual;

    @Column(name = "factor_depreciacion_historico")
    private BigDecimal factorDepreciacionHistorico;

    @Column(name = "factor_depreciacion_actual")
    private BigDecimal factorDepreciacionActual;

    @Column(name = "incorporacion_especial")
    private Boolean incorporacionEspecial;

    @Column(name = "cat_fuente_financiamiento")
    private String catFuenteFinanciamiento;

    @Column(name = "tab_fuente_financiamiento")
    private Integer tabFenteFinanciamiento;

    @Column(name = "fuente_financiamiento")
    private Integer fuenteFinanciamiento;

    @Column(name = "cat_organismo_financiador")
    private String catOrganismoFinanciador;

    @Column(name = "tab_organismo_financiador")
    private Integer tabOrganismoFinanciador;

    @Column(name = "organismo_financiador")
    private Integer organismoFinanciador;

    @Column(name = "nro_convenio")
    private String nroConvenio;

    @Column(name = "orden_compra")
    private String ordenCompra;

    @JoinColumn(name = "id_usuario_asignado", referencedColumnName = "id_usuario")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private TxUsuario idUsuarioAsignado;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "codigo_extendido")
    private String codigoExtendido;

    @Column(name = "codigo_rfid")
    private String codigoRfid;

    @Column(name = "codigo_ean")
    private String codigoEan;

    @Column(name = "cat_estado_activo_fijo")
    private String catEstadoActivoFijo;

    @Column(name = "tab_estado_activo_fijo")
    private Integer tabEstadoActivoFijo;

    @Column(name = "estado_activo_fijo")
    private Integer estadoActivoFijo;

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

    /* @Column(name = "id_sub_familia")
    private Integer idSubFamilia;

    @Column(name = "id_proveedor")
    private Integer idProveedor;

    @Column(name = "id_nota_recepcion")
    private Integer idNotaRecepcion;

    @Column(name = "id_garantia_activo_fijo")
    private Integer idGarantiaActivoFijo;

    @Column(name = "id_factura")
    private Integer idFactura;

    @Column(name = "id_ambiente")
    private Integer idAmbiente; */
        
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idActivoFijo", fetch = FetchType.LAZY)
    private List<AfBajaActivoFijo> afBajaActivoFijoList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idActivoFijo", fetch = FetchType.LAZY) // EAGER
    private List<AfAtributoActivoFijo> afAtributoActivoFijoList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idActivoFijo", fetch = FetchType.LAZY) // EAGER
    private List<AfComponenteActivoFijo> afComponenteActivoFijoList;

    @OneToMany(mappedBy = "idActivoFijo", fetch = FetchType.LAZY) // EAGER
    private List<AfSolicitudActivoFijo> afSolicitudActivoFijoList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idActivoFijo", fetch = FetchType.LAZY) // EAGER
    private List<AfAccesorioActivoFijo> afAccesorioActivoFijoList;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "idActivoFijo",
            fetch = FetchType.LAZY,
            orphanRemoval = true) // EAGER
    private List<AfRevaluoActivoFijo> afRevaluoActivoFijoList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idActivoFijo", fetch = FetchType.LAZY) // EAGER
    private List<AfTransferenciaAsignacion> afTransferenciaAsignacionList;

    @JoinColumn(name = "id_sub_familia", referencedColumnName = "id_sub_familia")
    @ManyToOne(fetch = FetchType.LAZY) // EAGER
    private AfSubFamiliaActivo idSubFamilia;

    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor")
    @ManyToOne(optional = true, fetch = FetchType.LAZY) // EAGER
    private AfProveedor idProveedor;

    @JoinColumn(name = "id_nota_recepcion", referencedColumnName = "id_nota_recepcion")
    @ManyToOne(optional = false, fetch = FetchType.LAZY) // EAGER
    private AfNotaRecepcion idNotaRecepcion;

    @JoinColumn(name = "id_garantia_activo_fijo", referencedColumnName = "id_garantia_activo_fijo")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // EAGER
    private AfGarantiaActivoFijo idGarantiaActivoFijo;

    @JoinColumn(name = "id_factura", referencedColumnName = "id_factura")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // EAGER
    private AfFactura idFactura;

    @JoinColumn(name = "id_ambiente", referencedColumnName = "id_ambiente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY) // EAGER
    private AfAmbiente idAmbiente;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idActivoFijo", fetch = FetchType.LAZY) // EAGER
    private List<AfImagenActivoFijo> afImagenActivoFijoList;

    @Transient
    private CalcContabService calculoContableVo;

    public AfActivoFijo(Integer idActivoFijo) {
        this.idActivoFijo = idActivoFijo;
    }

    public AfActivoFijo(
            Integer idActivoFijo,
            Integer gestion,
            boolean revalorizado,
            String catCentroCosto,
            String catEstadoUso,
            String catTipoActualizacion,
            BigDecimal costoHistorico,
            BigDecimal costoActual,
            BigDecimal depAcumuladaActual,
            BigDecimal depAcumuladaHistorico,
            Date fechaHistorico,
            Date fechaActual,
            BigDecimal factorDepreciacionHistorico,
            BigDecimal factorDepreciacionActual,
            boolean incorporacionEspecial,
            String catFuenteFinanciamiento,
            String descripcion,
            String codigoRfid,
            String codigoEan,
            String catEstadoActivoFijo,
            String estado,
            Integer idTransaccion,
            Date txFchIni,
            Integer txUsrIni,
            String txHostIni) {
        this.idActivoFijo = idActivoFijo;
        this.gestion = gestion;
        this.revalorizado = revalorizado;
        this.catCentroCosto = catCentroCosto;
        this.catEstadoUso = catEstadoUso;
        this.catTipoActualizacion = catTipoActualizacion;
        this.costoHistorico = costoHistorico;
        this.costoActual = costoActual;
        this.depAcumuladaActual = depAcumuladaActual;
        this.depAcumuladaHistorico = depAcumuladaHistorico;
        this.fechaHistorico = fechaHistorico;
        this.fechaActual = fechaActual;
        this.factorDepreciacionHistorico = factorDepreciacionHistorico;
        this.factorDepreciacionActual = factorDepreciacionActual;
        this.incorporacionEspecial = incorporacionEspecial;
        this.catFuenteFinanciamiento = catFuenteFinanciamiento;
        this.descripcion = descripcion;
        this.codigoRfid = codigoRfid;
        this.codigoEan = codigoEan;
        this.catEstadoActivoFijo = catEstadoActivoFijo;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
        this.txFchIni = txFchIni;
        this.txUsrIni = txUsrIni;
        this.txHostIni = txHostIni;
    }

    @Transient
    public CalcContabService getCalculoContableVo() {
        return calculoContableVo;
    }

    @Transient
    public void setCalculoContableVo(CalcContabService calculoContableVo) {
        this.calculoContableVo = calculoContableVo;
    }

/*     public Integer getIdActivoFijo() {
        return idActivoFijo;
    }

    public void setIdActivoFijo(Integer idActivoFijo) {
        this.idActivoFijo = idActivoFijo;
    }

    public Integer getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(Integer correlativo) {
        this.correlativo = correlativo;
    }

    public Integer getGestion() {
        return gestion;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }

    public boolean getRevalorizado() {
        return revalorizado;
    }

    public void setRevalorizado(boolean revalorizado) {
        this.revalorizado = revalorizado;
    }

    public String getCodigoAntiguo() {
        return codigoAntiguo;
    }

    public void setCodigoAntiguo(String codigoAntiguo) {
        this.codigoAntiguo = codigoAntiguo;
    }

    public String getCatCentroCosto() {
        return catCentroCosto;
    }

    public void setCatCentroCosto(String catCentroCosto) {
        this.catCentroCosto = catCentroCosto;
    }

    public String getCatEstadoUso() {
        return catEstadoUso;
    }

    public void setCatEstadoUso(String catEstadoUso) {
        this.catEstadoUso = catEstadoUso;
    }

    public String getCatTipoAsignacion() {
        return catTipoAsignacion;
    }

    public void setCatTipoAsignacion(String catTipoAsignacion) {
        this.catTipoAsignacion = catTipoAsignacion;
    }

    public String getCatTipoActualizacion() {
        return catTipoActualizacion;
    }

    public void setCatTipoActualizacion(String catTipoActualizacion) {
        this.catTipoActualizacion = catTipoActualizacion;
    }

    public BigDecimal getCostoHistorico() {
        return costoHistorico;
    }

    public void setCostoHistorico(BigDecimal costoHistorico) {
        this.costoHistorico = costoHistorico;
    }

    public BigDecimal getCostoActual() {
        return costoActual;
    }

    public void setCostoActual(BigDecimal costoActual) {
        this.costoActual = costoActual;
    }

    public BigDecimal getCostoAntesRevaluo() {
        return costoAntesRevaluo;
    }

    public void setCostoAntesRevaluo(BigDecimal costoAntesRevaluo) {
        this.costoAntesRevaluo = costoAntesRevaluo;
    }

    public BigDecimal getDepAcumuladaActual() {
        return depAcumuladaActual;
    }

    public void setDepAcumuladaActual(BigDecimal depAcumuladaActual) {
        this.depAcumuladaActual = depAcumuladaActual;
    }

    public BigDecimal getDepAcumuladaHistorico() {
        return depAcumuladaHistorico;
    }

    public void setDepAcumuladaHistorico(BigDecimal depAcumuladaHistorico) {
        this.depAcumuladaHistorico = depAcumuladaHistorico;
    }

    public Date getFechaHistorico() {
        return fechaHistorico;
    }

    public void setFechaHistorico(Date fechaHistorico) {
        this.fechaHistorico = fechaHistorico;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public BigDecimal getFactorDepreciacionHistorico() {
        return factorDepreciacionHistorico;
    }

    public void setFactorDepreciacionHistorico(BigDecimal factorDepreciacionHistorico) {
        this.factorDepreciacionHistorico = factorDepreciacionHistorico;
    }

    public BigDecimal getFactorDepreciacionActual() {
        return factorDepreciacionActual;
    }

    public void setFactorDepreciacionActual(BigDecimal factorDepreciacionActual) {
        this.factorDepreciacionActual = factorDepreciacionActual;
    }

    public boolean getIncorporacionEspecial() {
        return incorporacionEspecial;
    }

    public void setIncorporacionEspecial(boolean incorporacionEspecial) {
        this.incorporacionEspecial = incorporacionEspecial;
    }

    public String getCatFuenteFinanciamiento() {
        return catFuenteFinanciamiento;
    }

    public void setCatFuenteFinanciamiento(String catFuenteFinanciamiento) {
        this.catFuenteFinanciamiento = catFuenteFinanciamiento;
    }

    public String getCatOrganismoFinanciador() {
        return catOrganismoFinanciador;
    }

    public void setCatOrganismoFinanciador(String catOrganismoFinanciador) {
        this.catOrganismoFinanciador = catOrganismoFinanciador;
    }

    public String getNroConvenio() {
        return nroConvenio;
    }

    public void setNroConvenio(String nroConvenio) {
        this.nroConvenio = nroConvenio;
    }

    public String getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(String ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public TxUsuario getIdUsuarioAsignado() {
        return idUsuarioAsignado;
    }

    public void setIdUsuarioAsignado(TxUsuario idUsuarioAsignado) {
        this.idUsuarioAsignado = idUsuarioAsignado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getCodigoExtendido() {
        return codigoExtendido;
    }

    public void setCodigoExtendido(String codigoExtendido) {
        this.codigoExtendido = codigoExtendido;
    }

    public String getCodigoRfid() {
        return codigoRfid;
    }

    public void setCodigoRfid(String codigoRfid) {
        this.codigoRfid = codigoRfid;
    }

    public String getCodigoEan() {
        return codigoEan;
    }

    public void setCodigoEan(String codigoEan) {
        this.codigoEan = codigoEan;
    }

    public String getCatEstadoActivoFijo() {
        return catEstadoActivoFijo;
    }

    public void setCatEstadoActivoFijo(String catEstadoActivoFijo) {
        this.catEstadoActivoFijo = catEstadoActivoFijo;
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

    public List<AfBajaActivoFijo> getAfBajaActivoFijoList() {
        return afBajaActivoFijoList;
    }

    public void setAfBajaActivoFijoList(List<AfBajaActivoFijo> afBajaActivoFijoList) {
        this.afBajaActivoFijoList = afBajaActivoFijoList;
    }

    public List<AfAtributoActivoFijo> getAfAtributoActivoFijoList() {
        return afAtributoActivoFijoList;
    }

    public void setAfAtributoActivoFijoList(List<AfAtributoActivoFijo> afAtributoActivoFijoList) {
        this.afAtributoActivoFijoList = afAtributoActivoFijoList;
    }

    public List<AfComponenteActivoFijo> getAfComponenteActivoFijoList() {
        return afComponenteActivoFijoList;
    }

    public void setAfComponenteActivoFijoList(List<AfComponenteActivoFijo> afComponenteActivoFijoList) {
        this.afComponenteActivoFijoList = afComponenteActivoFijoList;
    }

    public List<AfSolicitudActivoFijo> getAfSolicitudActivoFijoList() {
        return afSolicitudActivoFijoList;
    }

    public void setAfSolicitudActivoFijoList(List<AfSolicitudActivoFijo> afSolicitudActivoFijoList) {
        this.afSolicitudActivoFijoList = afSolicitudActivoFijoList;
    }

    public List<AfAccesorioActivoFijo> getAfAccesorioActivoFijoList() {
        return afAccesorioActivoFijoList;
    }

    public void setAfAccesorioActivoFijoList(List<AfAccesorioActivoFijo> afAccesorioActivoFijoList) {
        this.afAccesorioActivoFijoList = afAccesorioActivoFijoList;
    }

    public List<AfRevaluoActivoFijo> getAfRevaluoActivoFijoList() {
        return afRevaluoActivoFijoList;
    }

    public void setAfRevaluoActivoFijoList(List<AfRevaluoActivoFijo> afRevaluoActivoFijoList) {
        this.afRevaluoActivoFijoList = afRevaluoActivoFijoList;
    }

    public List<AfTransferenciaAsignacion> getAfTransferenciaAsignacionList() {
        return afTransferenciaAsignacionList;
    }

    public void setAfTransferenciaAsignacionList(List<AfTransferenciaAsignacion> afTransferenciaAsignacionList) {
        this.afTransferenciaAsignacionList = afTransferenciaAsignacionList;
    }

    public AfSubFamiliaActivo getIdSubFamilia() {
        return idSubFamilia;
    }

    public void setIdSubFamilia(AfSubFamiliaActivo idSubFamilia) {
        this.idSubFamilia = idSubFamilia;
    }

    public AfProveedor getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(AfProveedor idProveedor) {
        this.idProveedor = idProveedor;
    }

    public AfNotaRecepcion getIdNotaRecepcion() {
        return idNotaRecepcion;
    }

    public void setIdNotaRecepcion(AfNotaRecepcion idNotaRecepcion) {
        this.idNotaRecepcion = idNotaRecepcion;
    }

    public AfGarantiaActivoFijo getIdGarantiaActivoFijo() {
        return idGarantiaActivoFijo;
    }

    public void setIdGarantiaActivoFijo(AfGarantiaActivoFijo idGarantiaActivoFijo) {
        this.idGarantiaActivoFijo = idGarantiaActivoFijo;
    }

    public AfFactura getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(AfFactura idFactura) {
        this.idFactura = idFactura;
    }

    public AfAmbiente getIdAmbiente() {
        return idAmbiente;
    }

    public void setIdAmbiente(AfAmbiente idAmbiente) {
        this.idAmbiente = idAmbiente;
    }

    public List<AfImagenActivoFijo> getAfImagenActivoFijoList() {
        return afImagenActivoFijoList;
    }

    public void setAfImagenActivoFijoList(List<AfImagenActivoFijo> afImagenActivoFijoList) {
        this.afImagenActivoFijoList = afImagenActivoFijoList;
    }

    @Override
    public int hashCode() {
        Integer hash = 0;
        hash += (idActivoFijo != null ? idActivoFijo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AfActivoFijo)) {
            return false;
        }
        AfActivoFijo other = (AfActivoFijo) object;
        if ((this.idActivoFijo == null && other.idActivoFijo != null)
                || (this.idActivoFijo != null && !this.idActivoFijo.equals(other.idActivoFijo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AfActivoFijo [idActivoFijo=" + idActivoFijo + ", correlativo="
                + correlativo + ", gestion=" + gestion + ", revalorizado="
                + revalorizado + ", codigoAntiguo=" + codigoAntiguo
                + ", catCentroCosto=" + catCentroCosto + ", catEstadoUso="
                + catEstadoUso + ", catTipoAsignacion=" + catTipoAsignacion
                + ", catTipoActualizacion=" + catTipoActualizacion
                + ", costoHistorico=" + costoHistorico + ", costoActual="
                + costoActual + ", costoAntesRevaluo=" + costoAntesRevaluo
                + ", depAcumuladaActual=" + depAcumuladaActual
                + ", depAcumuladaHistorico=" + depAcumuladaHistorico
                + ", fechaHistorico=" + fechaHistorico + ", fechaActual="
                + fechaActual + ", factorDepreciacionHistorico="
                + factorDepreciacionHistorico + ", factorDepreciacionActual="
                + factorDepreciacionActual + ", incorporacionEspecial="
                + incorporacionEspecial + ", catFuenteFinanciamiento="
                + catFuenteFinanciamiento + ", catOrganismoFinanciador="
                + catOrganismoFinanciador + ", nroConvenio=" + nroConvenio
                + ", ordenCompra=" + ordenCompra + ", idUsuarioAsignado="
                + idUsuarioAsignado + ", descripcion=" + descripcion
                + ", observaciones=" + observaciones + ", codigoExtendido="
                + codigoExtendido + ", codigoRfid=" + codigoRfid
                + ", codigoEan=" + codigoEan + ", catEstadoActivoFijo="
                + catEstadoActivoFijo + ", estado=" + estado
                + ", idTransaccion=" + idTransaccion + ", txFchIni=" + txFchIni
                + ", txUsrIni=" + txUsrIni + ", txHostIni=" + txHostIni
                + ", txFchMod=" + txFchMod + ", txUsrMod=" + txUsrMod
                + ", txHostMod=" + txHostMod + "]";
    } */
}
