package gob.gamo.activosf.app.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import gob.gamo.activosf.app.domain.OrgUnidad;
import gob.gamo.activosf.app.dto.UnidadResponse;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.OrgUnidadRepository;
import gob.gamo.activosf.app.services.UnidadService;
import gob.gamo.activosf.app.utils.HeaderUtil;
import gob.gamo.activosf.app.utils.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;

@Slf4j
@RestController
@RequestMapping(Constants.API_URL_ROOT + Constants.API_URL_VERSION)
// @RequiresPermissions("sys:manage:role")

@RequiredArgsConstructor
public class UnidadController {
    private final UnidadService service;
    private final OrgUnidadRepository repository;
    private static final String ENTITY_NAME = "Unidad";

    @GetMapping(Constants.API_URL_CLASS_UNIDADES)
    public ResponseEntity<List<UnidadResponse>> getAll(Pageable pageable) {
        log.info("rest unidad list {} query {}", this.getClass().getSimpleName(),
                pageable != null ? pageable.toString() : "");

        Page<UnidadResponse> list = service.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(list,
                Constants.API_URL_ROOT + Constants.API_URL_VERSION + "/unidades");
        // return ResponseEntity.ok(list);
        return new ResponseEntity<>(list.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Crea un nuevo registro")
    /*
     * @ApiResponses({
     *
     * @ApiResponse(responseCode = "201", content = {
     *
     * @Content(schema = @Schema(implementation = UnidadResponse.class), mediaType =
     * "application/json")
     * }),
     *
     * @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema())
     * })
     * })
     */
    @PostMapping(value = Constants.API_URL_CLASS_UNIDADES)
    public ResponseEntity<UnidadResponse> create(@RequestBody UnidadResponse req) {
        log.info("ingresando a Controller nuevo {}", req.toString());
        // try {
        UnidadResponse result = service.crearNuevo(OrgUnidad.createOrgUnidad(req));
        // return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        ENTITY_NAME, result.id().toString()))
                .body(result);
        /*
         * } catch (Exception e) {
         * log.error("Error " + e.getMessage(), e);
         * return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
         * }
         */
    }

    @GetMapping(Constants.API_URL_CLASS_UNIDADES + "/{id}")
    public ResponseEntity<UnidadResponse> getById(@PathVariable Integer id) {
        try {
            log.info("en {} query {}", this.getClass().getSimpleName());
            OrgUnidad result = repository.findById(id).orElseThrow(() -> new DataException("Registro inexistente"));
            return ResponseEntity.ok(new UnidadResponse(result));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = Constants.API_URL_CLASS_UNIDADES + "/{id}")
    // @ResponseBody
    public ResponseEntity<UnidadResponse> updateRole(@PathVariable String id, @RequestBody UnidadResponse entityReq) {
        log.info("REST request to update Post {}: {}", id, entityReq);
        if (entityReq.id() == null) {
            return create(entityReq);
        }
        UnidadResponse result = service.update(entityReq);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(
                        ENTITY_NAME, entityReq.id().toString()))
                .body(result);
    }

    @DeleteMapping(Constants.API_URL_CLASS_UNIDADES + "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        log.debug("REST request to delete : {}", id);
        repository.deleteById(id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }

    /*
     * public static SearchResponse getSearchResultFromException(Throwable e) {
     * log.info("Generando objeto respuesta por exception " + e.getMessage());
     * StringBuilder sb = new StringBuilder();
     * int count = 0;
     * for (Throwable cause = e; (cause != null && cause != cause.getCause()); cause
     * = cause.getCause()) {
     * if (cause instanceof SQLException) {
     * count++;
     * sb.append(cause.getMessage());
     * sb.append(" , ");
     * }
     * }
     * String consent = (count > 0 ? sb.toString().replace(", null ,", "") :
     * e.getMessage().replace(", null ,", ""));
     *
     * SearchResponse searchResponse = createResponse(Constants.COD_RESP_ERR_02, 1,
     * null, null);
     * searchResponse.setDescripResp(consent);
     *
     * log.info("Respuesta con Error " + searchResponse.getCodResp() + ": " +
     * searchResponse.getDescripResp());
     * return searchResponse;
     * }
     */

}
