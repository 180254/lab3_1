package pl.com.bottega.ecommerce.sales.domain.invoicing;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class BookKeeperTest {
	InvoiceFactory invoiceFactory;
	BookKeeper bookKeeper;
	InvoiceRequest invoiceRequest;
	TaxPolicy taxPolicy;

	@Before
	public void setUp() throws Exception {
		RequestItem requestItem = new RequestItemBuilder()
				.build();

		invoiceRequest = new InvoiceRequestBuilder()
				.addItem(requestItem)
				.build();

		Invoice invoice = new InvoiceBuilder()
				.withClient(invoiceRequest.getClient())
				.build();

		Tax tax = new TaxBuilder()
				.build();

		// given, always same
		invoiceFactory = Mockito.mock(InvoiceFactory.class);
		bookKeeper = new BookKeeper(invoiceFactory);
		taxPolicy = Mockito.mock(TaxPolicy.class);

		Mockito.when(invoiceFactory.
				create(invoiceRequest.getClientData())).
				thenReturn(invoice);

		Mockito.when(taxPolicy.
				calculateTax(requestItem.getProductData().getType(), requestItem.getProductData().getPrice())).
				thenReturn(tax);
	}

	@Test
	public final void testIssuance_testReult_invoiceRequestWithOneItemshoudReturnOneItem() {
		// when
		Invoice newInvoice = bookKeeper.issuance(invoiceRequest, taxPolicy);

		// then
		assertThat(newInvoice.getItems().size(), is(1));
	}

	@Test
	public final void testIssuance_testState_invoiceWithTwoItemsShouldInvokeTaxPolicyTwice() {
		// when
		RequestItem secondItem = new RequestItemBuilder().build();
		invoiceRequest.add(secondItem);
		bookKeeper.issuance(invoiceRequest, taxPolicy);

		// then
		Mockito.verify(taxPolicy, Mockito.times(2)).
				calculateTax(Mockito.any(ProductType.class),
						Mockito.any(Money.class));
	}

	@Test
	public final void testIssuance_testReult_invoiceShouldContainProperClientData() {
		// when
		Invoice newInvoice = bookKeeper.issuance(invoiceRequest, taxPolicy);

		// then
		assertThat(newInvoice.getClient(), is(invoiceRequest.getClientData()));
	}

	@Test
	public final void testIssuance_testState_methodFactoryFromInvoiceFactoryShouldBeAlwaysInvokedOnce() {
		// when
		bookKeeper.issuance(invoiceRequest, taxPolicy);

		// then
		Mockito.verify(invoiceFactory, Mockito.times(1)).create(invoiceRequest.getClientData());
	}

}
