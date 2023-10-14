package gob.gamo.activosf.app.dto;

import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import gob.gamo.activosf.app.domain.OrgEmpleado;
import gob.gamo.activosf.app.domain.entities.GenDesctabla;
import gob.gamo.activosf.app.handlers.DateDesserializerJson;
import gob.gamo.activosf.app.handlers.DateSerializerJson;

@JsonRootName("empleado")
public record EmpleadoVo(Integer id,
        Integer idUnidad,
        String unidaddesc,
        String codInternoempl,
        Integer idPersona,
        String codPersona,
        Integer idCargo,
        Integer tabRolempleado,
        Integer rolempleado,
        GenDesctabla rolempleadodesc,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") //, timezone = "America/Los_Angeles"
        @JsonSerialize(using = DateSerializerJson.class)
        @JsonDeserialize(using = DateDesserializerJson.class)
        Date fechaIngreso,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") //, timezone = "America/Los_Angeles"
        @JsonSerialize(using = DateSerializerJson.class)
        @JsonDeserialize(using = DateDesserializerJson.class)        
        Date fechaBaja,

        String estado,
        PersonaVO persona
        ) {
    public EmpleadoVo(OrgEmpleado e) {
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
                e.getRolempleadodesc(), 
                e.getFechaIngreso(), 
                e.getFechaBaja(), 
                e.getEstado(),
                e.getPersona() != null ? new PersonaVO(e.getPersona()) : null
                );
    }
    public EmpleadoVo(OrgEmpleado e, PersonaVO p) {
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
                e.getRolempleadodesc(), 
                e.getFechaIngreso(), 
                e.getFechaBaja(), 
                e.getEstado(),
                p != null ? p : null
                );
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
                .fechaIngreso(fechaIngreso)
                .fechaBaja(fechaBaja)
                .estado(estado)
                .build();
    }
}

// = new HashSet<>(),
