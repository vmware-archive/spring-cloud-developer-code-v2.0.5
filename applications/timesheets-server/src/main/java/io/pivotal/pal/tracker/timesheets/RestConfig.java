package io.pivotal.pal.tracker.timesheets;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;


@Configuration
public class RestConfig {

    @Bean
    RestOperations restOperations(
            @Value("${project.client.connect.timeout.ms}") int connectTimeout,
            @Value("${project.client.read.timeout.ms}") int readTimeout
    ) {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.of(connectTimeout, ChronoUnit.MILLIS))
                .setReadTimeout(Duration.of(readTimeout, ChronoUnit.MILLIS))
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}
