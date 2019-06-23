package pl.ca.recruitment.simplestore.store;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ca.recruitment.simplestore.store.domain.StoreApi;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsRequestDTO;
import pl.ca.recruitment.simplestore.store.model.PurchaseItemsResponseDTO;

import java.util.function.Function;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
class StoreController {
    private final StoreApi storeApi;

    @PostMapping("/purchase")
    public ResponseEntity<PurchaseItemsResponseDTO> purchaseItems(@RequestBody PurchaseItemsRequestDTO purchaseItemsRequestDTO) {
        return storeApi.purchaseItems(purchaseItemsRequestDTO)
                .map(ResponseEntity::ok)
                .getOrElseThrow(Function.identity());
    }
}
