package edu.rorke.blog.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author Rorke
 */
@EnableJpaAuditing
@SpringBootApplication
public class BlogBackgroundManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogBackgroundManagementApplication.class, args);
    }

}
