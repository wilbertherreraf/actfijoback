package gob.gamo.activosf.app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.OrgEmpleado;
import gob.gamo.activosf.app.domain.OrgUnidad;
import gob.gamo.activosf.app.domain.entities.Recurso;
import gob.gamo.activosf.app.domain.entities.Roles;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.UnidadResponse;
import gob.gamo.activosf.app.dto.sec.RolesVO;
import gob.gamo.activosf.app.dto.sec.SingleRolResponse;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.sec.RolesRepository;
import gob.gamo.activosf.app.services.sec.RolesService;
import gob.gamo.activosf.app.utils.PaginationUtil;
import gob.gamo.activosf.app.utils.WebUtil;

// @Api("Role Controller")
@Slf4j
@RestController
@RequestMapping(value = Constants.API_URL_ROOT + Constants.API_URL_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
// @RequiresPermissions("sys:manage:role")
@RequiredArgsConstructor
public class RolesController {
    private final RolesService rolesService;
    private final RolesRepository repository;
    private static final String ENTITY_NAME = Constants.REC_ROLES;

    @GetMapping(Constants.API_ROLES)
    public ResponseEntity<List<RolesVO>> getRolePresentationList(Pageable pageable) {
        Page<Roles> page = rolesService.getRoles(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, Constants.API_URL_ROOT + Constants.API_URL_VERSION + Constants.API_ROLES);
        return ResponseEntity.ok().headers(headers).body(page.getContent().stream().map(r -> new RolesVO(r)).toList());
    }

    @PostMapping(Constants.API_ROLES)
    // @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public SingleRolResponse createRol(User me, @RequestBody RolesVO request) {
        log.info("en create rol {}", request.toString());
        RolesVO rol = rolesService.createRol(me, request);
        return new SingleRolResponse(rol);
    }

    @GetMapping(Constants.API_ROLES + "/{slug}")
    public SingleRolResponse getSingleRol(@PathVariable(value = "slug") String codrol) {
        RolesVO rol = rolesService.getSingleRol(codrol);
        SingleRolResponse srol = new SingleRolResponse(rol);
        Gson gson = new Gson();
        String json = gson.toJson(srol);
        log.info("json {}", json);
        return srol;
    }

    // @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    @PutMapping(Constants.API_ROLES + "/{slug}")
    public SingleRolResponse updateRol(@PathVariable(value = "slug") String codrol,
            @RequestBody RolesVO request) {
        log.info("En updates rol {} {}", codrol, request);
        RolesVO rol = rolesService.updateRol(null, codrol, request);
        return new SingleRolResponse(rol);
    }

    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    @DeleteMapping(Constants.API_ROLES + "/{slug}")
    public void deleteRol(User me, @PathVariable String codrol) {
        rolesService.deleteRol(me, codrol);
    }

    @GetMapping(Constants.API_ROLES + "/{slug}" + Constants.API_PERMISOS)
    public ResponseEntity<List<Recurso>> rolesPermisos(
            @PathVariable(value = "slug") String id, Pageable pageable) {
        log.info("Pageable {} {} -> {}", pageable.getPageSize(), pageable.getPageNumber(), pageable);

        Roles result = repository.findByCodrol(id).orElseThrow(() -> new DataException("Registro inexistente"));
        Set<Recurso> empl = result.getIncludeRecursos().stream().map(x -> x.getRecurso()).collect(Collectors.toSet());
        Page<Recurso> pageRet = PaginationUtil.pageForList(
                (int) pageable.getPageNumber(), pageable.getPageSize(), new ArrayList<>(empl));

        log.info("request uni {}", WebUtil.getRequest().getRequestURI());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                pageRet, WebUtil.getBaseURL() + WebUtil.getRequest().getRequestURI());
        // return
        // ResponseEntity.ok().headers(headers).body(RestResponse.of(pageRet.getContent()));
        return ResponseEntity.ok().headers(headers).body(pageRet.getContent());
    }

    // @PostMapping(Constants.API_ROLES + "/{slug}" + Constants.API_PERMISOS)
    public ResponseEntity<List<Recurso>> updatePermisos(
            @PathVariable(value = "slug") String id, @RequestBody RolesVO request, Pageable pageable) {
        log.info("Pageable {} {} -> {}", pageable.getPageSize(), pageable.getPageNumber(), pageable);
        log.info("roles {}", request);

        Roles result = repository.findByCodrol(id).orElseThrow(() -> new DataException("Registro inexistente"));
        Set<Recurso> empl = result.getIncludeRecursos().stream().map(x -> x.getRecurso()).collect(Collectors.toSet());
        Page<Recurso> pageRet = PaginationUtil.pageForList(
                (int) pageable.getPageNumber(), pageable.getPageSize(), new ArrayList<>(empl));

        log.info("request uni {}", WebUtil.getRequest().getRequestURI());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                pageRet, WebUtil.getBaseURL() + WebUtil.getRequest().getRequestURI());
        // return
        // ResponseEntity.ok().headers(headers).body(RestResponse.of(pageRet.getContent()));
        return ResponseEntity.ok().headers(headers).body(pageRet.getContent());
    }
}
