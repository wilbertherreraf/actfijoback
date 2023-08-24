package gob.gamo.activosf.app.dto;

public enum ReporteContableEnum {
    INVENTARIO_ORDENADO_CODIGO_ACTIVO(
            "/Rep1_INVENTARIO_ORDENADO_CODIGO_ACTIVO.xlsx", "Inventario ordenado por Código de Activo"),
    INVENTARIO_AGRUPADO_POR_FAMILIA("/Rep2_AGRUPADO_POR_FAMILIA.xlsx", "Inventario agrupado por Familia"),
    INVENTARIO_AGRUPADO_POR_SUBFAMILIA("/Rep3_AGRUPADO_POR_SUBFAMILIA.xlsx", "Inventario agrupado por Subfamilia"),
    INVENTARIO_AGRUPADO_POR_CODIGO_CONTABLE(
            "/Rep4_AGRUPADO_POR_CODIGO_CONTABLE.xlsx", "Inventario agrupado por Código Contable"),
    INVENTARIO_AGRUPADO_POR_PARTIDA_PRESUPUESTARIA(
            "/Rep5_AGRUPADO_POR_PARTIDA_PRESUPUESTARIA.xlsx", "Inventario agrupado por Partida Presupuestaria"),
    AGRUPADO_POR_FAMILIA("/Rep6_AGRUPADO_POR_FAMILIA.xlsx", "Agrupado por Familia"),
    INVENTARIO_AGRUPADO_RESPONSABLE("/Rep7_AGRUPADO_POR_RESPONSABLE.xlsx", "Inventario agrupado por Responsable"),
    INVENTARIO_ORDENADO_CODIGO_ACTIVO_REVALUO(
            "/Rep1_1_INVENTARIO_ORDENADO_CODIGO_ACTIVO_REVALUO.xlsx",
            "Inventario ordenado por Código de Activo con Revalúo"),
    AGRUPADO_POR_FAMILIA_REVALUO("/Rep6_1_AGRUPADO_POR_FAMILIA_REVALUO.xlsx", "Agrupado por Familia con Revalúo");

    private String template;
    private String label;

    private ReporteContableEnum(String template, String label) {
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
