package pl.com.bottega.ecommerce.sales.application.api.handler;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class ProductBuilder {
	private Id aggregateId;
	private Money price;
	private String name;
	private ProductType productType;

	public ProductBuilder() {
		aggregateId = Id.generate();
		price = new Money(10d);
		name = "not imporant";
		productType = ProductType.STANDARD;
	}

	public ProductBuilder(Product product) {
		this.aggregateId = product.getId();
		this.price = product.getPrice();
		this.name = product.getName();
		this.productType = product.getProductType();
	}

	public ProductBuilder withId(Id id) {
		this.aggregateId = id;
		return this;
	}

	public ProductBuilder withPrice(Money price) {
		this.price = price;
		return this;
	}

	public ProductBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public ProductBuilder withProductType(ProductType productType) {
		this.productType = productType;
		return this;
	}

	public Product build() {
		return new Product(aggregateId, price, name, productType);
	}
}
