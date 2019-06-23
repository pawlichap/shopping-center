package pl.ca.recruitment.simplestore.items.domain.setup;

import com.google.common.collect.Iterables;
import org.assertj.core.util.Lists;
import pl.ca.recruitment.simplestore.items.domain.persistence.ItemEntity;
import pl.ca.recruitment.simplestore.items.domain.persistence.ItemRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryItemRepository implements ItemRepository {
    private AtomicLong ids = new AtomicLong(0);
    private ConcurrentHashMap<Long, ItemEntity> entities = new ConcurrentHashMap<>();

    @Override
    public List<ItemEntity> findAll() {
        return Lists.newArrayList(entities.values());
    }

    @Override
    public List<ItemEntity> findAllById(Iterable<Long> iterable) {
        return findAll().stream()
                .filter(item -> Iterables.contains(iterable, item.getId()))
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
    public void delete(ItemEntity ItemEntity) {
        entities.values().remove(ItemEntity);
    }

    @Override
    public void deleteAll(Iterable<? extends ItemEntity> iterable) {
        iterable.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        entities.clear();
    }

    @Override
    public <S extends ItemEntity> S save(S s) {
        entities.put(ids.getAndIncrement(), s);
        return s;
    }

    @Override
    public <S extends ItemEntity> List<S> saveAll(Iterable<S> iterable) {
        iterable.forEach(this::save);
        return Lists.newArrayList(iterable);
    }

    @Override
    public Optional<ItemEntity> findById(Long aLong) {
        return Optional.ofNullable(entities.get(aLong));
    }

    @Override
    public boolean existsById(Long aLong) {
        return findById(aLong).isPresent();
    }

    @Override
    public List<ItemEntity> findAllByNameStartingWithIgnoreCase(String name) {
        return entities.values().stream()
                .filter(item -> item.getName().toLowerCase().startsWith(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemEntity> findAllByNameIn(List<String> names) {
        return entities.values().stream()
                .filter(item -> names.contains(item.getName()))
                .collect(Collectors.toList());
    }
}
