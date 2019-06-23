package pl.ca.recruitment.simplestore.customers.domain.persistence;

import io.vavr.control.Option;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {
    boolean existsByNameIgnoreCase(String name);

    Option<CustomerEntity> findByNameIgnoreCase(String name);
}
