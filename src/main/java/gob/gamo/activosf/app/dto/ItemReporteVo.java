package gob.gamo.activosf.app.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ItemReporteVo implements java.io.Serializable {

	
	
	private String centroCosto;
	private String codigo;
	private String descripcion;
	private String condicion;
	private BigDecimal costoHistorico;
	private BigDecimal costoMigrado; //actualInicial
	private BigDecimal depreciacionMigrado; //actualInicial
	private Date fechaMigracion;
	private Date fechaHistorica;
	private BigDecimal indiceUfv;
	private boolean revaluado;
	private BigDecimal vidaUtil;
	private Integer diasVidaUtilNominal;
	private BigDecimal porcentajeDepreciacionAnual;
	private Integer diasConsumidos;
	private BigDecimal factorActualizacion;
	private BigDecimal actualizacionGestion;
	private BigDecimal costoFinalActualizado; //Costo total actualizado
	private BigDecimal depreciacionGestion;
	private BigDecimal depreciacionAcumuladaTotal;
	private BigDecimal actualizacionDepreciacionAcumulada;
	private BigDecimal valorNeto;
	private String familia;
	private String subfamilia;
	private String partidaPresupuestaria;
	private String codigoContable;
	private Integer cantidad;
	private String edificio;
	private String piso;
	private String ambiente;
	private String responsable;
	private String observaciones;
	private String fuenteFinanciamiento;
	private String organismoFinanciador;
	private boolean revaluarOBaja;
	private String tipoAsignacion;
	private String atributos;
	private String componentes;
	private String factura;
	private String garantia;
	
	private BigDecimal antesRevaluo;
	private BigDecimal incremento;
	private BigDecimal decremento;
	
	private BigDecimal valorNetoInicial; //Solo para agrupados
	
	public ItemReporteVo() {
		costoHistorico = BigDecimal.ZERO;
		costoMigrado = BigDecimal.ZERO;
		depreciacionMigrado = BigDecimal.ZERO;
		depreciacionAcumuladaTotal = BigDecimal.ZERO;
		valorNetoInicial = BigDecimal.ZERO;
		actualizacionGestion = BigDecimal.ZERO;
		costoFinalActualizado = BigDecimal.ZERO;
		depreciacionGestion = BigDecimal.ZERO;
		depreciacionAcumuladaTotal = BigDecimal.ZERO;
		valorNeto = BigDecimal.ZERO;
		cantidad = 0;
		antesRevaluo = BigDecimal.ZERO;
		incremento = BigDecimal.ZERO;
		decremento = BigDecimal.ZERO;
		actualizacionDepreciacionAcumulada = BigDecimal.ZERO;
	}
	
	public void agruparItemReporteVo(ItemReporteVo itemReporteVo) {
		cantidad++;
		costoHistorico = costoHistorico.add(itemReporteVo.costoHistorico);
		costoMigrado = costoMigrado.add(itemReporteVo.costoMigrado);
		depreciacionMigrado = depreciacionMigrado.add(itemReporteVo.depreciacionMigrado);
		depreciacionAcumuladaTotal = depreciacionAcumuladaTotal.add(itemReporteVo.depreciacionAcumuladaTotal);
		valorNetoInicial = valorNetoInicial.add(itemReporteVo.valorNetoInicial);
		actualizacionGestion = actualizacionGestion.add(itemReporteVo.actualizacionGestion);
		costoFinalActualizado = costoFinalActualizado.add(itemReporteVo.costoFinalActualizado);
		depreciacionGestion = depreciacionGestion.add(itemReporteVo.depreciacionGestion);
		antesRevaluo = antesRevaluo.add(itemReporteVo.antesRevaluo);
		incremento = incremento.add(itemReporteVo.incremento);
		decremento = decremento.add(itemReporteVo.decremento);
		valorNeto = valorNeto.add(itemReporteVo.valorNeto);
		actualizacionDepreciacionAcumulada = actualizacionDepreciacionAcumulada.add(itemReporteVo.actualizacionDepreciacionAcumulada);
	}
    
		
	public BigDecimal getActualizacionDepreciacionAcumulada() {
		return actualizacionDepreciacionAcumulada;
	}

	public void setActualizacionDepreciacionAcumulada(
			BigDecimal actualizacionDepreciacionAcumulada) {
		this.actualizacionDepreciacionAcumulada = actualizacionDepreciacionAcumulada;
	}

	public String getCentroCosto() {
		return centroCosto;
	}
	public void setCentroCosto(String centroCosto) {
		this.centroCosto = centroCosto;
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
	public String getCondicion() {
		return condicion;
	}
	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}
	public BigDecimal getCostoHistorico() {
		return costoHistorico;
	}
	public void setCostoHistorico(BigDecimal costoHistorico) {
		this.costoHistorico = costoHistorico;
	}
	public BigDecimal getCostoMigrado() {
		return costoMigrado;
	}
	public void setCostoMigrado(BigDecimal costoMigrado) {
		this.costoMigrado = costoMigrado;
	}
	public Date getFechaMigracion() {
		return fechaMigracion;
	}
	public void setFechaMigracion(Date fechaMigracion) {
		this.fechaMigracion = fechaMigracion;
	}
	public Date getFechaHistorica() {
		return fechaHistorica;
	}
	public void setFechaHistorica(Date fechaHistorica) {
		this.fechaHistorica = fechaHistorica;
	}
	public BigDecimal getIndiceUfv() {
		return indiceUfv;
	}
	public void setIndiceUfv(BigDecimal indiceUfv) {
		this.indiceUfv = indiceUfv;
	}
	public boolean isRevaluado() {
		return revaluado;
	}
	public void setRevaluado(boolean revaluado) {
		this.revaluado = revaluado;
	}
	public BigDecimal getVidaUtil() {
		return vidaUtil;
	}
	public void setVidaUtil(BigDecimal vidaUtil) {
		this.vidaUtil = vidaUtil;
	}
	public BigDecimal getPorcentajeDepreciacionAnual() {
		return porcentajeDepreciacionAnual;
	}
	public void setPorcentajeDepreciacionAnual(
			BigDecimal porcentajeDepreciacionAnual) {
		this.porcentajeDepreciacionAnual = porcentajeDepreciacionAnual;
	}
	public Integer getDiasConsumidos() {
		return diasConsumidos;
	}
	public void setDiasConsumidos(int diasConsumidos) {
		this.diasConsumidos = diasConsumidos;
	}
	public BigDecimal getFactorActualizacion() {
		return factorActualizacion;
	}
	public void setFactorActualizacion(BigDecimal factorActualizacion) {
		this.factorActualizacion = factorActualizacion;
	}
	public BigDecimal getCostoFinalActualizado() {
		return costoFinalActualizado;
	}
	public void setCostoFinalActualizado(BigDecimal costoFinalActualizado) {
		this.costoFinalActualizado = costoFinalActualizado;
	}
	public BigDecimal getDepreciacionGestion() {
		return depreciacionGestion;
	}
	public void setDepreciacionGestion(BigDecimal depreciacionGestion) {
		this.depreciacionGestion = depreciacionGestion;
	}
	public BigDecimal getDepreciacionAcumuladaTotal() {
		return depreciacionAcumuladaTotal;
	}
	public void setDepreciacionAcumuladaTotal(BigDecimal depreciacionAcumuladaTotal) {
		this.depreciacionAcumuladaTotal = depreciacionAcumuladaTotal;
	}
	public BigDecimal getValorNeto() {
		return valorNeto;
	}
	public void setValorNeto(BigDecimal valorNeto) {
		this.valorNeto = valorNeto;
	}
	public String getFamilia() {
		return familia;
	}
	public void setFamilia(String familia) {
		this.familia = familia;
	}
	public String getSubfamilia() {
		return subfamilia;
	}
	public void setSubfamilia(String subfamilia) {
		this.subfamilia = subfamilia;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getAmbiente() {
		return ambiente;
	}
	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}
	public String getEdificio() {
		return edificio;
	}
	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}
	public String getPiso() {
		return piso;
	}
	public void setPiso(String piso) {
		this.piso = piso;
	}
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getOrganismoFinanciador() {
		return organismoFinanciador;
	}
	public void setOrganismoFinanciador(String organismoFinanciador) {
		this.organismoFinanciador = organismoFinanciador;
	}
	public String getFuenteFinanciamiento() {
		return fuenteFinanciamiento;
	}

	public void setFuenteFinanciamiento(String fuenteFinanciamiento) {
		this.fuenteFinanciamiento = fuenteFinanciamiento;
	}

	public boolean isRevaluarOBaja() {
		return revaluarOBaja;
	}
	public void setRevaluarOBaja(boolean revaluarOBaja) {
		this.revaluarOBaja = revaluarOBaja;
	}
	public String getPartidaPresupuestaria() {
		return partidaPresupuestaria;
	}
	public void setPartidaPresupuestaria(String partidaPresupuestaria) {
		this.partidaPresupuestaria = partidaPresupuestaria;
	}
	public String getCodigoContable() {
		return codigoContable;
	}
	public void setCodigoContable(String codigoContable) {
		this.codigoContable = codigoContable;
	}
	public String getAtributos() {
		return atributos;
	}
	public void setAtributos(String atributos) {
		this.atributos = atributos;
	}
	public String getComponentes() {
		return componentes;
	}
	public void setComponentes(String componentes) {
		this.componentes = componentes;
	}
	public String getFactura() {
		return factura;
	}
	public void setFactura(String factura) {
		this.factura = factura;
	}
	public String getGarantia() {
		return garantia;
	}
	public void setGarantia(String garantia) {
		this.garantia = garantia;
	}
	public BigDecimal getDepreciacionMigrado() {
		return depreciacionMigrado;
	}
	public void setDepreciacionMigrado(BigDecimal depreciacionMigrado) {
		this.depreciacionMigrado = depreciacionMigrado;
	}
	public Integer getDiasVidaUtilNominal() {
		return diasVidaUtilNominal;
	}
	public void setDiasVidaUtilNominal(int diasVidaUtilNominal) {
		this.diasVidaUtilNominal = diasVidaUtilNominal;
	}
	public BigDecimal getActualizacionGestion() {
		return actualizacionGestion;
	}
	public void setActualizacionGestion(BigDecimal actualizacionGestion) {
		this.actualizacionGestion = actualizacionGestion;
	}

	public String getTipoAsignacion() {
		return tipoAsignacion;
	}

	public void setTipoAsignacion(String tipoAsignacion) {
		this.tipoAsignacion = tipoAsignacion;
	}

	public BigDecimal getAntesRevaluo() {
		return antesRevaluo;
	}

	public void setAntesRevaluo(BigDecimal antesRevaluo) {
		this.antesRevaluo = antesRevaluo;
	}

	public BigDecimal getIncremento() {
		return incremento;
	}

	public void setIncremento(BigDecimal incremento) {
		this.incremento = incremento;
	}

	public BigDecimal getDecremento() {
		return decremento;
	}

	public void setDecremento(BigDecimal decremento) {
		this.decremento = decremento;
	}
	
}
