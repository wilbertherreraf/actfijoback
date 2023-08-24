/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.services;

import java.util.List;
import java.util.Optional;

import gob.gamo.activosf.app.domain.AfKardexMaterial;
import gob.gamo.activosf.app.domain.AfRegistroKardexMaterial;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.repository.AfRegistroKardexMaterialRepository;

/**
 *
 * @author wherrera
 */
public class AfRegistroKardexMaterialBl {

    AfRegistroKardexMaterialRepository afRegistroKardexMaterialRepository;

    TxTransaccionBl txTransaccionBl;

    public void mergeAfRegistroKardexMaterial(
            AfRegistroKardexMaterial afRegistroKardexMaterial, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afRegistroKardexMaterialRepository.save(afRegistroKardexMaterial);
    }

    public void persistAfRegistroKardexMaterial(
            AfRegistroKardexMaterial afRegistroKardexMaterial, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afRegistroKardexMaterialRepository.save(afRegistroKardexMaterial);
    }

    public void deleteAfRegistroKardexMaterial(
            AfRegistroKardexMaterial afRegistroKardexMaterial, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afRegistroKardexMaterialRepository.delete(afRegistroKardexMaterial);
    }

    public List<AfRegistroKardexMaterial> findAllActivesAfRegistroKardexMaterialByKardexMaterial(
            AfKardexMaterial afKardexMaterial) {
        return afRegistroKardexMaterialRepository.findAllActivesByAfKardexMaterial(afKardexMaterial);
    }

    public List<AfRegistroKardexMaterial> findAllActivesConSaldoByAfMaterialPorGestionOrdenadoPeps(
            AfKardexMaterial afKardexMaterial, Integer gestion) {
        return afRegistroKardexMaterialRepository.findIngresoInicialConSaldoByAfKardexMaterialPorGestionOrdenadoPeps(
                afKardexMaterial, gestion);
    }

    public Optional<AfRegistroKardexMaterial> findByPkAfRegistroKardexMaterial(Integer pk) {
        return afRegistroKardexMaterialRepository.findById(pk);
    }
}
