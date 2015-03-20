package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.sales.domain.invoicing.Tax;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class TaxBuilder {
	private Money amount;
	private String description;

	public TaxBuilder() {
		amount = new Money(10d);
		description = "not important";
	}

	public TaxBuilder(Tax tax) {
		this.amount = tax.getAmount();
		this.description = tax.getDescription();
	}

	public TaxBuilder withAmount(Money amount) {
		this.amount = amount;
		return this;
	}

	public TaxBuilder withDescription(String description) {
		this.description = description;
		return this;
	}

	public Tax build() {
		return new Tax(amount, description);
	}
}
