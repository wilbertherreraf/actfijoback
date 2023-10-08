package gob.gamo.activosf.app.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import gob.gamo.activosf.app.IntegrationTest;
import gob.gamo.activosf.app.commons.Constants;
import gob.gamo.activosf.app.domain.entities.Roles;
import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.sec.LoginUserRequest;
import gob.gamo.activosf.app.dto.sec.RolesVO;
import gob.gamo.activosf.app.dto.sec.SignUpUserRequest;
import gob.gamo.activosf.app.dto.sec.UpdateUserRequest;
import gob.gamo.activosf.app.dto.sec.UserVO;
import gob.gamo.activosf.app.repository.sec.UserRepository;
import gob.gamo.activosf.app.security.SessionsSearcherService;
import gob.gamo.activosf.app.services.sec.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import com.google.gson.Gson;

import gob.gamo.activosf.app.config.AppProperties;
import gob.gamo.activosf.app.config.JwtTokenProvider;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@IntegrationTest
public class UserControllerTest {
    public static final String tkTestInv = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhc2RmIiwiZXhwIjoxNjkzNzE5MTY0LCJhdXRoIjoiVVNSX0FMTUFDRU5FUy5BU0lHTkFDSU9ORVMsVVNSX0FMTUFDRU5FUy5TT0xJQ0lUVUQgREUgTUFURVJJQUxFUyxSRVNQT05TQUJMRS5SRUNFUENJT04sUkVTUE9OU0FCTEUuSU5WRU5UQVJJTyBUT1RBTCBBRiBDSUVSUkUsUkVTUE9OU0FCTEUuUkVQT1JURSBVRlYsUkVTUE9OU0FCTEUuQVNJR05BQ0lPTkVTLFJFU1BPTlNBQkxFLlBBUlRJREFTIFBSRVNVUFVFU1RBUklBUyxSRVNQT05TQUJMRS5DT0RJR09TIENPTlRBQkxFUyxSRVNQT05TQUJMRS5HRVNUSU9OIEFGLFJFU1BPTlNBQkxFLkFTSUdOQUNJT04gQUYsUkVTUE9OU0FCTEUuQlVTQ0FET1IsUkVTUE9OU0FCTEUuU1VCLUZBTUlMSUFTIEFGLFJFU1BPTlNBQkxFLlBST1ZFRURPUkVTLFJFU1BPTlNBQkxFLkZBTUlMSUFTIEFGLFJFU1BPTlNBQkxFLkFNQklFTlRFUyIsImlhdCI6MTY5MzcxODI2NH0.TNMpjsQGpap-prSZWtmNFu01zE-lOcvsqAPLyTnIV_t1oBto3HDlmktIbK8COqluoB1QDJkp1dIV054xVaGtu07E89ruVxYa_JJ4PBopCoARkNj2g-De0RzGnEh8iuVmWv39cm6WLicydsQqmXg9kkCWN64DoDeWLprhUPFQbRBePqz6Aefn9erZEc2enhEIiL2zgeQvIP5XrU3xGXT5CaY_27_q8GQ31cuVNkWjhpDSK05Si1QKbC0QUAIqremFTzctCZ-NpwKv6yPLPcLWljYCkl-Y3BR4sP-ItyImE3IyEho67yGJ6ktalm4X1sQJ1AsH7rBFzJ7oH1h6uDTFUA";
    public static final String tkTest = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhc2RmIiwiYXJ0YWYiOiIzMmQ3YTA3MDliMDg0OTMwYjhkODE5ZjRiYjEyZThhYSIsImV4cCI6MTY5MzgyMzYzMiwiYXV0aCI6IlVTUl9BTE1BQ0VORVMuQVNJR05BQ0lPTkVTLFVTUl9BTE1BQ0VORVMuU09MSUNJVFVEIERFIE1BVEVSSUFMRVMsUkVTUE9OU0FCTEUuUkVDRVBDSU9OLFJFU1BPTlNBQkxFLklOVkVOVEFSSU8gVE9UQUwgQUYgQ0lFUlJFLFJFU1BPTlNBQkxFLlJFUE9SVEUgVUZWLFJFU1BPTlNBQkxFLkFTSUdOQUNJT05FUyxSRVNQT05TQUJMRS5QQVJUSURBUyBQUkVTVVBVRVNUQVJJQVMsUkVTUE9OU0FCTEUuQ09ESUdPUyBDT05UQUJMRVMsUkVTUE9OU0FCTEUuR0VTVElPTiBBRixSRVNQT05TQUJMRS5BU0lHTkFDSU9OIEFGLFJFU1BPTlNBQkxFLkJVU0NBRE9SLFJFU1BPTlNBQkxFLlNVQi1GQU1JTElBUyBBRixSRVNQT05TQUJMRS5QUk9WRUVET1JFUyxSRVNQT05TQUJMRS5GQU1JTElBUyBBRixSRVNQT05TQUJMRS5BTUJJRU5URVMiLCJpYXQiOjE2OTM4MjI3MzJ9.YeKh9ejY02aDfNRzZ0bGyDDdEqQ70QziBnfkh9vXJaiVr7bvz3vy5xvwFHUZLjFTFGOdliPkVbqNJZI7Y4umcVdSGNpwepaUKJxZRnYrQ4u8U77ntM3C59Qb_B6Q4huXDl__bKlmSDGxHCXhYrUIlZevFvVmvuXA-ZHkOvy30iOXi4euXeqJCBugw4v4V2hbtyqIYecgExZHQypw5x3iNeSSGtIct2MoAD6m1gzrgI9sALh8SajWaAUIhmxtrIJiGzFD8fLvOgRR4yknHtd3mlAFEiOyS_CvqycME00FUCFkhmrJm3w8-EGiG6rMNdEohwNtyHYFNMF70-q2noAxcg";
    public String urlReq = Constants.API_URL_ROOT + Constants.API_URL_VERSION;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SessionsSearcherService sessionsSearcherService;
    @Autowired
    private JwtTokenProvider JwtTokenProvider;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    private ObjectMapper mapper;
    @Autowired
    private AppProperties appProperties;

