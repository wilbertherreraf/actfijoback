package gob.gamo.activosf.app.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import gob.gamo.activosf.app.IntegrationTest;
import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.dto.UnidadResponse;
import gob.gamo.activosf.app.dto.sec.RolesVO;
import gob.gamo.activosf.app.services.UnidadService;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;

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
    public String urlReq = Constants.API_URL_ROOT + Constants.API_URL_VERSION ;

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

            mockMvc.perform(get(urlReq + Constants.API_URL_CLASS_UNIDADES)).andExpect(status().isOk())
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

    @Test
    @DisplayName("provides an API that allows authenticated users to create roles.")
    void createEntity() throws Exception {
        // given
        UnidadResponse request = new UnidadResponse(0, "UNIDAD", "CALLE 1", "UNID", "123456",
                "SERVICIO", 0, "V");

        // when
        ResultActions resultActions = mockMvc.perform(post(urlReq + Constants.API_URL_CLASS_UNIDADES)
                // .header("Authorization", jamesToken)
                .content(objectMapper.writeValueAsString(Map.of("unidad", request)))
                .contentType(MediaType.APPLICATION_JSON));
        String s = objectMapper.writeValueAsString(Map.of("unidad", request));
        log.info("*************** {}", s);
        resultActions.andDo(print());
        log.info("***************");

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sigla").value("UNID"))
                /*
                 * .andExpect(jsonPath("$.rol.descripcion").value("Test description"))
                 * .andExpect(jsonPath("$.rol.permisosList")
                 * .value(new JSONArray().appendElement("RecT1").appendElement("RecT2")))
                 */
                // .andExpect(jsonPath("$.article.author.username").value("james"))
                .andDo(print());
    }
}
