/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import gob.gamo.activosf.app.domain.AfAltaMaterial;
import gob.gamo.activosf.app.domain.AfAltaMaterialDetalle;
import gob.gamo.activosf.app.domain.AfFactura;
import gob.gamo.activosf.app.domain.AfKardexMaterial;
import gob.gamo.activosf.app.domain.AfRegistroKardexMaterial;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.domain.TxUsuario;
import gob.gamo.activosf.app.dto.StatusEnum;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.AfAltaMaterialRepository;

/**
 *
 * @author wherrera
 */
@Service
@RequiredArgsConstructor
public class AfAltaMaterialBl {

    AfAltaMaterialRepository afAltaMaterialRepository;

    AfKardexMaterialBl afKardexMaterialBl;

    AfRegistroKardexMaterialBl afRegistroKardexMaterialBl;

    TxTransaccionService txTransaccionBl;

    @Transactional(readOnly = true)
    public Page<AfAltaMaterial> findAll(Pageable pageable) {
        Page<AfAltaMaterial> list = afAltaMaterialRepository.findAll(pageable);
        return list;
    }

    public void mergeAfAltaMaterial(AfAltaMaterial afAltaMaterial, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afAltaMaterialRepository.save(afAltaMaterial);
    }

    public void registroAfAltaMaterial(
            AfAltaMaterial afAltaMaterial,
            List<AfAltaMaterialDetalle> afAltaMaterialDetalleList,
            boolean tieneFactura,
            String numeroFactura,
            Date fechaFactura,
            UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);

        if (afAltaMaterial.getIdAlmacen().getEsValorado()) {
            if (tieneFactura) {
                AfFactura factura = new AfFactura();
                factura.setNroFactura(numeroFactura);
                factura.setFechaFactura(fechaFactura);
                factura.setRazonSocial(afAltaMaterial.getIdProveedor().getNombre());
                factura.setNit(afAltaMaterial.getIdProveedor().getNit());
                factura.setEstado(StatusEnum.ACTIVE.getStatus());
                // TransactionUtil.setInitTransactionData(factura);
                afAltaMaterial.setFechaValorado(fechaFactura);
                afAltaMaterial.setIdFactura(factura);
            }
        } else {
            afAltaMaterial.setIdProveedor(null);
            afAltaMaterial.setIdFactura(null);
            afAltaMaterial.setFechaValorado(null);
        }

        afAltaMaterial.setCatEstadoAltaMaterial("PROING");
        for (AfAltaMaterialDetalle afAltaMaterialDetalle : afAltaMaterialDetalleList) {
            afAltaMaterialDetalle.setIdAltaMaterial(afAltaMaterial);
            afAltaMaterialDetalle.setEstado(StatusEnum.ACTIVE.getStatus());
            // TransactionUtil.setInitTransactionData(afAltaMaterialDetalle);
        }
        afAltaMaterial.setAfAltaMaterialDetalleList(afAltaMaterialDetalleList);

