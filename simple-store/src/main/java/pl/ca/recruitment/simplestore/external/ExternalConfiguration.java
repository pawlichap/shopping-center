package pl.ca.recruitment.simplestore.external;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class ExternalConfiguration {
    @Bean
    ExternalApi externalShoppingRegistry(RestTemplate restTemplate) {
        return new ExternalShoppingRegistry("http://localhost:8081/api/notification/purchase", restTemplate);
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
}
