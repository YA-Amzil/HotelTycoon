package be.pxl.hotel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import be.pxl.hotel.domain.Building;
import be.pxl.hotel.domain.BuildingPlot;
import be.pxl.hotel.domain.Facility;
import be.pxl.hotel.domain.Hotel;
import be.pxl.hotel.domain.Transaction;
import be.pxl.hotel.domain.TransactionType;
import be.pxl.hotel.domain.Wallet;
import be.pxl.hotel.exception.ConstructionViolationException;
import be.pxl.hotel.exception.DuplicateFacilityException;
import be.pxl.hotel.exception.InvalidBookingException;
import be.pxl.hotel.exception.UnsufficientMoneyException;

@SpringBootTest
class HoteltycoonBackendApplicationTests {

	private Wallet wallet;
	private Hotel hotel;

	@BeforeEach
	void setUp() {
		wallet = new Wallet(1000000.0);
		hotel = new Hotel("My Hotel");
		hotel.setBuildingPlot(new BuildingPlot(50000.0, 5, true));
	}

	@Test
	void contextLoads() {
	}

	@Test
	void test_Wallet_RegisterPayment() {
		double initialAmount = wallet.getAmount();
		wallet.registerPayment(50.0, "Valid Payment");
		assertEquals(initialAmount - 50, wallet.getAmount(), 0.001);
		List<Transaction> transactions = wallet.getTransactions();
		assertEquals(2, transactions.size());
		Transaction lastTransaction = transactions.get(transactions.size() - 1);
		assertEquals(50.0, lastTransaction.getAmount(), 0.001);
		assertEquals(TransactionType.PAYMENT, lastTransaction.getTransactionType());
		assertEquals("Valid Payment", lastTransaction.getDescription());
	}

	@Test
	void test_Wallet_RegisterReceipt() {
		double initialAmount = wallet.getAmount();
		wallet.registerReceipt(30.0, "Valid Refund");
		assertEquals(initialAmount + 30, wallet.getAmount(), 0.001);
		List<Transaction> transactions = wallet.getTransactions();
		assertEquals(2, transactions.size());
		Transaction lastTransaction = transactions.get(transactions.size() - 1);
		assertEquals(30.0, lastTransaction.getAmount(), 0.001);
		assertEquals(TransactionType.RECEIPT, lastTransaction.getTransactionType());
		assertEquals("Valid Refund", lastTransaction.getDescription());
	}

	@Test
	void test_Wallet_RegisterPaymentNegativeAmountShouldThrowException() {
		assertThrows(IllegalArgumentException.class, () -> wallet.registerPayment(-50.0, "Invalid Payment"));
	}

	@Test
	void test_Wallet_RegisterReceiptNegativeAmountShouldThrowException() {
		assertThrows(IllegalArgumentException.class, () -> wallet.registerReceipt(-70.0, "Invalid Refund"));
	}

	@Test
	void test_Wallet_RegisterPayment_InsufficientFunds_ShouldThrowException() {
		assertThrows(UnsufficientMoneyException.class, () -> wallet.registerPayment(100000001.0, "Luxury Goods"));
	}

	@Test
	void test_Hotel_AddBuildingMainBuilding() {
		hotel.addBuilding(wallet);
		assertEquals(1, hotel.getBuildings().size());
		assertEquals("Main Building", hotel.getBuildings().get(0).getName());
		assertEquals(925000.0, wallet.getAmount(), 0.001);
	}

	@Test
	void test_Hotel_AddBuilding_AdditionalBuilding() {
		hotel.addBuilding(wallet);
		hotel.addBuilding(wallet);
		assertEquals(2, hotel.getBuildings().size());
		assertEquals("Additional Building 1", hotel.getBuildings().get(1).getName());
		assertEquals(870000.0, wallet.getAmount(), 0.001);
	}

	@Test
	void test_Hotel_AddBuilding_MaxBuildingsReached_ShouldThrowException() {
		for (int i = 0; i < 5; i++) {
			hotel.addBuilding(wallet);
		}
		assertThrows(ConstructionViolationException.class, () -> hotel.addBuilding(wallet));
	}

	@Test
	void test_Hotel_AddFacility() {
		Building mainBuilding = new Building(LocalDateTime.now(), 75000.0, "main building", hotel);
		hotel.getBuildings().add(mainBuilding);
		hotel.addFacility(Facility.SWIMMING_POOL, wallet);
		assertTrue(hotel.hasFacility(Facility.SWIMMING_POOL));
		assertEquals(982500.0, wallet.getAmount(), 0.001);
	}

