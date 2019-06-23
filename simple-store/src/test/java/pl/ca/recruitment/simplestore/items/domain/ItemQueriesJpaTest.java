package pl.ca.recruitment.simplestore.items.domain;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.ca.recruitment.simplestore.items.domain.persistence.ItemEntity;
import pl.ca.recruitment.simplestore.items.domain.persistence.ItemRepository;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-integration.properties")
public class ItemQueriesJpaTest {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void threeItemsAreQueriesButOnlyTwoExist() {
        // given
        final ItemEntity hammer = ItemEntity.builder()
                .name("Hammer")
                .amount(5)
                .price(BigDecimal.ONE)
                .build();

        // and
        final ItemEntity saw = ItemEntity.builder()
                .name("Saw")
                .amount(5)
                .price(BigDecimal.ONE)
                .build();

        // when
        itemRepository.saveAll(Lists.newArrayList(hammer, saw));

        // then
        assertEquals(2, itemRepository.findAllByNameIn(Lists.newArrayList("Hammer", "Nail", "Saw")).size());
    }

    @Test
    public void threeItemsAreInTheStoreButOnlyTwoAreMatchingTheQuery() {
        // given
        final ItemEntity hammer = ItemEntity.builder()
                .name("HAMMER")
                .amount(5)
                .price(BigDecimal.ONE)
                .build();

        final ItemEntity hammock = ItemEntity.builder()
                .name("hAmmock")
                .amount(5)
                .price(BigDecimal.ONE)
                .build();

        final ItemEntity saw = ItemEntity.builder()
                .name("Saw")
                .amount(5)
                .price(BigDecimal.ONE)
                .build();

        // when
        itemRepository.saveAll(Lists.newArrayList(hammer, hammock, saw));

        // then
        assertEquals(2, itemRepository.findAllByNameStartingWithIgnoreCase("ha").size());
    }
}
