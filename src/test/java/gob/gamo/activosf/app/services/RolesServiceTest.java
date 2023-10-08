package gob.gamo.activosf.app.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import gob.gamo.activosf.app.IntegrationTest;
import gob.gamo.activosf.app.domain.entities.Profile;
import gob.gamo.activosf.app.domain.entities.Recurso;
import gob.gamo.activosf.app.domain.entities.Roles;
import gob.gamo.activosf.app.dto.sec.RolesVO;
import gob.gamo.activosf.app.dto.sec.SingleRolResponse;
import gob.gamo.activosf.app.repository.sec.ProfileRepository;
import gob.gamo.activosf.app.repository.sec.RecursoRepository;
import gob.gamo.activosf.app.repository.sec.RolesRepository;
import gob.gamo.activosf.app.services.sec.ProfileService;
import gob.gamo.activosf.app.services.sec.RolesService;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;

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
    @Autowired
    private ProfileService profileService;

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
    public void dataProfiles() throws Exception {
        PageRequest pageable = PageRequest.of(0, 20);
        List<Profile> pl = profileRepository.findAll();
        int nr = pl.size();
        assertThat(nr).isNotEqualTo(0);
        pl.stream().forEach(p -> {
            log.info("profile [{} - {}] {} - {}", p.getId().getRolId(), p.getId().getRecursoId(), p.getRol().getId(),
                    p.getRecurso().getId());
        });
        List<Profile> plr = profileRepository.findByIdRolId(5);
        plr.stream().forEach(p -> {
            log.info("profile rol [{} - {}] {} - {}", p.getId().getRolId(), p.getId().getRecursoId(),
                    p.getRol().getId(), p.getRecurso().getId());
        });
        assertThat(plr.size()).isNotEqualTo(0);
    }

    @Test
    public void dataViewDTOs() {
        try {
            PageRequest pageable = PageRequest.of(0, 100);
            recursoRepository.findAll().forEach(r -> {
                log.info("reg rec {}", r.getCodrec());
            });

            RolesVO rol = rolesService.getSingleRol("USR_ALMACENES");
            SingleRolResponse srol = new SingleRolResponse(rol);
            Gson gson = new Gson();
            String json = gson.toJson(srol);
            log.info("json {}", json);
        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }

    @Test
    public void createRol() throws Exception {
        // try {
        RolesVO createRequest = new RolesVO("Test1", "Test description",
                List.of("AMBIENTES", "GESTION AF", "FAMILIAS AF", "INGRESO DE MATERIALES", "KARDEX DE MATERIALES"));
        String codrol = createRequest.codrol();

        RolesVO rolesVO = rolesService.createRol(null, createRequest);
        Roles roles = rolesRepository.findByCodrol(codrol).get();
        int codrolid = roles.getId();
        log.info(":> codrolid {} {}", codrolid, roles.getIncludeRecursos().size());
        List<Profile> profiles0 = profileRepository.findByIdRolId(codrolid);

        roles.getIncludeRecursos().forEach(r -> {
            Optional<Profile> pro = profileRepository.findByRolIdRecursoId(r.getId().getRolId(),
                    r.getId().getRecursoId());
            log.info("recurso [{} : {}] {} {} present: {} ", r.getId().getRolId(), r.getId().getRecursoId(),
                    r.getRecurso().getId(), r.getRecurso().getCodrec(), pro.isPresent());

        });
        profiles0.stream().forEach(r -> {
            log.info("recurso [{} : {}] {} {} present: {} ", r.getId().getRolId(), r.getId().getRecursoId(),
                    r.getRecurso().getId(), r.getRecurso().getCodrec());
        });
        assertThat(profiles0.size()).isEqualTo(5);
        assertThat(rolesVO.permisosList().size()).isEqualTo(5);
        assertThat(roles.getIncludeRecursos().size()).isEqualTo(5);

        String json = new ObjectMapper().writeValueAsString(rolesVO);
        log.info(":> json rol create {}", json);

        RolesVO rol = rolesService.getSingleRol(codrol);
        SingleRolResponse srol = new SingleRolResponse(rol);

        assertThat(rol.permisosList().size()).isEqualTo(5);
        assertThat(srol.rol().permisosList().size()).isEqualTo(5);

        json = new ObjectMapper().writeValueAsString(srol);
        log.info("json single rol query {}", json);

        // json = new ObjectMapper().writeValueAsString(roles);
        log.info("json rool query [{}] {}", codrolid, roles.getIncludeRecursos().size());

        RolesVO newrol = new RolesVO("Test1", "Test description update",
                List.of("PROVEEDORES", "AMBIENTES", "SOLICITUD DE MATERIALES"));

        newrol = rolesService.updateRol(null, newrol.codrol(), newrol);
        roles = rolesRepository.findByCodrol(codrol).get();

        json = new ObjectMapper().writeValueAsString(newrol);
        log.info("POST update json roleid [{}] permisos: {} [{}]", codrolid, newrol.permisosList().size(), json);

        List<Profile> profiles = profileRepository.findByIdRolId(codrolid);

        assertThat(profiles.size()).isEqualTo(3);
        assertThat(newrol.permisosList().size()).isEqualTo(3);
        assertThat(roles.getIncludeRecursos().size()).isEqualTo(3);
        
        roles = rolesRepository.findByCodrol(codrol).get();
        json = new ObjectMapper().writeValueAsString(roles);
        log.info("json update rool query {} [{}]", json, roles.getIncludeRecursos().size());
        
        assertThat(roles.getIncludeRecursos().size()).isEqualTo(3);

        newrol = new RolesVO("Test1", "Test description update",
                List.of("PROVEEDORES", "AMBIENTES"));

        RolesVO newrol2 = rolesService.updateRol(null, newrol.codrol(), newrol);

        roles = rolesRepository.findByCodrol(codrol).get();
        json = new ObjectMapper().writeValueAsString(roles);
        log.info("json delete rool query {} [{}]", json, roles.getIncludeRecursos().size());

        profiles = profileRepository.findByIdRolId(codrolid);

        assertThat(profiles.size()).isEqualTo(2);
        profiles.stream().forEach(r -> {
            log.info("Select recurso [{} : {}] {} {} codrol: {} ", r.getId().getRolId(), r.getId().getRecursoId(),
                    r.getRecurso().getId(), r.getRecurso().getCodrec(), r.getRol().getCodrol());
        });

        roles.getIncludeRecursos().forEach(r -> {
            log.info("recurso [{} : {}] {} {} codrol: {} ", r.getId().getRolId(), r.getId().getRecursoId(),
                    r.getRecurso().getId(), r.getRecurso().getCodrec(), r.getRol().getCodrol());
        });
        assertThat(roles.getIncludeRecursos().size()).isEqualTo(2);

        /*
         * } catch (Exception e) {
         * log.error("Error:" + e.getMessage(), e);
         * }
         */
    }

    @Test
    public void updateData() {
        try {
            /*
             * List<Profile> pl0 = profileRepository.findByIdRolId(1);
             * pl0.forEach(p -> {
             * log.info("11111111111111111 {}", p.getId().getRecursoId());
             * });
             */

            PageRequest pageable = PageRequest.of(0, 20);
            Page<Roles> list = rolesService.getRoles(null,pageable);
            log.info("roles {}", list.getContent().size());
            list.forEach(r -> {
                String listid = r.getIncludeRecursos().stream().map(rr -> rr.getId().toString())
                        .collect(Collectors.toList())
                        .toString();
                log.info("reg: {} {} {} [{}]", r.getCodrol(), r.getIncludeRecursos().size(), r.getIncludeRecursos(),
                        listid);
            });

            RolesVO createRequest = new RolesVO("USR_ALMACENES", "Test description",
                    List.of("PROVEEDORES", "ASIGNACIONES", "SOLICITUD DE MATERIALES"));
            rolesService.updateRol(null, createRequest.codrol(), createRequest);
            Optional<Roles> resp = rolesRepository.findByCodrol(createRequest.codrol());

            log.info("resp rol {} -> {}", resp.get().getIncludeRecursos().size(),
                    resp.get().getIncludeRecursos().size());
            list = rolesService.getRoles(null,pageable);
            list.forEach(r -> {
                String listid = r.getIncludeRecursos().stream().map(rr -> rr.getId().toString())
                        .collect(Collectors.toList())
                        .toString();
                log.info("--> reg: {} {} {} [{}]", r.getCodrol(), r.getIncludeRecursos().size(), r.getIncludeRecursos(),
                        listid);
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
