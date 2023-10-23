package gob.gamo.activosf.app.services;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.domain.AfActivoFijo;
import gob.gamo.activosf.app.domain.OrgEmpleado;
import gob.gamo.activosf.app.domain.OrgPersona;
import gob.gamo.activosf.app.domain.OrgUnidad;
import gob.gamo.activosf.app.domain.entities.GenDesctabla;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.CriteriosBusquedaEnum;
import gob.gamo.activosf.app.dto.QueryParams;
import gob.gamo.activosf.app.search.SearchCriteria;
import gob.gamo.activosf.app.search.SearchQueryCriteriaConsumer;
import gob.gamo.activosf.app.utils.PaginationUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class AfSearchService {
    @PersistenceContext
    private final EntityManager em;

    public Integer buscadorAvanzadoContar(HashMap<CriteriosBusquedaEnum, Object> criterios) {
        QueryParams queryParams = buscadorAvanzadoQuery(criterios);
        // @Query(queryParams.getQueryForCount());
        Query query = em.createNativeQuery(queryParams.getQueryForCount());
        queryParams.populateParams(query);
        return ((BigInteger) query.getSingleResult()).intValue();
    }

    public List<AfActivoFijo> buscadorAvanzadoBuscar(
            HashMap<CriteriosBusquedaEnum, Object> criterios, Integer limit, Integer offset) {
        System.out.println("Criterios: " + criterios);
        QueryParams queryParams = buscadorAvanzadoQuery(criterios);
        // @Query(queryParams.getQueryForPagination(limit, offset));
        Query query = em.createNativeQuery(queryParams.getQueryForPagination(limit, offset), AfActivoFijo.class);
        queryParams.populateParams(query);
        return (List<AfActivoFijo>) query.getResultList();
    }

    private QueryParams buscadorAvanzadoQuery(HashMap<CriteriosBusquedaEnum, Object> criterios) {
        Map<String, Object> params = new HashMap<>();
        String orderBy = "ORDER BY af.codigo_ean";
        StringBuilder query = new StringBuilder("SELECT\n" + "   af.*\n"
                + "FROM\n"
                + "   safi.af_activo_fijo_hist af\n"
                + "   JOIN safi.af_ambiente amb ON af.id_ambiente = amb.id_ambiente\n"
                + "   LEFT JOIN safi.af_proveedor pro ON af.id_proveedor = pro.id_proveedor\n"
                + "   JOIN safi.af_sub_familia_activo sfa ON af.id_sub_familia = sfa.id_sub_familia\n"
                + "   JOIN safi.af_familia_activo fam ON sfa.id_familia_activo = fam.id_familia_activo\n"
                + "   JOIN safi.af_partida_presupuestaria pp ON fam.id_partida_presupuestaria = pp.id_partida_presupuestaria\n"
                + "   JOIN safi.af_codigo_contable cc ON cc.id_codigo_contable = fam.id_codigo_contable\n"
                + "	JOIN seguridad.tx_usuario usu ON af.id_usuario_asignado = usu.id_usuario\n"
                + "   JOIN seguridad.tx_persona per ON usu.id_persona = per.id_persona\n"
                + "   JOIN seguridad.tx_cargo car ON per.id_cargo = car.id_cargo\n"
                + "   JOIN seguridad.tx_area ar ON ar.id_area = car.id_area\n"
                + "WHERE \n"
                + "    af.estado = 'A' "
                + " AND ultimo_gestion = true "
                + " AND af.cat_estado_activo_fijo != 'PROREC' ");
        if (criterios.containsKey(CriteriosBusquedaEnum.CODIGO_ACTIVO)) {
            query.append(" AND af.codigo_extendido LIKE(:codigoExtendido) ");
            params.put("codigoExtendido", QueryParams.prepareStringForLikeSufix((String)
                    criterios.get(CriteriosBusquedaEnum.CODIGO_ACTIVO)));
        }

        if (criterios.containsKey(CriteriosBusquedaEnum.USUARIO_ASIGNADO)) {
            query.append(" AND af.id_usuario_asignado IN "
                    + QueryParams.prepareNumberArrayForIn(
                            (Integer[]) criterios.get(CriteriosBusquedaEnum.USUARIO_ASIGNADO)));
        }

        if (criterios.containsKey(CriteriosBusquedaEnum.PARTIDAS_PRESUPUESTARIAS)) {
            query.append(" AND pp.codigo IN "
                    + QueryParams.prepareStringArrayForIn(
                            (String[]) criterios.get(CriteriosBusquedaEnum.PARTIDAS_PRESUPUESTARIAS)));
        }

        if (criterios.containsKey(CriteriosBusquedaEnum.CODIGOS_CONTABLES)) {
            query.append(" AND cc.codigo IN "
                    + QueryParams.prepareStringArrayForIn(
                            (String[]) criterios.get(CriteriosBusquedaEnum.CODIGOS_CONTABLES)));
        }

        if (criterios.containsKey(CriteriosBusquedaEnum.FAMILIAS)) {
            query.append(" AND fam.codigo IN "
                    + QueryParams.prepareStringArrayForIn((String[]) criterios.get(CriteriosBusquedaEnum.FAMILIAS)));
        }

        if (criterios.containsKey(CriteriosBusquedaEnum.SUB_FAMILIAS)) {
            query.append(" AND fam.codigo||'|'||sfa.codigo IN "
                    + QueryParams.prepareStringArrayForIn(
                            (String[]) criterios.get(CriteriosBusquedaEnum.SUB_FAMILIAS)));
        }

        if (criterios.containsKey(CriteriosBusquedaEnum.CENTRO_COSTO)) {
            query.append(" AND af.cat_centro_costo IN "
                    + QueryParams.prepareStringArrayForIn(
                            (String[]) criterios.get(CriteriosBusquedaEnum.CENTRO_COSTO)));
        }

        if (criterios.containsKey(CriteriosBusquedaEnum.FUENTE_FINANCIAMIENTO)) {
            query.append(" AND af.cat_fuente_financiamiento IN "
                    + QueryParams.prepareStringArrayForIn(
                            (String[]) criterios.get(CriteriosBusquedaEnum.FUENTE_FINANCIAMIENTO)));
        }

        if (criterios.containsKey(CriteriosBusquedaEnum.ORGANISMO_FINANCIADOR)) {
            query.append(" AND af.cat_organismo_financiador IN "
                    + QueryParams.prepareStringArrayForIn(
                            (String[]) criterios.get(CriteriosBusquedaEnum.ORGANISMO_FINANCIADOR)));
        }

        if (criterios.containsKey(CriteriosBusquedaEnum.AMBIENTES)) {
            query.append(" AND amb.id_ambiente IN "
                    + QueryParams.prepareNumberArrayForIn((Integer[]) criterios.get(CriteriosBusquedaEnum.AMBIENTES)));
        }

        if (criterios.containsKey(CriteriosBusquedaEnum.TIPO_ASIGNACION)) {
            query.append(" AND af.cat_tipo_asignacion IN "
                    + QueryParams.prepareStringArrayForIn(
                            (String[]) criterios.get(CriteriosBusquedaEnum.TIPO_ASIGNACION)));
        }

        if (criterios.containsKey(CriteriosBusquedaEnum.ESTADO)) {
            query.append(" AND af.cat_estado_activo_fijo IN "
                    + QueryParams.prepareStringArrayForIn((String[]) criterios.get(CriteriosBusquedaEnum.ESTADO)));
        }

        if (criterios.containsKey(CriteriosBusquedaEnum.COSTO)) {
            query.append(" AND af.costo_historico BETWEEN :costoInicial AND :costoFinal ");
            BigDecimal[] costos = (BigDecimal[]) criterios.get(CriteriosBusquedaEnum.COSTO);
            params.put("costoInicial", costos[0]);
            params.put("costoFinal", costos[1]);
        }
        if (criterios.containsKey(CriteriosBusquedaEnum.FECHA_HISTORICA)) {
            query.append(" AND af.fecha_historico BETWEEN :fechaInicial AND :fechaFinal ");
            Date[] fechas = (Date[]) criterios.get(CriteriosBusquedaEnum.FECHA_HISTORICA);
            params.put("fechaInicial", fechas[0]);
            params.put("fechaFinal", fechas[1]);
        }

        if (criterios.containsKey(CriteriosBusquedaEnum.ESTADO_USO_ACTIVO)) {
            query.append(" AND af.cat_estado_uso IN "
                    + QueryParams.prepareStringArrayForIn(
                            (String[]) criterios.get(CriteriosBusquedaEnum.ESTADO_USO_ACTIVO)));
        }
        if (criterios.containsKey(CriteriosBusquedaEnum.TIPO_ACTUALIZACION)) {
            query.append(" AND af.cat_tipo_actualizacion IN "
                    + QueryParams.prepareStringArrayForIn(
                            (String[]) criterios.get(CriteriosBusquedaEnum.TIPO_ACTUALIZACION)));
        }
        if (criterios.containsKey(CriteriosBusquedaEnum.GESTION)) {
            query.append(" AND af.gestion = :gestion");
            params.put("gestion", criterios.get(CriteriosBusquedaEnum.GESTION));
        }
        if (criterios.containsKey(CriteriosBusquedaEnum.PROVEEDOR)) {
            query.append(" AND pro.id_proveedor IN "
                    + QueryParams.prepareNumberArrayForIn((Integer[]) criterios.get(CriteriosBusquedaEnum.PROVEEDOR)));
        }
        if (criterios.containsKey(CriteriosBusquedaEnum.AREAS)) {
            query.append(" AND ar.id_area IN "
                    + QueryParams.prepareNumberArrayForIn((Integer[]) criterios.get(CriteriosBusquedaEnum.AREAS)));
        }

        if (criterios.containsKey(CriteriosBusquedaEnum.SOLO_REVALUADOS)) {
            boolean soloRevaluados = (boolean) criterios.get(CriteriosBusquedaEnum.SOLO_REVALUADOS);
            if (soloRevaluados) {
                query.append(" AND af.revalorizado = :revaluado ");
                params.put("revaluado", soloRevaluados);
            }
        }

        System.out.println("Query: " + query);
        return new QueryParams(query, params, orderBy);
    }

    public Page<OrgPersona> searchPersona(SearchCriteria params, Pageable pageable) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<OrgPersona> root = query.from(OrgPersona.class);
        root.alias("persona");

        Boolean existEmpleado = findInSearchCriteria(params, "empleado.");
        Boolean existUsuario = findInSearchCriteria(params, "usuario.");
        Boolean existTipopers = findInSearchCriteria(params, "tipopers.");

        if (existEmpleado) {
            Join<OrgPersona, OrgEmpleado> j = root.join("empleos", JoinType.INNER);
            j.alias("empleado");
        }
        if (existUsuario) {
            Join<OrgPersona, OrgEmpleado> j = root.join("usersist", JoinType.INNER);
            j.alias("usuario");
        }

        if (existTipopers) {
            Join<OrgPersona, GenDesctabla> j = root.join("tipopersdesc", JoinType.INNER);
            j.alias("tipopers");
        }

        List<Tuple> listT = generateQuery(builder, root, query, params, pageable, root);

        List<OrgPersona> result = listT.stream()
                .map(r -> {
                    OrgPersona e = (OrgPersona) r.get(0);
                    return e;
                })
                .map((x) -> x)
                .toList();

        int total = countAll(builder, query, root);
        Page<OrgPersona> page =
                PaginationUtil.pageForList((int) pageable.getPageNumber(), pageable.getPageSize(), total, result);

        return page;
    }

    public Page<OrgEmpleado> searchEmpleados(SearchCriteria params, Pageable pageable) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        final Root<OrgEmpleado> root = query.from(OrgEmpleado.class);
        root.alias("empleado");

        Boolean existPersona = findInSearchCriteria(params, "persona.");
        Boolean existUnidad = findInSearchCriteria(params, "unidad.");
        Boolean existCargo = findInSearchCriteria(params, "rolempleado.");

        if (existPersona) {
            Join<OrgEmpleado, OrgPersona> j = root.join("persona", JoinType.INNER);
            j.alias("persona");
        }

        if (existUnidad) {
            Join<OrgEmpleado, OrgUnidad> j = root.join("unidad", JoinType.INNER);
            j.alias("unidad");
        }

        if (existCargo) {
            Join<OrgEmpleado, GenDesctabla> j = root.join("rolempleadodesc", JoinType.INNER);
            j.alias("rolempleado");
        }

        List<Tuple> listT = generateQuery(builder, root, query, params, pageable, root);

        List<OrgEmpleado> result =
                listT.stream().map(r -> (OrgEmpleado) r.get(0)).toList();
        int total = countAll(builder, query, root);

        return PaginationUtil.pageForList((int) pageable.getPageNumber(), pageable.getPageSize(), total, result);
    }

    public Page<User> searchUsuarios(SearchCriteria params, Pageable pageable) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        final Root<User> root = query.from(User.class);
        root.alias("usuario");

        Boolean existPersona = findInSearchCriteria(params, "persona.");

        if (existPersona) {
            Join<User, OrgPersona> j = root.join("persona", JoinType.INNER);
            j.alias("persona");
        }

        List<Tuple> listT = generateQuery(builder, root, query, params, pageable, root);

        List<User> result = listT.stream().map(r -> (User) r.get(0)).toList();
        int total = countAll(builder, query, root);

        return PaginationUtil.pageForList((int) pageable.getPageNumber(), pageable.getPageSize(), total, result);
    }

    public Page<OrgUnidad> searchUnidades(SearchCriteria params, Pageable pageable) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        final Root<OrgUnidad> root = query.from(OrgUnidad.class);
        root.alias("unidad");

        List<Tuple> listT = generateQuery(builder, root, query, params, pageable, root);

        List<OrgUnidad> result = listT.stream().map(r -> (OrgUnidad) r.get(0)).toList();
        int total = countAll(builder, query, root);

        return PaginationUtil.pageForList((int) pageable.getPageNumber(), pageable.getPageSize(), total, result);
    }

    public List<Tuple> generateQuery(
            CriteriaBuilder builder,
            Root<?> root,
            CriteriaQuery<Tuple> query,
            SearchCriteria params,
            Pageable pageable,
            Selection<?>... selections) {

        SearchQueryCriteriaConsumer<?> searchConsumer = new SearchQueryCriteriaConsumer<>(null, builder, root);

        searchConsumer.accept(params);

        query.multiselect(selections); //
        query.where(searchConsumer.getPredicates().toArray(new Predicate[0]));

        List<Order> sorts = orders(builder, root, pageable);
        if (sorts.size() > 0) query.orderBy(sorts);

        List<Tuple> l = em.createQuery(query)
                .setMaxResults(pageable.getPageSize())
                .setFirstResult((int) pageable.getOffset())
                .getResultList();

        return l;
    }

    public List<Order> orders(CriteriaBuilder builder, Root<?> root, Pageable pageable) {
        List<Order> o = new ArrayList<>();
        if (pageable.getSort() != null) {
            for (org.springframework.data.domain.Sort.Order sort : pageable.getSort()) {
                int idx = sort.getProperty().indexOf('.');
                boolean isJoin = idx > 0;
                String alias = isJoin ? sort.getProperty().substring(0, idx) : sort.getProperty();
                String attrib = isJoin ? sort.getProperty().substring(idx + 1) : sort.getProperty();
                Expression<String> path = null;
                // log.info("Order tab: {} col [{}] -> {} {}", alias, attrib,
                // sort.getProperty(), sort.getDirection());
                if (root.getAlias() != null && alias.equalsIgnoreCase(root.getAlias()) || !isJoin) {
                    path = root.get(attrib);
                } else {

                    for (Join<?, ?> j : root.getJoins()) {
                        // log.info("Order attrib[{}] -> alias: {} {}", attrib, j.getAlias(),
                        // j.getAttribute().getName());
                        if (j.getAlias() != null && alias.equalsIgnoreCase(j.getAlias())
                                || sort.getProperty()
                                        .toLowerCase()
                                        .startsWith(j.getAttribute().getName().toLowerCase())) {
                            path = j.get(attrib);
                            break;
                        }
                    }
                }
                if (sort.isDescending()) {
                    Order order = builder.desc(path);
                    o.add(order);
                } else {
                    Order order = builder.asc(path);
                    o.add(order);
                }
            }
        }
        return o;
    }

    public int countAll(CriteriaBuilder builder, CriteriaQuery<Tuple> query, Root<?> root) {
        query.multiselect(builder.count(root));
        query.orderBy();
        Tuple result = em.createQuery(query).getSingleResult();
        long count = (Long) result.get(0);
        int countR = (int) count;
        return countR;
    }

    public static boolean findInSearchCriteria(SearchCriteria sc, String key) {
        if (sc == null) {
            return false;
        }
        if (sc.getKey().startsWith(key)) {
            return true;
        } else {
            for (SearchCriteria s : sc.getChildren()) {
                boolean exists = findInSearchCriteria(s, key);
                if (exists) return true;
            }
        }

        return false;
    }
}
