package gob.gamo.activosf.app.dto.sec;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

import gob.gamo.activosf.app.domain.entities.Roles;

@JsonRootName("rol")
public record RolesVO(String codrol, String descripcion, List<String> permisosList) {
    public RolesVO(Roles rol) {
        this(
                rol.getCodrol(),
                rol.getDescripcion(),
                new ArrayList<>(rol.getIncludeRecursos().stream()
                        .map(x -> x.getRecurso().getCodrec())
                        .toList()));
    }
}
