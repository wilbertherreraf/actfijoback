package gob.gamo.activosf.app.controllers;

// import gob.gamo.activosf.app.dto.AfActivoFijo;

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
