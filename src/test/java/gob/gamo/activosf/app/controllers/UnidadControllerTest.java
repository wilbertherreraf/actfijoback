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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import gob.gamo.activosf.app.IntegrationTest;
import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.dto.UnidadResponse;
import gob.gamo.activosf.app.dto.sec.LoginUserRequest;
import gob.gamo.activosf.app.dto.sec.RolesVO;
import gob.gamo.activosf.app.dto.sec.UserVO;
import gob.gamo.activosf.app.services.UnidadService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.json.JSONObject;

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
    public String urlReq = Constants.API_URL_ROOT + Constants.API_URL_VERSION;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Test
    public void getUnidades() {
        try {
            PageRequest pageable = PageRequest.of(0, 20);
            List<UnidadResponse> list = service.findAll(pageable).getContent();
            log.info("Undis {}", list.size());
        } catch (Exception e) {
            log.error("Error " + e.getMessage(), e);
        }
    }

    @Test
    public void getUnidadesRest() {
        try {
            String jamesToken = returnTokenUser();

            // when
            ResultActions resultActions = mockMvc.perform(
                    get(urlReq + Constants.API_UNIDS).param("pageCode", "USR_ALMACENES")
                            .header("Authorization", "Token " + jamesToken));

            String strRet = resultActions.andReturn().getResponse().getContentAsString();
            log.info("return rest Header *****************");
            for (String hdr : resultActions.andReturn().getResponse().getHeaderNames()) {
                String valHdr = resultActions.andReturn().getResponse().getHeader(hdr);
                log.info("Header {} -> {}", hdr, valHdr);
            }
            log.info("return rest {}", strRet);

            /* resultActions.andExpect(status().isOk()).andDo(print());
            log.info("************** NUEVA LLAMADA ***************");
            resultActions = mockMvc.perform(
                    get(urlReq + Constants.API_UNIDS + "/8")
                            .header("Authorization", "Token " + jamesToken));

            strRet = resultActions.andReturn().getResponse().getContentAsString();
            log.info("xxx: nn return rest {}", strRet);

            resultActions.andExpect(status().isOk()).andDo(print()); */
        } catch (Exception e) {
            log.error("Error " + e.getMessage(), e);
        }
    }

    @Test
    public void getEmplUnidades() {
        try {
            String jamesToken = returnTokenUser();

            // when
            ResultActions resultActions = mockMvc.perform(
                    get(urlReq + Constants.API_UNIDS + "/8" + "/empleados?page=1&size=2")
                            .header("Authorization", "Token " + jamesToken));

            String strRet = resultActions.andReturn().getResponse().getContentAsString();

            log.info("return rest Header *****************");
            for (String hdr : resultActions.andReturn().getResponse().getHeaderNames()) {
                String valHdr = resultActions.andReturn().getResponse().getHeader(hdr);
                log.info("Header {} -> {}", hdr, valHdr);
            }

            log.info("return rest {}", strRet);
            log.info("************** NW ***********");
            MvcResult result = mockMvc
                    .perform(get(urlReq + Constants.API_UNIDS + "/8" + "/empleados")
                            .header("Authorization", "Token " + jamesToken))
                    .andExpect(status().isOk()).andReturn();
            MockHttpServletResponse response = result.getResponse();

            JSONObject jsonObject = new JSONObject(response.getContentAsString());
            String jsonPrettyPrintString = jsonObject.toString(4);
            log.info("jsonObject {} ", jsonPrettyPrintString);
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
        ResultActions resultActions = mockMvc.perform(post(urlReq + Constants.API_UNIDS)
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

    @Test
    public void validTk() {
        String tk = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3JlYWx3b3JsZC5pbyIsInN1YiI6IjIzIiwiZXhwIjoxNjkzMzAxMTA4LCJpYXQiOjE2OTMzMDA4MDh9.bBD_518xZp5j_D4Nf00WV7qNrlzqz8NujOqaprE8PuD6tdq1KJ_2Tf1Ajj2-9CADrJgmAVjW5-FRmTfGrIPHJlCdI3YX9QoGmo9e-a6eq21hkRmGOHNNoNxmyMzh4Tr6wi-dMx9bi_xtpHH6IZD79TfEmC_0K_pw7VclHZexf-vuXiEcfzdbflVOOHFkbP4BQu5XkfZub_AWZ72arJFIRl_sxKblx42DeEVJ_n85c89fD7Ap47-1YR9kPqmn4mu2M9xL2XO83AnniIdjK7dtG9Ow_NktRKEf0nhMY6JfLuSTtDODKk3GlyTpHbKVfKRvg_vImWtdPpDqK0H6GiHmwQ";
        try {
            log.info("nimbus {}", (jwtDecoder == null));
            Jwt token = jwtDecoder.decode(tk);
            token.getClaims().entrySet().forEach(t -> {
                log.info("entry ", t.getKey(), t.getValue());
            });
            ;
        } catch (Exception e) {
            log.error("error " + e.getMessage(), e);
        }
    }

    public String returnTokenUser() throws Exception {
        LoginUserRequest loginRequest = new LoginUserRequest("asdf", "asdf");

        ResultActions resultActions = mockMvc.perform(post(Constants.API_ROOT_VERSION + Constants.API_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of("user", loginRequest))));

        MvcResult retTest = resultActions.andReturn();
        String strRes = retTest.getResponse().getContentAsString();
        log.info("___returl {}", strRes);

        Gson gson = new Gson();
        UserResponse st = gson.fromJson(strRes, UserResponse.class);
        log.info("user map {}", st.toString());
        UserVO user = st.getUser();

        return user.token();
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class UserResponse {
        private UserVO user;
    }
}
