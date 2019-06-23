package pl.ca.recruitment.simplestore.items.domain.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.ca.recruitment.simplestore.items.model.ItemDTO;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String name;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false, scale = 2)
    private BigDecimal price;

    public void take(int toTake) {
        if (this.amount < toTake)
            throw new IllegalStateException("There is not enough items in the store");

        this.amount = this.amount - toTake;
    }

    public boolean isAvailable() {
        return amount > 0;
    }

    public ItemDTO asDTO() {
        return ItemDTO.builder()
                .name(name)
                .amount(amount)
                .price(price)
                .build();
    }
}
