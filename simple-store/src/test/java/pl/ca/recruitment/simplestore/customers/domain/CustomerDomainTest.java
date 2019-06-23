package pl.ca.recruitment.simplestore.customers.domain;

import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.Before;
import org.junit.Test;
import pl.ca.recruitment.simplestore.customers.domain.setup.InMemoryCustomerRepository;
import pl.ca.recruitment.simplestore.customers.model.CustomerDTO;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerDomainTest {
    private CustomerApi customerApi;

    @Before
    public void initialize() {
        final InMemoryCustomerRepository inMemoryCustomerRepository = new InMemoryCustomerRepository();
        final CustomerConfiguration customerConfiguration = new CustomerConfiguration();
        final CustomerCommand command = customerConfiguration.customerCommand(inMemoryCustomerRepository);
        final CustomerQuery query = customerConfiguration.customerQuery(inMemoryCustomerRepository);

        customerApi = customerConfiguration.customerApi(command, query);
    }

    @Test
    public void customerCreationFlowShouldComplete() {
        // given
        final CustomerDTO customer = CustomerDTO.builder()
                .name("Jack")
                .money(new BigDecimal("15.00"))
                .build();

        // when
        final Try<Void> result = customerApi.create(customer);

        // then
        assertTrue(result.isSuccess());

        // and
        final Option<CustomerDTO> found = customerApi.findByName("Jack");

        assertTrue(found.isDefined());
        assertEquals(customer, found.get());
    }
}
