package pl.ca.recruitment.simplestore.customers.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class CustomerDTO {
	private String name;
	private BigDecimal money;
}
