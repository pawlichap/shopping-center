package pl.ca.recruitment.simplestore.items.domain.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<ItemEntity, Long> {
    List<ItemEntity> findAllByNameStartingWithIgnoreCase(String name);

    List<ItemEntity> findAllByNameIn(List<String> names);
}
