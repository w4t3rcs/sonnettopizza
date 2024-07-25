package org.sonnetto.dish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class DishServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DishServiceApplication.class, args);
    }
}
