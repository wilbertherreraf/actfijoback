package gob.gamo.activosf.app.dto;

public enum ReporteActivoFijoEnum {
    INVENTARIO_ORDENADO_CODIGO_ACTIVO("/Rep8_INVENTARIO_GENERAL.xlsx", "Inventario ordenado por Código de Activo"),
    INVENTARIO_AGRUPADO_POR_FAMILIA("/Rep9_AGRUPADO_POR_FAMILIA.xlsx", "Inventario agrupado por Familia"),
    INVENTARIO_AGRUPADO_POR_SUBFAMILIA("/Rep10_AGRUPADO_POR_SUBFAMILIA.xlsx", "Inventario agrupado por Subfamilia"),
    INVENTARIO_AGRUPADO_POR_CODIGO_CONTABLE(
            "/Rep11_AGRUPADO_POR_CODIGO_CONTABLE.xlsx", "Inventario agrupado por Código Contable"),
    INVENTARIO_AGRUPADO_POR_PARTIDA_PRESUPUESTARIA(
            "/Rep12_AGRUPADO_POR_PARTIDA_PRESUPUESTARIA.xlsx", "Inventario agrupado por Partida Presupuestaria"),
    INVENTARIO_AGRUPADO_RESPONSABLE("/Rep13_INVENTARIO_POR_RESPONSABLE.xlsx", "Inventario agrupado por Responsable");

    private String template;
    private String label;

    private ReporteActivoFijoEnum(String template, String label) {
        this.template = template;
        this.label = label;
    }

    public String getTemplate() {
        return template;
    }

    public String getLabel() {
        return label;
    }
}
