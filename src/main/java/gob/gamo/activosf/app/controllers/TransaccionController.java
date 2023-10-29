package gob.gamo.activosf.app.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.domain.TxTransdet;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.TransaccionVo;
import gob.gamo.activosf.app.dto.TransdetVo;
import gob.gamo.activosf.app.errors.DataException;
import gob.gamo.activosf.app.repository.TxTransaccionRepository;
import gob.gamo.activosf.app.search.SearchCriteria;
import gob.gamo.activosf.app.services.AfSearchService;
import gob.gamo.activosf.app.services.TxTransaccionService;
import gob.gamo.activosf.app.services.TxTransdetService;
import gob.gamo.activosf.app.utils.HeaderUtil;
import gob.gamo.activosf.app.utils.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = Constants.API_URL_ROOT + Constants.API_URL_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
public class TransaccionController {
    private final TxTransaccionService service;
    private final TxTransaccionRepository repository;
    private final AfSearchService searchService;
    private final TxTransdetService transdetService;
    private static final String ENTITY_NAME = Constants.REC_TRANSACCION;

    @PostMapping(Constants.API_TRANSACCION + "/f")
    public ResponseEntity<List<TxTransaccion>> getAllSearch(
            @RequestBody(required = false) SearchCriteria sc, Pageable pageable) {
        searchService.searchTransaf(sc, pageable);
        Page<TxTransaccion> page = searchService.searchTransaf(sc, pageable);
        ;
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, Constants.API_URL_ROOT + Constants.API_URL_VERSION + Constants.API_TRANSACCION);
        return ResponseEntity.ok()
                .headers(headers)
                .body(page.getContent());

    }

    @Operation(summary = "Crea un nuevo registro")
    @PostMapping(value = Constants.API_TRANSACCION)
    // @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<TxTransaccion> create(@RequestBody TransaccionVo req) {
        TxTransaccion result = service.registro(req.getTransaccion());

        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping(Constants.API_TRANSACCION + "/{slug}")
    public ResponseEntity<TxTransaccion> getById(@PathVariable(value = "slug") Integer id) {
        TxTransaccion result = service.findById(id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
                .body(result);
    }

    @PostMapping(value = Constants.API_TRANSACCION + "/init")
    // @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<TxTransaccion> iniciarTransaccion(User me, @RequestBody TxTransaccion req) {
        TxTransaccion result = service.generarOperacion(me, req);

        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping(value = Constants.API_TRANSACCION + "/{slug}" + Constants.API_TRANSDET)
    // @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<List<TransdetVo>> allTransdet(@PathVariable(value = "slug") Integer id) {
        TxTransaccion trx = service.findById(id);
        List<TransdetVo> result = transdetService.getAll(trx).stream().map(x -> new TransdetVo(x)).toList();

        return ResponseEntity.ok()
                .body(result);
    }

    @PostMapping(value = Constants.API_TRANSACCION + "/{slug}" + Constants.API_TRANSDET)
    // @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<TransdetVo> createTransdet(@PathVariable(value = "slug") Integer id,
            @RequestBody TransdetVo req) {
        TxTransaccion trx = service.findById(id);
        TxTransdet result = transdetService.create(trx, req.transdet());

        return ResponseEntity.ok()
                .body(new TransdetVo(result));
    }
}
