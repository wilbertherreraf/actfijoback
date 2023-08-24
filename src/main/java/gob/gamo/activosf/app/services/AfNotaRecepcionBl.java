/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gob.gamo.activosf.app.domain.AfActivoFijo;
import gob.gamo.activosf.app.domain.AfAtributoActivoFijo;
import gob.gamo.activosf.app.domain.AfComisionRecepcion;
import gob.gamo.activosf.app.domain.AfComponenteActivoFijo;
import gob.gamo.activosf.app.domain.AfImagenActivoFijo;
import gob.gamo.activosf.app.domain.AfNotaRecepcion;
import gob.gamo.activosf.app.domain.AfTransferenciaAsignacion;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.dto.StatusEnum;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.AfActivoFijoRepository;
import gob.gamo.activosf.app.repository.AfComisionRecepcionRepository;
import gob.gamo.activosf.app.repository.AfFamiliaActivoRepository;
import gob.gamo.activosf.app.repository.AfNotaRecepcionRepository;
import gob.gamo.activosf.app.repository.AfTipoCambioRepository;
import gob.gamo.activosf.app.repository.AfTransferenciaAsignacionRepository;
import gob.gamo.activosf.app.utils.LazyLoadingUtil;

/**
 *
 * @author wherrera
 */
public class AfNotaRecepcionBl {

    enum CodeType {
        CODE,
        BARCODE_128,
        RFID
    };

    AfNotaRecepcionRepository afNotaRecepcionRepository;

    AfComisionRecepcionRepository afComisionRecepcionRepository;

    AfActivoFijoRepository afActivoFijoRepository;

    AfFamiliaActivoRepository afFamiliaTipoActivoRepository;

    TxTransaccionBl txTransaccionBl;

    AfTipoCambioRepository afTipoCambioRepository;

    AfActivoFijoBl afActivoFijoBl;

    AfTransferenciaAsignacionRepository afTransferenciaAsignacionRepository;

    private String generateCodeForAfActivoFijo(AfActivoFijo afActivoFijo, CodeType codeType) {
        // Luego de coordinar con RFID colocar aqui la codificación adecuada.
        String result = null;
        switch (codeType) {
            case CODE:
                result = "UIF-"
                        + afActivoFijo.getIdSubFamilia().getIdFamiliaActivo().getCodigo() + "-"
                        + afActivoFijo.getIdSubFamilia().getCodigo() + "-"
                        + String.format("%04d", afActivoFijo.getCorrelativo());
                break;
            case BARCODE_128:
                result = afActivoFijo.getIdSubFamilia().getIdFamiliaActivo().getCodigo()
                        + afActivoFijo.getIdSubFamilia().getCodigo()
                        + String.format("%04d", afActivoFijo.getCorrelativo());
                break;
            case RFID:
                result = "A0000000"
                        + afActivoFijo.getIdSubFamilia().getIdFamiliaActivo().getCodigo()
                        + afActivoFijo.getIdSubFamilia().getCodigo()
                        + String.format("%04d", afActivoFijo.getCorrelativo());
                break;
        }
        return result;
    }

    private String generateCodeForAfComponenteActivoFijo(
            AfComponenteActivoFijo afComponenteActivoFijo, CodeType codeType) {
        // Luego de coordinar con RFID colocar aqui la codificación adecuada.
        return "C-"
                + afComponenteActivoFijo
                        .getIdActivoFijo()
                        .getIdSubFamilia()
                        .getIdFamiliaActivo()
                        .getCodigo()
                + "-"
                + afComponenteActivoFijo.getIdActivoFijo().getIdSubFamilia().getCodigo()
                + "-"
                + String.format("%04d", afComponenteActivoFijo.getIdActivoFijo().getCorrelativo());
    }

