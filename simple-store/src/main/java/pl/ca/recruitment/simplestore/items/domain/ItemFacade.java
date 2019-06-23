package pl.ca.recruitment.simplestore.items.domain;

import com.google.common.collect.Lists;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import pl.ca.recruitment.simplestore.items.model.ItemDTO;
import pl.ca.recruitment.simplestore.items.model.ItemFilterDTO;

import java.util.List;

@RequiredArgsConstructor
class ItemFacade implements ItemApi {
    private final ItemQuery itemQuery;
    private final ItemCommand itemCommand;

    @Override
    public List<ItemDTO> search(ItemFilterDTO filter) {
        return Try.of(() -> itemQuery.search(filter))
                .getOrElse(Lists.newArrayList());
    }

    @Override
    public Try<Void> create(ItemDTO itemDTO) {
        return Try.run(() -> itemCommand.create(itemDTO));
    }
}
