package io.pivotal.pal.tracker.timesheets;

import io.pivotal.pal.tracker.restsupport.ServiceLocator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimesheetsAppConfig {
    @ConditionalOnMissingBean(name = "eurekaClient")
    @Bean
    ServiceLocator getServiceLocator() {
        return serviceName -> "/";
    }
}
