package pl.ca.recruitment.simplestore.customers.domain;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import pl.ca.recruitment.simplestore.customers.model.CustomerDTO;

@RequiredArgsConstructor
class CustomerFacade implements CustomerApi {
    private final CustomerCommand customerCommand;
    private final CustomerQuery customerQuery;

    @Override
    public Try<Void> create(CustomerDTO customerDTO) {
        return Try.run(() -> customerCommand.create(customerDTO));
    }

    @Override
    public Option<CustomerDTO> findByName(String name) {
        return customerQuery.findByName(name);
    }
}
