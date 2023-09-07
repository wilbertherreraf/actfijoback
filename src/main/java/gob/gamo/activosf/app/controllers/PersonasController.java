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
import gob.gamo.activosf.app.domain.OrgPersona;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.EmpleadoRepository;
import gob.gamo.activosf.app.repository.PersonaRepository;
import gob.gamo.activosf.app.services.EmpleadoService;
import gob.gamo.activosf.app.services.PersonaService;
import gob.gamo.activosf.app.utils.HeaderUtil;
import gob.gamo.activosf.app.utils.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = Constants.API_URL_ROOT + Constants.API_URL_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PersonasController {
 private final PersonaService service;

    private final PersonaRepository repository;
    private static final String ENTITY_NAME = "personas";

    @GetMapping(Constants.API_PERSONAS)
    public ResponseEntity<List<OrgPersona>> getAll(Pageable pageable) {
        final Page<OrgPersona> page = service.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, Constants.API_URL_ROOT + Constants.API_URL_VERSION + Constants.API_PERSONAS);
        // return
        // ResponseEntity.ok().headers(headers).body(RestResponse.of(page.getContent()));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @Operation(summary = "Crea un nuevo registro")
    @PostMapping(value = Constants.API_PERSONAS)
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<OrgPersona> create(@RequestBody OrgPersona req) {
        OrgPersona result = service.crearNuevo(req);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        ENTITY_NAME, result.getIdPersona().toString()))
                .body(result);
    }

    @GetMapping(Constants.API_PERSONAS + "/{slug}")
    public ResponseEntity<OrgPersona> getById(@PathVariable(value = "slug") Integer id) {
        OrgPersona result = repository.findById(id).orElseThrow(() -> new DataException("Registro inexistente"));
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
                .body(result);
    }

    @PutMapping(value = Constants.API_PERSONAS + "/{slug}")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<OrgPersona> updateRole(
            @PathVariable(value = "slug") String id, @RequestBody OrgPersona entityReq) {
        if (entityReq.getIdPersona() == null) {
            return create(entityReq);
        }
        OrgPersona result = service.update(entityReq);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        ENTITY_NAME, entityReq.getIdPersona().toString()))
                .body(result);
    }

    @DeleteMapping(Constants.API_PERSONAS + "/{slug}")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<Void> delete(@PathVariable(value = "slug") Integer id) {
        service.delete(id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }

/*     @GetMapping(Constants.API_PERSONAS + "/{slug}" + "/empleados")
    public ResponseEntity<List<OrgPersona>> unidadEmpleados(
            @PathVariable(value = "slug") Integer id, Pageable pageable) {
        log.info("Pageable {} {} -> {}", pageable.getPageSize(), pageable.getPageNumber(), pageable);

        OrgUnidad result = repository.findById(id).orElseThrow(() -> new DataException("Registro inexistente"));
        Set<OrgPersona> empl = result.getEmpleados();
        Page<OrgPersona> pageRet = PaginationUtil.pageForList(
                (int) pageable.getPageNumber(), pageable.getPageSize(), new ArrayList<>(empl));

        log.info("request uni {}", WebUtil.getRequest().getRequestURI());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                pageRet, WebUtil.getBaseURL() + WebUtil.getRequest().getRequestURI());
        // return
        // ResponseEntity.ok().headers(headers).body(RestResponse.of(pageRet.getContent()));
        return ResponseEntity.ok().headers(headers).body(pageRet.getContent());
    } */    
}
