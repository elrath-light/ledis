package me.ledis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(LedisApplication.class, args);
    }
}