    public void enterAfNotaRecepcion(AfNotaRecepcion afNotaRecepcion, UserRequestVo userRequestVo) {
        // TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afNotaRecepcion = findByPkAfNotaRecepcion(afNotaRecepcion.getIdNotaRecepcion(), false);
        afNotaRecepcion.setCatEstadoNotaRecepcion("INGRES");
        if (afNotaRecepcion.getAfActivoFijoList() != null
                && afNotaRecepcion.getAfActivoFijoList().size() != 0) {
            for (AfActivoFijo activoFijo : afNotaRecepcion.getAfActivoFijoList()) {
                activoFijo.setGestion(afNotaRecepcion.getGestion());
                if (activoFijo.getIdUsuarioAsignado() != null) {
                    AfTransferenciaAsignacion afTransferenciaAsignacion = new AfTransferenciaAsignacion();
                    afTransferenciaAsignacion.setGestion(afNotaRecepcion.getGestion());
                    afTransferenciaAsignacion.setCatCentroCostoDestino(activoFijo.getCatCentroCosto());
                    afTransferenciaAsignacion.setCatEstadoUso(activoFijo.getCatEstadoUso());
                    afTransferenciaAsignacion.setFechaTransferencia(afNotaRecepcion.getFechaRecepcion());
                    afTransferenciaAsignacion.setIdActivoFijo(activoFijo);
                    afTransferenciaAsignacion.setIdUsuarioDestino(activoFijo.getIdUsuarioAsignado());
                    afTransferenciaAsignacion.setIdAmbienteDestino(activoFijo.getIdAmbiente());
                    afTransferenciaAsignacion.setIdNotaRecepcion(afNotaRecepcion);
                    afTransferenciaAsignacion.setCatTransferenciaAsignacion(
                            AfTransferenciaAsignacion.TipoTransferencia.ASIGNACION.getValor());
                    afTransferenciaAsignacion.setCatTipoAsignacion(activoFijo.getCatTipoAsignacion());
                    if ("ASGNDO".equals(activoFijo.getCatTipoAsignacion())) {
                        afTransferenciaAsignacion.setCatMotivoTransferencia("CUMFUN"); // CUMFUN: CUMPLIMIENTO DE SUS
                        // FUNCIONES
                    } else if ("CUSTOD".equals(activoFijo.getCatTipoAsignacion())) {
                        afTransferenciaAsignacion.setCatMotivoTransferencia("CUSTOD"); // CUSTOD: PARA SU CUSTODIA
                    }
                    afTransferenciaAsignacion.setEstado(StatusEnum.ACTIVE.getStatus());
                    // TransactionUtil.setInitTransactionData(afTransferenciaAsignacion);
                    if (activoFijo.getAfTransferenciaAsignacionList() == null) {
                        activoFijo.setAfTransferenciaAsignacionList(new ArrayList<AfTransferenciaAsignacion>());
                    }
                    afTransferenciaAsignacionRepository.save(afTransferenciaAsignacion);
                    // activoFijo.getAfTransferenciaAsignacionList().add(afTransferenciaAsignacion);

                    if ("ASGNDO".equals(activoFijo.getCatTipoAsignacion())) {
                        activoFijo.setCatEstadoActivoFijo("ASGNDO");
                    } else {
                        activoFijo.setCatEstadoActivoFijo("CUSTOD");
                    }
                } else {
                    activoFijo.setCatEstadoActivoFijo("RECEPC");
                }
                activoFijo.setEstado(StatusEnum.ACTIVE.getStatus());
                activoFijo.setIdNotaRecepcion(afNotaRecepcion);
                // TransactionUtil.setUpdateTransactionData(activoFijo);
            }
        }
        // Hacemos el update y hacesmos que se haga flush y merge para que se asigne el
        // correlativo.
        // Luego de asignado el correlativo podemos generar las etiquetas
        afNotaRecepcionRepository.save(afNotaRecepcion);
        if (afNotaRecepcion.getAfActivoFijoList() != null
                && !afNotaRecepcion.getAfActivoFijoList().isEmpty()) {
            for (AfActivoFijo activoFijo : afNotaRecepcion.getAfActivoFijoList()) {
                activoFijo.setCodigoEan(generateCodeForAfActivoFijo(activoFijo, CodeType.BARCODE_128));
                activoFijo.setCodigoRfid(generateCodeForAfActivoFijo(activoFijo, CodeType.RFID));
                activoFijo.setCodigoExtendido(generateCodeForAfActivoFijo(activoFijo, CodeType.CODE));
                int i = 1;
                for (AfComponenteActivoFijo afComponenteActivoFijo : activoFijo.getAfComponenteActivoFijoList()) {
                    afComponenteActivoFijo.setCorrelativo(i++);
                    afComponenteActivoFijo.setCodigoRfid(
                            generateCodeForAfComponenteActivoFijo(afComponenteActivoFijo, CodeType.RFID));
                    afComponenteActivoFijo.setCodigoEan(
                            generateCodeForAfComponenteActivoFijo(afComponenteActivoFijo, CodeType.BARCODE_128));
                    // TransactionUtil.setUpdateTransactionData(afComponenteActivoFijo);
                }

                // Si es una donación generamos los datos contables solo para adquicisiones
                // superiores al 2008
                if ("02".equals(afNotaRecepcion.getCatMotivoTipoMovimiento())) {
                    Date fechaActual = activoFijo.getFechaActual();
                    Date fechaHistorica = activoFijo.getFechaHistorico();

                    // Se cambia momentaneamente para hacer los calculos
                    activoFijo.setFechaActual(fechaHistorica);
                    afActivoFijoBl.realizarCalculosContablesIndividual(activoFijo, fechaActual);
                    // Se reestablece
                    activoFijo.setFechaActual(fechaActual);
                    activoFijo.setCostoActual(activoFijo.getCalculoContableVo().getValorActual());
                    activoFijo.setDepAcumuladaActual(
                            activoFijo.getCalculoContableVo().getDepreciacionAcumulada());
                }
            }
        }
        afNotaRecepcionRepository.save(afNotaRecepcion);
    }

