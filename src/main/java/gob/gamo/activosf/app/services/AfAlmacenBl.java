/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import gob.gamo.activosf.app.domain.AfAlmacen;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.repository.AfAlmacenRepository;

/**
 *
 * @author wherrera
 */
@Service
@RequiredArgsConstructor
public class AfAlmacenBl {

    private final AfAlmacenRepository afAlmacenRepository;
    // private final TxTransaccionBl txTransaccionBl;

    public void mergeAfAlmacen(AfAlmacen afAlmacen, UserRequestVo userRequestVo) {
        //  TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afAlmacenRepository.save(afAlmacen);
    }

    public void persistAfAlmacen(AfAlmacen afAlmacen, UserRequestVo userRequestVo) {
        // TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afAlmacenRepository.save(afAlmacen);
    }

    public void deleteAfAlmacen(AfAlmacen afAlmacen, UserRequestVo userRequestVo) {
        // TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afAlmacenRepository.delete(afAlmacen);
    }

    public Optional<AfAlmacen> findByPkAfAlmacen(Integer pk) {
        return afAlmacenRepository.findById(pk);
    }
}
