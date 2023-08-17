package gob.gamo.activosf.app.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;




public class ActivoFijoImp implements java.io.Serializable{

	
	
	
	private String codigo;
	private String codigoAntiguo;
	private Integer gestion;
	private Integer idAmbiente;
	private String catCentroCosto;
	private String catEstadoUso;
	private String catTipoAsignacion;
	//private String catTipoActualizacion;
	private BigDecimal costoHistorico;
	private BigDecimal costoActual;
	private BigDecimal depAcumuladaActual;
	//private BigDecimal depAcumuladaHistorico;
	private Date fechaHistorico;
	private Date fechaActual;
	//private BigDecimal factorDepreciacionHistorico;
	//private BigDecimal factorDepreciacionActual;
	//private boolean incorporacionEspecial;
	//private String catFuenteFinanciamiento;
	//private String nroConvenio;
	//private String ordenCompra;
	private Integer idUsuarioAsignado;
	private String descripcion;
	private String observaciones;

	//private List<byte[]> imagen;
	private List<Tuple> atributos;
	//private List<TupleVo<String,String>> componentes;
	
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
