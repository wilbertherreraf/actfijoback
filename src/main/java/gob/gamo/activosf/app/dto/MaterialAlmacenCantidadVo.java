package gob.gamo.activosf.app.dto;

import java.util.ArrayList;
import java.util.List;

import gob.gamo.activosf.app.domain.AfMaterial;
import gob.gamo.activosf.app.domain.AfSolicitudMaterial;

public class MaterialAlmacenCantidadVo implements java.io.Serializable {

    private AfMaterial afMaterial;
    private AfSolicitudMaterial afSolicitudMaterial;
    private Integer cantidadSolicitada;

    private List<AlmacenCantidadVo> almacenCantidadVoList;

    public MaterialAlmacenCantidadVo(AfMaterial afMaterial) {
        super();
        this.afMaterial = afMaterial;
        almacenCantidadVoList = new ArrayList<AlmacenCantidadVo>();
    }

    public MaterialAlmacenCantidadVo() {
        super();
        almacenCantidadVoList = new ArrayList<AlmacenCantidadVo>();
    }

    public AfMaterial getAfMaterial() {
        return afMaterial;
    }

    public void setAfMaterial(AfMaterial afMaterial) {
        this.afMaterial = afMaterial;
    }

    public List<AlmacenCantidadVo> getAlmacenCantidadVoList() {
        return almacenCantidadVoList;
    }

    public void setAlmacenCantidadVoList(List<AlmacenCantidadVo> almacenCantidadVoList) {
        this.almacenCantidadVoList = almacenCantidadVoList;
    }

    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(int cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public AfSolicitudMaterial getAfSolicitudMaterial() {
        return afSolicitudMaterial;
    }

    public void setAfSolicitudMaterial(AfSolicitudMaterial afSolicitudMaterial) {
        this.afSolicitudMaterial = afSolicitudMaterial;
    }
}
