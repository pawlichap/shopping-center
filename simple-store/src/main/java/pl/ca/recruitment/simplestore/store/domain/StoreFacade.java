package pl.ca.recruitment.simplestore.store.domain;

import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import pl.ca.recruitment.simplestore.external.ExternalApi;
import pl.ca.recruitment.simplestore.store.domain.exception.PurchaseItemsException;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsRequestDTO;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsResponseDTO;

@RequiredArgsConstructor
class StoreFacade implements StoreApi {
    private final StoreCommand storeCommand;
    private final ExternalApi externalShoppingRegistry;

    @Override
    public Either<PurchaseItemsException, PurchaseItemsResponseDTO> purchaseItems(PurchaseItemsRequestDTO request) {
        return Try.of(() -> storeCommand.purchaseItems(request))
                .onSuccess(resp -> externalShoppingRegistry.sendNotification(request, resp))
                .toEither()
                .mapLeft(PurchaseItemsException::fromOther);
    }
}
