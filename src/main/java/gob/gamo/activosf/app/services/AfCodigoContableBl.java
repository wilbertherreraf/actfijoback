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

import gob.gamo.activosf.app.domain.AfCodigoContable;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.repository.AfCodigoContableRepository;

/**
 *
 * @author wherrera
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AfCodigoContableBl {

    AfCodigoContableRepository afCodigoContableRepository;

    TxTransaccionService txTransaccionBl;

    @Transactional(readOnly = true)
    public Page<AfCodigoContable> findAll(Pageable pageable) {
        Page<AfCodigoContable> list = afCodigoContableRepository.findAll(pageable);
        return list;
    }

    public AfCodigoContable mergeAfCodigoContable(AfCodigoContable afCodigoContable, UserRequestVo userRequestVo) {
        // TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        // afCodigoContableRepository.save(afCodigoContable);
        return afCodigoContableRepository.save(afCodigoContable);
    }

    public void persistAfCodigoContable(AfCodigoContable afCodigoContable, UserRequestVo userRequestVo) {
        // TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        // afCodigoContableRepository.save(afCodigoContable);
        afCodigoContableRepository.save(afCodigoContable);
    }

    public void deleteAfCodigoContable(AfCodigoContable afCodigoContable, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        // afCodigoContableRepository.delete(afCodigoContable);
        afCodigoContableRepository.delete(afCodigoContable);
    }

    public void delete(Integer id, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        // afCodigoContableRepository.delete(afCodigoContable);
        afCodigoContableRepository.deleteById(id);
    }

    public Optional<AfCodigoContable> findByPkAfCodigoContable(Integer pk) {
        return afCodigoContableRepository.findById(pk);
    }

    public List<AfCodigoContable> findAllActivesAfCodigoContableByGestion(Integer gestion) {
        return afCodigoContableRepository.findAllActivesByGestion(gestion);
    }
}
