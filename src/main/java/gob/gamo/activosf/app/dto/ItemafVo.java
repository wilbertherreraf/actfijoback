package gob.gamo.activosf.app.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonRootName;

import gob.gamo.activosf.app.domain.AfItemaf;
import gob.gamo.activosf.app.domain.entities.GenDesctabla;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;

@JsonRootName(value = "itemsaf")
public record ItemafVo(
        Integer id,
        String codnemo,
        String nivel,
        String tipo,
        String grupo,
        String clase,
        String familia,
        String item,
        String nombre,
        String unidmedida,
        String codclasif,
        String tipoCostodi,
        String tipoCostofv,
        BigDecimal punit,
        Integer stock,
        Integer stockMin,
        Integer tabUmedida,
        Integer umedida,
        GenDesctabla umedidadesc) {
    public ItemafVo(AfItemaf t) {
        this(
                t.getId(),
                t.getCodnemo(),
                t.getNivel(),
                t.getTipo(),
                t.getGrupo(),
                t.getClase(),
                t.getFamilia(),
                t.getItem(),
                t.getNombre(),
                t.getUnidmedida(),
                t.getCodclasif(),
                t.getTipoCostodi(),
                t.getTipoCostofv(),
                t.getPunit(),
                t.getStock(),
                t.getStockMin(),
                t.getTabUmedida(),
                t.getUmedida(),
                t.getUmedidadesc());
    }

    public AfItemaf itemaf() {
        return AfItemaf.builder()
                .id(id)
                .codnemo(codnemo)
                .nivel(nivel)
                .tipo(tipo)
                .grupo(grupo)
                .clase(clase)
                .familia(familia)
                .item(item)
                .nombre(nombre)
                .unidmedida(unidmedida)
                .codclasif(codclasif)
                .tipoCostodi(tipoCostodi)
                .tipoCostofv(tipoCostofv)
                .punit(punit)
                .stock(stock)
                .stockMin(stockMin)
                .tabUmedida(tabUmedida)
                .umedida(umedida)
                .build();
    };
}
