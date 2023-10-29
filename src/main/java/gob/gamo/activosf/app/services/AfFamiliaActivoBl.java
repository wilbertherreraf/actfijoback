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

import gob.gamo.activosf.app.domain.AfFamiliaActivo;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.repository.AfFamiliaActivoRepository;

/**
 *
 * @author wherrera
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AfFamiliaActivoBl {

    AfFamiliaActivoRepository afFamiliaActivoRepository;

    TxTransaccionService txTransaccionBl;

    @Transactional(readOnly = true)
    public Page<AfFamiliaActivo> findAll(Pageable pageable) {
        Page<AfFamiliaActivo> list = afFamiliaActivoRepository.findAll(pageable);
        return list;
    }

    public void mergeAfFamiliaActivo(AfFamiliaActivo afFamiliaTipoActivo, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        // afFamiliaActivoRepository.save(afFamiliaTipoActivo);
        afFamiliaActivoRepository.save(afFamiliaTipoActivo);
    }

    public void persistAfFamiliaActivo(AfFamiliaActivo afFamiliaTipoActivo, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        // afFamiliaActivoRepository.save(afFamiliaTipoActivo);
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

    public Optional<AfFamiliaActivo> findAfFamiliaActivoByCodigoAndGestion(String codigo, Integer gestion) {
        return afFamiliaActivoRepository.findAfFamiliaActivoByCodigoAndGestion(codigo, gestion);
    }
}
