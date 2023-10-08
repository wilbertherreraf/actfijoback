/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gob.gamo.activosf.app.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import gob.gamo.activosf.app.domain.entities.GenDesctabla;
import gob.gamo.activosf.app.dto.sec.UserVO;

/**
 *
 * @author wherrera
 */
@Entity
@Getter
@Setter
@Builder
@Table(name = "org_persona")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class OrgPersona {
    @Id
    @Column(name = "id_persona")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPersona;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "nombre_desc")
    private String nombreDesc;

    @Column(name = "primer_apellido")
    private String primerApellido;

    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @Column(name = "tabtipodoc")
    private Integer tabTipodoc;

    @Column(name = "tipodoc")
    private Integer tipodoc;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "email")
    private String email;

    @Column(name = "tabtipopers")
    private Integer tabTipopers;

    @Column(name = "tipopers")
    private Integer tipopers;

    @Column(name = "nemonico")
    private String nemonico;

    @Column(name = "tx_fecha")
    private Date txFecha;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "estado")
    private String estado;

    @Column(name = "trato")
    private String trato;

    // @Transient
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(updatable = false, insertable = false, name = "tabtipodoc", referencedColumnName = "des_codtab"),
        @JoinColumn(updatable = false, insertable = false, name = "tipodoc", referencedColumnName = "des_codigo")
    })
    private GenDesctabla tipodocdesc;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(updatable = false, insertable = false, name = "tabtipopers", referencedColumnName = "des_codtab"),
        @JoinColumn(updatable = false, insertable = false, name = "tipopers", referencedColumnName = "des_codigo")
    })
    private GenDesctabla tipopersdesc;

    @Transient
    private UserVO user;
}
