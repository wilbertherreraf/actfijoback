package gob.gamo.activosf.app.controllers;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;

import gob.gamo.activosf.app.IntegrationTest;
import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.controllers.UnidadControllerTest.UserResponse;
import gob.gamo.activosf.app.domain.entities.Recurso;
import gob.gamo.activosf.app.domain.entities.Roles;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.sec.LoginUserRequest;
import gob.gamo.activosf.app.dto.sec.RolesVO;
import gob.gamo.activosf.app.dto.sec.SignUpUserRequest;
import gob.gamo.activosf.app.dto.sec.SingleRolResponse;
import gob.gamo.activosf.app.dto.sec.UserVO;
import gob.gamo.activosf.app.repository.sec.RecursoRepository;
import gob.gamo.activosf.app.repository.sec.RolesRepository;
import gob.gamo.activosf.app.repository.sec.UserRepository;
import gob.gamo.activosf.app.services.sec.RolesService;
import gob.gamo.activosf.app.services.sec.UserService;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Slf4j
@IntegrationTest
@DisplayName("Realworld Application")
public class RolesControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private String jamesToken;
    private String simpsonToken;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private RolesService rolesService;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private RecursoRepository recursoRepository;

    @BeforeEach
    void setUp() throws Exception {
        SignUpUserRequest jamesSignUpRequest = new SignUpUserRequest(null,"test@example.com", "test", "password",
                "test user",null,null);
        User james = userService.signUp(jamesSignUpRequest);

        SignUpUserRequest simpsonSignUpRequest = new SignUpUserRequest(null,"simpson@example.com", "simpson", "password",
                "test user", null, null);
        User simpson = userService.signUp(simpsonSignUpRequest);

        /*
         * Recurso java = new Recurso("java", "java");
         * recursoRepository.save(java);
         */

        Roles effectiveJava = Roles.builder()
                .codrol("RolT0")
                .descripcion("description")
                .build();

        // java.permissioning(effectiveJava);
        rolesRepository.save(effectiveJava);

        LoginUserRequest jamesLoginRequest = new LoginUserRequest("test", "password");
        jamesToken = "Token " + userService.login(jamesLoginRequest).token();

        LoginUserRequest simpsonLoginRequest = new LoginUserRequest("simpson", "password");
        simpsonToken = "Token " + userService.login(simpsonLoginRequest).token();
    }

    @Test
    @DisplayName("provides an API that allows unauthenticated users to view recent articles under specific conditions.")
    void getRoles0() throws Exception {
        mockMvc.perform(get(Constants.API_ROOT_VERSION + Constants.API_ROLES))
                .andExpect(status().isOk())
                /*
                 * .andExpect(jsonPath("$.articlesCount").value(1))
                 * .andExpect(jsonPath("$.articles[0].title").value("Effective Java"))
                 * .andExpect(jsonPath("$.articles[0].author.username").value("james"))
                 * .andExpect(jsonPath("$.articles[0].favorited").value(false))
                 * .andExpect(jsonPath("$.articles[0].favoritesCount").value(1))
                 * .andExpect(jsonPath("$.articles[0].tagList[0]").value("java"))
                 */
                .andDo(print());
    }

    @Test
    @DisplayName("provides an API that allows authenticated users to create roles.")
    void createRoles() throws Exception {
        // String jamesToken = returnTokenUser("asdf","asdf");
        // given
        RolesVO request = new RolesVO("RolT", "Test description", List.of("RecT1", "RecT2"));

        // when
        ResultActions resultActions = mockMvc.perform(post(Constants.API_ROOT_VERSION + Constants.API_ROLES)
                .header("Authorization", jamesToken)
                .content(objectMapper.writeValueAsString(Map.of("rol", request)))
                .contentType(MediaType.APPLICATION_JSON));
        String s = objectMapper.writeValueAsString(Map.of("rol", request));
        log.info("*************** {}", s);
        resultActions.andDo(print());
        log.info("***************");
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rol.codrol").value("RolT"))
                .andExpect(jsonPath("$.rol.descripcion").value("Test description"))
                .andExpect(jsonPath("$.rol.permisosList")
                        .value(new JSONArray().appendElement("RecT1").appendElement("RecT2")))
                // .andExpect(jsonPath("$.article.author.username").value("james"))
                .andDo(print());
    }

    @Test
    @DisplayName("provides an API that allows authenticated users to edit articles.")
    public void updateRol() throws Exception {
        // given
        String jamesToken = returnTokenUser("asdf", "asdf");
        // - create a test article
        RolesVO createRequest = new RolesVO("Test1", "Test description", List.of("AMBIENTES"));
        String slug = createRequest.codrol();
        // - get the slug of the article
        ResultActions resultActions = mockMvc.perform(post(Constants.API_ROOT_VERSION + Constants.API_ROLES)
                .header("Authorization", "Token " + jamesToken)
                .content(objectMapper.writeValueAsString(Map.of("rol", createRequest)))
                .contentType(MediaType.APPLICATION_JSON));

        String s = objectMapper.writeValueAsString(Map.of("rol", createRequest));

        log.info("content post {}", s);
        // resultActions.andDo(print());

        String response = resultActions.andReturn()
                .getResponse()
                .getContentAsString();
        log.info("RESPONSE post::> {}", response);

        RolesVO rol = rolesService.getSingleRol("Test1");
        SingleRolResponse srol = new SingleRolResponse(rol);
        String json = new ObjectMapper().writeValueAsString(srol);
        log.info("json post create {}", json);

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        log.info("ANTES DE GET 1 ROL ");
        resultActions = mockMvc.perform(get(Constants.API_ROOT_VERSION + Constants.API_ROLES + "/{slug}", slug)
                .header("Authorization", "Token " + jamesToken));

        response = resultActions.andReturn()
                .getResponse()
                .getContentAsString();

        log.info("RESPONSE GET 1::> {}", response);

        resultActions.andExpect(status().isOk());

        // when
        // - update the article
        log.info("**** ANTES DE UPDATE ROL ");
        RolesVO updateRequest = new RolesVO("Test1", "Updated description",
                List.of("AMBIENTES", "PROVEEDORES", "ASIGNACIONES", "SOLICITUD DE MATERIALES"));

        resultActions = mockMvc
                .perform(put(Constants.API_ROOT_VERSION + Constants.API_ROLES + "/{slug}", slug)
                        .header("Authorization", "Token " + jamesToken)
                        .content(objectMapper.writeValueAsString(Map.of("rol", updateRequest)))
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                // .andExpect(jsonPath("$.rol.descripcion").value("Updated description"))
                /*
                 * .andExpect(jsonPath("$.article.title").value("Updated Title"))
                 * .andExpect(jsonPath("$.article.description").value("Updated description"))
                 * .andExpect(jsonPath("$.article.body").value("Updated body"))
                 */
                .andDo(print());
        log.info("ANTES DE GET ROL ");

        resultActions = mockMvc.perform(get(Constants.API_ROOT_VERSION + Constants.API_ROLES + "/{slug}", slug)
                .header("Authorization", "Token " + jamesToken));

        response = resultActions.andReturn()
                .getResponse()
                .getContentAsString();

        log.info("RESPONSE GET::> {}", response);

        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("provides an API that allows authenticated users to delete articles.")
    public void deleteRol() throws Exception {
        // given
        // - create a test article
        RolesVO createRequest = new RolesVO("Test Article", "Test description", List.of("test"));

        // - get the slug of the article
        String slug = JsonPath.parse(mockMvc.perform(post(Constants.API_ROOT_VERSION + Constants.API_ROLES)
                .header("Authorization", jamesToken)
                .content(objectMapper.writeValueAsString(Map.of("rol", createRequest)))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString())
                .read("$.rol.codrol");

        // when
        ResultActions resultActions = mockMvc
                .perform(delete(Constants.API_ROOT_VERSION + Constants.API_ROLES + "/{codrol}", slug)
                        .header("Authorization", jamesToken));

        // then
        resultActions.andExpect(status().isOk()).andDo(print());
    }

    public String returnTokenUser(String u, String p) throws Exception {
        LoginUserRequest loginRequest = new LoginUserRequest(u, p);

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
}
