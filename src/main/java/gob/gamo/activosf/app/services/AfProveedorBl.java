/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.domain.AfProveedor;
import gob.gamo.activosf.app.domain.AfProveedorActEco;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.StatusEnum;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.AfProveedorRepository;

/**
 *
 * @author wherrera
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AfProveedorBl {

    AfProveedorRepository afProveedorRepository;
    TxTransaccionBl txTransaccionBl;

    @Transactional(readOnly = true)
    public Page<AfProveedor> findAll(Pageable pageable) {
        Page<AfProveedor> list = afProveedorRepository.findAll(pageable);
        return list;
    }

    public AfProveedor mergeAfProveedor(AfProveedor afProveedor, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        List<AfProveedorActEco> afProveedorActEcoList = afProveedor.getAfProveedorActEcoList();
        for (AfProveedorActEco afProveedorActEco : afProveedorActEcoList) {
            afProveedorActEco.setIdProveedor(afProveedor);
            afProveedorActEco.setEstado(StatusEnum.ACTIVE.getStatus());
            // TransactionUtil.setInitTransactionData(afProveedorActEco,txTransaccion);
        }
        return afProveedorRepository.save(afProveedor);
    }

    public void persistAfProveedor(AfProveedor afProveedor, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        List<AfProveedorActEco> afProveedorActEcoList = afProveedor.getAfProveedorActEcoList();
        for (AfProveedorActEco afProveedorActEco : afProveedorActEcoList) {
            afProveedorActEco.setIdProveedor(afProveedor);
            afProveedorActEco.setEstado(StatusEnum.ACTIVE.getStatus());
            // TransactionUtil.setInitTransactionData(afProveedorActEco,txTransaccion);
        }
        afProveedorRepository.save(afProveedor);
    }

    public void deleteAfProveedor(AfProveedor afProveedor, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afProveedorRepository.delete(afProveedor);
    }

    public void delete(Integer id, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afProveedorRepository.deleteById(id);
    }

    public AfProveedor findByPkAfProveedor(Integer pk) {
        return afProveedorRepository.findById(pk).orElseThrow(() -> new DataException("id inexistente"));
    }
}
