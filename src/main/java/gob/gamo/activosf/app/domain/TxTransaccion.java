/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.entities.GenDesctabla;

/**
 *
 * @author wherrera
 */
@Entity
@Table(name = "tx_transaccion")
@Setter
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class TxTransaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private Integer idTransaccion;

    @Column(name = "id_itemaf")
    private Integer idItemaf;

    @Column(name = "tab_monedaamt")
    private Integer tabMonedaamt;

    @Column(name = "monedaamt")
    private Integer monedaamt;

    @Column(name = "importe")
    private BigDecimal importe;

    @Column(name = "nro_doc")
    private String nroDoc;

    @Column(name = "tab_tipoopersub")
    private Integer tabTipoopersub;

    @Column(name = "tipoopersub")
    private Integer tipoopersub;

    @Column(name = "tab_tipooperacion")
    private Integer tabTipooperacion;

    @Column(name = "tipooperacion")
    private Integer tipooperacion;

    @Column(name = "glosa")
    private String glosa;

    @Column(name = "monto")
    private BigDecimal monto;

    @Column(name = "fecha_oper")
    private Date fechaOper;

    @Column(name = "fecha_valor")
    private Date fechaValor;

    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @Column(name = "id_empleadoaut")
    private Integer idEmpleadoaut;

    @Column(name = "id_unidad")
    private Integer idUnidad;

    @Column(name = "id_unidaddest")
    private Integer idUnidaddest;

    @Column(name = "id_usrreg")
    private String idUsrreg;

    @Column(name = "id_usraut")
    private String idUsraut;

    @Column(name = "id_trxorigen")
    private Integer idTrxorigen;

    @Column(name = "tab_tareaoperacion")
    private Integer tabTareaoperacion;

    @Column(name = "tareaoperacion")
    private Integer tareaoperacion;

    @Column(name = "tx_fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date txFecha;
    /*
     * @Column(name = "fecha_aprob")
     *
     * @Temporal(TemporalType.TIMESTAMP)
     * private Date fecha_aprob;
     */

    @Column(name = "tx_usuario")
    private Integer txUsuario;
    // @IpAddress
    @Column(name = "tx_host")
    private String txHost;

    @Transient
    private GenDesctabla tipooperaciondesc;

    @Transient
    private GenDesctabla tipoopersubdesc;

    @Transient
    private GenDesctabla tareaoperaciondesc;

    @Transient
    private OrgUnidad unidad;

    @Transient
    private OrgEmpleado empleado;

    @Transient
    private AfItemaf itemaf;

    @Builder.Default
    @Transient
    private List<TxTransdet> transdet = new ArrayList<>();

    public static TxTransaccion clone(TxTransaccion t) {
        return TxTransaccion.builder() //
                .idTransaccion(t.getIdTransaccion())
                .tabTipoopersub(t.getTabTipoopersub())
                .tipoopersub(t.getTipoopersub())
                .tabTipooperacion(t.getTabTipooperacion())
                .tipooperacion(t.getTipooperacion())
                .glosa(t.getGlosa())
                .monto(t.getMonto())
                .fechaOper(t.getFechaOper())
                .fechaValor(t.getFechaValor())
                .idEmpleado(t.getIdEmpleado())
                .idEmpleadoaut(t.getIdEmpleadoaut())
                .idUnidad(t.getIdUnidad())
                .idUnidaddest(t.getIdUnidaddest())
                .idUsrreg(t.getIdUsrreg())
                .idUsraut(t.getIdUsraut())
                .idTrxorigen(t.getIdTrxorigen())
                .tabTareaoperacion(t.getTabTareaoperacion())
                .tareaoperacion(t.getTareaoperacion())
                .txFecha(t.getTxFecha())
                .txUsuario(t.getTxUsuario())
                .txHost(t.getTxHost())
                .build();
    }

    public static TxTransaccion nuevoReg() {
        TxTransaccion td = new TxTransaccion();
        td.setTabTipoopersub(Constants.TAB_MD);
        // td.setTabTipooperacion(Constants.TAB_TASK);
        td.setTabTareaoperacion(Constants.TAB_TASK);
        td.setTareaoperacion(Constants.TAB_TASK_PRE);
        td.setMonto(BigDecimal.ZERO);
        td.setImporte(BigDecimal.ZERO);
        td.setFechaOper(new Date());
        return td;
    }
}
