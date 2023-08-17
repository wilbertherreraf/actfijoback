package gob.gamo.activosf.app.controllers;

import java.util.List;
import org.springframework.data.domain.Pageable;
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

import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.sec.RolesVO;
import gob.gamo.activosf.app.dto.sec.SingleRolResponse;
import gob.gamo.activosf.app.services.sec.RolesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Api("Role Controller")
@Slf4j
@RestController
@RequestMapping("/api/auth")
// @RequiresPermissions("sys:manage:role")
@RequiredArgsConstructor
public class RolesController {
    private final RolesService rolesService;

    @GetMapping("/roles")
    public ResponseEntity<List<RolesVO>> getRolePresentationList(Pageable pageable) {
        log.info("en roles:: {}", pageable.toString());
        List<RolesVO> list = rolesService.getRoles(null, pageable);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/roles")
    //@PreAuthorize("hasRole('rol:write')")
    public SingleRolResponse createRol(User me, @RequestBody RolesVO request) {
        RolesVO rol = rolesService.createRol(me, request);
        return new SingleRolResponse(rol);
    }

    @GetMapping("/roles/{codrol}")
    public SingleRolResponse getSingleRol(User me, @PathVariable String codrol) {
        RolesVO rol = rolesService.getSingleRol(me, codrol);
        return new SingleRolResponse(rol);
    }

    @PutMapping("/roles/{codrol}")
    public SingleRolResponse updateRol(
            User me, @PathVariable String codrol, @RequestBody RolesVO request) {
        RolesVO rol = rolesService.updateRol(me, codrol, request);
        return new SingleRolResponse(rol);
    }

    @DeleteMapping("/roles/{codrol}")
    public void deleteRol(User me, @PathVariable String codrol) {
        rolesService.deleteRol(me, codrol);
    }
}
