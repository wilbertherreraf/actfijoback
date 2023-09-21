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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.AfCodigoContable;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.AfCodigoContableRepository;
import gob.gamo.activosf.app.services.AfCodigoContableBl;
import gob.gamo.activosf.app.utils.HeaderUtil;
import gob.gamo.activosf.app.utils.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;

@Slf4j
@RestController
@RequestMapping(value = Constants.API_URL_ROOT + Constants.API_URL_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CodigoContablesController {
    private final AfCodigoContableBl service;

    private final AfCodigoContableRepository repository;
    private static final String ENTITY_NAME = Constants.REC_CODIGOSCONTABLES;

    @GetMapping(Constants.API_CODIGOSCONTABLES)
    public ResponseEntity<List<AfCodigoContable>> getAll(Pageable pageable) {
        final Page<AfCodigoContable> page = service.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, Constants.API_URL_ROOT + Constants.API_URL_VERSION + Constants.API_CODIGOSCONTABLES);
        // return
        // ResponseEntity.ok().headers(headers).body(RestResponse.of(page.getContent()));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @Operation(summary = "Crea un nuevo registro")
    @PostMapping(value = Constants.API_CODIGOSCONTABLES)
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<AfCodigoContable> create(User me, @RequestBody AfCodigoContable req) {
        AfCodigoContable result = service.mergeAfCodigoContable(req, UserRequestVo.convertUser(me));
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        ENTITY_NAME, result.getIdCodigoContable().toString()))
                .body(result);
    }

    @GetMapping(Constants.API_CODIGOSCONTABLES + "/{slug}")
    public ResponseEntity<AfCodigoContable> getById(@PathVariable(value = "slug") Integer id) {
        AfCodigoContable result = repository.findById(id).orElseThrow(() -> new DataException("Registro inexistente"));
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
                .body(result);
    }

    @PutMapping(value = Constants.API_CODIGOSCONTABLES + "/{slug}")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<AfCodigoContable> updateRole(
            User me, @PathVariable(value = "slug") String id, @RequestBody AfCodigoContable entityReq) {

        if (entityReq.getIdCodigoContable() == null) {
            return create(me, entityReq);
        }
        AfCodigoContable result = service.mergeAfCodigoContable(entityReq, UserRequestVo.convertUser(me));
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        ENTITY_NAME, entityReq.getIdCodigoContable().toString()))
                .body(result);
    }

    @DeleteMapping(Constants.API_CODIGOSCONTABLES + "/{slug}")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<Void> deletePost(User me, @PathVariable(value = "slug") Integer id) {
        service.delete(id, UserRequestVo.convertUser(me));
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }

    /*     @GetMapping(Constants.API_CODIGOSCONTABLES + "/{slug}" + "/empleados")
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
