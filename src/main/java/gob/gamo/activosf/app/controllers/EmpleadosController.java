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
import gob.gamo.activosf.app.domain.OrgEmpleado;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.EmpleadoRepository;
import gob.gamo.activosf.app.services.EmpleadoService;
import gob.gamo.activosf.app.utils.HeaderUtil;
import gob.gamo.activosf.app.utils.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = Constants.API_URL_ROOT + Constants.API_URL_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EmpleadosController {
    private final EmpleadoService service;

    private final EmpleadoRepository repository;
    private static final String ENTITY_NAME = "empleados";

    @GetMapping(Constants.API_EMPLEADOS)
    public ResponseEntity<List<OrgEmpleado>> getAll(Pageable pageable) {
        final Page<OrgEmpleado> page = service.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, Constants.API_URL_ROOT + Constants.API_URL_VERSION + Constants.API_EMPLEADOS);
        // return
        // ResponseEntity.ok().headers(headers).body(RestResponse.of(page.getContent()));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @Operation(summary = "Crea un nuevo registro")
    @PostMapping(value = Constants.API_EMPLEADOS)
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<OrgEmpleado> create(@RequestBody OrgEmpleado req) {
        OrgEmpleado result = service.crearNuevo(req);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @GetMapping(Constants.API_EMPLEADOS + "/{slug}")
    public ResponseEntity<OrgEmpleado> getById(@PathVariable(value = "slug") Integer id) {
        OrgEmpleado result = repository.findById(id).orElseThrow(() -> new DataException("Registro inexistente"));
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
                .body(result);
    }

    @PutMapping(value = Constants.API_EMPLEADOS + "/{slug}")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<OrgEmpleado> updateRole(
            @PathVariable(value = "slug") String id, @RequestBody OrgEmpleado entityReq) {
        if (entityReq.getId() == null) {
            return create(entityReq);
        }
        OrgEmpleado result = service.update(entityReq);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        ENTITY_NAME, entityReq.getId().toString()))
                .body(result);
    }

    @DeleteMapping(Constants.API_EMPLEADOS + "/{slug}")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<Void> deletePost(@PathVariable(value = "slug") Integer id) {
        service.delete(id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }

/*     @GetMapping(Constants.API_EMPLEADOS + "/{slug}" + "/empleados")
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
