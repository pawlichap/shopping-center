package pl.ca.recruitment.simplestore.customers.domain.setup;

import com.google.common.collect.Iterables;
import io.vavr.control.Option;
import org.assertj.core.util.Lists;
import pl.ca.recruitment.simplestore.customers.domain.persistence.CustomerEntity;
import pl.ca.recruitment.simplestore.customers.domain.persistence.CustomerRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryCustomerRepository implements CustomerRepository {
    private AtomicLong ids = new AtomicLong(0);
    private ConcurrentHashMap<Long, CustomerEntity> entities = new ConcurrentHashMap<>();

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return entities.values().stream()
                .map(CustomerEntity::getName)
                .anyMatch(inDb -> inDb.equalsIgnoreCase(name));
    }

    @Override
    public Option<CustomerEntity> findByNameIgnoreCase(String name) {
        return Option.ofOptional(
                entities.values().stream()
                        .filter(inDb -> inDb.getName().equalsIgnoreCase(name))
                        .findFirst()
        );
    }

    @Override
    public List<CustomerEntity> findAll() {
        return Lists.newArrayList(entities.values());
    }

    @Override
    public List<CustomerEntity> findAllById(Iterable<Long> iterable) {
        return findAll().stream()
                .filter(customer -> Iterables.contains(iterable, customer.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return entities.values().size();
    }

    @Override
    public void deleteById(Long aLong) {
        entities.remove(aLong);
    }

    @Override
    public void delete(CustomerEntity customerEntity) {
        entities.values().remove(customerEntity);
    }

    @Override
    public void deleteAll(Iterable<? extends CustomerEntity> iterable) {
        iterable.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        entities.clear();
    }

    @Override
    public <S extends CustomerEntity> S save(S s) {
        entities.put(ids.getAndIncrement(), s);
        return s;
    }

    @Override
    public <S extends CustomerEntity> List<S> saveAll(Iterable<S> iterable) {
        iterable.forEach(this::save);
        return Lists.newArrayList(iterable);
    }

    @Override
    public Optional<CustomerEntity> findById(Long aLong) {
        return Optional.ofNullable(entities.get(aLong));
    }

    @Override
    public boolean existsById(Long aLong) {
        return findById(aLong).isPresent();
    }
}