	@Test
	void test_Hotel_AddTwoFacility() {
		Building mainBuilding = new Building(LocalDateTime.now(), 75000.0, "main building", hotel);
		hotel.getBuildings().add(mainBuilding);
		hotel.addFacility(Facility.SWIMMING_POOL, wallet);
		hotel.addFacility(Facility.WELLNESS, wallet);
		assertTrue(hotel.hasFacility(Facility.SWIMMING_POOL));
		assertTrue(hotel.hasFacility(Facility.WELLNESS));
		assertEquals(937500.0, wallet.getAmount(), 0.001);
	}

	@Test
	void test_Hotel_AddFacility_DuplicateFacility_ShouldThrowException() {
		Building mainBuilding = new Building(LocalDateTime.now(), 75000.0, "main building", hotel);
		hotel.getBuildings().add(mainBuilding);
		hotel.addFacility(Facility.SWIMMING_POOL, wallet);
		assertThrows(DuplicateFacilityException.class, () -> hotel.addFacility(Facility.SWIMMING_POOL, wallet));
	}

	@Test
	void test_Hotel_AddFacility_NoBuilding_ShouldThrowException() {
		assertThrows(ConstructionViolationException.class, () -> hotel.addFacility(Facility.SWIMMING_POOL, wallet));
	}

	@Test
	void test_Hotel_GetStars() {
		Building mainBuilding = new Building(LocalDateTime.now(), 75000.0, "main building", hotel);
		hotel.getBuildings().add(mainBuilding);
		hotel.addFacility(Facility.SWIMMING_POOL, wallet);
		hotel.addFacility(Facility.FITNESS, wallet);
		assertEquals(3, hotel.getStars());
	}

	@Test
	void test_Hotel_GetStars_MaxStars() {
		Building mainBuilding = new Building(LocalDateTime.now(), 75000.0, "main building", hotel);
		hotel.getBuildings().add(mainBuilding);
		hotel.addFacility(Facility.SWIMMING_POOL, wallet);
		hotel.addFacility(Facility.WELLNESS, wallet);
		hotel.addFacility(Facility.FITNESS, wallet);
		hotel.addFacility(Facility.RESTAURANT, wallet);
		assertEquals(5, hotel.getStars());
	}

	@Test
	void test_Hotel_GetPricePerNight() {
		Building mainBuilding = new Building(LocalDateTime.now(), 75000.0, "main building", hotel);
		hotel.getBuildings().add(mainBuilding);
		hotel.addFacility(Facility.SWIMMING_POOL, wallet);
		hotel.addFacility(Facility.FITNESS, wallet);

		double basePrice = hotel.getBuildingPlot().getPrice() / 400.0;
		double starSurcharge = (hotel.getStars() > 1) ? (hotel.getStars() - 1) * basePrice / 3 : 0;
		double expectedPrice = basePrice + starSurcharge + hotel.getPriceFacilities();

		assertEquals(expectedPrice, hotel.getPricePerNight(), 0.001);
	}

	@Test
	void test_Hotel_Book_SuccessfulBooking_ShouldRegisterReceipt() {
		int numberOfPersons = 2;
		int numberOfNights = 3;

		Building mainBuilding = new Building(LocalDateTime.now(), 75000.0, "main building", hotel);
		hotel.getBuildings().add(mainBuilding);
		hotel.book(numberOfPersons, numberOfNights, wallet);

		double totalPrice = hotel.getPricePerNight() * numberOfPersons * numberOfNights;
		assertEquals(1000000.0 + totalPrice, wallet.getAmount(), 0.001);

		List<Transaction> transactions = wallet.getTransactions();
		assertEquals(2, transactions.size());

		Transaction lastTransaction = transactions.get(transactions.size() - 1);
		assertEquals(totalPrice, lastTransaction.getAmount(), 0.001);
		assertEquals(TransactionType.RECEIPT, lastTransaction.getTransactionType());
		assertEquals("Payment for booking a room for " + numberOfPersons + " persons for " + numberOfNights + " nights",
				lastTransaction.getDescription());
	}

	@Test
	void test_Hotel_Book_InvalidBookingNoBuildings_ShouldThrowException() {
		assertThrows(InvalidBookingException.class, () -> hotel.book(2, 3, wallet));
	}

	@Test
	void test_Hotel_Book_InsufficientFunds_ShouldThrowException() {
		Building mainBuilding = new Building(LocalDateTime.now(), 75000.0, "main building", hotel);
		hotel.getBuildings().add(mainBuilding);
		wallet = new Wallet(0.0);
		UnsufficientMoneyException exception = assertThrows(UnsufficientMoneyException.class,
				() -> hotel.book(2, 3, wallet));
		assertEquals("Insufficient funds to book a room!", exception.getMessage());
	}

}
