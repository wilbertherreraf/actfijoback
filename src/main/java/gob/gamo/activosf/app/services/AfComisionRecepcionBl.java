/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.domain.AfComisionRecepcion;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.repository.AfComisionRecepcionRepository;

/**
 *
 * @author wherrera
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AfComisionRecepcionBl {

    AfComisionRecepcionRepository afComisionRecepcionRepository;

    TxTransaccionService txTransaccionBl;

    @Transactional(readOnly = true)
    public Page<AfComisionRecepcion> findAll(Pageable pageable) {
        Page<AfComisionRecepcion> list = afComisionRecepcionRepository.findAll(pageable);
        return list;
    }

    public void mergeAfComisionRecepcion(AfComisionRecepcion afComisionRecepcion, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afComisionRecepcionRepository.save(afComisionRecepcion);
    }

    public void persistAfComisionRecepcion(AfComisionRecepcion afComisionRecepcion, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afComisionRecepcionRepository.save(afComisionRecepcion);
    }

    public void deleteAfComisionRecepcion(AfComisionRecepcion afComisionRecepcion, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afComisionRecepcionRepository.delete(afComisionRecepcion);
    }

    public Optional<AfComisionRecepcion> findByPkAfComisionRecepcion(Integer pk) {
        return afComisionRecepcionRepository.findById(pk);
    }
}
