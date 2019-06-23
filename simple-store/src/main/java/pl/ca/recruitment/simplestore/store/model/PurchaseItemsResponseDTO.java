package pl.ca.recruitment.simplestore.store.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class PurchaseItemsResponseDTO {
	private BigDecimal moneyLeft;
}
