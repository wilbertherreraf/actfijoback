package gob.gamo.activosf.app.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import gob.gamo.activosf.app.IntegrationTest;
import gob.gamo.activosf.app.domain.OrgEmpleado;
import gob.gamo.activosf.app.domain.OrgPersona;
import gob.gamo.activosf.app.search.SearchCriteria;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@IntegrationTest

public class EmpleadoServiceTest {
    @Autowired
    EmpleadoService empleadoService;

    @Test
    public void dataSearch() {
        try {
            log.info("********* INICIO ************");
            SearchCriteria root = SearchCriteria.builder().key("").operation("ROOT").value("").build();
            SearchCriteria scAnd = SearchCriteria.builder().key("").operation("AND").value("").build();
            SearchCriteria scFecha = SearchCriteria.builder().key("fechaBaja").operation("Nil").value(1).build();
            SearchCriteria scNombre = SearchCriteria.builder().key("persona.nombre").operation(":").value("asd").build();

            root.getChildren().add(scAnd);
            scAnd.getChildren().add(scNombre);
            scAnd.getChildren().add(scFecha);

            List<OrgEmpleado>  l = empleadoService.search(root);
            log.info("emples {}", l.size());
        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }
    @Test
    public void dataSearchOR() {
        try {
            log.info("********* INICIO ************");
            SearchCriteria root = SearchCriteria.builder().key("").operation("ROOT").value("").build();
            SearchCriteria scAnd = SearchCriteria.builder().key("").operation("OR").value("").build();
            SearchCriteria scFecha = SearchCriteria.builder().key("persona.numeroDocumento").operation(":").value("asd").build();
            SearchCriteria scNombre = SearchCriteria.builder().key("persona.nombre").operation(":").value("asd").build();

            root.getChildren().add(scAnd);
            scAnd.getChildren().add(scNombre);
            scAnd.getChildren().add(scFecha);

            List<OrgEmpleado>  l = empleadoService.search(root);
            log.info("emples {}", l.size());
        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }

    @Test
    public void dataSearchORAND() {
        try {
            log.info("********* INICIO ************");
            SearchCriteria root = SearchCriteria.builder().key("").operation("ROOT").value("").build();
            SearchCriteria scOr = SearchCriteria.builder().key("").operation("OR").value("").build();
            SearchCriteria scFecha = SearchCriteria.builder().key("persona.numeroDocumento").operation(":").value("asd*").build();
            SearchCriteria scNombre = SearchCriteria.builder().key("persona.nombre").operation(":").value("asd*").build();

            SearchCriteria scAnd = SearchCriteria.builder().key("").operation("AND").value("").build();            
            SearchCriteria scTipoper = SearchCriteria.builder().key("persona.tipopers").operation(":").value(1).build();
            SearchCriteria scFechab = SearchCriteria.builder().key("fechaBaja").operation("Nil").build();

            root.getChildren().add(scOr);
            scOr.getChildren().add(scNombre);
            scOr.getChildren().add(scFecha);

            root.getChildren().add(scAnd);
            scAnd.getChildren().add(scTipoper);
            scAnd.getChildren().add(scFechab);

            List<OrgEmpleado>  l = empleadoService.search(root);
            log.info("emples {}", l.size());
            l.forEach(r -> {
                log.info("resp {} ; {} ; {}", r.getPersona().getNombre(),r.getFechaBaja(), r.getPersona().getNumeroDocumento());
            });
        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }    

    public static void main(String[] args) {
        String cad = "asdfa*";
        System.out.println("asdf " + cad.replaceAll("\\*", ""));
    }
}
