package pl.ca.recruitment.simplestore.items;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ca.recruitment.simplestore.items.domain.ItemApi;
import pl.ca.recruitment.simplestore.items.model.ItemDTO;
import pl.ca.recruitment.simplestore.items.model.ItemFilterDTO;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
class ItemController {
    private final ItemApi itemApi;

    @GetMapping("/all")
    public ResponseEntity<List<ItemDTO>> allItems() {
        return ResponseEntity.ok(itemApi.search(ItemFilterDTO.empty()));
    }

    @PostMapping("/search")
    public ResponseEntity<List<ItemDTO>> search(@RequestBody ItemFilterDTO itemFilter) {
        return ResponseEntity.ok(itemApi.search(itemFilter));
    }

    @PutMapping
    public ResponseEntity<Void> create(@RequestBody ItemDTO itemDTO) {
        return itemApi.create(itemDTO)
                .map(ResponseEntity::ok)
                .getOrElseThrow(ex -> new RuntimeException("Error during item creation", ex));
    }
}
