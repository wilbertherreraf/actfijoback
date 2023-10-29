package gob.gamo.activosf.app.security.mapper;

import java.util.*;

import gob.gamo.activosf.app.domain.entities.Roles;
import gob.gamo.activosf.app.dto.sec.RolesVO;

public class RoleMapper {
    public static Collection<RolesVO> toRoleDtos(Collection<Roles> all) {
        if (all == null) {
            return Collections.emptyList();
        }
        return all.stream().map(x -> new RolesVO(x)).toList();
    }

    public static List<String> recursosToList(Collection<Roles> all) {
        return all.stream().flatMap(r -> r.getIncludeRecursos().stream()).map(r -> r.getRecurso().getCodrec()).distinct().toList();
    }

    public static List<String> rolesToList(Collection<Roles> all) {
        return all.stream().map(r -> r.getCodrol()).distinct().toList();
    }    
}
