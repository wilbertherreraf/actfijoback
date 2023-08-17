/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.services;

import java.util.List;
import java.util.Optional;

import gob.gamo.activosf.app.repository.AfSolicitudMaterialRepository;
import gob.gamo.activosf.app.domain.AfSolicitudMaterial;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.UserRequestVo;

/**
 *
 * @author wherrera
 */

public class AfSolicitudMaterialBl {

    
    AfSolicitudMaterialRepository afSolicitudMaterialRepository;
    
    
    TxTransaccionBl txTransaccionBl;
    
    public void mergeAfSolicitudMaterial(AfSolicitudMaterial afSolicitudMaterial, UserRequestVo userRequestVo) {
    	TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afSolicitudMaterialRepository.save(afSolicitudMaterial);
    }
    
    public void persistAfSolicitudMaterial(AfSolicitudMaterial afSolicitudMaterial, UserRequestVo userRequestVo) {
    	TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afSolicitudMaterialRepository.save(afSolicitudMaterial);
    }
    
    public void deleteAfSolicitudMaterial(AfSolicitudMaterial afSolicitudMaterial, UserRequestVo userRequestVo) {
    	TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afSolicitudMaterialRepository.delete(afSolicitudMaterial);
    }
    
   
    public Optional<AfSolicitudMaterial> findByPkAfSolicitudMaterial(Integer pk) {
    	return afSolicitudMaterialRepository.findById(pk);
    	
    }
}
