package gob.gamo.activosf.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import gob.gamo.activosf.app.domain.entities.GenDesctabla;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
@Table(name = "acf_itemaf")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AfItemaf {
    @Id
    @Column(name = "id_item")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codnemo")
    private String codnemo;

    @Column(name = "nivel")
    private String nivel;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "grupo")
    private String grupo;

    @Column(name = "clase")
    private String clase;

    @Column(name = "familia")
    private String familia;

    @Column(name = "item")
    private String item;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "unidmedida")
    private String unidmedida;

    @Column(name = "codclasif")
    private String codclasif;

    @Column(name = "tipo_costodi")
    private String tipoCostodi;

    @Column(name = "tipo_costofv")
    private String tipoCostofv;

    @Column(name = "precio_unitario")
    private BigDecimal punit;
    
    @Column(name = "stock")
    private Integer stock;
    
    @Column(name = "stock_min")
    private Integer stockMin;

    @Column(name = "tab_umedida")
    private Integer tabUmedida;

    @Column(name = "umedida")
    private Integer umedida;

    @Transient
    private GenDesctabla umedidadesc;
}
