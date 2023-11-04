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

import com.google.gson.Gson;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.AfItemaf;
import gob.gamo.activosf.app.domain.OrgUnidad;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.EmpleadoVo;
import gob.gamo.activosf.app.dto.ItemafVo;
import gob.gamo.activosf.app.dto.PersonaVO;
import gob.gamo.activosf.app.dto.sec.RolesVO;
import gob.gamo.activosf.app.dto.sec.SingleRolResponse;
import gob.gamo.activosf.app.repository.AfItemafRepository;
import gob.gamo.activosf.app.search.SearchCriteria;
import gob.gamo.activosf.app.services.AfItemafService;
import gob.gamo.activosf.app.services.AfSearchService;
import gob.gamo.activosf.app.utils.HeaderUtil;
import gob.gamo.activosf.app.utils.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = Constants.API_URL_ROOT + Constants.API_URL_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemafController {
    private final AfSearchService searchService;
    private final AfItemafService service;
    private final AfItemafRepository repository;
    private static final String ENTITY_NAME = Constants.REC_ITEMS;

    @PostMapping(Constants.API_ITEMS + "/f")
    public ResponseEntity<List<AfItemaf>> getAllSearch(
            @RequestBody(required = false) SearchCriteria sc, Pageable pageable) {

        Page<AfItemaf> page = searchService.searchItemsaf(sc, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, Constants.API_URL_ROOT + Constants.API_URL_VERSION + Constants.API_ITEMS);
        return ResponseEntity.ok()
                .headers(headers)
                .body(page.getContent());

    }

    @PostMapping(value = Constants.API_ITEMS)
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<AfItemaf> create(@RequestBody ItemafVo req) {
        AfItemaf result = service.crearNuevo(req.itemaf());
        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping(Constants.API_ITEMS + "/{slug}")
    public ResponseEntity<AfItemaf> getById(@PathVariable(value = "slug") Integer id) {
        AfItemaf result = service.findById(id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
                .body(result);
    }

    @PutMapping(value = Constants.API_ITEMS + "/{slug}")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<AfItemaf> updateEntity(
            @PathVariable(value = "slug") String id, @RequestBody ItemafVo entityReq) {
        AfItemaf result = service.update(entityReq.itemaf());
        return ResponseEntity.ok()
                .body(result);
    }

    @DeleteMapping(Constants.API_ITEMS + "/{slug}")
    @PreAuthorize("hasAuthority('" + ENTITY_NAME + "')")
    public ResponseEntity<Void> deleteEntity(@PathVariable(value = "slug") Integer id) {
        repository.deleteById(id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString()))
                .build();
    }
}
