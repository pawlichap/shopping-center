package pl.ca.recruitment.simplestore.store.domain;

import io.vavr.control.Either;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import pl.ca.recruitment.simplestore.Randomize;
import pl.ca.recruitment.simplestore.customers.domain.persistence.CustomerEntity;
import pl.ca.recruitment.simplestore.customers.domain.persistence.CustomerRepository;
import pl.ca.recruitment.simplestore.customers.domain.setup.InMemoryCustomerRepository;
import pl.ca.recruitment.simplestore.items.domain.persistence.ItemEntity;
import pl.ca.recruitment.simplestore.items.domain.persistence.ItemRepository;
import pl.ca.recruitment.simplestore.items.domain.setup.InMemoryItemRepository;
import pl.ca.recruitment.simplestore.store.domain.exception.PurchaseExceptionCode;
import pl.ca.recruitment.simplestore.store.domain.exception.PurchaseItemsException;
import pl.ca.recruitment.simplestore.store.domain.setup.FakeExternalShoppingRegistry;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsRequestDTO;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsRequestDTO.ItemToPurchaseDTO;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsResponseDTO;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class StoreDomainTest {
    private FakeExternalShoppingRegistry externalShoppingRegistry;
    private CustomerRepository customerRepository;
    private ItemRepository itemRepository;
    private StoreApi storeApi;

    @Before
    public void initialize() {
        itemRepository = new InMemoryItemRepository();
        customerRepository = new InMemoryCustomerRepository();
        externalShoppingRegistry = new FakeExternalShoppingRegistry();

        final StoreConfiguration storeConfiguration = new StoreConfiguration();
        final StoreCommand storeCommand = storeConfiguration.storeCommand(customerRepository, itemRepository);
        storeApi = storeConfiguration.storeApi(storeCommand, externalShoppingRegistry);
    }

    @Test
    public void customerWasNotFound() {
        // given
        final PurchaseItemsRequestDTO request = PurchaseItemsRequestDTO.builder()
                .customerName("Jack")
                .items(Lists.newArrayList(Randomize.random(ItemToPurchaseDTO.class)))
                .build();

        // when
        final Either<PurchaseItemsException, PurchaseItemsResponseDTO> result = storeApi.purchaseItems(request);

        // then
        assertTrue(result.isLeft());

        // and
        assertSame(PurchaseExceptionCode.CUSTOMER_NOT_FOUND, result.getLeft().getCode());
    }

    @Test
    public void someOfTheRequestedItemsDontEvenExist() {
        //given
        final CustomerEntity customer = Randomize.random(CustomerEntity.class);
        customerRepository.save(customer);

        // and
        final PurchaseItemsRequestDTO request = PurchaseItemsRequestDTO.builder()
                .customerName(customer.getName())
                .items(Lists.newArrayList(Randomize.random(ItemToPurchaseDTO.class)))
                .build();

        // when
        final Either<PurchaseItemsException, PurchaseItemsResponseDTO> result = storeApi.purchaseItems(request);

        // then
        assertTrue(result.isLeft());

        // and
        assertSame(PurchaseExceptionCode.NOT_ALL_ITEMS_FOUND, result.getLeft().getCode());
    }

    @Test
    public void someItemsAreNotAvailable() {
        //given
        final CustomerEntity customer = Randomize.random(CustomerEntity.class);
        customerRepository.save(customer);

        // and
        final ItemEntity item = Randomize.random(ItemEntity.class).toBuilder()
                .amount(0)
                .build();
        itemRepository.save(item);

        // and
        final PurchaseItemsRequestDTO request = PurchaseItemsRequestDTO.builder()
                .customerName(customer.getName())
                .items(Lists.newArrayList(
                        ItemToPurchaseDTO.builder()
                                .name(item.getName())
                                .amount(1)
                                .build()
                ))
                .build();

        // when
        final Either<PurchaseItemsException, PurchaseItemsResponseDTO> result = storeApi.purchaseItems(request);

        // then
        assertTrue(result.isLeft());

        // and
        assertSame(PurchaseExceptionCode.NOT_ALL_ITEMS_AVAILABLE, result.getLeft().getCode());
    }

    @Test
    public void customersWantsToBuyMoreItemsThanInStore() {
        // given
        final CustomerEntity customer = Randomize.random(CustomerEntity.class);
        customerRepository.save(customer);

        // and
        final ItemEntity item = Randomize.random(ItemEntity.class).toBuilder()
                .amount(5)
                .build();
        itemRepository.save(item);

        // and
        final PurchaseItemsRequestDTO request = PurchaseItemsRequestDTO.builder()
                .customerName(customer.getName())
                .items(Lists.newArrayList(
                        ItemToPurchaseDTO.builder()
                                .name(item.getName())
                                .amount(6)
                                .build()
                ))
                .build();

        // when
        final Either<PurchaseItemsException, PurchaseItemsResponseDTO> result = storeApi.purchaseItems(request);

        // then
        assertTrue(result.isLeft());

        // and
        assertSame(PurchaseExceptionCode.NOT_ENOUGH_ITEMS, result.getLeft().getCode());
    }

    @Test
    public void customerHasNotEnoughMoney() {
        // given
        final CustomerEntity customer = Randomize.random(CustomerEntity.class).toBuilder()
                .money(new BigDecimal("20.00"))
                .build();
        customerRepository.save(customer);

        // and
        final ItemEntity item = Randomize.random(ItemEntity.class).toBuilder()
                .amount(5)
                .price(new BigDecimal("10.00"))
                .build();
        itemRepository.save(item);

        // and
        final PurchaseItemsRequestDTO request = PurchaseItemsRequestDTO.builder()
                .customerName(customer.getName())
                .items(Lists.newArrayList(
                        ItemToPurchaseDTO.builder()
                                .name(item.getName())
                                .amount(3)
                                .build()
                ))
                .build();

        // when
        final Either<PurchaseItemsException, PurchaseItemsResponseDTO> result = storeApi.purchaseItems(request);

        // then
        assertTrue(result.isLeft());

        // and
        assertSame(PurchaseExceptionCode.NOT_ENOUGH_MONEY, result.getLeft().getCode());
    }

    @Test
    public void purchaseIsSuccessfulAndAmountOfItemsInStoreIsReduced() {
        // given
        final CustomerEntity customer = Randomize.random(CustomerEntity.class).toBuilder()
                .money(new BigDecimal("100.00"))
                .build();
        customerRepository.save(customer);

        final ItemEntity item = Randomize.random(ItemEntity.class).toBuilder()
                .amount(5)
                .price(new BigDecimal("10.00"))
                .build();
        itemRepository.save(item);

        // and
        final PurchaseItemsRequestDTO request = PurchaseItemsRequestDTO.builder()
                .customerName(customer.getName())
                .items(Lists.newArrayList(
                        ItemToPurchaseDTO.builder()
                                .name(item.getName())
                                .amount(3)
                                .build()
                ))
                .build();

        // when
        final Either<PurchaseItemsException, PurchaseItemsResponseDTO> result = storeApi.purchaseItems(request);

        // then
        assertTrue(result.isRight());

        // and
        assertEquals(2, item.getAmount());
    }

    @Test
    public void purchaseIsSuccessfulAndClientsMoneyAmountIsReducedAndReturnedToTheClient() {
        // given
        final CustomerEntity customer = Randomize.random(CustomerEntity.class).toBuilder()
                .money(new BigDecimal("150.00"))
                .build();
        customerRepository.save(customer);

        final ItemEntity item = Randomize.random(ItemEntity.class).toBuilder()
                .amount(10)
                .price(new BigDecimal("15.00"))
                .build();
        itemRepository.save(item);

        // and
        final PurchaseItemsRequestDTO request = PurchaseItemsRequestDTO.builder()
                .customerName(customer.getName())
                .items(Lists.newArrayList(
                        ItemToPurchaseDTO.builder()
                                .name(item.getName())
                                .amount(6)
                                .build()
                ))
                .build();

        // when
        final Either<PurchaseItemsException, PurchaseItemsResponseDTO> result = storeApi.purchaseItems(request);

        // then
        assertTrue(result.isRight());

        // and
        assertEquals(new BigDecimal("60.00"), customer.getMoney());

        // and
        final PurchaseItemsResponseDTO purchaseResponse = result.get();
        assertEquals(new BigDecimal("60.00"), purchaseResponse.getMoneyLeft());
    }

    @Test
    public void purchaseIsSuccessfulAndExternalShoppingRegistryIsNotified() {
        // given
        final CustomerEntity customer = Randomize.random(CustomerEntity.class).toBuilder()
                .money(new BigDecimal("150.00"))
                .build();
        customerRepository.save(customer);

        // and
        final ItemEntity item = Randomize.random(ItemEntity.class).toBuilder()
                .amount(10)
                .price(new BigDecimal("15.00"))
                .build();
        itemRepository.save(item);

        // and
        final PurchaseItemsRequestDTO request = PurchaseItemsRequestDTO.builder()
                .customerName(customer.getName())
                .items(Lists.newArrayList(
                        ItemToPurchaseDTO.builder()
                                .name(item.getName())
                                .amount(6)
                                .build()
                ))
                .build();

        // when
        final Either<PurchaseItemsException, PurchaseItemsResponseDTO> result = storeApi.purchaseItems(request);

        // then
        assertTrue(result.isRight());

        // and
        assertTrue(externalShoppingRegistry.wasNotificationSent(request, result.get()));
    }

    @Test
    public void purchaseIsNotSuccessfulAndExternalShoppingRegistryIsNotNotified() {
        // given
        final PurchaseItemsRequestDTO request = PurchaseItemsRequestDTO.builder()
                .customerName("Jack")
                .items(Lists.newArrayList(Randomize.random(ItemToPurchaseDTO.class)))
                .build();

        // when
        final Either<PurchaseItemsException, PurchaseItemsResponseDTO> result = storeApi.purchaseItems(request);

        // then
        assertTrue(result.isLeft());

        // and
        assertTrue(externalShoppingRegistry.noNotifications());
    }
}
