package gob.gamo.activosf.app.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.AfItemaf;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.domain.TxTransdet;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.AfItemafRepository;
import gob.gamo.activosf.app.repository.EmpleadoRepository;
import gob.gamo.activosf.app.repository.GenDesctablaRespository;
import gob.gamo.activosf.app.repository.OrgUnidadRepository;
import gob.gamo.activosf.app.repository.TxTransaccionRepository;
import gob.gamo.activosf.app.repository.TxTransdetRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TxAlmService {
    private final TxTransaccionRepository txTransaccionRepository;
    private final TxTransdetRepository transdetRepository;
    private final AfItemafService itemafService;
    private final AfItemafRepository itemRepositoryEntity;
    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoService empleadoService;
    private final OrgUnidadRepository unidadRepository;
    private final GenDesctablaRespository tablasRespository;
    private final GenDesctablaService tablasService;

    @Transactional
    public TxTransdet saveDetalle(User me, TxTransaccion tx, TxTransdet trxdet) {
        completeAudit(trxdet);
        trxdet.setIdEmpleado(me.getIdUnidEmpl());
        validar(me, tx, trxdet);
        return transdetRepository.save(trxdet);
    }

    @Transactional
    public TxTransdet saveDetalleKdx(User me, TxTransaccion tx, TxTransdet trxdet) {
        completeAudit(trxdet);
        trxdet.setIdEmpleado(me.getIdUnidEmpl());
        validarKdx(me, tx, trxdet);
        return transdetRepository.save(trxdet);
    }

    @Transactional
    public TxTransaccion saveTransacc(User me, TxTransaccion tx) {
        completeAudit(tx);
        tx.setIdEmpleado(me.getIdUnidEmpl());
        validar(tx);
        return txTransaccionRepository.save(tx);
    }

    @Transactional
    public void modAlmProcess(User me, TxTransaccion tx) {
        log.info("inicio process ALM {}", tx.getIdTransaccion());
        List<TxTransdet> list = transdetRepository.findByIdTransaccion(tx.getIdTransaccion());
        for (TxTransdet txTransdet : list) {
            modAlmUpdStock(me, tx, txTransdet);
        }
        log.info("Fin process ALM {}", tx.getIdTransaccion());
        // finishTransac(me, tx);
    }

    private void finishTransac(User me, TxTransaccion tx) {
        tx.setTareaoperacion(Constants.TAB_TASK_PRO);
        tx.setImporte(tx.getMonto());

        List<TxTransdet> list = transdetRepository.findByIdTransaccion(tx.getIdTransaccion());
        for (TxTransdet txTransdet : list) {
            txTransdet.setMontoCont(txTransdet.getMonto());
            txTransdet.setTareaoperacion(Constants.TAB_TASK_PRO);
        }
    }

    @Transactional
    public void modAlmUpdStock(User me, TxTransaccion tx, TxTransdet trxdet) {
        // actualizacion por FIFO, recuperar items segun ingresos
        // recover information of fixed active
        // ingreso por item, precio io, cantidad - IVA
        // retiro de item precio compra, cantidad - IVA
        // registro iva
        // registro descuento sin iva

        Integer idItemaf = trxdet.getIdItemaf();
        Integer tipoOper = tx.getTipooperacion();

        if (tipoOper == Constants.TAB_ALM_CREKARDEX) {
            processKardex(me, tx, trxdet);
        } else if (tipoOper == Constants.TAB_ALM_INGRESO) {
            processInputStock(me, tx, trxdet);
        } else if (tipoOper == Constants.TAB_ALM_SALIDA) {
            processOutputStock(me, tx, trxdet);
        } else if (tipoOper == Constants.TAB_ALM_ACTKRD) {

        }
    }

    public void processKardex(User me, TxTransaccion tx, TxTransdet trxdet) {
        retCreateKdxTrx(me, tx, trxdet);

        TxTransaccion txkdx = returnTrxKardex(trxdet.getIdItemaf());
        createKdxDet(me, txkdx, trxdet);
        TxTransdet newtd = kardexInitial(me, txkdx, trxdet.getIdItemaf(), trxdet.getOpermayor())
                .orElseThrow(() -> new DataException(
                        "Detalle kardex inexistente para " + trxdet.getIdItemaf() + " mayor " + trxdet.getOpermayor()));
    }

    public void processInputStock(User me, TxTransaccion tx, TxTransdet trxdet) {
        Integer tareaoperacion = Constants.TAB_TASK_VER;
        Integer detoperacion = Constants.TAB_DOP_ITEM;
        Integer opermayor = Constants.TAB_MY_INGRESO_NETO;
        Integer tipoCargo = Constants.TAB_MY_SALDO;
        Integer idItemaf = trxdet.getIdItemaf();

        AfItemaf itemaf = itemafService.findById(idItemaf);
        if (itemaf.getIdTrxkdx() == null) {
            TxTransdet trxdetKdxInit = TxTransdet.nuevoReg();
            trxdetKdxInit.setOpermayor(Constants.TAB_MY_SALDOINICIAL);
            trxdetKdxInit.setIdItemaf(idItemaf);
            trxdetKdxInit.setIdTransdetpadre(null); // root
            trxdetKdxInit.setIdTrxorigen(trxdet.getIdTransdet());

            processKardex(me, tx, trxdetKdxInit);
        }

        TxTransaccion txkdx = returnTrxKardex(trxdet.getIdItemaf());

        /*
         * TxTransdet balanceInit = kardexInitial(me, txkdx, trxdet,
         * Constants.TAB_MY_SALDOINICIAL)
         * .orElseThrow(() -> new DataException("Saldo inicial inexistente para item " +
         * trxdet.getIdItemaf()));
         *
         * List<TxTransdet> list =
         * transdetRepository.findByTrxItemKardex(txkdx.getIdTransaccion(),
         * tipoOperacion,
         * detoperacion, opermayor, idItemaf);
         *
         * BigDecimal balanceStock = balanceInit.getCantidad();
         * for (TxTransdet txTransdet : list) {
         * balanceStock = balanceStock.add(txTransdet.getCantidad());
         * }
         */

        TxTransdet newtrxdet = TxTransdet.nuevoReg();

        newtrxdet.setIdTransaccion(txkdx.getIdTransaccion());
        newtrxdet.setTabTipooperacion(tx.getTabTipooperacion());
        newtrxdet.setTipooperacion(tx.getTipooperacion());
        newtrxdet.setTabTareaoperacion(Constants.TAB_TASK);
        newtrxdet.setTareaoperacion(tareaoperacion);
        newtrxdet.setTabDetoperacion(Constants.TAB_DOP);
        newtrxdet.setDetoperacion(detoperacion);
        newtrxdet.setTabOpermayor(Constants.TAB_MY);
        newtrxdet.setOpermayor(opermayor);
        newtrxdet.setTipocargo(tipoCargo);
        newtrxdet.setIdItemaf(idItemaf);

        BigDecimal monto = trxdet.getMonto();
        BigDecimal tipoCambio = trxdet.getTipoCambio();
        BigDecimal montoCont = monto.multiply(tipoCambio).negate();
        // BigDecimal cantidad = balanceStock.add(trxdet.getCantidad());

        newtrxdet.setTipoCambio(tipoCambio);
        newtrxdet.setMonto(monto);
        newtrxdet.setMontoOrig(trxdet.getMontoOrig());
        newtrxdet.setMonedaamtorig(trxdet.getMonedaamtorig());
        newtrxdet.setMontoCont(montoCont);
        newtrxdet.setCantidad(trxdet.getCantidad());
        newtrxdet.setIdTransdetpadre(trxdet.getIdTransdet()); // ????? en veremos
        newtrxdet.setIdTrxorigen(trxdet.getIdTransdet());

        saveDetalle(me, txkdx, newtrxdet);

        BigDecimal stock = BigDecimal.valueOf(itemaf.getStock());
        stock = stock.add(newtrxdet.getCantidad());
        itemaf.setStock(stock.intValue());
        itemafService.update(itemaf);
    }

    public void processOutputStock(User me, TxTransaccion tx, TxTransdet trxdet) {
        BigDecimal montoOrig = BigDecimal.ZERO;
        BigDecimal montoDesc = BigDecimal.ZERO;

        Integer unidadmed = 0;
        Integer metodocalc = 0;
        String glosa = "";

        Integer tipoOperacion = tx.getTipooperacion();
        Integer tareaoperacion = Constants.TAB_TASK_VER;
        Integer detoperacion = Constants.TAB_DOP_ITEM;
        Integer opermayor = Constants.TAB_MY_SALIDA_NETO;
        Integer tipoCargo = Constants.TAB_MY_SALDO;
        Integer idItemaf = trxdet.getIdItemaf();

        AfItemaf itemaf = itemafService.findById(idItemaf);

        TxTransaccion txkdx = returnTrxKardex(idItemaf);

        List<TxTransdet> list = transdetRepository.findByTrxItemKardex(
                txkdx.getIdTransaccion(),
                Constants.TAB_ALM_INGRESO,
                detoperacion,
                Constants.TAB_MY_INGRESO_NETO,
                idItemaf);

        List<TxTransdet> listImplicados = new ArrayList<>();
        BigDecimal cantidad = trxdet.getCantidad();
        BigDecimal precioVenta = trxdet.getMontoOrig();

        for (TxTransdet txTransdet : list) {
            TxTransdet clon = TxTransdet.clone(txTransdet);

            BigDecimal precio = txTransdet.getMontoOrig();

            if (txTransdet.getCantidad().compareTo(cantidad) <= 0) {
                // se registra la cantidad a descontar del item
                clon.setCantidad(txTransdet.getCantidad());
                cantidad = cantidad.subtract(clon.getCantidad());
            } else {
                BigDecimal nvoStock = txTransdet.getCantidad().subtract(cantidad);
                clon.setCantidad(cantidad);
                cantidad = BigDecimal.ZERO;
            }
            // determinando los nuevos valores del precio y reduciendo stockx
            clon.setTipoCambio(trxdet.getTipoCambio());

            BigDecimal amtBuy = TxTransdetQryService.calculateTotalAmount(
                    clon.getCantidad(), txTransdet.getMontoOrig(), BigDecimal.ZERO, BigDecimal.ZERO);

            BigDecimal amtSell = TxTransdetQryService.calculateTotalAmount(
                    clon.getCantidad(), precioVenta, BigDecimal.ZERO, BigDecimal.ZERO);

            BigDecimal montoDiff = amtSell.subtract(amtBuy);

            clon.setMontoOrig(precio);
            clon.setMonto(amtSell);
            clon.setMontoDesc(montoDiff);
            clon.setIdTransdetpadre(txTransdet.getIdTransdet());

            listImplicados.add(clon);
            if (cantidad.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }
        }
        Integer nStock = 0;
        BigDecimal tipoCambio = trxdet.getTipoCambio();
        for (TxTransdet txTransdet : listImplicados) {
            txTransdet.setIdTransaccion(txkdx.getIdTransaccion());
            txTransdet.setTabTipooperacion(tx.getTabTipooperacion());
            txTransdet.setTipooperacion(tx.getTipooperacion());
            txTransdet.setTabTareaoperacion(Constants.TAB_TASK);
            txTransdet.setTareaoperacion(tareaoperacion);
            txTransdet.setTabDetoperacion(Constants.TAB_DOP);
            txTransdet.setDetoperacion(detoperacion);
            txTransdet.setTabOpermayor(Constants.TAB_MY);
            txTransdet.setOpermayor(opermayor);
            txTransdet.setTipocargo(tipoCargo);

            // BigDecimal cantidad = balanceStock.add(trxdet.getCantidad());
            BigDecimal montoCont = txTransdet.getCantidad().multiply(txTransdet.getMontoOrig());
            txTransdet.setMonedaamtorig(trxdet.getMonedaamtorig());
            txTransdet.setMontoCont(montoCont);
            txTransdet.setCantidad(txTransdet.getCantidad().negate());
            txTransdet.setIdTrxorigen(trxdet.getIdTransdet());

            saveDetalle(me, txkdx, txTransdet);
            nStock = nStock + txTransdet.getCantidad().intValue();
        }
        Integer stock = itemaf.getStock();
        stock = stock - nStock;
        itemaf.setStock(stock);
        itemafService.update(itemaf);
    }

    public void createKdxDet(User me, TxTransaccion txkdx, TxTransdet trxdet) {
        Integer tareaoperacion = Constants.TAB_TASK_VER;
        Integer detoperacion = Constants.TAB_DOP_ITEM;
        Integer opermayor = trxdet.getOpermayor();
        Integer tipoCargo = Constants.TAB_MY_SALDO;

        TxTransdet newtd = kardexInitial(me, txkdx, trxdet.getIdItemaf(), opermayor)
                .orElseGet(() -> {
                    TxTransdet newtrxdet = TxTransdet.nuevoReg();

                    newtrxdet.setIdTransaccion(txkdx.getIdTransaccion());
                    newtrxdet.setTabTipooperacion(txkdx.getTabTipooperacion());
                    newtrxdet.setTipooperacion(txkdx.getTipooperacion());
                    newtrxdet.setTareaoperacion(tareaoperacion);
                    newtrxdet.setTabDetoperacion(Constants.TAB_DOP);
                    newtrxdet.setDetoperacion(detoperacion);
                    newtrxdet.setTabOpermayor(Constants.TAB_MY);
                    newtrxdet.setOpermayor(opermayor);
                    newtrxdet.setTipocargo(tipoCargo);

                    newtrxdet.setIdItemaf(trxdet.getIdItemaf());
                    newtrxdet.setTipoCambio(trxdet.getTipoCambio());
                    newtrxdet.setMonto(trxdet.getMonto());
                    newtrxdet.setMontoOrig(trxdet.getMontoOrig());
                    newtrxdet.setMonedaamtorig(trxdet.getMonedaamtorig());
                    newtrxdet.setMontoCont(trxdet.getMonto());
                    newtrxdet.setCantidad(trxdet.getCantidad());
                    newtrxdet.setIdTransdetpadre(trxdet.getIdTransdetpadre());
                    newtrxdet.setIdTrxorigen(trxdet.getIdTrxorigen());

                    return newtrxdet;
                });
        if (newtd.getIdTransdet() == null) {
            TxTransdet newt = saveDetalleKdx(me, txkdx, newtd);
            log.info("Kardex creado/upd Idtrx: {} idxDet: {}", txkdx.getIdTransaccion(), newt.getIdTransdet());
        }
    }

    public void retCreateKdxTrx(User me, TxTransaccion txIn, TxTransdet trxdet) {
        Integer tipoopersub = txIn.getTipoopersub();
        Integer tipoOperacion = Constants.TAB_TOP_KARDEX;
        Integer tareaoperacion = Constants.TAB_TASK_VER;
        Integer idItemaf = trxdet.getIdItemaf();

        AfItemaf af = itemafService.findById(idItemaf);

        TxTransaccion newTrx = returnTrxKardex(me, txIn, idItemaf, tipoOperacion)
                .orElseGet(() -> {
                    TxTransaccion tx = TxTransaccion.nuevoReg();
                    tx.setTabTipoopersub(txIn.getTabTipoopersub());
                    tx.setTipoopersub(tipoopersub);
                    tx.setTabTipooperacion(Constants.TAB_TOP);
                    tx.setTipooperacion(tipoOperacion);
                    tx.setIdTrxorigen(txIn.getIdTransaccion());
                    tx.setIdItemaf(idItemaf);
                    tx.setMonedaamt(trxdet.getMonedaamtorig());
                    return tx;
                });

        if (newTrx.getIdTransaccion() == null) {
            TxTransaccion trxkdx = saveTransacc(me, newTrx);
            af.setIdTrxkdx(trxkdx.getIdTransaccion());
            af.setStock(0);
            itemafService.update(af);
        } else {
            if (af.getIdTrxkdx() == null) {
                af.setIdTrxkdx(newTrx.getIdTransaccion());
                af.setStock(0);
                itemafService.update(af);
            }
        }
    }

    public TxTransaccion returnTrxKardex(Integer idItemaf) {
        return txTransaccionRepository
                .kardexVig(idItemaf)
                .orElseThrow(() -> new DataException("Kardex vigente inexistente para item " + idItemaf));
    }

    public Optional<TxTransaccion> returnTrxKardex(User me, TxTransaccion tx, Integer idItemaf, Integer tipoOperacion) {

        // Integer tipoopersub = tx.getTipoopersub();
        List<TxTransaccion> list = txTransaccionRepository.findByItemaf(tx.getTipoopersub(), tipoOperacion, idItemaf);
        if (list.size() > 0) {
            return Optional.of(list.get(0));
        }
        return Optional.empty();
    }

    public Optional<TxTransdet> kardexInitial(User me, TxTransaccion txkdx, Integer idItemaf, Integer opermayor) {
        // Integer opermayor = Constants.TAB_MY_SALDOINICIAL;
        Integer detoperacion = Constants.TAB_DOP_ITEM;
        List<TxTransdet> list = transdetRepository.findByTrxItemKardex(
                txkdx.getIdTransaccion(), txkdx.getTipooperacion(), detoperacion, opermayor, idItemaf);
        if (list.size() > 0) {
            return Optional.of(list.get(0));
        }
        return Optional.empty();
    }

    private void updItem0(Integer idItemaf) {
        AfItemaf item = itemafService.findById(idItemaf);
        TxTransaccion txkdx = returnTrxKardex(idItemaf);
        BigDecimal stock = retunStock(txkdx, idItemaf);
        item.setStock(stock.intValue());

        itemafService.update(item);
    }

    private BigDecimal retunStock(TxTransaccion trx, Integer idItemaf) {
        List<TxTransdet> list = transdetRepository.findByItemKardex(
                Constants.TAB_ALM,
                Constants.TAB_MY_SALDO,
                Constants.TAB_MY_SALDO,
                Constants.TAB_MY_SALDO,
                Constants.TAB_MY_SALDO,
                idItemaf);
        if (list.size() > 0) {
            return list.get(0).getCantidad();
        }
        return BigDecimal.ZERO;
    }

    public static void completeAudit(TxTransaccion trx) {
        trx.setTxFecha(new Date());
        trx.setTxUsuario(0);
        trx.setTxHost("");
    }

    public static void completeAudit(TxTransdet trx) {
        trx.setTxFecha(new Date());
        trx.setTxUsuario(0);
        trx.setTxHost("");
    }

    @Transactional(readOnly = true)
    public TxTransdet updDataForPrepare(User me, TxTransaccion tx, TxTransdet trxdet) {
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

        TxTransdet.normalizerData(trxdet);

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
                cantidad = trxdet.getCantidad().intValue() >= itemaf.getStock()
                        ? BigDecimal.valueOf(itemaf.getStock())
                        : trxdet.getCantidad();
                monto = TxTransdetQryService.totalMontoXCantidad(tx, trxdet);
            } else if (tipoOper == Constants.TAB_ALM_ACTKRD) {

            }
            // glosa = trxdet.getOpermayordesc().getDesDescrip() + " - " + glosa;
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

    public static void validar(User me, TxTransaccion tx, TxTransdet trxdet) {
        if (trxdet.getIdItemaf() == null) {
            throw new DataException("Item requerido");
        }

        if (trxdet.getCantidad().compareTo(BigDecimal.ZERO) == 0) {
            throw new DataException("Cantidad requerida");
        }
        if (trxdet.getMontoOrig().compareTo(BigDecimal.ZERO) == 0) {
            throw new DataException("Precio unit requerido " + trxdet.getIdItemaf());
        }

        if (trxdet.getMonto().compareTo(BigDecimal.ZERO) == 0) {
            throw new DataException("monto requerido");
        }
    }

    public static void validarKdx(User me, TxTransaccion tx, TxTransdet trxdet) {
        if (trxdet.getIdItemaf() == null) {
            throw new DataException("Item requerido");
        }
    }

    public static void validar(TxTransaccion tx) {
        if (tx.getTipoopersub() == null) {
            throw new DataException("Modulo requerido");
        }
        if (tx.getTipooperacion() == null) {
            throw new DataException("Tipo operacion requerido");
        }
        if (tx.getIdEmpleado() == null) {
            throw new DataException("ID empleado requerido");
        }
        if (tx.getFechaOper() == null) {
            throw new DataException("Fecha operación requerido");
        }
    }
}
