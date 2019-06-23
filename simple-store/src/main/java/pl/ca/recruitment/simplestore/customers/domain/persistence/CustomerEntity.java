package pl.ca.recruitment.simplestore.customers.domain.persistence;

import lombok.*;
import pl.ca.recruitment.simplestore.customers.model.CustomerDTO;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String name;

    @Column(nullable = false, scale = 2)
    private BigDecimal money;

    public void withdraw(BigDecimal toWithdraw) {
        if(toWithdraw.compareTo(money) > 0)
            throw new IllegalStateException("There is not enough money to withdraw");

        this.money = this.money.subtract(toWithdraw);
    }

    public CustomerDTO asDTO() {
        return CustomerDTO.builder()
                .name(name)
                .money(money)
                .build();
    }
}
