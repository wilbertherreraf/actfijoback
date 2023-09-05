package gob.gamo.activosf.app.config;

import java.util.Arrays;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
// @ComponentScan
@EnableAutoConfiguration
// @PropertySource(value = "file:" + Constants.BASE_DIR_APP +
// Constants.FILE_APPLICATION)
// @EnableConfigurationProperties({ AppProperties.class,
// LavadoProperties.class})
@EnableConfigurationProperties({AppProperties.class})
public class ConfigApp {
    @Autowired
    private Environment env;

    @Autowired
    private AppProperties appProperties;

    @PostConstruct
    public void initApplication() throws Throwable {
        log.info("------- Params Service Service -------");
        log.info("server.port : " + env.getProperty("server.port"));
        log.info("server.address: " + env.getProperty("server.address"));
        log.info("server.contextPath: " + env.getProperty("server.contextPath"));
        log.info("spring.application.name: " + env.getProperty("spring.application.name"));
        log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
        log.info("Running with spring.config.location: " + env.getProperty("spring.config.location"));
        log.info("Running with base64-secret: " + env.getProperty("activosf.jwt.sessionTimeout"));
        appProperties
                .getSecurity()
                .getAuthentication()
                .getJwt()
                .setBase64Secret(env.getProperty("security.authentication.jwt.base64-secret"));
        Integer timeout = Integer.valueOf(env.getProperty("activosf.jwt.sessionTimeout"));
        appProperties.getSecurity().getAuthentication().getJwt().setTokenValidityInSeconds(timeout);
        createDirs();
    }

    /**
     * Creacion de directorios de la aplicacion
     */
    private void createDirs() {
        String pathBase = env.getProperty("spring.config.location");
        /*
         * new File(pathBase + Constants.DIR_FILES_STORE).mkdirs();
         * new File(pathBase + Constants.DIR_LOTES).mkdirs();
         * new File(pathBase + Constants.DIR_OFAC_FILES).mkdirs();
         * new File(pathBase + Constants.DIR_OFAC_FILES_TMP).mkdirs();
         * new File(pathBase + Constants.DIR_OFAC_FILES_DESCARGA).mkdirs();
         * new File(pathBase + Constants.DIR_QUERIES).mkdirs();
         */
    }
}
