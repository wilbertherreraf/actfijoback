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

import gob.gamo.activosf.app.domain.AfAlmacen;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.AfAlmacenRepository;

/**
 *
 * @author wherrera
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AfAlmacenBl {
    private final AfAlmacenRepository repositoryEntity;
    // private final TxTransaccionBl txTransaccionBl;
    @Transactional(readOnly = true)
    public Page<AfAlmacen> findAll(Pageable pageable) {
        Page<AfAlmacen> list = repositoryEntity.findAll(pageable);
        return list;
    }

    @Transactional
    public AfAlmacen crearNuevo(AfAlmacen entity) {
        if (entity.getIdAlmacen() != null && entity.getIdAlmacen().compareTo(0) > 0)
            new DataException("Entidad con id, debe ser nulo");

        AfAlmacen newEntity = repositoryEntity.save(entity);
        return newEntity;
    }

    @Transactional
    public AfAlmacen update(AfAlmacen entityReq) {
        if (entityReq.getIdAlmacen() == null || entityReq.getIdAlmacen().compareTo(0) == 0)
            new DataException("Entidad con id, debe nulo");

        AfAlmacen newEntity = repositoryEntity.save(entityReq);
        return newEntity;
    }

    @Transactional
    public void delete(Integer id) {
        repositoryEntity.deleteById(id);
    }

    public void mergeAfAlmacen(AfAlmacen afAlmacen, UserRequestVo userRequestVo) {
        //  TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        repositoryEntity.save(afAlmacen);
    }

    public void persistAfAlmacen(AfAlmacen afAlmacen, UserRequestVo userRequestVo) {
        // TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        repositoryEntity.save(afAlmacen);
    }

    public void deleteAfAlmacen(AfAlmacen afAlmacen, UserRequestVo userRequestVo) {
        // TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        repositoryEntity.delete(afAlmacen);
    }

    public Optional<AfAlmacen> findByPkAfAlmacen(Integer pk) {
        return repositoryEntity.findById(pk);
    }
}
