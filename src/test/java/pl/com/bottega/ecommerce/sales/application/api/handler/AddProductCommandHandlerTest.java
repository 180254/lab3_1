package pl.com.bottega.ecommerce.sales.application.api.handler;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.application.api.command.AddProductCommand;
import pl.com.bottega.ecommerce.sales.domain.client.ClientRepository;
import pl.com.bottega.ecommerce.sales.domain.equivalent.SuggestionService;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductRepository;
import pl.com.bottega.ecommerce.sales.domain.reservation.Reservation;
import pl.com.bottega.ecommerce.sales.domain.reservation.ReservationRepository;
import pl.com.bottega.ecommerce.system.application.SystemContext;

@RunWith(MockitoJUnitRunner.class)
public class AddProductCommandHandlerTest {

	@Mock
	ReservationRepository reservationRepository;
	@Mock
	ProductRepository productRepository;
	@Mock
	SuggestionService suggestionService;
	@Mock
	ClientRepository clientRepository;
	@Mock
	SystemContext systemContext;

	@InjectMocks
	AddProductCommandHandler addProductCommandHandler = new AddProductCommandHandler();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		systemContext = Mockito.mock(SystemContext.class);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testHandle_testState_reservationHasBeenSaved() {
		// when
		Product product = new ProductBuilder().build();
		Reservation reservation = new ReservationBuilder().build();
		AddProductCommand addProductCommand = new AddProductCommandBuilder().build();

		MockitoAnnotations.initMocks(addProductCommandHandler);
		Mockito.when(reservationRepository.load(Mockito.any(Id.class))).thenReturn(reservation);
		Mockito.when(productRepository.load(Mockito.any(Id.class))).thenReturn(product);
		addProductCommandHandler.handle(addProductCommand);

		// then
		Mockito.verify(reservationRepository, Mockito.times(1)).save(reservation);

	}
	
	@Test
	public final void testHandle_testState_reservationRipositoryLoadproperOrder() {
		Id orderID = new Id("167");
		Product product = new ProductBuilder().build();
		Reservation reservation = new ReservationBuilder().build();
		AddProductCommand addProductCommand = new AddProductCommandBuilder().withOrderId(orderID).build();

		MockitoAnnotations.initMocks(addProductCommandHandler);
		Mockito.when(reservationRepository.load(Mockito.any(Id.class))).thenReturn(reservation);
		Mockito.when(productRepository.load(Mockito.any(Id.class))).thenReturn(product);
		addProductCommandHandler.handle(addProductCommand);

		// then
		Mockito.verify(reservationRepository, Mockito.times(1)).load(orderID);
	}

}
