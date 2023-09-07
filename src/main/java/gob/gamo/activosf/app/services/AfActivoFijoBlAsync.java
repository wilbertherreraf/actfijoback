package gob.gamo.activosf.app.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import gob.gamo.activosf.app.domain.AfActivoFijo;
import gob.gamo.activosf.app.domain.AfTipoCambio;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.AfActivoFijoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class AfActivoFijoBlAsync {

    AfActivoFijoRepository afActivoFijoRepository;

    AfTipoCambioBl afTipoCambioBl;

    // private SessionContext sctx;
    public Future<CalcContabService> realizarCalculosContablesIndividualAsync(
            int idActivoFijo, Integer gestion, Date fechaCalculo, BigDecimal tipoCambio) {
        AfActivoFijo afActivoFijo = afActivoFijoRepository.findByIdActivoFijoYGestion(idActivoFijo, gestion);

        CalcContabService calculoContableVo = null;
        if (tipoCambio != null && fechaCalculo != null) {
            calculoContableVo = new CalcContabService(afActivoFijo, fechaCalculo);
            // if (!sctx.wasCancelCalled()) {
            AfTipoCambio tipoCambioVidaUtil =
                    afTipoCambioBl.getAfTipoCambioByCatMonedaAndFecha("UFV", calculoContableVo.getFechaVidaUtil());
            calculoContableVo.calcular(
                    afTipoCambioBl
                            .getAfTipoCambioByCatMonedaAndFecha("UFV", afActivoFijo.getFechaActual())
                            .getCambio(),
                    tipoCambio,
                    tipoCambioVidaUtil != null ? tipoCambioVidaUtil.getCambio() : BigDecimal.ZERO);
            // }
        } else {
            throw new DataException("EL tipo de cambio y la fecha de calculo deben ser diferentes de null: "
                    + tipoCambio + " - " + fechaCalculo);
        }
        // ojoooooooooo cambiar proceso de contable
        return CompletableFuture.supplyAsync((Supplier<CalcContabService>) calculoContableVo);
    }
}
