/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.services;

import java.util.List;
import java.util.Optional;

import gob.gamo.activosf.app.repository.AfFamiliaActivoRepository;
import gob.gamo.activosf.app.domain.AfFamiliaActivo;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.UserRequestVo;

/**
 *
 * @author wherrera
 */

public class AfFamiliaActivoBl {

    
    AfFamiliaActivoRepository afFamiliaActivoRepository;
    
    
    TxTransaccionBl txTransaccionBl;
    
    public void mergeAfFamiliaActivo(AfFamiliaActivo afFamiliaTipoActivo, UserRequestVo userRequestVo) {
    	TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        //afFamiliaActivoRepository.save(afFamiliaTipoActivo);
        afFamiliaActivoRepository.save(afFamiliaTipoActivo);
    }
    
    public void persistAfFamiliaActivo(AfFamiliaActivo afFamiliaTipoActivo, UserRequestVo userRequestVo) {
    	TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        //afFamiliaActivoRepository.save(afFamiliaTipoActivo);
        afFamiliaActivoRepository.save(afFamiliaTipoActivo);
    }
    
    public void deleteAfFamiliaActivo(AfFamiliaActivo afFamiliaTipoActivo, UserRequestVo userRequestVo) {
    	TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afFamiliaActivoRepository.delete(afFamiliaTipoActivo);
    }
    
    
    public Optional<AfFamiliaActivo> findByPkAfFamiliaActivo(Integer pk) {
    	return afFamiliaActivoRepository.findById(pk);
    }
    
    public List<AfFamiliaActivo> findAllActivesAfFamiliaActivoByGestion(Integer gestion) {
        return afFamiliaActivoRepository.findAllActivesByGestion(gestion);
    }
    
    public Optional<AfFamiliaActivo> findAfFamiliaActivoByCodigoAndGestion(String codigo , Integer gestion) {
        return afFamiliaActivoRepository.findAfFamiliaActivoByCodigoAndGestion(codigo, gestion);
    }
}
