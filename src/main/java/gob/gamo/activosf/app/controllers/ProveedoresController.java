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
import gob.gamo.activosf.app.domain.AfProveedor;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.UserRequestVo;
import gob.gamo.activosf.app.domain.AfProveedor;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.AfProveedorRepository;
import gob.gamo.activosf.app.repository.EmpleadoRepository;
import gob.gamo.activosf.app.services.AfProveedorBl;
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
public class ProveedoresController {
  private final AfProveedorBl service;

    private final AfProveedorRepository repository;
    private static final String ENTITY_NAME = "proveedores";

    @GetMapping(Constants.API_PROVEEDORES)
    public ResponseEntity<List<AfProveedor>> getAll(Pageable pageable) {
        final Page<AfProveedor> page = service.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, Constants.API_URL_ROOT + Constants.API_URL_VERSION + Constants.API_PROVEEDORES);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @Operation(summary = "Crea un nuevo registro")
    @PostMapping(value = Constants.API_PROVEEDORES)
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<AfProveedor> create(User me, @RequestBody AfProveedor req) {
        AfProveedor result = service.mergeAfProveedor(req, UserRequestVo.convertUser(me) );
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        ENTITY_NAME, result.getIdProveedor().toString()))
                .body(result);
    }

    @GetMapping(Constants.API_PROVEEDORES + "/{slug}")
    public ResponseEntity<AfProveedor> getById(@PathVariable(value = "slug") Integer id) {
        AfProveedor result = repository.findById(id).orElseThrow(() -> new DataException("Registro inexistente"));
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
                .body(result);
    }

    @PutMapping(value = Constants.API_PROVEEDORES + "/{slug}")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<AfProveedor> updateRole(User me,
            @PathVariable(value = "slug") String id, @RequestBody AfProveedor entityReq) {
        if (entityReq.getIdProveedor() == null) {
            return create(me ,entityReq);
        }
        AfProveedor result = service.mergeAfProveedor(entityReq, UserRequestVo.convertUser(me));
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        ENTITY_NAME, entityReq.getIdProveedor().toString()))
                .body(result);
    }

    @DeleteMapping(Constants.API_PROVEEDORES + "/{slug}")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<Void> deletePost(User me, @PathVariable(value = "slug") Integer id) {
        service.delete(id, UserRequestVo.convertUser(me));
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }

/*     @GetMapping(Constants.API_PROVEEDORES + "/{slug}" + "/empleados")
    public ResponseEntity<List<AfProveedor>> unidadEmpleados(
            @PathVariable(value = "slug") Integer id, Pageable pageable) {
        log.info("Pageable {} {} -> {}", pageable.getPageSize(), pageable.getPageNumber(), pageable);

        OrgUnidad result = repository.findById(id).orElseThrow(() -> new DataException("Registro inexistente"));
        Set<AfProveedor> empl = result.getEmpleados();
        Page<AfProveedor> pageRet = PaginationUtil.pageForList(
                (int) pageable.getPageNumber(), pageable.getPageSize(), new ArrayList<>(empl));

        log.info("request uni {}", WebUtil.getRequest().getRequestURI());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                pageRet, WebUtil.getBaseURL() + WebUtil.getRequest().getRequestURI());
        // return
        // ResponseEntity.ok().headers(headers).body(RestResponse.of(pageRet.getContent()));
        return ResponseEntity.ok().headers(headers).body(pageRet.getContent());
    } */   
}
