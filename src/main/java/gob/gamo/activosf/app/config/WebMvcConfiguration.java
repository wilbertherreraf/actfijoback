package gob.gamo.activosf.app.config;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.Module;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.repository.sec.UserRepository;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final UserRepository userRepository;

    @Bean
    public PageRequest defaultPageRequest() {
        return PageRequest.of(0, 100);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserArgumentResolver(userRepository));
        resolvers.add(pageableResolvers(resolvers));
        log.info("resolver arg {}", resolvers.size());
    }

    /*
     * @Override
     * public void addFormatters(FormatterRegistry registry) {
     * DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
     * registrar.setUseIsoFormat(true);
     * registrar.registerFormatters(registry);
     * }
     */
    @Override
    public void configureMessageConverters(final List<HttpMessageConverter<?>> messageConverters) {
        log.info("resolver arg xcccccccccc{}");
        final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.modules(iso8601SerializeModule())
                .featuresToEnable(DeserializationFeature.UNWRAP_ROOT_VALUE)
                .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        builder.indentOutput(true)
                .dateFormat(new SimpleDateFormat("dd-MM-yyyy hh:mm"));

        messageConverters.add(new MappingJackson2HttpMessageConverter(builder.build()));

        // builder.indentOutput(true).dateFormat(new SimpleDateFormat("dd-MM-yyyy
        // hh:mm"));

    }

    protected HandlerMethodArgumentResolver pageableResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        SortHandlerMethodArgumentResolver argumentResolver = new SortHandlerMethodArgumentResolver();
        argumentResolver.setSortParameter("sort");
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver(argumentResolver);
        resolver.setFallbackPageable(defaultPageRequest());
        resolver.setPageParameterName("page");
        resolver.setSizeParameterName("size");
        return resolver;
    }

    private Module iso8601SerializeModule() {
        return new JavaTimeModule().addSerializer(LocalDateTime.class, new JsonSerializer<>() {
            @Override
            public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
                    throws IOException {
                String formattedDateTime = value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
                log.info("en mapppper {} -> {}", value, formattedDateTime);
                gen.writeString(formattedDateTime);
            }
        });
    }
}
