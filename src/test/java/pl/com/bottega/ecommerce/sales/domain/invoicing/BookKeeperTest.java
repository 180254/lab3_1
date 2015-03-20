package pl.com.bottega.ecommerce.sales.domain.invoicing;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class BookKeeperTest {

	BookKeeper bookKeeper;
	TaxPolicy taxPolicy;
	InvoiceFactory invoiceFactory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// given, always same
		taxPolicy = Mockito.mock(TaxPolicy.class);
		invoiceFactory = Mockito.mock(InvoiceFactory.class);

		bookKeeper = new BookKeeper(invoiceFactory);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testIssuance_testReult_invoiceRequestWithOneItemshoudReturnOneItem() {

		InvoiceRequest invoiceRequest = new InvoiceRequestBuilder().build();
		RequestItem requestItem = new RequestItemBuilder().build();
		invoiceRequest.add(requestItem);

		Invoice invoice = new InvoiceBuilder().withClient(invoiceRequest.getClient()).build();
		Tax tax = new TaxBuilder().build();

		// when
		Mockito.when(invoiceFactory.create(invoiceRequest.getClientData())).
				thenReturn(invoice);
		Mockito.when(taxPolicy.calculateTax(requestItem.getProductData().getType(),
				requestItem.getProductData().getPrice())).
				thenReturn(tax);

		// then
		Invoice newInvoice = bookKeeper.issuance(invoiceRequest, taxPolicy);
		assertThat(newInvoice.getItems().size(), is(1));
	}

	@Test
	public final void testIssuance_testState_invoiceWithTwoItemsShouldInvokeTaxPolicyTwice() {
		InvoiceRequest invoiceRequest = new InvoiceRequestBuilder().build();
		RequestItem[] requestItems = new RequestItem[2];
		requestItems[0] = new RequestItemBuilder().build();
		requestItems[1] = new RequestItemBuilder(requestItems[0]).build();

		invoiceRequest.add(requestItems[0]);
		invoiceRequest.add(requestItems[1]);

		Invoice invoice = new InvoiceBuilder().withClient(invoiceRequest.getClient()).build();
		Tax tax = new TaxBuilder().build();

		// when
		Mockito.when(invoiceFactory.create(invoiceRequest.getClientData())).
				thenReturn(invoice);
		Mockito.when(taxPolicy.calculateTax(requestItems[0].getProductData().getType(),
				requestItems[0].getProductData().getPrice())).
				thenReturn(tax);

		// then
		bookKeeper = new BookKeeper(invoiceFactory);
		bookKeeper.issuance(invoiceRequest, taxPolicy);
		Mockito.verify(taxPolicy, Mockito.times(2)).
				calculateTax(Mockito.any(ProductType.class),
						Mockito.any(Money.class));
	}

	@Test
	public final void testIssuance_testReult_invoiceShouldContainProperClientData() {
		InvoiceRequest invoiceRequest = new InvoiceRequestBuilder().build();
		RequestItem requestItem = new RequestItemBuilder().build();
		invoiceRequest.add(requestItem);

		Invoice invoice = new InvoiceBuilder().withClient(invoiceRequest.getClient()).build();
		Tax tax = new TaxBuilder().build();

		// when
		Mockito.when(invoiceFactory.create(invoiceRequest.getClientData())).
				thenReturn(invoice);
		Mockito.when(taxPolicy.calculateTax(requestItem.getProductData().getType(),
				requestItem.getProductData().getPrice())).
				thenReturn(tax);

		// then
		Invoice newInvoice = bookKeeper.issuance(invoiceRequest, taxPolicy);
		assertThat(newInvoice.getClient(), is(invoiceRequest.getClientData()));
	}

	@Test
	public final void testIssuance_testState_methodFactoryFromInvoiceFactoryShouldBeInvokedOneTime() {
		ClientData clientData = new ClientData(Id.generate(), "not important");
		Invoice invoice = new Invoice(Id.generate(), clientData);
		InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
		Money price = new Money(10);
		ProductData productData = new ProductData(Id.generate(), price, "not important", ProductType.DRUG, new Date());
		RequestItem item = new RequestItem(productData, 0, new Money(10));
		invoiceRequest.add(item);
		invoiceRequest.add(item);

		InvoiceFactory invoiceFactory = Mockito.mock(InvoiceFactory.class);
		TaxPolicy taxPolicy = Mockito.mock(TaxPolicy.class);
		Mockito.when(invoiceFactory.create(invoiceRequest.getClientData())).thenReturn(invoice);
		Mockito.when(taxPolicy.calculateTax(ProductType.DRUG, price)).thenReturn(
				new Tax(new Money(1d), "not important"));

		bookKeeper = new BookKeeper(invoiceFactory);
		Invoice newInvoice = bookKeeper.issuance(invoiceRequest, taxPolicy);
		Mockito.verify(invoiceFactory, Mockito.times(1)).create(clientData);
	}

}
