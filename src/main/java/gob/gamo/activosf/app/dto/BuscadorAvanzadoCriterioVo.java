package gob.gamo.activosf.app.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class BuscadorAvanzadoCriterioVo implements Serializable{

	
	private String codigoActivo;
	private String[] partidasPresupuestarias;
	private String[] codigosContables;
	private String[] familias;
	private String[] subfamilias;
	private String [] centroCosto;
	private String [] fuenteFinanciamiento;
	private String [] organismoFinanciador;
	private Integer[] ambientes;
	private Integer [] area;
	private String[] tipoAsignacion; //Asignado o custodia
	private String[] estado;
	private BigDecimal costoInferior;
	private BigDecimal costoSuperior;
	private Date fechaHistoricaInferior;
	private Date fechaHistoricaSuperior;
	private String[] estadoUsoActivo;
	private String[] tipoActualizacion;
	private Integer gestion;
	private Integer[] proveedor;
	private Integer[] usuarioAsignado;
	private boolean soloRevaluados;
	
	
	public BuscadorAvanzadoCriterioVo(Integer gestion) {
		this.gestion = gestion;
	}

	private HashMap<CriteriosBusquedaEnum, Object> criterios = new HashMap<CriteriosBusquedaEnum, Object>();
	
	public HashMap<CriteriosBusquedaEnum, Object> getCriterios() {
		criterios.clear();
		if (!esNulo(codigoActivo))  
			criterios.put(CriteriosBusquedaEnum.CODIGO_ACTIVO, ""+this.codigoActivo.toUpperCase());
		if (!esNulo(partidasPresupuestarias))  
			criterios.put(CriteriosBusquedaEnum.PARTIDAS_PRESUPUESTARIAS, partidasPresupuestarias);
		if (!esNulo(codigosContables))  
			criterios.put(CriteriosBusquedaEnum.CODIGOS_CONTABLES, codigosContables );
		if (!esNulo(familias))  
			criterios.put(CriteriosBusquedaEnum.FAMILIAS, familias);
		if (!esNulo(subfamilias))  
			criterios.put(CriteriosBusquedaEnum.SUB_FAMILIAS, subfamilias );
		if (!esNulo(centroCosto))  
			criterios.put(CriteriosBusquedaEnum.CENTRO_COSTO, centroCosto);
		if (!esNulo(fuenteFinanciamiento))  
			criterios.put(CriteriosBusquedaEnum.FUENTE_FINANCIAMIENTO, fuenteFinanciamiento);
		if (!esNulo(organismoFinanciador))  
			criterios.put(CriteriosBusquedaEnum.ORGANISMO_FINANCIADOR, organismoFinanciador);
		if (!esNulo(ambientes))  
			criterios.put(CriteriosBusquedaEnum.AMBIENTES, ambientes);
		if (!esNulo(area))  
			criterios.put(CriteriosBusquedaEnum.AREAS, area );
		if (!esNulo(tipoAsignacion))  
			criterios.put(CriteriosBusquedaEnum.TIPO_ASIGNACION, tipoAsignacion);
		if (!esNulo(estado))  
			criterios.put(CriteriosBusquedaEnum.ESTADO, estado);
		if (costoInferior != null && costoSuperior != null)  
			criterios.put(CriteriosBusquedaEnum.COSTO, new BigDecimal[]{costoInferior,costoSuperior} );
		if (!esNulo(fechaHistoricaInferior) && !esNulo(fechaHistoricaSuperior))  
			criterios.put(CriteriosBusquedaEnum.FECHA_HISTORICA, new Date[]{fechaHistoricaInferior,fechaHistoricaSuperior} );
		if (!esNulo(estadoUsoActivo))  
			criterios.put(CriteriosBusquedaEnum.ESTADO_USO_ACTIVO, estadoUsoActivo );
		if (!esNulo(tipoActualizacion))  
			criterios.put(CriteriosBusquedaEnum.TIPO_ACTUALIZACION, tipoActualizacion );
		if (!esNulo(gestion))  
			criterios.put(CriteriosBusquedaEnum.GESTION, gestion );
		if (!esNulo(proveedor))  
			criterios.put(CriteriosBusquedaEnum.PROVEEDOR, proveedor );
		if(!esNulo(usuarioAsignado)){
			criterios.put(CriteriosBusquedaEnum.USUARIO_ASIGNADO, usuarioAsignado );
		}
		criterios.put(CriteriosBusquedaEnum.SOLO_REVALUADOS, soloRevaluados);
		return criterios;
	}
	
	
	public String getCodigoActivo() {
		return codigoActivo;
	}

	
	private static  boolean esNulo(Object obj){
		
		if (obj != null) {
			if (obj instanceof String) {
				String atributo = (String) obj;
				return "".equals(atributo.trim())?true:false;
			} else if (obj instanceof Integer && (Integer)obj == 0) {
				return true;
			} else if (obj instanceof Integer && (Short)obj == 0) {
				return true;
			} else if (obj instanceof Integer && (Long)obj == 0) {
				return true;
			} else if (obj instanceof BigDecimal && BigDecimal.ZERO.compareTo((BigDecimal)obj) == 0) {
				return true;
			} else if ( obj instanceof String[] && ((String[]) obj).length == 0) {
				return true;
			} else if ( obj instanceof Integer[] && ((Integer[]) obj).length == 0) {
				return true;
			} else if ( obj instanceof Integer[] && ((Integer[]) obj).length == 0) {
				return true;
			}
			return false;
		}
		return true;
		
	}


	public String[] getPartidasPresupuestarias() {
		return partidasPresupuestarias;
	}


	public void setPartidasPresupuestarias(String[] partidasPresupuestarias) {
		this.partidasPresupuestarias = partidasPresupuestarias;
	}


	public String[] getCodigosContables() {
		return codigosContables;
	}


	public void setCodigosContables(String[] codigosContables) {
		this.codigosContables = codigosContables;
	}


	public String[] getFamilias() {
		return familias;
	}


	public void setFamilias(String[] familias) {
		this.familias = familias;
	}


	public String[] getSubfamilias() {
		return subfamilias;
	}


	public void setSubfamilias(String[] subfamilias) {
		this.subfamilias = subfamilias;
	}


	public String[] getCentroCosto() {
		return centroCosto;
	}


	public void setCentroCosto(String[] centroCosto) {
		this.centroCosto = centroCosto;
	}


	public String[] getFuenteFinanciamiento() {
		return fuenteFinanciamiento;
	}


	public void setFuenteFinanciamiento(String[] fuenteFinanciamiento) {
		this.fuenteFinanciamiento = fuenteFinanciamiento;
	}

	

	public String[] getOrganismoFinanciador() {
		return organismoFinanciador;
	}


	public void setOrganismoFinanciador(String[] organismoFinanciador) {
		this.organismoFinanciador = organismoFinanciador;
	}


	public Integer[] getAmbientes() {
		return ambientes;
	}


	public void setAmbientes(Integer[] ambientes) {
		this.ambientes = ambientes;
	}


	public Integer[] getArea() {
		return area;
	}


	public void setArea(Integer[] area) {
		this.area = area;
	}


	public String[] getTipoAsignacion() {
		return tipoAsignacion;
	}


	public void setTipoAsignacion(String[] tipoAsignacion) {
		this.tipoAsignacion = tipoAsignacion;
	}


	public String[] getEstado() {
		return estado;
	}


	public void setEstado(String[] estado) {
		this.estado = estado;
	}


	public BigDecimal getCostoInferior() {
		return costoInferior;
	}


	public void setCostoInferior(BigDecimal costoInferior) {
		this.costoInferior = costoInferior;
	}


	public BigDecimal getCostoSuperior() {
		return costoSuperior;
	}


	public void setCostoSuperior(BigDecimal costoSuperior) {
		this.costoSuperior = costoSuperior;
	}


	public Date getFechaHistoricaInferior() {
		return fechaHistoricaInferior;
	}


	public void setFechaHistoricaInferior(Date fechaHistoricaInferior) {
		this.fechaHistoricaInferior = fechaHistoricaInferior;
	}


	public Date getFechaHistoricaSuperior() {
		return fechaHistoricaSuperior;
	}


	public void setFechaHistoricaSuperior(Date fechaHistoricaSuperior) {
		this.fechaHistoricaSuperior = fechaHistoricaSuperior;
	}


	public String[] getEstadoUsoActivo() {
		return estadoUsoActivo;
	}


	public void setEstadoUsoActivo(String[] estadoUsoActivo) {
		this.estadoUsoActivo = estadoUsoActivo;
	}

	public Integer getGestion() {
		return gestion;
	}


	public void setGestion(Integer gestion) {
		this.gestion = gestion;
	}


	public Integer[] getProveedor() {
		return proveedor;
	}


	public void setProveedor(Integer[] proveedor) {
		this.proveedor = proveedor;
	}


	public void setCodigoActivo(String codigoActivo) {
		this.codigoActivo = codigoActivo;
	}


	public void setCriterios(HashMap<CriteriosBusquedaEnum, Object> criterios) {
		this.criterios = criterios;
	}


	public String[] getTipoActualizacion() {
		return tipoActualizacion;
	}


	public void setTipoActualizacion(String[] tipoActualizacion) {
		this.tipoActualizacion = tipoActualizacion;
	}


	public Integer[] getUsuarioAsignado() {
		return usuarioAsignado;
	}


	public void setUsuarioAsignado(Integer[] usuarioAsignado) {
		this.usuarioAsignado = usuarioAsignado;
	}


	public boolean isSoloRevaluados() {
		return soloRevaluados;
	}


	public void setSoloRevaluados(boolean soloRevaluados) {
		this.soloRevaluados = soloRevaluados;
	}


	@Override
	public String toString() {
		return "BuscadorAvanzadoCriterioVo [codigoActivo=" + codigoActivo
				+ ", partidasPresupuestarias="
				+ Arrays.toString(partidasPresupuestarias)
				+ ", codigosContables=" + Arrays.toString(codigosContables)
				+ ", familias=" + Arrays.toString(familias) + ", subfamilias="
				+ Arrays.toString(subfamilias) + ", centroCosto="
				+ Arrays.toString(centroCosto) + ", fuenteFinanciamiento="
				+ Arrays.toString(fuenteFinanciamiento)
				+ ", organismoFinanciador="
				+ Arrays.toString(organismoFinanciador) + ", ambientes="
				+ Arrays.toString(ambientes) + ", area="
				+ Arrays.toString(area) + ", tipoAsignacion="
				+ Arrays.toString(tipoAsignacion) + ", estado="
				+ Arrays.toString(estado) + ", costoInferior=" + costoInferior
				+ ", costoSuperior=" + costoSuperior
				+ ", fechaHistoricaInferior=" + fechaHistoricaInferior
				+ ", fechaHistoricaSuperior=" + fechaHistoricaSuperior
				+ ", estadoUsoActivo=" + Arrays.toString(estadoUsoActivo)
				+ ", tipoActualizacion=" + Arrays.toString(tipoActualizacion)
				+ ", gestion=" + gestion + ", proveedor="
				+ Arrays.toString(proveedor) + ", usuarioAsignado="
				+ Arrays.toString(usuarioAsignado) + ", soloRevaluados="
				+ soloRevaluados + "]";
	}
	
	
}
