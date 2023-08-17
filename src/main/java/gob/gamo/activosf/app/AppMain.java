package gob.gamo.activosf.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.softwaremill.realworld.application.user.controller.UserController;

@SpringBootApplication

public class AppMain {
    public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);
    }    
}
