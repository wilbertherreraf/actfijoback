package gob.gamo.activosf.app.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwaremill.realworld.IntegrationTest;

import gob.gamo.activosf.app.dto.UnidadResponse;
import gob.gamo.activosf.app.services.UnidadService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@IntegrationTest
@DisplayName("Realworld Application")
public class UnidadControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UnidadService service;

    @Test
    public void getUnidades() {
        try {
            List<UnidadResponse> list = service.findAll();
            log.info("Undis {}", list.size());
        } catch (Exception e) {
            log.error("Error " + e.getMessage(), e);
        }
    }

    @Test
    public void getUnidadesRest() {
        try {

            mockMvc.perform(get("/api/v1/unidades")).andExpect(status().isOk())
                    /*
                     * .andExpect(jsonPath("$.articles[0].title").value("Effective Java"))
                     * .andExpect(jsonPath("$.articles[0].author.username").value("james"))
                     * .andExpect(jsonPath("$.articles[0].favorited").value(false))
                     * .andExpect(jsonPath("$.articles[0].favoritesCount").value(1))
                     * .andExpect(jsonPath("$.articles[0].tagList[0]").value("java"))
                     */
                    .andDo(print());

        } catch (Exception e) {
            log.error("Error " + e.getMessage(), e);
        }
    }
}