    public void enterAfActivoFijoMigracion(
            AfActivoFijo activoFijo, UserRequestVo userRequestVo, String catMotivoTipoMovimiento) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);

        activoFijo.setCatFuenteFinanciamiento("00");
        setTransactionToAfAtributoActivoFijo(txTransaccion, activoFijo);
        activoFijo.setFactorDepreciacionHistorico(activoFijo.getIdSubFamilia().getFactorDepreciacion());
        setAtributosContables(activoFijo, "MIGRAC");

        AfNotaRecepcion afNotaRecepcion = null;
        afNotaRecepcion = findByPkAfNotaRecepcion(2015 - activoFijo.getGestion(), false); // 0: 2015 y -1:2016

        AfTransferenciaAsignacion afTransferenciaAsignacion = new AfTransferenciaAsignacion();
        afTransferenciaAsignacion.setGestion(activoFijo.getGestion());
        afTransferenciaAsignacion.setCatCentroCostoDestino(activoFijo.getCatCentroCosto());
        afTransferenciaAsignacion.setCatEstadoUso(activoFijo.getCatEstadoUso());
        afTransferenciaAsignacion.setFechaTransferencia(userRequestVo.getDate());
        afTransferenciaAsignacion.setIdActivoFijo(activoFijo);
        afTransferenciaAsignacion.setIdUsuarioDestino(activoFijo.getIdUsuarioAsignado());
        afTransferenciaAsignacion.setIdAmbienteDestino(activoFijo.getIdAmbiente());
        afTransferenciaAsignacion.setIdNotaRecepcion(afNotaRecepcion);
        afTransferenciaAsignacion.setCatTransferenciaAsignacion(
                AfTransferenciaAsignacion.TipoTransferencia.ASIGNACION.getValor());
        afTransferenciaAsignacion.setCatTipoAsignacion(activoFijo.getCatTipoAsignacion());

        if ("ASGNDO".equals(activoFijo.getCatTipoAsignacion())) {
            afTransferenciaAsignacion.setCatMotivoTransferencia("CUMFUN"); // CUMFUN: CUMPLIMIENTO DE SUS FUNCIONES
        } else if ("CUSTOD".equals(activoFijo.getCatTipoAsignacion())) {
            afTransferenciaAsignacion.setCatMotivoTransferencia("CUSTOD"); // CUSTOD: PARA SU CUSTODIA
        }

        afTransferenciaAsignacion.setEstado(StatusEnum.ACTIVE.getStatus());
        // TransactionUtil.setInitTransactionData(afTransferenciaAsignacion);
        if (activoFijo.getAfTransferenciaAsignacionList() == null) {
            activoFijo.setAfTransferenciaAsignacionList(new ArrayList<AfTransferenciaAsignacion>());
        }
        activoFijo.getAfTransferenciaAsignacionList().add(afTransferenciaAsignacion);

        if ("ASGNDO".equals(activoFijo.getCatTipoAsignacion())) {
            activoFijo.setCatEstadoActivoFijo("ASGNDO");
        } else {
            activoFijo.setCatEstadoActivoFijo("CUSTOD");
        }

        activoFijo.setEstado(StatusEnum.ACTIVE.getStatus());
        activoFijo.setIdNotaRecepcion(afNotaRecepcion);
        // TransactionUtil.setUpdateTransactionData(activoFijo);

        int i = 1;
        if (activoFijo.getAfComponenteActivoFijoList() != null
                && !activoFijo.getAfComponenteActivoFijoList().isEmpty()) {
            for (AfComponenteActivoFijo afComponenteActivoFijo : activoFijo.getAfComponenteActivoFijoList()) {
                afComponenteActivoFijo.setCorrelativo(i++);
                afComponenteActivoFijo.setCodigoRfid(
                        generateCodeForAfComponenteActivoFijo(afComponenteActivoFijo, CodeType.RFID));
                afComponenteActivoFijo.setCodigoEan(
                        generateCodeForAfComponenteActivoFijo(afComponenteActivoFijo, CodeType.BARCODE_128));
                // TransactionUtil.setUpdateTransactionData(afComponenteActivoFijo);
            }
        }

        // Si es una donación generamos los datos contables solo para adquicisiones
        // superiores al 2008
        if ("02".equals(catMotivoTipoMovimiento)) {
            Date fechaActual = activoFijo.getFechaActual();
            Date fechaHistorica = activoFijo.getFechaHistorico();

            // Se cambia momentaneamente para hacer los calculos
            activoFijo.setFechaActual(fechaHistorica);
            afActivoFijoBl.realizarCalculosContablesIndividual(activoFijo, fechaActual);
            // Se reestablece
            activoFijo.setFechaActual(fechaActual);
            activoFijo.setCostoActual(activoFijo.getCalculoContableVo().getValorActual());
            activoFijo.setDepAcumuladaActual(activoFijo.getCalculoContableVo().getDepreciacionAcumulada());
        }

        afActivoFijoRepository.save(activoFijo);

        activoFijo.setCodigoEan(generateCodeForAfActivoFijo(activoFijo, CodeType.BARCODE_128));
        activoFijo.setCodigoRfid(generateCodeForAfActivoFijo(activoFijo, CodeType.RFID));
        activoFijo.setCodigoExtendido(generateCodeForAfActivoFijo(activoFijo, CodeType.CODE));

        afActivoFijoRepository.save(activoFijo);
    }

    public void persistAfNotaRecepcion(AfNotaRecepcion afNotaRecepcion, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afNotaRecepcion.setCatEstadoNotaRecepcion("PROREC");
        if (afNotaRecepcion.getAfActivoFijoList() != null
                && afNotaRecepcion.getAfActivoFijoList().size() != 0) {
            // Solicitado así por la Lic. Susana. Si se cambia tambien cambiar
            // NotaRecepcion.addActivoFijoToList()
            String catTipoActializacion = "02".equals(afNotaRecepcion.getCatMotivoTipoMovimiento())
                    ? "DONACN"
                    : "REGINI"; // 02 POR DONACION -> DONACN
            for (AfActivoFijo activoFijo : afNotaRecepcion.getAfActivoFijoList()) {
                activoFijo.setGestion(afNotaRecepcion.getGestion());
                activoFijo.setCatEstadoActivoFijo("PROREC");
                activoFijo.setEstado(StatusEnum.ACTIVE.getStatus());
                activoFijo.setCodigoEan("NO-ASIGNADO"); // 128 Texto
                activoFijo.setCodigoRfid("NO-ASIGNADO");
                activoFijo.setIdNotaRecepcion(afNotaRecepcion);

                /**
                 * Si es revaluo deberia llegar el factor depreciación,en caso cotrario se
                 * coloca el de la familia.
                 * Si se cambia el cat_motivo_tipo_movimiento a algo diferente a revaluo
                 * entonces existe la posibilidad
                 * de que no llegue el valor entonces igual se copia el de la familia
                 */
                // Si es nulo o no es REVALUO
                if (activoFijo.getFactorDepreciacionHistorico() == null) {
                    activoFijo.setFactorDepreciacionHistorico(
                            activoFijo.getIdSubFamilia().getFactorDepreciacion());
                }

                // GESTIONAMOS LA GARANTIA
                setTransactionToAfGarantiaActivoFijo(txTransaccion, activoFijo);

                // GESTIONAMOS LOS ATRIBUTOS
                setTransactionToAfAtributoActivoFijo(txTransaccion, activoFijo);

                // GESTIONAMOS LOS COMPONENTES
                setTransactionAndCodeToAfComponenteActivoFijo(txTransaccion, activoFijo);

                // GESTIONAMOS LA FACTURA
                setTransactionToAfFactura(txTransaccion, activoFijo);

                // GESTIONAMOS LAS IMAGENES
                setTransactionToAfImagenActivoFijo(txTransaccion, activoFijo);

                // GESITONAMOS LA INFORMACION CONTABLE
                setAtributosContables(activoFijo, catTipoActializacion);

                // TransactionUtil.setInitTransactionData(activoFijo);
            }
        }
        if (afNotaRecepcion.getAfComisionRecepcionList() != null
                && afNotaRecepcion.getAfComisionRecepcionList().size() != 0) {
            for (AfComisionRecepcion afComisionRecepcion : afNotaRecepcion.getAfComisionRecepcionList()) {
                afComisionRecepcion.setEstado(StatusEnum.ACTIVE.getStatus());
                afComisionRecepcion.setIdNotaRecepcion(afNotaRecepcion);
                // TransactionUtil.setInitTransactionData(afComisionRecepcion);
            }
        }

        afNotaRecepcionRepository.save(afNotaRecepcion);
    }

    public void mergeAfNotaRecepcion(
            AfNotaRecepcion afNotaRecepcion,
            List<AfComisionRecepcion> comisionRecepcion,
            List<AfActivoFijo> activos,
            UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        if (activos != null) {
            removeAllAfActivoFijo(afNotaRecepcion.getIdNotaRecepcion(), txTransaccion);
            String catTipoActializacion = "02".equals(afNotaRecepcion.getCatMotivoTipoMovimiento())
                    ? "DONACN"
                    : "REGINI"; // 02 POR DONACION -> DONACN
            for (AfActivoFijo activoFijo : activos) {
                activoFijo.setIdNotaRecepcion(afNotaRecepcion);
                if (activoFijo.getIdActivoFijo() != null) {
                    // TransactionUtil.setUpdateTransactionData(activoFijo);
                } else {
                    activoFijo.setGestion(afNotaRecepcion.getGestion());
                    activoFijo.setCatEstadoActivoFijo("PROREC");
                    activoFijo.setEstado(StatusEnum.ACTIVE.getStatus());
                    activoFijo.setCodigoEan("NO-ASIGNADO"); // 128 Texto
                    activoFijo.setCodigoRfid("NO-ASIGNADO");
                    activoFijo.setIdNotaRecepcion(afNotaRecepcion);
                    /**
                     * Si es revaluo deberia llegar el factor depreciación,en caso cotrario se
                     * coloca el de la familia.
                     * Si se cambia el cat_motivo_tipo_movimiento a algo diferente a revaluo
                     * entonces existe la posibilidad
                     * de que no llegue el valor entonces igual se copia el de la familia
                     */
                    // Si es nulo o no es REVALUO
                    if (activoFijo.getFactorDepreciacionHistorico() == null) {
                        activoFijo.setFactorDepreciacionHistorico(
                                activoFijo.getIdSubFamilia().getFactorDepreciacion());
                    }
                    // TransactionUtil.setInitTransactionData(activoFijo);
                }

                // GESTIONAMOS LA GARANTIA
                setTransactionToAfGarantiaActivoFijo(txTransaccion, activoFijo);

                // GESTIONAMOS LOS ATRIBUTOS
                setTransactionToAfAtributoActivoFijo(txTransaccion, activoFijo);

                // GESTIONAMOS LOS COMPONENTES
                setTransactionAndCodeToAfComponenteActivoFijo(txTransaccion, activoFijo);

                // GESTIONAMOS LA FACTURA
                setTransactionToAfFactura(txTransaccion, activoFijo);

                // GESTIONAMOS LAS IMAGENES
                setTransactionToAfImagenActivoFijo(txTransaccion, activoFijo);

                // GESITONAMOS LA INFORMACION CONTABLE
                setAtributosContables(activoFijo, catTipoActializacion);
            }
            afNotaRecepcion.setAfActivoFijoList(activos);
        }
        if (comisionRecepcion != null) {
            removeAllAfComisionRecepcion(afNotaRecepcion.getIdNotaRecepcion(), txTransaccion);
            for (AfComisionRecepcion afComisionRecepcion : comisionRecepcion) {
                afComisionRecepcion.setIdNotaRecepcion(afNotaRecepcion);
                if (afComisionRecepcion.getTxFchIni() != null) {
                    // TransactionUtil.setUpdateTransactionData(afComisionRecepcion);
                } else {
                    afComisionRecepcion.setEstado(StatusEnum.ACTIVE.getStatus());
                    afComisionRecepcion.setIdNotaRecepcion(afNotaRecepcion);
                    // TransactionUtil.setInitTransactionData(afComisionRecepcion);
                }
            }
            afNotaRecepcion.setAfComisionRecepcionList(comisionRecepcion);
        }

        afNotaRecepcionRepository.save(afNotaRecepcion);
    }

    /**
     * GESTIONAMOS LA INFORMACION CONTABLE
     */
    private void setAtributosContables(AfActivoFijo afActivoFijo, String catTipoActualizacion) {
        if (afActivoFijo.getCostoActual() == null) {
            afActivoFijo.setCostoActual(afActivoFijo.getCostoHistorico());
        }
        if (afActivoFijo.getDepAcumuladaHistorico() == null) {
            afActivoFijo.setDepAcumuladaHistorico(BigDecimal.ZERO);
        }
        afActivoFijo.setCatTipoActualizacion(catTipoActualizacion);
        afActivoFijo.setFactorDepreciacionActual(afActivoFijo.getIdSubFamilia().getFactorDepreciacion());
        if (afActivoFijo.getFechaHistorico() == null) {
            afActivoFijo.setFechaHistorico(afActivoFijo.getFechaActual());
        }
        if (afActivoFijo.getDepAcumuladaActual() == null) {
            afActivoFijo.setDepAcumuladaActual(BigDecimal.ZERO);
        }

        // Calculo del factor de depreciacion del Activo Fijo con amortización variable
        if (afActivoFijo.getIdSubFamilia().isDepreciar()
                && afActivoFijo.getIdSubFamilia().isAmortizacionVariable()) {
            boolean vigenciaRegistrada = false;
            for (AfAtributoActivoFijo afAtributoActivoFijo : afActivoFijo.getAfAtributoActivoFijoList()) {
                if ("VIGENC".equals(afAtributoActivoFijo.getCatTipoAtributo())) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        LocalDate vencimiento = LocalDate.ofInstant(
                                formatter
                                        .parse(afAtributoActivoFijo.getDetalle())
                                        .toInstant(),
                                ZoneId.systemDefault());
                        // Days dias = Days.daysBetween(new LocalDate(afActivoFijo.getFechaActual()),
                        // vencimiento);
                        int days = (int) ChronoUnit.DAYS.between(
                                LocalDate.ofInstant(
                                        afActivoFijo.getFechaActual().toInstant(), ZoneId.systemDefault()),
                                vencimiento);
                        if (days > 1) {
                            afActivoFijo.setFactorDepreciacionActual(BigDecimal.ONE.divide(
                                    (new BigDecimal(days).divide(new BigDecimal("365"), 10, RoundingMode.HALF_UP)),
                                    10,
                                    RoundingMode.HALF_UP));
                        } else {
                            afActivoFijo.setFactorDepreciacionActual(BigDecimal.ONE);
                        }
                        vigenciaRegistrada = true;
                        break;
                    } catch (ParseException e) {
                        throw new DataException("La fecha de vigencia tiene un formato diferente a dd/mm/aaaa: "
                                + afAtributoActivoFijo.getDetalle());
                    }
                }
            }
            if (!vigenciaRegistrada) {
                throw new DataException("El activo fijo que desea registrar debe para la subfamilia:"
                        + afActivoFijo.getIdSubFamilia().getDescripcion()
                        + ". Debe tener el atributo VIGENCIA para determinar su amortización.");
            }
        }
    }

    /**
     * GESTIONAMOS LOS COMPONENTES
     */
    private void setTransactionAndCodeToAfComponenteActivoFijo(TxTransaccion txTransaccion, AfActivoFijo activoFijo) {

        if (activoFijo.getAfComponenteActivoFijoList() != null) {
            int i = 0;
            for (AfComponenteActivoFijo afComponenteActivoFijo : activoFijo.getAfComponenteActivoFijoList()) {
                afComponenteActivoFijo.setCorrelativo(i++);
                afComponenteActivoFijo.setCodigoRfid("SIN-ASIGNACION");
                afComponenteActivoFijo.setCodigoEan("SIN-ASIGNACION");
                if (afComponenteActivoFijo.getIdComponenteActivoFijo() == null) {
                    afComponenteActivoFijo.setIdActivoFijo(activoFijo);
                    afComponenteActivoFijo.setEstado(StatusEnum.ACTIVE.getStatus());
                    // TransactionUtil.setInitTransactionData(afComponenteActivoFijo);
                } else {
                    // TransactionUtil.setUpdateTransactionData(afComponenteActivoFijo);
                }
            }
        }
    }

    private void setTransactionToAfImagenActivoFijo(TxTransaccion txTransaccion, AfActivoFijo activoFijo) {
        Date now = new Date();
        if (activoFijo.getAfImagenActivoFijoList() != null) {
            for (AfImagenActivoFijo afImagenActivoFijo : activoFijo.getAfImagenActivoFijoList()) {
                if (afImagenActivoFijo.getIdImagenActivoFijo() == null) {
                    afImagenActivoFijo.setIdActivoFijo(activoFijo);
                    afImagenActivoFijo.setEstado(StatusEnum.ACTIVE.getStatus());
                    afImagenActivoFijo.setFechaCaptura(now);
                    // TransactionUtil.setInitTransactionData(afImagenActivoFijo);
                } else {
                    // TransactionUtil.setUpdateTransactionData(afImagenActivoFijo);
                }
            }
        }
    }

    /**
     * GESTIONAMOS LOS ATRIBUTOS
     */
    private void setTransactionToAfAtributoActivoFijo(TxTransaccion txTransaccion, AfActivoFijo activoFijo) {
        if (activoFijo.getAfAtributoActivoFijoList() != null) {
            for (AfAtributoActivoFijo afAtributoActivoFijo : activoFijo.getAfAtributoActivoFijoList()) {
                if (afAtributoActivoFijo.getIdAtributoActivoFijo() == null) {
                    afAtributoActivoFijo.setIdActivoFijo(activoFijo);
                    afAtributoActivoFijo.setEstado(StatusEnum.ACTIVE.getStatus());
                    // TransactionUtil.setInitTransactionData(afAtributoActivoFijo);
                } else {
                    // TransactionUtil.setUpdateTransactionData(afAtributoActivoFijo);
                }
            }
        }
    }

    /**
     * GESTIONAMOS LA GARANTIA
     */
    private void setTransactionToAfGarantiaActivoFijo(TxTransaccion txTransaccion, AfActivoFijo activoFijo) {
        if (activoFijo.getIdGarantiaActivoFijo() != null) {
            if (activoFijo.getIdGarantiaActivoFijo().getIdGarantiaActivoFijo() == null) {
                activoFijo.getIdGarantiaActivoFijo().setEstado(StatusEnum.ACTIVE.getStatus());
                // TransactionUtil.setInitTransactionData(activoFijo.getIdGarantiaActivoFijo());
            } else {
                // TransactionUtil.setUpdateTransactionData(activoFijo.getIdGarantiaActivoFijo());
            }
        }
    }

    /**
     * GESTIONAMOS LA FACTURA
     */
    private void setTransactionToAfFactura(TxTransaccion txTransaccion, AfActivoFijo activoFijo) {
        if (activoFijo.getIdFactura() != null) {
            if (activoFijo.getIdFactura().getIdFactura() == null) {
                activoFijo.getIdFactura().setEstado(StatusEnum.ACTIVE.getStatus());
                // TransactionUtil.setInitTransactionData(activoFijo.getIdFactura());
            } else {
                // TransactionUtil.setUpdateTransactionData(activoFijo.getIdFactura());
            }
        }
    }

    public void deleteAfNotaRecepcion(AfNotaRecepcion afNotaRecepcion, UserRequestVo userRequestVo) {
        TxTransaccion txTransaccion = txTransaccionBl.generateTxTransaccion(userRequestVo);
        afNotaRecepcion = afNotaRecepcionRepository
                .findById(afNotaRecepcion.getIdNotaRecepcion())
                .orElseThrow(() -> new DataException("id inexistente"));
        afNotaRecepcion.setCatEstadoNotaRecepcion("CANCEL");
        for (AfComisionRecepcion afComisionRecepcion : afNotaRecepcion.getAfComisionRecepcionList()) {
            afComisionRecepcion.setEstado(StatusEnum.INACTIVE.getStatus());
            // TransactionUtil.setUpdateTransactionData(afComisionRecepcion);
        }
        for (AfActivoFijo activoFijo : afNotaRecepcion.getAfActivoFijoList()) {
            activoFijo.setEstado(StatusEnum.INACTIVE.getStatus());
            // TransactionUtil.setUpdateTransactionData(activoFijo);
            if (activoFijo.getIdGarantiaActivoFijo() != null) {
                activoFijo.getIdGarantiaActivoFijo().setEstado(StatusEnum.INACTIVE.getStatus());
                // TransactionUtil.setUpdateTransactionData(activoFijo.getIdGarantiaActivoFijo());
            }
        }
        afNotaRecepcionRepository.delete(afNotaRecepcion);
    }

    public List<AfNotaRecepcion> findAllActivesAfNotaRecepcionSinMigracion(Integer gestion) {
        List<AfNotaRecepcion> notas = afNotaRecepcionRepository.findAllActivesByGestionSinMigracion(gestion);
        for (AfNotaRecepcion afNotaRecepcion : notas) {
            String[] lazyActivoFijo = {"afTransferenciaAsignacionList"}; /*
                                                                            * {"afBajaActivoFijoList","idSubFamilia",
                                                                            * "idUsuarioAsignado","idAmbiente",
                                                                            * "idGarantiaActivoFijo",
                                                                            * "afAtributoActivoFijoList",
                                                                            * "afComponenteActivoFijoList",
                                                                            * "afAccesorioActivoFijoList",
                                                                            * "afImagenActivoFijoList",
                                                                            * "afTransferenciaAsignacionList",
                                                                            * "idProveedor"};
                                                                            */
            initializeLazyAfNotaRecepcion(afNotaRecepcion, lazyActivoFijo);
        }
        return notas;
    }

    public AfNotaRecepcion findByPkAfNotaRecepcion(Integer pk, boolean lazy) {
        AfNotaRecepcion afNotaRecepcion =
                afNotaRecepcionRepository.findById(pk).orElseThrow(() -> new DataException("id inexistente"));
        if (afNotaRecepcion != null && lazy) {
            String[] lazyActivoFijo = {
                "afBajaActivoFijoList",
                "idSubFamilia",
                "idUsuarioAsignado",
                "idFactura",
                "idAmbiente",
                "idGarantiaActivoFijo",
                "afAtributoActivoFijoList",
                "afComponenteActivoFijoList",
                "afAccesorioActivoFijoList",
                "afImagenActivoFijoList",
                "afTransferenciaAsignacionList",
                "idProveedor"
            };
            initializeLazyAfNotaRecepcion(afNotaRecepcion, lazyActivoFijo);
        }
        return afNotaRecepcion;
    }

    private void initializeLazyAfNotaRecepcion(AfNotaRecepcion afNotaRecepcion, String[] lazyActivoFijo) {
        if (afNotaRecepcion.getAfActivoFijoList() != null) {
            LazyLoadingUtil.loadCollection(afNotaRecepcion.getAfActivoFijoList(), lazyActivoFijo);
        }
        if (afNotaRecepcion.getAfComisionRecepcionList() != null) {
            afNotaRecepcion.getAfComisionRecepcionList().size();
        }
    }

    public void removeAllAfComisionRecepcion(Integer pk, TxTransaccion tx) {
        AfNotaRecepcion afNotaRecepcion = findByPkAfNotaRecepcion(pk, false);
        afNotaRecepcion.getAfComisionRecepcionList().clear();
        afNotaRecepcionRepository.save(afNotaRecepcion);
    }

    public void removeAllAfActivoFijo(Integer pk, TxTransaccion tx) {
        AfNotaRecepcion afNotaRecepcion = findByPkAfNotaRecepcion(pk, false);
        afNotaRecepcion.getAfActivoFijoList().clear();
        afNotaRecepcionRepository.save(afNotaRecepcion);
    }

    public List<AfNotaRecepcion> findAllActivesAfNotaRecepcionByGestion(Integer gestion) {
        return afNotaRecepcionRepository.findAllActivesByGestion(gestion);
    }
}
