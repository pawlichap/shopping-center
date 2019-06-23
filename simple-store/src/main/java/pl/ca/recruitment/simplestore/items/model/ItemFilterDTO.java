package pl.ca.recruitment.simplestore.items.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ItemFilterDTO {
    private String nameStartsWith;

    public static ItemFilterDTO empty() {
        return ItemFilterDTO.builder().build();
    }
}
