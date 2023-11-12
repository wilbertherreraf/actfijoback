package gob.gamo.activosf.app.services;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.OrgEmpleado;
import gob.gamo.activosf.app.domain.OrgUnidad;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.domain.TxTransdet;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.AfItemafRepository;
import gob.gamo.activosf.app.repository.EmpleadoRepository;
import gob.gamo.activosf.app.repository.GenDesctablaRespository;
import gob.gamo.activosf.app.repository.OrgUnidadRepository;
import gob.gamo.activosf.app.repository.TxTransdetRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TxTransdetService {
    private final TxTransdetRepository transdetRepository;
    private final TxAlmService txAlmService;
    private final AfItemafService itemafService;
    private final AfItemafRepository itemRepositoryEntity;
    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoService empleadoService;
    private final OrgUnidadRepository unidadRepository;
    private final GenDesctablaRespository tablasRespository;
    private final GenDesctablaService tablasService;

    @Transactional(readOnly = true)
    public Page<TxTransdet> getAll(TxTransaccion trx, Pageable pageable) {
        return transdetRepository
                .findByIdTransaccion(trx.getIdTransaccion(), pageable)
                .map(x -> completeRels(x));
    }

    @Transactional
    public TxTransdet create(User me, TxTransaccion trx, TxTransdet trxdet) {
        trxdet.setMontoCont(BigDecimal.ZERO);
        TxTransdet.normalizerData(trxdet);

        trxdet.setFechaOper(new Date());
        trxdet.setIdTransaccion(trx.getIdTransaccion());
        validar(me, trx, trxdet);
        TxAlmService.completeAudit(trxdet);
        TxTransdet newTrx = transdetRepository.save(trxdet);
        completeRels(newTrx);
        return newTrx;
    }

    @Transactional
    public TxTransdet update(User me, TxTransaccion trx, TxTransdet trxdet) {
        trxdet.setFechaOper(new Date());
        trxdet.setIdTransaccion(trx.getIdTransaccion());
        TxTransdet.normalizerData(trxdet);
        TxTransdet newTrx = transdetRepository.save(trxdet);
        completeRels(newTrx);
        return newTrx;
    }

    @Transactional
    public void reversa(User me, TxTransaccion trx, Integer id) {
        TxTransdet trxdetNew = findById(id);
        TxTransdet trxdet = TxTransdet.clone(trxdetNew);
    }

    @Transactional
    public void delete(User me, TxTransaccion trx, Integer id) {
        TxTransdet trxdet = findById(id);
        if ((trx.getIdTransaccion() != trxdet.getIdTransaccion())) {
            throw new DataException("FATAL!: Incosistencia de ids " + trx.getIdTransaccion() + " " + id + " -> "
                    + trxdet.getIdTransaccion());
        }
        validarEstado(me, trx, trxdet);
        transdetRepository.delete(trxdet);
    }

    @Transactional(readOnly = true)
    public TxTransdet prepareTrxdet(User me, TxTransaccion trx, TxTransdet trxdet) {
        Integer modulo = trx.getTipoopersub();
        Integer tipoOper = trx.getTipooperacion();

        OrgEmpleado empl = empleadoService.empleadoIsActivo(me.getIdUnidEmpl());
        OrgUnidad unid = empleadoService.retUnidad(me, empl.getId(), trx.getIdUnidad());
        trxdet.setIdEmpleado(empl.getId());
        trxdet.setIdUnidad(unid.getIdUnidad());
        trxdet.setTabTipooperacion(trx.getTabTipooperacion());
        trxdet.setTipooperacion(tipoOper);
        trxdet.setTabTareaoperacion(Constants.TAB_TASK);
        trxdet.setTareaoperacion(Constants.TAB_TASK_PRE);
        trxdet.setTabUnidadmed(Constants.TAB_UND);
        trxdet.setUnidadmed(Constants.TAB_UND_UND);

        if (modulo == Constants.TAB_MD_AF) {

        } else if (modulo == Constants.TAB_MD_AL) {
            // modAlmacenesDet(me, trx, trxdet);
        } else {
            throw new DataException("Modulo no implementado " + modulo);
        }
        return trxdet;
    }

    @Transactional(readOnly = true)
    public TxTransdet updInfoItemaf(User me, TxTransaccion trx, TxTransdet trxdetIn) {
        Integer modulo = trx.getTipoopersub();
        Integer tipoOper = trx.getTipooperacion();

        TxTransdet trxdet = prepareTrxdet(me, trx, trxdetIn);
        trxdet.setIdTransaccion(trx.getIdTransaccion());

        if (modulo == Constants.TAB_MD_AF) {

        } else if (modulo == Constants.TAB_MD_AL) {
            txAlmService.updDataForPrepare(me, trx, trxdet);
        } else {
            throw new DataException("Modulo no implementado " + modulo);
        }
        try {
            validar(me, trx, trxdet);
            trxdet.setTabTareaoperacion(Constants.TAB_TASK);
            trxdet.setTareaoperacion(Constants.TAB_TASK_VER);
        } catch (Exception e) {
            log.warn("en prepare registro no valido" + e.getMessage());
        }
        return trxdet;
    }

    public TxTransdet findById(Integer id) {
        if (id == null) {
            throw new DataException("Id de transaccion nulo");
        }
        return transdetRepository.findById(id).orElseThrow(() -> new DataException("Registro inexistente " + id));
    }

    public TxTransdet completeRels(TxTransdet td) {
        if (td.getIdItemaf() != null) {
            itemRepositoryEntity.findById(td.getIdItemaf()).ifPresent(r -> td.setItemaf(r));
        }
        if (td.getIdEmpleado() != null) {
            empleadoRepository.findById(td.getIdEmpleado()).ifPresent(g -> td.setEmpleado(g));
        }

        if (td.getTipooperacion() != null)
            tablasRespository
                    .findByDesCodtabAndDesCodigo(td.getTabTipooperacion(), td.getTipooperacion())
                    .ifPresent(g -> td.setTipooperaciondesc(g));

        if (td.getOpermayor() != null)
            tablasRespository
                    .findByDesCodtabAndDesCodigo(td.getTabOpermayor(), td.getOpermayor())
                    .ifPresent(g -> td.setOpermayordesc(g));
        return td;
    }

    public void validar(User me, TxTransaccion tx, TxTransdet trxdet) {
        Integer modulo = tx.getTipoopersub();

        if (trxdet.getIdTransaccion() == null) {
            throw new DataException("ID transaccion requerido");
        }

        if (trxdet.getIdEmpleado() == null) {
            throw new DataException("ID empleado requerido");
        }

        if (modulo == Constants.TAB_MD_AF) {

        } else if (modulo == Constants.TAB_MD_AL) {
            TxAlmService.validar(me, tx, trxdet);
        } else {
            throw new DataException("Modulo no implementado " + modulo);
        }
    }

    public void validarEstadoNoCont(User me, TxTransaccion tx, TxTransdet trxdet) {
        if (trxdet.getMontoCont().compareTo(BigDecimal.ZERO) == 0) {
            throw new DataException("Operación no aprobada");
        }
    }

    public void validarEstado(User me, TxTransaccion tx, TxTransdet trxdet) {
        if (trxdet.getMontoCont() != null && trxdet.getMontoCont().compareTo(BigDecimal.ZERO) != 0) {
            throw new DataException("Operación contabilizada");
        }
    }

    public void updFromItemaf(TxTransdet trxdet) {
        trxdet.setMontoOrig(trxdet.getMontoOrig() == null ? BigDecimal.ZERO : trxdet.getMontoOrig());
        trxdet.setTipoCambio(trxdet.getTipoCambio() == null ? BigDecimal.ZERO : trxdet.getTipoCambio());
        trxdet.setMontoDesc(trxdet.getMontoDesc() == null ? BigDecimal.ZERO : trxdet.getMontoDesc());
        // trxdet.setMontoCont(trxdet.getMontoCont() == null ? BigDecimal.ZERO :
        // trxdet.getMontoCont());
        trxdet.setCantidad(trxdet.getCantidad() == null ? BigDecimal.ZERO : trxdet.getCantidad());
        trxdet.setMonto(trxdet.getMonto() == null ? BigDecimal.ZERO : trxdet.getMonto());
    }

    public static void completeAudit(TxTransdet trx) {
        trx.setTxFecha(new Date());
        trx.setTxUsuario(0);
        trx.setTxHost("");
    }
}
