package io.pivotal.pal.tracker.restsupport;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.EurekaClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestConfig {

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }

    @ConditionalOnProperty(
            value = {"eureka.client.enabled"},
            matchIfMissing = true
    )
    @Bean
    ServiceLocator getServiceLocator(EurekaClient eurekaClient) {
        return new EurekaServiceLocator(eurekaClient);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}
