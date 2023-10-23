package gob.gamo.activosf.app.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import gob.gamo.activosf.app.IntegrationTest;
import gob.gamo.activosf.app.domain.OrgEmpleado;
import gob.gamo.activosf.app.domain.OrgPersona;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.search.SearchCriteria;
import gob.gamo.activosf.app.utils.UtilsDate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@IntegrationTest
public class PersonaServiceTest {
    @Autowired
    PersonaService personaService;
    @Autowired
    AfSearchService AfSearchService;

    @Test
    public void dataSearchPageable() {
        try {
            Sort sort = Sort.by("nombre").descending();
            PageRequest pageable = PageRequest.of(0, 55, sort);
            log.info("********* INICIO ************");
            SearchCriteria root = SearchCriteria.builder().key("").operation("ROOT").value("").build();
            SearchCriteria scAnd = SearchCriteria.builder().key("").operation("AND").value("").build();
            SearchCriteria scOr = SearchCriteria.builder().key("").operation("OR").value("").build();
            // SearchCriteria scFecha =
            // SearchCriteria.builder().key("fechaBaja").operation("Nil").value(1).build();
            SearchCriteria scNombre = SearchCriteria.builder().key("persona.nombre").operation(":").value("*asd*")
                    .build();
            SearchCriteria scNroDoc = SearchCriteria.builder().key("persona.numeroDocumento").operation(":")
                    .value("*asd*")
                    .build();
            SearchCriteria scFechab = SearchCriteria.builder().key("empleado.fechaBaja").operation("Nil").build();
            SearchCriteria scUser = SearchCriteria.builder().key("usuario.idUnidEmpl").operation("notNil").build();

            root.getChildren().add(scAnd);
            root.getChildren().add(scOr);
            scOr.getChildren().add(scNombre);
            scOr.getChildren().add(scNroDoc);
            scAnd.getChildren().add(scFechab);
            // scAnd.getChildren().add(scUser);

            Page<OrgPersona> l = AfSearchService.searchPersona(root, pageable);
            log.info("emples {}", l.getContent().size());
            l.getContent().forEach(p -> {
                log.info("persona {} {} empleados: {}", p.getIdPersona(), p.getNombre(),
                        (p.getEmpleos() != null ? p.getEmpleos().size() : 0));
            });
            Arrays.asList(0, 1, 2, 3, 4, 5).forEach(pagenumber -> {
                log.info("******* PAGE {} *********", pagenumber);
                PageRequest pg = PageRequest.of(pagenumber, 3, sort);
                Page<OrgPersona> lp = AfSearchService.searchPersona(root, pg);
                lp.getContent().forEach(p -> {
                    log.info("[{}] persona {} {} empleados: {}", pg.getPageNumber(), p.getIdPersona(), p.getNombre(),
                            (p.getEmpleos() != null ? p.getEmpleos().size() : 0));
                });
            });
        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }

    @Test
    public void dataSearchEmpleados() {
        try {
            Sort sort = Sort.by(Direction.ASC, "persona.nombre").and(Sort.by(Direction.ASC, "empleado.id"));
            Date f = UtilsDate.dateFromString("10/10/2023", "dd/MM/yyyy");
            PageRequest pageable = PageRequest.of(0, 55, sort);
            log.info("********* INICIO ************");
            SearchCriteria root = SearchCriteria.builder().key("").operation("ROOT").value("").build();
            SearchCriteria scAnd = SearchCriteria.builder().key("persona.").operation("AND").value("").build();
            SearchCriteria scOr = SearchCriteria.builder().key("").operation("OR").value("").build();
            // SearchCriteria scFecha =
            // SearchCriteria.builder().key("fechaBaja").operation("Nil").value(1).build();
            SearchCriteria scNombre = SearchCriteria.builder().key("persona.nombre").operation(":").value("*asd*")
                    .build();
            SearchCriteria scNroDoc = SearchCriteria.builder().key("persona.numeroDocumento").operation(":")
                    .value("*asd*")
                    .build();
            // SearchCriteria scFechab =
            // SearchCriteria.builder().key("empleado.fechaBaja").operation("Nil").build();
            SearchCriteria scFechab = SearchCriteria.builder().key("empleado.fechaIngreso").operation(">").value(f)
                    .build();
            SearchCriteria scIdpernotnull = SearchCriteria.builder().key("empleado.idPersona").operation("notNil")
                    .build();
            SearchCriteria scUser = SearchCriteria.builder().key("usuario.idUnidEmpl").operation("notNil").build();

            root.getChildren().add(scAnd);
            root.getChildren().add(scOr);
            scOr.getChildren().add(scNombre);
            // scOr.getChildren().add(scNroDoc);
            scAnd.getChildren().add(scFechab);
            scAnd.getChildren().add(scUser);
            scAnd.getChildren().add(scIdpernotnull);

            Page<OrgEmpleado> l = AfSearchService.searchEmpleados(root, pageable);
            log.info("emples {}", l.getContent().size());
            l.getContent().forEach(p -> {
                log.info("empleado {} <-> {} nombre: {}", p.getId(), p.getIdPersona(),
                        (p.getPersona() != null ? p.getPersona().getNombre() : ""));
            });
            Arrays.asList(0, 1, 2, 3, 4, 5).forEach(pagenumber -> {
                log.info("******* PAGE {} *********", pagenumber);
                PageRequest pg = PageRequest.of(pagenumber, 5, sort);
                Page<OrgEmpleado> lp = AfSearchService.searchEmpleados(root, pg);
                lp.getContent().forEach(p -> {
                    log.info("[{}] persona {} <-> {}; {} nombre: {} ", pg.getPageNumber(), p.getId(), p.getIdPersona(),
                            p.getFechaIngreso(),
                            (p.getPersona() != null ? p.getPersona().getNombre() : ""));
                });
            });
        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }

    @Test
    public void dataSearchUsers() {
        try {
            Sort sort = Sort.by(Direction.ASC, "persona.nombre").and(Sort.by(Direction.ASC, "usuario.id"));
            Date f = UtilsDate.dateFromString("10/10/2023", "dd/MM/yyyy");
            PageRequest pageable = PageRequest.of(0, 55, sort);
            log.info("********* INICIO ************");
            SearchCriteria root = SearchCriteria.builder().key("").operation("ROOT").value("").build();
            SearchCriteria scAnd = SearchCriteria.builder().key("persona.").operation("AND").value("").build();
            SearchCriteria scOr = SearchCriteria.builder().key("").operation("OR").value("").build();
            // SearchCriteria scFecha =
            // SearchCriteria.builder().key("fechaBaja").operation("Nil").value(1).build();
            SearchCriteria scNombre = SearchCriteria.builder().key("persona.nombre").operation(":").value("*asd*")
                    .build();
            SearchCriteria scNroDoc = SearchCriteria.builder().key("persona.numeroDocumento").operation(":")
                    .value("*asd*")
                    .build();
            // SearchCriteria scFechab =
            // SearchCriteria.builder().key("empleado.fechaBaja").operation("Nil").build();
            SearchCriteria scFechab = SearchCriteria.builder().key("empleado.fechaIngreso").operation(">").value(f)
                    .build();
            SearchCriteria scIdpernotnull = SearchCriteria.builder().key("empleado.idPersona").operation("notNil")
                    .build();
            SearchCriteria scUser = SearchCriteria.builder().key("usuario.idUnidEmpl").operation("notNil").build();
            SearchCriteria scTipopers = SearchCriteria.builder().key("persona.tipopers").operation(":").value(1)
                    .build();
            root.getChildren().add(scAnd);
            root.getChildren().add(scOr);
            scOr.getChildren().add(scNombre);
            // scOr.getChildren().add(scNroDoc);
            scAnd.getChildren().add(scFechab);
            scAnd.getChildren().add(scUser);
            scAnd.getChildren().add(scIdpernotnull);
            scAnd.getChildren().add(scTipopers);

            Page<User> l = AfSearchService.searchUsuarios(root, pageable);
            log.info("emples {}", l.getContent().size());
            l.getContent().forEach(p -> {
                log.info("empleado {} <-> {} nombre: {}", p.getId(), p.getIdUnidEmpl(),
                        (p.getPersona() != null ? p.getPersona().getNombre() : ""));
            });
            Arrays.asList(0, 1, 2, 3, 4, 5).forEach(pagenumber -> {
                log.info("******* PAGE {} *********", pagenumber);
                PageRequest pg = PageRequest.of(pagenumber, 5, sort);
                Page<User> lp = AfSearchService.searchUsuarios(root, pg);
                lp.getContent().forEach(p -> {
                    log.info("[{}] persona {} <-> {}; {} nombre: {} ", pg.getPageNumber(), p.getId(), p.getIdUnidEmpl(),
                            p.getUsername(),
                            (p.getPersona() != null ? p.getPersona().getNombre() : ""));
                });
            });
        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }

}
