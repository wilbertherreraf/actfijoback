package gob.gamo.activosf.app.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.entities.Recurso;
import gob.gamo.activosf.app.domain.entities.Roles;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.sec.RolesVO;
import gob.gamo.activosf.app.dto.sec.SingleRolResponse;
import gob.gamo.activosf.app.repository.sec.RecursoRepository;
import gob.gamo.activosf.app.services.sec.RolesService;
import gob.gamo.activosf.app.utils.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = Constants.API_URL_ROOT + Constants.API_URL_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
public class RecursosController {
    private final RolesService service;
    private final RecursoRepository repository;
    private static final String ENTITY_NAME = Constants.REC_PERMISOS;

    @GetMapping(Constants.API_PERMISOS)
    public ResponseEntity<List<Recurso>> getAll() {
        List<Recurso> list = repository.findAll();
        log.info("permisos {}", list.size());
/*         HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, Constants.API_URL_ROOT + Constants.API_URL_VERSION + Constants.API_PERMISOS); */
        return ResponseEntity.ok().body(list);
    }
/* 
    @PostMapping(Constants.API_PERMISOS)
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public SingleRolResponse createRol(User me, @RequestBody RolesVO request) {
        RolesVO rol = rolesService.createRol(me, request);
        return new SingleRolResponse(rol);
    }

    @GetMapping(Constants.API_PERMISOS + "/{slug}")
    public SingleRolResponse getSingleRol(User me, @PathVariable(value = "slug") String codrol) {
        RolesVO rol = rolesService.getSingleRol(me, codrol);
        return new SingleRolResponse(rol);
    }

    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    @PutMapping(Constants.API_PERMISOS + "/{slug}")
    public SingleRolResponse updateRol(User me, @PathVariable(value = "slug") String codrol,
            @RequestBody RolesVO request) {
        RolesVO rol = rolesService.updateRol(me, codrol, request);
        return new SingleRolResponse(rol);
    }

    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    @DeleteMapping(Constants.API_PERMISOS + "/{slug}")
    public void deleteRol(User me, @PathVariable String codrol) {
        rolesService.deleteRol(me, codrol);
    }     */
}
