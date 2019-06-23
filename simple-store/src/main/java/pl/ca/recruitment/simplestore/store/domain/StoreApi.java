package pl.ca.recruitment.simplestore.store.domain;

import io.vavr.control.Either;
import pl.ca.recruitment.simplestore.store.domain.exception.PurchaseItemsException;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsRequestDTO;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsResponseDTO;

public interface StoreApi {
	Either<PurchaseItemsException, PurchaseItemsResponseDTO> purchaseItems(PurchaseItemsRequestDTO purchaseItemsRequestDTO);
}
