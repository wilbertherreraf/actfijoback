package gob.gamo.activosf.app.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import lombok.extern.slf4j.Slf4j;

import gob.gamo.activosf.app.handlers.CustomeRequestLoggingFilter;

@Slf4j
public class CustomWebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        log.info("inicial on startup ...");
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.baeldung.web.log");
        container.addListener(new ContextLoaderListener(context));

        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        container
                .addFilter("customRequestLoggingFilter", CustomeRequestLoggingFilter.class)
                .addMappingForServletNames(null, false, "dispatcher");
    }
}
