package pl.ca.recruitment.simplestore.external.model;

import lombok.Builder;
import lombok.Value;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsRequestDTO;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class PurchaseNotificationDTO {
    private String customerName;
    private BigDecimal moneyLeft;
    private List<PurchaseItemsRequestDTO.ItemToPurchaseDTO> items;
}
