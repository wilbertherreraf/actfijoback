package gob.gamo.activosf.app.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.google.common.base.Joiner;

import gob.gamo.activosf.app.IntegrationTest;
import gob.gamo.activosf.app.domain.OrgPersona;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.repository.PersonaRepository;
import gob.gamo.activosf.app.search.CriteriaParser;
import gob.gamo.activosf.app.search.GenericSpecificationsBuilder;
import gob.gamo.activosf.app.search.SearchCriteria;
import gob.gamo.activosf.app.search.SearchOperation;
import gob.gamo.activosf.app.search.SpecSearchCriteria;
import gob.gamo.activosf.app.search.UserSpecification;
import gob.gamo.activosf.app.search.UserSpecificationsBuilder;
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
            params.add(new SearchCriteria("nombre", ":", "sd"));
            params.add(new SearchCriteria("direccion", ":", "asdf"));

            PageRequest pageable = PageRequest.of(0, 20);
            List<OrgPersona> list = service.findAll(pageable).getContent();
            list.forEach(r -> {
                log.info("reg {} - {}, {}", r.getIdPersona(), r.getNombre(), r.getDireccion());
            });
            list = service.search(params);
            list.forEach(r -> {
                log.info("reg {} - {}, {}", r.getIdPersona(), r.getNombre(), r.getDireccion());
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

            List<OrgPersona> list = service.findAll(pageable).getContent();
            list.forEach(r -> {
                log.info("reg {} - {}, {}", r.getIdPersona(), r.getNombre(), r.getDireccion());
            });

            Page<OrgPersona> list0 = service.search(searchTxt, pageable);
            list0.forEach(r -> {
                log.info("Search reg {} - {}, {}", r.getIdPersona(), r.getNombre(), r.getDireccion());
            });

        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }
}
