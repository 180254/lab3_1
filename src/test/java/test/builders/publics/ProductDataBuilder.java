package test.builders.publics;

import java.util.Date;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class ProductDataBuilder {
	private Id productId;
	private Money price;
	private String name;
	private Date snapshotDate;
	private ProductType type;

	public ProductDataBuilder() {
		productId = Id.generate();
		price = new Money(10d);
		name = "not important";
		snapshotDate = new Date();
		type = ProductType.STANDARD;
	}

	public ProductDataBuilder(ProductData bean) {
		this.productId = bean.getProductId();
		this.price = bean.getPrice();
		this.name = bean.getName();
		this.snapshotDate = bean.getSnapshotDate();
		this.type = bean.getType();
	}

	public ProductDataBuilder withProductId(Id productId) {
		this.productId = productId;
		return this;
	}

	public ProductDataBuilder withPrice(Money price) {
		this.price = price;
		return this;
	}

	public ProductDataBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public ProductDataBuilder withSnapshotDate(Date snapshotDate) {
		this.snapshotDate = snapshotDate;
		return this;
	}

	public ProductDataBuilder withType(ProductType type) {
		this.type = type;
		return this;
	}

	public ProductData build() {
		return new ProductData(productId, price, name, type, snapshotDate);
	}
}