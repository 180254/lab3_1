package pl.com.bottega.ecommerce.sales.domain.invoicing;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

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

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testIssuance_testReult_invoiceWithOneItem_shoudReturnOneItem() {
		ClientData clientData = new ClientData(Id.generate(), "not important");
		Invoice invoice = new Invoice(Id.generate(), clientData);
		InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
		Money price = new Money(10);
		ProductData productData = new ProductData(Id.generate(), price, "not important", ProductType.DRUG, new Date());
		RequestItem item = new RequestItem(productData, 0, new Money(10));
		invoiceRequest.add(item);

		InvoiceFactory invoiceFactory = Mockito.mock(InvoiceFactory.class);
		TaxPolicy taxPolicy = Mockito.mock(TaxPolicy.class);
		Mockito.when(invoiceFactory.create(invoiceRequest.getClientData())).thenReturn(invoice);
		Mockito.when(taxPolicy.calculateTax(ProductType.DRUG, price)).thenReturn(new Tax(new Money(1d), "not important"));
		
		bookKeeper = new BookKeeper(invoiceFactory);
		Invoice newInvoice = bookKeeper.issuance(invoiceRequest, taxPolicy);
		assertThat(newInvoice.getItems().size(), is(1));
	}

	@Test
	public final void testIssuance_testState_invoiceWithTwoItems_checkTaxPolicyInvokeCounter() {
		fail("Not yet implemented");
	}

}
