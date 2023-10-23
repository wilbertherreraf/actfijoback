package gob.gamo.activosf.app.controllers;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.OrgEmpleado;
import gob.gamo.activosf.app.domain.OrgPersona;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.EmpleadoVo;
import gob.gamo.activosf.app.dto.PersonaVO;
import gob.gamo.activosf.app.dto.sec.UserVO;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.EmpleadoRepository;
import gob.gamo.activosf.app.repository.PersonaRepository;
import gob.gamo.activosf.app.repository.sec.UserRepository;
import gob.gamo.activosf.app.search.SearchCriteria;
import gob.gamo.activosf.app.services.AfSearchService;
import gob.gamo.activosf.app.services.EmpleadoService;
import gob.gamo.activosf.app.services.PersonaService;
import gob.gamo.activosf.app.utils.HeaderUtil;
import gob.gamo.activosf.app.utils.PaginationUtil;
import gob.gamo.activosf.app.utils.WebUtil;
import io.swagger.v3.oas.annotations.Operation;

@Slf4j
@RestController
@RequestMapping(value = Constants.API_URL_ROOT + Constants.API_URL_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PersonasController {
    private final PersonaService service;
    private final PersonaRepository repository;
    private final UserRepository userRepository;
    private final EmpleadoService empleadoService;
    private final EmpleadoRepository empleadoRepository;
    private final AfSearchService searchService;
    private static final String ENTITY_NAME = Constants.REC_PERSONAS;

    @PostMapping(Constants.API_PERSONAS + "/f")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<List<OrgPersona>> getAllByFilter(
            @RequestBody(required = false) SearchCriteria sc, Pageable pageable) {
        Page<OrgPersona> page = searchService.searchPersona(sc, pageable).map(x -> {
            userRepository.findByIdPersona(x.getIdPersona()).ifPresent(u -> {
                x.setUser(new UserVO(u));
            });
            empleadoService.empleadoActivo(x.getIdPersona()).ifPresent(e -> {
                PersonaVO p = new PersonaVO(x);
                EmpleadoVo emp = new EmpleadoVo(e, p);
                x.setEmpleado(emp);
            });
            return x;
        });

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, Constants.API_URL_ROOT + Constants.API_URL_VERSION + Constants.API_PERSONAS);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @Operation(summary = "Crea un nuevo registro")
    @PostMapping(value = Constants.API_PERSONAS)
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<OrgPersona> create(@RequestBody PersonaVO req) {
        OrgPersona result = service.crearNuevo(req);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        ENTITY_NAME, result.getIdPersona().toString()))
                .body(result);
    }

    @GetMapping(Constants.API_PERSONAS + "/{slug}")
    @PreAuthorize("(#idpersona == #me.idUnidEmpl + '')  or hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<OrgPersona> getByCodPersona(User me, @PathVariable(value = "slug") String idpersona) {

        log.info("en get personas::> {} {}", idpersona, me.getIdUnidEmpl());
        Integer id = Integer.valueOf(idpersona);
        OrgPersona result = repository.findById(id).orElseThrow(() -> new DataException("Persona inexistente " + id));

        userRepository.findByIdPersona(result.getIdPersona()).ifPresent(u -> {
            result.setUser(new UserVO(u));
        });
        empleadoService.empleadoActivo(result.getIdPersona()).ifPresent(e -> {
            PersonaVO p = new PersonaVO(result);
            EmpleadoVo emp = new EmpleadoVo(e, p);
            result.setEmpleado(emp);
        });

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
                .body(result);
    }

    @PutMapping(value = Constants.API_PERSONAS + "/{slug}")
    // @PreAuthorize("#username == authentication.name or hasAuthority('" +
    // ENTITY_NAME + "')")
    @PreAuthorize("(#idpersona == #me.idUnidEmpl + '')  or hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<OrgPersona> updatePers(
            User me, @PathVariable(value = "slug") String idpersona, @RequestBody PersonaVO entityReq) {

        OrgPersona result = service.update(entityReq);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        ENTITY_NAME, entityReq.idPersona().toString()))
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

    @GetMapping(Constants.API_PERSONAS + "/search")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<List<OrgPersona>> search(
            @RequestParam(value = "q", required = false) String search, Pageable pageable) {
        Page<OrgPersona> page = service.search(search, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, WebUtil.getBaseURL() + WebUtil.getRequest().getRequestURI());
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping(Constants.API_PERSONAS + "/{slug}" + "/jobs")
    public ResponseEntity<List<EmpleadoVo>> personaJobs(@PathVariable(value = "slug") Integer id, Pageable pageable) {
        OrgPersona result = service.findById(id);
        // Set<OrgEmpleado> empl = result.getEmpleados();
        List<OrgEmpleado> empl = empleadoRepository.findAllByIdPersona(id);

        Page<OrgEmpleado> pageRet = PaginationUtil.pageForList(
                (int) pageable.getPageNumber(), pageable.getPageSize(), new ArrayList<>(empl));

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                pageRet, WebUtil.getBaseURL() + WebUtil.getRequest().getRequestURI());
        return ResponseEntity.ok()
                .headers(headers)
                .body(pageRet.getContent().stream().map(e -> new EmpleadoVo(e)).toList());
    }

    @GetMapping(Constants.API_PERSONAS + "/{slug}" + "/job")
    public ResponseEntity<EmpleadoVo> personaEmpleadoAct(@PathVariable(value = "slug") Integer id) {
        OrgPersona result = service.findById(id);
        OrgEmpleado emp = empleadoService.empleadoActivo(id).orElseGet(() -> OrgEmpleado.builder()
                .build());
        /*
         * log.info("empleado fecha... {} [{}]",
         * UtilsDate.stringFromDate(emp.getFechaIngreso(), "dd/MM/yyyy"),
         * emp.getFechaIngreso());
         */

        EmpleadoVo entityReq = new EmpleadoVo(emp);
        /*
         * ObjectMapper mapper = new ObjectMapper();
         * String st;
         * try {
         * Gson gson = new Gson();
         * st = mapper.writeValueAsString(Map.of("empleado", entityReq));
         * String gs = gson.toJson(entityReq);
         * log.info("eeeeeeeeeeeee map {} {} {}", st);
         * log.info("gggggggg map {}", gs);
         * log.info("eeeeeeeeeeeee map {} {}", entityReq.fechaIngreso(),
         * UtilsDate.stringFromDate(entityReq.fechaIngreso(), "dd/MM/yyyy"));
         * // Usuario1 usuario1 = gson.fromJson(json, Usuario1.class);
         *
         * } catch (JsonProcessingException e) {
         * // TODO Auto-generated catch block
         * log.error("errrrr " + e.getMessage());
         * }
         */
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
                .body(entityReq);
    }

    @GetMapping(Constants.API_PERSONAS)
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<List<OrgPersona>> getAll(
            @RequestParam(value = "q", required = false) String search, Pageable pageable) {
        final Page<OrgPersona> page = service.findAll(search, pageable).map(x -> {
            userRepository.findByIdPersona(x.getIdPersona()).ifPresent(u -> {
                x.setUser(new UserVO(u));
            });
            empleadoService.empleadoActivo(x.getIdPersona()).ifPresent(e -> {
                PersonaVO p = new PersonaVO(x);
                EmpleadoVo emp = new EmpleadoVo(e, p);
                x.setEmpleado(emp);
            });
            return x;
        });

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, Constants.API_URL_ROOT + Constants.API_URL_VERSION + Constants.API_PERSONAS);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class EmpleadoVO1 {
        private EmpleadoVo empleado;
    }
}
