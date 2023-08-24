package gob.gamo.activosf.app.dto;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AgrupadorReporteVo implements java.io.Serializable {

    public static final SimpleDateFormat SD_NORMAL = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat SD_EXTENDIDO = new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy");

    private String familia;
    private String subfamilia;
    private String partidaPresupuestaria;
    private String codigoContable;
    private String asignado;
    private String ambiente;
    private Date fechaCalculo;
    private BigDecimal tipoCambioFechaCalculo;

    private List<ItemReporteVo> items;

    public String getFechaLiteralExtendida() {
        return SD_EXTENDIDO.format(fechaCalculo);
    }

    public String getFechaLiteral() {
        return SD_NORMAL.format(fechaCalculo);
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getSubfamilia() {
        return subfamilia;
    }

    public void setSubfamilia(String subfamilia) {
        this.subfamilia = subfamilia;
    }

    public String getPartidaPresupuestaria() {
        return partidaPresupuestaria;
    }

    public void setPartidaPresupuestaria(String partidaPresupuestaria) {
        this.partidaPresupuestaria = partidaPresupuestaria;
    }

    public String getCodigoContable() {
        return codigoContable;
    }

    public void setCodigoContable(String codigoContable) {
        this.codigoContable = codigoContable;
    }

    public String getAsignado() {
        return asignado;
    }

    public void setAsignado(String asignado) {
        this.asignado = asignado;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public Date getFechaCalculo() {
        return fechaCalculo;
    }

    public void setFechaCalculo(Date fechaCalculo) {
        this.fechaCalculo = fechaCalculo;
    }

    public BigDecimal getTipoCambioFechaCalculo() {
        return tipoCambioFechaCalculo;
    }

    public void setTipoCambioFechaCalculo(BigDecimal tipoCambioFechaCalculo) {
        this.tipoCambioFechaCalculo = tipoCambioFechaCalculo;
    }

    public List<ItemReporteVo> getItems() {
        return items;
    }

    public void setItems(List<ItemReporteVo> items) {
        this.items = items;
    }
}
