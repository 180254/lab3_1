package pl.com.bottega.ecommerce.sales.domain.invoicing;

import java.util.ArrayList;
import java.util.List;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.sales.domain.invoicing.InvoiceRequest;
import pl.com.bottega.ecommerce.sales.domain.invoicing.RequestItem;

public class InvoiceRequestBuilder {
	private ClientData client;
	private List<RequestItem> items;

	public InvoiceRequestBuilder() {
		this.client = new ClientDataBuilder().build();
		this.items = new ArrayList<>();
	}

	public InvoiceRequestBuilder(InvoiceRequest invoiceRequest) {
		client = invoiceRequest.getClient();

		for (RequestItem item : invoiceRequest.getItems()) {
			items.add(item);
		}
	}

	public InvoiceRequestBuilder withClient(ClientData client) {
		this.client = client;
		return this;
	}

	public InvoiceRequestBuilder addItems(List<RequestItem> items) {
		this.items.addAll(items);
		return this;
	}

	public InvoiceRequestBuilder addItem(RequestItem item) {
		items.add(item);
		return this;
	}

	public InvoiceRequest build() {
		InvoiceRequest invoiceRequest = new InvoiceRequest(client);
		for (RequestItem item : items) {
			invoiceRequest.add(item);
		}
		return invoiceRequest;
	}
}
