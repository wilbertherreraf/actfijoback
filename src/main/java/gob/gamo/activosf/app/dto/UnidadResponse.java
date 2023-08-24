package gob.gamo.activosf.app.dto;

import gob.gamo.activosf.app.domain.OrgUnidad;

public record UnidadResponse(
        Integer id,
        String nombre,
        String domicilio,
        String sigla,
        String telefono,
        String tipoUnidad,
        Integer idUnidadPadre,
        String estado) {
    public UnidadResponse(OrgUnidad entity) {
        this(
                entity.getIdUnidad(),
                entity.getNombre(),
                entity.getDomicilio(),
                entity.getSigla(),
                entity.getTelefono(),
                entity.getTipoUnidad(),
                entity.getIdUnidadPadre(),
                entity.getEstado());
    }
}
