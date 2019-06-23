package pl.ca.recruitment.shoppingregistry.notification.model;

import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class PurchaseNotificationDTO {
    private String customerName;
    private BigDecimal moneyLeft;
    private List<ItemToPurchaseDTO> items;

    @Value
    public static class ItemToPurchaseDTO {
        private String name;
        private int amount;
    }
}
