package gob.gamo.activosf.app.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonRootName;

import gob.gamo.activosf.app.domain.OrgUnidad;

@JsonRootName("unidad")
public record UnidadResponse(
        Integer id,
        String nombre,
        String domicilio,
        String sigla,
        String telefono,
        String tipoUnidad,
        Integer idUnidadPadre,
        Integer tabRolempleado,
        Integer rolempleado,
        EmpleadoVo empleadoBoss,
        String estado,
        UnidadResponse unidadPadre,
        Set<UnidadResponse> children) {
    public UnidadResponse(OrgUnidad entity) {

        this(
                entity.getIdUnidad(),
                entity.getNombre(),
                entity.getDomicilio(),
                entity.getSigla(),
                entity.getTelefono(),
                entity.getTipoUnidad(),
                entity.getIdUnidadPadre(),
                entity.getTabRolempleado(),
                entity.getRolempleado(),
                entity.getEmpleadoBoss() != null ? new EmpleadoVo(entity.getEmpleadoBoss()) : null,
                entity.getEstado(),
                entity.getUnidadPadre() != null
                        ? new UnidadResponse(entity.getIdUnidadPadre(), entity.getUnidadPadre().getNombre(),
                                entity.getUnidadPadre().getDomicilio(),
                                entity.getUnidadPadre().getSigla(),
                                entity.getUnidadPadre().getTelefono(),
                                entity.getUnidadPadre().getTipoUnidad(),
                                null,
                                entity.getUnidadPadre().getTabRolempleado(),
                                entity.getUnidadPadre().getRolempleado(),
                                entity.getEmpleadoBoss() != null ? new EmpleadoVo(entity.getEmpleadoBoss()) : null,
                                entity.getUnidadPadre().getEstado(),
                                null, new HashSet<>())
                        : null,
                entity.getChildren().stream().map(x ->
                // UnidadResponse::new
                new UnidadResponse(x.getIdUnidad(),
                        x.getNombre(),
                        x.getDomicilio(),
                        x.getSigla(),
                        x.getTelefono(), x.getTipoUnidad(),
                        x.getIdUnidadPadre(),
                        x.getTabRolempleado(),
                        x.getRolempleado(),
                        x.getEmpleadoBoss() != null ? new EmpleadoVo(x.getEmpleadoBoss()) : null,
                        x.getEstado(),
                        null, new HashSet<>())).collect(Collectors.toSet()));
    };

    public UnidadResponse(OrgUnidad entity, boolean all) {

        this(
                entity.getIdUnidad(),
                entity.getNombre(),
                entity.getDomicilio(),
                entity.getSigla(),
                entity.getTelefono(),
                entity.getTipoUnidad(),
                entity.getIdUnidadPadre(),
                entity.getTabRolempleado(),
                entity.getRolempleado(),
                entity.getEmpleadoBoss() != null ? new EmpleadoVo(entity.getEmpleadoBoss()) : null,
                entity.getEstado(),
                entity.getUnidadPadre() != null
                        ? new UnidadResponse(entity.getIdUnidadPadre(), entity.getUnidadPadre().getNombre(),
                                entity.getUnidadPadre().getDomicilio(),
                                entity.getUnidadPadre().getSigla(),
                                entity.getUnidadPadre().getTelefono(),
                                entity.getUnidadPadre().getTipoUnidad(),
                                null,
                                entity.getTabRolempleado(),
                                entity.getRolempleado(),
                                entity.getEmpleadoBoss() != null ? new EmpleadoVo(entity.getEmpleadoBoss()) : null,
                                entity.getUnidadPadre().getEstado(),
                                null, new HashSet<>())
                        : null,
                entity.getChildren().stream().map(x -> new UnidadResponse(x, true)).collect(Collectors.toSet()));
    }
}
