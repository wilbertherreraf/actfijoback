/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import gob.gamo.activosf.app.domain.entities.GenDesctabla;
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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "tx_usuario")
    private Integer txUsuario;

    @Column(name = "tx_host")
    private String txHost;

    @Transient
    private GenDesctabla tipooperaciondesc;

    @Transient
    private GenDesctabla tipoopersubdesc;

    @Transient
    private OrgUnidad unidad;    

    @Transient
    private OrgEmpleado empleado;

    @Transient
    private List<TxTransdet> transdet;
}
