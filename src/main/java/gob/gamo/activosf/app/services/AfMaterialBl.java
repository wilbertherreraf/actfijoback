/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.services;

import java.util.List;
import java.util.Optional;

import gob.gamo.activosf.app.domain.AfAlmacen;
import gob.gamo.activosf.app.domain.AfMaterial;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.repository.AfMaterialRepository;

/**
 *
 * @author wherrera
 */
public class AfMaterialBl {

    AfMaterialRepository afMaterialRepository;

    TxTransaccionBl txTransaccionBl;

    public void mergeAfMaterial(AfMaterial afMaterial, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afMaterialRepository.save(afMaterial);
    }

    public void persistAfMaterial(AfMaterial afMaterial, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afMaterialRepository.save(afMaterial);
    }

    public void deleteAfMaterial(AfMaterial afMaterial, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afMaterialRepository.delete(afMaterial);
    }

    public List<AfMaterial> findAllActivesAfMaterial() {
        return afMaterialRepository.findAllActives();
    }

    public List<AfMaterial> findAllActivesAfMaterialPorAfAlmacenYGestion(AfAlmacen afAlmacen, Integer gestion) {
        return afMaterialRepository.findAllActivesAfMaterialPorAfAlmacenYGestion(afAlmacen, gestion);
    }

    public Optional<AfMaterial> findByPkAfMaterial(Integer pk) {
        return afMaterialRepository.findById(pk);
    }
}
