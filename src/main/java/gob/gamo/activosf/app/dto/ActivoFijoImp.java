package gob.gamo.activosf.app.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/*
export interface Claves {
  id?: number;
  codclave?: string;
  descrip?: string;
  valor?: string;
};
export interface Tablas {
  id?: number;
  codigo?: number;
  descripcion?: string;
};
export interface Desctabla {
  id?: number;
  codtab?: number;
  codigo?: number;
  descrip?: string;
  codeiso?: string;
};
export interface Unidad {
  id?: number;
  nombre?: string;
  domicilio?: string;
  sigla?: string;
  telefono?: string;
  tipoUnidad?: string;
  idUnidadPadre?: number;
  estado?: string;
};
export interface Persona {
  id?: number;
  nombre?: string;
  nombreDesc?: string;
  primerApellido?: string;
  segundoApellido?: string;
  numeroDocumento?: string;
  tipoDocumento?: string;
  tabtipodoc?: number;
  tipodoc?: number;
  direccion?: string;
  telefono?: string;
  email?: string;
  tabtipopers?: number;
  tipopers?: number;
  nemonico?: string;
  txFecha?: Date;
  estado?: string;
  trato?: string;
};
export interface Empleado {
  id?: number;
  codInternoempl?: string;
  idPersona?: number;
  idCargo?: number;
  fechaIngreso?: Date;
  fechaBaja?: Date;
  estado?: string;
};
export interface UnidadEmp {
  id?: number;
  idUnidad?: number;
  idEmplresponsable?: number;
  idEmplsubordinado?: number;
  fechaReg?: Date;
  estado?: string;
};

export interface Usuario {
  id?: number;
  login?: string;
  nombres?: string;
  email?: string;
  password?: string;
  idUnidEmpl?: number;
  tabtipousr?: number;
  tipousr?: number;
};

export interface Profile {
  id?: number;
  prrResid?: number;
  prrRolid?: number;
};
export interface Userrol {
  id?: number;
  uroUsrid?: number;
  uroRolid?: number;
};
export interface Inmueble {
  id?: number;
  domicilio?: string;
  fechaRegistro?: Date;
  nombre?: string;
  sigla?: string;
  telefono?: string;
  idEmpleadoResp?: number;
  fechaCreacion?: Date;
  idUnidad?: number;
};
export interface AccesorioActivoFijo {
  id?: number;
  idActivoFijo?: number;
  idFactura?: number;
  catTipoAccesorio?: string;
  tabTipoAccesorio?: number;
  tipoAccesorio?: number;
  fechaAdquisicion?: Date;
  detalle?: string;
  importeTotal?: number;
  cantidad?: number;
  observacion?: string;
  estado?: string;
};

export interface ActivoFijo {
  id?: number;
  correlativo?: number;
  gestion?: number;
  idSubFamilia?: number;
  idProveedor?: number;
  idAmbiente?: number;
  idNotaRecepcion?: number;
  idGarantiaActivoFijo?: number;
  idFactura?: number;
  revalorizado?: string;
  codigoAntiguo?: string;
  catCentroCosto?: string;
  tabCentroCosto?: number;
  centroCosto?: number;
  catEstadoUso?: string;
  tabEstadoUso?: number;
  estadoUso?: number;
  catTipoAsignacion?: string;
  tabTipoAsignacion?: number;
  tipoAsignacion?: number;
  catTipoActualizacion?: string;
  tabTipoActualizacion?: number;
  tipoActualizacion?: number;
  costoHistorico?: number;
  costoActual?: number;
  costoAntesRevaluo?: number;
  depAcumuladaActual?: number;
  depAcumuladaHistorico?: number;
  fechaHistorico?: Date;
  fechaActual?: Date;
  factorDepreciacionHistorico?: number;
  factorDepreciacionActual?: number;
  incorporacionEspecial?: string;
  catFuenteFinanciamiento?: string;
  tabFuenteFinanciamiento?: number;
  fuenteFinanciamiento?: number;
  catOrganismoFinanciador?: string;
  nroConvenio?: string;
  ordenCompra?: string;
  idUsuarioAsignado?: number;
  descripcion?: string;
  observaciones?: string;
  codigoExtendido?: string;
  codigoRfid?: string;
  codigoEan?: string;
  catEstadoActivoFijo?: string;
  tabEstadoActivoFijo?: number;
  estadoActivoFijo?: number;
  estado?: string;
};

export interface Almacen {
  id?: number;
  nombre?: string;
  observaciones?: string;
  esValorado?: string;
  estado?: string;
};

export interface AltaMaterial {
  id?: number;
  correlativo?: number;
  gestion?: number;
  catTipoAltaMaterial?: string;
  tabTipoAltaMaterial?: number;
  tipoAltaMaterial?: number;
  idFactura?: number;
  idAlmacen?: number;
  idProveedor?: number;
  fechaAlta?: Date;
  idUsuarioAlta?: number;
  fechaValorado?: Date;
  observaciones?: string;
  catEstadoAltaMaterial?: string;
  tabEstadoAltaMaterial?: number;
  estadoAltaMaterial?: number;
  estado?: string;
};

export interface AltaMaterialDetalle {
  id?: number;
  idAltaMaterial?: number;
  idMaterial?: number;
  idRegistroKardexMaterial?: number;
  importeUnitario?: number;
  cantidad?: number;
  estado?: string;
};

export interface Ambiente {
  id?: number;
  catTipoAmbiente?: string;
  tabTipoAmbiente?: number;
  tipoAmbiente?: number;
  catEdificio?: string;
  catPiso?: string;
  nombre?: string;
  estado?: string;
};

export interface AtributoActivoFijo {
  id?: number;
  idActivoFijo?: number;
  catTipoAtributo?: string;
  tabTipoAtributo?: number;
  tipoAtributo?: number;
  detalle?: string;
  observacion?: string;
  estado?: string;
};

export interface AtributoSubFamilia {
  id?: number;
  idSubFamilia?: number;
  catTipoAtributo?: string;
  tabTipoAtributo?: number;
  tipoAtributo?: number;
  prioridad?: number;
  imprimible?: string;
  idOrigen?: number;
  estado?: string;
};

export interface BajaActivoFijo {
  id?: number;
  correlativo?: number;
  gestion?: number;
  idActivoFijo?: number;
  catMotivoBajaActivoFijo?: string;
  documentoRespaldo?: string;
  observaciones?: string;
  estado?: string;
};

export interface BajaMaterial {
  id?: number;
  idKardexMaterial?: number;
  idRegistroKardexMaterial?: number;
  correlativo?: number;
  gestion?: number;
  catTipoBajaMaterial?: string;
  tabTipoBajaMaterial?: number;
  tipoBajaMaterial?: number;
  idUsuarioBaja?: number;
  detalle?: string;
  cantidad?: number;
  catEstadoBajaMaterial?: string;
  tabEstadoBajaMaterial?: number;
  estadoBajaMaterial?: number;
  estado?: string;
};

export interface CodigoContable {
  id?: number;
  gestion?: number;
  codigo?: string;
  descripcion?: string;
  idOrigen?: number;
  estado?: string;
};

export interface ComisionRecepcion {
  id?: number;
  idNotaRecepcion?: number;
  idUsuario?: number;
  estado?: string;
};

export interface ComponenteActivoFijo {
  id?: number;
  idActivoFijo?: number;
  correlativo?: number;
  catComponenteActivoFijo?: string;
  cantidad?: number;
  observacion?: string;
  codigoRfid?: string;
  codigoEan?: string;
  estado?: string;
};

export interface Factura {
  id?: number;
  nroFactura?: string;
  fechaFactura?: Date;
  nroAutorizacion?: string;
  codigoControl?: string;
  razonSocial?: string;
  nit?: string;
  estado?: string;
};

export interface FamiliaActivo {
  id?: number;
  idCodigoContable?: number;
  idPartidaPresupuestaria?: number;
  gestion?: number;
  codigo?: string;
  descripcion?: string;
  idOrigen?: number;
  estado?: string;
};

export interface GarantiaActivoFijo {
  id?: number;
  catTipoGarantia?: string;
  tabTipoGarantia?: number;
  tipoGarantia?: number;
  codigoContrato?: string;
  estado?: string;
};

export interface Gestion {
  id?: number;
  gestion?: number;
  vigente?: string;
  catEstadoGestion?: string;
  tabEstadoGestion?: number;
  estadoGestion?: number;
  estado?: string;
};

export interface ImagenActivoFijo {
  id?: number;
  idActivoFijo?: number;
  idAccesorioActivoFijo?: number;
  idTransferenciaActivoFijo?: number;
  imagen?: string;
  nombreArchivo?: string;
  tipoMime?: string;
  estado?: string;
};

export interface KardexMaterial {
  id?: number;
  gestion?: number;
  idMaterial?: number;
  idAlmacen?: number;
  saldoCantidad?: number;
  saldoImporte?: number;
  version?: number;
  estado?: string;
};

export interface KardexMaterialHist {
  id?: number;
  idKardexMaterial?: number;
  gestion?: number;
  idMaterial?: number;
  idAlmacen?: number;
  saldoCantidad?: number;
  saldoImporte?: number;
  version?: number;
  ultimoGestion?: string;
  estado?: string;
};

export interface Material {
  id?: number;
  codigo?: string;
  catFamiliaMaterial?: string;
  idPartidaPresupuestaria?: number;
  catMedida?: string;
  nombre?: string;
  minimo?: number;
  maximo?: number;
  fungible?: string;
  revisarMinimo?: string;
  estado?: string;
};

export interface MaterialProveedor {
  id?: number;
  idMaterial?: number;
  idProveedor?: number;
  estado?: string;
};

export interface NotaRecepcion {
  id?: number;
  correlativo?: number;
  gestion?: number;
  catTipoMovimiento?: string;
  tabTipoMovimiento?: number;
  tipoMovimiento?: number;
  catMotivoTipoMovimiento?: string;
  catTipoDocumentoRecepcion?: string;
  tabTipoDocumentoRecepcion?: number;
  tipoDocumentoRecepcion?: number;
  nroDocumentoRecepcion?: string;
  ordenCompra?: string;
  catEstadoNotaRecepcion?: string;
  tabEstadoNotaRecepcion?: number;
  estadoNotaRecepcion?: number;
  idUsuarioRecepcion?: number;
  idAreaSolicitante?: number;
  idControlCalidad?: number;
  estado?: string;
};

export interface PartidaPresupuestaria {
  id?: number;
  gestion?: number;
  codigo?: string;
  descripcion?: string;
  idOrigen?: number;
  estado?: string;
};

export interface Proveedor {
  id?: number;
  nombre?: string;
  nit?: string;
  telefono?: string;
  correoElectronico?: string;
  personaContacto?: string;
  cargoContacto?: string;
  estado?: string;
};
export interface ProveedorActEco {
  id?: number;
  catActividadEconomica?: string;
  idProveedor?: number;
  estado?: string;
};
export interface RegistroKardexMaterial {
  id?: number;
  idKardexMaterial?: number;
  idSolicitudMaterial?: number;
  detalle?: string;
  importeUnitario?: number;
  cantidad?: number;
  saldo?: number;
  catTipoRegistroKardex?: string;
  tabTipoRegistroKardex?: number;
  tipoRegistroKardex?: number;
  idUsuarioRegistro?: number;
  estado?: string;
};


export interface RevaluoActivoFijo {
  id?: number;
  idActivoFijo?: number;
  fechaRevaluo?: Date;
  nuevoFactorDepreciacion?: number;
  dispocisionRespaldo?: string;
  motivo?: string;
  costoHistorico?: number;
  costoNuevo?: number;
  depAlRevaluo?: number;
  depAcumAlRevaluo?: number;
  valorNetoAlRevaluo?: number;
  estado?: string;
};

export interface Solicitud {
  id?: number;
  correlativo?: number;
  gestion?: number;
  catTipoSolicitud?: string;
  tabTipoSolicitud?: number;
  tipoSolicitud?: number;
  catEstadoSolicitud?: string;
  tabEstadoSolicitud?: number;
  estadoSolicitud?: number;
  idUsuarioSolicitud?: number;
  detalleSolicitud?: string;
  idUsuarioAutorizacion?: number;
  detalleAutorizacion?: string;
  idUsuarioEjecucion?: number;
  detalleEjecucion?: string;
  estado?: string;
};

export interface SolicitudActivoFijo {
  id?: number;
  idSolicitud?: number;
  idActivoFijo?: number;
  estado?: string;
};

export interface SolicitudMaterial {
  id?: number;
  idSolicitud?: number;
  idMaterial?: number;
  cantidadSolicitada?: number;
  cantidadAprobada?: number;
  cantidadEntregada?: number;
  estado?: string;
};

export interface SubFamiliaActivo {
  id?: number;
  gestion?: number;
  idFamiliaActivo?: number;
  codigo?: string;
  descripcion?: string;
  factorDepreciacion?: number;
  depreciar?: string;
  actualizar?: string;
  amortizacionVariable?: string;
  idOrigen?: number;
  estado?: string;
};
export interface TipoCambio {
  id?: number;
  catMoneda?: string;
  tabMoneda?: number;
  moneda?: number;
  fecha?: Date;
  cambio?: number;
  estado?: string;
};
export interface TransferenciaAsignacion {
  id?: number;
  correlativo?: number;
  gestion?: number;
  idActivoFijo?: number;
  idNotaRecepcion?: number;
  catTransferenciaAsignacion?: string;
  catMotivoTransferencia?: string;
  catTipoAsignacion?: string;
  tabTipoAsignacion?: number;
  tipoAsignacion?: number;
  catEstadoUso?: string;
  tabEstadoUso?: number;
  estadoUso?: number;
  observaciones?: string;
  catCentroCostoOrigen?: string;
  tabCentroCostoOrigen?: number;
  centroCostoOrigen?: number;
  idUsuarioOrigen?: number;
  idAmbienteOrigen?: number;
  catCentroCostoDestino?: string;
  tabCentroCostoDestino?: number;
  centroCostoDestino?: number;
  idUsuarioDestino?: number;
  idAmbienteDestino?: number;
  estado?: string;
};

export interface TxPersona {
  id?: number;
  nombre?: string;
  nombreDesc?: string;
  primerApellido?: string;
  segundoApellido?: string;
  numeroDocumento?: string;
  tipoDocumento?: string;
  tabtipodoc?: number;
  tipodoc?: number;
  direccion?: string;
  telefono?: string;
  email?: string;
  tabtipopers?: number;
  tipopers?: number;
  nemonico?: string;
  txFecha?: Date;
  estado?: string;
  trato?: string;
};
*/
public class ActivoFijoImp implements java.io.Serializable {

