package gob.gamo.activosf.app.services;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.OrgEmpleado;
import gob.gamo.activosf.app.domain.OrgUnidad;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.domain.TxTransdet;
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

    @Transactional
    public TxTransaccion registro(User me, TxTransaccion trx) {
        trx.setIdTransaccion(null);
        trx.setTabTareaoperacion(Constants.TAB_TASK);
        trx.setTareaoperacion(Constants.TAB_TASK_REG);

        validar(trx);
        retEmpleado(me, trx);
        retOperacion(me, trx);
        empleadoService.retUnidad(me, trx.getIdEmpleado(), trx.getIdUnidad());
        completeAudit(trx);

        TxTransaccion newTrx = txTransaccionRepository.save(trx);
        completeRels(newTrx);
        return newTrx;
    }

    @Transactional(readOnly = true)
    public TxTransaccion generarOperacion0(User me, TxTransaccion tx) {
        completeAudit(tx);
        TxTransaccion txTransaccion = new TxTransaccion();

        GenDesctabla tipoOperSub = retOperacion(me, tx);
        OrgEmpleado empl = retEmpleado(me, tx);
        OrgUnidad unid = empleadoService.retUnidad(me, tx.getIdEmpleado(), tx.getIdUnidad());
        OrgEmpleado boss = retEmpleadoBoss(me, tx);

        txTransaccion.setIdUnidad(unid.getIdUnidad());
        txTransaccion.setFechaOper(new Date());
        txTransaccion.setIdUsrreg(me.getUsername());
        txTransaccion.setTxUsuario(me.getIdUnidEmpl());
        txTransaccion.setIdEmpleadoaut(boss.getId());

        return txTransaccion;
    }

    public GenDesctabla retOperacion(User me, TxTransaccion tx) {
        GenDesctabla modulo = tablasService.find(tx.getTabTipoopersub(), tx.getTipoopersub());
        GenDesctabla tipoOper = tablasService.find(tx.getTabTipooperacion(), tx.getTipooperacion());

        // RoleMapper.toRoleDtos(me.getRoles());
        if (!StringUtils.isBlank(tipoOper.getDesCodrec())) {
            boolean existsRec = RoleMapper.recursosToList(me.getRoles()).contains(tipoOper.getDesCodrec());
            if (!existsRec) {
                throw new DataException("Operacion: usuario requiere rol " + tipoOper.getDesCodrec()
                        + ", no esta asociado al tipo de transaccion  " + tipoOper.getDesDescrip()
                        + " configurar en desctabla.");
            }
        }
        return tipoOper;
    }

    public OrgEmpleado retEmpleado(User me, TxTransaccion tx) {
        return empleadoService.empleadoIsActivo(me.getIdUnidEmpl());
    }

    public OrgEmpleado retEmpleadoBoss(User me, TxTransaccion tx) {
        OrgEmpleado empl = empleadoService.empleadoBoss(tx.getIdUnidad(), true).orElseThrow(
                () -> new DataException(
                        "Operacion: No se pudo encontrar empleado superior para unidad " + tx.getIdUnidad()));

        return empl;
    }

    public static void completeAudit(TxTransaccion trx) {
        trx.setTxFecha(new Date());
        trx.setTxUsuario(0);
        trx.setTxHost("");
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

    public TxTransaccion findById(Integer id) {
        return txTransaccionRepository.findById(id).orElseThrow(() -> new DataException("Registro inexistente"));
    }

    /************* TIPOS DE OPERACION ************ */
    @Transactional(readOnly = true)
    public TxTransaccion prepareOperation(User me, TxTransaccion txIn) {
        validarIni(txIn);

        TxTransaccion tx = new TxTransaccion();
        tx.setTabTipoopersub(Constants.TAB_MD);        
        tx.setTipoopersub(txIn.getTipoopersub());
        tx.setTipooperacion(txIn.getTipooperacion());

        Integer modulo = tx.getTipoopersub();
        tx.setTabTareaoperacion(Constants.TAB_TASK);
        tx.setTareaoperacion(Constants.TAB_TASK_PRE);

        if (modulo == Constants.TAB_MD_AF) {
            modActivosf(me, tx);
        } else if (modulo == Constants.TAB_MD_AL) {
            modAlmacenes(me, tx);
        } else {
            throw new DataException("Modulo no implementado " + txIn.getTipoopersub());
        }

        OrgEmpleado empl = retEmpleado(me, tx);

        tx.setIdEmpleado(empl.getId());
        tx.setIdUnidad(empl.getIdUnidad());
        tx.setIdUsrreg(me.getUsername());
        tx.setFechaOper(new Date());
        //tx.setTxHost(me.getToken());

        completeRels(tx);
        return tx;
    }

    @Transactional(readOnly = true)
    public TxTransaccion prepareOperationdet(User me, Integer id) {
        TxTransaccion tx = findById(id);
        validar(tx);
        validarOwner(me, tx);

        return tx;
    }

    public TxTransaccion modActivosf(User me, TxTransaccion tx) {
        Integer tipoOper = tx.getTipooperacion();
        tx.setTabTipooperacion(tx.getTipoopersub());

        return tx;
    }

    public TxTransaccion modAlmacenes(User me, TxTransaccion tx) {
        Integer tipoOper = tx.getTipooperacion();
        tx.setTabTipooperacion(tx.getTipoopersub());

        if (tipoOper == Constants.TAB_ALM_CREKARDEX) {
        } else if (tipoOper == Constants.TAB_ALM_INGRESO) {
        } else if (tipoOper == Constants.TAB_ALM_SALIDA) {

        } else if (tipoOper == Constants.TAB_ALM_ACTKRD) {

        }
        return tx;
    }

 

    public static void validarIni(TxTransaccion tx) {
        if (tx.getTipoopersub() == null) {
            throw new DataException("Modulo requerido");
        }
        if (tx.getTipooperacion() == null) {
            throw new DataException("Tipo operacion requerido");
        }
    }

    public static void validar(TxTransaccion tx) {
        validarIni(tx);
        if (tx.getIdEmpleado() == null) {
            throw new DataException("ID empleado requerido");
        }
        if (tx.getFechaOper() == null) {
            throw new DataException("Fecha operación requerido");
        }
        if (tx.getIdUnidad() == null) {
            throw new DataException("Unidad requerido");
        }
    }

    public void validarOwner(User me, TxTransaccion tx) {
        OrgEmpleado empl = retEmpleado(me, tx);
        if (empl.getId() != tx.getIdEmpleado()) {
            throw new DataException("Operacion " + tx.getIdTransaccion() + " no pertenece al usuario "
                    + me.getUsername() + ", no puede modificar");
        }
    }

    /************************ OLD ************ */
    public TxTransaccion generateTxTransaccion(UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = new TxTransaccion();
        txTransaccion.setTxFecha(userRequestVo.getDate());
        txTransaccion.setTxHost(userRequestVo.getHost());
        txTransaccion.setTxUsuario(userRequestVo.getUserId().intValue());
        txTransaccionRepository.save(txTransaccion);
        return txTransaccion;
    }

}
