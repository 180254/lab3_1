package pl.com.bottega.ecommerce.sales.application.api.handler;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.application.api.command.AddProductCommand;

public class AddProductCommandBuilder {
	private Id orderId;
	private Id productId;
	private int quantity;

	public AddProductCommandBuilder() {
		orderId = Id.generate();
		productId = Id.generate();
		quantity = 1;
	}

	public AddProductCommandBuilder(AddProductCommand addProductCommand) {
		this.orderId = addProductCommand.getOrderId();
		this.productId = addProductCommand.getOrderId();
		this.quantity = addProductCommand.getQuantity();
	}

	public AddProductCommandBuilder withOrderId(Id orderId) {
		this.orderId = orderId;
		return this;
	}

	public AddProductCommandBuilder withProductId(Id productId) {
		this.productId = productId;
		return this;
	}

	public AddProductCommandBuilder withQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}

	public AddProductCommand build() {
		return new AddProductCommand(orderId, productId, quantity);
	}
}