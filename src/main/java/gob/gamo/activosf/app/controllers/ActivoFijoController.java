package gob.gamo.activosf.app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import gob.gamo.activosf.app.domain.AfActivoFijo;
import gob.gamo.activosf.app.domain.OrgEmpleado;
import gob.gamo.activosf.app.domain.OrgUnidad;
import gob.gamo.activosf.app.domain.entities.User;
//import gob.gamo.activosf.app.dto.AfActivoFijo;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.dto.sec.UserVO;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.AfActivoFijoRepository;
import gob.gamo.activosf.app.repository.OrgUnidadRepository;
import gob.gamo.activosf.app.services.AfActivoFijoBl;
import gob.gamo.activosf.app.services.UnidadService;
import gob.gamo.activosf.app.utils.HeaderUtil;
import gob.gamo.activosf.app.utils.PaginationUtil;
import gob.gamo.activosf.app.utils.WebUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/* @Slf4j
@RestController
@RequestMapping(value = Constants.API_URL_ROOT + Constants.API_URL_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor */
public class ActivoFijoController {
    /* private final AfActivoFijoBl service;

    private final AfActivoFijoRepository repository;
    private static final String ENTITY_NAME = "activosf";

    @GetMapping(Constants.API_ACTIVOSF)
    public ResponseEntity<List<AfActivoFijo>> getAll(Pageable pageable) {
        final Page<AfActivoFijo> page = service.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, Constants.API_URL_ROOT + Constants.API_URL_VERSION + Constants.API_ACTIVOSF);
        // return
        // ResponseEntity.ok().headers(headers).body(RestResponse.of(page.getContent()));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    } */

    /* @Operation(summary = "Crea un nuevo registro")
    @PostMapping(value = Constants.API_ACTIVOSF)
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<AfActivoFijo> create(User me, @RequestBody AfActivoFijo req) {
        service.mergeAfActivoFijo(req, UserRequestVo.convertUser(me));
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        ENTITY_NAME, req.getIdActivoFijo().toString()))
                .body(respon);
    }
 */
/*     @GetMapping(Constants.API_ACTIVOSF + "/{slug}")
    public ResponseEntity<AfActivoFijo> getById(@PathVariable(value = "slug") Integer id) {
        OrgUnidad result = repository.findById(id).orElseThrow(() -> new DataException("Registro inexistente"));
        result.getEmpleados().forEach(r -> {
            log.info("unida {} - {}", id, r.getCod_internoempl());
        });
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
                .body(new AfActivoFijo(result));
    }

    @PutMapping(value = Constants.API_ACTIVOSF + "/{slug}")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<AfActivoFijo> updateRole(
            @PathVariable(value = "slug") String id, @RequestBody AfActivoFijo entityReq) {
        if (entityReq.id() == null) {
            return create(entityReq);
        }
        AfActivoFijo result = service.update(entityReq);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        ENTITY_NAME, entityReq.id().toString()))
                .body(result);
    }

    @DeleteMapping(Constants.API_ACTIVOSF + "/{slug}")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<Void> deletePost(@PathVariable(value = "slug") Integer id) {
        service.delete(id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }

    @GetMapping(Constants.API_ACTIVOSF + "/{slug}" + "/empleados")
    public ResponseEntity<List<OrgEmpleado>> unidadEmpleados(
            @PathVariable(value = "slug") Integer id, Pageable pageable) {
        log.info("Pageable {} {} -> {}", pageable.getPageSize(), pageable.getPageNumber(), pageable);

        OrgUnidad result = repository.findById(id).orElseThrow(() -> new DataException("Registro inexistente"));
        Set<OrgEmpleado> empl = result.getEmpleados();
        Page<OrgEmpleado> pageRet = PaginationUtil.pageForList(
                (int) pageable.getPageNumber(), pageable.getPageSize(), new ArrayList<>(empl));

        log.info("request uni {}", WebUtil.getRequest().getRequestURI());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                pageRet, WebUtil.getBaseURL() + WebUtil.getRequest().getRequestURI());
        // return
        // ResponseEntity.ok().headers(headers).body(RestResponse.of(pageRet.getContent()));
        return ResponseEntity.ok().headers(headers).body(pageRet.getContent());
    } */

}
