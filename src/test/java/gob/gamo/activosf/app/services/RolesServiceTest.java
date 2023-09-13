package gob.gamo.activosf.app.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import gob.gamo.activosf.app.IntegrationTest;
import gob.gamo.activosf.app.domain.entities.Profile;
import gob.gamo.activosf.app.domain.entities.Recurso;
import gob.gamo.activosf.app.domain.entities.Roles;
import gob.gamo.activosf.app.dto.sec.RolesVO;
import gob.gamo.activosf.app.repository.sec.ProfileRepository;
import gob.gamo.activosf.app.repository.sec.RecursoRepository;
import gob.gamo.activosf.app.repository.sec.RolesRepository;
import gob.gamo.activosf.app.services.sec.RolesService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@IntegrationTest
@DisplayName("The Roles Services")

public class RolesServiceTest {
    @Autowired
    private RolesService rolesService;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private RecursoRepository recursoRepository;
    @Autowired
    private ProfileRepository profileRepository;

    @Test
    public void dataView() {
        try {
            PageRequest pageable = PageRequest.of(0, 20);
            /*
             * List<Roles> list = rolesService.getRoles(pageable);
             * log.info("roles {}", list.size());
             * 
             * list.forEach(r -> {
             * String perms = r.getRecursos().stream().map(rec ->
             * rec.getCodrec()).collect(Collectors.joining(","));
             * //String users = r.getUsers().stream().map(rec ->
             * rec.getCodrec()).collect(Collectors.joining(","));
             * log.info("Rol reg: {} recs: {} perms: {} -> {}", r.getCodrol(),
             * r.getRecursos().size(), perms, r.getCodrecursos());
             * });
             */

            recursoRepository.findAll().forEach(r -> {
                log.info("reg rec {}", r.getCodrec());
            });

        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }

    @Test
    public void updateData() {
        try {
/*             List<Profile> pl0 = profileRepository.findByIdRolId(1);
            pl0.forEach(p -> {
                log.info("11111111111111111 {}", p.getId().getRecursoId());
            }); */

            PageRequest pageable = PageRequest.of(0, 20);
            Page<Roles> list = rolesService.getRoles(null, pageable);
            log.info("roles {}", list.getContent().size());
            list.forEach(r -> {
                String listid = r.getRecursos().stream().map(rr -> rr.getId().toString()).collect(Collectors.toList())
                        .toString();
                log.info("reg: {} {} {} [{}]", r.getCodrol(), r.getRecursos().size(), r.getCodrecursos(), listid);
            });

            RolesVO createRequest = new RolesVO("USR_ALMACENES", "Test description",
                    List.of("PROVEEDORES", "ASIGNACIONES", "SOLICITUD DE MATERIALES"));
            rolesService.updateRol(null, createRequest.codrol(), createRequest);
            Optional<Roles> resp = rolesRepository.findByCodrol(createRequest.codrol());

            log.info("resp rol {} -> {}", resp.get().getIncludeRecursos().size(), resp.get().getRecursos().size());
            list = rolesService.getRoles(null, pageable);
            list.forEach(r -> {
                String listid = r.getRecursos().stream().map(rr -> rr.getId().toString()).collect(Collectors.toList())
                        .toString();
                log.info("--> reg: {} {} {} [{}]", r.getCodrol(), r.getRecursos().size(), r.getCodrecursos(), listid);
            });

            List<Profile> pl = profileRepository.findByIdRolId(1);
            pl.forEach(p -> {
                log.info("pppppppppp {}", p.getId().getRecursoId());
            });
        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }
}
