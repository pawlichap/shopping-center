package pl.ca.recruitment.simplestore.store.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
// TODO add some validation to avoid incorrect purchase requests
public class PurchaseItemsRequestDTO {
	private String customerName;
	private List<ItemToPurchaseDTO> items;

	@Value
	@Builder
	public static class ItemToPurchaseDTO {
		private String name;
		private int amount;
	}
}
