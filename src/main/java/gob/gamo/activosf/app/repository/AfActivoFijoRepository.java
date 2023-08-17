package gob.gamo.activosf.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gob.gamo.activosf.app.domain.AfActivoFijo;

public interface AfActivoFijoRepository extends JpaRepository<AfActivoFijo, Integer> {
    @Query(value = "SELECT a FROM AfActivoFijo a "
    	    			+ " WHERE a.idNotaRecepcion.idNotaRecepcion = :idNotaRecepcion "
    	    			+ " AND a.estado = 'A'"
    	    			+ " ORDER BY a.correlativo ASC")
	public List<AfActivoFijo> findAllAfActivoFijoByNotaRecepcion(@Param("idNotaRecepcion") Integer idNotaRecepcion); 

  	@Query(value = "SELECT a.* "
    			+ " FROM safi.af_activo_fijo_hist a "
    			+ " WHERE a.cat_Estado_Activo_Fijo != 'PROREC' "
    			+ " AND a.gestion = :gestion"
    			+ " AND a.ultimo_gestion = true "
    			+ " AND a.estado = 'A' "
    			+ " ORDER BY a.correlativo ASC", nativeQuery = true)    
	public List<AfActivoFijo> findAllAfActivoFijoIngresado(Integer gestion) ;
    
   	@Query(value = "SELECT a.* "
    			+ " FROM safi.af_activo_fijo_hist a "
    			+ " WHERE a.cat_estado_activo_fijo != 'PROREC' "
    			+ " AND a.estado = 'A' "
    			+ " AND a.gestion = :gestion "
    			+ " AND a.ultimo_gestion = true "
    			+ " AND a.id_usuario_asignado = :idUsuario "
    			+ " ORDER BY a.fecha_actual DESC, a.correlativo DESC", nativeQuery = true)    
	public List<AfActivoFijo> findAllAfActivoFijoAsignadoPorIdUsuario(Integer gestion, Integer idUsuario) ;
    
    @Query(value = " SELECT a.* "
    			+ " FROM safi.af_activo_fijo_hist a "
    			+ " WHERE a.gestion = :gestion "
    			+ " AND a.estado = 'A' "
    			+ " AND a.ultimo_gestion = true "
    			+ " ORDER BY a.codigo_extendido", nativeQuery = true)
	public List<AfActivoFijo> findAllActivesByGestion(Integer gestion) ;
    
    @Query(value = "SELECT a.* "
    			+ " FROM safi.af_activo_fijo_hist a "
    			+ " WHERE a.id_activo_fijo = :idActivoFijo "
    			+ " AND a.gestion = :gestion "
    			+ " AND a.estado = 'A' "
    			+ " AND a.ultimo_gestion = true ", nativeQuery = true)
    public AfActivoFijo findByIdActivoFijoYGestion(Integer idActivoFijo, Integer gestion) ;
    
    @Query(value = "SELECT a.* "
    			+ " FROM safi.af_activo_fijo_hist a "
    			+ " WHERE a.codigo_ean = :codigoEan "
    			+ " AND a.gestion = :gestion " 
    			+ " AND a.ultimo_gestion = true "
    			+ " AND a.estado = 'A' ", nativeQuery = true)
	public AfActivoFijo findByCodigoEanActivoFijo(Integer gestion, String codigoEan) ;
    
    @Query(value = "SELECT a.* "
    			+ " FROM safi.af_activo_fijo_hist a "
    			+ " WHERE a.codigoExtendido = :codigoExtendido "
    			+ " AND a.gestion = :gestion "
    			+ " AND a.ultimo_gestion = true "
    			+ " AND a.estado = 'A'", nativeQuery = true)
	public AfActivoFijo findByCodigoExtendido(Integer gestion, String codigoExtendido);
	
