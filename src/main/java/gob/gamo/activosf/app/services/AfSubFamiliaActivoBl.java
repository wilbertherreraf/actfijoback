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

import gob.gamo.activosf.app.domain.AfAtributoSubFamilia;
import gob.gamo.activosf.app.domain.AfFamiliaActivo;
import gob.gamo.activosf.app.domain.AfSolicitud;
import gob.gamo.activosf.app.domain.AfSubFamiliaActivo;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.StatusEnum;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.repository.AfSubFamiliaActivoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author wherrera
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AfSubFamiliaActivoBl {

    AfSubFamiliaActivoRepository afSubFamiliaActivoRepository;

    TxTransaccionBl txTransaccionBl;
    @Transactional(readOnly = true)
    public Page<AfSubFamiliaActivo> findAll(Pageable pageable) {
        Page<AfSubFamiliaActivo> list = afSubFamiliaActivoRepository.findAll(pageable);
        return list;
    }
    public void mergeAfSubFamiliaActivo(AfSubFamiliaActivo afSubFamiliaActivo, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        List<AfAtributoSubFamilia> afAtributoSubFamiliaList = afSubFamiliaActivo.getAfAtributoSubFamiliaList();
        for (AfAtributoSubFamilia afAtributoSubFamilia : afAtributoSubFamiliaList) {
            afAtributoSubFamilia.setIdSubFamilia(afSubFamiliaActivo);
            afAtributoSubFamilia.setEstado(StatusEnum.ACTIVE.getStatus());
            // TransactionUtil.setInitTransactionData(afAtributoSubFamilia,txTransaccion);
        }
        afSubFamiliaActivoRepository.save(afSubFamiliaActivo);
    }

    public void persistAfSubFamiliaActivo(AfSubFamiliaActivo afSubFamiliaActivo, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        List<AfAtributoSubFamilia> afAtributoSubFamiliaList = afSubFamiliaActivo.getAfAtributoSubFamiliaList();
        for (AfAtributoSubFamilia afAtributoSubFamilia : afAtributoSubFamiliaList) {
            afAtributoSubFamilia.setIdSubFamilia(afSubFamiliaActivo);
            afAtributoSubFamilia.setEstado(StatusEnum.ACTIVE.getStatus());
            // TransactionUtil.setInitTransactionData(afAtributoSubFamilia,txTransaccion);
        }
        afSubFamiliaActivoRepository.save(afSubFamiliaActivo);
    }

    public void deleteAfSubFamiliaActivo(AfSubFamiliaActivo afFamiliaTipoActivo, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afSubFamiliaActivoRepository.delete(afFamiliaTipoActivo);
    }

    public Optional<AfSubFamiliaActivo> findByPkAfSubFamiliaActivo(Integer pk) {
        return afSubFamiliaActivoRepository.findById(pk);
    }

    public List<AfSubFamiliaActivo> findAllActivesAfSubFamiliaActivoByGestion(Integer gestion) {
        return afSubFamiliaActivoRepository.findAllActivesByGestion(gestion);
    }

    public List<AfSubFamiliaActivo> findAllActivesByGestionAndAfFamiliaActivo(
            Integer gestion, AfFamiliaActivo afFamiliaActivo) {
        return afSubFamiliaActivoRepository.findAllActivesByGestionAndAfFamiliaActivo(gestion, afFamiliaActivo);
    }

    public Optional<AfSubFamiliaActivo> findAfSubFamiliaActivoByCodigoAndGestion(
            AfFamiliaActivo afFamiliaActivo, String codigo, Integer gestion) {
        return afSubFamiliaActivoRepository.findAfSubFamiliaActivoByCodigoAndGestion(afFamiliaActivo, codigo, gestion);
    }
}
