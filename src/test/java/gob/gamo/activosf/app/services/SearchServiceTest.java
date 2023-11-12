package gob.gamo.activosf.app.services;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import gob.gamo.activosf.app.IntegrationTest;
import gob.gamo.activosf.app.domain.OrgPersona;
import gob.gamo.activosf.app.repository.PersonaRepository;
import gob.gamo.activosf.app.search.CriteriaParser;
import gob.gamo.activosf.app.search.SearchCriteria;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@IntegrationTest
public class SearchServiceTest {
    @Autowired
    PersonaService service;
    @Autowired
    PersonaRepository repository;

    @Test
    public void dataSearch() {
        try {
            final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
            params.add(SearchCriteria.builder().key("nombre").operation(":").value("sd").build());
            //params.add(new SearchCriteria("direccion", ":", "asdf"));
            params.add(SearchCriteria.builder().key("tipopers").operation(":").value(1).build());

            PageRequest pageable = PageRequest.of(0, 20);
            List<OrgPersona> list = service.findAll(null, pageable).getContent();
            list.forEach(r -> {
                log.info("reg {} - {}, {} tipoP: [{}]", r.getIdPersona(), r.getNombre(), r.getDireccion(),
                        (r.getTipopersdesc() != null ? r.getTipopersdesc().getId().getDesCodigo() : ""));
            });
            log.info("====== inicio search params ======");
            //list = service.search(params);
            list.forEach(r -> {
                log.info("reg {} - {}, {} tipoP: [{}]", r.getIdPersona(), r.getNombre(), r.getDireccion(),
                        (r.getTipopersdesc() != null ? r.getTipopersdesc().getId().getDesCodigo() : ""));
            });
        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }

    @Test
    public void dataSearchTxt() {
        try {
            log.info("====== inicio txt ======");

            String searchTxt = "nombre:asdf or direccion:*f dd* or telefono:a*";
            CriteriaParser parser = new CriteriaParser();
            PageRequest pageable = PageRequest.of(0, 20);

            List<OrgPersona> list = service.findAll(null, pageable).getContent();
            list.forEach(r -> {
                log.info("reg {} - {}, {}", r.getIdPersona(), r.getNombre(), r.getDireccion());
            });

            /* Page<OrgPersona> list0 = service.search(searchTxt, pageable);
            list0.forEach(r -> {
                log.info("Search reg {} - {}, {}", r.getIdPersona(), r.getNombre(), r.getDireccion());
            }); */

        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }


}
