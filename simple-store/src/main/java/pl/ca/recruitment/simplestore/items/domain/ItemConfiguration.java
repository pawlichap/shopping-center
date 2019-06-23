package pl.ca.recruitment.simplestore.items.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.ca.recruitment.simplestore.items.domain.persistence.ItemRepository;

@Configuration
class ItemConfiguration {
    @Bean
    ItemApi itemApi(ItemQuery query, ItemCommand command) {
        return new ItemFacade(query, command);
    }

    @Bean
    ItemQuery itemQuery(ItemRepository itemRepository) {
        return new ItemQuery(itemRepository);
    }

    @Bean
    ItemCommand itemCommand(ItemRepository itemRepository) {
        return new ItemCommand(itemRepository);
    }
}
