package gob.gamo.activosf.app.dto;

import java.math.BigDecimal;
import java.util.Date;

public class RevaluoImp implements java.io.Serializable {

    private String codigoActivo;
    private Date fechaRevaluo;
    private BigDecimal nuevoValor;
    private BigDecimal nuevoFactorDepreciacion;
    private String dispocisionRespaldo;
    private String motivo;

    public RevaluoImp() {
        super();
    }

    public String getCodigoActivo() {
        return codigoActivo;
    }

    public void setCodigoActivo(String codigoActivo) {
        this.codigoActivo = codigoActivo;
    }

    public Date getFechaRevaluo() {
        return fechaRevaluo;
    }

    public void setFechaRevaluo(Date fechaRevaluo) {
        this.fechaRevaluo = fechaRevaluo;
    }

    public BigDecimal getNuevoValor() {
        return nuevoValor;
    }

    public void setNuevoValor(BigDecimal nuevoValor) {
        this.nuevoValor = nuevoValor;
    }

    public BigDecimal getNuevoFactorDepreciacion() {
        return nuevoFactorDepreciacion;
    }

    public void setNuevoFactorDepreciacion(BigDecimal nuevoFactorDepreciacion) {
        this.nuevoFactorDepreciacion = nuevoFactorDepreciacion;
    }

    public String getDispocisionRespaldo() {
        return dispocisionRespaldo;
    }

    public void setDispocisionRespaldo(String dispocisionRespaldo) {
        this.dispocisionRespaldo = dispocisionRespaldo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
