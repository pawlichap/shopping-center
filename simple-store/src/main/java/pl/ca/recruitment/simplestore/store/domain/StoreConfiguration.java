package pl.ca.recruitment.simplestore.store.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.ca.recruitment.simplestore.customers.domain.persistence.CustomerRepository;
import pl.ca.recruitment.simplestore.external.ExternalApi;
import pl.ca.recruitment.simplestore.items.domain.persistence.ItemRepository;

@Configuration
class StoreConfiguration {
    @Bean
    StoreApi storeApi(StoreCommand storeCommand, ExternalApi externalShoppingRegistry) {
        return new StoreFacade(storeCommand, externalShoppingRegistry);
    }

    @Bean
    StoreCommand storeCommand(CustomerRepository customerRepository, ItemRepository itemRepository) {
        return new StoreCommand(customerRepository, itemRepository);
    }
}
