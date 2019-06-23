package pl.ca.recruitment.simplestore.customers.domain;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.ca.recruitment.simplestore.customers.domain.persistence.CustomerEntity;
import pl.ca.recruitment.simplestore.customers.domain.persistence.CustomerRepository;
import pl.ca.recruitment.simplestore.customers.model.CustomerDTO;

@RequiredArgsConstructor
class CustomerQuery {
    private final CustomerRepository customerRepository;

    @Transactional
    Option<CustomerDTO> findByName(String customerName) {
        return customerRepository.findByNameIgnoreCase(customerName)
                .map(CustomerEntity::asDTO);
    }
}
