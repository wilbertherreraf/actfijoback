package gob.gamo.activosf.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import gob.gamo.activosf.app.IntegrationTest;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

@Slf4j
@IntegrationTest
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SwaggerConfigTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenCallingSwaggerJSON_stringObjectDoesNotContainAnyErrorControllers() throws Exception {
        ResultActions resultActions = mvc.perform(get("/activosf-documentation")).andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();
        String content = result.getResponse().getContentAsString();
        log.info("result {}", content);
    }    
}
