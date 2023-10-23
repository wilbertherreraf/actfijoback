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

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.OrgEmpleado;
import gob.gamo.activosf.app.domain.OrgPersona;
import gob.gamo.activosf.app.dto.EmpleadoVo;
import gob.gamo.activosf.app.dto.PersonaVO;
import gob.gamo.activosf.app.dto.sec.UserVO;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.EmpleadoRepository;
import gob.gamo.activosf.app.repository.sec.UserRepository;
import gob.gamo.activosf.app.search.SearchCriteria;
import gob.gamo.activosf.app.services.AfSearchService;
import gob.gamo.activosf.app.services.EmpleadoService;
import gob.gamo.activosf.app.utils.HeaderUtil;
import gob.gamo.activosf.app.utils.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;

@Slf4j
@RestController
@RequestMapping(value = Constants.API_URL_ROOT + Constants.API_URL_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EmpleadosController {
    private static final String ENTITY_NAME = Constants.REC_EMPLEADOS;

    private final EmpleadoService service;
    private final EmpleadoRepository repository;
    private final AfSearchService searchService;
    private final UserRepository userRepository;

    @PostMapping(Constants.API_EMPLEADOS + "/f")
    public ResponseEntity<List<EmpleadoVo>> getAllSearch(
            @RequestBody(required = false) SearchCriteria sc, Pageable pageable) {
        Page<OrgEmpleado> page = searchService.searchEmpleados(sc, pageable).map(x -> {
            OrgPersona p = x.getPersona();
            userRepository.findByIdPersona(x.getIdPersona()).ifPresent(u -> {
                //                log.info("user encontrado empl {} person {} user {}", x.getId(), p.getIdPersona(),
                // u.getId());
                p.setUser(new UserVO(u));
            });
            return x;
        });

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, Constants.API_URL_ROOT + Constants.API_URL_VERSION + Constants.API_EMPLEADOS);
        return ResponseEntity.ok()
                .headers(headers)
                .body(page.getContent().stream()
                        .map(e -> {
                            EmpleadoVo em = null;
                            if (e.getPersona() != null) {
                                PersonaVO p = new PersonaVO(
                                        e.getPersona(), e.getPersona().getUser());
                                em = new EmpleadoVo(e, p);
                            } else {
                                em = new EmpleadoVo(e);
                            }
                            return em;
                        })
                        .toList());
    }

    @Operation(summary = "Crea un nuevo registro")
    @PostMapping(value = Constants.API_EMPLEADOS)
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<EmpleadoVo> create(@RequestBody EmpleadoVo req) {
        OrgEmpleado result = service.crearNuevo(req.empleado());
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        ENTITY_NAME, result.getId().toString()))
                .body(new EmpleadoVo(result));
    }

    @GetMapping(Constants.API_EMPLEADOS + "/{slug}")
    public ResponseEntity<EmpleadoVo> getById(@PathVariable(value = "slug") Integer id) {
        OrgEmpleado result = repository.findById(id).orElseThrow(() -> new DataException("Registro inexistente"));
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
                .body(new EmpleadoVo(result));
    }

    @PutMapping(value = Constants.API_EMPLEADOS + "/{slug}")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<EmpleadoVo> update(
            @PathVariable(value = "slug") String id, @RequestBody EmpleadoVo entityReq) {
        ObjectMapper mapper = new ObjectMapper();
        OrgEmpleado result = service.update(entityReq.empleado());
        return ResponseEntity.ok().body(new EmpleadoVo(result));
    }

    @DeleteMapping(Constants.API_EMPLEADOS + "/{slug}")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<Void> deleteEntity(@PathVariable(value = "slug") Integer id) {

        service.eliminarEmpleado(id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }

    @PutMapping(value = Constants.API_EMPLEADOS + "/{slug}/rmv")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<Void> inactivate(
            @PathVariable(value = "slug") Integer id, @RequestBody EmpleadoVo entityReq) {
        service.bajaEmpleado(id, entityReq.fechaBaja(), true);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }

    @GetMapping(Constants.API_EMPLEADOS)
    public ResponseEntity<List<EmpleadoVo>> getAll(Pageable pageable) {
        final Page<OrgEmpleado> page = service.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, Constants.API_URL_ROOT + Constants.API_URL_VERSION + Constants.API_EMPLEADOS);
        return ResponseEntity.ok()
                .headers(headers)
                .body(page.getContent().stream().map(e -> new EmpleadoVo(e)).toList());
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class EmpleadoVO1 {
        private EmpleadoVo empleado;
    }
}
