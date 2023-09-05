package gob.gamo.activosf.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import gob.gamo.activosf.app.IntegrationTest;
import gob.gamo.activosf.app.domain.entities.Roles;
import gob.gamo.activosf.app.dto.sec.RolesVO;
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

    @Test
    public void dataView() {
        try {
            PageRequest pageable = PageRequest.of(0, 20);
/*             List<Roles> list = rolesService.getRoles(pageable);
            log.info("roles {}", list.size());

            list.forEach(r -> {
                String perms = r.getRecursos().stream().map(rec -> rec.getCodrec()).collect(Collectors.joining(","));
                //String users = r.getUsers().stream().map(rec -> rec.getCodrec()).collect(Collectors.joining(","));
                log.info("Rol reg: {} recs: {} perms: {} -> {}", r.getCodrol(), r.getRecursos().size(), perms, r.getCodrecursos());
            }); */
            
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
            PageRequest pageable = PageRequest.of(0, 20);
            List<RolesVO> list = rolesService.getRoles(null, pageable);
            log.info("roles {}", list.size());
            list.forEach(r -> {
                log.info("reg: {}", r.codrol());
            });
            
            recursoRepository.findAll().forEach(r -> {
                log.info("reg rec {}", r.getCodrec());
            });

        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }
}
