package pl.ca.recruitment.simplestore.items.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
//TODO This can lead to creation of incorrect Items, add some validation
public class ItemDTO {
	private String name;
	private int amount;
	private BigDecimal price;
}
