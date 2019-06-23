package pl.ca.recruitment.simplestore.customers.domain;

import io.vavr.control.Option;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.ca.recruitment.simplestore.customers.domain.persistence.CustomerEntity;
import pl.ca.recruitment.simplestore.customers.domain.persistence.CustomerRepository;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@DataJpaTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-integration.properties")
public class CustomerQueriesJpaTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void customerShouldExistEvenThoNameIsDifferentCase() {
        // given
        final CustomerEntity customer = CustomerEntity.builder()
                .name("Jack")
                .money(new BigDecimal("15.50"))
                .build();

        // when
        customerRepository.save(customer);

        // then
        assertTrue(customerRepository.existsByNameIgnoreCase("jAcK"));
    }

    @Test
    public void customerShouldBeRetrievedEvenThoNameIsDifferentCase() {
        // given
        final CustomerEntity customer = CustomerEntity.builder()
                .name("Jack")
                .money(new BigDecimal("15.50"))
                .build();

        // when
        customerRepository.save(customer);

        // then
        final Option<CustomerEntity> found = customerRepository.findByNameIgnoreCase("jAcK");

        assertTrue(found.isDefined());
        assertEquals(customer, found.get());
    }
}
