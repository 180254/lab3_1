package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.sales.domain.invoicing.RequestItem;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class RequestItemBuilder {
	private ProductData productData;
	private int quantity;
	private Money totalCost;

	public RequestItemBuilder() {
		this.productData = new ProductDataBuilder().build();
		this.quantity = 1;
		this.totalCost = new Money(10);
	}

	public RequestItemBuilder(RequestItem requestItem) {
		this.productData = requestItem.getProductData();
		this.quantity = requestItem.getQuantity();
		this.totalCost = requestItem.getTotalCost();
	}

	public RequestItemBuilder withProductData(ProductData productData) {
		this.productData = productData;
		return this;
	}

	public RequestItemBuilder withQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}

	public RequestItemBuilder withTotalCost(Money totalCost) {
		this.totalCost = totalCost;
		return this;
	}

	public RequestItem build() {
		return new RequestItem(productData, quantity, totalCost);
	}
}