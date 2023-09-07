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

import gob.gamo.activosf.app.domain.AfNotaRecepcion;
import gob.gamo.activosf.app.domain.AfPartidaPresupuestaria;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.repository.AfPartidaPresupuestariaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author wherrera
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AfPartidaPresupuestariaBl {

    AfPartidaPresupuestariaRepository afPartidaPresupuestariaRepository;

    TxTransaccionBl txTransaccionBl;
    @Transactional(readOnly = true)
    public Page<AfPartidaPresupuestaria> findAll(Pageable pageable) {
        Page<AfPartidaPresupuestaria> list = afPartidaPresupuestariaRepository.findAll(pageable);
        return list;
    }
    public void mergeAfPartidaPresupuestaria(
            AfPartidaPresupuestaria afPartidaPresupuestaria, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afPartidaPresupuestariaRepository.save(afPartidaPresupuestaria);
    }

    public void persistAfPartidaPresupuestaria(
            AfPartidaPresupuestaria afPartidaPresupuestaria, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afPartidaPresupuestariaRepository.save(afPartidaPresupuestaria);
    }

    public void deleteAfPartidaPresupuestaria(
            AfPartidaPresupuestaria afPartidaPresupuestaria, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afPartidaPresupuestariaRepository.delete(afPartidaPresupuestaria);
    }

    public List<AfPartidaPresupuestaria> findAllActivesAfPartidaPresupuestariaByGestion(Integer gestion) {
        return afPartidaPresupuestariaRepository.findAllActivesByGestion(gestion);
    }

    public Optional<AfPartidaPresupuestaria> findByPkAfPartidaPresupuestaria(Integer pk) {
        return afPartidaPresupuestariaRepository.findById(pk);
    }
}
