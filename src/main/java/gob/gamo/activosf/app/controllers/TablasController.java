package gob.gamo.activosf.app.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.entities.GenDesctabla;
import gob.gamo.activosf.app.repository.GenDesctablaRespository;
import gob.gamo.activosf.app.utils.PaginationUtil;
import gob.gamo.activosf.app.utils.WebUtil;

@Slf4j
@RestController
@RequestMapping(value = Constants.API_URL_ROOT + Constants.API_URL_VERSION, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TablasController {
    private final GenDesctablaRespository repository;
    private static final String ENTITY_NAME = Constants.REC_TABLAS;
 
    @GetMapping(Constants.API_TABLAS)
    public ResponseEntity<List<GenDesctabla>> getAll(Pageable pageable) {
        final Page<GenDesctabla> page = repository.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                page, Constants.API_URL_ROOT + Constants.API_URL_VERSION + Constants.API_UNIDS);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping(Constants.API_TABLAS + "/{slug}" + Constants.API_TABLASDET)
    public ResponseEntity<List<GenDesctabla>> unidadEmpleados(
            @PathVariable(value = "slug") Integer id, Pageable pageable) {
        List<GenDesctabla> result = repository.findByDesCodtab(id);
        Page<GenDesctabla> pageRet =
                PaginationUtil.pageForList((int) pageable.getPageNumber(), pageable.getPageSize(), result);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                pageRet, WebUtil.getBaseURL() + WebUtil.getRequest().getRequestURI());
        return ResponseEntity.ok().headers(headers).body(pageRet.getContent());
    }
}
