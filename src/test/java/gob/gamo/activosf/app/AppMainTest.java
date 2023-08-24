package gob.gamo.activosf.app;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwaremill.realworld.IntegrationTest;

import gob.gamo.activosf.app.domain.entities.User;
import gob.gamo.activosf.app.dto.sec.LoginUserRequest;
import gob.gamo.activosf.app.dto.sec.RolesVO;
import gob.gamo.activosf.app.dto.sec.SignUpUserRequest;
import gob.gamo.activosf.app.repository.AfAlmacenRepository;
import gob.gamo.activosf.app.repository.sec.UserRepository;
import gob.gamo.activosf.app.services.AfAlmacenBl;
import gob.gamo.activosf.app.services.sec.RolesService;
import gob.gamo.activosf.app.services.sec.UserService;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@IntegrationTest
@DisplayName("Realworld Application")
public class AppMainTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AfAlmacenRepository afAlmacenRepository;
    @Autowired
    private AfAlmacenBl afAlmacenBl;
    @Test
    @DisplayName("spring container of Realworld Application is loaded.")
    void contextLoads() {
        log.info("en tests...");
        SignUpUserRequest signUpRequest = new SignUpUserRequest("test@example.com", "test",
                "password", "jhon doe test");
        User u = userService.signUp(signUpRequest);
        Long i = userRepository.count();
        System.out.println("aaaaaaaaaaaaaaaaaa: " + i);
    }

    @Test
    @DisplayName("provides membership registration API.")
    void signUp() throws Exception {
        // given
        // - sign up request
        int i = afAlmacenRepository.findAll().size();
        afAlmacenBl.findByPkAfAlmacen(0);
        log.info("aaaaaaaaaaaaa {}", i);
        SignUpUserRequest signUpRequest = new SignUpUserRequest("test@example.com", "test", "password",
                "test test");

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of("user", signUpRequest))));

        // then
        resultActions
                .andExpect(status().isTemporaryRedirect())
                .andExpect(view().name("redirect:/api/users/login"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute(
                        "user",
                        Map.of("user", new LoginUserRequest("test@example.com", "password"))))
                .andDo(print());
    }

    @Test
    @DisplayName("provides login function.")
    void login() {
        // given
        // - sign up
        try {
            log.info("userService {}", (userService == null));
            SignUpUserRequest signUpRequest = new SignUpUserRequest("test@example.com", "test",
                    "password", "jhon doe");
            User u = userService.signUp(signUpRequest);
            Long i = userRepository.count();
            log.info("ooooooooooo {}", u.toString());
            log.info("*********** {}", i);

            // - login request
            LoginUserRequest loginRequest = new LoginUserRequest("test", "password");

            // when
            ResultActions resultActions = mockMvc.perform(post("/api/users/login")
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
    void getRolesServ() {
        try {
            log.info("en get roles test");
/*             Integer i = userRepository.count();
            log.info("en get roles test {}", i);
            List<User> lu = userRepository.findAll();
            log.info("en get users {}", lu.size()); */
            List<RolesVO> l = rolesService.getRoles(null, null);
            log.info("en get roles test {}", l.size());
            l.forEach(r -> {
                log.info("rol {}", r.toString());
            });
        } catch (Exception e) {
            log.error("error :: " + e.getMessage(), e);
        }

    }
}
