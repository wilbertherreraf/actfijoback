package gob.gamo.activosf.app.dto;

import gob.gamo.activosf.app.domain.AfAlmacen;

public class AlmacenCantidadVo implements java.io.Serializable {

    private AfAlmacen afAlmacen;
    private Integer cantidad;
    private Integer cantidadAsignada;

    public AlmacenCantidadVo() {
        super();
    }

    public AlmacenCantidadVo(AfAlmacen afAlmacen, Integer cantidad) {
        super();
        this.afAlmacen = afAlmacen;
        this.cantidad = cantidad;
    }

    public AfAlmacen getAfAlmacen() {
        return afAlmacen;
    }

    public void setAfAlmacen(AfAlmacen afAlmacen) {
        this.afAlmacen = afAlmacen;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidadAsignada() {
        return cantidadAsignada;
    }

    public void setCantidadAsignada(int cantidadAsignada) {
        this.cantidadAsignada = cantidadAsignada;
    }
}