    private String codigo;
    private String codigoAntiguo;
    private Integer gestion;
    private Integer idAmbiente;
    private String catCentroCosto;
    private String catEstadoUso;
    private String catTipoAsignacion;
    // private String catTipoActualizacion;
    private BigDecimal costoHistorico;
    private BigDecimal costoActual;
    private BigDecimal depAcumuladaActual;
    // private BigDecimal depAcumuladaHistorico;
    private Date fechaHistorico;
    private Date fechaActual;
    // private BigDecimal factorDepreciacionHistorico;
    // private BigDecimal factorDepreciacionActual;
    // private boolean incorporacionEspecial;
    // private String catFuenteFinanciamiento;
    // private String nroConvenio;
    // private String ordenCompra;
    private Integer idUsuarioAsignado;
    private String descripcion;
    private String observaciones;

    // private List<byte[]> imagen;
    private List<Tuple> atributos;
    // private List<TupleVo<String,String>> componentes;

    private String catMotivoTipoMovimiento;

    public String getCatMotivoTipoMovimiento() {
        return catMotivoTipoMovimiento;
    }

    public void setCatMotivoTipoMovimiento(String catMotivoTipoMovimiento) {
        this.catMotivoTipoMovimiento = catMotivoTipoMovimiento;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoAntiguo() {
        return codigoAntiguo;
    }

    public void setCodigoAntiguo(String codigoAntiguo) {
        this.codigoAntiguo = codigoAntiguo;
    }

    public Integer getGestion() {
        return gestion;
    }

    public void setGestion(Integer gestion) {
        this.gestion = gestion;
    }

    public Integer getIdAmbiente() {
        return idAmbiente;
    }

    public void setIdAmbiente(Integer idAmbiente) {
        this.idAmbiente = idAmbiente;
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

    public BigDecimal getDepAcumuladaActual() {
        return depAcumuladaActual;
    }

    public void setDepAcumuladaActual(BigDecimal depAcumuladaActual) {
        this.depAcumuladaActual = depAcumuladaActual;
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

    public Integer getIdUsuarioAsignado() {
        return idUsuarioAsignado;
    }

    public void setIdUsuarioAsignado(int idUsuarioAsignado) {
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

    public List<Tuple> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<Tuple> atributos) {
        this.atributos = atributos;
    }
}
