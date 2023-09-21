/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.domain.AfBajaMaterial;
import gob.gamo.activosf.app.domain.AfKardexMaterial;
import gob.gamo.activosf.app.domain.AfRegistroKardexMaterial;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.domain.TxUsuario;
import gob.gamo.activosf.app.dto.TupleVo;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.AfBajaMaterialRepository;

/**
 *
 * @author wherrera
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AfBajaMaterialBl {
    AfBajaMaterialRepository afBajaMaterialRepository;
    AfRegistroKardexMaterialBl afRegistroKardexMaterialBl;
    AfKardexMaterialBl afKardexMaterialBl;

    TxTransaccionBl txTransaccionBl;

    @Transactional(readOnly = true)
    public Page<AfBajaMaterial> findAll(Pageable pageable) {
        Page<AfBajaMaterial> list = afBajaMaterialRepository.findAll(pageable);
        return list;
    }

    public void ingresarAfBajaMaterial(AfBajaMaterial afBajaMaterial, UserRequestVo userRequestVo) {
        AfKardexMaterial afKardexMaterial = afKardexMaterialBl
                .findByPkAfKardexMaterial(afBajaMaterial.getIdKardexMaterial().getIdKardexMaterial())
                .orElseThrow(() -> new DataException("id inexistente material"));
        int saldoDisponible = 0;

        afBajaMaterial = findByPkAfBajaMaterial(afBajaMaterial.getIdBajaMaterial())
                .orElseThrow(() -> new DataException("id inexistente material"));
        afBajaMaterial.setFechaBaja(new Date());
        afBajaMaterial.setIdUsuarioBaja(new TxUsuario(userRequestVo.getUserId()));
        List<AfRegistroKardexMaterial> afRegistroKardexMaterialList =
                afBajaMaterial.getIdKardexMaterial().getAfRegistroKardexMaterialList();
        Collections.sort(afRegistroKardexMaterialList, new Comparator<AfRegistroKardexMaterial>() {
            public int compare(AfRegistroKardexMaterial o1, AfRegistroKardexMaterial o2) {
                return o1.getFechaRegistro().compareTo(o2.getFechaRegistro());
            }
        });
        for (AfRegistroKardexMaterial afRegistroKardexMaterial : afRegistroKardexMaterialList) {
            if (afRegistroKardexMaterial.getCatTipoRegistroKardex().startsWith("ING")
                    || afRegistroKardexMaterial.getCatTipoRegistroKardex().startsWith("SALINI")) {
                saldoDisponible += afRegistroKardexMaterial.getSaldo();
            }
        }

        if (saldoDisponible < afBajaMaterial.getCantidad()) {
            throw new DataException("No existe saldos suficiente para realizar la baja de material. El saldo total es: "
                    + saldoDisponible);
        }

        int pendiente = afBajaMaterial.getCantidad();

        List<TupleVo<Integer, BigDecimal>> cantidadImporte = new ArrayList<TupleVo<Integer, BigDecimal>>();
        for (AfRegistroKardexMaterial afRegistroKardexMaterial : afRegistroKardexMaterialList) {
            if ((afRegistroKardexMaterial.getCatTipoRegistroKardex().startsWith("ING")
                            || afRegistroKardexMaterial
                                    .getCatTipoRegistroKardex()
                                    .startsWith("SALINI"))
                    && afRegistroKardexMaterial.getSaldo() != 0
                    && pendiente != 0) {
                if (pendiente <= afRegistroKardexMaterial.getSaldo()) {
                    afRegistroKardexMaterial.setSaldo(afRegistroKardexMaterial.getSaldo() - pendiente);
                    cantidadImporte.add(new TupleVo<>(pendiente, afRegistroKardexMaterial.getImporteUnitario()));
                    afRegistroKardexMaterialBl.mergeAfRegistroKardexMaterial(afRegistroKardexMaterial, userRequestVo);
                    pendiente = 0;
                    break;
                } else {
                    cantidadImporte.add(new TupleVo<>(
                            afRegistroKardexMaterial.getSaldo(), afRegistroKardexMaterial.getImporteUnitario()));
                    afRegistroKardexMaterial.setSaldo(0);
                    afRegistroKardexMaterialBl.mergeAfRegistroKardexMaterial(afRegistroKardexMaterial, userRequestVo);
                    pendiente = pendiente - afRegistroKardexMaterial.getSaldo();
                }
            }
        }

        /*
         * boolean disponible = false;
         * int saldo = 0;
         *
         * for (AfRegistroKardexMaterial afRegistroKardexMaterial :
         * afRegistroKardexMaterialList) {
         * if (afRegistroKardexMaterial.getCatTipoRegistroKardex().startsWith("ING")) {
         * if (!disponible) {
         * descuento -= afRegistroKardexMaterial.getCantidad();
         * if (descuento <= 0) {
         * disponible = true;
         * if (descuento < 0 ) { // Ya tengo N = -descuento disponible
         * int disp = descuento * -1;
         * if (disp >= afBajaMaterial.getCantidad()) {
         * cantidadImporte.add(new
         * TupleVo<>(afBajaMaterial.getCantidad(),afRegistroKardexMaterial.
         * getImporteUnitario()));
         * break;
         * } else {
         * cantidadImporte.add(new
         * TupleVo<>(disp,afRegistroKardexMaterial.getImporteUnitario()));
         * saldo = afBajaMaterial.getCantidad() - disp ;
         * }
         * }
         * }
         * } else{
         * if (saldo < afRegistroKardexMaterial.getCantidad()) {
         * cantidadImporte.add(new
         * TupleVo<>(saldo,afRegistroKardexMaterial.getImporteUnitario()));
         * break;
         * } else {
         * cantidadImporte.add(new
         * TupleVo<>(afRegistroKardexMaterial.getCantidad(),afRegistroKardexMaterial.
         * getImporteUnitario()));
         * saldo = saldo - afRegistroKardexMaterial.getCantidad();
         * }
         * }
         *
         * }
         * }
         */
        for (TupleVo<Integer, BigDecimal> tupleVo : cantidadImporte) {
            AfRegistroKardexMaterial afRegistroKardexMaterial = new AfRegistroKardexMaterial();
            afRegistroKardexMaterial.setIdKardexMaterial(afKardexMaterial);
            afRegistroKardexMaterial.setFechaRegistro(userRequestVo.getDate());
            afRegistroKardexMaterial.setDetalle("SALIDA DE MATERIAL");
            afRegistroKardexMaterial.setImporteUnitario(tupleVo.getValue());
            afRegistroKardexMaterial.setCantidad(tupleVo.getKey());
            afRegistroKardexMaterial.setCatTipoRegistroKardex(afBajaMaterial.getCatTipoBajaMaterial());
            afRegistroKardexMaterial.setIdUsuarioRegistro(new TxUsuario(userRequestVo.getUserId()));
            afRegistroKardexMaterialBl.persistAfRegistroKardexMaterial(afRegistroKardexMaterial, userRequestVo);
        }
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afBajaMaterial.setCatEstadoBajaMaterial("INGRES");
        afBajaMaterialRepository.save(afBajaMaterial);
    }

    public void mergeAfBajaMaterial(AfBajaMaterial afBajaMaterial, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afBajaMaterialRepository.save(afBajaMaterial);
    }

    public void persistAfBajaMaterial(AfBajaMaterial afBajaMaterial, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afBajaMaterialRepository.save(afBajaMaterial);
    }

    public void deleteAfBajaMaterial(AfBajaMaterial afBajaMaterial, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afBajaMaterialRepository.delete(afBajaMaterial);
    }

    public List<AfBajaMaterial> findAllActivesAfBajaMaterialPorGestion(Integer gestion) {
        return afBajaMaterialRepository.findAllActivesPorGestion(gestion);
    }

    public Optional<AfBajaMaterial> findByPkAfBajaMaterial(Integer pk) {
        return afBajaMaterialRepository.findById(pk);
    }
}
