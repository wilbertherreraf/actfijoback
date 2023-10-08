package gob.gamo.activosf.app.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonRootName;

import gob.gamo.activosf.app.domain.OrgPersona;
import gob.gamo.activosf.app.dto.sec.SignUpUserRequest;
import gob.gamo.activosf.app.dto.sec.UserVO;

@JsonRootName("persona")
public record PersonaVO(
        Integer idPersona,
        String nombre,
        String nombreDesc,
        String primerApellido,
        String segundoApellido,
        String numeroDocumento,
        String tipoDocumento,
        Integer tabTipodoc,
        Integer tipodoc,
        String direccion,
        String telefono,
        String email,
        Integer tabTipopers,
        Integer tipopers,
        String nemonico,
        Date txFecha,
        String usuario,
        String estado,
        String trato,
        SignUpUserRequest user
        ) {

    public OrgPersona persona() {
        return OrgPersona.builder()
                .idPersona(idPersona)
                .nombre(nombre)
                .nombreDesc(nombreDesc)
                .primerApellido(primerApellido)
                .segundoApellido(segundoApellido)
                .numeroDocumento(numeroDocumento)
                .tipoDocumento(tipoDocumento)
                .tabTipodoc(tabTipodoc)
                .tipodoc(tipodoc)
                .direccion(direccion)
                .telefono(telefono)
                .email(email)
                .tabTipopers(tabTipopers)
                .tipopers(tipopers)
                .nemonico(nemonico)
                .txFecha(txFecha)
                .usuario(usuario)
                .estado(estado)
                .trato(trato)
                //.user(user)
                .build();
    }
    ;
}
