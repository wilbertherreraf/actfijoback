package gob.gamo.activosf.app.controllers;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.dto.UnidadResponse;
import gob.gamo.activosf.app.services.UnidadService;

@Slf4j
@RestController
@RequestMapping("/api/v1/unidades")
// @RequiresPermissions("sys:manage:role")
@RequiredArgsConstructor
public class UnidadController {
    private final UnidadService service;

    @GetMapping("/")
    public ResponseEntity<List<UnidadResponse>> getAll(Pageable pageable) {
        log.info("en {} query {}", this.getClass().getSimpleName(), pageable.toString());
        List<UnidadResponse> list = service.findAll();
        return ResponseEntity.ok(list);
    }
}
