package gob.gamo.activosf.app.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import gob.gamo.activosf.app.domain.OrgEmpleado;
import gob.gamo.activosf.app.domain.OrgPersona;
import gob.gamo.activosf.app.domain.entities.GenDesctabla;
import gob.gamo.activosf.app.handlers.DateDesserializerJson;
import gob.gamo.activosf.app.handlers.DateSerializerJson;

@JsonRootName("empleado")
public record EmpleadoVo(
        Integer id,
        Integer idUnidad,
        String unidaddesc,
        String codInternoempl,
        Integer idPersona,
        String codPersona,
        Integer idCargo,
        Integer tabRolempleado,
        Integer rolempleado,
        Integer idEmpleadopadre,
        GenDesctabla rolempleadodesc,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // , timezone = "America/Los_Angeles"
                @JsonSerialize(using = DateSerializerJson.class)
                @JsonDeserialize(using = DateDesserializerJson.class)
                Date fechaIngreso,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // , timezone = "America/Los_Angeles"
                @JsonSerialize(using = DateSerializerJson.class)
                @JsonDeserialize(using = DateDesserializerJson.class)
                Date fechaBaja,
        String estado,
        String nombre,
        String numeroDocumento,
        String direccion,
        String telefono,
        String email,
        PersonaVO persona) {

    public EmpleadoVo(OrgEmpleado e) {
        this(e, e.getPersona() != null ? new PersonaVO(e.getPersona(), null) : null);
    }

    public EmpleadoVo(OrgEmpleado e, OrgPersona p, boolean includePer) {
        this(e, p != null ? new PersonaVO(p, null) : null, includePer, false);
    }

    public EmpleadoVo(OrgEmpleado e, PersonaVO p) {
        this(e, p, true, false);
    }

    public EmpleadoVo(OrgEmpleado e, PersonaVO p, boolean includePer, boolean includeDet) {
        this(
                e.getId(),
                e.getIdUnidad(),
                e.getUnidad() != null ? e.getUnidad().getNombre() : null,
                e.getCodInternoempl(),
                e.getIdPersona(),
                e.getCodPersona(),
                e.getIdCargo(),
                e.getTabRolempleado(),
                e.getRolempleado(),
                e.getIdEmpleadopadre(),
                e.getRolempleadodesc(),
                e.getFechaIngreso(),
                e.getFechaBaja(),
                e.getEstado(),
                p != null ? p.nombre() : null,
                p != null ? p.numeroDocumento() : null,
                p != null ? p.direccion() : null,
                p != null ? p.telefono() : null,
                p != null ? p.email() : null,
                includePer ? p : null);
    }

    public OrgEmpleado empleado() {
        return OrgEmpleado.builder()
                .id(id)
                .idUnidad(idUnidad)
                .codInternoempl(codInternoempl)
                .idPersona(idPersona)
                .codPersona(codPersona)
                .idCargo(idCargo)
                .tabRolempleado(tabRolempleado)
                .rolempleado(rolempleado)
                .idEmpleadopadre(idEmpleadopadre)
                .fechaIngreso(fechaIngreso)
                .fechaBaja(fechaBaja)
                .estado(estado)
                .build();
    }
}
