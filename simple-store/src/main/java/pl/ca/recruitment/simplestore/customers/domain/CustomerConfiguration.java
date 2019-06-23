package pl.ca.recruitment.simplestore.customers.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.ca.recruitment.simplestore.customers.domain.persistence.CustomerRepository;

@Configuration
class CustomerConfiguration {
    @Bean
    CustomerApi customerApi(CustomerCommand command, CustomerQuery query) {
        return new CustomerFacade(command, query);
    }

    @Bean
    CustomerCommand customerCommand(CustomerRepository customerRepository) {
        return new CustomerCommand(customerRepository);
    }

    @Bean
    CustomerQuery customerQuery(CustomerRepository customerRepository) {
        return new CustomerQuery(customerRepository);
    }
}
