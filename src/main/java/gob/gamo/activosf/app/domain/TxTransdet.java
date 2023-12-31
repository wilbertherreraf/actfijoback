package gob.gamo.activosf.app.domain;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import gob.gamo.activosf.app.domain.entities.GenDesctabla;

@Entity
@Table(name = "tx_transdet")
@Setter
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class TxTransdet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transdet")
    private Integer idTransdet;

    @Column(name = "id_transaccion")
    private Integer idTransaccion;

    @Column(name = "id_correlativo")
    private Integer idCorrelativo;

    @Column(name = "tab_detoperacion")
    private Integer tabDetoperacion;

    @Column(name = "detoperacion")
    private Integer detoperacion;

    @Column(name = "tab_tareaoperacion")
    private Integer tabTareaoperacion;

    @Column(name = "tareaoperacion")
    private Integer tareaoperacion;

    @Column(name = "tab_opermayor")
    private Integer tabOpermayor;

    @Column(name = "opermayor")
    private Integer opermayor;

    @Column(name = "id_itemaf")
    private Integer idItemaf;

    @Column(name = "glosa")
    private String glosa;

    @Column(name = "monto_orig")
    private BigDecimal montoOrig;

    @Column(name = "tab_monedaamtorig")
    private Integer tabMonedaamtorig;

    @Column(name = "monedaamtorig")
    private Integer monedaamtorig;

    @Column(name = "tipo_cambio")
    private BigDecimal tipoCambio;

    @Column(name = "monto_desc")
    private BigDecimal montoDesc;

    @Column(name = "monto_cont")
    private BigDecimal montoCont;

    @Column(name = "tab_tipocargo")
    private Integer tabTipocargo;

    @Column(name = "tipocargo")
    private Integer tipocargo;

    @Column(name = "monto")
    private BigDecimal monto;

    @Column(name = "cantidad")
    private BigDecimal cantidad;

    @Column(name = "tab_unidadmed")
    private Integer tabUnidadmed;

    @Column(name = "unidadmed")
    private Integer unidadmed;

    @Column(name = "tab_metodocalc")
    private Integer tabMetodocalc;

    @Column(name = "metodocalc")
    private Integer metodocalc;

    @Column(name = "fecha_oper")
    private Date fechaOper;

    @Column(name = "fecha_valor")
    private Date fechaValor;

    @Column(name = "tab_tipooperacion")
    private Integer tabTipooperacion;

    @Column(name = "tipooperacion")
    private Integer tipooperacion;

    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @Column(name = "id_empleadoaut")
    private Integer idEmpleadoaut;

    @Column(name = "id_unidad")
    private Integer idUnidad;

    @Column(name = "id_usrreg")
    private String idUsrreg;

    @Column(name = "id_usraut")
    private String idUsraut;

    @Column(name = "id_transdetpadre")
    private Integer idTransdetpadre;

    @Column(name = "id_trxorigen")
    private Integer idTrxorigen;

    @Column(name = "tx_fecha")
    private Date txFecha;

    @Column(name = "tx_usuario")
    private Integer txUsuario;
    // @IpAddress
    @Column(name = "tx_host")
    private String txHost;

    @Transient
    private GenDesctabla detoperaciondesc;

    @Transient
    private GenDesctabla tareaoperaciondesc;

    @Transient
    private GenDesctabla opermayordesc;

    @Transient
    private AfItemaf itemaf;

    @Transient
    private OrgEmpleado empleado;

    @Transient
    private GenDesctabla tipooperaciondesc;

    public static TxTransdet clone(TxTransdet t) {
        return TxTransdet.builder()
                // .idTransdet(t.getIdTransdet())//
                .idTransaccion(t.getIdTransaccion()) //
                .idCorrelativo(t.getIdCorrelativo())
                .tabDetoperacion(t.getTabDetoperacion())
                .detoperacion(t.getDetoperacion())
                .tabTareaoperacion(t.getTabTareaoperacion())
                .tareaoperacion(t.getTareaoperacion())
                .tabOpermayor(t.getTabOpermayor())
                .opermayor(t.getOpermayor())
                .idItemaf(t.getIdItemaf())
                .glosa(t.getGlosa())
                .monto(t.getMonto())
                .montoOrig(t.getMontoOrig())
                .tabMonedaamtorig(t.getTabMonedaamtorig())
                .monedaamtorig(t.getMonedaamtorig())
                .tipoCambio(t.getTipoCambio())
                .montoDesc(t.getMontoDesc())
                .montoCont(t.getMontoCont())
                .tipocargo(t.getTipocargo())
                .cantidad(t.getCantidad())
                .tabUnidadmed(t.getTabUnidadmed())
                .unidadmed(t.getUnidadmed())
                .tabMetodocalc(t.getTabMetodocalc())
                .metodocalc(t.getMetodocalc())
                .fechaOper(t.getFechaOper())
                .fechaValor(t.getFechaValor())
                .tabTipooperacion(t.getTabTipooperacion())
                .tipooperacion(t.getTipooperacion())
                .idEmpleado(t.getIdEmpleado())
                .idEmpleadoaut(t.getIdEmpleadoaut())
                .idUnidad(t.getIdUnidad())
                .idUsrreg(t.getIdUsrreg())
                .idUsraut(t.getIdUsraut())
                .idTransdetpadre(t.getIdTransdetpadre())
                .idTrxorigen(t.getIdTrxorigen())
                .build();
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

    public static void normalizerData(TxTransdet trxdet) {
        trxdet.setMontoOrig(trxdet.getMontoOrig() == null ? BigDecimal.ZERO : trxdet.getMontoOrig());
        trxdet.setTipoCambio(trxdet.getTipoCambio() == null ? BigDecimal.ZERO : trxdet.getTipoCambio());
        trxdet.setMontoDesc(trxdet.getMontoDesc() == null ? BigDecimal.ZERO : trxdet.getMontoDesc());
        trxdet.setMontoCont(trxdet.getMontoCont() == null ? BigDecimal.ZERO : trxdet.getMontoCont());
        trxdet.setCantidad(trxdet.getCantidad() == null ? BigDecimal.ZERO : trxdet.getCantidad());
        trxdet.setMonto(trxdet.getMonto() == null ? BigDecimal.ZERO : trxdet.getMonto());
    }
}
