package pl.ca.recruitment.simplestore.customers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ca.recruitment.simplestore.customers.domain.CustomerApi;
import pl.ca.recruitment.simplestore.customers.model.CustomerDTO;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
class CustomersController {
    private final CustomerApi customerApi;

    @PutMapping
    public ResponseEntity<Void> create(@RequestBody CustomerDTO customerDTO) {
        return customerApi.create(customerDTO)
                .map(ResponseEntity::ok)
                .getOrElseThrow(ex -> new RuntimeException("Error during customer creation", ex));
    }

    @GetMapping("/{name}")
    public ResponseEntity<CustomerDTO> findByName(@PathVariable("name") String name) {
        return customerApi.findByName(name)
                .map(ResponseEntity::ok)
                .getOrElse(ResponseEntity.notFound().build());
    }
}
