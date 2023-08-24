package gob.gamo.activosf.app.dto.sec;

import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonRootName;

import gob.gamo.activosf.app.domain.entities.Recurso;
import gob.gamo.activosf.app.domain.entities.Roles;

@JsonRootName("rol")
public record RolesVO(String codrol, String descripcion, List<String> permisosList) {
    public Set<Recurso> permisos() {
        return permisosList.stream().map(p -> new Recurso(p, p)).collect(toSet());
    }

    public RolesVO(Roles rol) {
        this(
                rol.getCodrol(),
                rol.getDescripcion(),
                rol.getIncludeRecursos().stream()
                        .map(r -> r.getRecurso().getCodrec())
                        .collect(Collectors.toList()));
    }
}
