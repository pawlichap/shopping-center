package pl.ca.recruitment.simplestore.external;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.ca.recruitment.simplestore.external.model.PurchaseNotificationDTO;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsRequestDTO;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsResponseDTO;

@RequiredArgsConstructor
class ExternalShoppingRegistry implements ExternalApi {
    private final String endpoint;
    private final RestTemplate restTemplate;

    public Try<Void> sendNotification(PurchaseItemsRequestDTO purchaseRequest, PurchaseItemsResponseDTO purchaseResponse) {
        return Try.run(() -> {
            final PurchaseNotificationDTO request = PurchaseNotificationDTO.builder()
                    .customerName(purchaseRequest.getCustomerName())
                    .items(purchaseRequest.getItems())
                    .moneyLeft(purchaseResponse.getMoneyLeft())
                    .build();

            final ResponseEntity<Void> response = restTemplate.postForEntity(endpoint, request, Void.class);

            if (!response.getStatusCode().is2xxSuccessful())
                throw new IllegalStateException("Could not notify shopping registry");
        });
    }
}
