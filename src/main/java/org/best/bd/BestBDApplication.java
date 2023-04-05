package org.best.bd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class BestBDApplication {
    public static void main(String[] args) {
        SpringApplication.run(BestBDApplication.class, args);
        System.out.println("http://127.0.0.1:8080/test");
    }
}
