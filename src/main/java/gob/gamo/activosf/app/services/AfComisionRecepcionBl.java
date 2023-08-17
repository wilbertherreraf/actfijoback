/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.services;

import java.util.List;
import java.util.Optional;

import gob.gamo.activosf.app.repository.AfComisionRecepcionRepository;
import gob.gamo.activosf.app.domain.AfComisionRecepcion;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.UserRequestVo;

/**
 *
 * @author wherrera
 */

public class AfComisionRecepcionBl {

    
    AfComisionRecepcionRepository afComisionRecepcionRepository;
    
    
    TxTransaccionBl txTransaccionBl;
    
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
