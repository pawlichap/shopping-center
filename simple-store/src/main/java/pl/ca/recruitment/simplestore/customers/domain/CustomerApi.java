package pl.ca.recruitment.simplestore.customers.domain;

import io.vavr.control.Option;
import io.vavr.control.Try;
import pl.ca.recruitment.simplestore.customers.model.CustomerDTO;

public interface CustomerApi {
    Try<Void> create(CustomerDTO customerDTO);

    Option<CustomerDTO> findByName(String name);
}
