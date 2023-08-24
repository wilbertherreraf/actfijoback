/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.services;

import java.util.Optional;

import gob.gamo.activosf.app.domain.AfAmbiente;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.repository.AfAmbienteRepository;

/**
 *
 * @author wherrera
 */
public class AfAmbienteBl {

    AfAmbienteRepository afAmbienteRepository;

    TxTransaccionBl txTransaccionBl;

    public void mergeAfAmbiente(AfAmbiente afAmbiente, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afAmbienteRepository.save(afAmbiente);
    }

    public void persistAfAmbiente(AfAmbiente afAmbiente, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afAmbienteRepository.save(afAmbiente);
    }

    public void deleteAfAmbiente(AfAmbiente afAmbiente, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afAmbienteRepository.delete(afAmbiente);
    }

    public Optional<AfAmbiente> findByPkAfAmbiente(Integer pk) {
        return afAmbienteRepository.findById(pk);
    }
}
