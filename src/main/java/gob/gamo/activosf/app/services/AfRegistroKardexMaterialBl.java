/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.domain.AfKardexMaterial;
import gob.gamo.activosf.app.domain.AfRegistroKardexMaterial;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.repository.AfRegistroKardexMaterialRepository;

/**
 *
 * @author wherrera
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AfRegistroKardexMaterialBl {

    AfRegistroKardexMaterialRepository afRegistroKardexMaterialRepository;

    TxTransaccionBl txTransaccionBl;

    @Transactional(readOnly = true)
    public Page<AfRegistroKardexMaterial> findAll(Pageable pageable) {
        Page<AfRegistroKardexMaterial> list = afRegistroKardexMaterialRepository.findAll(pageable);
        return list;
    }

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
