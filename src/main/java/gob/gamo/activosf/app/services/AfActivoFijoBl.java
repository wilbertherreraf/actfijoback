package gob.gamo.activosf.app.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import gob.gamo.activosf.app.domain.AfAccesorioActivoFijo;
import gob.gamo.activosf.app.domain.AfActivoFijo;
import gob.gamo.activosf.app.domain.AfAtributoActivoFijo;
import gob.gamo.activosf.app.domain.AfBajaActivoFijo;
import gob.gamo.activosf.app.domain.AfComponenteActivoFijo;
import gob.gamo.activosf.app.domain.AfImagenActivoFijo;
import gob.gamo.activosf.app.domain.AfRevaluoActivoFijo;
import gob.gamo.activosf.app.domain.AfTipoCambio;
import gob.gamo.activosf.app.domain.AfTransferenciaAsignacion;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.domain.entities.GenDesctabla;
import gob.gamo.activosf.app.dto.ActivoFijoRfidVo;
import gob.gamo.activosf.app.dto.AgrupadorReporteVo;
import gob.gamo.activosf.app.dto.CriteriosBusquedaEnum;
import gob.gamo.activosf.app.dto.ItemReporteVo;
import gob.gamo.activosf.app.dto.ReporteActivoFijoEnum;
import gob.gamo.activosf.app.dto.ReporteContableEnum;
import gob.gamo.activosf.app.dto.StatusEnum;
import gob.gamo.activosf.app.dto.UnidadResponse;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.AfActivoFijoRepository;
import gob.gamo.activosf.app.repository.AfRevaluoActivoFijoRepository;
import gob.gamo.activosf.app.repository.GenDesctablaRespository;

