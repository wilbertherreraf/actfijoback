package gob.gamo.activosf.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AppMain {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(AppMain.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");
    }
}
