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
import gob.gamo.activosf.app.domain.TxTransaccion;
import gob.gamo.activosf.app.domain.TxTransdet;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.TransaccionVo;
import gob.gamo.activosf.app.dto.TransdetVo;
import gob.gamo.activosf.app.repository.TxTransaccionRepository;
import gob.gamo.activosf.app.search.SearchCriteria;
import gob.gamo.activosf.app.services.AfSearchService;
import gob.gamo.activosf.app.services.TxTransaccionService;
import gob.gamo.activosf.app.services.TxTransdetQryService;
import gob.gamo.activosf.app.services.TxTransdetService;
import gob.gamo.activosf.app.utils.HeaderUtil;
import gob.gamo.activosf.app.utils.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = Constants.API_URL_ROOT + Constants.API_URL_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
public class TransaccionController {
    private final TxTransaccionService service;
    private final TxTransaccionRepository repository;
    private final AfSearchService searchService;
    private final TxTransdetService transdetService;
    private final TxTransdetQryService qryService;
    private static final String ENTITY_NAME = Constants.REC_TRANSACCION;

    @PostMapping(Constants.API_TRANSACCION + "/f")
    public ResponseEntity<List<TransaccionVo>> getAllSearch(
            @RequestBody(required = false) SearchCriteria sc, Pageable pageable) {

        Page<TransaccionVo> page = searchService.searchTransaf(sc, pageable).map(x -> new TransaccionVo(x));

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, Constants.API_URL_ROOT + Constants.API_URL_VERSION + Constants.API_TRANSACCION);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @Operation(summary = "Crea un nuevo registro")
    @PostMapping(value = Constants.API_TRANSACCION)
    // @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<TransaccionVo> create(User me, @RequestBody TransaccionVo req) {
        TxTransaccion result = service.registro(me, req.transaccion());

        return ResponseEntity.ok().body(new TransaccionVo(result));
    }

    @GetMapping(Constants.API_TRANSACCION + "/{slug}")
    public ResponseEntity<TransaccionVo> getById(User me, @PathVariable(value = "slug") Integer id) {
        TxTransaccion result = service.findByIdAndComplete(id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
                .body(new TransaccionVo(result));
    }

    @PostMapping(value = Constants.API_TRANSACCION + Constants.API_PREPARE)
    // @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<TransaccionVo> iniciarTransaccion(User me, @RequestBody TransaccionVo req) {
        TxTransaccion result = service.prepareOperation(me, req.transaccion());
        /*
         * ObjectMapper mapper = new ObjectMapper();
         * String json;
         * try {
         * json = mapper.writeValueAsString(r);
         * log.info("JSOM salid {}", json);
         * } catch (JsonProcessingException e) {
         * // TODO Auto-generated catch block
         * e.printStackTrace();
         * }
         */
        return ResponseEntity.ok().body((new TransaccionVo(result)));
    }

    @GetMapping(value = Constants.API_TRANSACCION + "/{slug}" + Constants.API_TRANSDET)
    // @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<List<TransdetVo>> allTransdet(@PathVariable(value = "slug") Integer id, Pageable pageable) {
        TxTransaccion trx = service.findById(id);
        Page<TransdetVo> page = transdetService.getAll(trx, pageable).map(x -> new TransdetVo(x));

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, Constants.API_URL_ROOT + Constants.API_URL_VERSION + Constants.API_TRANSACCION);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping(value = Constants.API_TRANSACCION + "/{slug}" + Constants.API_TRANSDET)
    // @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<TransdetVo> createTransdet(
            User me, @PathVariable(value = "slug") Integer id, @RequestBody TransdetVo req) {
        TxTransaccion trx = service.findById(id);
        TxTransdet result = transdetService.create(me, trx, req.transdet());

        return ResponseEntity.ok().body(new TransdetVo(result));
    }

    @PutMapping(value = Constants.API_TRANSACCION + "/{slug}" + Constants.API_TRANSDET + "/{id}")
    // @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<TransdetVo> updateTransdet(
            User me,
            @PathVariable(value = "slug") Integer id,
            @RequestBody TransdetVo req,
            @PathVariable(value = "id") Integer idDet) {
        TxTransaccion trx = service.findById(id);
        TxTransdet trxdet = transdetService.findById(idDet);
        TxTransdet result = transdetService.update(me, trx, req.transdet());

        return ResponseEntity.ok().body(new TransdetVo(result));
    }

    @DeleteMapping(value = Constants.API_TRANSACCION + "/{slug}" + Constants.API_TRANSDET + "/{id}")
    // @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<Void> delete(
            User me, @PathVariable(value = "slug") Integer id, @PathVariable(value = "id") Integer idTrandet) {
        TxTransaccion trx = service.findById(id);
        transdetService.delete(me, trx, idTrandet);

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }

    @DeleteMapping(value = Constants.API_TRANSACCION + "/{slug}" + Constants.API_TRANSDET + "/{id}" + "/rv")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<Void> reversa(User me, @PathVariable(value = "slug") Integer id) {
        TxTransaccion trx = service.findById(id);
        transdetService.reversa(me, trx, id);

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }

    @PostMapping(value = Constants.API_TRANSACCION + "/{slug}" + Constants.API_TRANSDET + Constants.API_PREPARE)
    // @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<TransdetVo> prepareTransdet(User me, @PathVariable(value = "slug") Integer id) {
        TxTransaccion trx = service.prepareOperationdet(me, id);
        TxTransdet newTd = TxTransdet.nuevoReg();
        TxTransdet result = transdetService.prepareTrxdet(me, trx, newTd);

        return ResponseEntity.ok().body(new TransdetVo(result));
    }

    @PostMapping(value = Constants.API_TRANSACCION + "/{slug}" + Constants.API_TRANSDET + "/info")
    // @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<TransdetVo> updInfoTransdet(
            User me, @PathVariable(value = "slug") Integer id, @RequestBody TransdetVo req) {
        TxTransaccion trx = service.findById(id);
        TxTransdet result = transdetService.updInfoItemaf(me, trx, req.transdet());

        return ResponseEntity.ok().body(new TransdetVo(result));
    }

    @GetMapping(Constants.API_TRANSACCION + "/{slug}" + "/summ")
    public ResponseEntity<TransaccionVo> summarizeAlmIO(User me, @PathVariable(value = "slug") Integer id) {
        TxTransaccion trx = service.findByIdAndComplete(id);
        TxTransdet result = qryService.summarizeTrx(trx);
        trx.getTransdet().add(result);

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
                .body(new TransaccionVo(trx));
    }

    @GetMapping(Constants.API_TRANSACCION + "/{slug}" + "/imp")
    public ResponseEntity<Void> acceptTransaction(User me, @PathVariable(value = "slug") Integer id) {
        TxTransaccion trx = service.findById(id);
        service.preAccept(me, trx);

        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }
}
