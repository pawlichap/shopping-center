package pl.ca.recruitment.simplestore.items.domain;

import io.vavr.control.Try;
import pl.ca.recruitment.simplestore.items.model.ItemDTO;
import pl.ca.recruitment.simplestore.items.model.ItemFilterDTO;

import java.util.List;

public interface ItemApi {
	List<ItemDTO> search(ItemFilterDTO filter);

	Try<Void> create(ItemDTO itemDTO);
}
