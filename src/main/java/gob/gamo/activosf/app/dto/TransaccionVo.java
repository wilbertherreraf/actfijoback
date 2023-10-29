package gob.gamo.activosf.app.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.domain.TxTransdet;
import gob.gamo.activosf.app.handlers.DateDesserializerJson;
import gob.gamo.activosf.app.handlers.DateSerializerJson;

@JsonRootName("transaccion")
public record TransaccionVo(
        Integer idTransaccion,
        Integer tabTipooperacion,
        Integer tipooperacion,
        Integer tabTipoopersub,
        Integer tipoopersub,
        String glosa,
        BigDecimal monto,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // , timezone = "America/Los_Angeles"
                @JsonSerialize(using = DateSerializerJson.class)
                @JsonDeserialize(using = DateDesserializerJson.class)        
        Date fechaOper,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // , timezone = "America/Los_Angeles"
                @JsonSerialize(using = DateSerializerJson.class)
                @JsonDeserialize(using = DateDesserializerJson.class)        
        Date fechaValor,
        Integer idEmpleado,
        Integer idEmpleadoaut,
        Integer idUnidad,
        Integer idUnidaddest,
        String idUsrreg,
        String idUsraut,
        Integer idTrxorigen,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // , timezone = "America/Los_Angeles"
                @JsonSerialize(using = DateSerializerJson.class)
                @JsonDeserialize(using = DateDesserializerJson.class)        
        Date txFecha,
        Integer txUsuario,
        String txHost,
        List<TransdetVo> transdet) {

    public TransaccionVo(TxTransaccion tx) {
        this(
                tx.getIdTransaccion(),
                tx.getTabTipooperacion(),
                tx.getTipooperacion(),
                tx.getTabTipoopersub(),
                tx.getTipoopersub(),
                tx.getGlosa(),
                tx.getMonto(),
                tx.getFechaOper(),
                tx.getFechaValor(),
                tx.getIdEmpleado(),
                tx.getIdEmpleadoaut(),
                tx.getIdUnidad(),
                tx.getIdUnidaddest(),
                tx.getIdUsrreg(),
                tx.getIdUsraut(),
                tx.getIdTrxorigen(),
                tx.getTxFecha(),
                tx.getTxUsuario(),
                tx.getTxHost(),
                tx.getTransdet() != null ? tx.getTransdet().stream().map(x -> new TransdetVo(x)).toList()
                        : new ArrayList<>());
    }

    public TxTransaccion getTransaccion() {
        return TxTransaccion.builder()
                .idTransaccion(idTransaccion)
                .tabTipooperacion(tabTipooperacion)
                .tipooperacion(tipooperacion)
                .tabTipoopersub(tabTipoopersub)
                .tipoopersub(tipoopersub)
                .glosa(glosa)
                .monto(monto)
                .fechaOper(fechaOper)
                .fechaValor(fechaValor)
                .idEmpleado(idEmpleado)
                .idEmpleadoaut(idEmpleadoaut)
                .idUnidad(idUnidad)
                .idUnidaddest(idUnidaddest)
                .idUsrreg(idUsrreg)
                .idUsraut(idUsraut)
                .idTrxorigen(idTrxorigen)
                .txFecha(txFecha)
                .txUsuario(txUsuario)
                .txHost(txHost)
                .transdet(
                        transdet != null ? transdet.stream().map(x -> x.transdet()).toList() : new ArrayList<>())
                .build();
    }
}
