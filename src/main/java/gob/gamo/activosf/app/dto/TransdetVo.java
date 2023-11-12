package gob.gamo.activosf.app.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import gob.gamo.activosf.app.domain.TxTransdet;
import gob.gamo.activosf.app.domain.entities.GenDesctabla;
import gob.gamo.activosf.app.handlers.DateDesserializerJson;
import gob.gamo.activosf.app.handlers.DateSerializerJson;

@JsonRootName(value = "transdet")
public record TransdetVo(
        Integer idTransdet,
        Integer idTransaccion,
        Integer idCorrelativo,
        Integer tabDetoperacion,
        Integer detoperacion,
        Integer tabTareaoperacion,
        Integer tareaoperacion,
        Integer tabOpermayor,
        Integer opermayor,
        Integer idItemaf,
        String glosa,
        BigDecimal monto,
        BigDecimal montoOrig,
        Integer tabMonedaamtorig,
        Integer monedaamtorig,
        BigDecimal tipoCambio,
        BigDecimal montoDesc,
        BigDecimal montoCont,
        Integer tabTipocargo,
        Integer tipocargo,
        BigDecimal cantidad,
        Integer tabUnidadmed,
        Integer unidadmed,
        Integer tabMetodocalc,
        Integer metodocalc,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // , timezone = "America/Los_Angeles"
                @JsonSerialize(using = DateSerializerJson.class)
                @JsonDeserialize(using = DateDesserializerJson.class)
                Date fechaOper,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // , timezone = "America/Los_Angeles"
                @JsonSerialize(using = DateSerializerJson.class)
                @JsonDeserialize(using = DateDesserializerJson.class)
                Date fechaValor,
        Integer tabTipooperacion,
        Integer tipooperacion,
        Integer idEmpleado,
        Integer idEmpleadoaut,
        Integer idUnidad,
        String idUsrreg,
        String idUsraut,
        Integer idTransdetpadre,
        Integer idTrxorigen,
        GenDesctabla detoperaciondesc,
        GenDesctabla tareaoperaciondesc,
        GenDesctabla opermayordesc,
        GenDesctabla tipooperaciondesc,
        ItemafVo itemsaf,
        EmpleadoVo empleado) {
    public TransdetVo(TxTransdet t) {
        this(
                t.getIdTransdet(),
                t.getIdTransaccion(),
                t.getIdCorrelativo(),
                t.getTabDetoperacion(),
                t.getDetoperacion(),
                t.getTabTareaoperacion(),
                t.getTareaoperacion(),
                t.getTabOpermayor(),
                t.getOpermayor(),
                t.getIdItemaf(),
                t.getGlosa(),
                t.getMonto(),
                t.getMontoOrig(),
                t.getTabMonedaamtorig(),
                t.getMonedaamtorig(),
                t.getTipoCambio(),
                t.getMontoDesc(),
                t.getMontoCont(),
                t.getTabTipocargo(),
                t.getTipocargo(),
                t.getCantidad(),
                t.getTabUnidadmed(),
                t.getUnidadmed(),
                t.getTabMetodocalc(),
                t.getMetodocalc(),
                t.getFechaOper(),
                t.getFechaValor(),
                t.getTabTipooperacion(),
                t.getTipooperacion(),
                t.getIdEmpleado(),
                t.getIdEmpleadoaut(),
                t.getIdUnidad(),
                t.getIdUsrreg(),
                t.getIdUsraut(),
                t.getIdTransdetpadre(),
                t.getIdTrxorigen(),
                t.getDetoperaciondesc(),
                t.getTareaoperaciondesc(),
                t.getOpermayordesc(),
                t.getTipooperaciondesc(),
                t.getItemaf() != null ? new ItemafVo(t.getItemaf()) : null,
                t.getEmpleado() != null ? new EmpleadoVo(t.getEmpleado()) : null);
    }
    ;

    public TxTransdet transdet() {
        return TxTransdet.builder()
                .idTransdet(idTransdet)
                .idTransaccion(idTransaccion)
                .idCorrelativo(idCorrelativo)
                .tabDetoperacion(tabDetoperacion)
                .detoperacion(detoperacion)
                .tabTareaoperacion(tabTareaoperacion)
                .tareaoperacion(tareaoperacion)
                .tabOpermayor(tabOpermayor)
                .opermayor(opermayor)
                .idItemaf(idItemaf)
                .glosa(glosa)
                .monto(monto)
                .montoOrig(montoOrig)
                .tabMonedaamtorig(tabMonedaamtorig)
                .monedaamtorig(monedaamtorig)
                .tipoCambio(tipoCambio)
                .montoDesc(montoDesc)
                .montoCont(montoCont)
                .tabTipocargo(tabTipocargo)
                .tipocargo(tipocargo)
                .cantidad(cantidad)
                .tabUnidadmed(tabUnidadmed)
                .unidadmed(unidadmed)
                .tabMetodocalc(tabMetodocalc)
                .metodocalc(metodocalc)
                .fechaOper(fechaOper)
                .fechaValor(fechaValor)
                .tabTipooperacion(tabTipooperacion)
                .tipooperacion(tipooperacion)
                .idEmpleado(idEmpleado)
                .idEmpleadoaut(idEmpleadoaut)
                .idUnidad(idUnidad)
                .idUsrreg(idUsrreg)
                .idUsraut(idUsraut)
                .idTransdetpadre(idTransdetpadre)
                .idTrxorigen(idTrxorigen)
                .build();
    }
}
