/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.domain.AfActivoFijo;
import gob.gamo.activosf.app.domain.AfAlmacen;
import gob.gamo.activosf.app.domain.AfGestion;
import gob.gamo.activosf.app.domain.AfKardexMaterial;
import gob.gamo.activosf.app.domain.AfMaterial;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.AfAlmacenRepository;
import gob.gamo.activosf.app.repository.AfGestionRepository;
import gob.gamo.activosf.app.repository.AfKardexMaterialRepository;
import gob.gamo.activosf.app.repository.AfMaterialRepository;
import gob.gamo.activosf.app.utils.UtilsDate;

/**
 *
 * @author wherrera
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AfGestionBl {

    AfGestionRepository afGestionRepository;
    AfFamiliaActivoBl afFamiliaActivoBl;
    AfKardexMaterialBl afKardexMaterialBl;
    TxTransaccionService txTransaccionBl;
    AfActivoFijoBl afActivoFijoBl;
    AfAlmacenRepository afAlmacenRepository;
    AfMaterialRepository afMaterialRepository;
    AfKardexMaterialRepository afKardexMaterialRepository;

    @Transactional(readOnly = true)
    public Page<AfGestion> findAll(Pageable pageable) {
        Page<AfGestion> list = afGestionRepository.findAll(pageable);
        return list;
    }

    public void cerrarGestion(AfGestion afGestion, UserRequestVo userRequestVo) {
        List<AfActivoFijo> afActivoFijoList;
        afGestion.setCatEstadoGestion("CERRADA");
        LocalDate finGestion = LocalDate.of(afGestion.getGestion(), 12, 31);
        Date inicioGestion = UtilsDate.dateOf(afGestion.getGestion() + 1, 1, 1);
        afActivoFijoList = afActivoFijoBl.findAllActivesAfActivoFijoByGestion(afGestion.getGestion());
        Date d = Date.from(finGestion.atStartOfDay(ZoneId.systemDefault()).toInstant());
        afActivoFijoBl.realizarCalculosContablesListAsync(afActivoFijoList, d);
        for (AfActivoFijo afActivoFijo : afActivoFijoList) {
            afActivoFijo.setCostoActual(afActivoFijo.getCalculoContableVo().getValorActual());
            afActivoFijo.setDepAcumuladaHistorico(afActivoFijo.getDepAcumuladaActual());
            afActivoFijo.setDepAcumuladaActual(
                    afActivoFijo.getCalculoContableVo().getDepreciacionAcumulada());

            afActivoFijo.setFechaActual(inicioGestion);
            afActivoFijo.setGestion((afGestion.getGestion() + 1));
            afActivoFijoBl.mergeAfActivoFijo(afActivoFijo, userRequestVo);
        }

        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);

        List<AfAlmacen> almacenes = afAlmacenRepository.findAll(); // .findAllActives();
        List<AfMaterial> materiales = afMaterialRepository.findAllActives();

        for (AfAlmacen afAlmacen : almacenes) {
            for (AfMaterial afMaterial : materiales) {
                AfKardexMaterial afKardexMaterial = afKardexMaterialRepository
                        .findActiveByGestionAndMaterialAndAlmacen(afGestion.getGestion(), afMaterial, afAlmacen)
                        .orElse(null);
                if (afKardexMaterial == null) {
                    afKardexMaterial = new AfKardexMaterial();
                    afKardexMaterial.setGestion(afGestion.getGestion());
                    afKardexMaterial.setIdMaterial(afMaterial);
                    afKardexMaterial.setIdAlmacen(afAlmacen);
                    afKardexMaterial.setSaldoCantidad(0);
                    if (afAlmacen.getEsValorado()) {
                        afKardexMaterial.setSaldoImporte(BigDecimal.ZERO);
                    }
                    afKardexMaterial.setEstado("A");
                    afKardexMaterialRepository.save(afKardexMaterial);
                }
            }
        }

        List<AfKardexMaterial> afKardexMaterialList =
                afKardexMaterialRepository.findAllActivesByGestion(afGestion.getGestion());
        for (AfKardexMaterial afKardexMaterial : afKardexMaterialList) {
            afKardexMaterialRepository.save(afKardexMaterial.getNuevaGestionConSaldoInicial(txTransaccion));
        }

        mergeAfGestion(afGestion, userRequestVo);
    }

    public void definirVigente(AfGestion afGestion, UserRequestVo userRequestVo) {
        List<AfGestion> afGestions = findAllActivesAfGestion();
        for (AfGestion gestion : afGestions) {
            if (gestion.equals(afGestion)) {
                gestion.setVigente(true);
            } else {
                gestion.setVigente(false);
            }
            mergeAfGestion(gestion, userRequestVo);
        }
    }

    public AfGestion getMaxAfGestion() {
        return afGestionRepository
                .getMaxAfGestion()
                .orElseThrow(() -> new DataException("no existe ninguna gestion anterior"));
    }
    ;

    public AfGestion getByGestion(Integer gestion) {
        return afGestionRepository.getByGestion(gestion);
    }
    ;

    public void mergeAfGestion(AfGestion afGestion, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afGestionRepository.save(afGestion);
    }

    public void persistAfGestion(AfGestion afGestion, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afGestionRepository.save(afGestion);
    }

    public AfGestion getGestionVigente() {
        return afGestionRepository
                .getAfGestionVigente(true)
                .orElseThrow(() -> new DataException("no existe gestion vigente"));
    }

    public void deleteAfGestion(AfGestion afGestion, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afGestionRepository.delete(afGestion);
    }

    public List<AfGestion> findAllActivesAfGestion() {
        return afGestionRepository.findAll(); // .findAllActives();
    }

    public AfGestion findByPkAfGestion(Integer pk) {
        return afGestionRepository.findById(pk).orElseThrow(() -> new DataException("id inexistente gestion"));
    }
}
