package pl.ca.recruitment.simplestore.customers.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.ca.recruitment.simplestore.customers.domain.persistence.CustomerEntity;
import pl.ca.recruitment.simplestore.customers.domain.persistence.CustomerRepository;
import pl.ca.recruitment.simplestore.customers.model.CustomerDTO;

@RequiredArgsConstructor
class CustomerCommand {
    private final CustomerRepository customerRepository;

    @Transactional
    void create(CustomerDTO customerDTO) {
        final CustomerEntity entity = CustomerEntity.builder()
                .name(customerDTO.getName())
                .money(customerDTO.getMoney())
                .build();

        customerRepository.save(entity);
    }
}
