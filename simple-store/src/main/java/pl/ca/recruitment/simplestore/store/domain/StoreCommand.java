package pl.ca.recruitment.simplestore.store.domain;

import lombok.RequiredArgsConstructor;
import pl.ca.recruitment.simplestore.customers.domain.persistence.CustomerEntity;
import pl.ca.recruitment.simplestore.customers.domain.persistence.CustomerRepository;
import pl.ca.recruitment.simplestore.items.domain.persistence.ItemEntity;
import pl.ca.recruitment.simplestore.items.domain.persistence.ItemRepository;
import pl.ca.recruitment.simplestore.store.domain.exception.PurchaseExceptionCode;
import pl.ca.recruitment.simplestore.store.domain.exception.PurchaseItemsException;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsRequestDTO;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsRequestDTO.ItemToPurchaseDTO;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class StoreCommand {
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;

    public PurchaseItemsResponseDTO purchaseItems(PurchaseItemsRequestDTO request) {
        final CustomerEntity foundCustomer = customerRepository.findByNameIgnoreCase(request.getCustomerName())
                .getOrElseThrow(() -> new PurchaseItemsException(PurchaseExceptionCode.CUSTOMER_NOT_FOUND));

        final List<ItemEntity> neededItems = itemRepository.findAllByNameIn(
                request.getItems().stream()
                        .map(ItemToPurchaseDTO::getName)
                        .collect(Collectors.toList())
        );

        if (neededItems.size() != request.getItems().size())
            throw new PurchaseItemsException(PurchaseExceptionCode.NOT_ALL_ITEMS_FOUND);

        // TODO validate if requested items are available (count higher than 0)

        // TODO validate if there is enough items in the store

        // TODO validate if customer has enough money for purchase

        // TODO update amount of every bought item in the store

        // TODO set the correct amount of money left after purchase

        // TODO add items to customer's inventory

        // TODO return correct response
        return null;
    }
}
