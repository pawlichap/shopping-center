package pl.ca.recruitment.simplestore.items.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.ca.recruitment.simplestore.items.domain.persistence.ItemEntity;
import pl.ca.recruitment.simplestore.items.domain.persistence.ItemRepository;
import pl.ca.recruitment.simplestore.items.model.ItemDTO;

@RequiredArgsConstructor
class ItemCommand {
    private final ItemRepository itemRepository;

    @Transactional
    public void create(ItemDTO itemDTO) {
        final ItemEntity entity = ItemEntity.builder()
                .amount(itemDTO.getAmount())
                .name(itemDTO.getName())
                .price(itemDTO.getPrice())
                .build();

        itemRepository.save(entity);
    }
}
