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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.OrgEmpleado;
import gob.gamo.activosf.app.domain.OrgUnidad;
import gob.gamo.activosf.app.dto.UnidadResponse;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.OrgUnidadRepository;
import gob.gamo.activosf.app.services.UnidadService;
import gob.gamo.activosf.app.utils.HeaderUtil;
import gob.gamo.activosf.app.utils.PaginationUtil;
import gob.gamo.activosf.app.utils.WebUtil;
import io.swagger.v3.oas.annotations.Operation;

@Slf4j
@RestController
@RequestMapping(value = Constants.API_URL_ROOT + Constants.API_URL_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UnidadController {
    private final UnidadService service;
    private final OrgUnidadRepository repository;
    private static final String ENTITY_NAME = Constants.REC_UNIDS;

    @GetMapping(Constants.API_UNIDS)
    // @PostMapping(value = Constants.API_UNIDS)
    public ResponseEntity<List<UnidadResponse>> getAll(@RequestParam(value = "q", required = false) String search,Pageable pageable) {
        final Page<UnidadResponse> page = service.findAll(search, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, Constants.API_URL_ROOT + Constants.API_URL_VERSION + Constants.API_UNIDS);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @Operation(summary = "Crea un nuevo registro")
    @PostMapping(value = Constants.API_UNIDS)
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<UnidadResponse> create(@RequestBody UnidadResponse req) {
        UnidadResponse result = service.crearNuevo(OrgUnidad.createOrgUnidad(req));
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        ENTITY_NAME, result.id().toString()))
                .body(result);
    }

    @GetMapping(Constants.API_UNIDS + "/{slug}")
    public ResponseEntity<UnidadResponse> getById(@PathVariable(value = "slug") Integer id) {
        OrgUnidad result = repository.findById(id).orElseThrow(() -> new DataException("Registro inexistente"));

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
                .body(new UnidadResponse(result));
    }

    @PutMapping(value = Constants.API_UNIDS + "/{slug}")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<UnidadResponse> updateRole(
            @PathVariable(value = "slug") String id, @RequestBody UnidadResponse entityReq) {
        if (entityReq.id() == null) {
            return create(entityReq);
        }
        UnidadResponse result = service.update(entityReq);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        ENTITY_NAME, entityReq.id().toString()))
                .body(result);
    }

    @DeleteMapping(Constants.API_UNIDS + "/{slug}")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<Void> deletePost(@PathVariable(value = "slug") Integer id) {
        repository.deleteById(id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }

    @GetMapping(Constants.API_UNIDS + "/{slug}" + Constants.API_EMPLEADOS)
    public ResponseEntity<List<OrgEmpleado>> unidadEmpleados(
            @PathVariable(value = "slug") Integer id, Pageable pageable) {
        OrgUnidad result = repository.findById(id).orElseThrow(() -> new DataException("Registro inexistente"));
        Set<OrgEmpleado> empl = result.getEmpleados();
        Page<OrgEmpleado> pageRet = PaginationUtil.pageForList(
                (int) pageable.getPageNumber(), pageable.getPageSize(), new ArrayList<>(empl));

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                pageRet, WebUtil.getBaseURL() + WebUtil.getRequest().getRequestURI());
        return ResponseEntity.ok().headers(headers).body(pageRet.getContent());
    }
    //pitfalls y jpa interesting
    //https://github.com/eugene-khyst/spring-data-examples
    @GetMapping(Constants.API_UNIDS + "/{slug}" + "/dependientes")
    public ResponseEntity<List<UnidadResponse>> unidadesHijo(
            @PathVariable(value = "slug") Integer id) {

        OrgUnidad entity = repository.findById(id).orElseThrow(() -> new DataException("Registro inexistente"));

        List<OrgUnidad> hijos = service.getHijosN1(entity);
        List<UnidadResponse> result = hijos.stream().filter(x -> x.getIdUnidad().compareTo(entity.getIdUnidad()) != 0)
                .map(x -> new UnidadResponse(x)).toList();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(Constants.API_UNIDS + "/{slug}" + "/padres")
    public ResponseEntity<List<UnidadResponse>> unidadesPadreEleg(
            @PathVariable(value = "slug") Integer id) {

        OrgUnidad entity = repository.findById(id).orElseThrow(() -> new DataException("Registro inexistente"));
        List<OrgUnidad> hijos = service.getHijos(entity);
        List<OrgUnidad> padres = repository.findAll().stream().filter(x -> x.getIdUnidad().compareTo(id) != 0)//
                .filter(x -> !hijos.stream().filter(y -> y.getIdUnidad().compareTo(x.getIdUnidad()) == 0).findFirst()
                        .isPresent()).collect(Collectors.toList());

        List<UnidadResponse> result = padres.stream().filter(x -> x.getIdUnidad().compareTo(entity.getIdUnidad()) != 0)
                .map(x -> new UnidadResponse(x)).toList();
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(Constants.API_UNIDS + "/{slug}" + "/dependientes/{id}")
    public ResponseEntity<UnidadResponse> unidadesHijoActPadre(
            @PathVariable(value = "slug") Integer id, @PathVariable(value = "id") Integer idNewPadre) {

        service.updatePadre(id, idNewPadre);
        OrgUnidad entity = repository.findById(id).orElseThrow(() -> new DataException("Registro inexistente"));

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
                .body(new UnidadResponse(entity));
    }
}
