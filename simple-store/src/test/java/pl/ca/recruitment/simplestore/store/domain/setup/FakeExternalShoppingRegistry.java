package pl.ca.recruitment.simplestore.store.domain.setup;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import org.assertj.core.util.Lists;
import pl.ca.recruitment.simplestore.external.ExternalApi;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsRequestDTO;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsResponseDTO;

import java.util.List;

public class FakeExternalShoppingRegistry implements ExternalApi {
    private List<Tuple2<PurchaseItemsRequestDTO, PurchaseItemsResponseDTO>> previousNotifications = Lists.newArrayList();

    @Override
    public Try<Void> sendNotification(PurchaseItemsRequestDTO purchaseRequest, PurchaseItemsResponseDTO purchaseResponse) {
        previousNotifications.add(Tuple.of(purchaseRequest, purchaseResponse));
        return Try.success(null);
    }

    public boolean wasNotificationSent(PurchaseItemsRequestDTO request, PurchaseItemsResponseDTO response) {
        return previousNotifications.contains(Tuple.of(request, response));
    }

    public boolean noNotifications() {
        return previousNotifications.isEmpty();
    }
}
