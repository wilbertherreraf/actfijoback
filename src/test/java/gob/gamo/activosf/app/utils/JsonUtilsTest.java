package gob.gamo.activosf.app.utils;

import java.util.Map.Entry;

import org.junit.jupiter.api.Test;
import com.google.gson.Gson;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;

import gob.gamo.activosf.app.dto.sec.UserVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtilsTest {
    @Test
    public void readJson() {
            log.info("hola mundo");        
        String json = "{\"username\":\"james@example.com\",\"email\":\"james@example.com\",\"token\":\"eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3JlYWx3b3JsZC5pbyIsInN1YiI6IjU0IiwiZXhwIjoxNjkzMjY2NTkyLCJpYXQiOjE2OTMyNjYyOTJ9.eTPO9p0Zq8ZDDNzRAVWc2F4gioPsKZtoMFVDGAsuNYMwRJAGNk2XXe1qpIdTNNv1g2YZplNpwPUhHb1HQKv4GNv2Hk6eF6NYtz4Tm_xadnrWUx0GFApRNHx2sg_CDdM4zCypzXi90-3MG-O2hCjS5YNYFVCE4NwFTb1Bz20pjAOHexLsXz25OpiXgUJqkOvyY7trGr8hQLjK3zx9mDpz5w3cuGlkjLsvusqf5ssht3qjhaSMWFjNvPDgA9vC-kqOOTWq-trkWJiW2CK2KCBYAmpLND9MuCXH1MD0ugowZsDUq13yG1wAmejjTvytndYWGidyMg0rwlHAve4pJ-4Lfw\",\"codempleado\":null,\"nombres\":\"james\"}";
        json = "{\"user\":{\"username\":\"james@example.com\",\"email\":\"james@example.com\",\"token\":\"eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3JlYWx3b3JsZC5pbyIsInN1YiI6IjM1IiwiZXhwIjoxNjkzMjU4NzM2LCJpYXQiOjE2OTMyNTg0MzZ9.Ciy_ws45ceoha1EeUujMRca56s_GP26KOWotyg_P_Y68OvPJDuqf4AgRgSDaK8Gv7LkKkl2aLYu-azmG4-hxUVfkef99WUXg8KB7itM-i8S93zQzyjYdd7MO25uJrKlnSa7-omfh8EdNxC0bhshBvaR05YZ4_Hid-2UyKww9iKIKe6vctNK84gAZ4UJ7cg_S1hD5qHA4F2Ofgc1F6dZe_v0O1Kpsvvm2zM8UYpc8h7c7CoH-fBmuSfMVTfqEI27b76jkLsLv-JaP9hfrYuMxQbx_jOBsg3M7NWeOAV0x_U3mXtYODRquv6YOPpyPOYxfJMZ5bZuw_xA-2Kly6im4qQ\",\"codempleado\":null,\"nombres\":\"james\"}}";
        json = "{'user':{'username':'asdf','email':'asdf@asdf.com','token':'eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJodHRwczovL3JlYWx3b3JsZC5pbyIsInN1YiI6IjIzIiwiZXhwIjoxNjkzMjc4OTg1LCJpYXQiOjE2OTMyNzg5ODB9.F75np1CtRd61sR9_9pooGXSznTMx-XiFcjdK30HYVGJBu_zSUJVWTUv4lFbHM5EcEKT3LgJZpp7xl3paZRB4HUNyvJzHJbfWqWGrAelPXz3OqrEcdm82PUwUgvLCEtOQgCz7KZExRJcxsETOf3pX_gbScYGYbQTwwja0fnJyd2RlbULAv4l7aSe36hRFDHfwOLaw3TApfGouNIjLBXnuXvzGYEKkbIgxyCsNeY9cxN7hRWD2DX9FRCMT7TSYEV03zZvUfwJfv_Y8ZbFYE_EgWa1Y_4pnmaSLsRTJNI5ewBrR__yz7fyCsGVOVFxpRWAsoZJWbUgwA8Xi_spo3tBQ_w','codempleado':null,'nombres':'asdf2 zzzz'}}";
        try {
            Gson gson = new Gson();
            Usuario1 usuario1 = gson.fromJson(json, Usuario1.class);
            log.info("user usuario1 {}", usuario1.getUser().toString());
            /*
             * JsonReader reader = new JsonReader(new StringReader(json));
             * reader.setLenient(true);
             * 
             * Map<?, ?> st = gson.fromJson(reader, Map.class);
             * log.info("user map {}", st.toString());
             * 
             * LinkedTreeMap tm = (LinkedTreeMap) st.get("user");
             * log.info("user Tee map {}", tm.toString());
             * UserVO user = gson.fromJson(st.get("user").toString(), UserVO.class);
             * // UserVO user = objectMapper.reader().forType(UserVO.class).readValue(json);
             * log.info("user {}", user);
             */
        } catch (Exception e) {
            log.error("error " + e.getMessage(), e);
        }
    }

    @Test
    public void parseJwt(){
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhc2RmIiwiZXhwIjoxNjkzNzE5MTY0LCJhdXRoIjoiVVNSX0FMTUFDRU5FUy5BU0lHTkFDSU9ORVMsVVNSX0FMTUFDRU5FUy5TT0xJQ0lUVUQgREUgTUFURVJJQUxFUyxSRVNQT05TQUJMRS5SRUNFUENJT04sUkVTUE9OU0FCTEUuSU5WRU5UQVJJTyBUT1RBTCBBRiBDSUVSUkUsUkVTUE9OU0FCTEUuUkVQT1JURSBVRlYsUkVTUE9OU0FCTEUuQVNJR05BQ0lPTkVTLFJFU1BPTlNBQkxFLlBBUlRJREFTIFBSRVNVUFVFU1RBUklBUyxSRVNQT05TQUJMRS5DT0RJR09TIENPTlRBQkxFUyxSRVNQT05TQUJMRS5HRVNUSU9OIEFGLFJFU1BPTlNBQkxFLkFTSUdOQUNJT04gQUYsUkVTUE9OU0FCTEUuQlVTQ0FET1IsUkVTUE9OU0FCTEUuU1VCLUZBTUlMSUFTIEFGLFJFU1BPTlNBQkxFLlBST1ZFRURPUkVTLFJFU1BPTlNBQkxFLkZBTUlMSUFTIEFGLFJFU1BPTlNBQkxFLkFNQklFTlRFUyIsImlhdCI6MTY5MzcxODI2NH0.TNMpjsQGpap-prSZWtmNFu01zE-lOcvsqAPLyTnIV_t1oBto3HDlmktIbK8COqluoB1QDJkp1dIV054xVaGtu07E89ruVxYa_JJ4PBopCoARkNj2g-De0RzGnEh8iuVmWv39cm6WLicydsQqmXg9kkCWN64DoDeWLprhUPFQbRBePqz6Aefn9erZEc2enhEIiL2zgeQvIP5XrU3xGXT5CaY_27_q8GQ31cuVNkWjhpDSK05Si1QKbC0QUAIqremFTzctCZ-NpwKv6yPLPcLWljYCkl-Y3BR4sP-ItyImE3IyEho67yGJ6ktalm4X1sQJ1AsH7rBFzJ7oH1h6uDTFUA";
        token = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhc2RmIiwiZXhwIjoxNjkzNzgyNDA3LCJhdXRoIjoiVVNSX0FMTUFDRU5FUy5BU0lHTkFDSU9ORVMsVVNSX0FMTUFDRU5FUy5TT0xJQ0lUVUQgREUgTUFURVJJQUxFUyxSRVNQT05TQUJMRS5SRUNFUENJT04sUkVTUE9OU0FCTEUuSU5WRU5UQVJJTyBUT1RBTCBBRiBDSUVSUkUsUkVTUE9OU0FCTEUuUkVQT1JURSBVRlYsUkVTUE9OU0FCTEUuQVNJR05BQ0lPTkVTLFJFU1BPTlNBQkxFLlBBUlRJREFTIFBSRVNVUFVFU1RBUklBUyxSRVNQT05TQUJMRS5DT0RJR09TIENPTlRBQkxFUyxSRVNQT05TQUJMRS5HRVNUSU9OIEFGLFJFU1BPTlNBQkxFLkFTSUdOQUNJT04gQUYsUkVTUE9OU0FCTEUuQlVTQ0FET1IsUkVTUE9OU0FCTEUuU1VCLUZBTUlMSUFTIEFGLFJFU1BPTlNBQkxFLlBST1ZFRURPUkVTLFJFU1BPTlNBQkxFLkZBTUlMSUFTIEFGLFJFU1BPTlNBQkxFLkFNQklFTlRFUyIsImlhdCI6MTY5Mzc4MTUwNywiWC1BQ1RJVk9TRi1SRUZSRVNILVRPS0VOIjoiNTc5NDY3MGY4OGY4NDhkNWJhNzNiOWUyNWQzMDY4YjEifQ.S5EZHItR_MB2NbrgF_BJ_FiU5Z1QP-vk0Z-j7VVwlBjR7v2VMP-daOJQtFXcsKa5zQufSb7Pxi-RqPAl-YpicMYMS1cXnuNi56eRiUSJvjPY25RtEsKjdUDXztG-WnRJ0bOEt29g6m0AHQ_Pmm0jt7eKRQElwDzRHeXStiS4O1O9iozNYLaxoFeyqoClkSJ5Ttvq-hW2Z7l1aSgeM-Tnjx9tCEHFphPy8YLIdfi_Hs3MYyI3OapCgOGk6rgmutj1gjhvEIH9k95Ef0_CI_EE4UlUcoPTEBvfRODaaF3VBvhNXP51zyA7Eg9xEfeou5N44Eo6wMqCyEGdRsRXoDBeMA";
        try {
            JWT parse = JWTParser.parse(token);
            log.info("parse {}", parse.getJWTClaimsSet());
            for (Entry<String, Object> entry : parse.getJWTClaimsSet().getClaims().entrySet()){
                log.info("key: {} -> {}", entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            log.error("error " + e.getMessage(), e);
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public class Usuario1 {
        private UserVO user;
    }


}
