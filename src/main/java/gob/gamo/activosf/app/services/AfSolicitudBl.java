/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gob.gamo.activosf.app.domain.AfRegistroKardexMaterial;
import gob.gamo.activosf.app.domain.AfSolicitud;
import gob.gamo.activosf.app.domain.AfSolicitudMaterial;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.domain.TxUsuario;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.StatusEnum;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.AfSolicitudRepository;
import gob.gamo.activosf.app.repository.sec.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author wherrera
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AfSolicitudBl {

    AfSolicitudRepository afSolicitudRepository;
    TxTransaccionBl txTransaccionBl;
    AfRegistroKardexMaterialBl afRegistroKardexMaterialBl;
    AfSolicitudMaterialBl afSolicitudMaterialBl;
    UserRepository userRepository;
    @Transactional(readOnly = true)
    public Page<AfSolicitud> findAll(Pageable pageable) {
        Page<AfSolicitud> list = afSolicitudRepository.findAll(pageable);
        return list;
    }
    public void mergeAfSolicitud(AfSolicitud afSolicitud, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        for (AfSolicitudMaterial afSolicitudMaterial : afSolicitud.getAfSolicitudMaterialList()) {
            afSolicitudMaterial.setIdSolicitud(afSolicitud);
            afSolicitudMaterial.setEstado(StatusEnum.ACTIVE.getStatus());
            // TransactionUtil.setInitTransactionData(afSolicitudMaterial);
        }
        afSolicitudRepository.save(afSolicitud);
    }

    public void aprobarSolicitudMaterial(AfSolicitud afSolicitud, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afSolicitud = findByPkAfSolicitud(afSolicitud.getIdSolicitud());
        afSolicitud.setFechaAutorizacion(new Date());
        afSolicitud.setCatEstadoSolicitud("AUTRZD");
        afSolicitudRepository.save(afSolicitud);
    }

    public void registrarSolicitudDeMaterial(AfSolicitud afSolicitud, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        Optional<User> usuarioSolicitante = userRepository.findById(userRequestVo.getUserId());

        for (AfSolicitudMaterial afSolicitudMaterial : afSolicitud.getAfSolicitudMaterialList()) {
            afSolicitudMaterial.setIdSolicitud(afSolicitud);
            afSolicitudMaterial.setEstado(StatusEnum.ACTIVE.getStatus());
            // TransactionUtil.setInitTransactionData(afSolicitudMaterial);
        }

        afSolicitud.setCatTipoSolicitud("SOLMAT");
        afSolicitud.setCatEstadoSolicitud("PROSOL");
        // ojooooooooooooo
        // afSolicitud.setIdUsuarioSolicitud(usuarioSolicitante);
        // TxCargo superior = usuarioSolicitante.getIdPersona().getIdCargo().getIdCargoPadre();
        // afSolicitud.setIdUsuarioAutorizacion(txUsuarioRepository.findByCargo(superior));

        if (afSolicitud.getFechaSolicitud() == null) {
            afSolicitud.setFechaSolicitud(userRequestVo.getDate());
        }
        afSolicitudRepository.save(afSolicitud);
    }

    public void solicitarSolicitudDeMaterial(AfSolicitud afSolicitud, UserRequestVo userRequestVo) {
        afSolicitud = findByPkAfSolicitud(afSolicitud.getIdSolicitud());
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afSolicitud.setCatEstadoSolicitud("SOLCTD");
        if (afSolicitud.getFechaSolicitud() == null) {
            afSolicitud.setFechaSolicitud(userRequestVo.getDate());
        }
        afSolicitudRepository.save(afSolicitud);
    }

    public void deleteAfSolicitud(AfSolicitud afSolicitud, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afSolicitudRepository.delete(afSolicitud);
    }

    public List<AfSolicitud> findAllActivesByGestionAndCatTipoSolicitudYUsuario(
            Integer gestion, String catTipoSolicitud, TxUsuario idUsuario) {
        return afSolicitudRepository.findAllActivesByGestionAndCatTipoSolicitudYUsuario(
                gestion, catTipoSolicitud, idUsuario);
    }

    public List<AfSolicitud> findAllActivesByGestionAndCatTipoSolicitud(Integer gestion, String catTipoSolicitud) {
        return afSolicitudRepository.findAllActivesByGestionAndCatTipoSolicitud(gestion, catTipoSolicitud);
    }

    public AfSolicitud findByPkAfSolicitud(Integer pk) {
        return afSolicitudRepository.findById(pk).orElseThrow(() -> new DataException("Id inexistente"));
    }
}
