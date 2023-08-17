/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.services;

import java.util.List;
import java.util.Optional;

import gob.gamo.activosf.app.repository.AfCodigoContableRepository;
import gob.gamo.activosf.app.domain.AfCodigoContable;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.UserRequestVo;

/**
 *
 * @author wherrera
 */

public class AfCodigoContableBl {

    
    AfCodigoContableRepository afCodigoContableRepository;
    
    
    TxTransaccionBl txTransaccionBl;
    
    public void mergeAfCodigoContable(AfCodigoContable afCodigoContable, UserRequestVo userRequestVo) {
    	//TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        //afCodigoContableRepository.save(afCodigoContable);
        afCodigoContableRepository.save(afCodigoContable);
    }
    
    public void persistAfCodigoContable(AfCodigoContable afCodigoContable, UserRequestVo userRequestVo) {
    	//TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        //afCodigoContableRepository.save(afCodigoContable);
        afCodigoContableRepository.save(afCodigoContable);
    }
    
    public void deleteAfCodigoContable(AfCodigoContable afCodigoContable, UserRequestVo userRequestVo) {
    	TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        //afCodigoContableRepository.delete(afCodigoContable);
        afCodigoContableRepository.delete(afCodigoContable);
    }
    
    public Optional<AfCodigoContable> findByPkAfCodigoContable(Integer pk) {
    	return afCodigoContableRepository.findById(pk);
    }
    
    public List<AfCodigoContable> findAllActivesAfCodigoContableByGestion(Integer gestion) {
        return afCodigoContableRepository.findAllActivesByGestion(gestion);
    }
}