/**
 *
 * @author wherrera
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AfActivoFijoBl {

    private final AfActivoFijoRepository afActivoFijoRepository;

    private final TxTransaccionBl txTransaccionBl;

    private final AfTipoCambioBl afTipoCambioBl;

    private final AfActivoFijoBlAsync afActivoFijoBlAsync;

    private final AfRevaluoActivoFijoRepository afRevaluoActivoFijoRepository;
    private final AfSearchService afSearchService;
    private final GenDesctablaRespository genDesctablaRespository;

    @Transactional(readOnly = true)
    public Page<AfActivoFijo> findAll(Pageable pageable) {
        Page<AfActivoFijo> list = afActivoFijoRepository.findAll(pageable);
        return list;
    }
    public Integer buscadorAvanzadoContar(HashMap<CriteriosBusquedaEnum, Object> criterios) {
        return afSearchService.buscadorAvanzadoContar(criterios);
    }

    public List<AfActivoFijo> buscadorAvanzadoBuscar(
            HashMap<CriteriosBusquedaEnum, Object> criterios, Integer limit, Integer offset, String[] lazy) {
        List<AfActivoFijo> result = afSearchService.buscadorAvanzadoBuscar(criterios, limit, offset);
        // LazyLoadingUtil.loadCollection(result, lazy);
        return result;
    }

    public List<AgrupadorReporteVo> getReporteContablePorCriterios(
            ReporteContableEnum reporte, HashMap<CriteriosBusquedaEnum, Object> criterios, Date fechaCalculoContable) {
        List<AgrupadorReporteVo> result = new ArrayList<>();
        switch (reporte) {
            case INVENTARIO_ORDENADO_CODIGO_ACTIVO:
            case INVENTARIO_ORDENADO_CODIGO_ACTIVO_REVALUO: {
                getDataInventario(criterios, fechaCalculoContable, result, true);
                break;
            }
            case INVENTARIO_AGRUPADO_RESPONSABLE: {
                getDataInventarioAgrupadoPorResponsable(criterios, result, fechaCalculoContable, true);
                break;
            }
            case INVENTARIO_AGRUPADO_POR_FAMILIA: {
                getDataInventarioAgrupadoPorFamilia(criterios, fechaCalculoContable, result, true);
                break;
            }

            case INVENTARIO_AGRUPADO_POR_CODIGO_CONTABLE: {
                getDataInventarioAgrupadoPorCodigoContable(criterios, fechaCalculoContable, result, true);
                break;
            }
            case INVENTARIO_AGRUPADO_POR_PARTIDA_PRESUPUESTARIA: {
                getDataInventarioAgrupadoPorPartidaPresupuestaria(criterios, fechaCalculoContable, result, true);
                break;
            }
            case INVENTARIO_AGRUPADO_POR_SUBFAMILIA: {
                getDataInventarioAgrupadoPorSubfamilia(criterios, fechaCalculoContable, result, true);
                break;
            }

            case AGRUPADO_POR_FAMILIA_REVALUO:
            case AGRUPADO_POR_FAMILIA: {
                Map<String, ItemReporteVo> agrupadoFamilia = new HashMap<String, ItemReporteVo>();
                String[] lazy = {"idSubFamilia"};
                List<AfActivoFijo> activosFijos = buscadorAvanzadoBuscar(criterios, null, null, lazy);
                // Map<TupleVo<String, String>, CnfValor> catalogos = cnfCatalogoBl.getMapCnfValor();

                realizarCalculosContablesListAsync(activosFijos, fechaCalculoContable);
                for (AfActivoFijo afActivoFijo : activosFijos) {
                    ItemReporteVo activoFijo = new ItemReporteVo();
                    construirItemReporteVoGeneral(activoFijo, afActivoFijo);
                    construirItemReporteVoContable(activoFijo, afActivoFijo);

                    ItemReporteVo familiaAgrupada = agrupadoFamilia.get(activoFijo.getFamilia());
                    if (familiaAgrupada == null) {
                        familiaAgrupada = new ItemReporteVo();
                        familiaAgrupada.setDescripcion(activoFijo.getFamilia());
                        familiaAgrupada.setVidaUtil(
                                afActivoFijo.getCalculoContableVo().getAniosVidaUtil());
                    }
                    familiaAgrupada.agruparItemReporteVo(activoFijo);
                    agrupadoFamilia.put(activoFijo.getFamilia(), familiaAgrupada);
                }
                AgrupadorReporteVo agrupador = new AgrupadorReporteVo();
                agrupador.setFechaCalculo(fechaCalculoContable);
                agrupador.setTipoCambioFechaCalculo(afTipoCambioBl
                        .getAfTipoCambioByCatMonedaAndFecha("UFV", fechaCalculoContable)
                        .getCambio());
                List<ItemReporteVo> items = new ArrayList<ItemReporteVo>();
                items.addAll(agrupadoFamilia.values());

                Collections.sort(items, new Comparator<ItemReporteVo>() {

                    public int compare(ItemReporteVo o1, ItemReporteVo o2) {
                        int result = o1.getDescripcion().compareTo(o2.getDescripcion());
                        return result;
                    }
                });
                agrupador.setItems(items);
                result.add(agrupador);
                break;
            }
        }
        return result;
    }

    public List<AgrupadorReporteVo> getReporteActivosFijosPorCriterios(
            ReporteActivoFijoEnum reporte, HashMap<CriteriosBusquedaEnum, Object> criterios) {
        List<AgrupadorReporteVo> result = new ArrayList<>();
        Date fechaReporte = new Date();
        switch (reporte) {
            case INVENTARIO_ORDENADO_CODIGO_ACTIVO: {
                getDataInventario(criterios, fechaReporte, result, false);
                break;
            }
            case INVENTARIO_AGRUPADO_RESPONSABLE: {
                getDataInventarioAgrupadoPorResponsable(criterios, result, fechaReporte, false);
                break;
            }
            case INVENTARIO_AGRUPADO_POR_FAMILIA: {
                getDataInventarioAgrupadoPorFamilia(criterios, fechaReporte, result, false);
                break;
            }
            case INVENTARIO_AGRUPADO_POR_CODIGO_CONTABLE: {
                getDataInventarioAgrupadoPorCodigoContable(criterios, fechaReporte, result, false);
            }
            case INVENTARIO_AGRUPADO_POR_PARTIDA_PRESUPUESTARIA: {
                getDataInventarioAgrupadoPorPartidaPresupuestaria(criterios, fechaReporte, result, false);
            }
            case INVENTARIO_AGRUPADO_POR_SUBFAMILIA: {
                getDataInventarioAgrupadoPorSubfamilia(criterios, fechaReporte, result, false);
                break;
            }
        }
        return result;
    }

    private void getDataInventarioAgrupadoPorResponsable(
            HashMap<CriteriosBusquedaEnum, Object> criterios,
            List<AgrupadorReporteVo> result,
            Date fechaReporte,
            boolean contable) {
        Map<String, List<ItemReporteVo>> responsablesMap = new HashMap<String, List<ItemReporteVo>>();
        String[] lazy = {"idUsuarioAsignado"};
        List<AfActivoFijo> activosFijos = buscadorAvanzadoBuscar(criterios, null, null, lazy);

        if (contable) {
            realizarCalculosContablesListAsync(activosFijos, fechaReporte);
        }
        for (AfActivoFijo afActivoFijo : activosFijos) {
            ItemReporteVo item = new ItemReporteVo();
            construirItemReporteVoGeneral(item, afActivoFijo);
            if (contable) {
                construirItemReporteVoContable(item, afActivoFijo);
            }
            List<ItemReporteVo> responsableList = responsablesMap.get(item.getResponsable());
            if (responsableList == null) {
                responsableList = new ArrayList<>();
                responsablesMap.put(item.getResponsable(), responsableList);
            }
            responsableList.add(item);
        }
        for (String key : responsablesMap.keySet()) {
            Collections.sort(responsablesMap.get(key), new Comparator<ItemReporteVo>() {
                // ojoooooo cambiar sort
                public int compare(ItemReporteVo o1, ItemReporteVo o2) {
                    return o1.getCodigo().compareTo(o2.getCodigo());
                }
            });
            AgrupadorReporteVo agrupador = new AgrupadorReporteVo();
            agrupador.setAsignado(key);
            agrupador.setFechaCalculo(fechaReporte);
            if (contable) {
                agrupador.setTipoCambioFechaCalculo(afTipoCambioBl
                        .getAfTipoCambioByCatMonedaAndFecha("UFV", fechaReporte)
                        .getCambio());
            }
            agrupador.setItems(responsablesMap.get(key));
            result.add(agrupador);
        }
        Collections.sort(result, new Comparator<AgrupadorReporteVo>() {

            public int compare(AgrupadorReporteVo o1, AgrupadorReporteVo o2) {
                return o1.getAsignado().compareTo(o2.getAsignado());
            }
        });
    }

    private void getDataInventarioAgrupadoPorSubfamilia(
            HashMap<CriteriosBusquedaEnum, Object> criterios,
            Date fechaCalculoContable,
            List<AgrupadorReporteVo> result,
            boolean contable) {
        Map<String, List<ItemReporteVo>> subfamiliasMap = new HashMap<String, List<ItemReporteVo>>();
        String[] lazy = {"idSubFamilia"};
        List<AfActivoFijo> activosFijos = buscadorAvanzadoBuscar(criterios, null, null, lazy);
        if (contable) {
            realizarCalculosContablesListAsync(activosFijos, fechaCalculoContable);
        }
        for (AfActivoFijo afActivoFijo : activosFijos) {
            ItemReporteVo item = new ItemReporteVo();
            construirItemReporteVoGeneral(item, afActivoFijo);
            if (contable) {
                construirItemReporteVoContable(item, afActivoFijo);
            }
            List<ItemReporteVo> subfamiliaList = subfamiliasMap.get(item.getSubfamilia());
            if (subfamiliaList == null) {
                subfamiliaList = new ArrayList<>();
                subfamiliasMap.put(item.getSubfamilia(), subfamiliaList);
            }
            subfamiliaList.add(item);
        }
        for (String key : subfamiliasMap.keySet()) {
            Collections.sort(subfamiliasMap.get(key), new Comparator<ItemReporteVo>() {

                public int compare(ItemReporteVo o1, ItemReporteVo o2) {
                    return o1.getCodigo().compareTo(o2.getCodigo());
                }
            });
            AgrupadorReporteVo agrupador = new AgrupadorReporteVo();
            agrupador.setFamilia(subfamiliasMap.get(key).get(0).getFamilia());
            agrupador.setSubfamilia(key);
            agrupador.setFechaCalculo(fechaCalculoContable);
            if (contable) {
                agrupador.setTipoCambioFechaCalculo(afTipoCambioBl
                        .getAfTipoCambioByCatMonedaAndFecha("UFV", fechaCalculoContable)
                        .getCambio());
            }
            agrupador.setItems(subfamiliasMap.get(key));
            result.add(agrupador);
        }
        Collections.sort(result, new Comparator<AgrupadorReporteVo>() {

            public int compare(AgrupadorReporteVo o1, AgrupadorReporteVo o2) {
                int result = o1.getFamilia().compareTo(o2.getFamilia());
                if (result == 0) {
                    return o1.getSubfamilia().compareTo(o2.getSubfamilia());
                }
                return result;
            }
        });
    }

    private void getDataInventario(
            HashMap<CriteriosBusquedaEnum, Object> criterios,
            Date fechaCalculoContable,
            List<AgrupadorReporteVo> result,
            boolean contable) {
        List<ItemReporteVo> items = new ArrayList<>();
        List<AfActivoFijo> activosFijos = buscadorAvanzadoBuscar(criterios, null, null, null);

        if (contable) {
            realizarCalculosContablesListAsync(activosFijos, fechaCalculoContable);
        }

        for (AfActivoFijo afActivoFijo : activosFijos) {
            ItemReporteVo item = new ItemReporteVo();
            construirItemReporteVoGeneral(item, afActivoFijo);
            if (contable) {
                construirItemReporteVoContable(item, afActivoFijo);
            }
            items.add(item);
        }
        Collections.sort(items, new Comparator<ItemReporteVo>() {

            public int compare(ItemReporteVo o1, ItemReporteVo o2) {
                return o1.getCodigo().compareTo(o2.getCodigo()); // Se asume que los codigos nunca seran nulos
            }
        });
        AgrupadorReporteVo agrupador = new AgrupadorReporteVo();
        agrupador.setFechaCalculo(fechaCalculoContable);
        if (contable) {
            agrupador.setTipoCambioFechaCalculo(afTipoCambioBl
                    .getAfTipoCambioByCatMonedaAndFecha("UFV", fechaCalculoContable)
                    .getCambio());
        }
        agrupador.setItems(items);
        result.add(agrupador);
    }

    private void getDataInventarioAgrupadoPorPartidaPresupuestaria(
            HashMap<CriteriosBusquedaEnum, Object> criterios,
            Date fechaCalculoContable,
            List<AgrupadorReporteVo> result,
            boolean contable) {
        Map<String, List<ItemReporteVo>> partidaPresupuestariaMap = new HashMap<String, List<ItemReporteVo>>();
        String[] lazy = {"idSubFamilia"};
        List<AfActivoFijo> activosFijos = buscadorAvanzadoBuscar(criterios, null, null, lazy);

        if (contable) {
            realizarCalculosContablesListAsync(activosFijos, fechaCalculoContable);
        }
        for (AfActivoFijo afActivoFijo : activosFijos) {
            ItemReporteVo item = new ItemReporteVo();
            construirItemReporteVoGeneral(item, afActivoFijo);
            if (contable) {
                construirItemReporteVoContable(item, afActivoFijo);
            }
            List<ItemReporteVo> partidaPresupuestariaList =
                    partidaPresupuestariaMap.get(item.getPartidaPresupuestaria());
            if (partidaPresupuestariaList == null) {
                partidaPresupuestariaList = new ArrayList<>();
                partidaPresupuestariaMap.put(item.getPartidaPresupuestaria(), partidaPresupuestariaList);
            }
            partidaPresupuestariaList.add(item);
        }
        for (String key : partidaPresupuestariaMap.keySet()) {
            Collections.sort(partidaPresupuestariaMap.get(key), new Comparator<ItemReporteVo>() {

                public int compare(ItemReporteVo o1, ItemReporteVo o2) {
                    return o1.getCodigo().compareTo(o2.getCodigo());
                }
            });
            AgrupadorReporteVo agrupador = new AgrupadorReporteVo();
            agrupador.setPartidaPresupuestaria(key);
            agrupador.setFechaCalculo(fechaCalculoContable);
            if (contable) {
                agrupador.setTipoCambioFechaCalculo(afTipoCambioBl
                        .getAfTipoCambioByCatMonedaAndFecha("UFV", fechaCalculoContable)
                        .getCambio());
            }
            agrupador.setItems(partidaPresupuestariaMap.get(key));
            result.add(agrupador);
        }
        Collections.sort(result, new Comparator<AgrupadorReporteVo>() {

            public int compare(AgrupadorReporteVo o1, AgrupadorReporteVo o2) {
                return o1.getPartidaPresupuestaria().compareTo(o2.getPartidaPresupuestaria());
            }
        });
    }

    private void getDataInventarioAgrupadoPorFamilia(
            HashMap<CriteriosBusquedaEnum, Object> criterios,
            Date fechaCalculoContable,
            List<AgrupadorReporteVo> result,
            boolean contable) {
        Map<String, List<ItemReporteVo>> familiasMap = new HashMap<String, List<ItemReporteVo>>();
        String[] lazy = {"idSubFamilia"};
        List<AfActivoFijo> activosFijos = buscadorAvanzadoBuscar(criterios, null, null, lazy);
        if (contable) {
            realizarCalculosContablesListAsync(activosFijos, fechaCalculoContable);
        }
        for (AfActivoFijo afActivoFijo : activosFijos) {
            ItemReporteVo item = new ItemReporteVo();
            construirItemReporteVoGeneral(item, afActivoFijo);
            if (contable) {
                construirItemReporteVoContable(item, afActivoFijo);
            }
            List<ItemReporteVo> familiaList = familiasMap.get(item.getFamilia());
            if (familiaList == null) {
                familiaList = new ArrayList<>();
                familiasMap.put(item.getFamilia(), familiaList);
            }
            familiaList.add(item);
        }
        for (String key : familiasMap.keySet()) {
            Collections.sort(familiasMap.get(key), new Comparator<ItemReporteVo>() {

                public int compare(ItemReporteVo o1, ItemReporteVo o2) {
                    return o1.getCodigo().compareTo(o2.getCodigo());
                }
            });
            AgrupadorReporteVo agrupador = new AgrupadorReporteVo();
            agrupador.setFamilia(key);
            agrupador.setFechaCalculo(fechaCalculoContable);
            if (contable) {
                agrupador.setTipoCambioFechaCalculo(afTipoCambioBl
                        .getAfTipoCambioByCatMonedaAndFecha("UFV", fechaCalculoContable)
                        .getCambio());
            }
            agrupador.setItems(familiasMap.get(key));
            result.add(agrupador);
        }
        Collections.sort(result, new Comparator<AgrupadorReporteVo>() {

            public int compare(AgrupadorReporteVo o1, AgrupadorReporteVo o2) {
                return o1.getFamilia().compareTo(o2.getFamilia());
            }
        });
    }

    private void getDataInventarioAgrupadoPorCodigoContable(
            HashMap<CriteriosBusquedaEnum, Object> criterios,
            Date fechaCalculoContable,
            List<AgrupadorReporteVo> result,
            boolean contable) {
        Map<String, List<ItemReporteVo>> codigoContableMap = new HashMap<String, List<ItemReporteVo>>();
        String[] lazy = {"idSubFamilia"};
        List<AfActivoFijo> activosFijos = buscadorAvanzadoBuscar(criterios, null, null, lazy);
        if (contable) {
            realizarCalculosContablesListAsync(activosFijos, fechaCalculoContable);
        }
        for (AfActivoFijo afActivoFijo : activosFijos) {
            ItemReporteVo item = new ItemReporteVo();
            construirItemReporteVoGeneral(item, afActivoFijo);
            if (contable) {
                construirItemReporteVoContable(item, afActivoFijo);
            }
            List<ItemReporteVo> codigoContableList = codigoContableMap.get(item.getCodigoContable());
            if (codigoContableList == null) {
                codigoContableList = new ArrayList<>();
                codigoContableMap.put(item.getCodigoContable(), codigoContableList);
            }
            codigoContableList.add(item);
        }
        for (String key : codigoContableMap.keySet()) {
            Collections.sort(codigoContableMap.get(key), new Comparator<ItemReporteVo>() {

                public int compare(ItemReporteVo o1, ItemReporteVo o2) {
                    return o1.getCodigo().compareTo(o2.getCodigo());
                }
            });
            AgrupadorReporteVo agrupador = new AgrupadorReporteVo();
            agrupador.setCodigoContable(key);
            agrupador.setFechaCalculo(fechaCalculoContable);
            if (contable) {
                agrupador.setTipoCambioFechaCalculo(afTipoCambioBl
                        .getAfTipoCambioByCatMonedaAndFecha("UFV", fechaCalculoContable)
                        .getCambio());
            }
            agrupador.setItems(codigoContableMap.get(key));
            result.add(agrupador);
        }
        Collections.sort(result, new Comparator<AgrupadorReporteVo>() {

            public int compare(AgrupadorReporteVo o1, AgrupadorReporteVo o2) {
                return o1.getCodigoContable().compareTo(o2.getCodigoContable());
            }
        });
    }

    private void construirItemReporteVoContable(ItemReporteVo itemReporteVo, AfActivoFijo afActivoFijo) {
        itemReporteVo.setIndiceUfv(afActivoFijo.getCalculoContableVo().getTipoCambioIncorporacion());
        itemReporteVo.setVidaUtil(afActivoFijo.getCalculoContableVo().getAniosVidaUtil());
        itemReporteVo.setPorcentajeDepreciacionAnual(
                afActivoFijo.getCalculoContableVo().getPorcentajeDepreciacion());
        itemReporteVo.setDiasConsumidos(afActivoFijo.getCalculoContableVo().getDiasConsumidos());
        itemReporteVo.setDiasVidaUtilNominal(afActivoFijo
                .getCalculoContableVo()
                .getDiasVidaUtilResidualNominal()
                .intValue());
        itemReporteVo.setFactorActualizacion(afActivoFijo.getCalculoContableVo().getFactorActual());
        itemReporteVo.setActualizacionGestion(
                afActivoFijo.getCalculoContableVo().getActualizacionGestion());
        itemReporteVo.setCostoFinalActualizado(
                afActivoFijo.getCalculoContableVo().getValorActual());
        itemReporteVo.setDepreciacionMigrado(afActivoFijo.getCalculoContableVo().getDepreciacionAcumuladaInicial());
        itemReporteVo.setDepreciacionGestion(afActivoFijo.getCalculoContableVo().getDepreciacionGestion());
        // itemReporteVo.setDepreciacionAcumuladaTotal(afActivoFijo.getCalculoContableVo().getDepreciacionAcumuladaInicial().add(afActivoFijo.getCalculoContableVo().getDepreciacionGestion()));
        itemReporteVo.setDepreciacionAcumuladaTotal(
                afActivoFijo.getCalculoContableVo().getDepreciacionAcumulada());
        itemReporteVo.setValorNeto(afActivoFijo.getCalculoContableVo().getValorNeto());
        itemReporteVo.setActualizacionDepreciacionAcumulada(
                afActivoFijo.getCalculoContableVo().getActualizacionDepreciacionAcumulada());
        if (BigDecimal.ONE.compareTo(afActivoFijo.getCalculoContableVo().getValorNeto()) >= 0) {
            itemReporteVo.setRevaluarOBaja(true);
        }
    }

    private void construirItemReporteVoGeneral(ItemReporteVo itemReporteVo, AfActivoFijo afActivoFijo) {
        itemReporteVo.setCodigo(afActivoFijo.getCodigoEan());
        // itemReporteVo.setCentroCosto(getDescCatalogo(catalogo, "CAT_CENTRO_COSTO",
        // afActivoFijo.getCatCentroCosto()));
        itemReporteVo.setCentroCosto(getDescCatalogo(afActivoFijo.getTabCentroCosto(), afActivoFijo.getCentroCosto()));
        itemReporteVo.setDescripcion(afActivoFijo.getDescripcion());
        // itemReporteVo.setCondicion(getDescCatalogo(catalogo, "CAT_ESTADO_USO",
        // afActivoFijo.getCatEstadoUso()));
        itemReporteVo.setCentroCosto(getDescCatalogo(afActivoFijo.getTabEstadoUso(), afActivoFijo.getEstadoUso()));
        itemReporteVo.setCostoHistorico(afActivoFijo.getCostoHistorico());
        itemReporteVo.setCostoMigrado(afActivoFijo.getCostoActual());
        itemReporteVo.setFechaMigracion(afActivoFijo.getFechaActual());
        itemReporteVo.setFechaHistorica(afActivoFijo.getFechaHistorico());
        itemReporteVo.setRevaluado(afActivoFijo.getRevalorizado());
        if (afActivoFijo.getRevalorizado()) {
            if (afActivoFijo.getCostoActual().compareTo(afActivoFijo.getCostoAntesRevaluo()) >= 0) {
                itemReporteVo.setIncremento(
                        afActivoFijo.getCostoActual().subtract(afActivoFijo.getCostoAntesRevaluo()));
            } else {
                itemReporteVo.setDecremento(
                        afActivoFijo.getCostoAntesRevaluo().subtract(afActivoFijo.getCostoActual()));
            }
            itemReporteVo.setAntesRevaluo(afActivoFijo.getCostoAntesRevaluo());
        }
        itemReporteVo.setFamilia(
                afActivoFijo.getIdSubFamilia().getIdFamiliaActivo().getCodigo() + ": "
                        + afActivoFijo.getIdSubFamilia().getIdFamiliaActivo().getDescripcion());
        itemReporteVo.setSubfamilia(afActivoFijo.getIdSubFamilia().getCodigo() + ": "
                + afActivoFijo.getIdSubFamilia().getDescripcion());
        // itemReporteVo                .setEdificio(getDescCatalogo(catalogo, "CAT_EDIFICIO",
        // afActivoFijo.getIdAmbiente().getCatEdificio()));
        itemReporteVo.setCentroCosto(getDescCatalogo(afActivoFijo.getTabEstadoUso(), afActivoFijo.getEstadoUso()));

        // itemReporteVo.setPiso(getDescCatalogo(catalogo, "CAT_PISO", afActivoFijo.getIdAmbiente().getCatPiso()));
        itemReporteVo.setAmbiente(afActivoFijo.getIdAmbiente().getNombre());
        itemReporteVo.setResponsable(
                afActivoFijo.getIdUsuarioAsignado().getIdPersona().getNombreCompleto());
        itemReporteVo.setObservaciones(afActivoFijo.getObservaciones());
        itemReporteVo.setOrganismoFinanciador("Fuente: "
                + getDescCatalogo(afActivoFijo.getTabFenteFinanciamiento(), afActivoFijo.getFuenteFinanciamiento())
                + " \n Organismo: "
                + getDescCatalogo(afActivoFijo.getTabOrganismoFinanciador(), afActivoFijo.getOrganismoFinanciador()));
        itemReporteVo.setTipoAsignacion(
                getDescCatalogo(afActivoFijo.getTabTipoAsignacion(), afActivoFijo.getTipoAsignacion()));

        itemReporteVo.setPartidaPresupuestaria(afActivoFijo
                        .getIdSubFamilia()
                        .getIdFamiliaActivo()
                        .getIdPartidaPresupuestaria()
                        .getCodigo()
                + ": "
                + afActivoFijo
                        .getIdSubFamilia()
                        .getIdFamiliaActivo()
                        .getIdPartidaPresupuestaria()
                        .getDescripcion());
        itemReporteVo.setCodigoContable(afActivoFijo
                        .getIdSubFamilia()
                        .getIdFamiliaActivo()
                        .getIdCodigoContable()
                        .getCodigo()
                + ": "
                + afActivoFijo
                        .getIdSubFamilia()
                        .getIdFamiliaActivo()
                        .getIdPartidaPresupuestaria()
                        .getDescripcion());

        StringBuilder atributos = new StringBuilder();
        if (afActivoFijo.getAfAtributoActivoFijoList() != null) {
            for (AfAtributoActivoFijo item : afActivoFijo.getAfAtributoActivoFijoList()) {
                atributos
                        .append(getDescCatalogo(item.getTabTipoAtributo(), item.getTipoAtributo()))
                        .append(": ");
                atributos.append(item.getDetalle()).append("\n");
            }
        } else {
            atributos.append("SIN ATRIBUTOS");
        }
        itemReporteVo.setAtributos(atributos.toString());

        StringBuilder componentes = new StringBuilder();
        if (afActivoFijo.getAfComponenteActivoFijoList() != null) {
            for (AfComponenteActivoFijo item : afActivoFijo.getAfComponenteActivoFijoList()) {
                /*                 componentes.append(
                getDescCatalogo(catalogo, "CAT_COMPONENTE_ACTIVO_FIJO", item.getCatComponenteActivoFijo()))
                .append(": "); */
                componentes.append(item.getCantidad()).append("\n");
            }
        } else {
            componentes.append("SIN COMPONENTES");
        }
        itemReporteVo.setComponentes(componentes.toString());

        StringBuilder factura = new StringBuilder();
        if (afActivoFijo.getIdFactura() != null) {
            factura.append("NIT: ").append(afActivoFijo.getIdFactura().getNit()).append("\n");
            factura.append("Fecha: ").append(afActivoFijo.getIdFactura().getFechaFactura());
        } else {
            factura.append("SIN FACTURA");
        }

        itemReporteVo.setFactura(factura.toString());

        StringBuilder garantia = new StringBuilder();
        if (afActivoFijo.getIdGarantiaActivoFijo() != null) {
            garantia.append("Tipo: ")
                    .append(getDescCatalogo(
                            afActivoFijo.getIdGarantiaActivoFijo().getTabTipoGarantia(),
                            afActivoFijo.getIdGarantiaActivoFijo().getTipoGarantia()))
                    .append("\n");
            garantia.append("Del ")
                    .append(AgrupadorReporteVo.SD_NORMAL.format(
                            afActivoFijo.getIdGarantiaActivoFijo().getFechaInicio()));
            garantia.append(" al ")
                    .append(AgrupadorReporteVo.SD_NORMAL.format(
                            afActivoFijo.getIdGarantiaActivoFijo().getFechaInicio()));
        } else {
            garantia.append("SIN GARANTIA");
        }
        itemReporteVo.setGarantia(garantia.toString());
    }

    private String getDescCatalogo(Integer cnfCatalogo, Integer cnfValor) {
        if (cnfValor != null) {
            Optional<GenDesctabla> genDesctablaOpt =
                    genDesctablaRespository.findByDesCodtabAndDesCodigo(cnfCatalogo, cnfValor);
            if (genDesctablaOpt.isPresent()) {
                return genDesctablaOpt.get().getDesDescrip();
            } else {
                throw new DataException("Se ha solicitado una combinación de catalogo valor no existente: "
                        + cnfCatalogo + " - " + cnfValor);
            }
        } else {
            return null;
        }
    }

    public void realizarCalculosContablesIndividual(AfActivoFijo afActivoFijoOrigen, Date fechaCalculo) {
        AfActivoFijo afActivoFijo = afActivoFijoRepository
                .findById(afActivoFijoOrigen.getIdActivoFijo())
                .orElseThrow(() -> new DataException("id inexistente material"));
        if (fechaCalculo == null) {
            fechaCalculo = new Date();
        }
        CalcContabService calculoContableVo = new CalcContabService(afActivoFijo, fechaCalculo);
        AfTipoCambio tipoCambioVidaUtil =
                afTipoCambioBl.getAfTipoCambioByCatMonedaAndFecha("UFV", calculoContableVo.getFechaVidaUtil());
        calculoContableVo.calcular(
                afTipoCambioBl
                        .getAfTipoCambioByCatMonedaAndFecha("UFV", afActivoFijo.getFechaActual())
                        .getCambio(),
                afTipoCambioBl
                        .getAfTipoCambioByCatMonedaAndFecha("UFV", fechaCalculo)
                        .getCambio(),
                tipoCambioVidaUtil != null ? tipoCambioVidaUtil.getCambio() : BigDecimal.ZERO);

        afActivoFijoOrigen.setCalculoContableVo(calculoContableVo);
    }

    public void realizarCalculosContablesListAsync(List<AfActivoFijo> activosFijos, Date fechaCalculo) {
        Map<AfActivoFijo, Future<CalcContabService>> calculos = new HashMap<>();

        if (fechaCalculo == null) {
            fechaCalculo = new Date();
        }
        AfTipoCambio afTipoCambio = afTipoCambioBl.getAfTipoCambioByCatMonedaAndFecha("UFV", fechaCalculo);

        if (afTipoCambio != null) {
            System.out.println("Procesando CALCULOS PARA: " + activosFijos.size());
            for (AfActivoFijo afActivoFijo : activosFijos) {
                System.out.println("--ID--> " + afActivoFijo.getIdActivoFijo());
                System.out.println("--Gestion--> " + afActivoFijo.getGestion());
                System.out.println("--FechaActual--> " + afActivoFijo.getFechaActual());
                Future<CalcContabService> calculoContableVo =
                        afActivoFijoBlAsync.realizarCalculosContablesIndividualAsync(
                                afActivoFijo.getIdActivoFijo(),
                                afActivoFijo.getGestion(),
                                fechaCalculo,
                                afTipoCambio.getCambio());
                calculos.put(afActivoFijo, calculoContableVo);
            }

            while (!calculos.isEmpty()) {
                Set<AfActivoFijo> keys = calculos.keySet();
                Set<AfActivoFijo> forRemove = new HashSet<>();
                for (AfActivoFijo afActivoFijo : keys) {
                    if (calculos.get(afActivoFijo).isDone()) {
                        try {
                            afActivoFijo.setCalculoContableVo(
                                    calculos.get(afActivoFijo).get());
                            forRemove.add(afActivoFijo);
                        } catch (InterruptedException | ExecutionException e) {
                            throw new DataException("Error al procesar asíncronamete ", e);
                        }
                    }
                }
                for (AfActivoFijo afActivoFijo : forRemove) {
                    calculos.remove(afActivoFijo);
                }
                if (!calculos.isEmpty()) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new DataException("Error al procesar asíncronamete (Durmiendo) ", e);
                    }
                }
            }
        } else {
            throw new DataException("No existe tipo de cambio registrado para la fecha: " + fechaCalculo);
        }
    }

    public void mergeAfActivoFijo(AfActivoFijo afActivoFijo, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        if (afActivoFijo.getIdFactura() != null && afActivoFijo.getIdFactura().getIdFactura() == null) {
            afActivoFijo.getIdFactura().setEstado(StatusEnum.ACTIVE.getStatus());
            // TransactionUtil.setInitTransactionData(afActivoFijo.getIdFactura());
        }
        afActivoFijoRepository.save(afActivoFijo);
    }

    public void revaluoActivoFijo(
            AfRevaluoActivoFijo afRevaluoActivoFijo, AfActivoFijo afActivoFijo, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        AfActivoFijo activo = afActivoFijoRepository
                .findById(afActivoFijo.getIdActivoFijo())
                .orElseThrow(() -> new DataException("id inexistente AF"));
        realizarCalculosContablesIndividual(activo, afRevaluoActivoFijo.getFechaRevaluo());
        afRevaluoActivoFijo.setDepAlRevaluo(activo.getDepAcumuladaActual());
        afRevaluoActivoFijo.setDepAcumAlRevaluo(activo.getCalculoContableVo().getDepreciacionAcumulada());
        afRevaluoActivoFijo.setValorNetoAlRevaluo(activo.getCalculoContableVo().getValorNeto());
        afRevaluoActivoFijo.setCostoHistorico(activo.getCostoActual());
        afRevaluoActivoFijo.setEstado(StatusEnum.ACTIVE.getStatus());
        afRevaluoActivoFijo.setIdActivoFijo(activo);
        afRevaluoActivoFijoRepository.save(afRevaluoActivoFijo);
        //// TransactionUtil.setInitTransactionData(afRevaluoActivoFijo);
        activo.getAfRevaluoActivoFijoList().add(afRevaluoActivoFijo);
        activo.setCostoAntesRevaluo(activo.getCalculoContableVo().getValorNeto());
        activo.setCostoActual(afRevaluoActivoFijo.getCostoNuevo());
        activo.setFechaActual(afRevaluoActivoFijo.getFechaRevaluo());
        activo.setFactorDepreciacionActual(afRevaluoActivoFijo.getNuevoFactorDepreciacion());
        activo.setDepAcumuladaHistorico(activo.getDepAcumuladaActual());
        activo.setDepAcumuladaActual(BigDecimal.ZERO);
        activo.setRevalorizado(true);
        afActivoFijoRepository.save(activo);
    }

    public void disableAfActivoFijo(
            AfActivoFijo afActivoFijo, AfBajaActivoFijo afBajaActivoFijo, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afActivoFijo.setCatEstadoActivoFijo("DEBAJA");
        afBajaActivoFijo.setIdActivoFijo(afActivoFijo);
        afBajaActivoFijo.setEstado(StatusEnum.ACTIVE.getStatus());
        // TransactionUtil.setInitTransactionData(afBajaActivoFijo);
        if (afActivoFijo.getAfBajaActivoFijoList() == null) {
            afActivoFijo.setAfBajaActivoFijoList(new ArrayList<AfBajaActivoFijo>());
        }
        afActivoFijo.getAfBajaActivoFijoList().add(afBajaActivoFijo);
        afActivoFijoRepository.save(afActivoFijo);
    }

    public void transferAfActivoFijo(
            AfActivoFijo afActivoFijo,
            AfTransferenciaAsignacion afTransferenciaAsignacion,
            AfTransferenciaAsignacion.TipoTransferencia catTransferenciaAsignacion,
            List<AfImagenActivoFijo> afImagenActivoFijos,
            UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);

        afTransferenciaAsignacion.setIdUsuarioDestino(afActivoFijo.getIdUsuarioAsignado());
        afTransferenciaAsignacion.setIdAmbienteDestino(afActivoFijo.getIdAmbiente());
        afTransferenciaAsignacion.setCatCentroCostoDestino(afActivoFijo.getCatCentroCosto());
        afTransferenciaAsignacion.setCatTransferenciaAsignacion(catTransferenciaAsignacion.getValor());
        afTransferenciaAsignacion.setCatEstadoUso(afActivoFijo.getCatEstadoUso());
        afTransferenciaAsignacion.setIdActivoFijo(afActivoFijo);

        afActivoFijo.setCatTipoAsignacion(afTransferenciaAsignacion.getCatTipoAsignacion());
        afActivoFijo.setCatEstadoActivoFijo(afTransferenciaAsignacion.getCatTipoAsignacion());
        afTransferenciaAsignacion.setEstado(StatusEnum.ACTIVE.getStatus());
        // TransactionUtil.setInitTransactionData(afTransferenciaAsignacion);
        if (afActivoFijo.getAfTransferenciaAsignacionList() == null) {
            afActivoFijo.setAfTransferenciaAsignacionList(new ArrayList<AfTransferenciaAsignacion>());
        }
        afActivoFijo.getAfTransferenciaAsignacionList().add(afTransferenciaAsignacion);

        Date now = new Date();
        // Agregamos las imagenes
        if (afImagenActivoFijos != null && !afImagenActivoFijos.isEmpty()) {
            for (AfImagenActivoFijo afImagenActivoFijo : afImagenActivoFijos) {
                afImagenActivoFijo.setIdActivoFijo(afActivoFijo);
                afImagenActivoFijo.setIdTransferenciaActivoFijo(afTransferenciaAsignacion);
                afImagenActivoFijo.setFechaCaptura(now);
                // TransactionUtil.setInitTransactionData(afImagenActivoFijo);
                afImagenActivoFijo.setEstado(StatusEnum.ACTIVE.getStatus());
            }
            afActivoFijo.getAfImagenActivoFijoList().addAll(afImagenActivoFijos);
        }
        afActivoFijoRepository.save(afActivoFijo);
    }

    public void addAfAccesorioActivoFijo(
            AfActivoFijo afActivoFijo,
            AfAccesorioActivoFijo afAccesorioActivoFijo,
            List<AfImagenActivoFijo> afImagenActivoFijos,
            UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);

        afAccesorioActivoFijo.setEstado(StatusEnum.ACTIVE.getStatus());
        afAccesorioActivoFijo.setIdActivoFijo(afActivoFijo);
        // TransactionUtil.setInitTransactionData(afAccesorioActivoFijo);

        // Inicializamos la factura
        afAccesorioActivoFijo.getIdFactura().setEstado(StatusEnum.ACTIVE.getStatus());
        // TransactionUtil.setInitTransactionData(afAccesorioActivoFijo.getIdFactura());

        if (afActivoFijo.getAfAccesorioActivoFijoList() == null) {
            afActivoFijo.setAfAccesorioActivoFijoList(new ArrayList<AfAccesorioActivoFijo>());
        }
        afActivoFijo.getAfAccesorioActivoFijoList().add(afAccesorioActivoFijo);

        // Agregamos las imagenes
        if (afImagenActivoFijos != null && !afImagenActivoFijos.isEmpty()) {
            for (AfImagenActivoFijo afImagenActivoFijo : afImagenActivoFijos) {
                afImagenActivoFijo.setIdActivoFijo(afActivoFijo);
                afImagenActivoFijo.setIdAccesorioActivoFijo(afAccesorioActivoFijo);
                afImagenActivoFijo.setFechaCaptura(new Date());
                // TransactionUtil.setInitTransactionData(afImagenActivoFijo);
                afImagenActivoFijo.setEstado(StatusEnum.ACTIVE.getStatus());
            }
            afActivoFijo.getAfImagenActivoFijoList().addAll(afImagenActivoFijos);
        }
        afActivoFijoRepository.save(afActivoFijo);
    }

    public void persistAfActivoFijo(AfActivoFijo afActivoFijo, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afActivoFijoRepository.save(afActivoFijo);
    }

    public void deleteAfActivoFijo(AfActivoFijo afActivoFijo, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afActivoFijo.setIdNotaRecepcion(null);
        afActivoFijoRepository.delete(afActivoFijo);
    }

    public List<AfActivoFijo> findAllActivesAfActivoFijo() {
        return afActivoFijoRepository.findAll(); // .findAllActives();
    }

    public AfActivoFijo findByPkAfActivoFijo(Integer pk) {
        return afActivoFijoRepository.findById(pk).orElseThrow(() -> new DataException("id inexistente AF: " + pk));
    }

    public AfActivoFijo findByPkAfActivoFijoLazy(Integer pk) {
        AfActivoFijo afActivoFijo =
                afActivoFijoRepository.findById(pk).orElseThrow(() -> new DataException("id inexistente AF: " + pk));
        // LazyLoadingUtil.load(afActivoFijo, "idSubFamilia");
        return afActivoFijo;
    }

    public List<AfActivoFijo> findAllAfActivoFijoByNotaRecepcion(
            Integer gestion, Integer idNotaRecepcion, String[] lazy) {
        List<AfActivoFijo> result = afActivoFijoRepository.findAllAfActivoFijoByNotaRecepcion(idNotaRecepcion);
        // LazyLoadingUtil.loadCollection(result, lazy);
        return result;
    }

    public List<AfActivoFijo> findAllAfActivoFijoIngresado(Integer gestion, String[] lazy) {
        List<AfActivoFijo> result = afActivoFijoRepository.findAllAfActivoFijoIngresado(gestion);
        // LazyLoadingUtil.loadCollection(result, lazy);
        return result;
    }

    public List<AfActivoFijo> findAllAfActivoFijoAsignadoPorIdUsuario(
            Integer gestion, Integer idUsuario, String[] lazy) {
        List<AfActivoFijo> result = afActivoFijoRepository.findAllAfActivoFijoAsignadoPorIdUsuario(gestion, idUsuario);
        // LazyLoadingUtil.loadCollection(result, lazy);
        return result;
    }

    public List<AfActivoFijo> findAllActivesAfActivoFijoByGestion(Integer gestion) {
        return afActivoFijoRepository.findAllActivesByGestion(gestion);
    }

    public AfActivoFijo findByCodigoEan(Integer gestion, String codigoActivoFijo) {
        return afActivoFijoRepository.findByCodigoEanActivoFijo(gestion, codigoActivoFijo);
    }

    public AfActivoFijo findByCodigoExtendido(Integer gestion, String codigoActivoFijo) {
        return afActivoFijoRepository.findByCodigoExtendido(gestion, codigoActivoFijo);
    }

    public ActivoFijoRfidVo findActivoFijoRfidVoByCodigoActivoFijo(Integer gestion, String codigoActivoFijo) {
        ActivoFijoRfidVo result = new ActivoFijoRfidVo();
        AfActivoFijo afActivoFijo = afActivoFijoRepository.findByCodigoEanActivoFijo(gestion, codigoActivoFijo);
        if (afActivoFijo != null) {
            ItemReporteVo item = new ItemReporteVo();
            construirItemReporteVoGeneral(item, afActivoFijo);

            result.setCodigoActivoFijo(codigoActivoFijo);
            result.setDescripcion(item.getDescripcion());
            result.setAtributos(item.getAtributos());
            result.setComponentes(item.getComponentes());
            result.setCodigoEpc(afActivoFijo.getCodigoRfid());
            List<AfImagenActivoFijo> afImagenActivoFijos = afActivoFijo.getAfImagenActivoFijoList();
            if (afImagenActivoFijos != null && !afImagenActivoFijos.isEmpty()) {
                result.setImagenMimeType(afImagenActivoFijos.get(0).getTipoMime());
                result.setImagen(afImagenActivoFijos.get(0).getImagen());
            }
        }
        return result;
    }

    /******************************************** */

    /*     private String getDescCatalogo0(Map<TupleVo<String, String>, CnfValor> catalogo, String cnfCatalogo,
            String cnfValor) {
        if (cnfValor != null) {
            CnfValor valor = catalogo.get(new TupleVo<String, String>(cnfCatalogo, cnfValor));
            if (valor != null) {
                return valor.getDescripcion();
            } else {
                throw new DataException("Se ha solicitado una combinación de catalogo valor no existente: "
                        + cnfCatalogo + " - " + cnfValor);
            }
        } else {
            return null;
        }

    } */
}
