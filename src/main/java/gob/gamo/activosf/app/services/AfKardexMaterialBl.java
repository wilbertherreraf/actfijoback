/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.services;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gob.gamo.activosf.app.domain.AfAlmacen;
import gob.gamo.activosf.app.domain.AfGestion;
import gob.gamo.activosf.app.domain.AfKardexMaterial;
import gob.gamo.activosf.app.domain.AfMaterial;
import gob.gamo.activosf.app.domain.AfRegistroKardexMaterial;
import gob.gamo.activosf.app.domain.AfSolicitudMaterial;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.domain.TxUsuario;
import gob.gamo.activosf.app.dto.AlmacenCantidadVo;
import gob.gamo.activosf.app.dto.MaterialAlmacenCantidadVo;
import gob.gamo.activosf.app.dto.TupleVo;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.AfKardexMaterialRepository;
import gob.gamo.activosf.app.repository.AfRegistroKardexMaterialRepository;
import gob.gamo.activosf.app.repository.AfSolicitudMaterialRepository;
import gob.gamo.activosf.app.repository.AfSolicitudRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author wherrera
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AfKardexMaterialBl {

    AfKardexMaterialRepository afKardexMaterialRepository;

    AfRegistroKardexMaterialRepository afRegistroKardexMaterialRepository;

    AfSolicitudMaterialRepository afSolicitudMaterialRepository;

    AfSolicitudRepository afSolicitudRepository;

    TxTransaccionBl txTransaccionBl;
    @Transactional(readOnly = true)
    public Page<AfKardexMaterial> findAll(Pageable pageable) {
        Page<AfKardexMaterial> list = afKardexMaterialRepository.findAll(pageable);
        return list;
    }
    public AfKardexMaterial getAfKardexMaterialByGestionAndMaterialAndAlmacen(
            Integer gestion, AfMaterial idMaterial, AfAlmacen idAlmacen, boolean detalle) {
        AfKardexMaterial afKardexMaterial = afKardexMaterialRepository
                .findActiveByGestionAndMaterialAndAlmacen(gestion, idMaterial, idAlmacen)
                .orElseThrow(
                        () -> new DataException("registro inexiste " + gestion + ", " + idMaterial + ", " + idAlmacen));
        armarDetalleKardexMaterial(idAlmacen, detalle, afKardexMaterial);
        return afKardexMaterial;
    }

    private void armarDetalleKardexMaterial(AfAlmacen idAlmacen, boolean detalle, AfKardexMaterial afKardexMaterial) {
        if (detalle) {
            List<AfRegistroKardexMaterial> afRegistroKardexMaterialList =
                    afKardexMaterial.getAfRegistroKardexMaterialList();
            // Collections.sort(afRegistroKardexMaterialList);
            Integer cantidadSaldo = 0;
            BigDecimal importeSaldo = BigDecimal.ZERO;
            for (AfRegistroKardexMaterial afRegistroKardexMaterial : afRegistroKardexMaterialList) {
                if ("SALINI".equals(afRegistroKardexMaterial.getCatTipoRegistroKardex())
                        || "INGADQ".equals(afRegistroKardexMaterial.getCatTipoRegistroKardex())
                        || "INGDEV".equals(afRegistroKardexMaterial.getCatTipoRegistroKardex())
                        || "INGACC".equals(afRegistroKardexMaterial.getCatTipoRegistroKardex())) {
                    cantidadSaldo = cantidadSaldo + afRegistroKardexMaterial.getCantidad();
                    if (idAlmacen.getEsValorado()) {
                        importeSaldo = importeSaldo.add(afRegistroKardexMaterial
                                .getImporteUnitario()
                                .multiply(new BigDecimal(afRegistroKardexMaterial.getCantidad())));
                        afRegistroKardexMaterial.setImporteSaldo(importeSaldo);
                    }
                } else {
                    cantidadSaldo = cantidadSaldo - afRegistroKardexMaterial.getCantidad();
                    if (idAlmacen.getEsValorado()) {
                        importeSaldo = importeSaldo.subtract(afRegistroKardexMaterial
                                .getImporteUnitario()
                                .multiply(new BigDecimal(afRegistroKardexMaterial.getCantidad()), new MathContext(2)));
                        afRegistroKardexMaterial.setImporteSaldo(importeSaldo);
                    }
                }
                afRegistroKardexMaterial.setCantidadSaldo(cantidadSaldo);
            }
        }
    }

    public AfKardexMaterial crearAfKardexMaterial(
            Integer gestion, AfMaterial idMaterial, AfAlmacen idAlmacen, UserRequestVo userRequestVo) {
        AfKardexMaterial afKardexMaterial = new AfKardexMaterial();
        afKardexMaterial.setGestion(gestion);
        afKardexMaterial.setIdMaterial(idMaterial);
        afKardexMaterial.setIdAlmacen(idAlmacen);
        afKardexMaterial.setSaldoCantidad(0);
        afKardexMaterial.setSaldoImporte(BigDecimal.ZERO);
        persistAfKardexMaterial(afKardexMaterial, userRequestVo);
        return afKardexMaterial;
    }

    public void mergeAfKardexMaterial(AfKardexMaterial afKardexMaterial, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afKardexMaterialRepository.save(afKardexMaterial);
    }

    public void persistAfKardexMaterial(AfKardexMaterial afKardexMaterial, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afKardexMaterialRepository.save(afKardexMaterial);
    }

    public void deleteAfKardexMaterial(AfKardexMaterial afKardexMaterial, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afKardexMaterialRepository.delete(afKardexMaterial);
    }

    public List<AfKardexMaterial> findAllActivesAfKardexMaterial(Integer gestion) {
        return afKardexMaterialRepository.findAllActivesByGestion(gestion);
    }

    public Optional<AfKardexMaterial> findByPkAfKardexMaterial(Integer pk) {
        return afKardexMaterialRepository.findById(pk);
    }

    public List<AfMaterial> getAfMaterialConSaldoDisponibleCantidadPorGestion(Integer gestion) {
        return new ArrayList<>(
                (new HashSet<>(afKardexMaterialRepository.getAfMaterialConSaldoCantidadDisponiblePorGestion(gestion))));
    }

    public List<MaterialAlmacenCantidadVo> findMaterialAlmacenCantidadByMaterialListAndGestion(
            List<AfMaterial> material, Integer gestion) {
        List<MaterialAlmacenCantidadVo> result = new ArrayList<>();
        // Sacamos tods los AfKardexMaterial que nos devolvera idMaterial,
        // idAlmacen e idSaldo
        List<AfKardexMaterial> afKardexMaterialList =
                afKardexMaterialRepository.findByAfMaterialListAndGestionConSaldo(material, gestion);
        // Iteramostods los afKardexMaterial
        for (AfKardexMaterial afKardexMaterial : afKardexMaterialList) {
            // Creamos una variable auxiliar para guardar la referencia si
            // existe o crearla luego sino.
            MaterialAlmacenCantidadVo materialAlmacenCantidadVo = null;
            boolean encontradoEnLista = false;
            // SI en el resultado ya se a creado un MaterialAlmacenCantidadVo
            // entonces se encentra la referencia y se rompe el bucle.
            for (MaterialAlmacenCantidadVo materialAlmacen : result) {
                if (afKardexMaterial.getIdMaterial().equals(materialAlmacen.getAfMaterial())) {
                    materialAlmacenCantidadVo = materialAlmacen;
                    encontradoEnLista = true;
                    break;
                }
            }
            // Si luego de buscar no hemos encontrado una referencia del
            // material, creamos una nueva instancia.
            if (materialAlmacenCantidadVo == null) {
                materialAlmacenCantidadVo = new MaterialAlmacenCantidadVo(afKardexMaterial.getIdMaterial());
            }
            // Agregamos el almacen y su saldo al material
            materialAlmacenCantidadVo
                    .getAlmacenCantidadVoList()
                    .add(new AlmacenCantidadVo(afKardexMaterial.getIdAlmacen(), afKardexMaterial.getSaldoCantidad()));
            if (!encontradoEnLista) {
                result.add(materialAlmacenCantidadVo);
            }
        }
        return result;
    }

    public void entregarMaterialBl(
            List<MaterialAlmacenCantidadVo> materialAlmacenCantidadList, Integer gestion, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        for (MaterialAlmacenCantidadVo materialAlmacen : materialAlmacenCantidadList) {
            Integer cantidadEntregadaMaterial = 0;
            AfSolicitudMaterial afSolicitudMaterial = afSolicitudMaterialRepository
                    .findById(materialAlmacen.getAfSolicitudMaterial().getIdSolicitudMaterial())
                    .orElseThrow(() -> new DataException("Registro inexistente"));

            for (AlmacenCantidadVo almacenCantidadVo : materialAlmacen.getAlmacenCantidadVoList()) {
                AfKardexMaterial afKardexMaterial = afKardexMaterialRepository
                        .findActiveByGestionAndMaterialAndAlmacen(
                                gestion, materialAlmacen.getAfMaterial(), almacenCantidadVo.getAfAlmacen())
                        .orElseThrow(() -> new DataException("Registro inexistente"));
                if (almacenCantidadVo.getCantidadAsignada() > afKardexMaterial.getSaldoCantidad()) {
                    throw new DataException(
                            "Uno de los almacenes ya no cuenta con la cantidad necesaria para realizar la operación");
                } else if (almacenCantidadVo.getCantidadAsignada() > 0) {
                    // Se busca todos los registros de kardex con saldo
                    // (debería ser por gestión)
                    List<AfRegistroKardexMaterial> afRegistroKardexMaterialList =
                            afRegistroKardexMaterialRepository
                                    .findIngresoInicialConSaldoByAfKardexMaterialPorGestionOrdenadoPeps(
                                            afKardexMaterial, gestion);
                    // Se inicializa la cantidad entregada en pendiente.

                    Integer pendiente = almacenCantidadVo.getCantidadAsignada();
                    // Se crea una lista de tuplas de la cantidad y el
                    // importe
                    List<TupleVo<Integer, BigDecimal>> cantidadImporte = new ArrayList<TupleVo<Integer, BigDecimal>>();
                    List<AfKardexMaterial> kardex = new ArrayList<>();

                    // ITeramos todos los kardexMaterial con saldo.
                    for (AfRegistroKardexMaterial afRegistroKardexMaterial : afRegistroKardexMaterialList) {
                        // Si es un ingreso, tiene saldo y tenemos pendiente.
                        if (afRegistroKardexMaterial.getSaldo() != 0 && pendiente != 0) {
                            // Si el pendiente se completa.
                            if (pendiente <= afRegistroKardexMaterial.getSaldo()) {
                                afRegistroKardexMaterial.setSaldo(afRegistroKardexMaterial.getSaldo() - pendiente);
                                cantidadImporte.add(
                                        new TupleVo<>(pendiente, afRegistroKardexMaterial.getImporteUnitario()));
                                kardex.add(afRegistroKardexMaterial.getIdKardexMaterial());
                                afRegistroKardexMaterialRepository.save(afRegistroKardexMaterial);
                                pendiente = 0;
                                break;
                            } else {
                                cantidadImporte.add(new TupleVo<>(
                                        afRegistroKardexMaterial.getSaldo(),
                                        afRegistroKardexMaterial.getImporteUnitario()));
                                kardex.add(afRegistroKardexMaterial.getIdKardexMaterial());
                                pendiente = pendiente - afRegistroKardexMaterial.getSaldo();
                                afRegistroKardexMaterial.setSaldo(0);
                                afRegistroKardexMaterialRepository.save(afRegistroKardexMaterial);
                            }
                        }
                    }

                    Integer i = 0;
                    Integer cantidadEjecutada = 0;
                    BigDecimal importeEjecutado = null;
                    if (almacenCantidadVo.getAfAlmacen().getEsValorado()) {
                        importeEjecutado = BigDecimal.ZERO;
                    }
                    for (TupleVo<Integer, BigDecimal> tupleVo : cantidadImporte) {
                        AfRegistroKardexMaterial afRegistroKardexMaterial = new AfRegistroKardexMaterial();
                        afRegistroKardexMaterial.setIdKardexMaterial(kardex.get(i));
                        afRegistroKardexMaterial.setFechaRegistro(userRequestVo.getDate());
                        afRegistroKardexMaterial.setDetalle("ENTREGA DE MATERIAL");
                        afRegistroKardexMaterial.setImporteUnitario(tupleVo.getValue());
                        afRegistroKardexMaterial.setCantidad(tupleVo.getKey());
                        afRegistroKardexMaterial.setCatTipoRegistroKardex("SALENT");
                        afRegistroKardexMaterial.setIdSolicitudMaterial(afSolicitudMaterial);
                        afRegistroKardexMaterial.setIdUsuarioRegistro(new TxUsuario(userRequestVo.getUserId()));
                        afRegistroKardexMaterialRepository.save(afRegistroKardexMaterial);
                        i++;
                        cantidadEjecutada += afRegistroKardexMaterial.getCantidad();
                        if (almacenCantidadVo.getAfAlmacen().getEsValorado()) {
                            importeEjecutado = importeEjecutado.add(afRegistroKardexMaterial.getImporteUnitario());
                        }
                    }
                    // Actualizamos el Kardex Material
                    afKardexMaterial.setSaldoCantidad(afKardexMaterial.getSaldoCantidad() - cantidadEjecutada);
                    cantidadEntregadaMaterial += cantidadEjecutada;
                    if (almacenCantidadVo.getAfAlmacen().getEsValorado()) {
                        afKardexMaterial.setSaldoImporte(
                                afKardexMaterial.getSaldoImporte().subtract(importeEjecutado));
                    }
                }
            }

            afSolicitudMaterial.setCantidadEntregada(cantidadEntregadaMaterial);
            afSolicitudMaterial.getIdSolicitud().setCatEstadoSolicitud("RELZDO");
            afSolicitudMaterial.getIdSolicitud().setIdUsuarioEjecucion(new TxUsuario(userRequestVo.getUserId()));
            afSolicitudMaterial.getIdSolicitud().setFechaEjecucion(new Date());
            afSolicitudMaterialRepository.save(afSolicitudMaterial);
        }
    }
}
