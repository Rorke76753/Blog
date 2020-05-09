package edu.rorke.blog.backend.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Rorke
 * @date 2020/5/8 22:29
 */
@Configuration
public class SerializeConfig {
    @Bean
    public LocalDateTimeSerializer localDateTimeSerializer(){
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Bean
    public LocalDateSerializer localDateSerializer(){
        return new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder(){
        return Jackson2ObjectMapperBuilder.json()
                .serializerByType(LocalDateTime.class,localDateTimeSerializer())
                .serializerByType(LocalDate.class,localDateSerializer());
    }

}

