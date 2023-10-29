package gob.gamo.activosf.app.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.OrgEmpleado;
import gob.gamo.activosf.app.domain.OrgUnidad;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.domain.entities.GenDesctabla;
import gob.gamo.activosf.app.domain.entities.Roles;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.EmpleadoRepository;
import gob.gamo.activosf.app.repository.GenDesctablaRespository;
import gob.gamo.activosf.app.repository.OrgUnidadRepository;
import gob.gamo.activosf.app.repository.TxTransaccionRepository;
import gob.gamo.activosf.app.security.mapper.RoleMapper;
import gob.gamo.activosf.app.services.sec.UserService;

/**
 * Session Bean implementation class TxTransaccionBl
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TxTransaccionService {
    private final TxTransaccionRepository txTransaccionRepository;
    private final GenDesctablaRespository tablasRespository;
    private final GenDesctablaService tablasService;
    private final UserService userService;
    private final EmpleadoService empleadoService;
    private final EmpleadoRepository empleadoRepository;
    private final OrgUnidadRepository unidadRepository;

    public TxTransaccion registro(TxTransaccion trx) {
        completeAudit(trx);
        TxTransaccion newTrx = txTransaccionRepository.save(trx);
        completeRels(newTrx);
        return newTrx;
    }

    public TxTransaccion iniciaTrx(User me, TxTransaccion trx) {
        TxTransaccion newTrx = txTransaccionRepository.save(trx);
        return newTrx;
    }

    public TxTransaccion generateTxTransaccion(UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = new TxTransaccion();
        txTransaccion.setTxFecha(userRequestVo.getDate());
        txTransaccion.setTxHost(userRequestVo.getHost());
        txTransaccion.setTxUsuario(userRequestVo.getUserId().intValue());
        txTransaccionRepository.save(txTransaccion);
        return txTransaccion;
    }

    @Transactional(readOnly = true)
    public TxTransaccion generarOperacion(User me, TxTransaccion tx) {
        completeAudit(tx);
        TxTransaccion txTransaccion = new TxTransaccion();

        GenDesctabla tipoOperSub = retOperacion(me, tx);
        OrgEmpleado empl = retEmpleado(me, tx);
        OrgUnidad unid = retUnidad(me, tx.getIdEmpleado(), tx.getIdUnidad());
        OrgEmpleado boss = retEmpleadoBoss(me, tx);

        txTransaccion.setIdUnidad(unid.getIdUnidad());
        txTransaccion.setFechaOper(new Date());
        txTransaccion.setIdUsrreg(me.getUsername());
        txTransaccion.setTxUsuario(me.getIdUnidEmpl());
        txTransaccion.setIdEmpleadoaut(boss.getId());

        return txTransaccion;
    }

    public GenDesctabla retOperacion(User me, TxTransaccion tx) {
        GenDesctabla tipoOper = tablasService.find(tx.getTabTipooperacion(), tx.getTipooperacion());

        if (tx.getTipooperacion() == Constants.TAB_TOP_AF) {
            GenDesctabla tipoOperSub = tablasService.find(tx.getTabTipoopersub(), tx.getTipoopersub());
            RoleMapper.toRoleDtos(me.getRoles());
            // the role is associet to resource, because roles can updates

            boolean existsRec = RoleMapper.recursosToList(me.getRoles()).contains(tipoOperSub.getCodrec());
            if (!existsRec) {
                throw new DataException("Operacion: usuario requiere rol " + tipoOperSub.getCodrec()
                        + ", no esta asociado al tipo de transaccion  " + tipoOperSub.getDesDescrip()
                        + " configurar en desctabla.");
            }
            return tipoOperSub;
        } else if (tx.getTipooperacion() == Constants.TAB_TOP_ALM) {

        }

        throw new DataException(
                "Operacion: no se pudo determinar la operacion " + tx.getTipooperacion() + " " + tx.getTipoopersub());
    }

    public OrgEmpleado retEmpleado(User me, TxTransaccion tx) {
        OrgEmpleado empl = empleadoService.findByIdAct(tx.getIdEmpleado());
        if (me.getIdUnidEmpl() == null || me.getIdUnidEmpl() != empl.getIdPersona()) {
            throw new DataException("Operacion: Empleado difiere con la Id de la operacion " + tx.getIdEmpleado());
        }

        return empl;
    }

    public OrgEmpleado retEmpleadoBoss(User me, TxTransaccion tx) {
        OrgEmpleado empl = empleadoService.empleadoBoss(tx.getIdUnidad(), true).orElseThrow(
                () -> new DataException(
                        "Operacion: No se pudo encontrar empleado superior para unidad " + tx.getIdUnidad()));

        return empl;
    }

    public OrgUnidad retUnidad(User me, Integer idEmpleado, Integer idUnidad) {
        OrgEmpleado empl = empleadoService.empleadoActivo(me.getIdUnidEmpl()).orElseThrow(
                () -> new DataException("ID Persona de usuario inexistente en empleados " + me.getIdUnidEmpl()));
        if (empl.getIdUnidad() == null || empl.getIdUnidad() != idUnidad) {
            throw new DataException("Operacion: Empleado difiere con la Id de la operacion " + idEmpleado);
        }
        return empl.getUnidad();
    }

    public static void completeAudit(TxTransaccion trx) {
        trx.setTxFecha(new Date());
        trx.setTxUsuario(0);
        trx.setTxHost("");
    }

    public void crearUpdKardex(User me, TxTransaccion tx) {

    }

    public TxTransaccion completeRels(TxTransaccion tx) {
        if (tx.getTipooperacion() != null)
            tablasRespository.findByDesCodtabAndDesCodigo(tx.getTabTipooperacion(), tx.getTipooperacion())
                    .ifPresent(g -> tx.setTipooperaciondesc(g));
        if (tx.getTipoopersub() != null)
            tablasRespository.findByDesCodtabAndDesCodigo(tx.getTabTipoopersub(), tx.getTipoopersub())
                    .ifPresent(g -> tx.setTipoopersubdesc(g));
        if (tx.getIdUnidad() != null)
            unidadRepository.findById(tx.getIdUnidad()).ifPresent(g -> tx.setUnidad(g));
        if (tx.getIdEmpleado() != null)
            empleadoRepository.findById(tx.getIdEmpleado()).ifPresent(g -> tx.setEmpleado(g));
        return tx;
    }
    public TxTransaccion findById(Integer id){
        return txTransaccionRepository.findById(id).orElseThrow(() -> new DataException("Registro inexistente"));
    }
}
