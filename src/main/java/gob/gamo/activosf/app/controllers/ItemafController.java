package gob.gamo.activosf.app.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.AfItemaf;
import gob.gamo.activosf.app.dto.EmpleadoVo;
import gob.gamo.activosf.app.dto.PersonaVO;
import gob.gamo.activosf.app.repository.AfItemafRepository;
import gob.gamo.activosf.app.search.SearchCriteria;
import gob.gamo.activosf.app.services.AfItemafService;
import gob.gamo.activosf.app.services.AfSearchService;
import gob.gamo.activosf.app.utils.PaginationUtil;
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
}
