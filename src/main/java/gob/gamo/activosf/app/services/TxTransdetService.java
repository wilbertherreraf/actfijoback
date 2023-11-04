package gob.gamo.activosf.app.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.AfItemaf;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TxTransdetService {
    private final TxTransdetRepository transdetRepository;
    private final AfItemafService itemafService;
    private final AfItemafRepository itemRepositoryEntity;
    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoService empleadoService;
    private final OrgUnidadRepository unidadRepository;
    private final GenDesctablaRespository tablasRespository;
    private final GenDesctablaService tablasService;

    @Transactional(readOnly = true)

    public Page<TxTransdet> getAll(TxTransaccion trx, Pageable pageable) {
        return transdetRepository.findByIdTransaccion(trx.getIdTransaccion(), pageable).map(x -> completeRels(x));
    }

    @Transactional
    public TxTransdet create(User me, TxTransaccion trx, TxTransdet trxdet) {
        trxdet.setIdTransdet(null);
        trxdet.setMontoCont(BigDecimal.ZERO);

        normalizerData(trxdet);
        validar(me, trx, trxdet);

        completeAudit(trxdet);
        trxdet.setFechaOper(new Date());
        trxdet.setIdTransaccion(trx.getIdTransaccion());
        TxTransdet newTrx = transdetRepository.save(trxdet);
        completeRels(newTrx);
        return newTrx;
    }

    @Transactional
    public TxTransdet update(User me, TxTransaccion trx, TxTransdet trxdet) {
        trxdet.setFechaOper(new Date());
        trxdet.setIdTransaccion(trx.getIdTransaccion());
        TxTransdet newTrx = transdetRepository.save(trxdet);
        completeRels(newTrx);
        return newTrx;
    }

    @Transactional
    public void reversa(User me, TxTransaccion trx, Integer id) {
        TxTransdet trxdetNew = findById(id);
        TxTransdet trxdet = clone(trxdetNew);
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
    public TxTransdet prepareNewTrxdet(User me, TxTransaccion trx) {
        TxTransdet trxdet = TxTransdetService.nuevoReg();
        return trxdet;
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
            modAlmacenesDet(me, trx, trxdet);
        } else {
            throw new DataException("Modulo no implementado " + modulo);
        }
        return trxdet;
    }

    public TxTransaccion modAlmacenesDet(User me, TxTransaccion tx, TxTransdet trxdet) {
        Integer tipoOper = tx.getTipooperacion();
        trxdet.setTabOpermayor(Constants.TAB_MY);
        if (tipoOper == Constants.TAB_ALM_CREKARDEX) {
            trxdet.setOpermayor(Constants.TAB_MY_SALDOINICIAL);
        } else if (tipoOper == Constants.TAB_ALM_INGRESO) {
            trxdet.setOpermayor(Constants.TAB_MY_INGRESO_STOCK);
        } else if (tipoOper == Constants.TAB_ALM_SALIDA) {
            trxdet.setOpermayor(Constants.TAB_MY_SALIDA_STOCK);
        } else if (tipoOper == Constants.TAB_ALM_ACTKRD) {

        }
        return tx;
    }

    @Transactional(readOnly = true)
    public TxTransdet updInfoItemaf(User me, TxTransaccion trx, TxTransdet trxdetIn) {
        Integer modulo = trx.getTipoopersub();
        Integer tipoOper = trx.getTipooperacion();

        TxTransdet trxdet = prepareTrxdet(me, trx, trxdetIn);
        trxdet.setIdTransaccion(trx.getIdTransaccion());

        if (modulo == Constants.TAB_MD_AF) {

        } else if (modulo == Constants.TAB_MD_AL) {
            modAlmUpdItemaf(me, trx, trxdet);
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

    private TxTransdet modAlmUpdItemaf(User me, TxTransaccion tx, TxTransdet trxdet) {
        BigDecimal montoOrig = BigDecimal.ZERO;
        BigDecimal tipoCambio = BigDecimal.ZERO;
        BigDecimal montoDesc = BigDecimal.ZERO;
        BigDecimal montoCont = BigDecimal.ZERO;
        BigDecimal monto = BigDecimal.ZERO;
        BigDecimal cantidad = BigDecimal.ZERO;
        Integer tipoCargo = 0;
        Integer unidadmed = 0;
        Integer metodocalc = 0;
        Integer detoperacion = 0;
        Integer opermayor = 0;
        String glosa = "";

        normalizerData(trxdet);
        modAlmacenesUpdToper(me, tx, trxdet);

        if (trxdet.getIdItemaf() != null) {
            AfItemaf itemaf = itemafService.findById(trxdet.getIdItemaf());
            glosa = itemaf.getNombre();

            Integer tipoOper = tx.getTipooperacion();
            if (tipoOper == Constants.TAB_ALM_CREKARDEX) {
                montoOrig = trxdet.getMontoOrig();
                cantidad = trxdet.getCantidad();
                monto = trxdet.getMonto();
            } else if (tipoOper == Constants.TAB_ALM_INGRESO) {
                montoOrig = trxdet.getMontoOrig();
                cantidad = trxdet.getCantidad();
                monto = trxdet.getMonto();
            } else if (tipoOper == Constants.TAB_ALM_SALIDA) {
                montoOrig = itemaf.getPunit();
                cantidad = trxdet.getCantidad().intValue() >= itemaf.getStock() ? BigDecimal.valueOf(itemaf.getStock())
                        : trxdet.getCantidad();
                monto = cantidad.multiply(montoOrig);
            } else if (tipoOper == Constants.TAB_ALM_ACTKRD) {

            }
            completeRels(trxdet);
            glosa = trxdet.getOpermayordesc().getDesDescrip() + " - " + glosa;
        }

        trxdet.setCantidad(cantidad);
        trxdet.setMontoOrig(montoOrig);
        trxdet.setTipoCambio(tipoCambio);
        trxdet.setMontoDesc(montoDesc);
        trxdet.setMontoCont(montoCont);
        trxdet.setMonto(monto);
        trxdet.setGlosa(glosa);

        return trxdet;
    }

    public TxTransaccion modAlmacenesUpdToper(User me, TxTransaccion tx, TxTransdet trxdet) {
        Integer tipoOper = tx.getTipooperacion();
        trxdet.setTabOpermayor(Constants.TAB_MY);
        if (tipoOper == Constants.TAB_ALM_CREKARDEX) {
            trxdet.setOpermayor(Constants.TAB_MY_SALDOINICIAL);
        } else if (tipoOper == Constants.TAB_ALM_INGRESO) {
            trxdet.setOpermayor(Constants.TAB_MY_INGRESO_STOCK);
        } else if (tipoOper == Constants.TAB_ALM_SALIDA) {
            trxdet.setOpermayor(Constants.TAB_MY_SALIDA_STOCK);
        } else if (tipoOper == Constants.TAB_ALM_ACTKRD) {

        }
        return tx;
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
            tablasRespository.findByDesCodtabAndDesCodigo(td.getTabTipooperacion(), td.getTipooperacion())
                    .ifPresent(g -> td.setTipooperaciondesc(g));

        if (td.getOpermayor() != null)
            tablasRespository.findByDesCodtabAndDesCodigo(td.getTabOpermayor(), td.getOpermayor())
                    .ifPresent(g -> td.setOpermayordesc(g));
        return td;
    }

    public void validar(User me, TxTransaccion tx, TxTransdet trxdet) {
        Integer modulo = tx.getTipoopersub();
        Integer tipoOper = tx.getTipooperacion();

        if (trxdet.getIdTransaccion() == null) {
            throw new DataException("ID transaccion requerido");
        }

        if (trxdet.getIdUnidad() == null) {
            throw new DataException("ID unidad requerido");
        }

        if (modulo == Constants.TAB_MD_AF) {

        } else if (modulo == Constants.TAB_MD_AL) {
            validarAlm(me, tx, trxdet);
        } else {
            throw new DataException("Modulo no implementado " + modulo);
        }

    }

    public void validarAlm(User me, TxTransaccion tx, TxTransdet trxdet) {
        if (trxdet.getIdItemaf() == null) {
            throw new DataException("Item requerido");
        }

        if (trxdet.getCantidad().compareTo(BigDecimal.ZERO) == 0) {
            throw new DataException("Cantidad requerida");
        }
        if (trxdet.getMontoOrig().compareTo(BigDecimal.ZERO) == 0) {
            throw new DataException("Precio unit requerido");
        }

        if (trxdet.getMonto().compareTo(BigDecimal.ZERO) == 0) {
            throw new DataException("monto requerido");
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

    public static void normalizerData(TxTransdet trxdet) {
        trxdet.setMontoOrig(trxdet.getMontoOrig() == null ? BigDecimal.ZERO : trxdet.getMontoOrig());
        trxdet.setTipoCambio(trxdet.getTipoCambio() == null ? BigDecimal.ZERO : trxdet.getTipoCambio());
        trxdet.setMontoDesc(trxdet.getMontoDesc() == null ? BigDecimal.ZERO : trxdet.getMontoDesc());
        trxdet.setMontoCont(trxdet.getMontoCont() == null ? BigDecimal.ZERO : trxdet.getMontoCont());
        trxdet.setCantidad(trxdet.getCantidad() == null ? BigDecimal.ZERO : trxdet.getCantidad());
        trxdet.setMonto(trxdet.getMonto() == null ? BigDecimal.ZERO : trxdet.getMonto());
    }

    public static void completeAudit(TxTransdet trx) {
        trx.setTxFecha(new Date());
        trx.setTxUsuario(0);
        trx.setTxHost("");
    }

    public static TxTransdet nuevoReg() {
        TxTransdet td = new TxTransdet();
        td.setCantidad(BigDecimal.ZERO);
        td.setMonedaamtorig(1);
        td.setMonto(BigDecimal.ZERO);
        td.setMontoOrig(BigDecimal.ZERO);
        td.setMontoCont(BigDecimal.ZERO);
        td.setMontoDesc(BigDecimal.ZERO);
        td.setTipoCambio(BigDecimal.ONE);
        td.setFechaOper(new Date());
        return td;
    }

    public static TxTransdet clone(TxTransdet t) {
        return TxTransdet.builder().idTransaccion(t.getIdTransaccion()).idCorrelativo(t.getIdCorrelativo())
                .tabDetoperacion(t.getTabDetoperacion()).detoperacion(t.getDetoperacion())
                .tabTareaoperacion(t.getTabTareaoperacion()).tareaoperacion(t.getTareaoperacion())
                .tabOpermayor(t.getTabOpermayor()).opermayor(t.getOpermayor()).idItemaf(t.getIdItemaf())
                .glosa(t.getGlosa()).monto(t.getMonto()).montoOrig(t.getMontoOrig())
                .tabMonedaamtorig(t.getTabMonedaamtorig()).monedaamtorig(t.getMonedaamtorig())
                .tipoCambio(t.getTipoCambio()).montoDesc(t.getMontoDesc()).montoCont(t.getMontoCont())
                .tipoCargo(t.getTipoCargo()).cantidad(t.getCantidad()).tabUnidadmed(t.getTabUnidadmed())
                .unidadmed(t.getUnidadmed()).tabMetodocalc(t.getTabMetodocalc()).metodocalc(t.getMetodocalc())
                .fechaOper(t.getFechaOper()).fechaValor(t.getFechaValor()).tabTipooperacion(t.getTabTipooperacion())
                .tipooperacion(t.getTipooperacion()).idEmpleado(t.getIdEmpleado()).idEmpleadoaut(t.getIdEmpleadoaut())
                .idUnidad(t.getIdUnidad()).idUsrreg(t.getIdUsrreg()).idUsraut(t.getIdUsraut())
                .idTransdetpadre(t.getIdTransdetpadre()).idTrxorigen(t.getIdTrxorigen())
                .build();
    }
}
