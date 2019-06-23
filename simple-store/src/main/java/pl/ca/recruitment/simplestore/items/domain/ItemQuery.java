package pl.ca.recruitment.simplestore.items.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.ca.recruitment.simplestore.items.domain.persistence.ItemEntity;
import pl.ca.recruitment.simplestore.items.domain.persistence.ItemRepository;
import pl.ca.recruitment.simplestore.items.model.ItemDTO;
import pl.ca.recruitment.simplestore.items.model.ItemFilterDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
class ItemQuery {
    private final ItemRepository itemRepository;

    @Transactional
    public List<ItemDTO> search(ItemFilterDTO filter) {
        if (filter.getNameStartsWith() != null) {
            return itemRepository.findAllByNameStartingWithIgnoreCase(filter.getNameStartsWith()).stream()
                    .map(ItemEntity::asDTO)
                    .collect(Collectors.toList());
        }

        return StreamSupport.stream(itemRepository.findAll().spliterator(), false)
                .map(ItemEntity::asDTO)
                .collect(Collectors.toList());
    }
}
