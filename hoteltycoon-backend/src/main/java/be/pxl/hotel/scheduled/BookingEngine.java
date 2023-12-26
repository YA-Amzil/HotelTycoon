package be.pxl.hotel.scheduled;

import be.pxl.hotel.domain.Hotel;
import be.pxl.hotel.service.HotelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class BookingEngine {

  static final Logger LOGGER = LoggerFactory.getLogger(BookingEngine.class);
  private final HotelService hotelService;
  private final static Random RANDOM = new Random();

  public BookingEngine(HotelService hotelService) {
    this.hotelService = hotelService;
  }

  @Scheduled(fixedDelay = 50)
  @Transactional(readOnly = true)
  public void createBooking() throws InterruptedException {
    List<Hotel> allHotels = hotelService.findAllOpenHotels();
    if (allHotels.isEmpty()) {
      LOGGER.info("No hotels to book...");
    } else {
      int hotelIndex = RANDOM.nextInt(allHotels.size());
      int people = RANDOM.nextInt(1, 5);
      int nights = RANDOM.nextInt(1, 12);
      hotelService.book(allHotels.get(hotelIndex), people, nights);
    }
  }
}
