/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gob.gamo.activosf.app.domain.AfSubFamiliaActivo;
import gob.gamo.activosf.app.domain.AfTipoCambio;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.AfTipoCambioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author wherrera
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AfTipoCambioBl {

    AfTipoCambioRepository afTipoCambioRepository;

    TxTransaccionBl txTransaccionBl;
    @Transactional(readOnly = true)
    public Page<AfTipoCambio> findAll(Pageable pageable) {
        Page<AfTipoCambio> list = afTipoCambioRepository.findAll(pageable);
        return list;
    }
    public void mergeAfTipoCambio(AfTipoCambio afTipoCambio, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        // afTipoCambioRepository.save(afTipoCambio);
        afTipoCambioRepository.save(afTipoCambio);
    }

    public void persistAfTipoCambio(AfTipoCambio afTipoCambio, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afTipoCambioRepository.save(afTipoCambio);
    }

    public void deleteAfTipoCambio(AfTipoCambio afTipoCambio, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afTipoCambioRepository.delete(afTipoCambio);
    }

    public Optional<AfTipoCambio> findByPkAfTipoCambio(Integer pk) {
        return afTipoCambioRepository.findById(pk);
    }

    public AfTipoCambio getAfTipoCambioByCatMonedaAndFecha(String catMoneda, Date fecha) {
        return afTipoCambioRepository
                .getAfTipoCambioByCatMonedaAndFecha(catMoneda, fecha)
                .orElseThrow(() -> new DataException("Tipo de cambio inexistente " + catMoneda + " fecha " + fecha));
    }

    public Optional<AfTipoCambio> getAfTipoCambioUltimo(String catMoneda) {
        return afTipoCambioRepository.getAfTipoCambioUltimo(catMoneda);
    }
}