        afAltaMaterialRepository.save(afAltaMaterial);
    }

    public void actualizarAfAltaMaterial(
            AfAltaMaterial afAltaMaterial,
            List<AfAltaMaterialDetalle> afAltaMaterialDetalleList,
            boolean tieneFactura,
            String numeroFactura,
            Date fechaFactura,
            UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);

        if (afAltaMaterial.getIdAlmacen().getEsValorado()) {
            if (tieneFactura) {
                AfFactura factura = new AfFactura();
                factura.setNroFactura(numeroFactura);
                factura.setFechaFactura(fechaFactura);
                factura.setRazonSocial(afAltaMaterial.getIdProveedor().getNombre());
                factura.setNit(afAltaMaterial.getIdProveedor().getNit());
                factura.setEstado(StatusEnum.ACTIVE.getStatus());
                // TransactionUtil.setInitTransactionData(factura);
                afAltaMaterial.setFechaValorado(fechaFactura);
                afAltaMaterial.setIdFactura(factura);
            }
        } else {
            afAltaMaterial.setIdProveedor(null);
            afAltaMaterial.setIdFactura(null);
            afAltaMaterial.setFechaValorado(null);
        }

        afAltaMaterial.setCatEstadoAltaMaterial("PROING");
        for (AfAltaMaterialDetalle afAltaMaterialDetalle : afAltaMaterialDetalleList) {
            afAltaMaterialDetalle.setIdAltaMaterial(afAltaMaterial);
            afAltaMaterialDetalle.setEstado(StatusEnum.ACTIVE.getStatus());
            // TransactionUtil.setInitTransactionData(afAltaMaterialDetalle);
        }
        afAltaMaterial.setAfAltaMaterialDetalleList(afAltaMaterialDetalleList);

        afAltaMaterialRepository.save(afAltaMaterial);
    }

    public void ingresarAfAltaMaterial(AfAltaMaterial afAltaMaterialIn, UserRequestVo userRequestVo) {

        Optional<AfAltaMaterial> afAltaMaterialOpt = findByPkAfAltaMaterial(afAltaMaterialIn.getIdAltaMaterial());
        if (afAltaMaterialOpt.isPresent()) {
            throw new DataException("Inexistente Matreial");
        }
        AfAltaMaterial afAltaMaterial = afAltaMaterialOpt.get();
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);

        for (AfAltaMaterialDetalle afAltaMaterialDetalle : afAltaMaterial.getAfAltaMaterialDetalleList()) {
            AfKardexMaterial afKardexMaterial = afKardexMaterialBl.getAfKardexMaterialByGestionAndMaterialAndAlmacen(
                    afAltaMaterial.getGestion(),
                    afAltaMaterialDetalle.getIdMaterial(),
                    afAltaMaterial.getIdAlmacen(),
                    false);
            if (afKardexMaterial == null) {
                afKardexMaterial = afKardexMaterialBl.crearAfKardexMaterial(
                        afAltaMaterial.getGestion(),
                        afAltaMaterialDetalle.getIdMaterial(),
                        afAltaMaterial.getIdAlmacen(),
                        userRequestVo);
            }
            AfRegistroKardexMaterial afRegistroKardexMaterial = new AfRegistroKardexMaterial();
            afRegistroKardexMaterial.setIdKardexMaterial(afKardexMaterial);
            afRegistroKardexMaterial.setFechaRegistro(afAltaMaterial.getFechaAlta());
            String detalle = "Alta de material a almacen "
                    + afAltaMaterial.getIdAlmacen().getNombre() + ". ";
            if (afAltaMaterial.getObservaciones() != null) {
                detalle = detalle + afAltaMaterial.getObservaciones();
            }
            afRegistroKardexMaterial.setDetalle(detalle);
            afRegistroKardexMaterial.setImporteUnitario(afAltaMaterialDetalle.getImporteUnitario());
            afRegistroKardexMaterial.setCantidad(afAltaMaterialDetalle.getCantidad());
            afRegistroKardexMaterial.setSaldo(afAltaMaterialDetalle.getCantidad());
            afRegistroKardexMaterial.setCatTipoRegistroKardex(afAltaMaterial.getCatTipoAltaMaterial());
            afRegistroKardexMaterial.setIdUsuarioRegistro(new TxUsuario(userRequestVo.getUserId()));
            afRegistroKardexMaterialBl.persistAfRegistroKardexMaterial(afRegistroKardexMaterial, userRequestVo);
            afAltaMaterialDetalle.setIdRegistroKardexMaterial(afRegistroKardexMaterial);

            // Actualizamos saldos del material:
            afKardexMaterial.setSaldoCantidad(
                    afKardexMaterial.getSaldoCantidad() + afAltaMaterialDetalle.getCantidad());
            if (afAltaMaterial.getIdAlmacen().getEsValorado()) {
                BigDecimal importeTotal = afAltaMaterialDetalle
                        .getImporteUnitario()
                        .multiply(new BigDecimal(afAltaMaterialDetalle.getCantidad()));
                afKardexMaterial.setSaldoImporte(
                        afKardexMaterial.getSaldoImporte().add(importeTotal));
            }
            afKardexMaterialBl.mergeAfKardexMaterial(afKardexMaterial, userRequestVo);
        }
        afAltaMaterial.setCatEstadoAltaMaterial("INGRES");
        afAltaMaterial.setIdUsuarioAlta(new TxUsuario(userRequestVo.getUserId()));
        afAltaMaterialRepository.save(afAltaMaterial);
    }

    public void persistAfAltaMaterial(AfAltaMaterial afAltaMaterial, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afAltaMaterialRepository.save(afAltaMaterial);
    }

    public void deleteAfAltaMaterial(AfAltaMaterial afAltaMaterial, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afAltaMaterialRepository.delete(afAltaMaterial);
    }

    public List<AfAltaMaterial> findAllActivesAfAltaMaterialPorGestion(Integer gestion) {
        return afAltaMaterialRepository.findAllActivesPorGestion(gestion);
    }

    public Optional<AfAltaMaterial> findByPkAfAltaMaterial(Integer pk) {
        return afAltaMaterialRepository.findById(pk);
    }
}
