package gob.gamo.activosf.app.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonRootName;

import gob.gamo.activosf.app.domain.OrgUnidad;
import gob.gamo.activosf.app.domain.entities.GenDesctabla;

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
        GenDesctabla rolempleadodesc,
        Integer idEmpleado,
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
                entity.getRolempleadodesc(),
                entity.getIdEmpleado(),
                entity.getEmpleadoBoss() != null ? new EmpleadoVo(entity.getEmpleadoBoss(), null, false) : null,
                entity.getEstado(),
                entity.getUnidadPadre() != null ? new UnidadResponse(entity.getUnidadPadre(), false) : null,
                entity.getChildren().stream()
                        .map(x -> new UnidadResponse(x, false))
                        .collect(Collectors.toSet()));
    }
    ;

    public UnidadResponse(OrgUnidad entity, boolean all) {
        this(
                entity.getIdUnidad(),
                entity.getNombre(),
                entity.getDomicilio(),
                entity.getSigla(),
                entity.getTelefono(),
                entity.getTipoUnidad(),
                all ? entity.getIdUnidadPadre() : null,
                entity.getTabRolempleado(),
                entity.getRolempleado(),
                entity.getRolempleadodesc(),
                entity.getIdEmpleado(),
                all
                        ? (entity.getEmpleadoBoss() != null
                                ? new EmpleadoVo(entity.getEmpleadoBoss(), null, false)
                                : null)
                        : null,
                entity.getEstado(),
                null,
                new HashSet<>());
    }
}
