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

import gob.gamo.activosf.app.domain.AfAmbiente;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.repository.AfAmbienteRepository;

/**
 *
 * @author wherrera
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AfAmbienteBl {
    AfAmbienteRepository afAmbienteRepository;

    TxTransaccionService txTransaccionBl;

    @Transactional(readOnly = true)
    public Page<AfAmbiente> findAll(Pageable pageable) {
        Page<AfAmbiente> list = afAmbienteRepository.findAll(pageable);
        return list;
    }

    @Transactional
    public void mergeAfAmbiente(AfAmbiente afAmbiente, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afAmbienteRepository.save(afAmbiente);
    }

    @Transactional
    public void persistAfAmbiente(AfAmbiente afAmbiente, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afAmbienteRepository.save(afAmbiente);
    }

    @Transactional
    public void deleteAfAmbiente(AfAmbiente afAmbiente, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afAmbienteRepository.delete(afAmbiente);
    }

    public Optional<AfAmbiente> findByPkAfAmbiente(Integer pk) {
        return afAmbienteRepository.findById(pk);
    }
}