    @BeforeEach
    void setup() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void dataRepo() {
        try {
            log.info("inicio test users");
            List<User> list = userService.getusers();
            list.stream().forEach(r -> {
                try {
                    String rjson = mapper.writeValueAsString(r);
                    log.info("rjson {} ", rjson);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                /*
                 * String roles = r.getRoles().stream().map(rec ->
                 * rec.getCodrol()).collect(Collectors.joining(","));
                 * log.info("rec {} roles{}", r.getUsername(), roles);
                 */

            });
            // mapper.writeValueAsString(List.of(userDto));
            String rjson = mapper.writeValueAsString(list);
            log.info("result: {}", rjson);
        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("provides login API.")
    void login() throws Exception {
        // given
        // - sign up
        SignUpUserRequest signUpRequest = new SignUpUserRequest(null, "james@example.com", "james@example.com",
                "password",
                "james", null, null);
        userService.signUp(signUpRequest);

        // - login request
        LoginUserRequest loginRequest = new LoginUserRequest("james@example.com", "password");

        // when
        ResultActions resultActions = mockMvc.perform(post(Constants.API_ROOT_VERSION + Constants.API_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of("user", loginRequest))));

        MvcResult retTest = resultActions.andReturn();
        String strRes = retTest.getResponse().getContentAsString();

        JsonNode rootNode = mapper.readTree(strRes);
        log.info("roooooooot {} {} {}", rootNode.asText(), rootNode.textValue(), rootNode.size());
        rootNode.forEach(n -> {
            log.info("node j {}", n.toString());
            try {
                Map<String, Object> map = objectMapper.readValue(n.toString(),
                        new TypeReference<Map<String, Object>>() {
                        });
                log.info("User vo {}", map.toString());
            } catch (Exception e) {
                log.error("error " + e.getMessage(), e);
            }
        });
        JsonParser jsonParser = new JsonFactory().createParser(strRes);

        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String name = jsonParser.getCurrentName();
            log.info("parser {}", name);
            if ("user".equals(name)) {
                jsonParser.nextToken();
                String userNode = jsonParser.getText();
                log.info("parser user node {}", userNode);
            }
        }

        /*
         * JsonNode jsonNode = objectMapper.readValue(strRes, JsonNode.class);
         * JsonNode nodeUser = jsonNode.get("user");
         * String userJson = nodeUser.asText();
         * log.info("___returl11111111 {}", strRes, userJson);
         * UserVO userVO = objectMapper.readValue(userJson, UserVO.class);
         * log.info("___returl userVO {}", userVO);
         * // then
         * resultActions
         * .andExpect(status().isCreated())
         * .andExpect(content().contentType(MediaType.APPLICATION_JSON))
         * .andExpect(jsonPath("$.user.email").value("james@example.com"))
         * .andExpect(jsonPath("$.user.username").value("james@example.com"))
         * .andExpect(jsonPath("$.user.token").isNotEmpty())
         * // .andExpect(jsonPath("$.user.bio").isEmpty())
         * // .andExpect(jsonPath("$.user.image").isEmpty())
         * .andDo(print());
         * log.info("___returl userVO 222222222{}", userVO);
         */
    }

    @Test
    @DisplayName("provides logged-in user information.")
    void getCurrentUser() throws Exception {
        // given
        String username = "asdf";
        String jamesToken = returnTokenUser(username, "asdf");
        // when
        ResultActions resultActions = mockMvc.perform(
                get(Constants.API_ROOT_VERSION + Constants.API_USUARIOS + "/" + username)
                        .param("pageCode", "USR_ALMACENES").header("Authorization", "Token " + jamesToken));
        String strRet = resultActions.andReturn().getResponse().getContentAsString();
        log.info("return rest {}", strRet);
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // .andExpect(jsonPath("$.user.email").value("james@example.com"))
                // .andExpect(jsonPath("$.user.username").value("james@example.com"))
                .andExpect(jsonPath("$.user.token").isNotEmpty())
                // .andExpect(jsonPath("$.user.bio").isEmpty())
                // .andExpect(jsonPath("$.user.image").isEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("provides logged-in user information.")
    void getRefreshToken() throws Exception {
        // given
        String jamesToken = returnTokenUser("asdf", "asdf");
        // String jamesToken = tkTest;
        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/user/refreshtoken").header("Authorization", "Token " + jamesToken));
        String strRet = resultActions.andReturn().getResponse().getContentAsString();
        log.info("return rest {}", strRet);
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user.token").isNotEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("provides logged-in user information.")
    void refreshTokenExpiredInvalid() throws Exception {
        appProperties.getSecurity().getAuthentication().getJwt().setTokenValidityInSeconds(3);
        sessionsSearcherService.initCache(5, TimeUnit.SECONDS);
        // given
        String jamesToken = returnTokenUser("asdf", "asdf");
        // String jamesToken = tkTest;
        // when
        ResultActions resultActions = mockMvc.perform(
                get(urlReq + Constants.API_UNIDS).header("Authorization", "Token " + jamesToken));
        resultActions
                .andExpect(status().isOk())
                .andDo(print());

        log.info("antes de sleep *****************");
        Thread.sleep(1000);

        resultActions = mockMvc.perform(
                get(urlReq + Constants.API_UNIDS).header("Authorization", "Token " + jamesToken));
        resultActions
                .andExpect(status().isProxyAuthenticationRequired())
                .andDo(print());

        log.info("antes de refresh *****************");
        resultActions = mockMvc.perform(
                post("/api/user/refreshtoken").header("Authorization", "Token " + jamesToken));
        String strRet = resultActions.andReturn().getResponse().getContentAsString();
        log.info("return rest {}", strRet);
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // .andExpect(jsonPath("$.user.token").isNotEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("provides logged-in user information.")
    void revalidRefreshTokenInvalid() throws Exception {
        String jamesToken = tkTest;
        sessionsSearcherService.initCache(120, TimeUnit.HOURS);
        String refreshToken = JwtTokenProvider.getClaim(jamesToken, Constants.SEC_HEADER_TOKEN_REFRESH);
        String subject = JwtTokenProvider.getClaim(jamesToken, "sub");
        UserVO userVO = new UserVO(null, subject, "", refreshToken, "", "", null, new ArrayList<>());

        log.info("vooooooooooo refreshToken{} subject {} userVO {}", refreshToken, subject, userVO.toString());
        Gson gson = new Gson();
        sessionsSearcherService.createSession(refreshToken, gson.toJson(userVO));

        // given
        // when
        ResultActions resultActions = mockMvc.perform(
                get(urlReq + Constants.API_UNIDS).header("Authorization", "Token " + jamesToken));

        log.info("return rest Header *****************");
        for (String hdr : resultActions.andReturn().getResponse().getHeaderNames()) {
            String valHdr = resultActions.andReturn().getResponse().getHeader(hdr);
            log.info("Header {} -> {}", hdr, valHdr);
        }
        String strRet = resultActions.andReturn().getResponse().getContentAsString();
        log.info("return rest {} status {}", strRet, resultActions.andReturn().getResponse().getStatus());

        resultActions
                .andExpect(status().isProxyAuthenticationRequired())
                .andDo(print());

        log.info("********** NUEW CALL REFFRESH ******************");
        resultActions = mockMvc.perform(
                post("/api/user/refreshtoken").header("Authorization", "Token " + jamesToken));
        strRet = resultActions.andReturn().getResponse().getContentAsString();
        log.info("return rest {}", strRet);
        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user.token").isNotEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("provides login function.")
    void loginNew() {
        // given
        // - sign up
        try {
            log.info("userService {}", (userService == null));
            Optional<User> u = userRepository.findById(17);
            Long i = userRepository.count();
            log.info("ooooooooooo {}", u.get().toString());
            log.info("*********** {}", i);

            // - login request
            LoginUserRequest loginRequest = new LoginUserRequest("asdf", "asdf");

            // when
            ResultActions resultActions = mockMvc.perform(post(Constants.API_ROOT_VERSION + Constants.API_LOGIN)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Map.of("user", loginRequest))));
            // then
            log.info("***********");
            resultActions.andDo(print());
            log.info("***********");

            resultActions
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    // .andExpect(jsonPath("$.user.email").value("test@example.com"))
                    .andExpect(jsonPath("$.user.username").value("asdf"))
                    .andExpect(jsonPath("$.user.token").isNotEmpty())
                    // .andExpect(jsonPath("$.user.bio").isEmpty())
                    // .andExpect(jsonPath("$.user.image").isEmpty())
                    .andDo(print());

            /*
             * LoginUserRequest jamesLoginRequest = new LoginUserRequest("asdf", "asdf");
             * jamesToken = "Token " + userService.login(jamesLoginRequest).token();
             * log.info("*********** TK: {}", jamesToken);
             * 
             * resultActions = mockMvc.perform(post("/api/auth/roles")
             * .header("Authorization", jamesToken)
             * //.content(objectMapper.writeValueAsString(Map.of("rol", request)))
             * .contentType(MediaType.APPLICATION_JSON));
             * log.info(".................");
             * resultActions.andDo(print());
             * log.info("....................");
             * // then
             * resultActions
             * .andExpect(status().isOk())
             * // .andExpect(jsonPath("$.article.author.username").value("james"))
             * .andDo(print());
             */

        } catch (Exception e) {
            log.info("error:{}", e.getMessage());
        }
    }

    @Test
    @DisplayName("provides login function.")
    void registerAndLogin() {
        // given
        // - sign up
        try {
            log.info("userService {}", (userService == null));
            SignUpUserRequest signUpRequest = new SignUpUserRequest(null, "test@example.com", "test",
                    "password", "jhon doe", null, null);
            User u = userService.signUp(signUpRequest);
            Long i = userRepository.count();
            log.info("ooooooooooo {}", u.toString());
            log.info("*********** {}", i);

            // - login request
            LoginUserRequest loginRequest = new LoginUserRequest("test", "password");

            // when
            ResultActions resultActions = mockMvc.perform(post(Constants.API_ROOT_VERSION + Constants.API_LOGIN)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Map.of("user", loginRequest))));
            // then
            resultActions
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.user.email").value("test@example.com"))
                    .andExpect(jsonPath("$.user.username").value("test"))
                    .andExpect(jsonPath("$.user.token").isNotEmpty())
                    // .andExpect(jsonPath("$.user.bio").isEmpty())
                    // .andExpect(jsonPath("$.user.image").isEmpty())
                    .andDo(print());

        } catch (Exception e) {
            log.info("error:{}", e.getMessage());
        }
    }

    @Test
    @DisplayName("provides user information update API.")
    void update() throws Exception {
        // given
        // - sign up
        SignUpUserRequest signUpRequest = new SignUpUserRequest(null, "test@example.com", "test",
                "password", "jhon doe", null, null);
        ;
        User u = userService.signUp(signUpRequest);
        log.info("User new [{}] {} roles :> {}", u.getId(), u.getUsername(), u.getRoles());
        // - login and get authorization token
        String tokenLogin = returnTokenUser("asdf", "asdf");
        Optional<User> userReq = userRepository.findByUsername("asdf");
        log.info("User REQUEST [{}] {} roles :> {}", userReq.get().getId(), userReq.get().getUsername(),
                userReq.get().getRoles().stream()
                .map(Roles::getCodrol).collect(Collectors.toList()).toString()
                );
        // - update request
        String email = u.getEmail();
        String username = u.getUsername();
        String password = signUpRequest.password();

        List<RolesVO> roles = Arrays.asList(new RolesVO("RESPONSABLE", "ASIGNACIONESXXX", new ArrayList<>()));
        UpdateUserRequest updateRequest = new UpdateUserRequest(email, username, password, "", "", null, roles);
        // when
        ResultActions resultActions = mockMvc
                .perform(put(Constants.API_ROOT_VERSION + Constants.API_USUARIOS + "/" + username + "/"
                        + Constants.API_ROLES)
                        .header("Authorization", "Token " + tokenLogin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("user", updateRequest))));

        // then
        MvcResult retTest = resultActions.andReturn();
        String strRes = retTest.getResponse().getContentAsString();
        log.info("___returl {}", strRes);

        Optional<User> user = userRepository.findById(u.getId());
        log.info("Roles user {} {}", user.get().getId(), user.get().getRoles());

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user.email").value(u.getEmail()))
                .andExpect(jsonPath("$.user.username").value(u.getUsername()))
                .andDo(print());

        Gson gson = new Gson();
        UserResponse st = gson.fromJson(strRes, UserResponse.class);
        log.info("user response {}", st.getUser().roles());
        assertThat(st.getUser().roles().size()).isEqualTo(roles.size());
    }

    public String returnToken() throws Exception {
        SignUpUserRequest signUpRequest = new SignUpUserRequest(null, "james@example.com", "james@example.com",
                "password",
                "james", null, null);
        userService.signUp(signUpRequest);

        LoginUserRequest loginRequest = new LoginUserRequest("james@example.com", "password");

        ResultActions resultActions = mockMvc.perform(post(Constants.API_ROOT_VERSION + Constants.API_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of("user", loginRequest))));

        MvcResult retTest = resultActions.andReturn();
        String strRes = retTest.getResponse().getContentAsString();
        log.info("___returl {}", strRes);

        Gson gson = new Gson();
        Map<?, ?> st = gson.fromJson(strRes, Map.class);
        log.info("user map {}", st.toString());
        UserVO user = gson.fromJson(st.get("user").toString(), UserVO.class);

        return user.token();
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

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class UserResponse {
        private UserVO user;
    }

}