    /*
    public Integer buscadorAvanzadoContar(HashMap<CriteriosBusquedaEnum, Object> criterios) {
    	QueryParams queryParams = buscadorAvanzadoQuery(criterios);
    	@Query(queryParams.getQueryForCount());
    	queryParams.populateParams(query);
    	return ((BigInteger) query.getSingleResult()).intValue();
    }
    
    
    public List<AfActivoFijo> buscadorAvanzadoBuscar(HashMap<CriteriosBusquedaEnum, Object> criterios, Integer limit, Integer offset) {
    	System.out.println("Criterios: " +  criterios);
    	QueryParams queryParams = buscadorAvanzadoQuery(criterios);
    	@Query(queryParams.getQueryForPagination(limit, offset), nativeQuery = true);
    	queryParams.populateParams(query);
    	return (List<AfActivoFijo>) query.getResultList();
    }
    
    private QueryParams buscadorAvanzadoQuery(HashMap<CriteriosBusquedaEnum, Object> criterios){
    	Map<String,Object> params = new HashMap<>();
    	String orderBy = "ORDER BY af.codigo_ean";
    	StringBuilder query = new StringBuilder("SELECT\n" + 
    			"   af.*\n" + 
    			"FROM\n" + 
    			"   safi.af_activo_fijo_hist af\n" + 
    			"   JOIN safi.af_ambiente amb ON af.id_ambiente = amb.id_ambiente\n" + 
    			"   LEFT JOIN safi.af_proveedor pro ON af.id_proveedor = pro.id_proveedor\n" + 
    			"   JOIN safi.af_sub_familia_activo sfa ON af.id_sub_familia = sfa.id_sub_familia\n" + 
    			"   JOIN safi.af_familia_activo fam ON sfa.id_familia_activo = fam.id_familia_activo\n" + 
    			"   JOIN safi.af_partida_presupuestaria pp ON fam.id_partida_presupuestaria = pp.id_partida_presupuestaria\n" + 
    			"   JOIN safi.af_codigo_contable cc ON cc.id_codigo_contable = fam.id_codigo_contable\n" +
    			"	JOIN seguridad.tx_usuario usu ON af.id_usuario_asignado = usu.id_usuario\n" + 
    			"   JOIN seguridad.tx_persona per ON usu.id_persona = per.id_persona\n" + 
    			"   JOIN seguridad.tx_cargo car ON per.id_cargo = car.id_cargo\n" + 
    			"   JOIN seguridad.tx_area ar ON ar.id_area = car.id_area\n"+
    			"WHERE \n" + 
    			"    af.estado = 'A' " +
    			" AND ultimo_gestion = true " +
    			" AND af.cat_estado_activo_fijo != 'PROREC' "
    			);
    	 if (criterios.containsKey(CriteriosBusquedaEnum.CODIGO_ACTIVO)) {
    		 query.append(" AND af.codigo_extendido LIKE(:codigoExtendido) ");
    		 params.put("codigoExtendido", QueryParams.prepareStringForLikeSufix( (String) criterios.get(CriteriosBusquedaEnum.CODIGO_ACTIVO)));
    	 }
    	 
    	 if (criterios.containsKey(CriteriosBusquedaEnum.USUARIO_ASIGNADO)) {
    		 query.append(" AND af.id_usuario_asignado IN " + QueryParams.prepareNumberArrayForIn((Integer[]) criterios.get(CriteriosBusquedaEnum.USUARIO_ASIGNADO)));
    	 }
    	 
    	 if (criterios.containsKey(CriteriosBusquedaEnum.PARTIDAS_PRESUPUESTARIAS)) {
    		 query.append(" AND pp.codigo IN " + QueryParams.prepareStringArrayForIn( (String[]) criterios.get(CriteriosBusquedaEnum.PARTIDAS_PRESUPUESTARIAS)));
    	 }
    	
    	 if (criterios.containsKey(CriteriosBusquedaEnum.CODIGOS_CONTABLES)) {
    		 query.append(" AND cc.codigo IN " + QueryParams.prepareStringArrayForIn( (String[]) criterios.get(CriteriosBusquedaEnum.CODIGOS_CONTABLES)));
    	 }
    	 
    	 if (criterios.containsKey(CriteriosBusquedaEnum.FAMILIAS)) {
    		 query.append(" AND fam.codigo IN " + QueryParams.prepareStringArrayForIn( (String[]) criterios.get(CriteriosBusquedaEnum.FAMILIAS)));
    	 }
    	 
    	 if (criterios.containsKey(CriteriosBusquedaEnum.SUB_FAMILIAS)) {
    		 query.append(" AND fam.codigo||'|'||sfa.codigo IN " + QueryParams.prepareStringArrayForIn( (String[]) criterios.get(CriteriosBusquedaEnum.SUB_FAMILIAS)));
    	 }
    	 
    	 if (criterios.containsKey(CriteriosBusquedaEnum.CENTRO_COSTO)) {
    		 query.append(" AND af.cat_centro_costo IN " + QueryParams.prepareStringArrayForIn( (String[]) criterios.get(CriteriosBusquedaEnum.CENTRO_COSTO)));
    	 }
    	 
    	 if (criterios.containsKey(CriteriosBusquedaEnum.FUENTE_FINANCIAMIENTO)) {
    		 query.append(" AND af.cat_fuente_financiamiento IN " + QueryParams.prepareStringArrayForIn( (String[]) criterios.get(CriteriosBusquedaEnum.FUENTE_FINANCIAMIENTO)));
    	 }
    	 
    	 if (criterios.containsKey(CriteriosBusquedaEnum.ORGANISMO_FINANCIADOR)) {
    		 query.append(" AND af.cat_organismo_financiador IN " + QueryParams.prepareStringArrayForIn( (String[]) criterios.get(CriteriosBusquedaEnum.ORGANISMO_FINANCIADOR)));
    	 }
    	 
    	 if (criterios.containsKey(CriteriosBusquedaEnum.AMBIENTES)) {
    		 query.append(" AND amb.id_ambiente IN " + QueryParams.prepareNumberArrayForIn((Integer[]) criterios.get(CriteriosBusquedaEnum.AMBIENTES)));
    	 }
    	 
    	 if (criterios.containsKey(CriteriosBusquedaEnum.TIPO_ASIGNACION)) {
    		 query.append(" AND af.cat_tipo_asignacion IN " + QueryParams.prepareStringArrayForIn((String[]) criterios.get(CriteriosBusquedaEnum.TIPO_ASIGNACION)));
    	 }
    	 
    	 if (criterios.containsKey(CriteriosBusquedaEnum.ESTADO)) {
    		 query.append(" AND af.cat_estado_activo_fijo IN " + QueryParams.prepareStringArrayForIn((String[]) criterios.get(CriteriosBusquedaEnum.ESTADO)));
    	 }

    	 if (criterios.containsKey(CriteriosBusquedaEnum.COSTO)) {
    		 query.append(" AND af.costo_historico BETWEEN :costoInicial AND :costoFinal ");
    		 BigDecimal[] costos = (BigDecimal[]) criterios.get(CriteriosBusquedaEnum.COSTO);
    		 params.put("costoInicial",costos[0]);
    		 params.put("costoFinal",costos[1]);
    	 }
    	 if (criterios.containsKey(CriteriosBusquedaEnum.FECHA_HISTORICA)) {
    		 query.append(" AND af.fecha_historico BETWEEN :fechaInicial AND :fechaFinal ");
    		 Date[] fechas = (Date[]) criterios.get(CriteriosBusquedaEnum.FECHA_HISTORICA);
    		 params.put("fechaInicial",fechas[0]);
    		 params.put("fechaFinal",fechas[1]);
    	 }
    	 
    	 if (criterios.containsKey(CriteriosBusquedaEnum.ESTADO_USO_ACTIVO)) {
    		 query.append(" AND af.cat_estado_uso IN " + QueryParams.prepareStringArrayForIn((String[]) criterios.get(CriteriosBusquedaEnum.ESTADO_USO_ACTIVO)));
    	 }
    	 if (criterios.containsKey(CriteriosBusquedaEnum.TIPO_ACTUALIZACION)) {
    		 query.append(" AND af.cat_tipo_actualizacion IN " + QueryParams.prepareStringArrayForIn((String[]) criterios.get(CriteriosBusquedaEnum.TIPO_ACTUALIZACION)));
    	 }
    	 if (criterios.containsKey(CriteriosBusquedaEnum.GESTION)) {
    		 query.append(" AND af.gestion = :gestion");
    		 params.put("gestion", criterios.get(CriteriosBusquedaEnum.GESTION));
    	 }
    	 if (criterios.containsKey(CriteriosBusquedaEnum.PROVEEDOR)) {
    		 query.append(" AND pro.id_proveedor IN " + QueryParams.prepareNumberArrayForIn((Integer[]) criterios.get(CriteriosBusquedaEnum.PROVEEDOR)));
    	 }
    	 if (criterios.containsKey(CriteriosBusquedaEnum.AREAS)) {
    		 query.append(" AND ar.id_area IN " + QueryParams.prepareNumberArrayForIn((Integer[]) criterios.get(CriteriosBusquedaEnum.AREAS)));
    	 }
    	 
    	 if (criterios.containsKey(CriteriosBusquedaEnum.SOLO_REVALUADOS)) {
    		 boolean soloRevaluados =(boolean) criterios.get(CriteriosBusquedaEnum.SOLO_REVALUADOS);
    		 if (soloRevaluados) {
    			 query.append(" AND af.revalorizado = :revaluado ");
    			 params.put("revaluado", soloRevaluados);
    		 }
    	 }
    	 
    	 System.out.println("Query: " + query);
    	 return new QueryParams(query, params, orderBy);
    }    
 */
}
