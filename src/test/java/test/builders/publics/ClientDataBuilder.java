package test.builders.publics;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;

public class ClientDataBuilder {

	private Id id;
	private String name;

	public ClientDataBuilder() {
		id = Id.generate();
		name = "not important";
	}

	public ClientDataBuilder(ClientData client) {
		this.id = client.getAggregateId();
		this.name = client.getName();
	}

	public ClientDataBuilder withId(Id id) {
		this.id = id;
		return this;
	}

	public ClientDataBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public ClientData build() {
		return new ClientData(id, name);
	}

}
