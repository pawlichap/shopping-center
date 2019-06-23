package pl.ca.recruitment.simplestore.external;

import io.vavr.control.Try;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsRequestDTO;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsResponseDTO;

public interface ExternalApi {
    Try<Void> sendNotification(PurchaseItemsRequestDTO purchaseRequest, PurchaseItemsResponseDTO purchaseResponse);
}
