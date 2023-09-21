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

import gob.gamo.activosf.app.domain.AfAlmacen;
import gob.gamo.activosf.app.domain.AfMaterial;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.AfMaterialRepository;

/**
 *
 * @author wherrera
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AfMaterialBl {
    private final AfMaterialRepository afMaterialRepository;
    private final AfMaterialRepository repositoryEntity;

    TxTransaccionBl txTransaccionBl;

    @Transactional(readOnly = true)
    public Page<AfMaterial> findAll(Pageable pageable) {
        Page<AfMaterial> list = afMaterialRepository.findAll(pageable);
        return list;
    }

    @Transactional
    public AfMaterial crearNuevo(AfMaterial entity) {
        if (entity.getIdMaterial() != null && entity.getIdMaterial().compareTo(0) > 0)
            new DataException("Entidad con id, debe ser nulo");

        AfMaterial newEntity = repositoryEntity.save(entity);
        return newEntity;
    }

    @Transactional
    public AfMaterial update(AfMaterial entityReq) {
        if (entityReq.getIdMaterial() == null || entityReq.getIdMaterial().compareTo(0) == 0)
            new DataException("Entidad con id, debe nulo");

        AfMaterial newEntity = repositoryEntity.save(entityReq);
        return newEntity;
    }

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

    public List<AfMaterial> findAllActivesAfMaterial(Pageable pageable) {
        return afMaterialRepository.findAllActives();
    }

    public List<AfMaterial> findAllActivesAfMaterialPorAfAlmacenYGestion(AfAlmacen afAlmacen, Integer gestion) {
        return afMaterialRepository.findAllActivesAfMaterialPorAfAlmacenYGestion(afAlmacen, gestion);
    }

    public Optional<AfMaterial> findByPkAfMaterial(Integer pk) {
        return afMaterialRepository.findById(pk);
    }
}
